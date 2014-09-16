// The "Poker_game" class.
import java.awt.*;
import hsa.Console;
import java.io.*;
import javax.imageio.*; // allows image loading

public class Poker_gamefinal
{
    static Console c;           // The output console
    public static Image loadImage (String name)  //loads image from file
    {
	Image img = null;
	try
	{
	    img = ImageIO.read (new File (name)); // load file into Image object
	}
	catch (IOException e)
	{
	    System.out.println ("File not found");
	}
	return img;
    }


    public static Image[] loadCardImages (boolean show)  // loads card gif files into array
    {
	Image pictureDeck[] = new Image [52]; // create array of 52 card images
	if (show)
	{
	    for (int x = 0 ; x <= 51 ; x++)
	    {
		pictureDeck [x] = loadImage ("cards2\\" + (x + 1) + ".gif"); // loads appropriate image from cards2 folder
		//   if(x==53)
		//     pictureDeck [x] = loadImage ("cards2\\" + ('b') + ".gif");//loads back of cards
	    }
	}
	if (!show)
	{
	    for (int x = 0 ; x <= 51 ; x++)
	    {
		pictureDeck [x] = loadImage ("cards2\\b.gif"); // loads appropriate image from cards2 folder
		//   if(x==53)
		//     pictureDeck [x] = loadImage ("cards2\\" + ('b') + ".gif");//loads back of cards
	    }
	}
	return pictureDeck;
    }


    public static Image[] hideCardImages ()  // loads card gif files into array
    {
	Image pictureDeck[] = new Image [52]; // create array of 52 card images
	for (int x = 0 ; x <= 51 ; x++)
	{
	    pictureDeck [x] = loadImage ("cards2\\b.gif"); // loads appropriate image from cards2 folder
	    //   if(x==53)
	    //     pictureDeck [x] = loadImage ("cards2\\" + ('b') + ".gif");//loads back of cards
	}
	return pictureDeck;
    }


    public static void show (int deck[], Image imageDeck[])
    {
	c.clear ();
	Color board = new Color (0, 149, 0);
	c.setColor (board);
	c.fillRect (0, 0, 2000, 500);
	for (int x = 0 ; x < deck.length ; x++) //go through deck
	{
	    c.drawImage (imageDeck [deck [x]], 20 * x % 260 + 150, 50 * (x / 13) + 100, null); // % and / create four rows of cards for display
	}
	c.getChar ();
    }


    public static void showHand (int hand[], boolean side, int x, int y, Image imageDeck[])
    {
	
	if (side)
	{
	    for (int i = 0 ; i < hand.length ; i++) //go through hand
	    {
		c.drawImage (imageDeck [hand [i]], 20 * i % 260 + x, 50 * (i / 13) + y, null);//draws hand
	    }
	}
	else if (!side)//same as above 
	{
	    for (int i = 0 ; i < hand.length ; i++) 
	    {
		c.drawImage (imageDeck [hand [i]], 20 * i % 260 + x, 50 * (i / 13) + y, null);
	    }
	}

	//c.getChar ();
    }




    public static int[] initDeck ()  // initialize standard deck
    {
	int deck[] = new int [52];
	for (int x = 0 ; x < deck.length ; x++)
	    deck [x] = x; // cards represented by numbers 0-51
	return deck;
    }


    public static int[] shuffle (int deck[])  //I re-purposed the 'swap' method for shuffling
    {
	for (int x = 0 ; x < deck.length ; x++) //go through deck
	{
	    int num1 = (int) ((Math.random () * 52) + 1) - 1; // choose two random numbers between
	    int num2 = (int) ((Math.random () * 52) + 1) - 1; //0 - 51
	    if (num1 >= 0 && num1 < deck.length && num2 >= 0 && num2 < deck.length) //error check/safety net
	    {
		//I am swapping the two values that are in the randomly chosen positions of the deck
		//this is true to real life shuffling because in real life you are actually just swapping
		//the position of the cards
		int temp = deck [num1];
		deck [num1] = deck [num2];
		deck [num2] = temp;
	    }
	}
	return deck;
    }


    public static int[] hand (int deck[])
    {
	int hand[] = new int [5];//creates a hand of 5 cards from first 5 cards of deck
	for (int x = 0 ; x < hand.length ; x++)
	    hand [x] = deck [x];

	return hand;
    }


    public static int[] deckHandResize (int deck[], int hand_length)
    {
	int newdeck[] = new int [deck.length - hand_length];
	for (int x = 0 ; x < newdeck.length ; x++)//resizes deck after cards have been taken from deck for hands
	    newdeck [x] = deck [x + hand_length];
	return newdeck;
    }


