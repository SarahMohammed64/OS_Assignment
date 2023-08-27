/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parser;
import java.io.BufferedReader;

import java.io.FileReader;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.io.*;
import static java.lang.Compiler.command;
import java.util.*;
import java.nio.file.InvalidPathException;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 * @author Hala
 */
public class terminal {
    String  directory="";
    String  finaldirectory="";
    public void help(){
        System.out.println("clear : terminal screen and it can be redirected to clear" +
"the screen of some other terminal.\ncd : This command changes the current directory to" +
"another one.\nIS : These programs list each given file or directory" +
"name. Directory contents are sorted" +
"alphabetically. For ls, files are by default listed in" +
"columns, sorted vertically, if the standard output is" +
"a terminal; otherwise, they are listed one per line.\ncp : If the last argument names an existing directory," +
"cp copies each other given file into a file with the" +
"same name in that directory. Otherwise, if only" +
"two files are given, it copies the first onto the" +
"second. It is an error if the last argument is not a" +
"directory and more than two files are given. By" +
"default, it does not copy directories.\nmv : If the last argument names an existing directory," +
"mv moves each other given file into a file with the" +
"same name in that directory. Otherwise, if only" +
"two files are given, it moves the first onto the" +
"second. It is an error if the last argument is not adirectory and more than two files are given. It can" +
"move only regular files across file systems. If a" +
"destination file is unwritable, the standard input is" +
"a tty, and the –f or --force option is not given, mv" +
"prompts the user for whether to overwrite the file." +
"If the response does not begin with y or Y, the file" +
"is skipped.\nrm : rm removes each specified file. By default, it does" +
"not remove directories. If a file is unwritable, the" +
"standard input is a tty, and the -f or --force option" +
"is not given, rm prompts the user for whether to" +
"remove the file. If the response does not begin" +
"with y or Y, the file is skipped." +
"rm can be used to remove directories and its" +
"subdirectories and files recursively suing option -r\nmkdir : mkdir creates a directory with each given name. By" +
"default, the mode of created directories is 0777" +
"minus the bits set in the umask.\nrmdir : rmdir removes each given empty directory. If any" +
"nonoption argument does not refer to an existing" +
"empty directory, it is an error.\ncat : Concatenate files and print on the standard" +
"output.\nmore : Let us display and scroll down the output in one" +
"direction only. You can scroll page by page or line" +
"by line.\npwd : Display current user directory.\ndate : To display or to set the date and time of the" +
"system. The format for setting date is" +
"[MMDDhhmm[[CC]YY][.ss]]");//clear, cd, ls, cp, mv, rm, mkdir,rmdir, cat, more, pwd, date.
    } 
   public void cd(String directory) {
       this.directory=directory;

        try {
            System.setProperty("user.dir", directory);
            System.out.println("Directory changed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to change directory.");
        }
    }
    public void cp(String sourceFilePath , String destinationDirectoryPath){
        
        File sourceFile = new File(sourceFilePath);
        File destinationDirectory = new File(destinationDirectoryPath);

        if (sourceFile.exists() && sourceFile.isFile() && destinationDirectory.exists() && destinationDirectory.isDirectory()) {
            try {
                Path sourcePath = sourceFile.toPath();
                Path destinationPath = new File(destinationDirectory, sourceFile.getName()).toPath();
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to copy the file.");
            }
        } else {
            System.out.println("Invalid source file or destination directory.");
        }
    
    }
    public  void pwd( ) {
        String pwd = System.getProperty("user.dir");
        System.out.println(pwd);
    }
    public static void testDirectory(String directory){
         try {
            // Syntax validation
            Path path = Paths.get(directory);
            if (!path.isAbsolute()) {
                path = path.toAbsolutePath();
            }

            // Directory existence
            if (Files.exists(path) && Files.isDirectory(path)) {
                System.out.println("The directory '" + path + "' exists.");
            } else {
                System.out.println("The directory '" + path + "' does not exist.");
                 System.exit(0);
            }
        } catch (InvalidPathException e) {
            System.err.println("Invalid directory path: " + e.getMessage());
             System.exit(0);
        }
    }
    
