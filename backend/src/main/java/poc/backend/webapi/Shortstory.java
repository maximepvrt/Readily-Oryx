package poc.backend.webapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import poc.backend.dao.ShortstoryDao;
import poc.backend.dao.ShortstoryDao.NotExists;
import poc.backend.dto.Rating;
import poc.backend.dto.Result;

@Path("shortstory")
public class Shortstory {
	
	@PUT
	@Path("rating")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result newAccount (Rating  rating) {

		
	}

}
