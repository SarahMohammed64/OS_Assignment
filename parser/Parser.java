/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parser;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;

/**
 *
 * @author Hala
 */
public class Parser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        while (true){
        // TODO code application logic here
 terminal newobject =new terminal();
 //newopject.help();
 //newopject.cd();
 // newopject.cp();
 InetAddress ip;
            String host;ip = InetAddress.getLocalHost();
host = ip.getHostName();
System.out.println(host + ":~$ ");
Scanner scanner = new Scanner(System.in);
      
        String commands = scanner.nextLine();

        String[] commandArray = commands.split("&");


        for (String command : commandArray) {
            
            command = command.trim();
            if (command.startsWith("cd")) {
                String directory = command.substring(2).trim();
                newobject.testDirectory(directory);
               newobject.cd(directory);
            }else if(command.startsWith("exit")){
                System.out.println("Thank you for using our terminal ..^_^ ");
                        System.exit(0);
            }
            else if (command.startsWith("ls")) {
                Scanner newfilepath = new Scanner(System.in); //1
                    String NEW_File_path = newfilepath.nextLine();  //2  ----------1&2 helping me to take input
                    newobject.ls(NEW_File_path);
                  
            } else if (command.startsWith("cp")) {
                String sourceFilePath = command.substring(2).trim();
                String destinationDirectoryPath = scanner.nextLine().trim();
                newobject.testDirectory(sourceFilePath);
                newobject.testDirectory(destinationDirectoryPath);
                newobject.cp(sourceFilePath, destinationDirectoryPath);
                
            } else if (command.startsWith("help")){
                newobject.help();
            } 
            else if (command.startsWith("mv")) {
                    Scanner get = new Scanner(System.in);
                String secondArg = get.next();
                newobject.mv( secondArg);
            } else if (command.startsWith("rm")) {
               newobject.rm(command);
            } else if (command.startsWith("mkdir")) {
               newobject.mkdir();
            } else if (command.startsWith("rmdir")) {
                newobject.rmdir(command);
            }else if (command.startsWith("cat")) {
               newobject.cat(command);
            } else if (command.startsWith("more")) {
             // String command2 = null;
              newobject.more(command);
            } else if (command.equals("pwd")) {
              
                newobject.pwd();
            } else if (command.equals("rv")) {
              
                newobject.rv(command);
            } else if (command.startsWith("date")) {
                
              newobject.date();
            } else if (command.equals("clear")) {
               newobject.clear();
            }
            
            else {
                System.out.println("Invalid command: " + command);
            }
        }
        
        }}}

   
    

