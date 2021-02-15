package PresidentGame;

public class Card {
	//public static enum suites { SPADES, HEARTS, DIAMONDS, CLUBS; }
	//public static enum values { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
		//NINE, TEN, JACK, QUEEN, KING; }
	private char suite;
	private String value;
	private boolean inDeck;
	
	public Card(char s, String v)
	{
		/*switch (s)
		{
			case 's':
			{
				suite = suites.SPADES;
				break;
			}
			case 'h':
			{
				suite = suites.HEARTS;
				break;
			}
			case 'd':
			{
				suite = suites.DIAMONDS;
				break;
			}
			case 'c':
			{
				suite = suites.CLUBS;
				break;
			}
			default:
			{
				suite = suites.SPADES;
				break;
			}
		}*/
		//value = values.ACE;
		suite = s;
		value = v;
		inDeck = true;
	}
	
	public char getSuite()
	{
		return suite;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public int getNumValue()
	{
		switch (value)
		{
			case "3":
				return 3;
			case "4":
				return 4;
			case "5":
				return 5;
			case "6":
				return 6;
			case "7":
				return 7;
			case "8":
				return 8;
			case "9":
				return 9;
			case "10":
				return 10;
			case "J":
				return 11;
			case "Q":
				return 12;
			case "K":
				return 13;
			case "2":
				return 14;
			default:
				return 1;
		}
	}
	
	public boolean getInDeck()
	{
		return inDeck;
	}
	
	public void setInDeck(boolean b)
	{
		inDeck = b;
	}
	
	public boolean cardEquals(Card other)
	{
		return (this.getSuite() == other.getSuite()
				&& this.getNumValue() == other.getNumValue());
	}
	
	public String toString()
	{
		/*String result = "";
		switch(value)
		{
			case TWO:
			{
				result = result + "2";
				break;
			}
			case THREE:
			{
				result = result + "3";
				break;
			}
			case FOUR:
			{
				result = result + "4";
				break;
			}
			case FIVE:
			{
				result = result + "5";
				break;
			}
			case SIX:
			{
				result = result + "6";
				break;
			}
			case SEVEN:
			{
				result = result + "7";
				break;
			}
			case EIGHT:
			{
				result = result + "8";
				break;
			}
			case NINE:
			{
				result = result + "9";
				break;
			}
			case TEN:
			{
				result = result + "10";
				break;
			}
			case JACK:
			{
				result = result + "J";
				break;
			}
			case QUEEN:
			{
				result = result + "Q";
				break;
			}
			case KING:
			{
				result = result + "K";
				break;
			}
			case ACE:
			{
				result = result + "A";
				break;
			}
		}
		switch (suite)
		{
			case SPADES:
			{
				result = result + "S";
				break;
			}
			case DIAMONDS:
			{
				result = result + "D";
				break;
			}
			case HEARTS:
			{
				result = result + "H";
				break;
			}
			case CLUBS:
			{
				result = result + "C";
				break;
			}
		}
		return result;*/
		return value + suite;
	}
}
