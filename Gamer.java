public class Gamer implements Comparable{
	private int score;
	private String name;
	
	public Gamer(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Gamer gamer = (Gamer)o;
		if(((Gamer) o).getScore() >this.score) {
			return 1;
		}
		return 0;
	}
}