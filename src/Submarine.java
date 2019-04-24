/**
 * Represents a submarine, which is a ship. It has a size of 2 and its type is 'S' which is an
 * abbreviation for submarine.
 * 
 * @author Alexander Thieler
 */
public class Submarine extends Ship {

  public static final int SIZE = 2;
  public static final char TYPE = 'S';

  public Submarine() {
    super(SIZE, TYPE);
  }

}
