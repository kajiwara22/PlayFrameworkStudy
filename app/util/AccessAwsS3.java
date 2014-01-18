package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AccessAwsS3 {

	private static String outputDir = "/usr/local/temp/";

	private String accessKey;

	private String accessSecretKey;

	private String endPoint;

	private String bucketName;

	private String objectKey;

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setAccessSecretKey(String accessSecretKey) {
		this.accessSecretKey = accessSecretKey;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	private AmazonS3Client makeS3Client() {
		return this.makeS3Client(this.accessKey, this.accessSecretKey);
	}

	private AmazonS3Client makeS3Client(String accessKey, String accessSecretKey) {

	    // 認証オブジェクトを作成
	    AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecretKey);

	    // ConfigurationでTimeout時間を30秒に設定
	    ClientConfiguration clientConfiguration = new ClientConfiguration();
	    clientConfiguration.setConnectionTimeout(30000);

	    // AmazonS3Clientをインスタンス化
	    return new AmazonS3Client(credentials, clientConfiguration);
	}

	public InputStream getObject()
	{
		try {
			return this.getObject(this.bucketName, this.objectKey);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	// オブジェクト取得
	private  InputStream getObject(String bucketName, String objectKey) throws FileNotFoundException {
	     // AmazonS3Clientインスタンスを作成
	    AmazonS3Client cli = makeS3Client();

	     // エンドポイントを設定
	    cli.setEndpoint(this.endPoint);

	     // rootDirectory(Bucket名), objectKey(オブジェクトまでの相対パス)からリクエストを作成
	    GetObjectRequest request = new GetObjectRequest(bucketName, objectKey);

	    S3Object object = cli.getObject(request);

	    // Objectを開いたInputStreamを返す
	    return object.getObjectContent();
	}

	public File getS3Object() throws IOException, FileNotFoundException {

	    String bucketName = this.bucketName;
	    String objectKey = this.objectKey;

	    InputStream is = null;
	    FileOutputStream fos = null;
	    String fileOutputPath = outputDir + getOnlyFileName(objectKey);
	    File checkFile = new File(fileOutputPath);
	    try {
	        is = getObject(bucketName, objectKey);
	        fos = new FileOutputStream(fileOutputPath);

	        byte[] buffer = new byte[1024*1024];
	        int readSize = -1;
	        while( (readSize = is.read(buffer, 0, buffer.length)) != -1) {
	            fos.write(buffer, 0, readSize);
	        }
	        fos.flush();

	    } finally {
	        if (is != null) {
	            is.close();
	        }

	        if (fos != null) {
	            fos.close();
	        }
	    }
	    return checkFile;
	}

	private String getOnlyFileName(String fullPath)
	{
		String regex = "[^/]*$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(fullPath);
		if(m.find()){
			return m.group(0);
		}else{
			return "";
		}
	}

}
