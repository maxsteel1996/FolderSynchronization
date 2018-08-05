

import java.util.Scanner;

public class SynchronizeFolder {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("input c for client and inout s for server");
		String input = sc.next();
		if (input.equals("s")) {
			int port;
			System.out.println("input port to start server : ");
			port = sc.nextInt();
			System.out.println("input location to synchronize : ");
			String path = sc.next();
			Server server = new Server(port, path);
			System.out.println("starting server....");
			server.runServer();
		} else if (input.equals("c")) {
			System.out.println("input server ip : ");
			String ip = sc.next();
			System.out.println("input port to start server : ");
			int port = sc.nextInt();
			System.out.println("input location to synchronize : ");
			String path = sc.next();
			Client client = new Client(ip, port, path);
			System.out.println("starting client....");
			client.runClient();
		} else {
			System.out.println("wrong input");
		}
	}
}
