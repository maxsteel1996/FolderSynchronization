

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

//C:/Users/HP/Desktop/test1/
 class Client {
	private String serverIP;
	private String clientSideDirectory = "";
	private int PORT_NUMBER;
	private Socket sock;
	private ObjectOutputStream oos;

	public Client(String serverIp, int port, String path) {
		this.serverIP = serverIp;
		clientSideDirectory = path;
		PORT_NUMBER = port;

	}

	public void runClient() {
		try {
			try {
				Path faxFolder = Paths.get(clientSideDirectory);
				WatchService watchService = FileSystems.getDefault().newWatchService();
				faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
				boolean valid = true;
				do {
					WatchKey watchKey = watchService.take();
					for (WatchEvent event : watchKey.pollEvents()) {
						WatchEvent.Kind kind = event.kind();
						String fileName = event.context().toString();
						File myFile = new File(clientSideDirectory + fileName);
						System.out.println("sending data" + myFile.getName());
						DAO dao = null;
						if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)
								|| StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)) {
							byte[] content = Files.readAllBytes(myFile.toPath());
							dao = new DAO(content, "persist", myFile.getName());

						} else if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)) {
							dao = new DAO(null, "remove", myFile.getName());
						}
						sock = new Socket(serverIP, PORT_NUMBER);
						oos = new ObjectOutputStream(sock.getOutputStream());
						oos.writeObject(dao);
						reinitConn();

					}
					valid = watchKey.reset();

				} while (valid);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reinitConn() throws Exception {

		oos.close();
		sock.close();
		sock = new Socket(serverIP, PORT_NUMBER);
		oos = new ObjectOutputStream(sock.getOutputStream());
	}
}
