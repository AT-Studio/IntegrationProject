import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Represents a player. Is responsible for passing player input to the game.
 * 
 * @author Alexander Thieler
 */
public class Player {

  private Scanner scanner;

  public Player() {
    this.scanner = new Scanner(System.in);
  }

  public String getPlayerInput() {
    return scanner.nextLine();
  }

  /**
   * Passes user input to the game during the ship placement phase of the game, if the player
   * chooses to place his/her ships manually. Uses regex to make sure player input has the correct
   * format.
   * 
   * @return An array of user inputs if the format of the inputs was valid else null.
   */
  public char[] getShipPlacement() {
    char[] data = new char[4];
    try {
      String regex = "\\w \\d \\d \\w";
      String input = scanner.nextLine();
      if (Pattern.matches(regex, input)) {
        data[0] = input.charAt(0);
        data[1] = input.charAt(2);
        data[2] = input.charAt(4);
        data[3] = input.charAt(6);
      } else {
        data = null;
      }
    } catch (Exception e) {
      return null;
    }
    return data;
  }

  /**
   * Passes user input to the game when firing upon the agent. Uses regex to make sure the player
   * input has the correct format and that the coordinates are within the bounds of the grid.
   * 
   * @return The coordinates to be fired on by the player or null if the input was invalid.
   */
  public BattleshipCoordinate fireOnAgent() {
    BattleshipCoordinate coords = null;
    try {
      String regex = "\\d, \\d";
      String coordString = scanner.nextLine();
      if (!Pattern.matches(regex, coordString)) {
        return null;
      }
      int x = Character.getNumericValue(coordString.charAt(0));
      int y = Character.getNumericValue(coordString.charAt(3));
      if (x >= BattleshipGame.GRID_SIZE || x < 0 || y >= BattleshipGame.GRID_SIZE || y < 0) {
        throw new Exception();
      }
      coords = BattleshipCoordinate.create(x, y);
    } catch (Exception e) {
      return null;
    }
    return coords;
  }

  public void closeScanner() {
    scanner.close();
  }
}
