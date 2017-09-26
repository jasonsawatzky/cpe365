import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SchoolSearchTest {
	static OutputStream output;
	static FileInputStream dataInput;
	
	@BeforeClass
	public static void setupIO() {
		try {
			dataInput = new FileInputStream("./students.txt");
		} catch (FileNotFoundException e) {
			System.err.println("students.txt not found");
			System.exit(1);
		}
		
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
	}
	

	@Test
	public void test() {
		String queries = "S COMO\nQ";
		
		String expectedResults = "Enter a query:\n" + 
				"Student: COMO, ZANDRA, Grade: 4, Classroom: 112, Teacher: CHIONCHIO, PERLA\r\n" + 
				"\n" + 
				"Enter a query:\n";
		
		assertEquals(expectedResults, runQueries(queries));
	}
	
	@Test
	public void test2() {
		String queries = "S COMO B\nQ";
		
		String expectedResults = "Enter a query:\n" + 
				"Student: COMO, ZANDRA, Bus: 53\r\n" + 
				"\n" + 
				"Enter a query:\n";
		
		assertEquals(expectedResults, runQueries(queries));
	}
	
	public String runQueries(String queries) {		
		InputStream queryInput = new ByteArrayInputStream(queries.getBytes());
		System.setIn(queryInput);
		
		/*SchoolSearch schoolSearch = new SchoolSearch(dataInput);
		
		schoolSearch.start();*/
		
		try {
			queryInput.close();
			//output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();
	}

}
