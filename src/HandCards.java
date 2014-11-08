import java.util.ArrayList;
import java.util.Collections;


public class HandCards implements Comparable<HandCards> {
	
	public static final int TYPE_BAOZI = 0;
	public static final int TYPE_SHUNJIN = 1;
	public static final int TYPE_JINHUA = 2;
	public static final int TYPE_SHUNZI = 3;
	public static final int TYPE_DUIZI = 4;
	public static final int TYPE_DANPAI = 5;
	public static final int TYPE_UNKNOWN = 6;

	private int type = TYPE_UNKNOWN;
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	private String desc = "N/A";
	
	private String ownerName = "";
	
	public boolean addCard(Card c){
		if (cards.size() > 3) {
			return false;
		}else
			cards.add(c);
		if (cards.size() == 3) {
			freshCards();
		}
		return true;
	}

	public ArrayList<Card> getCards(){
		Collections.sort(cards);
		return cards;
	}
	
	private void freshCards() {
		
		Collections.sort(cards);
		
		String oneFlower,twoFlower,threeFlower,oneNumber,twoNumber,threeNumber;
		oneFlower = cards.get(0).getFlowerType();
		oneNumber = cards.get(0).getNumberType();
		
		twoFlower = cards.get(1).getFlowerType();
		twoNumber = cards.get(1).getNumberType();
		
		threeFlower = cards.get(2).getFlowerType();
		threeNumber = cards.get(2).getNumberType();
		
		if (oneNumber.equals(twoNumber) && twoNumber.equals(threeNumber)) {
			//豹子
			type = TYPE_BAOZI;
			desc = oneNumber + "豹";
		}else if( oneFlower.equals(twoFlower)  && twoFlower.equals(threeFlower)  ) {
			//金花
			if (isShunzi(cards.get(0),cards.get(1), cards.get(2))) {
				//顺金
				type = TYPE_SHUNJIN;
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("顺金-");
				sBuilder.append(getSortedString());
				desc = sBuilder.toString();
			}else {
				//普通金
				type = TYPE_JINHUA;
				StringBuilder sb = new StringBuilder();
				sb.append("金花-");
				sb.append(getSortedString());
				desc = sb.toString();
			}
		}else if(isShunzi(cards.get(0),cards.get(1), cards.get(2))) {
			//顺子
			type = TYPE_SHUNZI;
			desc = "顺子-" + getSortedString();
		}else if ( 	( cards.get(0).getNumber() == cards.get(1).getNumber() ) ||
				(cards.get(1).getNumber() == cards.get(2).getNumber() )
				) {
			//对子
			type = TYPE_DUIZI;
			StringBuilder sb = new StringBuilder();
			sb.append("对");
			if ( cards.get(0).getNumber() == cards.get(1).getNumber() ) {
				sb.append(cards.get(0).getNumberType() + "");
				sb.append(" +"+cards.get(2).getNumberType());
			}else if (cards.get(1).getNumber() == cards.get(2).getNumber()) {
				sb.append(cards.get(1).getNumberType() + "");
				sb.append(" +"+cards.get(0).getNumberType());
			}
			desc = sb.toString();
		}else {
			type = TYPE_DANPAI;
			desc = "单牌-"+getSortedString();
		}
	}
	
	public String getDesc(){
		return desc;
	}
	
	public int getType(){
		return type;
	}
	
	public void clearCards(){
		cards.clear();
		type = TYPE_UNKNOWN;
		desc = "N/A";
	}
	
	private String getSortedString() {
		Collections.sort(cards);
		StringBuilder sbBuilder = new StringBuilder();
		for (int i = 0; i < cards.size(); i++) {
			sbBuilder.append(cards.get(i).getNumberType());
			sbBuilder.append(",");
		}
		if (sbBuilder.length() > 0) {
			return sbBuilder.substring(0,sbBuilder.length()-1);
		}else {
			return "";
		}
	}

