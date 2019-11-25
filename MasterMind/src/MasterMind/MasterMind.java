package MasterMind;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Ron Arvanites
 * <h1>MasterMind Game</h1>
 * <p>This is usually a two-player game, one player is the <em>code maker</em>
 * and the other player is the <em>code breaker</em></p>
 *
 */

public class MasterMind {
	//Declare variables for class
	private int[] code;
	private int numberTries;
	private boolean win;
	private boolean lose;
	private boolean validInput;
	private int correctCount;
	private int[] guess;
	
	public static void main(String[] args) {
		//Creates player instance
		MasterMind player;
		player = new MasterMind();
		
		//Calls the play function for the player object
		player.play();
		
	}
	
	public MasterMind() {
		//Initializes values for variables
		code = new int[4];
		guess = new int[4];
		numberTries = 10;
		win = false;
		lose = false;
		validInput = true;
		
		//Calls the createCode function to create the code.
		createCode();
	}
	
	
	////////// Methods Needed /////////////
	/**
	 * Creates a 4 digit code with unique numbers from 0 to 9.
	 */
	//Creates a 4 digit code
	private void createCode() {
		//Creates 4 random numbers and adds them to code
		ArrayList<Integer> codeList = new ArrayList<Integer>();
        for (int i=1; i<10; i++) {
            codeList.add(new Integer(i));
        }
        Collections.shuffle(codeList);
       
        //Adds the items in codeList to the code array
        for (int i=0; i<4; i++) {
            code[i] = codeList.get(i);
        }
        
////////////// Uncomment to display the secret code when the game starts //////////////////
//        for (int codenumber:code) {
//        	System.out.println(codenumber);
//        }
///////////////////////////////////////////////////////////////////////////////////////////
        
	}

	
	
	/**
	 * Validates user's input is positive numbers from 0 to 9
	 * @param s the string to validate
	 * @return true if the string is valid or false if the string is not valid
	 */
	
	//Checks to see if the characters in the string are valid
		static boolean validate (String s) { 
			String regex = "[0-9]"; //The valid characters: 0-9
			return (Pattern.matches(regex, s));
		}
		
		
	/**
	 * Collects the user's input
	 */
	//Allows the user to input a guess
	private void collectUserInput() {
		//Initialize variables
		String inString = "";
		String[] tokens = null;
		boolean isValid = true;
		
		//Reads user's input
		Scanner in = new Scanner (System.in);
		inString = in.nextLine();
		
		//Checks if string is empty
		if (inString.trim().length() == 0){
			System.out.println("You did not enter anything. Please try again."); //Tells the user they entered an empty string
			decrementNumberTries();//Subtracts the user's tries by one
			setValidInput(false); //Sets the ValidInput variable to false
			return;
		}
		
		//If string is not empty
		if (inString.isEmpty() == false) {
			tokens = inString.split("\\s"); //Splits the inString up base on spaces and stores it in the token variable
			//Checks to see if entered characters are valid
			for (String x : tokens) {//Loops through elements in tokens
				isValid = validate(x); //Calls the validate function for each item in tokens
				
				//If one of the items in the string is invalid
				if (isValid == false ) { 
					System.out.print("The following character is not a number between 0-9:");
					System.out.println(x); //Prints the current value of x which is the invalid character
					System.out.println("Incorrect String. Please enter any string with unique numbers from 0-9");
					decrementNumberTries();//Subtracts the user's tries by one
					setValidInput(false);//Sets the ValidInput variable to false
					return;
					
				}
			}
			
			
			//Checks to see if user entered to many numbers
			if (tokens.length > 4) {
				System.out.println("You entered to many numbers. Please try again.");//Tells the user they entered to many numbers
				decrementNumberTries();//Subtracts the user's tries by one
				setValidInput(false);//Sets the ValidInput variable to false
				return;
			}
			
			//Checks to see if user entered not enough numbers
			if (tokens.length < 4) {
				System.out.println("You entered not enough numbers. Please try again.");//Tells the user they entered not enough numbers
				decrementNumberTries();//Subtracts the user's tries by one
				setValidInput(false);//Sets the ValidInput variable to false
				return;
			}
			
			//Adds the user's input numbers to guessList
			ArrayList<Integer> guessList = new ArrayList<Integer>();
			for (String token:tokens) {
				int number = Integer.parseInt(token); //Sets user's input to number
	            guessList.add(number);
	        }
			
			//Adds all the numbers in guessList to guess array
			for (int i=0; i<4; i++) {
	            guess[i] = guessList.get(i);
	        }
			
			decrementNumberTries();//Subtracts the user's tries by one
		
			//Checks to see if the user entered a number more than once
			for (int i = 0; i<4;i++) {
				for(int j = 0; j<4;j++) {
					//If number is already in the array
					if ((guess[i] == guess[j]) && (i !=j)) {
						System.out.println("You entered a number more than one. Please try again.");//Tells the user they entered a number more than once
						setValidInput(false);//Sets the ValidInput variable to false
						return;
					}else {
						setValidInput(true);//Sets the ValidInput variable to true
					}
				}
			}
		
		}
		
	}
	
	
	/**
	 * Checks user's input to see if it is correct and gives feedback
	 * 
	 */
	//Checks user's input and gives feedback
	private void checkGuessAndCode() {
		//If user's input was valid
		if (validInput == true) {
			setCorrectCount(0); //Resets correctCount to zero
			int[] code = getCode(); //Gets code array
			int[] guess = getGuess();//Get guess array
			System.out.print("Score:");
		
			//Checks the user's input to see if number is in correct spot
			for (int i=0;i<4;i++) {
			
				//If guess number is in the same position in code array
				if (guess[i] == code[i]) {
					System.out.print("W ");//Prints out B to let the user know a guessed number is in the correct place
					int newCorrectCount = correctCount + 1;//Increments the correctCount by one and sets it to the newCorrectCount
					setCorrectCount(newCorrectCount); //Sets the correctCount value
				}else{
				
					//Checks if the user's guess numbers are contained in the code array
					for (int codeNumber: code) {
						//If the user's guessed number is in the code array
					
						if (codeNumber == guess[i]) {
							System.out.print("B ");//Prints out W to let the user know a guessed number is one of the code numbers but not in the correct place
						}
					}
				}
			}
			
			System.out.println(" ");//Prints out a new line
		
		}
	}
	
	
	
