import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents an agent. The player plays against the agent and every game only has one agent. The
 * class defines the behavior of the agent in a game of Battleship.
 * 
 * @author Alexander Thieler
 */
public class BattleshipAgent {

  private BattleshipGame game;
  private char[][] playerGridHidden;

  boolean hasFlippedDirection = false;
  BattleshipCoordinate lastFiredPosition;

  boolean foundShip;
  boolean checkSurroundings;
  ArrayList<BattleshipCoordinate> surroundingsQueue;
  BattleshipCoordinate startPos;
  BattleshipVector dirVector;

  /**
   * Constructor for the agent.
   * 
   * @param game The game currently being played.
   * @param playerGridHidden The hidden player grid used by the agent its decision making.
   */
  public BattleshipAgent(BattleshipGame game, char[][] playerGridHidden) {
    this.game = game;
    this.playerGridHidden = playerGridHidden;
    surroundingsQueue = new ArrayList();
  }

  /**
   * Method containing the logic for agent's decision making when firing on the player. The agent
   * will fire at a random position on the board that it has not yet previously fired upon until it
   * hits a ship. Once a ship is hit, it will continue by checking the surroundings of the ship
   * (top, right, bottom, left). Once it lands another hit in the surroundings of the the initial
   * hit, it will continue firing in that direction until the ship is sunk or until it reaches the
   * end of the grid or hits water. If it reaches the end of the grid or hits water before the ship
   * is sunk, it will go back to where the first hit was landed and move in the opposite direction
   * until the ship is finally sunk. After the ship is sunk, the agent will continue to fire
   * randomly until the next ship is hit.
   */
  public void fireOnPlayer() {
    if (foundShip) {
      if (checkSurroundings) {
        if (!surroundingsQueue.isEmpty()) {
          BattleshipCoordinate coord = surroundingsQueue.remove(0);
          BattleshipGame.FireResult result = game.fireOnPlayer(coord.getX(), coord.getY());
          lastFiredPosition = coord;
          if (result.hitShip) {
            playerGridHidden[coord.getY()][coord.getX()] = 'X';
            checkSurroundings = false;
            if (!result.sunkShip) {
              dirVector = new BattleshipVector(coord.getX() - startPos.getX(),
                  coord.getY() - startPos.getY());
            } else {
              hasFlippedDirection = false;
              foundShip = false;
              surroundingsQueue.clear();
            }
          } else {
            playerGridHidden[coord.getY()][coord.getX()] = '~';
          }
        } else {
          checkSurroundings = false;
          foundShip = false;
          fireOnPlayer();
        }
      } else if (dirVector != null) {
        BattleshipCoordinate check = BattleshipCoordinate.create(lastFiredPosition, dirVector);
        if (check != null && playerGridHidden[check.getY()][check.getX()] == '?') {
          BattleshipGame.FireResult result = game.fireOnPlayer(check.getX(), check.getY());
          lastFiredPosition = check;
          if (result.hitShip) {
            playerGridHidden[check.getY()][check.getX()] = 'X';
            if (result.sunkShip) {
              hasFlippedDirection = false;
              foundShip = false;
              surroundingsQueue.clear();
            }
          } else {
            playerGridHidden[check.getY()][check.getX()] = '~';
            if (!hasFlippedDirection) {
              hasFlippedDirection = true;
              dirVector = new BattleshipVector(-dirVector.getX(), -dirVector.getY());
              lastFiredPosition = startPos;
            } else {
              checkSurroundings = true;
              hasFlippedDirection = false;
              // TODO: there must be another ship
            }
          }
        } else {
          if (!hasFlippedDirection) {
            hasFlippedDirection = true;
            dirVector = new BattleshipVector(-dirVector.getX(), -dirVector.getY());
            lastFiredPosition = startPos;
            fireOnPlayer();
          } else {
            checkSurroundings = true;
            hasFlippedDirection = false;
            fireOnPlayer();
            // TODO: there must be another ship
          }
        }
      } else {
        checkSurroundings = false;
        foundShip = false;
        fireOnPlayer();
      }
    } else {
      Random random = new Random();

      int x = 0;
      int y = 0;

      do {
        x = random.nextInt(playerGridHidden.length);
        y = random.nextInt(playerGridHidden.length);
      } while (playerGridHidden[y][x] != '?');

      BattleshipGame.FireResult result = game.fireOnPlayer(x, y);

      if (result.hitShip) {
        playerGridHidden[y][x] = 'X';
        if (!result.sunkShip) {
          startPos = BattleshipCoordinate.create(x, y);
          lastFiredPosition = startPos;
          final BattleshipCoordinate top = BattleshipCoordinate.create(x, y - 1);
          final BattleshipCoordinate right = BattleshipCoordinate.create(x + 1, y);
          final BattleshipCoordinate bottom = BattleshipCoordinate.create(x, y + 1);
          final BattleshipCoordinate left = BattleshipCoordinate.create(x - 1, y);
          if (top != null && playerGridHidden[top.getY()][top.getX()] == '?') {
            surroundingsQueue.add(top);
          }
          if (right != null && playerGridHidden[right.getY()][right.getX()] == '?') {
            surroundingsQueue.add(right);
          }
          if (bottom != null && playerGridHidden[bottom.getY()][bottom.getX()] == '?') {
            surroundingsQueue.add(bottom);
          }
          if (left != null && playerGridHidden[left.getY()][left.getX()] == '?') {
            surroundingsQueue.add(left);
          }
          checkSurroundings = true;
          foundShip = true;
        }
      } else {
        playerGridHidden[y][x] = '~';
      }

    }
  }

}