    public static int[] sort (int deck[])  // sorts the deck
    {
	int temp;
	for (int x = 0 ; x < deck.length - 1 ; x++) //goes through all the cards in the deck but the last
	{
	    int lowPos = x;
	    for (int y = x + 1 ; y < deck.length ; y++) //goes through deck but one position ahead of previous 'for' loop
	    {
		if (deck [x] > deck [y]) //if preceeding value is greater than any of its following values then swap them
		{
		    temp = deck [x];
		    deck [x] = deck [y];
		    deck [y] = temp;
		}
	    }
	}
	return deck; //return sorted deck
    }



    public static int checkRank (int hand[])
    {//driver method for evaluating poker hand
	hand = sort (hand);
	if (royalFlush (hand))
	    return 10;
	if (straightFlush (hand))
	    return 9;
	if (fourOfAKind (hand))
	    return 8;
	if (fullHouse (hand))
	    return 7;
	if (flush (hand))
	    return 6;
	if (straight (hand))
	    return 5;
	if (threeOfAKind (hand))
	    return 4;
	if (twoPairs (hand))
	    return 3;
	if (pair (hand))
	    return 2;
	else
	    return 1;

    }


    public static boolean royalFlush (int[] hand)
    {
	int total = 0;//royal flush
	for (int x = 0 ; x < hand.length ; x++)
	    total += hand [x];//accumulates hand total
	if (total == 245 || total == 180 || total == 115 || total == 50)//if total is equal to said values it is a royal flush
	    if (flush (hand))//if it is also a flush then it is a royal flush for sure
		return true;
	return false;
    }


    public static boolean straightFlush (int[] hand)
    {
	boolean check = true;
	for (int x = 0 ; x < hand.length - 2 ; x++)
	    if (hand [x] != hand [x + 1] - 1)//checks if next card is one value less than itself
		check = false;

	return check;

    }


    public static boolean fourOfAKind (int[] hand)
    {
	int count = 0;
	for (int x = 0 ; x < 13 ; x++)
	{
	    count = 0;
	    for (int y = 0 ; y < hand.length ; y++)
		if (hand [y] % 13 == x)//if the modulus 13 of all the cards in the hand are equal then you have four of a kind
		    count++;
	    if (count == 4)//makes sure that there are 4 of a kind (not 1,2,3, or 5 b/c that would not be 4 of a kind)
		return true;
	}

	return false;

    }


    public static boolean fullHouse (int[] hand)
    {
	int count = 0, countTemp = 0, temp_index = 0;
	int temp[] = new int [100];
	for (int x = 0 ; x < 13 ; x++)//everything contained in this loop checks to see if there are 3 of the same cards in the hand
	{
	    count = 0;
	    temp_index = 0;
	    for (int y = 0 ; y < hand.length ; y++)
	    {
		if (hand [y] % 13 == x)
		    count++;
		else
		{//if any cards are not the same then they are put into another array to be looked at later
		    temp [temp_index] = hand [y];
		    temp_index++;
		}

	    }
	    if (count == 3)//if 3 like cards are found (based off of modulus 13 value) then exit loop
		break;
	}
	int newTemp[] = new int [temp_index];
	for (int x = 0 ; x < newTemp.length ; x++)
	    newTemp [x] = temp [x];//fills new array with remaining hand values
	for (int x = 0 ; x < 13 ; x++)
	{
	    countTemp = 0;
	    for (int y = 0 ; y < newTemp.length ; y++)
	    {
		if (newTemp [y] % 13 == x)//if 2 like cards are found (based off of modulus 13 value) then exit loop
		    countTemp++;
		if (countTemp == 2)
		    break;
	    }
	    if (countTemp == 2)
		break;
	}
	if (count == 3 && countTemp == 2)//if full house then return TRUE 
	    return true;

	else
	    return false;
    }


    public static boolean flush (int[] hand)
    {
	boolean check = true;
	//check for spade
	if (hand [0] >= 39 && hand [0] <= 51)
	{
	    for (int x = 0 ; x < hand.length ; x++)
		if (hand [x] < 39 || hand [x] > 51)
		    check = false;
	    return check;
	}
	//check for hearts
	if (hand [0] >= 26 && hand [0] <= 38)
	{
	    for (int x = 0 ; x < hand.length ; x++)
		if (hand [x] < 26 || hand [x] > 38)
		    check = false;
	    return check;
	}
	//check for diamonds
	if (hand [0] >= 13 && hand [0] <= 25)
	{
	    for (int x = 0 ; x < hand.length ; x++)
		if (hand [x] < 13 || hand [x] > 25)
		    check = false;
	    //return check;
	}
	//check for clubs
	if (hand [0] >= 0 && hand [0] <= 12)
	{
	    for (int x = 0 ; x < hand.length ; x++)
		if (hand [x] < 0 || hand [x] > 12)
		    check = false;
	    return check;
	}
	return check;


    }


