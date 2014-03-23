window.Readily = window.Readily || {};

(function(Readily){
	
	var base = "http://localhost:8080/backend/webapi";
	
	function Callback(status, data) {
		this.status = status;
		this.data = data;
	}
	
	function httpCall(verb, url, param, callback) {
		var params = {
				dataType: "application/json",
				type: verb,
	            url: base + url,
	            data: param,
	            contentType: "application/json",
	            success: function(data) {
	            	if (data.status == "OK")
	            		callback(new Callback("OK",data));
					else
						callback(new Callback("KO",data));
	            },
	            error: function(data) {
	            	 callback = new Callback("KO",data);
	            }				
		};
		$.ajax(params);
	}
	
	function Connection() {
		
		this.login = function(login, password, callback) {
			// httpCall("POST", "/account/login", {login:login, password:password}, callback);
			httpCall("GET", "/account/find", {login:login, password:password}, callback);
		};
		
		this.newAccount = function(login, password, birthyear, gender, callback) {
			var data = {login:login, password:password, birthyear:birthyear, gender:gender};
			httpCall("PUT", "/account/newaccount", JSON.stringify(data) ,callback);
		};
		
		this.newaccount = function(login, password, birthyear, gender, callback) {
			httpCall("POST", "shortstory/find?lenght={length}&type={type}&number={nb}", "", callback);
		};
		
	}

	Readily.Connection = new Connection();
	
})(window.Readily);
