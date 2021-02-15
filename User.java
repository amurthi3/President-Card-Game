package PresidentGame;

import java.util.*;

public class User extends Player {
	Scanner s;
	
	public User(Scanner scan, int idx)
	{
		super(idx);
		s = scan;
	}
	
	public CardSet move(int stackTop)
	{
		System.out.println("Your turn.");
		String input = s.nextLine();
		//s.close();
		return parseCommand(input, stackTop);
	}
	
	public CardSet parseCommand(String command, int stackTop)
	{
		command = command.toUpperCase();
		if (command.length() < 1)
		{
			return null;
		}
		int idx = 0;
		char currentChar = command.charAt(idx);
		String val = "";
		
		if ((currentChar >= '1' && currentChar <= '9') || currentChar == 'J'
				|| currentChar == 'Q' || currentChar == 'K' || currentChar == 'A')
		{
			val += currentChar;
		}
		else
		{
			return null;
		}
		switch(currentChar)
		{
			case '1':
			{
				idx++;
				currentChar = command.charAt(idx);
				if (currentChar == '0')
				{
					val += currentChar;
				}
				else
				{
					return null;
				}
				break;
			}
		}
		idx++;
		if (command.length() <= idx)
		{
			return null;
		}
		currentChar = command.charAt(idx);
		char suite;
		if (currentChar == 'S' || currentChar == 'H' || currentChar == 'D' ||
				currentChar == 'C')
		{
			suite = currentChar;
		}
		switch(currentChar)
		{
			case 'S':
			case 'H':
			case 'D':
			case 'C':
			{
				suite = currentChar;
				break;
			}
			case ' ':
			case '-':
			case '/':
			case ',':
			{
				idx++;
				if (command.length() > idx)
				{
					currentChar = command.charAt(idx);
					int amount = (int) currentChar;
					if (amount > (int) '0' && amount <= (int) '9')
					{
						amount -= (int) '0';
					}
					else
					{
						return null;
					}
					idx++;
					if (command.length() > idx && amount == 1)
					{
						amount = 10 + (int) (command.charAt(idx) - '0');
						if (amount > 12)
						{
							amount = 12;
						}
						else if (amount < 10)
						{
							amount = 1;
						}
					}
					return playMultiple(val, amount, stackTop);
				}
				else
				{
					return null;
				}
			}
			default:
			{
				return null;
			}
		}
		return playSingle(val, suite, stackTop);
	}
	
	private CardSet playSingle(String v, char s, int stackTop)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			Card c = hand.get(i);
			if (c.getSuite() == s && c.getValue().equals(v) && (c.getNumValue() == 1 ||
					c.getNumValue() >= stackTop))
			{
				CardSet cardPlayed = new CardSet();
				cardPlayed.addCard(c);
				hand.remove(i);
				return cardPlayed;
			}
		}
		for (int i = 0; i < hand.size(); i++)
		{
			Card c = hand.get(i);
			if (c.getValue().equals(v) && (c.getNumValue() == 1 ||
					c.getNumValue() >= stackTop))
			{
				CardSet cardPlayed = new CardSet();
				cardPlayed.addCard(c);
				hand.remove(i);
				return cardPlayed;
			}
		}
		return null;
	}
	
	public CardSet playMultiple(String v, int amount, int stackTop)
	{
		int idx = -1;
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getValue().equals(v))
			{
				idx = i;
				break;
			}
		}
		if (idx < 0)
		{
			return null;
		}
		CardSet cardsPlayed = new CardSet();
		while (idx < hand.size())
		{
			Card c = hand.get(idx);
			if (c.getValue().equals(v) && cardsPlayed.getSize() < amount)
			{
				cardsPlayed.addCard(c);
			}
			else
			{
				break;
			}
		}
		if (cardsPlayed.getSize() == amount && cardsPlayed.getTotalValue() >= stackTop)
		{
			for (Card c : cardsPlayed.getSet())
			{
				int index = 0;
				int cardsRemoved = 0;
				while (index < hand.size() && cardsRemoved < amount)
				{
					if (hand.get(index).getNumValue() == c.getNumValue())
					{
						hand.remove(index);
						cardsRemoved++;
					}
					else
					{
						index++;
					}
				}
			}
			return cardsPlayed;
		}
		else
		{
			return null;
		}
	}
	
	public int chooseSwapIn()
	{
		System.out.println("You may pick a card. Type the value of the card or simply press Enter to pass.");
		switch(s.nextLine().toUpperCase())
		{
			case "A":
				return 1;
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
				return 0;
		}
	}
	
	public Card chooseSwapOut()
	{
		System.out.println("Enter the value of the card to drop.");
		int val = 0;
		switch(s.nextLine().toUpperCase())
		{
			case "A":
			{
				val = 1;
				break;
			}
			case "3":
			{
				val = 3;
				break;
			}
			case "4":
			{
				val = 4;
				break;
			}
			case "5":
			{
				val = 5;
				break;
			}
			case "6":
			{
				val = 6;
				break;
			}
			case "7":
			{
				val = 7;
				break;
			}
			case "8":
			{
				val = 8;
				break;
			}
			case "9":
			{
				val = 9;
				break;
			}
			case "10":
			{
				val = 10;
				break;
			}
			case "J":
			{
				val = 11;
				break;
			}
			case "Q":
			{
				val = 12;
				break;
			}
			case "K":
			{
				val = 13;
				break;
			}
			case "2":
			{
				val = 14;
				break;
			}
			default:
			{
				return null;
			}
		}
		for (Card c : hand)
		{
			if (c.getNumValue() == val)
			{
				return c;
			}
		}
		return null;
	}
	
	public void printMessage(String msg, boolean startNewLine)
	{
		System.out.print(msg);
		if (startNewLine)
		{
			System.out.println();
		}
	}
	
	public void printHand()
	{
		for (Card c : hand)
		{
			System.out.print(c.toString() + " ");
		}
		System.out.println();
	}
}