    public static boolean straight (int[] hand)
    {
	boolean check = true;
	int temp;
	for (int x = 0 ; x < hand.length - 1 ; x++) //goes through all the cards in the deck but the last
	{
	    int lowPos = x;
	    for (int y = x + 1 ; y < hand.length ; y++) //goes through deck but one position ahead of previous 'for' loop
	    {
		if (hand [x] % 13 > hand [y] % 13) //if preceeding value is greater than any of its following values then swap them
		{
		    temp = hand [x];
		    hand [x] = hand [y];
		    hand [y] = temp;
		}
	    }
	}
	for (int x = 0 ; x < hand.length - 1 ; x++)
	    if (hand [x] % 13 != (hand [x + 1] % 13) - 1)
		check = false;

	return check;

    }




    public static boolean threeOfAKind (int[] hand)
    {
	int count = 0;
	for (int x = 0 ; x < 13 ; x++)
	{
	    count = 0;
	    for (int y = 0 ; y < hand.length ; y++)
		if (hand [y] % 13 == x)//checks if 3 cards have the same modulus 13 value 
		    count++;
	    if (count == 3)//if they(^) do then it is a THREE OF A KIND
		return true;
	}

	return false;


    }


    public static boolean twoPairs (int[] hand)
    {
	int count = 0, countTemp = 0, temp_index = 0;
	int temp[] = new int [100];
	for (int x = 0 ; x < 13 ; x++)//checks for a pair
	{
	    count = 0;
	    temp_index = 0;
	    for (int y = 0 ; y < hand.length ; y++)
	    {
		if (hand [y] % 13 == x)
		    count++;
		else
		{
		    temp [temp_index] = hand [y];
		    temp_index++;
		}

	    }
	    if (count == 2)
		break;
	}
	int newTemp[] = new int [temp_index];//stores remainder of the hand in a new array
	for (int x = 0 ; x < newTemp.length ; x++)
	    newTemp [x] = temp [x];
	for (int x = 0 ; x < 13 ; x++)//checks for another pair
	{
	    countTemp = 0;
	    for (int y = 0 ; y < newTemp.length ; y++)
	    {
		if (newTemp [y] % 13 == x)
		    countTemp++;
		if (countTemp == 2)
		    break;
	    }
	    if (countTemp == 2)
		break;
	}
	if (count == 2 && countTemp == 2)//if two pairs found then return true
	    return true;

	else
	    return false;
    }


    public static boolean pair (int[] hand)
    {
	int count = 0;
	for (int x = 0 ; x < 13 ; x++)//checks for a pair
	{
	    count = 0;
	    for (int y = 0 ; y < hand.length ; y++)
		if (hand [y] % 13 == x)//by looking at the result of the cards modulus 13 value
		    count++;
	    if (count == 2)
		return true;
	}

	return false;


    }


    public static int[] sortDescending (int deck[])
    {
	int temp;
	for (int x = 0 ; x < deck.length - 1 ; x++) //goes through all the cards in the deck but the last
	{
	    int lowPos = x;
	    for (int y = x + 1 ; y < deck.length ; y++) //goes through deck but one position ahead of previous 'for' loop
	    {
		if (deck [x] < deck [y]) //if preceeding value is greater than any of its following values then swap them
		{
		    temp = deck [x];
		    deck [x] = deck [y];
		    deck [y] = temp;
		}
	    }
	}
	return deck; //return sorted deck
    }


