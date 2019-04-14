// Alexander Thieler
// Inheritance: When a class extends another class or implements an interface 
//              in order to inherit its fields and methods. Some of the benefits
//              of inheritance are that it greatly reduces boilerplate code in related
//              classes and that the programmer can change the behavior of a parent class
//              and that this change will be reflected its derived classes
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
  public boolean place(/* Method parameters -> */int x, int y, char dir, char[][] playerGrid) {
    if (dir == 'v') {
      if (y + size > BattleshipGame.GRID_SIZE) return false;
      for (int i = 0; i < size; i++) {
        if (playerGrid[y + i][x] != '~') return false;
      }
      for (int i = 0; i < size; i++) {
        playerGrid[y + i][x] = type;
      }
    } else {
      if (x + size > BattleshipGame.GRID_SIZE) return false;
      for (int i = 0; i < size; i++) {
        if (playerGrid[y][x + i] != '~') return false;
      }
      for (int i = 0; i < size; i++) {
        playerGrid[y][x + i] = type;
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
  
  public boolean containsCoordinate(int x, int y) {
    if (direction == DIRECTION_HORIZONTAL) {
      if (y == position.getY() && x >= position.getX() && x < position.getX() + size) {
        return true;
      }
    } else {
      if (x == position.getX() && y >= position.getY() && x < position.getY() + size) {
        return true;
      }
    }
    return false;
  }
  
  public void damageShip() {
    damage++;
    if (damage >= size) {
      String type = "";
      if (this instanceof Carrier) type = "Carrier";
      else if (this instanceof Battleship) type = "Battleship";
      else if (this instanceof Cruiser) type = "Cruiser";
      else if (this instanceof Submarine) type = "Submarine";
      else if (this instanceof Destroyer) type = "Destroyer";
      System.out.println("A " + type + " was sunk!");
    }
  }
  
  public boolean isDestroyed() {
    if (size > damage) return false;
    else return true;
  }
  
}
