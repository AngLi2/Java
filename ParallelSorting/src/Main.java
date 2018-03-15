import java.util.Random;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0)
			ParSort.cutoff = Integer.parseInt(args[0]);
		Random random = new Random(0L);
		int[] array = new int[2000];
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(10000);
		ParSort.sort(array, 0, array.length);
		if (array[0] == 11)
			System.out.println("Success!");

		// Test the run time
		double startTime;
		double endTime;
		final int TRY_TIME = 10;
		final int LENGTH_INCREMENT = 200000;
		final int MAX_LENGTH = 1000000;

		// Warm up
		System.out.println("Start Warm Up");
		for (int length = LENGTH_INCREMENT; length <= MAX_LENGTH; length += LENGTH_INCREMENT) {
			final int CUTOFF_INIT = length * 2;
			ParSort.cutoff = CUTOFF_INIT;
			while (ParSort.cutoff >= length / 4) {
				for (int num = 0; num < TRY_TIME; num++) {
					array = new int[length];
					for (int i = 0; i < array.length; i++)
						array[i] = random.nextInt(MAX_LENGTH);
					ParSort.sort(array, 0, array.length);
				}
				ParSort.cutoff /= 2;
			}
		}
		System.out.println("End Warm Up");

		for (int length = LENGTH_INCREMENT; length <= MAX_LENGTH; length += LENGTH_INCREMENT) {
			System.out.println("---------- length: " + length + " ----------");
			final int CUTOFF_INIT = length * 2;
			ParSort.cutoff = CUTOFF_INIT;
			while (ParSort.cutoff >= length / 16) {
				double runTime = 0;
				// double systemRunTime = 0;
				for (int num = 0; num < TRY_TIME; num++) {
					array = new int[length];
					for (int i = 0; i < array.length; i++)
						array[i] = random.nextInt(MAX_LENGTH);
					startTime = System.currentTimeMillis();
					ParSort.sort(array, 0, array.length);
					endTime = System.currentTimeMillis();
					runTime += endTime - startTime;
					System.out.println("total thread is: "+ ParSort.threadNum);
					ParSort.threadNum = 0; // initialize the counter of threads

					// // Test System Parallel Sorting
					// array = new int[length];
					// for (int i = 0; i < array.length; i++)
					// array[i] = random.nextInt(MAX_LENGTH);
					// startTime = System.currentTimeMillis();
					// Arrays.parallelSort(array);
					// endTime = System.currentTimeMillis();
					// systemRunTime += endTime - startTime;

				}
				double averageRunTime = runTime / 10;
				// double systemAverageRunTime = systemRunTime / 10;
				System.out.println("Average Run Time of cutoff " + ParSort.cutoff + " is: " + averageRunTime);
				// System.out.println("Average Run Time of cutoff " + ParSort.cutoff + " is: " +
				// systemAverageRunTime);
				ParSort.cutoff /= 2;
			}
		}
	}
}
