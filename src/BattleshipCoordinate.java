/**
 * Represents a coordinate used for the Battleship game.
 * 
 * @author Alexander Thieler
 */
public class BattleshipCoordinate {

  /**
   * Responsible for creating an instance of the class. Also makes sure that the coordinates stored
   * in the object are within the bounds of the grid.
   * 
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @return A new BattleshipCoordinate or null if coordinates are outside of the bounds of the grid
   */
  public static BattleshipCoordinate create(int x, int y) {
    if (x >= 0 && x < BattleshipGame.GRID_SIZE && y >= 0 && y < BattleshipGame.GRID_SIZE) {
      return new BattleshipCoordinate(x, y);
    } else {
      return null;
    }
  }

  /**
   * Creates a new coordinate by taking a position and adding a vector to it.
   * 
   * @param position Coordinates of a position
   * @param dirVector Vector representing the direction to move in
   * @return A new coordinate that is the sum of the position and direction vector
   */
  public static BattleshipCoordinate create(BattleshipCoordinate position,
      BattleshipVector dirVector) {
    int newX = position.getX() + dirVector.getX();
    int newY = position.getY() + dirVector.getY();
    return BattleshipCoordinate.create(newX, newY);
  }

  private int coordX;
  private int coordY;

  private BattleshipCoordinate() {}

  private BattleshipCoordinate(int x, int y) {
    this.coordX = x;
    this.coordY = y;
  }

  public int getX() {
    return coordX;
  }

  public void setX(int x) {
    this.coordX = x;
  }

  public int getY() {
    return coordY;
  }

  public void setY(int y) {
    this.coordY = y;
  }

}
