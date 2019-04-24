/**
 * Represents a cruiser, which is a ship. It has a size of 3 and its type is 'R' which is an
 * abbreviation for cruiser.
 * 
 * @author Alexander Thieler
 */
public class Cruiser extends Ship {

  public static final int SIZE = 3;
  public static final char TYPE = 'R';

  public Cruiser() {
    super(SIZE, TYPE);
  }

}
