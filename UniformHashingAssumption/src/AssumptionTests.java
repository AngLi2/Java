import java.util.Random;

/**
 * @author Ang Li
 *
 */
public class AssumptionTests {
	private final static Random RAMDOM = new Random();
	private final static int TIME = 2;
	private final static int TRY_NUM = 100;
	private final static int M_START = 10;
	private final static int M_END = 100000;

	public static void main(String[] args) {
		System.out.println("---------------------- START TEST ----------------------");
		for (int m = M_START; m <= M_END; m *= TIME) {

			int birthdayProblemSum = 0;
			int couponCollectorSum = 0;

			System.out.println("---------------------- m = " + m + " ----------------------");
			for (int i = 0; i < TRY_NUM; i++) {
				HashDemo hashDemo = new HashDemo(m);
				boolean isBP = false, isCC = false;

				while (isBP == false || isCC == false) {
					int currentKey = RAMDOM.nextInt();
					hashDemo.insert(currentKey);

					// check if coupon collector occurs
					if (!isBP)
						if (hashDemo.isBP()) {
							birthdayProblemSum += hashDemo.getHashCounter();
							isBP = true;
						}

					// check if birthday problem occurs
					if (!isCC)
						if (hashDemo.isCC()) {
							couponCollectorSum += hashDemo.getHashCounter();
							isCC = true;
						}
				}
			}
			printInfo(m, birthdayProblemSum, couponCollectorSum);
		}
	}

	private static void printInfo(int m, int birthdayProblemSum, int couponCollectorSum) {
		System.out.println("Avarage Hash Numbers for Birthday Problem is: " + birthdayProblemSum / TRY_NUM);
		System.out.println("Expected: " + (int) Math.sqrt(Math.PI * m / 2));
		System.out.println("Avarage Hash Numbers for Coupon Collector is: " + couponCollectorSum / TRY_NUM);
		System.out.println("Expected: " + (int) (m * Math.log(m)));
	}
}
