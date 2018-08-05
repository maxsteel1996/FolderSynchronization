

import java.io.File;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

 class Server {
	private int PORT_NUMBER;

	private Socket sock;
	private ObjectInputStream ois;
	private ServerSocket servsock;
	private String serverSideDirectory;

	public Server(int port, String path) {
		PORT_NUMBER = port;
		serverSideDirectory = path;
	}

	public void runServer() {

		try {
			servsock = new ServerSocket(PORT_NUMBER);
			while (true) {
				sock = servsock.accept();
				ois = new ObjectInputStream(sock.getInputStream());
				DAO dao = (DAO) ois.readObject();
				System.out.println("request received for " + dao.fileName + " to " + dao.operation);
				File f = new File(serverSideDirectory + dao.fileName);
				if (dao.operation.equals("remove"))
					f.delete();
				else
					Files.write(f.toPath(), dao.content);
				reinitConn();
			}
		} catch (Exception e) {

		}
	}

	private void reinitConn() throws Exception {
		ois.close();
		sock.close();
		sock = servsock.accept();
		ois = new ObjectInputStream(sock.getInputStream());
	}
}
