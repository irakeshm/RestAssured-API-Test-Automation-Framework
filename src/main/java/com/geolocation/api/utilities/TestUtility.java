package com.geolocation.api.utilities;

import java.io.BufferedInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.csvreader.CsvReader;

public class TestUtility {

	public static String TESTDATA_CSV_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/geolocation/api/testdata/InputTestData.csv";
	public static String EXPECTED_RESULT_CSV_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/geolocation/api/testdata/ExpectedResult.csv";
	private static CsvReader reader = null;
	private static Object[][] testData = null;
	private static Object[][] resultData = null;
	static Logger log = Logger.getLogger(TestUtility.class);

	public static Object[][] getInputCSVData() {
		int index = 0;
		try {
			int csvRowCount = GetRowcount(TESTDATA_CSV_PATH);
			testData = new Object[csvRowCount][1];
			reader = new CsvReader(TESTDATA_CSV_PATH);
			while (reader.readRecord()) {
				testData[index][0] = reader.get(0);
				index++;
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			log.error("Exception Occured in getInputCSVData method" + ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			log.error("Exception Occured getInputCSVData method" + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Exception Occured getInputCSVData method" + ex.getMessage());
		}
		return testData;
	}

	public static Object[][] getExpectedResultCSVData() {
		int index = 0;
		try {
			int csvRowCount = GetRowcount(EXPECTED_RESULT_CSV_PATH);
			resultData = new Object[csvRowCount][4];
			reader = new CsvReader(EXPECTED_RESULT_CSV_PATH);
			while (reader.readRecord()) {
				resultData[index][0] = reader.get(0);
				resultData[index][1] = reader.get(1);
				resultData[index][2] = reader.get(2);
				resultData[index][3] = reader.get(3);
				index++;
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			log.error("Exception Occured in getExpectedResultCSVData method" + ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			log.error("Exception Occured in getExpectedResultCSVData method" + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Exception Occured in getExpectedResultCSVData method" + ex.getMessage());
		}
		return resultData;
	}

	public static int GetRowcount(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) 
			{
				empty = false;
				for (int i = 0; i < readChars; ++i) 
				{
					if (c[i] == '\n') 
					{
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}