	/**
	 * Starts the game loop
	 * 
	 */
	
	private void play() {
		//Describes the game and rules to the user
		System.out.println("Welcome to MasterMind.");
		System.out.println("Can you guess the unique 4 digit code I am think?");
		System.out.println("Rules:");
		System.out.println("You have 10 tries to guess the code.");
		System.out.println("B means a digit is in the correct place but not the correct posistion.");
		System.out.println("W means a digit is in the correct place and the correct posistion.");
		
		//Loop if isWin is false and isLose is false
		while (win == false && lose == false) {
			System.out.println("Enter guess...");//Tells the user to enter a guess
			collectUserInput();//Calls the collectUserInput function to allow the user to input a guess
			checkGuessAndCode();//Calls the checkGuessAndCode function to check the user's input
			isWin();//Checks to see if user won
			isLose();//Checks to see if user lost
		}
	}
	
	/**
	 * Checks to see if user won
	 * @return true if user won
	 */
	//Checks to see if the user won by seeing if correctCount is equal to 4
	private boolean isWin() {		
		correctCount = getCorrectCount();//Gets correctCount
		
		//If user guessed the code correctly
		if (correctCount == 4) {
			
			//If user guessed the code correctly on last try
			if (numberTries == 0) {
				System.out.println("Congradulations! You guessed the code on your last try.");//Tells the user they one
			}else {//If user guessed the code correctly with more than one try left
				System.out.println("Congradulations! You guessed the code with "+ numberTries + " tries left.");//Tells the user they one
			}
			return win = true;//Sets win equal to true
		}else {
			return win = false;//Sets win equal to false
		}
	}
	
	/**
	 * Checks to see if user lost
	 * @return true if user lost
	 */
	//Checks to see if user lost by checking to see if the numberTries variable is equal to zero
	private boolean isLose() {
		
		//If the user has no more tries left
		if (numberTries == 0) {
			System.out.println("You lost. Try again.");//Tells the user they lost
			System.out.println("The code was ");
			//Prints out the code
			for (int number:code) {
				System.out.print(number + " ");
			}
			return lose = true;//Sets lose to true
		}else {
			return lose = false;//Sets lose to false
		}
	}
	
	/**
	 * Decrements the user's number of tries
	 * 
	 */

	private void decrementNumberTries() {
		int triesLeft = numberTries - 1;//Decrements numberTries by 1 and sets it to triesleft
		setNumberTries(triesLeft); //Sets the numberTries equal to triesleft
		
		//Prints out user's turns left
		System.out.println(numberTries + " tries left.");
	}
	
	/**
	 * @return the numberTries
	 */
	public int getNumberTries() {
		return numberTries;
	}

	/**
	 * @return the code
	 */
	public int[] getCode() {
		return code;
	}


	/**
	 * @return the guess
	 */
	public int[] getGuess() {
		return guess;
	}
	
	
	/**
	 * @param numberTries the numberTries to set
	 */
	public void setNumberTries(int numberTries) {
		this.numberTries = numberTries;
	}

	/**
	 * @return the correctCount
	 */
	public int getCorrectCount() {
		return correctCount;
	}

	/**
	 * @param correctCount the correctCount to set
	 */
	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}

	/**
	 * @return the validInput
	 */
	public boolean isValidInput() {
		return validInput;
	}

	/**
	 * @param validInput the validInput to set
	 */
	public void setValidInput(boolean validInput) {
		this.validInput = validInput;
	}
	
	

}

