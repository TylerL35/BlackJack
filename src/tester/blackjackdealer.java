package tester;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class blackjackdealer {
	
	private ArrayList<deck> d = new ArrayList<deck>();
	private int decks;
	private boolean active;
	private int dscore;
	private int pscore;
	private int bal;
	private int softpscore;
	private int softdscore;
	Scanner s;
	
	card p1;
	card p2;
	card d1;
	card d2;
	
	public blackjackdealer(int decks, int buyIn, Scanner s)
	{
		this.decks = decks;
		this.s = s;
		for (int i = 0; i < decks; i++)
		{
			d.add(i, new deck());
		}
		active = true;
		dscore = 0;
		pscore = 0;
		bal = buyIn;
	}
	
	public int getDecks()
	{
		return decks;
	}
	
	public int getDeckSize()
	{
		int size = 0;
		for (int i = 0; i < d.size(); i++)
			size += d.get(i).getDeckSize();
		return size;
		
	}
	
	public int getMuckSize()
	{
		int size = 0;
		for (int i = 0; i < d.size(); i++)
			size += d.get(i).getMuckSize();
		return size;
	}
	
	public card drawCard(boolean readOut)
	{
		for (int i = 0; i < d.size(); i++)
		{
			if (!d.get(i).isEmpty())
			{
				return d.get(i).drawCard(readOut);
			}
		}
		System.out.println("No cards left. Please Shuffle");
		return null;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void changeActive(boolean tf)
	{
		active = tf;
	}
	
	public void shuffle()
	{
		for (int i = 0; i < d.size(); i++)
			d.get(i).shuffle();
		Collections.shuffle(d);
	}
	
	public int getPlayerScore()
	{	
		return pscore;
	}
	
	public int getDealerScore()
	{
		return dscore;
	}
	
	public boolean dealRound(int betSize)
	{
		System.out.println("Balance: " + bal);
		pscore = 0;
		dscore = 0;
		softpscore = 0;
		softdscore = 0;
		
		System.out.println("dealer burns 1 card \n");
		drawCard(false);
		
		System.out.println("Player's 1st card: ");
		p1 = drawCard(true);
		
		System.out.println("Dealer's 1st card: ");
		d1 = drawCard(true);
		
		System.out.println("Player's 2nd card: ");
		p2 = drawCard(true);
		
		System.out.println("Player's 2nd card: Hidden");
		d2 = drawCard(false);
		
		if (p1.getValue() >= 11 && p2.getValue() >= 11)
			pscore += 20;
		else if (p2.getValue() >= 11)
			pscore += 10 + p1.getValue();
		else if (p1.getValue() >= 11)
			pscore += 10 + p2.getValue();
		else 
			pscore += p1.getValue() + p2.getValue();
		
		if (d1.getValue() >= 11 && d2.getValue() >= 11)
			dscore += 20;
		else if (d2.getValue() >= 11)
			dscore += 10 + d1.getValue();
		else if (p1.getValue() >= 11)
			dscore += 10 + d2.getValue();
		else 
			dscore += d1.getValue() + d2.getValue();
		
		if (pscore == 11 && (p1.getValue() == 1 || p2.getValue() == 1))
		{
			pscore = 21;
			if (dscore == 11 && (d1.getValue() == 1 || d2.getValue() == 1))
			{
				dscore = 21;
				System.out.println("Dealer 2nd card: " + d2.toString());
				System.out.println("Push");
				s.close();
				return false;
			}
			else
			{
				System.out.println("Dealer 2nd card: " + d2.toString());
				System.out.println("BLACKJACK");
				bal += betSize;
				s.close();
				return true;
			}
		}
		else
		{
			if (d1.getValue() == 1)
			{
				System.out.println("Dealer has an ace. Insurance? 1-yes, 2-no");
				int r = s.nextInt();
				if (r == 1)
				{
					bal -= betSize;
					if (d2.getScoreValue() == 10)
					{
						System.out.println("Dealer has Blackjack");
						bal += betSize;
					}
					else
					{
						System.out.println("No blackjack for dealer.");
					}
				}
			}
			if (dscore == 11 && (d1.getValue() == 1 || d2.getValue() == 1))
			{
				dscore = 21;
				bal -= betSize;
				System.out.println("Dealer 2nd card: " + d2.toString());
				System.out.println("Dealer Blackjack");
				s.close();
				return false;
			}
			
			softpscore = pscore;
			softdscore  = dscore;
			
			if (p1.getValue() == 1)
			{
	
				pscore++;
				softpscore += 11;	
			}
			
			
			if (p2.getValue() == 1)
			{
				if (pscore < 11)
				{
					pscore++;
					softpscore += 11;
				}		
			}
			
			if (d1.getValue() == 1)
			{
	
				dscore++;
				softdscore += 11;	
			}
			
			
			if (d2.getValue() == 1)
			{
				if (dscore < 11)
				{
					dscore++;
					softdscore += 11;
				}		
			}
		}
		
		
		card temporary;
		
		
		
		while (pscore < 21)
		{
			System.out.println("\nPlayer Score: " + pscore + ", Dealer Card: " + d1.toString());
			if (softpscore != pscore && softpscore <= 21)
			{
				System.out.println("Soft score: " + softpscore);
			}
			System.out.println("1 - Hit, 2 - Stand");
			if (s.nextInt() == 1)
			{
				System.out.println("Your card is: ");
				temporary = drawCard(true);
				if (temporary.getValue() == 1)
				{
					if (pscore < 11)
					{
						pscore++;
						if (softpscore < 11)
						{
							softpscore += 11;
						}
					}		
				}
				else
					pscore += temporary.getValue();
				if (pscore > 21)
				{
					System.out.println("BUSTED");
					bal -= betSize;
					return false;
				}
				if (pscore == 21)
				{
					System.out.println("Player Score is 21");
					break;
				}
				else if (softpscore == 21)
				{
					System.out.println("Player Score is 21");
					pscore = softpscore;
					break;
				}
			}
			else
			{
				if (softpscore > pscore && softpscore <= 21)
				{
					pscore = softpscore;
					break;
				}
				System.out.println("\nPlayer Score: " + pscore);
				if (pscore > 21)
				{
					System.out.println("BUSTED");
					bal -= betSize;
					return false;
				}
				else if	(pscore == 21)
				{
					System.out.println("Player Score is 21");
					break;
				}
				else if (softpscore == 21)
				{
					System.out.println("Player Score is 21");
					pscore = softpscore;
					break;
				}
				else
				{
					break;
				}
			}
		}
		
		System.out.println("Dealer 1st Card: " + d1.toString() + ", Dealer 2nd card: " + d2.toString());
		
		if (dscore > pscore)
		{
			bal -= betSize;
			System.out.println("Dealer score: " + dscore);
			return false;
		}
		else if (softdscore > pscore)
		{
			bal -= betSize;
			System.out.println("Dealer score: " + softdscore);
			return false;
		}
		
		while (dscore < 17 && softdscore < 17)
		{
			System.out.println("Dealer card is: ");
			temporary = drawCard(true);
			if (temporary.getValue() == 1)
			{
				if (dscore < 11)
				{
					dscore++;
					if (softdscore < 11)
					{
						softpscore += 11;
					}
				}		
			}
			else
				dscore += temporary.getValue();
			if (dscore > 21)
			{
				System.out.println("DEALER BUSTED");
				bal += betSize;
				return true;
			}
			else if (dscore == 21)
			{
				System.out.println("DEALER HAS 21");	
				
			}
			else if (softdscore == 21)
			{
				dscore = softdscore;
				System.out.println("DEALER HAS 21");	
			}
		}
		
		if (softdscore > dscore && softdscore <= 21)
		{
			dscore = softdscore;
		}
		System.out.println("\nDeaker Score: " + dscore);
		
		
		if (dscore == pscore)
		{
			System.out.println("PUSH");
			return false;
		}
		else if (dscore > pscore)
		{
			System.out.println("DEALER WINS");
			bal+=betSize;
			return false;
		}
		else
		{
			System.out.println("YOU WIN");
			bal+=betSize;
			return true;
		}
		
	
	}

}
