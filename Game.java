package PresidentGame;

import java.util.*;

public class Game extends GameInfo {
	private Player user;
	private int userID;
	private Scanner s;
	
	public Game(int numPlayers, int numDecks, Scanner scan) {
		super(numPlayers, numDecks);
		userID = chooseOrder() + 1;
		s = scan;
		user = new User(s, userID);
		players.set(userID - 1, user);
		setupGame();
		for (Player p : players)
		{
			p.arrangeHand();
		}
	}
	
	private int chooseOrder()
	{
		return (int)(Math.random() * players.size());
	}
	
	public Player[] startGame()
	{
		System.out.println("You are Player " + userID);
		System.out.println("Here is your hand. Take a good look.");
		//players.get(userID).printHand();
		s.nextLine();
		Player[] ranks = playRound(s, 0);
		while (ranks == null)
		{
			ranks = playRound(s, currentPlayer);
		}
		return ranks;
	}
	
	public Player[] playRound(Scanner s, int starter)
	{
		//Scanner s = new Scanner(System.in);
		boolean endGame = false;
		currentPlayer = starter;
		int numPasses = 0;
		while (!endGame)
		{
			if (players.size() == 1)
			{
				endGame = true;
				winners.add(players.get(0));
				players.remove(0);
				break;
			}
			if (numPasses >= 2)
			{
				System.out.println("Player " + players.get(currentPlayer).getNumber() + " skipped.");
				nextTurn();
				break;
			}
			if (hasUser)
			{
				System.out.print("Your cards: ");
				user.printHand();
			}
			//Scan here
			System.out.print("Player " + players.get(currentPlayer).getNumber() + " playing.");
			if (players.get(currentPlayer).getNumber() == userID)
			{
				System.out.print(" That's you!");
			}
			System.out.println();
			System.out.print("Top card(s): ");
			printStackTop(false);
			//System.out.println(getStackTop());
			Card topCard;
			if (playStack.isEmpty())
			{
				topCard = null;
			}
			else
			{
				topCard = playStack.get(playStack.size() - 1).getSet().get(0);
			}
			List<HouseParty> hpList = new ArrayList<HouseParty>();
			for (Player p : players)
			{
				if ((!hasUser || p.getNumber() != userID) && p.canHP() &&
						playStack.size() > 0 && Math.random() < 0.9 - 0.02 * players.size())
				{
					HouseParty party = p.findHouseParty(topCard.getNumValue(),
							4 - numStackContains(topCard.getValue()) % 4, -1.0, players.size());
					if (party != null)
					{
						hpList.add(party);
					}
					else if (Math.random() > 0.95 - 0.005 * players.size())
					{
						p.setHP(players.size());
					}
					//System.out.println("Cards to HP: " + (4 - numStackContains(topCard.getValue()) % 4));
				}
			}
			System.out.println("Press the enter key to start the turn.");
			long userHPStart = System.nanoTime();
			String turnInput = s.nextLine();
			if (turnInput.contentEquals("p") && players.get(currentPlayer).getNumber() == userID)
			{
				System.out.println("Player " + userID + " passing.");
				numPasses++;
				nextTurn();
				continue;
			}
			else if (turnInput.contentEquals("h") && playStack.size() > 0 && hasUser &&
					user.canHP())
			{
				long userHPFinish = System.nanoTime();
				double reactionTime = ((double)(userHPFinish - userHPStart)) / 1000000000;
				HouseParty party = user.findHouseParty(topCard.getNumValue(),
						4 - numStackContains(topCard.getValue()) % 4, reactionTime, players.size());
				if (party != null)
				{
					hpList.add(party);
				}
				else
				{
					System.out.println("Your house party was invalid. 1-round penalty.");
					user.setHP(players.size());
				}
			}
			double minTime = Double.MAX_VALUE;
			for (HouseParty party : hpList)
			{
				if (party.getTime() < minTime)
				{
					minTime = party.getTime();
				}
			}
			/*System.out.print("Player " + (currentPlayer + 1) + "'s hand: ");
			players.get(currentPlayer).printHand();
			System.out.println(players.get(currentPlayer).canPlay(getStackTop()));*/
			if (players.get(currentPlayer).canPlay(getStackTop()))
			{
				numPasses = 0;
				CardSet cardsPlayed;
				double moveTime;
				long moveStart = System.nanoTime();
				do
				{
					//System.out.println(getStackTop());
					cardsPlayed = players.get(currentPlayer).move(getStackTop());
				} while (cardsPlayed == null && players.get(currentPlayer).getNumber() == userID);
				if (cardsPlayed == null)
				{
					System.out.println("Player " + players.get(currentPlayer).getNumber() + " passing.");
					numPasses++;
					nextTurn();
					continue;
				}
				if (players.get(currentPlayer).getNumber() == userID)
				{
					long moveFinish = System.nanoTime();
					moveTime = ((double)(moveFinish - moveStart)) / 1000000000;
				}
				else
				{
					moveTime = players.get(currentPlayer).getReactionTime(players.size());
				}
				if (minTime < moveTime)
				{
					for (Card c : cardsPlayed.getSet())
					{
						players.get(currentPlayer).addCard(c);
					}
					players.get(currentPlayer).arrangeHand();
					playHouseParty(hpList);
					continue;
				}
				else if (topCard != null)
				{
					HouseParty currentHP = players.get(currentPlayer).findHouseParty(topCard.getNumValue()
						, 4 - numStackContains(topCard.getValue()) % 4, moveTime, players.size());
					if (currentHP != null)
					{
						System.out.println(cardsPlayed.getValue() + ", " + topCard.getNumValue() + ", "
								+ cardsPlayed.getSize() + ", " + currentHP.getCardsPlayed().getSize());
					}
					if (currentHP != null && cardsPlayed.getValue() == topCard.getNumValue() &&
							cardsPlayed.getSize() == currentHP.getCardsPlayed().getSize())
					{
						List<HouseParty> chp = new ArrayList<HouseParty>();
						chp.add(currentHP);
						playHouseParty(chp);
						continue;
					}
				}
				playStack.add(cardsPlayed);
				System.out.print("Played: ");
				printStackTop(true);
				if (getStackTop() == 1)
				{
					if (players.get(currentPlayer).getHandSize() == 0)
					{
						System.out.println("Player " + players.get(currentPlayer).getNumber()
								+ " lost by default.");
						losers.add(players.get(currentPlayer));
						players.remove(currentPlayer);
						if (currentPlayer == players.size())
						{
							nextTurn();
						}
						if (players.get(currentPlayer).getNumber() == userID)
						{
							hasUser = false;
						}
						//nextTurn();
					}
					break;
				}
				else
				{
					if (players.get(currentPlayer).getHandSize() == 0)
					{
						System.out.println("Player " + players.get(currentPlayer).getNumber()
								+ " finished!");
						winners.add(players.get(currentPlayer));
						if (players.get(currentPlayer).getNumber() == userID)
						{
							hasUser = false;
						}
						players.remove(currentPlayer);
						if (currentPlayer == players.size())
						{
							nextTurn();
						}
					}
					else
					{
						nextTurn();
					}
					if (playStack.size() > 1)
					{
						CardSet topSet = playStack.get(playStack.size() - 1);
						CardSet belowSet = playStack.get(playStack.size() - 2);
						if (topSet.getSize() % 4 == 1 && topSet.getSize() == belowSet.getSize() &&
								topSet.getValue() == belowSet.getValue() &&
								numStackContains(topSet.getSet().get(0).getValue()) == 2 * topSet.getSize())
						{
							System.out.println("Player " + players.get(currentPlayer).getNumber() + " skipped.");
							nextTurn();
						}
					}
				}
			}
			else
			{
				if (hpList.isEmpty() || minTime > players.get(currentPlayer).getReactionTime(players.size()))
				{
					System.out.println("Can't play. Passing.");
					numPasses++;
					nextTurn();
				}
				else
				{
					playHouseParty(hpList);
				}
			}
		}
		//s.close();
		clearStack();
		if (endGame)
		{
			return showWinners();
		}
		else
		{
			return null;
		}
	}
	
