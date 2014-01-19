package controllers;

import models.AwsS3;

import java.util.*;
import com.amazonaws.regions.Regions;
import play.*;
import play.mvc.*;
import views.html.*;
import views.html.defaultpages.error;
import play.data.*;
import scala.Tuple2;
import static play.data.Form.*;

public class Application extends Controller {

	public static Result add() {
		Form<AwsS3> f = new Form<AwsS3>(AwsS3.class);
		List<Tuple2<String,String>>  opts = new ArrayList<Tuple2<String,String>>();
    	for (Regions regions : Regions.values()) {
    		opts.add(new Tuple2<String,String>(regions.getName(),regions.getName()));
		}
		return ok(add.render("投稿フォーム",f,opts));
		//return null;
	}

	public static Result create(){
		Form<AwsS3> f = new Form<AwsS3>(AwsS3.class).bindFromRequest();
		if(!f.hasErrors()){
			AwsS3 data = f.get();
			data.save();
			return redirect("/");
		}else{
			List<Tuple2<String,String>>  opts = new ArrayList<Tuple2<String,String>>();
	    	for (Regions regions : Regions.values()) {
	    		opts.add(new Tuple2<String,String>(regions.getName(),regions.getName()));
			}
			return badRequest(add.render("ERROR",f,opts));
			//return null;
		}
	}

    public static Result index() {
    	List<AwsS3> data = AwsS3.find.all();
        return ok(index.render("Your new application is ready.",data));
    }

    public static Result select(){
    	List<AwsS3> data = AwsS3.find.all();
    	List<Tuple2<String,String>>  opts = new ArrayList<Tuple2<String,String>>();
    	for (AwsS3 awss3 : data) {
			opts.add(new Tuple2<String,String>(awss3.id.toString(),awss3.subject));
		}
    	Form<AwsS3> form = new Form<>(AwsS3.class);
    	return ok(sel.render("Please select value", form, opts));
    }

    public static Result showJson(){
    	Form<AwsS3> f = new Form<AwsS3>(AwsS3.class).bindFromRequest();
		AwsS3 resultdata = f.get();
		List<AwsS3> targetData = AwsS3.find.where().eq("id", resultdata.id).findList();
		if(targetData.size()>0)
		{
			return ok(targetData.get(0).getJson()).as("json");
		}
		else {
			return ok("error");
		}
    }

}
