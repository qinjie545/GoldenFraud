import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;


public class Main {
	
	public final static int PEOPLE_NUMBER = 8;
	
	public final static int ROUNDS  = 1000;
	
	public static void main(String[] args){
		Cards cards = new Cards();

		ArrayList<HandCards> handCards = new ArrayList<>();
		HashMap<HandCards, Integer> resultSet = new HashMap<>();
		HashMap<Integer, Integer> typeSet = new HashMap<>();
		HashMap<String, Integer> winSet = new HashMap<>();
		
		for (int i = 0; i < PEOPLE_NUMBER; i++) {
			HandCards hCards = new HandCards();
			hCards.setOwnerName(i+"");
			handCards .add(hCards);
		}
		
		for (int i = 0; i < ROUNDS; i++) {
			cards.shuffle();
			
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < handCards.size(); k++) {
					handCards.get(k).addCard(cards.getCard());
				}
			}
			//card dispatch ok;
			Collections.sort(handCards);
			
			for (int l = 0; l < handCards.size(); l++) {
				handCards.get(l).getCards().get(0).hashCode();
				System.out.print( "owner:"+handCards.get(l).getOwner() + ":" + handCards.get(l).toString() + "\t"); 
				if (l == handCards.size() -1) {
					System.out.print( "\t"+" Win: "+handCards.get(l).toString() );
					HandCards h;
					try {
						h = (HandCards) ( handCards.get(l).clone() );
						if (resultSet.containsKey(h)) {
							Integer count  = resultSet.get(h) ;
							count += 1 ;
							resultSet.put(h, count);
						}else {
							resultSet.put(h, 1);
						}
						if (typeSet.containsKey(h.getType())) {
							typeSet.put(h.getType(), typeSet.get(h.getType()) + 1);
						}else {
							typeSet.put(h.getType(), 1);
						}
						if (winSet.containsKey(h.getOwner())) {
							winSet.put(h.getOwner(), winSet.get(h.getOwner()) + 1);
						}else {
							winSet.put(h.getOwner(), 1);
						}
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
				handCards.get(l).clear();
			}
			System.out.println();
		}
		
		printResultSet(resultSet);
		printTypeSet(typeSet);
		
	}

	private static void printTypeSet(HashMap<Integer, Integer> typeSet) {
		System.out.println("=================TYPE-SET===================");
		
		for(Entry<Integer, Integer> e : typeSet.entrySet()){
			int number = (int) (( e.getValue() * 1.0 / ROUNDS ) * 10000);
			System.out.println(  HandCards.getTypeName(e.getKey())  + "---- " + e.getValue()  + ",Ratio:"+ number/100 + "."+ number% 100 +"%"  );
		}
		
		System.out.println("=================END===================");
	}

	private static void printResultSet(HashMap<HandCards, Integer> resultSet) {
		System.out.println("=================RESULT===================");
		
		for(Entry<HandCards, Integer> e : resultSet.entrySet()){
			System.out.println(e.getKey().toString()  + "---- " + e.getValue() );
		}
		
		System.out.println("=================END===================");
	}
}
