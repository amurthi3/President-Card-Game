package PresidentGame;

import java.util.*;

public class Runner {
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to President! Enter 'y' to begin a Tournament,"
				+ " or anything else to play a normal game.");
		String input = s.next();
		if (input.contentEquals("y"))
		{
			playTournament(s);
			s.close();
			return;
		}
		System.out.println("How many players? (5 - 25)");
		int numPlayers = 5;
		if (input.length() > 0)
		{
			switch (input.charAt(0))
			{
				case '6':
				{
					numPlayers = 6;
					break;
				}
				case '7':
				{
					numPlayers = 7;
					break;
				}
				case '8':
				{
					numPlayers = 8;
					break;
				}
				case '9':
				{
					numPlayers = 9;
					break;
				}
				case '1':
				{
					if (input.length() == 1)
					{
						break;
					}
					switch (input.charAt(1))
					{
						case '0':
						{
							numPlayers = 10;
							break;
						}
						case '1':
						{
							numPlayers = 11;
							break;
						}
						case '2':
						{
							numPlayers = 12;
							break;
						}
						case '3':
						{
							numPlayers = 13;
							break;
						}
						case '4':
						{
							numPlayers = 14;
							break;
						}
						case '5':
						{
							numPlayers = 15;
							break;
						}
						case '6':
						{
							numPlayers = 16;
							break;
						}
						case '7':
						{
							numPlayers = 17;
							break;
						}
						case '8':
						{
							numPlayers = 18;
							break;
						}
						case '9':
						{
							numPlayers = 19;
							break;
						}
					}
					break;
				}
				case '2':
				{
					if (input.length() == 1)
					{
						break;
					}
					switch (input.charAt(1))
					{
						case '0':
						{
							numPlayers = 20;
							break;
						}
						case '1':
						{
							numPlayers = 21;
							break;
						}
						case '2':
						{
							numPlayers = 22;
							break;
						}
						case '3':
						{
							numPlayers = 23;
							break;
						}
						case '4':
						{
							numPlayers = 24;
							break;
						}
						case '5':
						{
							numPlayers = 25;
							break;
						}
					}
					break;
				}
			}
		}
		
		System.out.println("How many decks? Choose from 1-12:");
		input = s.next();
		int numDecks = 1;
		if (input.length() > 0)
		{
			switch (input.charAt(0))
			{
				case '2':
				{
					numDecks = 2;
					break;
				}
				case '3':
				{
					numDecks = 3;
					break;
				}
				case '4':
				{
					numDecks = 4;
					break;
				}
				case '5':
				{
					numDecks = 5;
					break;
				}
				case '6':
				{
					numDecks = 6;
					break;
				}
				case '7':
				{
					numDecks = 7;
					break;
				}
				case '8':
				{
					numDecks = 8;
					break;
				}
				case '9':
				{
					numDecks = 9;
					break;
				}
				case '1':
				{
					if (input.length() == 1)
					{
						break;
					}
					switch(input.charAt(1))
					{
						case '0':
						{
							numDecks = 10;
							break;
						}
						case '1':
						{
							numDecks = 11;
							break;
						}
						case '2':
						{
							numDecks = 12;
							break;
						}
					}
					break;
				}
			}
		}
		System.out.println("Creating game with " + numPlayers + " players and " +
				numDecks + " deck(s).");
		Game game = new Game(numPlayers, numDecks, s);
		while (true)
		{
			/*System.out.println("Want to know how to play? Enter y for yes and anything else for no.");
			String answer = s.nextLine();
			if (answer.contentEquals("y"))
			{
				System.out.println("Here's how to play...");
				System.out.println("Objective: Get rid of all your cards before the other players.");
			}*/
			Player[] ranks = game.startGame();
			game.resetGame(ranks);
			System.out.println("Play again? Enter n to quit and any other key to keep playing.");
			if (s.nextLine().contentEquals("n"))
			{
				for (int i = 0; i < ranks.length; i++)
				{
					for (int j = 0; j < ranks.length - 1; j++)
					{
						if (ranks[j].getScore() < ranks[j + 1].getScore())
						{
							Player temp = ranks[j];
							ranks[j] = ranks[j + 1];
							ranks[j + 1] = temp;
						}
					}
				}
				System.out.println("Overall Scores: (Place, Score, Player)");
				for (int i = 0; i < ranks.length; i++)
				{
					if (ranks[i].getScore() >= 0)
					{
						System.out.println((i + 1) + "\t+" + ranks[i].getScore()
								+ "\tPlayer " + ranks[i].getNumber());
					}
					else
					{
						System.out.println((i + 1) + "\t" + ranks[i].getScore()
								+ "\tPlayer " + ranks[i].getNumber());
					}
				}
				s.close();
				break;
			}
			game.pickCards(ranks);
		}
	}
	
	public static void playTournament(Scanner s)
	{
		System.out.println("Starting a tournament, a set of 4 games of president with 3 decks and 12"
				+ " players facing off!");
		Game tournament = new Game(12, 3, s);
		Player[] ranks;
		int gameNum = 1;
		while (true)
		{
			System.out.println("Starting game " + gameNum + " of 4...");
			ranks = tournament.startGame();
			tournament.resetGame(ranks);
			if (gameNum >= 4)
			{
				break;
			}
			tournament.pickCards(ranks);
			gameNum++;
		}
		for (int i = 0; i < ranks.length; i++)
		{
			for (int j = 0; j < ranks.length - 1; j++)
			{
				if (ranks[j].getScore() < ranks[j + 1].getScore())
				{
					Player temp = ranks[j];
					ranks[j] = ranks[j + 1];
					ranks[j + 1] = temp;
				}
			}
		}
		System.out.println("Overall Scores: (Place, Score, Player)");
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i].getScore() >= 0)
			{
				System.out.println((i + 1) + "\t+" + ranks[i].getScore()
						+ "\tPlayer " + ranks[i].getNumber());
			}
			else
			{
				System.out.println((i + 1) + "\t" + ranks[i].getScore()
						+ "\tPlayer " + ranks[i].getNumber());
			}
		}
	}
}
