
public class Card implements Comparable<Card> {
	private String flowerType = "UF";
	private String numberType = "UN";
	
	public Card(String flowerType, String numberType) {
		super();
		this.flowerType = flowerType;
		this.numberType = numberType;
	}
	public String getFlowerType() {
		return flowerType;
	}
	public void setFlowerType(String flowerType) {
		this.flowerType = flowerType;
	}
	public String getNumberType() {
		return numberType;
	}
	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}
	
	public int getNumber(){
		if (numberType.equals("K")) {
			return 13;
		}else if (numberType.equals("Q")) {
			return 12;
		}else if (numberType.equals("J")) {
			return 11;
		}else if (numberType.equals("A")) {
			return 1;
		}else {
			return Integer.parseInt(numberType);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb
		 = new StringBuilder();
		sb.append("[");
		sb.append(flowerType);
		sb.append("-");
		sb.append(numberType);
		sb.append("]");
		return sb.toString();
	}
	
	public static int compareTo(Card a , Card b){
		return a.compareTo(b);
	}
	
	@Override
	public int compareTo(Card o) {
		//对方为空,则直接返回比他大.
		if (o == null) {
			return 1;
		}
		int thatNumber = o.getNumber();
		int thisNumber = getNumber();
		
		//相等,则直接返回.
		if (thatNumber == thisNumber) {
			return 0;
		}else {
			//A 特殊处理
			if (thatNumber == 1) {
				return -1;
			}else if (thisNumber == 1) {
				return 1;
			}else {
				//比数字大小
				return thatNumber > thisNumber ? -1:1;
			}
		}
		
	}
	
	@Override
	public int hashCode() {
		int flowerType = Cards.getCardFlowerType(this.flowerType);
		int number = getNumber();
		
//		int a = flowerType << 4;
//		int x = 0xF0 & ( flowerType << 4 );
//		int y = 0x0F & number;
//		int z = 0xFF & ( x | y );
		
		return 0xFF & (   ( 0xF0 &  flowerType << 4 ) | (0x0F & number) ) ;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Card c = new Card(this.flowerType, this.numberType);
		return c;
	}
	
}
