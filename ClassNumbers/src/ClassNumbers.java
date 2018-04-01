import java.io.*;
import java.util.*;
public class ClassNumbers
	{
	    public static void main(String[] args) throws Exception
	    {
	        Map<String, String> map = new HashMap<String, String>();
	        BufferedReader br =
	            new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:/courses/CSYE6200/Homework3/Input.txt"))));
	        String str;
	        while ((str = br.readLine()) != null)
	        {
	            String[] strs = str.split(",");
	            try
	            {
	                map.put(strs[0], strs[1]);
	            }
	            catch (NumberFormatException e)
	            {
	                e.printStackTrace();
	            }
	        }
	        br.close();
	        List<String> strr = new ArrayList<String>();
	        strr.add("c1");
	        strr.add("c2");
	        strr.add("c3");
	        strr.add("c4");
	        strr.add("c5");
	        strr.add("c6");
	        strr.add("c7");
	        Scanner imput = new Scanner(System.in);
			System.out.println("Please import 'a' to execute for loop or 'b' to excute iterator loop:");
			String num = imput.next();
			int sum = 0;
			switch(num) {
				case("a"):
			        for(String i:strr) {
			        	sum = sum + Integer.parseInt(map.get(i));
			        }
		        	System.out.println("File using for loop has been created successfully!");
		        	NewFile.newFile(sum);
			        break;
				case("b"):
			        Iterator<String> iit = strr.iterator();
			        while (iit.hasNext()) 
			        	sum = sum + Integer.parseInt(map.get(iit.next()));
				    System.out.println("File using iterator loop has been created successfully!");
		        	NewFile.newFile(sum);
			        break;
			    default:
			    	System.out.println("Please run again and import 'a' or 'b'!");
			        }
			imput.close();
	    }
	}