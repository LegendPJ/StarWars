package Services;

public class Messages {
	
	private static String message = new String();

	public static void print() {
		System.out.print(message);
		Messages.clear();
	}
	
	public static void println() {
		Messages.print();
		System.out.println();
	}

	public static void setMessage(String message) {
		Messages.message = message;
	}
	
	public static void append(String message) {
		Messages.message += message;
	}
	
	public static void appendln(String message) {
		Messages.message += "\n"+message;
	}
	
	public static void clear() {
		Messages.message = new String();
	}
	
	public static boolean isEmpty() {
		return Messages.message.isEmpty();
	}
	
}
