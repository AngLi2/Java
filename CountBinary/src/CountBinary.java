import java.util.Scanner;

public class CountBinary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner imput = new Scanner(System.in);
		System.out.println("Please import a number:");
		int num = imput.nextInt();
		int sum = 0;
		String binum = Integer.toBinaryString(num);
		char[] c = binum.toCharArray();
		int[] ci = new int[binum.length()];
		for(int i = 0; i < binum.length(); i++) {
			ci[i] = c[i]- '0';
		}
		for(int i = 0; i < binum.length(); i++) {
			if(ci[i] == 1) {
				sum += 1;
			}
		}
		System.out.println("The number of 1 bits in the binary representation of the given number is:");
		System.out.println(sum);
	}
}
