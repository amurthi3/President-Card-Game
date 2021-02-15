package PresidentGame;

import java.util.*;

public class CardSet {
	private List<Card> cards;
	
	public CardSet()
	{
		cards = new ArrayList<Card>();
	}
	
	public CardSet(List<Card> cards)
	{
		this.cards = cards;
	}
	
	public List<Card> getSet()
	{
		return cards;
	}
	
	public int getSize()
	{
		return cards.size();
	}
	
	public int getValue()
	{
		if (getSize() == 0)
		{
			return -1;
		}
		else
		{
			return cards.get(0).getNumValue();
		}
	}
	
	public int getTotalValue()
	{
		if (getSize() == 0)
		{
			return -1;
		}
		else
		{
			return 14 * (getSize() - 1) + getValue();
		}
	}
	
	public boolean addCard(Card c)
	{
		if (c.getNumValue() == getValue() || getValue() < 0)
		{
			cards.add(c);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		String result = "";
		for (Card c : cards)
		{
			result += (c.toString() + " ");
		}
		return result;
	}
}
