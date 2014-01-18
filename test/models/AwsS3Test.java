package models;


import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import static org.fest.assertions.Assertions.*;

public class AwsS3Test {

	@Test
	public void getJsonTest() throws Exception{
		AwsS3 awsS3 = new AwsS3();
		awsS3.accessKey="AKIAJ2EHVUX57DUOYKPQ";
		awsS3.secretKey="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";
		awsS3.endPoint="https://s3-ap-northeast-1.amazonaws.com";
		awsS3.bucketName="gitandvpnserver";
		awsS3.objectKey="20140120/20140120.zip";
		String result = awsS3.getJson();
		StringBuilder retText = new StringBuilder();
		File file = new File("/Users/KAJIWARAYutaka/Documents/20140118Json.txt");
		Path path = file.toPath();
		for (String readLine : Files.readAllLines(path, Charset.defaultCharset())) {
			retText.append(readLine);
		}
		assertThat(result).isEqualTo(retText.toString());
	}

}
