/**
 * Represents a battleship, which is a ship. It has a size of 4 and its type is 'B' which is an
 * abbreviation for battleship.
 * 
 * @author Alexander Thieler
 */
public class Battleship extends Ship {

  public static final int SIZE = 4;
  public static final char TYPE = 'B';

  public Battleship() {
    super(SIZE, TYPE);
  }

}
