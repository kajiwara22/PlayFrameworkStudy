package models;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

import util.AccessAwsS3;

@Entity
public class AwsS3 extends Model{

	/**
	 *
	 */
	private static final long serialVersionUID = 6133254580847071005L;
	@Id
	public Long id;
	public String subject;
	public String accessKey;
	public String secretKey;
	public String endPoint;
	public String bucketName;
	public String objectKey;

	public static Finder<Long,AwsS3> find = new Finder<Long,AwsS3>(Long.class,AwsS3.class);

	@Override
	public String toString(){
		return ("accessKey:"+accessKey+",secretKey:"+secretKey);
	}

	public String getJson(){
		AccessAwsS3 accessAwsS3 = new AccessAwsS3();
		accessAwsS3.setAccessKey(accessKey);
		accessAwsS3.setAccessSecretKey(secretKey);
		accessAwsS3.setBucketName(bucketName);
		accessAwsS3.setEndPoint(endPoint);
		accessAwsS3.setObjectKey(objectKey);
		StringBuilder retText = new StringBuilder();
		try {
			File getFile = accessAwsS3.getS3Object();
			if(getFile.exists())
			{
				util.Unzip unzip = new util.Unzip();
				unzip.decode(getFile, getFile.getParent());
				File dir = new File(getFile.getParent());
				for (File file : dir.listFiles()) {
					Path path = file.toPath();
					if(path.toString().endsWith(".json")||path.toString().endsWith(".txt")){
						for (String readLine : Files.readAllLines(path, Charset.defaultCharset())) {
							retText.append(readLine);
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			retText.append("Fileが見つかりませんでした。");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			retText.append("IOExceptionが発生しました。");
		}

		return retText.toString();
	}

}
