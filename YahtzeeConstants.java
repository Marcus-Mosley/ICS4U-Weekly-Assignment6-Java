/**
* This file contains all the Yahtzee Constants.
*
* @author  Marcus A. Mosley
* @version 1.0
* @since   2021-06-16
*/
public interface YahtzeeConstants {

  /** The number of dice in the game. */
  public static final int N_DICE = 5;

  /** The maximum number of players. */
  public static final int MAX_PLAYERS = 4;

  /** The constants that specify points on the scoresheet. */
  public static final int UPPER_PAR = 63;
  public static final int UPPER_BONUS = 35;
  
  public static final int FULL_HOUSE = 25;
  public static final int SM_STRAIGHT = 30;
  public static final int LG_STRAIGHT = 40;
  public static final int YAHTZEE = 50;
  public static final int YAHTZEE_BONUS = 100;
}