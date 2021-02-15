package PresidentGame;

public class HouseParty {
	private CardSet cardsPlayed;
	private double reactionTime;
	private Player player;
	
	public HouseParty(CardSet cards, double reaction, Player p)
	{
		cardsPlayed = cards;
		reactionTime = reaction;
		player = p;
	}
	
	public CardSet getCardsPlayed()
	{
		return cardsPlayed;
	}
	
	public double getTime()
	{
		return reactionTime;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
