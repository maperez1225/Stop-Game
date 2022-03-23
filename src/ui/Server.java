package ui;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.Session;
import model.Match;
public class Server {
	private static Server instance;
	private ArrayList<Session> sessions;
	private Server() {
		
	}
	public static void main(String[] args) {
		try {
			instance = new Server();
			instance.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	private void initialize() throws IOException {
		sessions = new ArrayList<>();
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket socket = server.accept();
			Session session = new Session(socket);
			session.start();
			sessions.add(session);
			int connections = sessions.size();
			if (connections % 2 == 0) {
				new Match(sessions.get(connections-2), session);
			}
		}
	}
	public void onDisconnect(Session s) {
		sessions.remove(s);
	}
	public static synchronized Server getInstance() {
		return instance;
	}
}