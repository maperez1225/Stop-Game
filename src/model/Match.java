package model;
import java.util.Random;
import com.Session;
import com.Session.MessageListener;
public class Match implements MessageListener {
	private Session a;
	private Session b;
	private char letter;
	public Match(Session a, Session b) {
		this.a = a;
		this.b = b;
		a.setListener(this);
		b.setListener(this);
		Random r = new Random();
		letter = (char) ('A'+r.ints(0,26).findFirst().getAsInt());
		a.startGame(letter);
		b.startGame(letter);
	}
	@Override
	public synchronized void onMessage(String msg, Session s) {
		if (s.equals(a)) {
			b.endGame(msg);
		} else {
			a.endGame(msg);
		}
	}
}