    public  void mkdir(){
        Scanner newfilepath = new Scanner(System.in); //1
        String NEW_File_path = newfilepath.nextLine();
        testDirectory(NEW_File_path);
        this.finaldirectory=NEW_File_path;
//2  ----------1&2 helping me to take input
        File f = new File(NEW_File_path);
        if (f.mkdir()) {
            System.out.println("Directory has been created successfully");
        }
        else {
            System.out.println("mkdir : missing operand ");
        }
    }


    public static void rmdir(String args)
    {
        File file = new File(args.substring(6));
        if(file.isDirectory())
        {
    		if(file.list().length == 0)
                {
    		   file.delete();
    		   System.out.println("Directory is deleted.");
                }
                else if(file.list().length != 0)
                    System.out.println("Directory is full.");
        }
    }


    public static void ls(String directoryPath) { //listDirectoryContents
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Directory not found or empty.");
        }
    }

    public static void cat(String filePath1, String filePath2) { //concatinate files
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

//            System.out.println("Contents of " + filePath1 + ":");
            String line1;
            while ((line1 = reader1.readLine()) != null) {
                System.out.println(line1);
            }

            String line2;
            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public static void cat(String args)
    {
            try 
            {
                java.io.FileReader fileReader = new java.io.FileReader(args.substring(4));
                java.io.BufferedReader in = new java.io.BufferedReader(fileReader);
                String line;
                while((line = in.readLine())!= null)
                {
                    System.out.println(line);
                }
            } 
            catch (java.io.FileNotFoundException ex) 
            {
                System.out.println(args.substring(4) + ", file not found.");
            }
            catch (java.io.IOException ex) 
            {
                System.out.println(args.substring(4) + ", input/output error.");
            }
    }
    public static void clear() {

           for(int i = 0 ; i < 50 ;i++)
           {
               String newline = System.lineSeparator();
               System.out.println(newline);
           }


    }
    public static void date() {
        java.util.Date date = new java.util.Date();
        System.out.println(date.toString());
    }
    
    public static void more (String arg)  
      {
        //File file1 = new File (arg) ;
        try
        {
            // in = new FileInputStream(arg);
            InputStream in = null;
            in = new FileInputStream(arg.substring(5));
            String Output;
            byte [] Info = new byte [48];
            while ((in.read(Info)) != -1)
            {
                Output = new String (Info);
                System.out.print(Output);
                String NextLine;
                Scanner sc = new Scanner (System.in);
                NextLine = sc.nextLine();
            }
            in.close();
        }
        catch (IOException e) 
        {
            System.out.print("Invalid Input!");
        }
       }
    public static void mv(String command) throws IOException
    {
        String[] parts = command.split(" ");
        if (parts.length != 3)
        {
            System.out.println("Usage: mv <oldname> <newname>");
            return;
        }

        File oldFile = new File(parts[1]);
        if (!oldFile.exists())
        {
            System.out.println("File not found: " + oldFile.getAbsolutePath());
            return;
        }

        File newFile = new File(parts[2]);
        if (newFile.exists())
        {
            System.out.println("File already exists: " + newFile.getAbsolutePath());
            return;
        }

        oldFile.renameTo(newFile);
    }

    public static void rv(String command) throws IOException
    {
        String[] parts = command.split(" ");
        if (parts.length != 2)
        {
            System.out.println("Usage: rv <filename>");
            return;
        }

        File file = new File(parts[1]);
        if (!file.exists())
        {
            System.out.println("File not found: " + file.getAbsolutePath());
            return;
        }

        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null)
        {
            //System.out.println(line.reverse());
        }
    }
    public static void deleteDirectory(File path) 
    {
            for(File f : path.listFiles())
            {
                if(f.isDirectory()) 
                {
                    deleteDirectory(f);
                    f.delete();
                }
                else
                {
                    f.delete();
                }
            }
            path.delete();
    }
     public static void rm(String args)
    {
        if(args.substring(3).contains(" -r "))
        {
            File file = new File(args.substring(6));
            if(file.isDirectory())
            {
                deleteDirectory(file);
                System.out.println("Directory is deleted. ");
            }
            else
            {
                System.out.println("Not a directory. ");
            }
        }
        else
        {
            File file = new File(args.substring(3));
            file.delete();
            System.out.println("File is deleted. ");
        }
        
    }
}

