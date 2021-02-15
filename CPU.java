package PresidentGame;

import java.util.*;

public class CPU extends Player {
	public CPU(int idx)
	{
		super(idx);
	}
	
	public CardSet move(int stackTop)
	{
		double strategy = Math.random();
		strategy *= strategy;
		List<CardSet> moveChoices = arrangeMoves(findMoves(stackTop));
		/*System.out.println("***Printing CPU's moves:");
		for (CardSet set: moveChoices)
		{
			System.out.println(set.toString());
		}
		System.out.println("***");*/
		CardSet choice = moveChoices.get((int)(moveChoices.size() * strategy));
		if (choice.getValue() == 1 && Math.random() > 0.6)
		{
			return null;
		}
		for (Card c : choice.getSet())
		{
			removeCard(c);
		}
		return choice;
	}
	
	private List<CardSet> arrangeMoves(List<CardSet> choices)
	{
		List<CardSet> temps = new ArrayList<CardSet>();
		for (int i = 0; i < choices.size(); i++)
		{
			for (int j = 0; j < choices.size() - 1; j++)
			{
				if (choices.get(j).getSize() > choices.get(j + 1).getSize())
				{
					CardSet temp = choices.get(j);
					choices.set(j, choices.get(j + 1));
					choices.set(j + 1, temp);
				}
			}
		}
		int idx = 0;
		while (idx < choices.size())
		{
			if (choices.get(idx).getValue() == 1)
			{
				temps.add(choices.get(idx));
				choices.remove(choices.get(idx));
			}
			else
			{
				idx++;
			}
		}
		idx = 0;
		while(idx < choices.size())
		{
			boolean isDuplicate = false;
			for (int i = 0; i < choices.size(); i++)
			{
				if (choices.get(i).getSize() > choices.get(idx).getSize()
						&& choices.get(i).getValue() == choices.get(idx).getValue())
				{
					isDuplicate = true;
				}
			}
			if (isDuplicate /*&& Math.random() > 0.25*/)
			{
				temps.add(choices.get(idx));
				choices.remove(choices.get(idx));
			}
			else
			{
				idx++;
			}
		}
		while (temps.size() > 0)
		{
			choices.add(temps.get(0));
			temps.remove(0);
		}
		return choices;
	}
	
	public int chooseSwapIn()
	{
		//int chosen = 0;
		int highTarget = 14 - (int)(Math.random() * Math.random() * 12);
		for (Card c : hand)
		{
			if (Math.random() < 0.3 && getCardOccurrences(c.getNumValue()) == 1)
			{
				return c.getNumValue();
			}
		}
		if (Math.random() < 0.5 && getCardOccurrences(1) == 0)
		{
			return 1;
		}
		for (Card c : hand)
		{
			if (Math.random() < 0.2 && getCardOccurrences(c.getNumValue()) % 4 == 2)
			{
				return c.getNumValue();
			}
		}
		return highTarget;
	}
	
	public Card chooseSwapOut()
	{
		for (Card c : hand)
		{
			if (getCardOccurrences(c.getNumValue()) == 1)
			{
				return c;
			}
		}
		for (Card c: hand)
		{
			if (c.getNumValue() > 1)
			{
				return c;
			}
		}
		return hand.get(0);
	}
	
	public void printMessage(String msg, boolean startNewLine)
	{
		return;
	}
	
	public void printHand()
	{
		return;
	}
}
