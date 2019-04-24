/**
 * Represents a vector used by the agent to move across the hidden player grid when firing upon the
 * player. The direction of the vector is represented by the x and y values.
 * 
 * @author Alexander Thieler
 */
public class BattleshipVector {

  private int coordX;
  private int coordY;

  public BattleshipVector(int x, int y) {
    this.coordX = x;
    this.coordY = y;
  }

  public int getX() {
    return coordX;
  }

  public int getY() {
    return coordY;
  }

}
