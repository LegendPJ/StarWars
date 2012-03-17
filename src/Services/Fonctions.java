package Services;

import java.util.Arrays;

public class Fonctions {
	
	public static boolean in_array(char needle, char[] haystack) {
		return Arrays.binarySearch(haystack, needle) >= 0;
	}
	
}
