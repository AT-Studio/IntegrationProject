/**
 * Represents a carrier, which is a ship. It has a size of 5 and its type is 'C' which is an
 * abbreviation for carrier.
 * 
 * @author Alexander Thieler
 */
public class Carrier extends Ship {

  public static final int SIZE = 5;
  public static final char TYPE = 'C';

  public Carrier() {
    super(SIZE, TYPE);
  }

}
