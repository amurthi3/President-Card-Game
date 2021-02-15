package PresidentGame;

//import PresidentGame.Card.suites;
//import PresidentGame.Card.values;

//import java.util.*;

public class Deck {
	private Card[] cards;
	//private int numDecks;
	private int cardsDealt;
	
	public Deck (int decks)
	{
		//System.out.println(decks);
		/*if (decks > 12)
		{
			numDecks = 12;
			System.out.println("Max number of decks is 12. Starting the game with 12 decks.");
		}
		else if (decks > 0)
		{
			numDecks = decks;
		}
		else
		{
			numDecks = 1;
			System.out.println("Min number of decks is 1. Starting the game with 1 deck.");
		}*/
		cards = new Card[decks * 52];
		for (int i = 0; i < decks; i++)
		{
			cards[i * 52] = new Card('S', "A");
			cards[i * 52 + 1] = new Card('S', "2");
			cards[i * 52 + 2] = new Card('S', "3");
			cards[i * 52 + 3] = new Card('S', "4");
			cards[i * 52 + 4] = new Card('S', "5");
			cards[i * 52 + 5] = new Card('S', "6");
			cards[i * 52 + 6] = new Card('S', "7");
			cards[i * 52 + 7] = new Card('S', "8");
			cards[i * 52 + 8] = new Card('S', "9");
			cards[i * 52 + 9] = new Card('S', "10");
			cards[i * 52 + 10] = new Card('S', "J");
			cards[i * 52 + 11] = new Card('S', "Q");
			cards[i * 52 + 12] = new Card('S', "K");
			
			cards[i * 52 + 13] = new Card('H', "A");
			cards[i * 52 + 14] = new Card('H', "2");
			cards[i * 52 + 15] = new Card('H', "3");
			cards[i * 52 + 16] = new Card('H', "4");
			cards[i * 52 + 17] = new Card('H', "5");
			cards[i * 52 + 18] = new Card('H', "6");
			cards[i * 52 + 19] = new Card('H', "7");
			cards[i * 52 + 20] = new Card('H', "8");
			cards[i * 52 + 21] = new Card('H', "9");
			cards[i * 52 + 22] = new Card('H', "10");
			cards[i * 52 + 23] = new Card('H', "J");
			cards[i * 52 + 24] = new Card('H', "Q");
			cards[i * 52 + 25] = new Card('H', "K");
			
			cards[i * 52 + 26] = new Card('D', "A");
			cards[i * 52 + 27] = new Card('D', "2");
			cards[i * 52 + 28] = new Card('D', "3");
			cards[i * 52 + 29] = new Card('D', "4");
			cards[i * 52 + 30] = new Card('D', "5");
			cards[i * 52 + 31] = new Card('D', "6");
			cards[i * 52 + 32] = new Card('D', "7");
			cards[i * 52 + 33] = new Card('D', "8");
			cards[i * 52 + 34] = new Card('D', "9");
			cards[i * 52 + 35] = new Card('D', "10");
			cards[i * 52 + 36] = new Card('D', "J");
			cards[i * 52 + 37] = new Card('D', "Q");
			cards[i * 52 + 38] = new Card('D', "K");
			
			cards[i * 52 + 39] = new Card('C', "A");
			cards[i * 52 + 40] = new Card('C', "2");
			cards[i * 52 + 41] = new Card('C', "3");
			cards[i * 52 + 42] = new Card('C', "4");
			cards[i * 52 + 43] = new Card('C', "5");
			cards[i * 52 + 44] = new Card('C', "6");
			cards[i * 52 + 45] = new Card('C', "7");
			cards[i * 52 + 46] = new Card('C', "8");
			cards[i * 52 + 47] = new Card('C', "9");
			cards[i * 52 + 48] = new Card('C', "10");
			cards[i * 52 + 49] = new Card('C', "J");
			cards[i * 52 + 50] = new Card('C', "Q");
			cards[i * 52 + 51] = new Card('C', "K");
		}
		cardsDealt = 0;
	}
	
	public int getNumDecks()
	{
		return cards.length / 52;
	}
	
	public void shuffle()
	{
		int newPos;
		Card temp;
		for (int i = 0; i < cards.length; i++)
		{
			newPos = (int)(Math.random() * cards.length);
			temp = cards[i];
			cards[i] = cards[newPos];
			cards[newPos] = temp;
		}
	}
	
	public Card dealCard()
	{
		cards[cardsDealt].setInDeck(false);
		cardsDealt++;
		return cards[cardsDealt - 1];
	}
	
	public int getCardsLeft()
	{
		return cards.length - cardsDealt;
	}
}