	public void playHouseParty(List <HouseParty> hpList)
	{
		if (hpList.isEmpty())
		{
			return;
		}
		HouseParty firstHP = hpList.get(0);
		for (HouseParty party : hpList)
		{
			System.out.println("Player " + party.getPlayer().getNumber() + "'s HP time: " + party.getTime());
			if (party.getTime() < firstHP.getTime())
			{
				firstHP = party;
			}
		}
		System.out.println("Player " + firstHP.getPlayer().getNumber() + " played a House Party!");
		System.out.print("Cards played: ");
		for (Card c : firstHP.getCardsPlayed().getSet())
		{
			System.out.print(c.toString() + " ");
			firstHP.getPlayer().removeCard(c);
		}
		System.out.println();
		clearStack();
		int idx = 0;
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).equals(firstHP.getPlayer()))
			{
				idx = i;
				break;
			}
		}
		currentPlayer = idx;
		if (players.get(currentPlayer).getHandSize() == 0)
		{
			System.out.println("Player " + players.get(currentPlayer).getNumber() + " finished!");
			if (players.get(currentPlayer).getNumber() == userID)
			{
				hasUser = false;
			}
			players.remove(currentPlayer);
			if (currentPlayer >= players.size())
			{
				currentPlayer = 0;
			}
		}
	}
	
	public void rankPlayers(Player[] ranks)
	{
		for (Player p : ranks)
		{
			p.setRank("Middling One");
		}
		ranks[0].setRank("President");
		ranks[1].setRank("Vice President");
		ranks[ranks.length - 2].setRank("Vice Scum");
		ranks[ranks.length - 1].setRank("Scum");
		if (ranks.length < 6)
		{
			return;
		}
		ranks[2].setRank("Secretary");
		ranks[ranks.length - 3].setRank("Scum Secretary");
		if (ranks.length < 10)
		{
			return;
		}
		ranks[3].setRank("Cabinet");
		ranks[ranks.length - 4].setRank("Scum Cabinet");
		if (ranks.length < 14)
		{
			return;
		}
		ranks[4].setRank("Governor");
		ranks[ranks.length - 5].setRank("Scum Governor");
		if (ranks.length < 18)
		{
			return;
		}
		ranks[5].setRank("Mayor");
		ranks[ranks.length - 6].setRank("Scum Mayor");
	}
	
	public int[] updateScores(Player[] ranks)
	{
		int[] scoreChanges = new int[ranks.length];
		int scalar = ranks.length * 4;
		for (int i = 0; i < ranks.length; i++)
		{
			if (!ranks[i].getRank().contentEquals("Middling One"))
			{
				scoreChanges[i] = scalar - i * 8;
				ranks[i].changeScore(scoreChanges[i]);
			}
			else
			{
				scoreChanges[i] = 0;
			}
		}
		return scoreChanges;
	}
	
	public Player[] showWinners()
	{
		System.out.println("Game Over!");
		System.out.print("\nRankings (Place, Points, Player, Title):");
		int totalPlayers = winners.size() + losers.size();
		Player[] ranks = new Player[totalPlayers];
		for (int i = 0; i < winners.size(); i++)
		{
			ranks[i] = winners.get(i);
		}
		for (int i = losers.size() - 1; i >= 0; i--)
		{
			ranks[winners.size() + losers.size() - (i + 1)] = losers.get(i);
		}
		rankPlayers(ranks);
		int[] scoreChanges = updateScores(ranks);
		//System.out.println(totalPlayers);
		int idx = 0;
		for (int i = 0; i < ranks.length; i++)
		{
			if (scoreChanges[i] >= 0)
			{
				System.out.print("\n" + (i + 1) + "\t+" + scoreChanges[i] + "\tPlayer "
						+ ranks[i].getNumber() + "\t" + ranks[i].getRank());
			}
			else
			{
				System.out.print("\n" + (i + 1) + "\t" + scoreChanges[i] + "\tPlayer "
						+ ranks[i].getNumber() + "\t" + ranks[i].getRank());
			}
			
			if (ranks[i].getClass().equals(new User(s, i).getClass()))
			{
				idx = i + 1;
				System.out.print(" (User)");
			}
		}
		/*for (int i = 0; i < winners.size(); i++)
		{
			System.out.print("\n" + (i + 1) + "\tPlayer " + winners.get(i).getNumber());
			if (winners.get(i).getClass().equals(new User(s, i).getClass()))
			{
				idx = i + 1;
				System.out.print(" (User)");
			}
		}
		for (int i = losers.size() - 1; i >= 0; i--)
		{
			System.out.print
					("\n" + (totalPlayers - i) + "\tPlayer " + losers.get(i).getNumber());
			if (losers.get(i).getClass().equals(new User(s, i).getClass()))
			{
				idx = totalPlayers + 1 - i;
				System.out.print(" (User)");
			}
		}*/
		System.out.println("\nYou placed " + idx + " out of " + totalPlayers +
				". Title: " + ranks[idx - 1].getRank());
		return ranks;
	}
	
	public void pickCards(Player[] ranks)
	{
		System.out.println("Let the picking begin!");
		int numPicking = 1;
		int trades = 6;
		int threshold = 18;
		while (threshold >= 6)
		{
			if (ranks.length >= threshold)
			{
				exchangeCards(ranks[trades - 1], ranks[ranks.length - trades], numPicking);
				numPicking++;
			}
			trades--;
			threshold -= 4;
		}
		while (trades > 0)
		{
			exchangeCards(ranks[trades - 1], ranks[ranks.length - trades], numPicking);
			numPicking++;
			trades--;
		}
		System.out.println("Picking concluded. Starting game...");
	}
	
	public void exchangeCards(Player picker, Player pickee, int numPicking)
	{
		System.out.println("Player " + picker.getNumber() + " picks " + numPicking + " cards from Player "
				+ pickee.getNumber());
		/*if (picker.getNumber() == userID)
		{
			System.out.print("Your cards: ");
			picker.printHand();
		}*/
		picker.printMessage("Your cards: ", false);
		picker.printHand();
		for (int i = 0; i < numPicking; i++)
		{
			int pickedIdx;
			Card replaced;
			do
			{
				pickedIdx = pickee.hasCard(picker.chooseSwapIn());
				//System.out.println(pickedIdx);
			} while (pickedIdx < 0);
			Card picked = pickee.removeCard(pickedIdx);
			pickee.arrangeHand();
			picker.addCard(picked);
			picker.arrangeHand();
			//System.out.println(picker.getNumber() + ", " + pickee.getNumber() + ", " + userID);
			/*if (picker.getNumber() == userID)
			{
				System.out.println("You received " + picked.toString() + " from Player " +
						pickee.getNumber());
				System.out.print("Your hand: ");
				picker.printHand();
			}
			else if (pickee.getNumber() == userID);
			{
				System.out.println("Player " + picker.getNumber() + " picked " + picked.toString()
						+ " from you.");
				System.out.print("Your hand: ");
				pickee.printHand();
			}*/
			picker.printMessage("You received " + picked.toString() + " from Player " +
						pickee.getNumber(), true);
			picker.printMessage("Your hand: ", false);
			picker.printHand();
			pickee.printMessage("Player " + picker.getNumber() + " picked " + picked.toString()
						+ " from you.", true);
			pickee.printMessage("Your hand: ", false);
			pickee.printHand();
			do
			{
				replaced = picker.chooseSwapOut();
			} while (replaced == null);
			picker.removeCard(replaced);
			picker.arrangeHand();
			pickee.addCard(replaced);
			pickee.arrangeHand();
			/*if (picker.getNumber() == userID)
			{
				System.out.println("You gave back " + replaced.toString() + " to Player " +
						pickee.getNumber());
				System.out.print("Your hand: ");
				picker.printHand();
			}
			else if (pickee.getNumber() == userID);
			{
				System.out.println("Player " + picker.getNumber() + " gave you " + replaced.toString()
						+ " in return.");
				System.out.print("Your hand: ");
				pickee.printHand();
			}*/
			picker.printMessage("You gave back " + replaced.toString() + " to Player " +
						pickee.getNumber(), true);
			picker.printMessage("Your hand: ", false);
			picker.printHand();
			pickee.printMessage("Player " + picker.getNumber() + " gave you " + replaced.toString()
						+ " in return.", true);
			pickee.printMessage("Your hand: ", false);
			pickee.printHand();
		}
		System.out.println("-----------------------------------------");
	}
}
