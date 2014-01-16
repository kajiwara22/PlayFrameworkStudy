package util;

import static org.fest.assertions.Assertions.*;

import  org.junit.*;

import java.io.File;

import org.junit.Test;

import util.Unzip;
public class UnzipTest {

	@Test
	public void testDecode() {
		File file = new File("/Users/KAJIWARAYutaka/test.zip");
		String outputPath ="/Users/KAJIWARAYutaka/output";
		Unzip unzip = new Unzip();
		unzip.decode(file,outputPath);
		File outputCheck = new File(outputPath+"/test.json");
		assertThat(outputCheck.exists()).isEqualTo(true);
	}

}
