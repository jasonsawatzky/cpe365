import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.Consumer;

public class SchoolSearch {
    private List<Student> students = new ArrayList<>();
	private InputStream dataInput;
	private InputStream queryInput;
	private OutputStream output;
	private PrintStream out;

    public static void main(String[] args) {
    	InputStream dataInput = null;
		try {
			dataInput = new FileInputStream("./students.txt");
		} catch (FileNotFoundException e) {
			System.err.println("students.txt not found");
			System.exit(1);
		}
    	InputStream queryInput = System.in;
    	OutputStream output = System.out;
    	
        SchoolSearch schoolSearch = new SchoolSearch(dataInput, queryInput, output);
        schoolSearch.start();
    }
    
    public SchoolSearch(InputStream dataInput, InputStream queryInput, OutputStream output) {
    	this.dataInput = dataInput;
    	this.queryInput = queryInput;
    	this.output = output;
    	this.out = new PrintStream(output);
    	readStudents();
    }

    public void start() {
        String inputString = "";
        Scanner queryScanner = new Scanner(queryInput);
        
        char firstLetter;

        do {
            out.println("Enter a query:");
            
            inputString = queryScanner.nextLine();
            firstLetter = inputString.charAt(0);

            switch(firstLetter) {
                case 'S':   studentLastname(inputString);
                            break;

                case 'T':   teacherLastname(inputString);
                            break;

                case 'B':   bus(inputString);
                            break;

                case 'G':   grade(inputString);
                            break;

                case 'A':   average(inputString);
                            break;

                case 'I':   info(inputString);
                			break;
            }

        } while(firstLetter != 'Q');
    }


    private void readStudents() {
    	String line;
        Scanner studentScanner = new Scanner(dataInput).useDelimiter(",");
        
        while(studentScanner.hasNextLine()){
        	line = studentScanner.nextLine();
            String ln = studentScanner.next();
            String fn = studentScanner.next();
            int grade = studentScanner.nextInt();
            int cr = studentScanner.nextInt();
            int bus = studentScanner.nextInt();
            double gpa = studentScanner.nextDouble();
            String tln = studentScanner.next();
            String tfn = studentScanner.next();

            Student student = new Student(ln,fn,grade,cr,bus,gpa,tln,tfn);

            students.add(student);

         }
      }
     
   

    private void studentLastname(String input) {
    	Pattern commandFormat = Pattern.compile("^\\s?(?:S|Student|s)\\s(\\w+)\\s?(B|b?)$");
      Matcher matcher = commandFormat.matcher(input);
      Consumer<Student> printer;

      if(!matcher.matches()) {
        return;
      }

      List<Student> results = students.stream().filter(s -> s.lastName.equalsIgnoreCase(matcher.group(1))).collect(Collectors.toList());
      if(matcher.group(2).equals("")) {
        printer = student ->
        	out.println("Student: " + student.lastName + ", " + student.firstName + ", Grade: " + student.grade + ", Classroom: " + student.classroom + ", Teacher: " + student.tLastName + ", " + student.tFirstName);
 
      }
      else {
        printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ",  Bus:" + student.bus);
      }

      results.stream().forEach(printer);
    }

    private void teacherLastname(String input) {
      Pattern commandFormat = Pattern.compile("^\\s?(?:T|Teacher|t)\\s(\\w+)\\s?");
      Matcher matcher = commandFormat.matcher(input);
      Consumer<Student> printer;

      if(!matcher.matches()) {
        return;
      }

      List<Student> results = students.stream().filter(s -> s.tLastName.equalsIgnoreCase(matcher.group(1))).collect(Collectors.toList());

      printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName);
      results.stream().forEach(printer);
    }

    private void bus(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void grade(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void average(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void info(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class Student{

      String lastName;
      String firstName;
      int grade;
      int classroom;
      int bus;
      double gpa;
      String tLastName;
      String tFirstName;

      public Student(String ln, String fn, int grade, int cr, int bus, double gpa,
            String tln, String tfn){
         lastName = ln;
         firstName = fn;
         this.grade = grade;
         classroom = cr;
         this.bus = bus;
         this.gpa = gpa;
         tLastName = tln;
         tFirstName = tfn;
      }

   }

}
