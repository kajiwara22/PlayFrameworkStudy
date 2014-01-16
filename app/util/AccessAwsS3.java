package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AccessAwsS3 {

	
	
	private AmazonS3Client makeS3Client(String accessKey, String accessSecretKey) {
		 
	    // 認証オブジェクトを作成
	    AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecretKey);
	 
	    // ConfigurationでTimeout時間を30秒に設定
	    ClientConfiguration clientConfiguration = new ClientConfiguration();
	    clientConfiguration.setConnectionTimeout(30000);
	 
	    // AmazonS3Clientをインスタンス化
	    return new AmazonS3Client(credentials, clientConfiguration);
	}
	
	// オブジェクト取得
	public InputStream getObject(String bucketName, String objectKey) throws FileNotFoundException {
	     // AmazonS3Clientインスタンスを作成
	    AmazonS3Client cli = makeS3Client();
	 
	     // エンドポイントを設定
	    cli.setEndpoint(ENDPOINT);
	 
	     // rootDirectory(Bucket名), objectKey(オブジェクトまでの相対パス)からリクエストを作成
	    GetObjectRequest request = new GetObjectRequest(bucketName, objectKey);
	 
	    S3Object object = cli.getObject(request);
	     
	    // Objectを開いたInputStreamを返す
	    return object.getObjectContent();
	}
	
	public void getS3Object() throws IOException, FileNotFoundException {
	     
	    String bucketName = "e987blkjvanlekjr23buaosda";
	    String objectKey = "sample/20130203/code/sample.bin";
	 
	    InputStream is = null;
	    FileOutputStream fos = null;
	 
	    try {
	        is = getObject(bucketName, objectKey);
	        fos = new FileOutputStream("/usr/local/temp/hoge.bin");
	         
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
	}
	
}
