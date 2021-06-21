import java.util.Scanner;

/**
* The Main program collects input and uses the Yahtzee Class.
*
* @author  Marcus A. Mosley
* @version 1.0
* @since   2021-06-17
*/
public class Main implements YahtzeeConstants {
  /**
  * The Main method receives input and checks viability.
  */
  public static void main(String[] args)throws Exception {
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome to Yahtzee!");
    
    boolean done = false;
    int numPlayers = 0;
    
    while (true) {
      try {
        System.out.print("\nHow many players are to be included (Max: 4)? ");
        numPlayers = input.nextInt();
        if (numPlayers > MAX_PLAYERS) {
          System.out.println("\nThe Max Number of Players is 4!");
          continue;
        }
        break;
      } catch (Exception e) {
        System.out.print("\nThat is not valid input! Please input an integer! ");
      }
    }
    
    Yahtzee[] playerArray = new Yahtzee[numPlayers];
    int[] scoreArray = new int[numPlayers];
    int count = 0;
    
    Scanner nameInput = new Scanner(System.in);
    for (count = 0; count < numPlayers; count++) {
      System.out.print("\nWhat is the player " + (count + 1) + "'s name? ");
      String name = nameInput.nextLine();
      playerArray[count] = new Yahtzee(name);
      System.out.println("\nPlayer " + (count + 1) + ": " + playerArray[count].name);
    }
    
    while (true) {
      for (count = 0; count < numPlayers; count++) {
        if (playerArray[count].playTurn()) {
          for (int counter = 0; counter < numPlayers; counter++) {
            scoreArray[counter] = playerArray[counter].getTotal();
          }
          done = true;
          break;
        }
      }
      if (done) {
        break;
      }
    }
    
    winner(playerArray, scoreArray);
  }
  
  /**
  * The checkWinner method prints the winner of the match and their score.
  */
  private static void winner(Yahtzee[] playerArray, int[] scoreArray) {
    int max = -1;
    int maxLoc = -1;
    
    for (int counter = 0; counter < scoreArray.length; counter++) {
      if (scoreArray[counter] > max) {
        max = scoreArray[counter];
        maxLoc = counter;
      }
    }
    
    System.out.println("\n" + playerArray[maxLoc].name + " has won, with a total of " + max
        + " points!");
    System.out.println("\nDone!");
  }
}
