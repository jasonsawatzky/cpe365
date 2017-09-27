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
import java.util.HashMap;
import java.util.Map;

public class SchoolSearch {
    private static final int MINGRADE = 0;
    private static final int MAXGRADE = 6;
    private List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        SchoolSearch program = new SchoolSearch();
        program.runQueries();
    }

    private void runQueries() {
        Map<Integer, Teacher> teachers = readTeachers();
        readFile(teachers);

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

                case 'B':   bus(inputString);
                            break;
                
                case 'C':   classSearch(inputString);
                            break;

                case 'G':   grade(inputString);
                            break;

                case 'A':   average(inputString);
                            break;

                case 'I':
                            for(int i = MINGRADE; i <= MAXGRADE; i++){
                                info(i, inputString);
                            }
                			break;
            }

            System.out.println();
        } while(firstLetter != 'Q' && firstLetter != 'q');
    }

    private Map<Integer, Teacher> readTeachers() {
        String line = null;
        String fileName = "./teachers.txt";
        Map<Integer, Teacher> teachers = new HashMap<>();
        Scanner s;
        String fn;
        String ln;
        int rn;

        try{
           FileReader fileReader = new FileReader(fileName);
           BufferedReader br = new BufferedReader(fileReader);
           while((line = br.readLine()) != null){
              s = new Scanner(line).useDelimiter(", ");
              ln = s.next();
              fn = s.next();
              rn = Integer.parseInt(s.next());
              teachers.put(new Integer(rn), new Teacher(ln, fn));
           }
        }
        catch(FileNotFoundException ex) {
           System.out.println(
                 "Unable to open file '" +
                 fileName + "'");
           		System.exit(1);
        }
        catch(IOException ex) {
           System.out.println(
                 "Error reading file '"
                 + fileName + "'");
    		 System.exit(1);

        }

        return teachers;
    }

    private void readFile(Map<Integer, Teacher> teachers) {
      String line = null;
      String fileName = "./list.txt";
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
            Teacher t = teachers.get(cr);

            Student student = new Student(ln,fn,grade,cr,bus,gpa, t);

            students.add(student);

         }
      }
      catch(FileNotFoundException ex) {
         System.out.println(
               "Unable to open file '" +
               fileName + "'");
         		System.exit(1);
      }
      catch(IOException ex) {
         System.out.println(
               "Error reading file '"
               + fileName + "'");
  		 System.exit(1);

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
        printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ", Grade: " + student.grade + ", Classroom: " + student.classroom + ", Teacher: " + student.teacher.lastName + ", " + student.teacher.firstName);
      }
      else {
        printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ", Bus: " + student.bus);
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

      List<Student> results = students.stream().filter(s -> s.teacher.lastName.equalsIgnoreCase(matcher.group(1))).collect(Collectors.toList());

      printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName);
      results.stream().forEach(printer);
    }

    private void bus(String input) {
        Pattern commandFormat = Pattern.compile("^\\s?(?:B|Bus|b)\\s(\\w\\w\\w?)\\s?$");
        Matcher matcher = commandFormat.matcher(input);
        Consumer<Student> printer;
        Integer bus;

        if(!matcher.matches()){
            return;
        }

        bus = Integer.parseInt(matcher.group(1));

        List<Student> results = students.stream().filter(s -> s.bus.equals(bus)).collect(Collectors.toList());

        printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ", Grade: " + student.grade + ", Classroom: " + student.classroom);

        results.stream().forEach(printer);

    }

    private void grade(String input) {
        Pattern commandFormat = Pattern.compile("^\\s?(?:G|Grade|g)\\s(\\w)\\s?((?:H|High|h|L|Low|l|T|t|Teacher)?)$");
        double tempGPA = 0;
        double temp2GPA = 5;
        Integer grade, teach = 0;
        final double GPAFinal;
        final double GPAFinal2;
        Matcher matcher = commandFormat.matcher(input);
        Consumer<Student> printer;
        Consumer<Teacher> printerT;
        
        if(!matcher.matches()) {
            return;
        }
        grade = Integer.parseInt(matcher.group(1));
        List<Student> results = students.stream().filter(s -> s.grade.equals(grade)).collect(Collectors.toList());
        List<Teacher> tlist = new ArrayList<>();
        
        if(matcher.group(2).equals("")) {
            printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName);
            results.stream().forEach(printer);
        }
        else {
            if(matcher.group(2).equals("H") || matcher.group(2).equals("h") || matcher.group(2).equals("High")){
                for(int i =0; i < results.size(); i++){
                    if(results.get(i).gpa > tempGPA){
                        tempGPA = results.get(i).gpa;
                    }
                }
                GPAFinal = tempGPA;
                results = results.stream().filter(s -> s.gpa.equals(GPAFinal)).collect(Collectors.toList());
            }
            
            if(matcher.group(2).equals("L") || matcher.group(2).equals("l") || matcher.group(2).equals("Low")){
                
                for(int i = 0; i < results.size(); i ++){
                    if(results.get(i).gpa < temp2GPA){
                        temp2GPA = results.get(i).gpa;
                    }
                }
                GPAFinal2 = temp2GPA;
                results = results.stream().filter(s -> s.gpa.equals(GPAFinal2)).collect(Collectors.toList());
            }
            
            if(matcher.group(2).equals("T") || matcher.group(2).equals("t") || matcher.group(2).equals("Teacher")){
                teach = 1;
                results = results.stream().filter(s -> s.grade.equals(grade)).collect(Collectors.toList());
                for(int i = 0; i < results.size(); i++){
                    if(!tlist.contains(results.get(i).teacher)){
                        tlist.add(results.get(i).teacher);
                    }
                }
                
            }
            
            if(teach == 0){
                printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName + ", GPA: " + student.gpa + ", Teacher: " + student.teacher.lastName + ", " + student.teacher.firstName + ", Bus: " + student.bus);
                results.stream().forEach(printer);
            }
            
            if(teach == 1){
                printerT = teacher -> System.out.println("Teacher: " + teacher.lastName + ", " + teacher.firstName);
                tlist.stream().forEach(printerT);
            }
        }
        
    }
    private void average(String input) {
        Pattern commandFormat = Pattern.compile("^\\s?(?:A|Average|a)\\s(\\w)\\s?$");
        Double GPATotal = 0.0;
        Double avgGPA;
        Integer grade;
        Matcher matcher = commandFormat.matcher(input);
        Consumer<Student> printer;
        List<Teacher> tlist = new ArrayList<>();

        if(!matcher.matches()) {
            return;
        }
        grade = Integer.parseInt(matcher.group(1));
        List<Student> results = students.stream().filter(s -> s.grade.equals(grade)).collect(Collectors.toList());
        for(int i = 0; i < results.size(); i++){
            System.out.printf("Student %f total GPA %f\n",results.get(i).gpa,GPATotal);
            GPATotal = GPATotal + results.get(i).gpa;
        }
        avgGPA = GPATotal/results.size();
            System.out.println("Grade: " + grade + ", " + "Average: " + avgGPA);

    }

    private void info(Integer grade, String input) {
    	 Pattern commandFormat = Pattern.compile("^\\s?(?:I|Info|i)\\s?$");
         Matcher matcher = commandFormat.matcher(input);
         Consumer<Student> printer;

         if(!matcher.matches()) {
             return;
         }

        List<Student> results = students.stream().filter(s -> s.grade.equals(grade)).collect(Collectors.toList());
        System.out.printf("%d: %d\n",grade,results.size());
    }

    private void classSearch(String input){
        Integer classroom;
        Pattern commandFormat = Pattern.compile("^\\s?(?:C|Class|c)\\s(\\w\\w\\w)\\s?((?:T|Teacher|t)?)$");
        Matcher matcher = commandFormat.matcher(input);
        Consumer<Student> printer;
        Consumer<Teacher> printerT;
        
        
        if(!matcher.matches()) {
            return;
        }
         classroom = Integer.parseInt(matcher.group(1));
        List<Student> results = students.stream().filter(s -> s.classroom.equals(classroom)).collect(Collectors.toList());
        List<Teacher> tlist = new ArrayList<>();
        
        if(matcher.group(2).equals("")) {
            printer = student -> System.out.println("Student: " + student.lastName + ", " + student.firstName);
            results.stream().forEach(printer);
        }
        
        if(matcher.group(2).equals("T") || matcher.group(2).equals("t") || matcher.group(2).equals("Teacher")){
            for(int i = 0; i < results.size(); i++){
                if(!tlist.contains(results.get(i).teacher)){
                    tlist.add(results.get(i).teacher);
                }
            }
            printerT = teacher -> System.out.println("Teacher: " + teacher.lastName + ", " + teacher.firstName);
            tlist.stream().forEach(printerT);
        }

        
    }
    
    private static class Student {

      String lastName;
      String firstName;
      Integer grade;
      Integer classroom;
      Integer bus;
      Double gpa;
      Teacher teacher;

      public Student(String ln, String fn, Integer grade, Integer cr, Integer bus, Double gpa, Teacher t){
         lastName = ln;
         firstName = fn;
         this.grade = grade;
         classroom = cr;
         this.bus = bus;
         this.gpa = gpa;
         teacher = t;
      }

   }

   private static class Teacher {
     String lastName;
     String firstName;

     public Teacher(String ln, String fn) {
       lastName = ln;
       firstName = fn;
     }
   }

}
