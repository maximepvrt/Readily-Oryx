package poc.backend.webapi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import poc.backend.checker.Verify;
import poc.backend.dao.AccountDao;
import poc.backend.dao.RatingDao;
import poc.backend.dao.TextDao;
import poc.backend.dto.RatingDto;
import poc.backend.dto.Result;
import poc.backend.dto.SmallText;
import poc.backend.dto.TextDto;
import poc.backend.entity.Account;
import poc.backend.entity.Text;

@Path("shortstory")
public class ShortstoryAPI {

	@PUT
	@Path("rating")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result rating (@CookieParam("id") String id, RatingDto  ratingDto) {
		Account account = AccountDao.get(id);
		if(account == null){
			return Result.KO;
		}
		boolean b = RatingDao.create(ratingDto);
		if(b){
			return Result.OK;
		}
		return Result.KO;
	}
	
	@PUT
	@Path("putstory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result putstory (@CookieParam("id") String id, TextDto  textDto) {
		Account account = AccountDao.get(id);
		if(account == null){
			return new Result (Result.STATUS_KO, "accountNull");
		}
		textDto = Verify.verifyPutStory(textDto);
		if(textDto != null){
			System.out.println(account.login);
			boolean b = TextDao.saveOrUpdate(textDto, account.login);
			if(b){
				return Result.OK;
			}
			return Result.KO;
		}
		return new Result (Result.STATUS_KO, "badParameters");
	}

	
	static public class FindDTO {
		public String timeToRead;
		public String type;
		public String category;
	}
	
	@POST
	@Path("findlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result findlist (FindDTO query) {
		
		Integer timeToRead = null;
		if(query.timeToRead != null){
			timeToRead = Integer.parseInt(query.timeToRead);
		}
		Integer min = 0;
		// if(timeToRead != null){min = timeToRead/2;}
		Integer max = 9999;
		if(timeToRead != null){max = timeToRead+timeToRead/2;}
		System.out.println("timeToRead:"+timeToRead);
		System.out.println("category:"+query.category);
		System.out.println("type:"+query.type);
		if ("Toutes".equalsIgnoreCase(query.category)) {
			query.category = null;
		}
		
		List<Text> textList = TextDao.search(min,max, query.type, query.category,0,5);
		System.out.println("mouhahaha:"+textList);
		if(textList != null && !textList.isEmpty()){
			List<SmallText> smallTextList = new ArrayList<SmallText>();
			for(Text text : textList){
				System.out.println(text.category+" "+text.id);
				smallTextList.add(new SmallText(text.id, text.title, text.summary, text.category, text.type, text.timeToRead));
			}
			return new Result (Result.STATUS_OK, smallTextList);
		}
		return Result.KO;
	}
	
	@GET
	@Path("findtext")
	@Produces(MediaType.APPLICATION_JSON)
	public Result account (@QueryParam("id") String id) {
		Text text = TextDao.get(id);
		if(text != null){
			return new Result (Result.STATUS_OK, text);
		}
		return Result.KO;
	}

}
