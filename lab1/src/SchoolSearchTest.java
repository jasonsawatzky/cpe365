import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SchoolSearchTest {
	static OutputStream output;
	static InputStream dataInput;
	
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(SchoolSearchTest.class);
			
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	   }
	
	@BeforeClass
	public static void setupStudentData() {
		try {
			dataInput = new FileInputStream("./students.txt");
		} catch (FileNotFoundException e) {
			System.err.println("students.txt not found");
			System.exit(1);
		}
	}
	
	@Before
	public void resetOutput() {
		output = new ByteArrayOutputStream();
	}

	@Test
	public void test() {
		InputStream queryInput = new ByteArrayInputStream("S COMO\nQ".getBytes());
		String expectedResults = "Enter a query:\n" + 
				"Student: COMO, ZANDRA, Grade: 4, Classroom: 112, Teacher: CHIONCHIO, PERLA\r\n" + 
				"\n" + 
				"Enter a query:\n";
		
		SchoolSearch schoolSearch = new SchoolSearch(dataInput, queryInput, output);
		schoolSearch.start();

		assertEquals(expectedResults, output.toString());
	}

}