	public boolean isShunzi(Card one,Card two,Card three){
		
		int n_one = one.getNumber();
		int n_two = two.getNumber();
		int n_three = three.getNumber();
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers.add(n_one);
		numbers.add(n_two);
		numbers.add(n_three);
		
		Collections.sort(numbers);
		
		if (n_one == 1 || n_two == 1 || n_three == 1) {
			
			if (numbers.get(1) == 2 && numbers.get(2) == 3 || numbers.get(1) == 12 && numbers.get(2) == 13) {
				return true;
			}else {
				return false;
			}
		}else {
			if (numbers.get(0) + 1 == numbers.get(1) && numbers.get(1) + 1 == numbers.get(2)) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	public void clear(){
		cards.clear();
	}
	
	@Override
	public String toString() {
		return desc;
	}

	@Override
	public int compareTo(HandCards o) {
		if ( o == null) {
			return 1;
		}
		if (getType() != o.getType()) {
			//不同类比较
			return getType() < o.getType()?1:-1;
		}else {
			//同类比较
			switch (getType()) {
			case TYPE_BAOZI:
				return getCards().get(0) .compareTo( o.getCards().get(0) ) ;
			case TYPE_SHUNJIN:
				return compareShunzi(this,o);
			case TYPE_JINHUA:
				return comparetCommon(this,o);
			case TYPE_SHUNZI:
				return compareShunzi(this, o);
			case TYPE_DUIZI:
				 return compareDuizi(this,o);
			case TYPE_DANPAI:
				return comparetCommon(this, o);
			default:
				break;
			}
		}
		return 0;
	}

	private int compareDuizi(HandCards handCards, HandCards o) {
		if (o == null) {
			return 1;
		}
		int duizi1,dan1,duizi2,dan2;
		if (handCards.getCards().get(0).getNumber() == handCards.getCards().get(1).getNumber()) {
			duizi1 = handCards.getCards().get(0).getNumber();
			dan1 = handCards.getCards().get(2).getNumber();
		}else {
			duizi1 = handCards.getCards().get(1).getNumber();
			dan1 = handCards.getCards().get(0).getNumber();
		}
		if (o.getCards().get(0).getNumber() == o.getCards().get(1).getNumber()) {
			duizi2 = o.getCards().get(0).getNumber();
			dan2 = o.getCards().get(2).getNumber();
		}else {
			duizi2 = o.getCards().get(1).getNumber();
			dan2 = o.getCards().get(0).getNumber();
		}
		if (duizi1 != duizi2) {
			return Integer.compare(duizi1, duizi2);
		}else {
			return Integer.compare(dan1, dan2);
		}
	}

	private int comparetCommon(HandCards handCards, HandCards o) {
		for (int i = 2; i >= 0; i--) {
			Card thiz = handCards.getCards().get(i);
			Card that = o.getCards().get(i);
			int result  = thiz.compareTo(that);
			if (result == 0) {
				continue;
			}else {
				return result;
			}
		}
		return 0;
	}
	
	public void setOwnerName(String name){
		this.ownerName = name; 
	}
	
	
	public String getOwner(){
		return ownerName;
	}
	

	private int compareShunzi(HandCards handCards, HandCards o) {
		if (o == null) {
			return 1;
		}
		Card maxCard1 = null;
		Card maxCard2 = null;
		if (handCards.getCards().get(2).getNumber() == 1) {
			if (handCards.getCards().get(0).getNumber() == 12) {
				maxCard1 = handCards.getCards().get(2);
			}else {
				maxCard1 = handCards.getCards().get(1);
			}
		}else {
			maxCard1 = handCards.getCards().get(2);
		}
		if (o.getCards().get(2).getNumber() == 1) {
			if (o.getCards().get(0).getNumber() == 12) {
				maxCard2 = o.getCards().get(2);
			}else {
				maxCard2 = o.getCards().get(1);
			}
		}else {
			maxCard2 = handCards.getCards().get(2);
		}
		return maxCard1.compareTo(maxCard2);
	}
	
	@Override
	public int hashCode() {
		int cardCode1 = getCards().get(0).hashCode();
		int cardCode2 = getCards().get(1).hashCode();
		int cardCode3 = getCards().get(2).hashCode();
		int code = 0xFFFFFF & (  (0xFF0000 & cardCode1 << 16)  | ( 0x00FF00 & cardCode2 << 8 ) | ( 0x0000FF & cardCode3));
		System.out.println( toString() + ",code:" +code );
		return code;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		HandCards hc = new HandCards();
		hc.cards = (ArrayList<Card>) this.getCards().clone();
		hc.type = this.type;
		hc.desc = this.desc;
		hc.ownerName = this.ownerName;
		return hc;
	}
	
	public static String getTypeName(int type){
		//同类比较
		switch (type) {
		case TYPE_BAOZI:
			return "豹子";
		case TYPE_SHUNJIN:
			return "顺金";
		case TYPE_JINHUA:
			return "金花";
		case TYPE_SHUNZI:
			return "顺子";
		case TYPE_DUIZI:
			 return "对子";
		case TYPE_DANPAI:
			return "单牌";
		default:
			return "未知";
		}
	}
	
}
