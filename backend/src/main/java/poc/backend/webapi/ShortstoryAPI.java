package poc.backend.webapi;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import poc.backend.dao.AccountDao;
import poc.backend.dao.RatingDao;
import poc.backend.dao.TextDao;
import poc.backend.dto.RatingDto;
import poc.backend.dto.Result;
import poc.backend.dto.TextDto;
import poc.backend.entity.Account;

@Path("shortstory")
public class ShortstoryAPI {

	@PUT
	@Path("rating")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result rating (@CookieParam("id") String id, RatingDto  ratingDto, @Context HttpServletResponse response) {
		Account account = AccountDao.get(id);
		if(account == null){
			return Result.KO;
		}
		response.addCookie(new Cookie("id", account.id));
		response.addCookie(new Cookie("login", account.login));
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
	public Result putstory (@CookieParam("id") String id, TextDto  textDto, @Context HttpServletResponse response) {
		Account account = AccountDao.get(id);
		if(account == null){
			return Result.KO;
		}
		response.addCookie(new Cookie("id", account.id));
		response.addCookie(new Cookie("login", account.login));
		boolean b = TextDao.saveOrUpdate(textDto, account.id);
		if(b){
			return Result.OK;
		}
		return Result.KO;
	}
	
	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Result account (@QueryParam("timeToRead") String timeToRead, @QueryParam("type") String type, @DefaultValue("All") 
    @QueryParam(value = "categorie") final List<String> categorieList) {
//		Account account = AccountDao.get(login, password);
//		if(account != null){
//			return new Result (Result.STATUS_OK, account);
//		}
		return Result.KO;
	}

}
