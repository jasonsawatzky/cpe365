import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.Consumer;

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
            inputString = input.nextLine();
            tokens = inputString.split("\\s");
            firstLetter = tokens[0].charAt(0);

            switch(firstLetter) {
                case 'S':   studentLastname(inputString);
                            break;

                case 'T':   teacherLastname(inputString);
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

    private void studentLastname(String input) {
    	Pattern commandFormat = Pattern.compile("^\\s?(?:S|Student|s)\\s(\\w+)\\s?(B|b?)$");
      Matcher matcher = commandFormat.matcher(input);
      Consumer<Student> printer;

      if(!matcher.matches()) {
        return;
      }

      List<Student> results = students.stream().filter(s -> s.lastName.equalsIgnoreCase(matcher.group(1))).collect(Collectors.toList());
      if(matcher.group(2).equals("")) {
        printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ", Grade: " + student.grade + ", Classroom: " + student.classroom + ", Teacher: " + student.tLastName + ", " + student.tFirstName);
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
