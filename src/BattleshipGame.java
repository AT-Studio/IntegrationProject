// Alexander Thieler

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {
  public static final int GRID_SIZE = 10; 
  // Definition - final: a variable that is declared final becomes and constant
  //                     and cannot be changed

  private char[][] playerGrid;
  private char[][] playerGridHidden;
  private char[][] agentGrid;
  private char[][] agentGridHidden;
  private char[] dividerField;
  
  private Map<Character, ArrayList<Ship>> playerShips;
  private Map<Character, ArrayList<Ship>> agentShips;
  
  Player player;
  BattleshipAgent agent;
  
  public void startGame(Player player) {
    this.player = player;
    
    playerGrid = new char[GRID_SIZE][GRID_SIZE];
    playerGridHidden = new char[GRID_SIZE][GRID_SIZE];
    agentGrid = new char[GRID_SIZE][GRID_SIZE];
    agentGridHidden = new char[GRID_SIZE][GRID_SIZE];
    dividerField = new char[10];
    for (int i = 0; i < GRID_SIZE; i++) {
      Arrays.fill(playerGrid[i], '~');
      Arrays.fill(playerGridHidden[i], '?');
      Arrays.fill(agentGrid[i], '~');
      Arrays.fill(agentGridHidden[i], '?');
    }
    Arrays.fill(dividerField, '-');
    
    playerShips = new HashMap();
    agentShips = new HashMap();
    
    this.agent = new BattleshipAgent(this, playerGridHidden);
    
    //--------- test computer grid
    
//    populateComputerGrid();
//    printComputerGrid();
//    if (true) return;
    
    //----------
    
    printPlayerGrid();
    System.out.println("Let's start placing your ships into the grid.");
    System.out.println("To place a ship you must, using single space seperaton, "
        + "the ship type (character in brackets after the name of the ship) \n"
        + "as well as the x and y coordinates and direction (h for horizontal "
        + "or v for vertical");
    System.out.println("For example to place a Carrier at location (0, 5) "
        + "with vertical direction you would enter: \"C 0 5 v\"");
    System.out.println("Press enter to continue.");
    player.getPlayerInput();
    
    System.out.println("Would you like to place your ships manually? (yes/no)");
    if (player.getPlayerInput().equals("yes")) {
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
        
        String[] data = player.getShipPlacement();
        
        //Next line has a method call inside of an if statement
        if (inputsAreValid(/* Method call arguments -> */data)) {
          char type = data[0].charAt(0);
          int x = Integer.parseInt(data[1]);
          int y = Integer.parseInt(data[2]);
          char dir = data[3].charAt(0);
          Ship ship = null;
          switch (type) {
            case Carrier.TYPE : {
              if (numCarriers > 0) {
                ship = new Carrier(); 
                numCarriers--;
              } else {
                System.out.println("You cannot place anymore Carriers");
                //the continue statement ends the current iteration of the loop
                //and the program continues by once again checking the loop 
                //condition of the loop it is nested inside of
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
    } else {
      populateGridRandomly(playerGrid);
    }
    
    populateGridRandomly(agentGrid);
    
    System.out.println("\nHere's a look at the full game");
    printFullGrid();
    
    //TODO: start firing at each other
  }
  
  private boolean inputsAreValid(String[] data) {
    if (data == null) return false;
    char type;
    int x;
    int y;
    char dir;
    try {
      type = data[0].charAt(0);
      x = Integer.parseInt(data[1]);
      y = Integer.parseInt(data[2]);
      dir = data[3].charAt(0);
    } catch (Exception e) {
      return false;
    }
    if (type != Carrier.TYPE && type != Battleship.TYPE && type != Cruiser.TYPE 
        && type != Submarine.TYPE && type != Destroyer.TYPE) return false;
    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return false;
    if (dir != Ship.DIRECTION_HORIZONTAL && dir != Ship.DIRECTION_VERTICAL) return false;
    return true;
  }
  
  public FireResult fireOnPlayer(int x, int y) {
    if (playerGrid[y][x] != '~') {
      //TODO: 
      char type = playerGrid[y][x];
      playerGrid[y][x] = 'X';
      ArrayList<Ship> ships = playerShips.get(type);
      for (Ship ship : ships) {
        if (ship.containsCoordinate(x, y)) {
          ship.damageShip();
          return new FireResult(true, ship.isDestroyed());
        }
      }
      return new FireResult(false, false);
    }
    else return new FireResult(false, false);
  }
  
  private void populateGridRandomly(char[][] grid) {
    Random random = new Random();
    
    Ship ship = new Carrier();
    for (int i = 0; i < 9; i++) {
      if (i == 0) ship = new Carrier();
      else if (i == 1) ship = new Battleship();
      else if (i == 2 || i == 3) ship = new Cruiser();
      else if (i == 4 || i == 5) ship = new Submarine();
      else ship = new Destroyer();
      while (!ship.place(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE), 
          random.nextInt(2) == 0 ? Ship.DIRECTION_VERTICAL : Ship.DIRECTION_HORIZONTAL, grid));
      if (grid == playerGrid) {
        ArrayList<Ship> ships = playerShips.get(ship.getType());
        if (ships == null) {
          ships = new ArrayList();
          playerShips.put(ship.getType(), ships);
        }
        ships.add(ship);
      } else {
        ArrayList<Ship> ships = agentShips.get(ship.getType());
        if (ships == null) {
          ships = new ArrayList();
          agentShips.put(ship.getType(), ships);
        }
        ships.add(ship);
      }
    }
  }
  
  public void printPlayerGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(playerGrid[i]));
    }
  }
  
  public void printComputerGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(agentGrid[i]));
    }
  }
  
  public void printLine() {
    System.out.println(Arrays.toString(dividerField));
  }
  
  public void printFullGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(agentGridHidden[i]));
    }
    System.out.println("\n");
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(playerGrid[i]));
    }
  }
  
  class FireResult {
    
    public final boolean hitShip;
    public final boolean sunkShip;
    
    public FireResult(boolean hitShip, boolean sunkShip) {
      this.hitShip = hitShip;
      this.sunkShip = sunkShip;
    }
    
  }
}
