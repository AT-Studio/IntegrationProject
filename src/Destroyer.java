/**
 * Represents a destroyer, which is a ship. It has a size of 1 and its type is 'D' which is an
 * abbreviation for destroyer.
 * 
 * @author Alexander Thieler
 */
public class Destroyer extends Ship {

  public static final int SIZE = 1;
  public static final char TYPE = 'D';

  public Destroyer() {
    super(SIZE, TYPE);
  }

}
