package Backend.FileHandling;

import java.io.*;

public class FileManager {

    public void writeFile(String path,String data){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            bw.write(data);

        }catch (IOException e){
            System.out.println("ERROR!,File not created :"+e.getMessage());
        }
    }
    public void readFile(String path){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line =br.readLine())!= null){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("File not found :" + e.getMessage());
        }
    }
    public void appendFile(String path,String data){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path,true));
            bw.append(data);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Data is not saved :"+e.getMessage());
        }
    }
    public Boolean searchTermInFile(String path,String term){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line ;
            while((line =br.readLine())!=null){
                if(line.contains(term)){
                 return true;
                }

            }
        } catch (IOException e) {
            System.out.println("File not found to search data"+e.getMessage());
        }
        return false;
    }
}
