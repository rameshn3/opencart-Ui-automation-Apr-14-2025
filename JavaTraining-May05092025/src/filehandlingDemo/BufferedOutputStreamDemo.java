package filehandlingDemo;

import java.io.*;

public class FileOutputStreamDemo{
private static File f=null;
private static FileInputStream fis=null;
private static FileOutputStream fos = null;
private static String finputPath = "C:\\javaprograms\\java\\input.txt";
    private static String foutputPath = "C:\\javaprograms\\java\\output.txt";

    public static void main(String[] args) throws IOException{

        try{
            f = new File(foutputPath);
            if(!f.exists()){
                f.createNewFile();
            }
             fis = new FileInputStream(new File(finputPath));
            fos = new FileOutputStream(f.getAbsolutePath());
             int data = 0;
             while((data=fis.read())!=-1){
                char c =(char)data;
                 System.out.print(c);
                 fos.write(data);
             }

        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }catch(IOException e){
            throw new RuntimeException(e);
        }finally{
            fis.close();
            fos.close();
        }

    }

}
