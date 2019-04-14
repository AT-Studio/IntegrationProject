//Alexander Thieler
public class BattleshipCoordinate {
  
  public static BattleshipCoordinate create(int x, int y) {
    if (x >= 0 && x < BattleshipGame.GRID_SIZE
        && y >= 0 && y < BattleshipGame.GRID_SIZE) {
      return new BattleshipCoordinate(x, y);
    } else {
      return null;
    }
  }
  
  public static BattleshipCoordinate create(BattleshipCoordinate position,
      BattleshipCoordinate dirVector) {
    int newX = position.getX() + dirVector.getX();
    int newY = position.getY() + dirVector.getY();
    if (newX >= 0 && newX < BattleshipGame.GRID_SIZE
        && newY >= 0 && newY < BattleshipGame.GRID_SIZE) {
      return new BattleshipCoordinate(newX, newY);
    } else {
      return null;
    }
  }
  
  private int x;
  private int y;
  
  private BattleshipCoordinate() {}
  
  private BattleshipCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int y) {
    this.y = y;
  }

}
