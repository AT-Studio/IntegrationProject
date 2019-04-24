// Inheritance: When a class extends another class or implements an interface
// in order to inherit its fields and methods. Some of the benefits
// of inheritance are that it greatly reduces boilerplate code in related
// classes and that the programmer can change the behavior of a parent class
// and that this change will be reflected its derived classes
/**
 * Represents a ship and is the parent class of all classes of ships in the game. Contains all
 * methods and fields shared by its child classes.
 * 
 * @author Alexander Thieler
 */
public abstract class Ship {

  public static final char DIRECTION_HORIZONTAL = 'h';
  public static final char DIRECTION_VERTICAL = 'v';

  private int size;
  private int damage;
  private char type;
  private BattleshipCoordinate position;
  private char direction;

  public Ship(int size, char type) {
    this.size = size;
    this.type = type;
  }

  // Next line is the header of a method
  /**
   * Attempts to place the ship with the provided direction and coordinates on a grid.
   * 
   * @param x The x coordinate of the position.
   * @param y The y coordinate of the position.
   * @param dir The direction the ship should face.
   * @param grid The grid the ship is to be placed on.
   * @return True if the ship was successfully placed or False if it the ship could not be placed.
   */
  public boolean place(/* Method parameters -> */int x, int y, char dir, char[][] grid) {
    if (dir == 'v') {
      if (y + size > BattleshipGame.GRID_SIZE) {
        return false;
      }
      for (int i = 0; i < size; i++) {
        if (grid[y + i][x] != '~') {
          return false;
        }
      }
      for (int i = 0; i < size; i++) {
        grid[y + i][x] = type;
      }
    } else {
      if (x + size > BattleshipGame.GRID_SIZE) {
        return false;
      }
      for (int i = 0; i < size; i++) {
        if (grid[y][x + i] != '~') {
          return false;
        }
      }
      for (int i = 0; i < size; i++) {
        grid[y][x + i] = type;
      }
    }
    this.position = BattleshipCoordinate.create(x, y);
    this.direction = dir;
    return true;
  }

  public int getSize() {
    return size;
  }

  public char getType() {
    return type;
  }

  public BattleshipCoordinate getPosition() {
    return position;
  }

  public char getDirection() {
    return direction;
  }

  /**
   * Checks if the ship position contains a certain coordinate.
   * 
   * @param x The x coordinate being checked
   * @param y The y coordinate being checked
   * @return True if the coordinate is contained by the ships position and False if not.
   */
  public boolean containsCoordinate(int x, int y) {
    if (direction == DIRECTION_HORIZONTAL) {
      if (y == position.getY() && x >= position.getX() && x < position.getX() + size) {
        return true;
      }
    } else {
      if (x == position.getX() && y >= position.getY() && y < position.getY() + size) {
        return true;
      }
    }
    return false;
  }

  /**
   * Deals damage to the ship and returns a result to the game.
   * 
   * @return A string containing the type of ship that was sunk if it was indeed sunk or null if not
   */
  public String damageShip() {
    damage++;
    if (damage >= size) {
      String type = "";
      if (this instanceof Carrier) {
        type = "Carrier";
      } else if (this instanceof Battleship) {
        type = "Battleship";
      } else if (this instanceof Cruiser) {
        type = "Cruiser";
      } else if (this instanceof Submarine) {
        type = "Submarine";
      } else if (this instanceof Destroyer) {
        type = "Destroyer";
      }
      return "A " + type + " was sunk!";
    }
    return null;
  }

  /**
   * Tells the caller whether or not the ship has been destroyed.
   * @return True if ship is destroyed else False.
   */
  public boolean isDestroyed() {
    if (damage >= size) {
      return true;
    } else {
      return false;
    }
  }

}
