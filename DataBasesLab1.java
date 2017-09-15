/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;

/**
 *
 * @author jsawatzk
 */
public class DataBasesLab1 {
    private List<Student> students = new ArrayList<>();
    
    public static void main(String[] args) {
        DataBasesLab1 program = new DataBasesLab1();
        program.runQueries();
    }
    
    private void runQueries() {
        readFile();
  
        String inputString = "";
        Scanner input = new Scanner(System.in);
        char firstLetter;
        
        do {
            System.out.println("Enter a query");
            inputString = input.nextLine();
            
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
            }
            
        } while(!(inputString.equals("Q") || inputString.equals("Quit")));
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

    private void studentLastname(String inputString) {
        List<Student> results = students.stream().filter(s -> s.last_name.length() > 1).collect(Collectors.toList());
        for(Student student : results) {
            System.out.println(student.last_name);
        }
    }

    private void teacherLastname(String inputString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void bus(String inputString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void grade(String inputString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void average(String inputString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void info(String inputString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class Student{

      String last_name;
      String first_name;
      int grade;
      int classroom;
      int bus;
      double gpa;
      String t_last_name;
      String t_first_name;

      public Student(String ln, String fn, int grade, int cr, int bus, double gpa, 
            String tln, String tfn){
         last_name = ln;
         first_name = fn;
         this.grade = grade;
         classroom = cr;
         this.bus = bus;
         this.gpa = gpa;
         t_last_name = tln;
         t_first_name = tfn;
      }

   }
    
}
