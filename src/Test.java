/**
 * Project Name: SMLabTesting
 * File Name: Test.java
 * Package Name: 
 * Date: May 1, 2020 5:36:29 PM
 * Copyright (c) 2020, yanggao2019@hotmail.com All Rights Reserved.
 */

/**
 * Describe:
 * 
 * @since JDK 1.8
 * @author Yang Gao 300128708
 * @Email ygao151@uottawa.ca
 * @Date: May 1, 2020 5:36:29 PM
 */
public class Test {

	/**
	 * @param args
	 * @author Yang Gao 300128708
	 * @Date: May 1, 2020 5:36:29 PM
	 */
	public static void main(String[] args) {
		String blank1 = String.format("%1s", "x");
		String blank = String.format("%" + 3 + "s", "x");
		System.out.println(blank1);
		System.out.println(blank);
		// countOfOccupying=2954, numMoves=3600, rate=0.000000
		double countOfOccupying = 2954;
		double numMoves = 3600;
		double rate = (countOfOccupying / numMoves);
		System.out.printf("%.2f%%", rate * 100);
	}

}
