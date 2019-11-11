import java.util.Random;

public class RandomEngine {
	static public int getInt(int mn, int mx) {
		Random r = new Random();
		return mn + r.nextInt(mx - mn);
	}
}