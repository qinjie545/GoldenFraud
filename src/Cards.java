import java.util.ArrayList;
import java.util.Collections;


public class Cards {
	private static final String[] FLOWER_TYPES = {"梅花","方片","红桃","黑桃"};
	private static String[] NUMBER_TYPES =new String[13];
	private static ArrayList<Card> cards = new ArrayList<Card>(52);
	static{
		for (int i = 0; i < NUMBER_TYPES.length; i++) {
			int cardNumber = i + 1;
			if (cardNumber <= 10) {
				if (cardNumber == 1) {
					NUMBER_TYPES[i] = "A";
				}else {
					NUMBER_TYPES[i] = cardNumber +"";
				}
			}else if (cardNumber == 11) {
				NUMBER_TYPES[i] = "J";
			}else if (cardNumber == 12) {
				NUMBER_TYPES[i] = "Q";
			}else if (cardNumber == 13) {
				NUMBER_TYPES[i] = "K";
			}
		}
	}
	
	public Cards(){
		shuffle();
	}
	
	public void shuffle(){
		resetCards();
		Collections.shuffle(cards);
	}
	
	public synchronized Card getCard(){
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
	
	public void resetCards(){
		for (int i = 0; i < FLOWER_TYPES.length; i++) {
			for (int j = 0; j < NUMBER_TYPES.length; j++) {
				Card c = new Card(FLOWER_TYPES[i],NUMBER_TYPES[j]);
				cards.add(c);
			}
		}
	}
	
	public void printCards(){
		for (int i = 0; i < cards.size(); i++) {
			if (i %10 == 0 && i != 0) {
				System.out.print("\n");
			}
			System.out.print("\t");
			System.out.print(cards.get(i).toString());
			System.out.print("\t");
		}
		
	}
	
	
	public static int getCardFlowerType(String flower ){
		if (flower == null || flower.length() == 0) {
			return 0;
		}
		for (int i = 0; i < FLOWER_TYPES.length; i++) {
			if (flower.equals(FLOWER_TYPES[i])) {
				return i+1;
			}
		}
		return 0;
	}
	
}
