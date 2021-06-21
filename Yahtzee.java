import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
* This class creates a Yahtzee Player.
*
* @author  Marcus A. Mosley
* @version 1.0
* @since   2021-06-16
*/
public class Yahtzee implements YahtzeeConstants {
  String name = "";
  private String category = "";
  private int[] dice = new int[N_DICE];
  private int[] diceList = {0, 0, 0, 0, 0, 0};
  private Hashtable<String, Integer> scoreTable = new Hashtable<String, Integer>();
  
  // Constructors
  
  /**
  * The Yahtzee method constructs a yahtzee player.
  */
  public Yahtzee(String playerName) {
    this.name = playerName;
    
    // Upper Section
    scoreTable.put("ONES", -1);
    scoreTable.put("TWOS", -1);
    scoreTable.put("THREES", -1);
    scoreTable.put("FOURS", -1);
    scoreTable.put("FIVES", -1);
    scoreTable.put("SIXES", -1);
    
    // Lower Section
    scoreTable.put("THREE_OF_A_KIND", -1);
    scoreTable.put("FOUR_OF_A_KIND", -1);
    scoreTable.put("FULL_HOUSE", -1);
    scoreTable.put("SM_STRAIGHT", -1);
    scoreTable.put("LG_STRAIGHT", -1);
    scoreTable.put("YAHTZEE", -1);
    scoreTable.put("CHANCE", -1);
    scoreTable.put("BONUS", 0);
  }
  
  // Methods
  
  /**
  * The generateDie method provides a pseudo-random die roll.
  */
  private static int generateDie() {
    Random rand = new Random();
    return rand.nextInt(6) + 1;
  }
  
  /**
  * The printDice method prints the 5 Die Rolls.
  */
  private void printDice() {
    for (int count = 0; count < N_DICE; count++) {
      System.out.print(dice[count] + " ");
    }
    System.out.print("\n");
  }
  
  /**
  * The rollDice method handles the rolling and rerolling of dice.
  */
  private void rollDice() {
    Scanner wait = new Scanner(System.in);
    System.out.print("\n" + name + "'s turn! Press enter to roll the dice!. ");
    wait.nextLine();
    
    for (int count = 0; count < N_DICE; count++) {
      dice[count] = generateDie();
    }
    printDice();
    
    for (int k = 0; k < 2; k++) {
      while (true) { 
        try {
          Scanner reroll = new Scanner(System.in);
          System.out.print("\nSelect the dice you wish to re-roll seperated by ', '"
              + " (Type -1 for none): ");
          String rerollString = reroll.nextLine();
          String[] rerollArray = rerollString.split(", ");
          for (int count = 0; count < rerollArray.length; count++) {
            if (Integer.parseInt(rerollArray[count]) == -1) {
              break;
            } else {
              dice[Integer.parseInt(rerollArray[count]) - 1] = generateDie();
            }
          }
          break;
        } catch (Exception e) {
          System.out.println("\nNot a die, please enter a number between 1-6!");
        }
      }
      printDice();
    }
  }
  
  /**
  * The totalDice method returns the total of all five dice.
  */
  private int totalDice() {
    int total = 0;

    for (int count = 0; count < N_DICE; count++) {
      total += dice[count];
    }
    return total;
  }
  
