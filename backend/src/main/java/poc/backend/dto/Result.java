package poc.backend.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Result {
	public static final String STATUS_KO = "KO";
	public static final String STATUS_OK = "OK";
	public String status;
	public Object message;
	public Result(){}
	public Result(String status, Object message){
		this.status = status;
		this.message = message;
	}
	static public Result KO = new Result(STATUS_KO, null);
	static public Result OK = new Result(STATUS_OK, null);
	
	
}
