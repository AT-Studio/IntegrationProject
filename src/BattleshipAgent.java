//Alexander Thieler


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BattleshipAgent {
  
  private BattleshipGame game;
  private char[][] playerGridHidden;
  
  int[] directionVector = new int[2];
  
  
  boolean hasFlippedDirection = false;
  BattleshipCoordinate lastFiredPosition;
  
  boolean foundShip;
  boolean checkSurroundings;
  ArrayList<BattleshipCoordinate> surroundingsQueue;
  BattleshipCoordinate startPos;
  BattleshipCoordinate dirVector;
  
  public BattleshipAgent(BattleshipGame game, char[][] playerGridHidden) {
    this.game = game;
    this.playerGridHidden = playerGridHidden;
    surroundingsQueue = new ArrayList();
  }
  
  public int[] fireOnPlayer() {
    if (foundShip) {
      if (checkSurroundings) {
        BattleshipCoordinate coord = surroundingsQueue.remove(0);
        BattleshipGame.FireResult result = game.fireOnPlayer(coord.getX(), coord.getY());
        lastFiredPosition = coord;
        if (result.hitShip) {
          playerGridHidden[coord.getY()][coord.getX()] = 'X';
          checkSurroundings = false;
          if (!result.sunkShip) {
            dirVector = BattleshipCoordinate.create(coord.getX() - startPos.getX(), 
                coord.getY() - startPos.getY());
          } else {
            hasFlippedDirection = false;
            foundShip = false;
            surroundingsQueue.clear();
          }
        } else {
          playerGridHidden[coord.getY()][coord.getX()] = '~';
        }
      }
      if (dirVector != null) {
//        int x = lastFiredPosition.getX() + dirVector.getX();
//        int y = lastFiredPosition.getY() + dirVector.getY();
        BattleshipCoordinate check = BattleshipCoordinate.create(lastFiredPosition, dirVector);
        if (check != null) {
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
              dirVector = BattleshipCoordinate.create(-dirVector.getX(), -dirVector.getY());
              lastFiredPosition = startPos;
            } else {
              checkSurroundings = true;
              hasFlippedDirection = false;
              //TODO: there must be another ship
            }
          }
        } else {
          if (!hasFlippedDirection) {
            hasFlippedDirection = true;
            dirVector = BattleshipCoordinate.create(-dirVector.getX(), -dirVector.getY());
            lastFiredPosition = startPos;
            fireOnPlayer();
          } else {
            checkSurroundings = true;
            hasFlippedDirection = false;
            fireOnPlayer();
            //TODO: there must be another ship
          }
        }
       
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
          BattleshipCoordinate top = BattleshipCoordinate.create(x, y - 1);
          BattleshipCoordinate right = BattleshipCoordinate.create(x + 1, y);
          BattleshipCoordinate bottom = BattleshipCoordinate.create(x, y + 1);
          BattleshipCoordinate left = BattleshipCoordinate.create(x - 1, y);
          if (top != null && playerGridHidden[top.getX()][top.getY()] == '?') surroundingsQueue.add(top);
          if (right != null && playerGridHidden[right.getX()][right.getY()] == '?') surroundingsQueue.add(right);
          if (bottom != null && playerGridHidden[bottom.getX()][bottom.getY()] == '?') surroundingsQueue.add(bottom);
          if (left != null && playerGridHidden[left.getX()][left.getY()] == '?') surroundingsQueue.add(left);
          checkSurroundings = true;
          foundShip = true;
        }
      } else {
        playerGridHidden[y][x] = '~';
      }
      
    }
    
    
    
    return null;
  }

}
