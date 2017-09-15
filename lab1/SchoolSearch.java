import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;

public class SchoolSearch {
    private List<Student> students = new ArrayList<>();
    
    public static void main(String[] args) {
        SchoolSearch program = new SchoolSearch();
        program.runQueries();
    }
    
    private void runQueries() {
        readFile();
  
        String inputString = "";
        String[] tokens;
        Scanner input = new Scanner(System.in);
        char firstLetter;
        
        do {
            System.out.println("Enter a query:");
            tokens = input.nextLine().split("\\s");
            firstLetter = tokens[0].charAt(0); 

            switch(firstLetter) {
                case 'S':   studentLastname(tokens);
                            break;

                case 'T':   teacherLastname(tokens);
                            break;

                case 'B':   bus(tokens);
                            break;

                case 'G':   grade(tokens);
                            break;

                case 'A':   average(tokens);
                            break;

                case 'I':   info(tokens);
                			break;
            }

            System.out.println();            
        } while(firstLetter != 'Q');
    }


    private void readFile() {
      String line = null;
      String fileName = "./students.txt";
      Scanner s; 

      try{
         FileReader fileReader = new FileReader(fileName);
         BufferedReader br = new BufferedReader(fileReader);
         while((line = br.readLine()) != null){
            s = new Scanner(line).useDelimiter(",");
            String ln = s.next();
            String fn = s.next();
            int grade = s.nextInt();
            int cr = s.nextInt();
            int bus = s.nextInt();
            double gpa = s.nextDouble();
            String tln = s.next();
            String tfn = s.next();

            Student student = new Student(ln,fn,grade,cr,bus,gpa,tln,tfn);

            students.add(student);

         }
      }
      catch(FileNotFoundException ex) {
         System.out.println(
               "Unable to open file '" + 
               fileName + "'");                
      }
      catch(IOException ex) {
         System.out.println(
               "Error reading file '" 
               + fileName + "'");                  
      }
    }

    private void studentLastname(String[] tokens) {
    	if(tokens.length != 2) {
    		if(tokens.length != 3 || tokens[2].charAt(0) != 'B') {
    			return;
    		}
    	}

        List<Student> results = students.stream().filter(s -> s.lastName.equals(tokens[1])).collect(Collectors.toList());

        if(tokens.length == 2) {
	        for(Student student : results) {
	            System.out.println("Student: " + student.lastName + ", " + student.firstName + ", Grade: " + student.grade + ", Classroom: " + student.classroom + ", Teacher: " + student.tLastName + ", " + student.tFirstName);
	        }
    	}
    	else {
	        for(Student student : results) {
	            System.out.println("Student: " + student.lastName + ", " + student.firstName + ",  Bus:" + student.bus);
	        }
    	}
    }

    private void teacherLastname(String[] tokens) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void bus(String[] tokens) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void grade(String[] tokens) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void average(String[] tokens) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void info(String[] tokens) {
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
