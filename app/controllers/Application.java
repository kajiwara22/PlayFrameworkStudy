package controllers;

import models.AwsS3;

import java.util.*;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;

public class Application extends Controller {

	public static Result add() {
		Form<AwsS3> f = new Form(AwsS3.class);
		return ok(add.render("投稿フォーム",f));
		//return null;
	}
	
	public static Result create(){
		Form<AwsS3> f = new Form(AwsS3.class).bindFromRequest();
		if(!f.hasErrors()){
			AwsS3 data = f.get();
			data.save();
			return redirect("/");
		}else{
			return badRequest(add.render("ERROR",f));
			//return null;
		}
	}
	
    public static Result index() {
    	List<AwsS3> data = AwsS3.find.all();
        return ok(index.render("Your new application is ready.",data));
    }

}