    public static void main (String[] args)
    {
	c = new Console ();
	Color board = new Color (0, 149, 0);
	c.setColor (board);
	c.fillRect (0, 0, 2000, 500);
	Image imageDeck[] = loadCardImages (true); //Calls the method to load pictures from file and stores in the array imageDeck
	Image hideDeck[] = hideCardImages ();
	int deck[] = initDeck (); //initializes deck
	show (deck, imageDeck); //shows the deck
	deck = shuffle (deck); //shuffles deck
	show (deck, imageDeck); //shows the shuffled deck
      
	c.print ("What is your Username? ");
	String username = c.readLine (); //asks for username

	c.clear ();
	Font f3 = new Font ("ROARINGFIRE", Font.BOLD, 15);
	c.setFont (f3);
	c.setColor (board);
	c.fillRect (0, 0, 2000, 500);
	c.setColor (Color.black);
	int user[] = hand (deck); //creates user hand
	deck = deckHandResize (deck, user.length); //resizes deck without user hand
	c.drawString (username, 290, 395); //shows user name
	showHand (user, true, 250, 400, imageDeck); //shows user hand

	int comp1[] = hand (deck); //creates first opponents hand
	deck = deckHandResize (deck, comp1.length); //resizes deck without opponents hand
	c.drawString ("Comp1 ", 428, 250); //displays username
	if (username.equals ("developer")) //if user name is developer then it will run the program showing only the user's cards
	{
	    imageDeck = loadCardImages (true);
	    showHand (comp1, true, 480, 200, imageDeck); //shows opponent's cards to user
	}
	else
	    imageDeck = loadCardImages (false);
	showHand (comp1, false, 480, 200, imageDeck); //shows back of opponent's cards to user

	int num = checkRank (user);//checks rank of user's hand
	int num1 = checkRank (comp1);//checks rank of opponent's hand

	int comp2[] = hand (deck); //create second opponent's hand
	deck = deckHandResize (deck, comp2.length); //resize deck
	c.drawString ("Comp2 ", 265, 110);
	if (username.equals ("developer")) //if username is developer then show back of cards for opponents and deck and only show user their own cards
	{
	    imageDeck = loadCardImages (true);
	    showHand (comp2, true, 225, 0, imageDeck); //shows back of opponent's cards
	}
	else
	    imageDeck = loadCardImages (false); //shows opponent's cards
	showHand (comp2, false, 225, 0, imageDeck); //shows opponent's cards

	int num2 = checkRank (comp2);//checks rank of opponent's hand
       
	int comp3[] = hand (deck);
	deck = deckHandResize (deck, comp3.length);
	c.drawString ("Comp3 ", 155, 250);
	if (username.equals ("developer"))
	{
	    imageDeck = loadCardImages (true);
	    showHand (comp3, true, 0, 200, imageDeck);
	}
	else
	    imageDeck = loadCardImages (false);
	showHand (comp3, false, 0, 200, imageDeck);
	
	int num3 = checkRank (comp3);//checks rank of opponent's hand
	int order[] = {num, num1, num2, num3};//puts all the ranks in an array
	order = sortDescending (order);//sorts the array in descending order essentially putting the wining hand in the front
	c.getChar ();
	c.clear ();
	Font f2 = new Font ("ROARINGFIRE", Font.BOLD, 10);
	c.setFont (f2);
	c.setCursor (10, 21);
	for (int x = 0 ; x <= 3 ; x++)//tells you what kind of poker hand you have based off of the checkRank method's return values
	{
	    int rank = 0;
	    String name = "NONAME";
	    if (x == 0)
	    {
		rank = num;
		name = username;
	    }
	    if (x == 1)
	    {
		rank = num1;
		name = "Comp1";
	    }
	    if (x == 2)
	    {
		rank = num2;
		name = "Comp2";
	    }
	    if (x == 3)
	    {
		rank = num3;
		name = "Comp3";
	    }
	    c.setCursor (10 + x, 21);
	    if (rank == 10)
		c.println (name + "'s hand was a royal flush ");
	    else if (rank == 9)
		c.println (name + "'s hand was a straight flush ");
	    else if (rank == 8)
		c.println (name + "'s hand was a four of a kind ");
	    else if (rank == 7)
		c.println (name + "'s hand was a full house ");
	    else if (rank == 6)
		c.println (name + "'s hand was a flush ");
	    else if (rank == 5)
		c.println (name + "'s hand was a straight ");
	    else if (rank == 4)
		c.println (name + "'s hand was a three of a kind ");
	    else if (rank == 3)
		c.println (name + "'s hand was a two pair ");
	    else if (rank == 2)
		c.println (name + "'s hand was a pair ");
	    else if (rank == 1)
		c.println (name + "'s hand was not a poker hand ");

	}
	c.setCursor (16, 21);
	if (order [0] == num)//checks if user was the winner
	{
	    if (order [0] != order [1])
		c.println (username + " is the winner!");
	    else//checks if there was a tie with any other player
	    {
		c.print ("It is a tie between " + username + " and ");
		if (order [1] == num1)
		{
		    if (order [1] != order [2])
			c.println ("Comp1");
		    else
		    {
			c.print ("Comp1 and ");
			if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp2 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp2 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num2)
		{
		    if (order [1] != order [2])
			c.println ("Comp2");
		    else
		    {
			c.print ("Comp2 and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num3)
		{
		    if (order [1] != order [2])
			c.println ("Comp3");
		    else
		    {
			c.print ("Comp3 and ");
			if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp2 and Comp1");
			    }
			}
			else if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp2 and Comp1");
			    }
			}
		    }
		}

	    }
	}

	else if (order [0] == num1)//checks if computer one is the winner
	{
	    if (order [0] != order [1])
		c.println ("Comp1 is the winner!");
	    else
	    {//checks if there were any ties
		c.print ("It is a tie between Comp1 and ");
		if (order [1] == num)
		{
		    if (order [1] != order [2])
			c.println (username);
		    else
		    {
			c.print (username + " and ");
			if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp2 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp2 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num2)
		{
		    if (order [1] != order [2])
			c.println ("Comp2");
		    else
		    {
			c.print ("Comp2 and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num3)
		{
		    if (order [1] != order [2])
			c.println ("Comp3");
		    else
		    {
			c.print ("Comp3 and ");
			if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp2 and Comp1");
			    }
			}
			else if (order [2] == num)
			{
			    if (order [2] != order [3])
				c.println (username);
			    else
			    {
				c.print ("Comp2 and" + username);
			    }
			}
		    }
		}

	    }
	}

	else if (order [0] == num2)//checks if computer 2 is the winner
	{
	    if (order [0] != order [1])
		c.println ("Comp2 is the winner!");
	    else//checks for any ties
	    {
		c.print ("It is a tie between Comp2 and ");
		if (order [1] == num)
		{
		    if (order [1] != order [2])
			c.println (username);
		    else
		    {
			c.print (username + " and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num)
		{
		    if (order [1] != order [2])
			c.println (username);
		    else
		    {
			c.print (username + "and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
			else if (order [2] == num3)
			{
			    if (order [2] != order [3])
				c.println ("Comp3");
			    else
			    {
				c.print ("Comp1 and Comp3");
			    }
			}
		    }
		}
		else if (order [1] == num3)
		{
		    if (order [1] != order [2])
			c.println ("Comp3");
		    else
		    {
			c.print ("Comp3 and ");
			if (order [2] == num)
			{
			    if (order [2] != order [3])
				c.println (username);
			    else
			    {
				c.print (username + " and Comp1");
			    }
			}
			else if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print (username + " and Comp1");
			    }
			}
		    }
		}

	    }
	}

	else if (order [0] == num3)//checks if computer 3 is the winner
	{
	    if (order [0] != order [1])
		c.println ("Comp3 is the winner!");
	    else//checks for any ties
	    {
		c.print ("It is a tie between Comp3 and ");
		if (order [1] == num)
		{
		    if (order [1] != order [2])
			c.println (username);
		    else
		    {
			c.print (username + " and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp2");
			    }
			}
			else if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp1 and Comp2");
			    }
			}
		    }
		}
		else if (order [1] == num)
		{
		    if (order [1] != order [2])
			c.println (username);
		    else
		    {
			c.print (username + "and ");
			if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print ("Comp1 and Comp2");
			    }
			}
			else if (order [2] == num2)
			{
			    if (order [2] != order [3])
				c.println ("Comp2");
			    else
			    {
				c.print ("Comp1 and Comp2");
			    }
			}
		    }
		}
		else if (order [1] == num2)
		{
		    if (order [1] != order [2])
			c.println ("Comp2");
		    else
		    {
			c.print ("Comp2 and ");
			if (order [2] == num)
			{
			    if (order [2] != order [3])
				c.println (username);
			    else
			    {
				c.print (username + " and Comp1");
			    }
			}
			else if (order [2] == num1)
			{
			    if (order [2] != order [3])
				c.println ("Comp1");
			    else
			    {
				c.print (username + " and Comp1");
			    }
			}
		    }
		}

	    }
	}


	else if (order [0] == num1 && order [0] != order [1])//declares the winner
	    c.println (username + " is the winner!");
	    
	    
	imageDeck = loadCardImages (true);
	showHand (user, true, 250, 400, imageDeck); //shows user hand
	showHand (comp1, true, 480, 200, imageDeck); //shows opponent's cards to user
	showHand (comp2, true, 225, 0, imageDeck); 
	showHand (comp3, true, 0, 200, imageDeck);

	c.drawString (username, 290, 395); //shows user name
	c.drawString ("Comp1 ", 483, 195); //displays username
	c.drawString ("Comp2 ", 265, 110);
	c.drawString ("Comp3 ", 98, 195);

	
	c.getChar ();
	//final exiting screen
	c.clear ();
	Font f1 = new Font ("ROARINGFIRE", Font.BOLD, 40);
	c.setFont (f1);
	c.drawString ("Thanks For Playing!!", 150, 270);
	c.getChar ();
	c.close (); //closes console and by extention the program

    } // main method
} // Poker_game class


