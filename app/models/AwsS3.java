package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class AwsS3 extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133254580847071005L;
	@Id
	public Long id;
	public String accessKey;
	public String seacretKey;

	public static Finder<Long,AwsS3> find = new Finder<Long,AwsS3>(Long.class,AwsS3.class);

	@Override
	public String toString(){
		return ("accessKey:"+accessKey+",seacretKey:"+seacretKey);

	}

}
