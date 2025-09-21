package tester;

public class card {
	
	private int value;
	private int suite;
	//1 = Spades 2 = Clubs 3 = Hearts 4 = Diamonds
	
	public card(int value, int suite)
	{
		this.value = value;
		this.suite = suite;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getSuite()
	{
		return suite;
	}
	
	public String toString()
	{
		return printValue() + " of " + printSuite();
	}
	
	private String printValue()
	{
		if (value > 1 && value < 11)
		{
			return(Integer.toString(value));
		}
		else if (value == 1)
			return "Ace";
		else if (value == 11)
			return "Jack";
		else if (value == 12)
			return "Queen";
		else if (value == 13)
			return "King";
		else 
			return "Error";
	}
	
	private String printSuite()
	{
		if (suite == 1)
			return "Spades";
		else if (suite == 2)
			return "Clubs";
		else if (suite == 3)
			return "Hearts";
		else if (suite == 4)
			return "Diamonds";
		else
			return "Error";
	}
	
}
