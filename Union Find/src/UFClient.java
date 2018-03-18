import java.util.Scanner;

public class UFClient {

	public static int count(int num) {
		WQUPC uf = new WQUPC(num);
		int counter = 0;
		while (uf.count() > 1) {
			int randomA = (int) (Math.random() * num);
			int randomB = (int) (Math.random() * num);
			if (!uf.connected(randomA, randomB))
				uf.union(randomA, randomB);
			if (randomA != randomB)
				counter++;
		}
		return counter;
	}

	private static boolean validate(String str) {
		try {
			if (Integer.parseInt(str) <= 0)
				throw new IllegalArgumentException();
			return true;
		} catch (Exception e) {
			System.out.println("Wrong Input! Only Positive Integer Permitted!");
			return false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String scStr;
		while (true) {
			System.out.println("Please input the n: ");
			scStr = sc.next();
			if (validate(scStr))
				System.out.println("The count is: \n" + count(Integer.parseInt(scStr)));
		}
	}
}
