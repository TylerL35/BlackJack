package tester;
import java.util.ArrayList;
import java.util.Collections;

public class deck {
	
	private ArrayList<card> inDeck = new ArrayList<card>();
	private ArrayList<card> inMuck = new ArrayList<card>();
	
	public deck() //creates ordered deck
	{
		for (int i = 1; i <= 13; i++)
		{
			for (int j = 1; j <=4; j++)
			{
				inDeck.add(new card(i, j));
			}
		}
	}
	
	public int getDeckSize()
	{
		return inDeck.size();
	}
	
	public int getMuckSize()
	{
		return inMuck.size();
	}
	
	public boolean isEmpty()
	{
		if (inDeck.size() > 0)
			return false;
		else 
			return true;
	}
	
	public card drawCard(boolean readOut)
	{
		card temp = inDeck.get(0);
		if(readOut)
			System.out.println(temp.toString());
		inMuck.add(temp);
		inDeck.remove(0);
		return temp;
	}
	
	public void shuffle()
	{
		if (inMuck.size() > 0)
		{
			inDeck.addAll(inMuck);
			inMuck.clear();
		}
		
		Collections.shuffle(inDeck);
	}
}
