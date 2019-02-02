// Alexander Thieler

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {
  public static final int GRID_SIZE = 10; 
  // Definition - final: a variable that is declared final becomes and constant
  //                     and cannot be changed

  private char[][] playerGrid;
  private char[][] computerGrid;
  private char[][] computerGridHidden;
  private char[] dividerField;
  
  public void startGame() {
    Scanner scanner = new Scanner(System.in);
    
    playerGrid = new char[10][10];
    computerGrid = new char[10][10];
    computerGridHidden = new char[10][10];
    dividerField = new char[10];
    for (int i = 0; i < GRID_SIZE; i++) {
      Arrays.fill(playerGrid[i], '~');
      Arrays.fill(computerGrid[i], '~');
      Arrays.fill(computerGridHidden[i], '?');
    }
    Arrays.fill(dividerField, '-');
    
    printPlayerGrid();
    System.out.println("Let's start placing your ships into the grid.");
    System.out.println("To place a ship you must, using single space seperaton, "
        + "the ship type (character in brackets after the name of the ship) \n"
        + "as well as the x and y coordinates and direction (h for horizontal "
        + "or v for vertical");
    System.out.println("For example to place a Carrier at location (0, 5) "
        + "with vertical direction you would enter: \"c 0 5 v\"");
    System.out.println("Press enter to continue.");
    scanner.nextLine();
    
    boolean hasShipsToPlace = true;
    int numCarriers = 1;
    int numBattleships = 1;
    int numCruisers = 2;
    int numSubmarines = 2;
    int numDestroyers = 3;
    
    while (hasShipsToPlace) {
      System.out.println("You must still place " + numCarriers + " Carriers(C) "
          + numBattleships + " Battleships(B) "
          + numCruisers + " Cruisers(R) "
          + numSubmarines + " Submarines(S) "
          + numDestroyers + " Destroyers(D)");
      System.out.println("Place a ship:");
      char type;
      int x;
      int y;
      char dir;
      try {
        type = scanner.next().charAt(0);
        x = scanner.nextInt();
        y = scanner.nextInt();
        dir = scanner.next().charAt(0);
      } catch (Exception e){
        System.out.println("Your input was invalid. Please try again.");
        scanner.nextLine();
        continue;
      }
      scanner.nextLine();
      if (inputsAreValid(type, x, y, dir)) {
        Ship ship = null;
        switch (type) {
          case Carrier.TYPE : {
            if (numCarriers > 0) {
              ship = new Carrier(); 
              numCarriers--;
            } else {
              System.out.println("You cannot place anymore Carriers");
              continue;
            }
            break;
          }
          case Battleship.TYPE : {
            if (numBattleships > 0) {
              ship = new Battleship(); 
              numBattleships--;
            } else {
              System.out.println("You cannot place anymore Battleships");
              continue;
            }
            break;
          }
          case Cruiser.TYPE : {
            if (numCruisers > 0) {
              ship = new Cruiser(); 
              numCruisers--;
            } else {
              System.out.println("You cannot place anymore Cruisers");
              continue;
            }
            break;
          }
          case Submarine.TYPE : {
            if (numSubmarines > 0) {
              ship = new Submarine(); 
              numSubmarines--;
            } else {
              System.out.println("You cannot place anymore Submarines");
              continue;
            }
            break;
          }
          case Destroyer.TYPE : {
            if (numDestroyers > 0) {
              ship = new Destroyer(); 
              numDestroyers--;
            } else {
              System.out.println("You cannot place anymore Destoyers");
              continue;
            }
            break;
          }
        }
        if (!ship.place(x, y, dir, playerGrid)) {
          switch (type) {
            case Carrier.TYPE : numCarriers++; break;
            case Battleship.TYPE : numBattleships++; break;
            case Cruiser.TYPE : numCruisers++; break;
            case Submarine.TYPE : numSubmarines++; break;
            case Destroyer.TYPE : numDestroyers++; break;
          }
          System.out.println("The ship could not be placed. Please try again.");
          continue;
        }
        printPlayerGrid();
        if (numCarriers + numBattleships + numCruisers + numSubmarines +
            numDestroyers == 0) hasShipsToPlace = false;
      } else {
        System.out.println("Your input was invalid. Please Try again.");
      }
    }
    System.out.println("You are done placing ships.");
    
    scanner.close();
  }
  
  private boolean inputsAreValid(char type, int x, int y, char dir) {
    if (type != Carrier.TYPE && type != Battleship.TYPE && type != Cruiser.TYPE 
        && type != Submarine.TYPE && type != Destroyer.TYPE) return false;
    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return false;
    if (dir != 'h' && dir != 'v') return false;
    return true;
  }
  
  private void populateComputerGrid() {
    Random random = new Random();
    
    Ship ship = new Carrier();
    while (!ship.place(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE), 
        (random.nextInt(2) == 0) ? 'v' : 'h', computerGrid));
    //TODO: create rest of ships
  }
  
  public void printPlayerGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(playerGrid[i]));
    }
  }
  
  public void printFullGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(computerGridHidden[i]));
    }
    System.out.println("\n");
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(playerGrid[i]));
    }
  }
}
