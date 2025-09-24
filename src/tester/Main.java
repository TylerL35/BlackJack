package tester;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		
		
		System.out.println("How many decks?");
		

		Scanner s = new Scanner(System.in);
		blackjackdealer bjd = new blackjackdealer(s.nextInt(), 100, s);
		
		while(bjd.isActive())
		{
			
			String response = new String();
			
			
			System.out.println("Actions: A-draw, B-shuffle, C-play round");
			response = s.next();
			
			
			if (response.equalsIgnoreCase("A"))
			{
				bjd.drawCard(true);
			}
			else if (response.equalsIgnoreCase("B"))
			{
				bjd.shuffle();
			}
			else if (response.equalsIgnoreCase("C"))
			{
				if (bjd.getDeckSize() < 10)
					System.out.println("Not enough cards. Please shuffle to play");
				else
					bjd.dealRound(1);
			}
			
			
			System.out.println("--------------------");
		}
		
		
		s.close();
		
	}
		
		
	
}
