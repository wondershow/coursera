package class1.hw;

public class Outcast {
	private WordNet wn;
	
	public Outcast(WordNet wordnet) {        // constructor takes a WordNet object
		 wn = wordnet;
	}
	   
	public String outcast(String[] nouns){   // given an array of WordNet nouns, return an outcast
		int max = Integer.MIN_VALUE;
		String maxNoun = "";
		
		for (int i = 0; i < nouns.length; i++) {
			int distance = 0;
			for (int j = 0; j < nouns.length; j++) {
				distance += wn.distance(nouns[i], nouns[j]);
			}
			if (distance > max) {
				maxNoun = nouns[i];
				max = distance;
			}
		}
		
		return maxNoun;
	}
	
	public static void main(String[] args) { // see test client below
		for (float i = 0; i <= 67; i += 0.5)
			System.out.println( i + " , " + Math.round(i * 40 / 67) / 2.0);
	}
}
