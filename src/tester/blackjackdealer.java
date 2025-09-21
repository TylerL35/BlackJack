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
	
	card p1;
	card p2;
	card d1;
	card d2;
	
	public blackjackdealer(int decks, int buyIn)
	{
		this.decks = decks;
		
		for (int i = 0; i < decks; i++)
		{
			d.add(i, new deck());
		}
		active = true;
		dscore = 0;
		pscore = 0;
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
		Scanner z = new Scanner(System.in);
		System.out.println("Balance: " + bal);
		pscore = 0;
		dscore = 0;
		
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
				z.close();
				return false;
			}
			else
			{
				System.out.println("Dealer 2nd card: " + d2.toString());
				System.out.println("BLACKJACK");
				bal += betSize;
				z.close();
				return true;
			}
		}
		else
		{
			if (dscore == 11 && (d1.getValue() == 1 || d2.getValue() == 1))
			{
				dscore = 21;
				bal -= betSize;
				System.out.println("Dealer 2nd card: " + d2.toString());
				System.out.println("Dealer Blackjack");
				z.close();
				return false;
			}
			
			if (p1.getValue() == 1)
			{
				System.out.println("Your first card is an Ace! Type 1 to keep as one, 2 to change it to eleven");
				if (z.nextInt() == 2)
					pscore += 10;
			}
			
			if (p2.getValue() == 1)
			{
				if (pscore < 11)
				{
					System.out.println("Your second card is an Ace! Type 1 to keep as one, 2 to change it to eleven");
					if (z.nextInt() == 2)
						pscore += 10;
				}		
			}
		}
		
		
		card temporary;
		
		
		
		while (pscore < 21)
		{
			System.out.println("\nPlayer Score: " + pscore);
			System.out.println("1 - Hit, 2 - Stand");
			if (z.nextInt() == 1)
			{
				System.out.println("Your card is: ");
				temporary = drawCard(true);
				if (temporary.getValue() == 1)
				{
					if (pscore < 11)
					{
						System.out.println("Your card is an Ace! Type 1 to keep as one, 2 to change it to eleven");
						if (z.nextInt() == 2)
							pscore += 10;
					}		
				}
				pscore += temporary.getValue();
				if (pscore > 21)
				{
					System.out.println("BUSTED");
					bal -= betSize;
					z.close();
					return false;
				}
				else if (pscore == 21)
				{
					bal += betSize;
					z.close();
					return true;
				}
			}
			else
				System.out.println("\nPlayer Score: " + pscore);
				break;
			
		}
		z.close();
		
		System.out.println("Dealer 2nd card: " + d2.toString());
		
		if (dscore > pscore)
		{
			bal -= betSize;
			return false;
		}
		
		ArrayList<Integer> permutations = new ArrayList<Integer>();
		ArrayList<card> dealerCards = new ArrayList<card>();
		
		dealerCards.add(d1);
		dealerCards.add(d2);
		
		permutations.add(dscore);
		
		
		if (d1.getValue() == 1)
		{
			permutations.add(dscore + 10);
		}
		
		if (d2.getValue() == 1)
		{
			if (pscore < 11)
			{
				permutations.add(dscore + 10);
			}
			
		}
		
		boolean lessThanSeventeen = true;

		while (lessThanSeventeen)
		{
			
			for (int i = 0; i < permutations.size(); i++)
			{
				if (permutations.get(i) > pscore)
				{
					System.out.println("Dealer score: " + permutations.get(i));
					dscore = permutations.get(i);	
					bal -= betSize;
					return false;
				}
				
				if (permutations.get(i) >= 17)
				{
					lessThanSeventeen = false;
					
					int max = permutations.get(i);
					for (int j = 0; j < permutations.size(); j++)
					{
						if (permutations.get(j) > max)
							max = permutations.get(j);
					}
					
					System.out.println("Dealer score: " + max);
					dscore = max;	
					if (dscore > pscore)
					{
						bal -= betSize;
						System.out.println("Dealer wins");
						return false;
					}
					else if (dscore == pscore)
					{
						System.out.println("Push");
						return false;
					}
					else
					{
						System.out.println("Player wins");
						bal += betSize;
					}
				}
				
			}
			
			
			
			
			
		}
		return true;
	
	}

}