  /**
  * The addPoints method adds the proper points to the proper category.
  */
  private void addPoints() {
    if (scoreTable.get(category) == -1) {
      scoreTable.put(category, 0);
    }
    
    if (category.contains("UPPER")) {
      if (dice[0] == 1) {
        scoreTable.put("ONES", dice[0] * 5);
      } else if (dice[0] == 2) {
        scoreTable.put("TWOS", dice[0] * 5);
      } else if (dice[0] == 3) {
        scoreTable.put("THREES", dice[0] * 5);
      } else if (dice[0] == 4) {
        scoreTable.put("FOURS", dice[0] * 5);
      } else if (dice[0] == 5) {
        scoreTable.put("FIVES", dice[0] * 5);
      } else if (dice[0] == 6) {
        scoreTable.put("SIXES", dice[0] * 5);
      }
    } else if (category.contains("ONES")) {
      scoreTable.put(category, diceList[0]);
    } else if (category.contains("TWOS")) {
      scoreTable.put(category, diceList[1] * 2);
    } else if (category.contains("THREES")) {
      scoreTable.put(category, diceList[2] * 3);
    } else if (category.contains("FOURS")) {
      scoreTable.put(category, diceList[3] * 4);
    } else if (category.contains("FIVES")) {
      scoreTable.put(category, diceList[4] * 5);
    } else if (category.contains("SIXES")) {
      scoreTable.put(category, diceList[5] * 6);
    } else if (category.contains("THREE_OF_A_KIND")) {
      scoreTable.put(category, totalDice());
    } else if (category.contains("FOUR_OF_A_KIND")) {
      scoreTable.put(category, totalDice());
    } else if (category.contains("FULL_HOUSE")) {
      scoreTable.put(category, FULL_HOUSE);
    } else if (category.contains("SM_STRAIGHT")) {
      scoreTable.put(category, SM_STRAIGHT);
    } else if (category.contains("LG_STRAIGHT")) {
      scoreTable.put(category, LG_STRAIGHT);
    } else if (category.contains("YAHTZEE")) {
      if (scoreTable.get("YAHTZEE") != -1) {
        System.out.print(scoreTable.get("YAHTZEE"));
        if (scoreTable.get("YAHTZEE") != 0) {
          scoreTable.put("BONUS", scoreTable.get("BONUS") + YAHTZEE_BONUS);
        }
        
        Scanner input = new Scanner(System.in);
        while (true) {
          System.out.print("You can now select a new category to place your extra points!");
          System.out.print("\nPlease input 'Upper' for anything in the Upper Section, Yahtzee"
              + " is not valid! ");
          category = input.nextLine();
          category = category.toUpperCase();
    
          if (category.contains("UPPER") || category.contains("THREE_OF_A_KIND") 
              || category.contains("FOUR_OF_A_KIND") || category.contains("FULL_HOUSE") 
              || category.contains("SM_STRAIGHT") || category.contains("LG_STRAIGHT")
              || category.contains("CHANCE")) {
            if (scoreTable.containsKey(category)) {
              if (scoreTable.get(category) == -1) {
                System.out.println("\nThat category has already been used!");
                continue;
              } else {
                break;
              }
            } else {
              break;
            }
          }
        }
        addPoints();
      } else {
        scoreTable.put(category, YAHTZEE);
      }
    } else if (category.contains("CHANCE")) {
      scoreTable.put(category, totalDice());
    }
  }
  
  /**
  * The checkCategory method ensures that the user has the correct roll for a given category.
  */
  private boolean checkCategory() {
    
    
    for (int count = 0; count < diceList.length; count++) {
      diceList[count] = 0;
    }
    
    for (int count = 0; count < N_DICE; count++) {
      if (dice[count] == 1) {
        diceList[0]++;
      } else if (dice[count] == 2) {
        diceList[1]++;
      } else if (dice[count] == 3) {
        diceList[2]++;
      } else if (dice[count] == 4) {
        diceList[3]++;
      } else if (dice[count] == 5) {
        diceList[4]++;
      } else if (dice[count] == 6) {
        diceList[5]++;
      }
    }
    
    if (category.contains("ONES")) {
      if (diceList[0] > 0) { 
        return true;
      }
    } else if (category.contains("TWOS")) {
      if (diceList[1] > 0) { 
        return true;
      }
    } else if (category.contains("THREES")) {
      if (diceList[2] > 0) { 
        return true;
      }
    } else if (category.contains("FOURS")) {
      if (diceList[3] > 0) { 
        return true;
      }
    } else if (category.contains("FIVES")) {
      if (diceList[4] > 0) { 
        return true;
      }
    } else if (category.contains("SIXES")) {
      if (diceList[5] > 0) { 
        return true;
      }
    } else if (category.contains("THREE_OF_A_KIND")) {
      if (diceList[0] >= 3 || diceList[1] >= 3 || diceList[2] >= 3 || diceList[3] >= 3 
          || diceList[4] >= 3 || diceList[5] >= 3) {
        return true;
      }
    } else if (category.contains("FOUR_OF_A_KIND")) {
      if (diceList[0] >= 4 || diceList[1] >= 4 || diceList[2] >= 4 || diceList[3] >= 4 
          || diceList[4] >= 4 || diceList[5] >= 4) {
        return true;
      }
    } else if (category.contains("FULL_HOUSE")) {
      if ((diceList[0] == 3 || diceList[1] == 3 || diceList[2] == 3 || diceList[3] == 3 
          || diceList[4] == 3 || diceList[5] == 3) && (diceList[0] == 2 || diceList[1] == 2 
          || diceList[2] == 2 || diceList[3] == 2 || diceList[4] == 2 || diceList[5] == 2)) {
        return true;
      }
    } else if (category.contains("SM_STRAIGHT")) {
      if ((diceList[0] >= 1 && diceList[1] >= 1 && diceList[2] >= 1 && diceList[3] >= 1)
          || (diceList[1] >= 1 && diceList[2] >= 1 && diceList[3] >= 1 && diceList[4] >= 1)
          || (diceList[2] >= 1 && diceList[3] >= 1 && diceList[4] >= 1 && diceList[5] >= 1)) {
        return true;
      }
    } else if (category.contains("LG_STRAIGHT")) {
      if ((diceList[0] == 1 && diceList[1] == 1 && diceList[2] == 1 && diceList[3] == 1 
          && diceList[4] == 1)
          || (diceList[1] == 1 && diceList[2] == 1 && diceList[3] == 1 && diceList[4] == 1 
          && diceList[5] == 1)) {
        return true;
      }
    } else if (category.contains("YAHTZEE")) {
      if (diceList[0] == 5 || diceList[1] == 5 || diceList[2] == 5 || diceList[3] == 5 
          || diceList[4] == 5 || diceList[5] == 5) {
        return true;
      }
    } else if (category.contains("CHANCE")) {
      return true;
    } else {
      System.out.print("Else");
    }
    
    return false;
  }
  
