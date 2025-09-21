package tester;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		
		System.out.println("How many decks?");
		

		blackjackdealer bjd = new blackjackdealer(s.nextInt(), 100);
		
		while(bjd.isActive())
		{
			System.out.println("Actions: A-draw, B-shuffle, C-play round\n");
			String response = s.next();
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
