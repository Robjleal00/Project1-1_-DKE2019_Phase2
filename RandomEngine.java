import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomEngine {
	public static SecureRandom r;
	//GENERAL COMMENT 
	//WE ARE USING OUR OWN RANDOM NUMBERS GENERATOR BECAUSE 
	//OF THE ISSUE OF TOO SHORT SEQUENCES OF 
	//NUMBERS
	//ISSUE IN THE GENETIC ALGORITHM WHEN RANDOMNESS ISN'T RANDOM ENOUGH...
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