  /**
  * The selectCategory method handles the selection of the correct category.
  */
  private void selectCategory() {
    while (true) {
      Scanner input = new Scanner(System.in);
      System.out.print("\nSelect a category for this roll: ");
      category = input.nextLine();
      category = category.toUpperCase();
      
      if (scoreTable.containsKey(category)) {
        if (scoreTable.get(category) != -1 && category.contains("YAHTZEE") == false) {
          System.out.println("\nThat category has already been used!");
          continue;
        } else {
          if (checkCategory()) {
            addPoints();
            return;
          } else {
            while (true) {
              System.out.print("\nYour hand does not award any points in this"
                  + " category, continue (Y/N)? ");
              String choice = input.nextLine();
              choice = choice.toUpperCase();
              if (choice.contains("Y")) {
                scoreTable.put(category, 0);
                return;
              } else if (choice.contains("N")) {
                break;
              } else {
                System.out.println("\nPlease input Y or N! ");
              }
            }
          }
        }
      } else {
        System.out.println("\nThat is not a valid category!");
        continue;
      }
    }
  }
  
  /**
  * The printTable method prints the player's score table.
  */
  public void printTable() {
    Set<String> keys = scoreTable.keySet();
    
    StringAlignUtils titleAlign = new StringAlignUtils(26, StringAlignUtils.Alignment.CENTER);
    StringAlignUtils keyAlign = new StringAlignUtils(20, StringAlignUtils.Alignment.LEFT);
    StringAlignUtils scoreAlign = new StringAlignUtils(5, StringAlignUtils.Alignment.CENTER);
  
    System.out.println(" " + "__________________________" + " ");
    System.out.println("|" + titleAlign.format("UPPER SECTION") + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("ONES") + "|" 
        + scoreAlign.format(scoreTable.get("ONES")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("TWOS") + "|" 
        + scoreAlign.format(scoreTable.get("TWOS")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("THREES") + "|" 
        + scoreAlign.format(scoreTable.get("THREES")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("FOURS") + "|" 
        + scoreAlign.format(scoreTable.get("FOURS")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("FIVES") + "|" 
        + scoreAlign.format(scoreTable.get("FIVES")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("SIXES") + "|" 
        + scoreAlign.format(scoreTable.get("SIXES")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + titleAlign.format("LOWER SECTION") + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("THREE_OF_A_KIND") 
        + "|" + scoreAlign.format(scoreTable.get("THREE_OF_A_KIND")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("FOUR_OF_A_KIND") 
        + "|" + scoreAlign.format(scoreTable.get("FOUR_OF_A_KIND")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("FULL_HOUSE") 
        + "|" + scoreAlign.format(scoreTable.get("FULL_HOUSE")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("SM_STRAIGHT") 
        + "|" + scoreAlign.format(scoreTable.get("SM_STRAIGHT")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("LG_STRAIGHT") 
        + "|" + scoreAlign.format(scoreTable.get("LG_STRAIGHT")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("YAHTZEE") 
        + "|" + scoreAlign.format(scoreTable.get("YAHTZEE")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("CHANCE") 
        + "|" + scoreAlign.format(scoreTable.get("CHANCE")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("|" + keyAlign.format("BONUS") 
        + "|" + scoreAlign.format(scoreTable.get("BONUS")) + "|");
    System.out.println("|" + "__________________________" + "|");
    System.out.println("* -1 means that the space is empty");
  }
  
  /**
  * The checkWinner method checks if the game has finished.
  */
  private boolean checkWinner() {
    Set<String> keys = scoreTable.keySet();
    for (String key : keys) {
      if (scoreTable.get(key) == -1) {
        return false;
      }
    }
    return true;
  }
  
  /**
  * The getTotal method returns a player's total score.
  */
  public int getTotal() {
    Set<String> keys = scoreTable.keySet();
    int upperTotal = 0;
    int total = 0;
    
    for (String key : keys) {
      if (scoreTable.get(key) != -1) {
        if (key.contains("ONES") || key.contains("TWOS") || key.contains("THREES") 
            || key.contains("FOURS") || key.contains("FIVES") || key.contains("SIXES")) {
          upperTotal += scoreTable.get(key);
        }
        total += scoreTable.get(key);
      }
    }
    
    if (upperTotal >= UPPER_PAR) {
      total += UPPER_BONUS;
    }
    
    return total;
  }
  
  /**
  * The playTurn method handles all of the methods needed to be executed.
  */
  public boolean playTurn() {
    rollDice();
    selectCategory();
    printTable();
    return checkWinner();
  }
}
