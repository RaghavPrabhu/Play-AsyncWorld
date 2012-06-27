package controllers;

import static play.libs.Json.toJson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.raghav.play.asyncsample.util.AsyncConstants;

import play.libs.Akka;
import play.libs.F.Function;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.asyncworld;

/**
 * Application.java
 *
 * @author Raghav Prabhu
 * @version 1.0
 */

public class Application extends Controller {
  
	
	/**
	 * Home page render with the string
	 */
  public static Result index() {
    return ok(asyncworld.render("User"));
    
  }
  
  /**
   * Asynchronous method to provide the JSON result
   */
  public static Result fetchPopuMessage(final String text){
	  return async(Akka.future(new Callable<String>() {

		public String call() throws Exception {
			return text;
		}
	}).map(new Function<String, Result>() {

		@Override
		public Result apply(String data) throws Throwable {
             Map<String,String> jsonData = new HashMap<String, String>();
             jsonData.put(AsyncConstants.USER_NAME, data);
             jsonData.put(AsyncConstants.CURRENT_TIME, new Date().toString());
           
             return ok(toJson(jsonData));
		
		}
	}));
  }
  
  
}