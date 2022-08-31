package IDATT2101.Task1;

import java.util.Date;
import java.util.Random;

/**
 * Exercise 1 program
 * @version 1.0
 * @author johnfb, teodorbi
 * @since 31.08.22
 */
public class Exercise1 {
	public static int[] priceChangeTestSample = {-1, 3, -9, 2, 2, -1, 2, -1, -5};

	/**
	 * Generates a random list of numbers
	 * @param n Number of price-changes to generate
	 * @return list of price-changes
	 */
	public static int[] generatePriceChanges(int n) {
		int[] list = new int[n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			list[i] = (r.nextInt((19 - 1) + 1) - 9);
		}
		return list;
	}

	/**
	 * Method for testing the algorithm speed
	 * @param priceChanges List of price-changes
	 */
	public static void testTime(int[] priceChanges) {
		int rounds = 0;
		Date end;
		Date start = new Date();

		do {
			int[] ints = analyzePriceChanges(priceChanges);
			end = new Date();
			rounds++;
		} while (end.getTime()-start.getTime() < 1000);

		double time = (double) (end.getTime() - start.getTime()) / rounds;
		System.out.println("n:" + priceChanges.length + ", milliseconds per round: " + time + "ms");
	}

	/**
	 * Method for analyzing stock-price changes
	 * @param priceChanges List of price changes
	 * @return array containing the best purchase-, sell-day and total earnings
	 */
	public static int[] analyzePriceChanges(int[] priceChanges) { 	// 0 deklarasjon
		int[] purchase = {0, 10}; 	//[DAY, PRICE]				 	// 1 tilordning
		int[] sell = {0, 0}; 		//[DAY, PRICE]				 	// 1 tilordning
		int currentPrice = 0;									 	// 1 tilordning
		int futurePrice = 0; 									 	// 1 tilordning
		int earnings = 0;										 	// 1 tilordning

		for (int i = 0; i < priceChanges.length; i++) {				// 1 + 2n, 1 x tilordning, n x sammenlikning, n x addisjon
			currentPrice += priceChanges[i];						// 2n, n addisjoner, n tabelloppslag
			futurePrice = currentPrice;								// n, n verdiendring
			for (int j = i+1; j < priceChanges.length; j++) {		// n + 2n^2, n x tilordning, n^2 sammenliking, n^2 addisjon
				futurePrice += priceChanges[j];						// 2n^2, n^2 addisjoner, n^2 tabelloppslag
				if (futurePrice - currentPrice > earnings) {		// n^2, n^2 sammenlikning
					earnings = futurePrice - currentPrice;			// n^2 mulig tilordning
					purchase = new int[]{i, currentPrice};			// n^2 mulig tilordning
					sell = new int[]{j, futurePrice};				// n^2 mulig tilordning
				}
			}
		}

		return new int[]{purchase[0], sell[0], earnings}; 			// 4, 1 x return, 2 x tabelloppslag, 1 x deklarasjon
	}

	public static void main(String[] args) {
		System.out.println("---------- STARTING APPLICATION ----------");

		int[] list = generatePriceChanges(1000);
		testTime(list);
		System.out.println();
		int[] list2 = generatePriceChanges(10000);
		testTime(list2);
		System.out.println();
		int[] list3 = generatePriceChanges(100000);
		testTime(list3);
		System.out.println();
		int[] list4 = generatePriceChanges(1000000);
		testTime(list4);
		System.out.println();
	}
}