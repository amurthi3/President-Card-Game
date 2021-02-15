package PresidentGame;

import java.util.*;

public abstract class Player {
	protected List<Card> hand;
	protected int masterIdx;
	protected int hpPenalty;
	protected String rank;
	protected int score;
	
	public Player(int idx)
	{
		hand = new ArrayList<Card>();
		masterIdx = idx;
		hpPenalty = -1;
		rank = "";
		score = 0;
	}
	
	public int getNumber()
	{
		return masterIdx;
	}
	
	public boolean canHP()
	{
		return hpPenalty <= 0;
	}
	
	public void setHP(int numPlayers)
	{
		hpPenalty = numPlayers;
		System.out.println("Player " + this.getNumber() + " given HP penalty, " + hpPenalty + " turns.");
	}
	
	public void updateHP()
	{
		if (hpPenalty > 0)
		{
			hpPenalty--;
		}
		if (hpPenalty == 0)
		{
			System.out.println("Player " + this.getNumber() + " may again HP.");
			hpPenalty = -1;
		}
	}
	
	public void addCard(Card newCard)
	{
		hand.add(newCard);
	}
	
	public boolean removeCard(Card c)
	{
		int pos = hasCard(c);
		if (pos >= 0)
		{
			hand.remove(pos);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Card removeCard(int idx)
	{
		if (idx < 0 || idx >= hand.size())
		{
			return null;
		}
		return hand.remove(idx);
	}
	
	public int hasCard(Card needed)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).cardEquals(needed))
			{
				return i;
			}
		}
		return -1;
	}
	
	public int hasCard(int cardVal)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getNumValue() == cardVal)
			{
				return i;
			}
		}
		return -1;
	}
	
	public int getCardOccurrences(int cardVal)
	{
		int occurrences = 0;
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getNumValue() == cardVal)
			{
				occurrences++;
			}
		}
		return occurrences;
	}
	
	public int getHandSize()
	{
		return hand.size();
	}
	
	public void arrangeHand()
	{
		for (int i = 0; i < hand.size(); i++)
		{
			for (int j = 0; j < hand.size() - 1; j++)
			{
				if (hand.get(j).getNumValue() > hand.get(j + 1).getNumValue())
				{
					Card temp = hand.get(j);
					hand.set(j, hand.get(j + 1));
					hand.set(j + 1, temp);
				}
			}
		}
	}
	
	public boolean canPlay(int topValue)
	{
		return findMoves(topValue).size() > 0;
	}
	
	public List<CardSet> findMoves(int topValue)
	{
		List<CardSet> moves = new ArrayList<CardSet>();
		//List<Card> currentMove;
		//System.out.println("Printing moves:");
		for (int i = 0; i < hand.size(); i++)
		{
			int currentVal = hand.get(i).getNumValue();
			CardSet set = new CardSet();
			//currentMove = new ArrayList<Card>();
			if (currentVal == 1)
			{
				//currentMove.add(hand.get(i));
				set.addCard(hand.get(i));
				moves.add(set);
				//System.out.println(hand.get(i).toString());
			}
			else
			{
				int idx = i;
				while (idx < hand.size() && set.addCard(hand.get(idx)))
				{
					idx++;
				}
				if (set.getSize() > 0 && set.getTotalValue() >= topValue)
				{
					moves.add(set);
					/*for (Card c : set.getSet())
					{
						System.out.print(c.toString() + " ");
					}
					System.out.println();*/
				}
			}
		}
		//System.out.println("Moves possible: " + moves.size());
		return moves;
	}
	
	abstract public CardSet move(int stackTop);
	
	public HouseParty findHouseParty(int top, int amt, double reactionTime, int numPlayers)
	{
		CardSet set = new CardSet();
		for (Card c : hand)
		{
			if (c.getNumValue() == top)
			{
				set.addCard(c);
				if (set.getSize() == amt)
				{
					break;
				}
			}
		}
		if (set.getSize() < amt)
		{
			return null;
		}
		else
		{
			if (reactionTime <= 0)
			{
				reactionTime = getReactionTime(numPlayers);
			}
			return new HouseParty(set, reactionTime, this);
		}
	}
	
	public double getReactionTime(int numPlayers)
	{
		double reactionTime = Math.random();
		return numPlayers * reactionTime * reactionTime + 0.5;
	}
	
	public String getRank()
	{
		return rank;
	}
	
	public void setRank(String newRank)
	{
		rank = newRank;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void changeScore(int change)
	{
		score += change;
	}

	abstract public int chooseSwapIn();
	
	abstract public Card chooseSwapOut();
	
	abstract public void printMessage(String msg, boolean startNewLine);
	
	abstract public void printHand();
}
