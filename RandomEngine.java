import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomEngine {
	public static SecureRandom r;

	public static void init() {
		try {
			r = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static int getInt(int mn, int mx) {
		return mn + r.nextInt(mx - mn);
	}

	public static double getDouble() {
		return r.nextDouble();
	}
}