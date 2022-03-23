package com;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javafx.application.Platform;
public class Connection extends Thread {
	public interface ActionListener {
		void beginGame(char l);
		void endGame(String msg);
	}
	private Socket socket;
	private BufferedWriter bw;
	private ActionListener listener;
	@Override
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 8080);
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			final String line = br.readLine();
			if (line != null) {
				Platform.runLater(() -> {
					listener.beginGame(line.charAt(0));
				});
			}
			String msg = br.readLine();
			listener.endGame(msg);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String json) {
		try {
			bw.write(json);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setListener(ActionListener l) {
		listener = l;
	}
}