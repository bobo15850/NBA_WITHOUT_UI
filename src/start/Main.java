package start;

public class Main {
	public static void main(String args[]) {
		Terminal console = new Terminal();
		console.excute(System.out, new String[] { "-player" });
		new Refresh().start();
	}
}
