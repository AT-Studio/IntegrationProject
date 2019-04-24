// Alexander Thieler

// Implementation of the Battleship game

// byte: The byte data type is an 8-bit signed two's complement integer. It has a minimum value of
// -128 and a maximum value of 127 (inclusive). The byte data type can be useful for saving memory
// in large arrays, where the memory savings actually matters. They can also be used in place of int
// where their limits help to clarify your code; the fact that a variable's range is limited can
// serve as a form of documentation.
//
// short: The short data type is a 16-bit signed two's complement integer. It has a minimum value of
// -32,768 and a maximum value of 32,767 (inclusive). As with byte, the same guidelines apply: you
// can use a short to save memory in large arrays, in situations where the memory savings actually
// matters.
//
// int: By default, the int data type is a 32-bit signed two's complement integer, which has a
// minimum value of -231 and a maximum value of 231-1. In Java SE 8 and later, you can use the int
// data type to represent an unsigned 32-bit integer, which has a minimum value of 0 and a maximum
// value of 232-1. Use the Integer class to use int data type as an unsigned integer. See the
// section The Number Classes for more information. Static methods like compareUnsigned,
// divideUnsigned etc have been added to the Integer class to support the arithmetic operations for
// unsigned integers.
//
// long: The long data type is a 64-bit two's complement integer. The signed long has a minimum
// value of -263 and a maximum value of 263-1. In Java SE 8 and later, you can use the long data
// type to represent an unsigned 64-bit long, which has a minimum value of 0 and a maximum value of
// 264-1. Use this data type when you need a range of values wider than those provided by int. The
// Long class also contains methods like compareUnsigned, divideUnsigned etc to support arithmetic
// operations for unsigned long.
//
// float: The float data type is a single-precision 32-bit IEEE 754 floating point. Its range of
// values is beyond the scope of this discussion, but is specified in the Floating-Point Types,
// Formats, and Values section of the Java Language Specification. As with the recommendations for
// byte and short, use a float (instead of double) if you need to save memory in large arrays of
// floating point numbers. This data type should never be used for precise values, such as currency.
// For that, you will need to use the java.math.BigDecimal class instead. Numbers and Strings covers
// BigDecimal and other useful classes provided by the Java platform.
//
// double: The double data type is a double-precision 64-bit IEEE 754 floating point. Its range of
// values is beyond the scope of this discussion, but is specified in the Floating-Point Types,
// Formats, and Values section of the Java Language Specification. For decimal values, this data
// type is generally the default choice. As mentioned above, this data type should never be used for
// precise values, such as currency.
//
// boolean: The boolean data type has only two possible values: true and false. Use this data type
// for simple flags that track true/false conditions. This data type represents one bit of
// information, but its "size" isn't something that's precisely defined.
//
// char: The char data type is a single 16-bit Unicode character. It has a minimum value of '\u0000'
// (or 0) and a maximum value of '\uffff' (or 65,535 inclusive).

// Definition - variable: a location in memory
// Definition - scope : a variable is only accessible in the method in which it was declared

import java.util.Scanner;

/**
 * This program is an implementation of the popular Battleship game. This class in particular is
 * responsible for starting the Battleship game by creating an instance of the BattleshipGame class.
 * 
 * @author Alexander Thieler
 */
public class Main {

  /**
   * Creates an instance of user and reads inputs from the user in order to
   * start the Battleship game.
   * @param args String array passed to main method.
   */
  public static void main(String[] args) {
    Player player = new Player();

    System.out.println("Welcome to my Integration Project. Let's play Battleship!");

    /**
     * The user is prompted to choose whether or not they want to play a game of Battleship. Once
     * he/she agrees, a new game of Battleship is started.
     */
    System.out.println("Are you ready? (yes/no)");
    String answer = player.getPlayerInput();
    if (answer.equals("yes")) {
      System.out.println("");
      System.out.println("Ok GREAT! Let's start!");
      startNewBattleshipGame(player);
    } else {
      do {
        System.out.println("Are you sure?? (yes/no)");
      } while (!player.getPlayerInput().equals("no"));
      System.out.println("");
      System.out.println("Ok GREAT! Let's start!");
      startNewBattleshipGame(player);
    }

    player.closeScanner();

    System.out.println("\n\n");

    /**
     * The code below is simply to meet the requirements of PSI 1-3 that aren't met in the
     * BattleshipGame itself.
     */
    double pi = Math.PI;
    int radiusEarth = 6371;
    int surfaceAreaEarth = (int) (4 * pi * Math.pow(radiusEarth, 2));
    System.out.println("Fun fact: The surface area of the earth is: " + surfaceAreaEarth + " km^2");
    // casting: telling java to trust the programmer that the restult will fit
    // into the data type that is being casted to.

    String firstString = "Mars";
    String secondString = "Venus";
    if (firstString.compareTo(secondString) > 0) {
      System.out.println(firstString + " is lexicographically greater than " + secondString);
    }

    int a = 1;
    a = a + a;
    a = a - a;
    a = a * a;
    if (a != 0) {
      a = a / a;
    }
    if (a != 0) {
      a = a % a;
    }
    a++;
    a--;
    a += a;

    // Certain operators take precedence over others, for instance the multiplication
    // operator (*) takes precendence over the addition operator (+) and therefore
    // a * a will be evaluated first before adding a to the product.
    a = a + a * a;

    int index = 1;
    while (true) {
      // The break statement will end the current loop it is nested inside of
      if (index > 5) {
        break;
      }
      index++;
    }

    int[] numberArr = {5, 3, 1, 2, 10};

    int smallest = numberArr[0];
    for (int i = 1; i < numberArr.length; i++) {
      smallest = numberArr[i] < smallest ? numberArr[i] : smallest;
    }

    int sum = 0;
    for (int i = 0; i < numberArr.length; i++) {
      sum += numberArr[i];
    }

    int valueToFind = 2;
    int idx = -1;
    for (int i = 0; i < numberArr.length; i++) {
      if (numberArr[i] == valueToFind) {
        idx = i;
        break;
      }
    }

    int[][] numberGrid = {{1, 9, 2}, {8, 3, 7}, {6, 4, 5}};

    int y = -1;
    int x = -1;
    outerLoop: for (int i = 0; i < numberGrid.length; i++) {
      for (int j = 0; j < numberGrid[i].length; j++) {
        if (numberGrid[i][j] == valueToFind) {
          x = j;
          y = i;
          break outerLoop;
        }
      }
    }

    System.out.println("Please enter a random number less than 10");
    try {
      int input = Integer.parseInt(player.getPlayerInput());
      if (input >= 10) {
        throw new Exception("Number was not less than 10");
      }
    } catch (NumberFormatException e) {
      System.out.println("Number was not a number");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    player.closeScanner();
  }

  /**
   * Starts a new game of Battleship.
   * 
   * @param player An instance of the Player class. the player playing the game.
   */
  private static void startNewBattleshipGame(Player player) {
    BattleshipGame game = new BattleshipGame();
    game.startGame(player);
  }
}
