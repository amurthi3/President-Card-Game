package PresidentGame;

import java.util.*;

public class GameInfo {
	protected List<Player> players;
	protected List<Player> winners;
	protected List<Player> losers;
	protected Deck gameDeck;
	protected int currentPlayer;
	protected List<CardSet> playStack;
	protected boolean hasUser;
	
	public GameInfo(int numPlayers, int numDecks)
	{
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++)
		{
			players.add(new CPU(i + 1));
		}
		winners = new ArrayList<Player>();
		losers = new ArrayList<Player>();
		gameDeck = new Deck(numDecks);
		currentPlayer = 0;
		playStack = new ArrayList<CardSet>();
		hasUser = true;
	}
	
	public void setupGame()
	{
		gameDeck.shuffle();
		while(gameDeck.getCardsLeft() / players.size() > 0)
		{
			for (Player p : players)
			{
				p.addCard(gameDeck.dealCard());
			}
		}
	}
	
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public void nextTurn()
	{
		for (Player p : players)
		{
			p.updateHP();
		}
		currentPlayer++;
		if (players.isEmpty())
		{
			currentPlayer = -1;
		}
		else if (currentPlayer >= players.size())
		{
			currentPlayer = 0;
		}
		System.out.println("-------------------------------------------------------");
	}
	
	public void addWinner(Player p)
	{
		if (players.remove(p))
		{
			winners.add(p);
		}
	}
	
	public void addStack(CardSet set)
	{
		playStack.add(set);
	}
	
	public int getStackTop()
	{
		if (playStack.size() == 0)
		{
			return -1;
		}
		CardSet stackTop = playStack.get(playStack.size() - 1);
		return stackTop.getTotalValue();
	}
	
	public void printStackTop(boolean lastOnly)
	{
		if (playStack.size() > 0)
		{
			if (playStack.size() > 1 && !lastOnly)
			{
				if (playStack.size() > 2)
				{
					CardSet stackTop3 = playStack.get(playStack.size() - 3);
					for (Card c : stackTop3.getSet())
					{
						System.out.print(c.toString() + " ");
					}
					System.out.print("    ");
				}
				CardSet stackTop2 = playStack.get(playStack.size() - 2);
				for (Card c : stackTop2.getSet())
				{
					System.out.print(c.toString() + " ");
				}
				System.out.print("    ");
			}
			CardSet stackTop1 = playStack.get(playStack.size() - 1);
			for (Card c : stackTop1.getSet())
			{
				System.out.print(c.toString() + " ");
			}
		}
		System.out.println();
	}
	
	public int numStackContains(String v)
	{
		int occurrences = 0;
		int idx = playStack.size() - 1;
		while (idx >= 0)
		{
			CardSet currentSet = playStack.get(idx);
			if (currentSet.getSet().get(0).getValue().equals(v))
			{
				occurrences += currentSet.getSize();
			}
			else
			{
				break;
			}
			idx--;
		}
		return occurrences;
	}
	
	public void clearStack()
	{
		playStack.clear();
	}
	
	public void resetGame(Player[] ranks)
	{
		clearStack();
		players.clear();
		winners.clear();
		losers.clear();
		for (Player p : ranks)
		{
			players.add(p);
		}
		gameDeck = new Deck(gameDeck.getNumDecks());
		currentPlayer = 0;
		hasUser = true;
		setupGame();
		for (Player p : players)
		{
			p.arrangeHand();
		}
	}
}
