package airport;

import java.util.Scanner;

public class Search {
	public static void main(String[] args) {
		int number = Integer.parseInt(args[0]);
		System.out.println(number);
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("¬ведите строку: ");
		String str = scanner.next();
	}
}
