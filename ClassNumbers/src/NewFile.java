import java.io.*;
public class NewFile {
	public static void newFile(int sum) throws Exception{
        FileWriter fw = new FileWriter("Output.txt");
        BufferedWriter bufw = new BufferedWriter(fw);
        String sumc = "The total number of students in the college is " + sum;
        bufw.write(sumc);      
        bufw.flush();   
        bufw.close();	
	}
}
