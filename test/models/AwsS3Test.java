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
		awsS3.accessKey="(please input your accesskey)";
		awsS3.secretKey="(please input your secretkey)";
		awsS3.region="(please input your region)";
		awsS3.bucketName="(please input your bucke name)";
		awsS3.objectKey="(please input your object key)";
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
