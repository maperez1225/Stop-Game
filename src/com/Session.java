package com;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import ui.Server;
public class Session extends Thread {
	public interface MessageListener {
		void onMessage(String msg, Session s);
	}
	private MessageListener listener;
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;
	public Session(Socket s) {
		socket = s;
	}
	@Override
	public void run() {
		try {
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = br.readLine();
			listener.onMessage(line, this);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void startGame(char letter) {
		new Thread(() -> {
			try {
				bw.write(letter+"\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	public void endGame(String msg) {
		new Thread(() -> {
			try {
				bw.write(msg+"\n");
				bw.flush();
				Server.getInstance().onDisconnect(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	public Socket getSocket() {
		return socket;
	}
	public void setListener(MessageListener l) {
		listener = l;
	}
}