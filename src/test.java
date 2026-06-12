import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test {
    static void main() {
        try{
             BufferedReader br = new BufferedReader(new FileReader("D:\\CODING\\Java\\Projects\\Inventory Management System Mine\\Products.txt"));
             String line ;
             while((line = br.readLine())!=null){
                 if(line.contains("P1")){
                     System.out.println(line);
                 }

             }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("hello");
    }
}
