package filehandlingDemo;

import java.io.*;

public class FileInputStreamDemo{
private static File f=null;
private static FileInputStream fis=null;

private static String finputPath = "C:\\javaprograms\\java\\input.txt";


    public static void main(String[] args) throws IOException{

        try{
            f = new File(finputPath);
             fis = new FileInputStream(f);
             int data = 0;
             while((data=fis.read())!=-1){
                char c =(char)data;
                 System.out.print(c);
             }

        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }catch(IOException e){
            throw new RuntimeException(e);
        }finally{
            fis.close();
        }

    }

}
