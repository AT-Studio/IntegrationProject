import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the Battleship game. This class is responsible for initializing all of the main
 * components of the game (maintaing the grids and ships, keeping track of the score etc.). Each
 * game is associated with exactly one player and one agent. This class will call upon the player
 * and agent to fire upon one another and all player and agent inputs will be handled by this class.
 * 
 * @author Alexander Thieler
 */
public class BattleshipGame {
  public static final int GRID_SIZE = 10;
  // Definition - final: a variable that is declared final becomes and constant
  // and cannot be changed

  private char[][] playerGrid;
  private char[][] playerGridHidden;
  private char[][] agentGrid;
  private char[][] agentGridHidden;
  // private char[] dividerField;

  private Map<Character, ArrayList<Ship>> playerShips;
  private Map<Character, ArrayList<Ship>> agentShips;

  Player player;
  BattleshipAgent agent;

  private int playerShipsSunk;
  private int agentShipsSunk;

  ArrayList<String> messageQueue;

  /**
   * This method is the main method of the game and is responsible for initializing fields and
   * ultimately determines the flow of the game. After the fields have been initialized, the agent
   * is created and the player goes through the ship placement process. The player can choose to
   * place his ships manually or have them placed randomly by the game. After that, the game will
   * then call on the player and agent repeatedly to fire on one another. Finally, the game
   * concludes when either the player or agent has sunk all of the opposing ships.
   * 
   * @param player The player provided by Main.java
   */
  public void startGame(Player player) {
    this.player = player;

    playerGrid = new char[GRID_SIZE][GRID_SIZE];
    playerGridHidden = new char[GRID_SIZE][GRID_SIZE];
    agentGrid = new char[GRID_SIZE][GRID_SIZE];
    agentGridHidden = new char[GRID_SIZE][GRID_SIZE];

    for (int i = 0; i < GRID_SIZE; i++) {
      Arrays.fill(playerGrid[i], '~');
      Arrays.fill(playerGridHidden[i], '?');
      Arrays.fill(agentGrid[i], '~');
      Arrays.fill(agentGridHidden[i], '?');
    }

    playerShips = new HashMap();
    agentShips = new HashMap();

    messageQueue = new ArrayList();

    this.agent = new BattleshipAgent(this, playerGridHidden);

    System.out.println("Let's start placing your ships into the grid.");
    System.out.println("To place a ship you must, using single space seperaton, "
        + "the ship type (character in brackets after the name of the ship) \n"
        + "as well as the x and y coordinates and direction (h for horizontal "
        + "or v for vertical");
    System.out.println("For example to place a Carrier at location (0, 5) "
        + "with vertical direction you would enter: \"C 0 5 v\"");
    System.out.println("Press enter to continue.");
    player.getPlayerInput();

    String response = null;

    do {
      System.out.println("Would you like to place your ships manually? (yes/no)");
      response = player.getPlayerInput();
    } while (!(response.equals("yes") || response.equals("no")));

    if (response.equals("yes")) {
      boolean hasShipsToPlace = true;
      int numCarriers = 1;
      int numBattleships = 2;
      int numCruisers = 3;
      int numSubmarines = 2;

      while (hasShipsToPlace) {
        System.out.println("");
        System.out.println("You must still place " + numCarriers + " Carriers(C) " + numBattleships
            + " Battleships(B) " + numCruisers + " Cruisers(R) " + numSubmarines
            + " Submarines(S)");
        System.out.println("Place a ship:");

        char[] data = player.getShipPlacement();

        // Next line has a method call inside of an if statement
        if (inputsAreValid(/* Method call arguments -> */data)) {
          char type = data[0];
          int x = Character.getNumericValue(data[1]);
          int y = Character.getNumericValue(data[2]);
          char dir = data[3];
          Ship ship = null;
          switch (type) {
            case Carrier.TYPE: {
              if (numCarriers > 0) {
                ship = new Carrier();
                numCarriers--;
              } else {
                System.out.println("You cannot place anymore Carriers");
                // the continue statement ends the current iteration of the loop
                // and the program continues by once again checking the loop
                // condition of the loop it is nested inside of
                continue;
              }
              break;
            }
            case Battleship.TYPE: {
              if (numBattleships > 0) {
                ship = new Battleship();
                numBattleships--;
              } else {
                System.out.println("You cannot place anymore Battleships");
                continue;
              }
              break;
            }
            case Cruiser.TYPE: {
              if (numCruisers > 0) {
                ship = new Cruiser();
                numCruisers--;
              } else {
                System.out.println("You cannot place anymore Cruisers");
                continue;
              }
              break;
            }
            case Submarine.TYPE: {
              if (numSubmarines > 0) {
                ship = new Submarine();
                numSubmarines--;
              } else {
                System.out.println("You cannot place anymore Submarines");
                continue;
              }
              break;
            }
            default: {
              System.out.println("Your input was invalid. Please Try again.");
              continue;
            }
          }
          if (!ship.place(x, y, dir, playerGrid)) {
            switch (type) {
              case Carrier.TYPE:
                numCarriers++;
                break;
              case Battleship.TYPE:
                numBattleships++;
                break;
              case Cruiser.TYPE:
                numCruisers++;
                break;
              case Submarine.TYPE:
                numSubmarines++;
                break;
              default: //something went wrong
            }
            System.out.println("The ship could not be placed. Please try again.");
            continue;
          }
          System.out.println("");
          printPlayerGrid();
          if (numCarriers + numBattleships + numCruisers + numSubmarines == 0) {
            hasShipsToPlace = false;
          }
        } else {
          System.out.println("Your input was invalid. Please Try again.");
        }
      }
      System.out.println("\nYou are done placing ships.");
    } else {
      populateGridRandomly(playerGrid);
    }

    populateGridRandomly(agentGrid);

    System.out.println("\nHere's a look at the full game");

    printFullGrids();

    while (playerShipsSunk < 8 && agentShipsSunk < 8) {
      System.out.println("Please enter the coordinates \"x, y\" to fire on: ");
      BattleshipCoordinate coords = player.fireOnAgent();
      if (coords == null) {
        System.out.println("Invalid coordinates");
        continue;
      }
      fireOnAgent(coords);
      try {
        Thread.sleep(300);
      } catch (Exception e) {
        //interrupted
      }
      try {
        Thread.sleep(300);
      } catch (Exception e) {
        //interrupted
      }
      agent.fireOnPlayer();
      printFullGrids();
    }
    System.out.println("\nThe game has ended!");
    if (playerShipsSunk == 8) {
      System.out.println("The agent won!");
    } else {
      System.out.println("The player won!");
    }
  }

  /**
   * Methods checks whether or not the player input provided by the player when placing ships is
   * valid (ship type, coordinates, direction).
   * 
   * @param data The input by the player
   * @return True if inputs are valid and False if they are invalid
   */
  private boolean inputsAreValid(char[] data) {
    if (data == null) {
      return false;
    }
    char type;
    int x;
    int y;
    char dir;
    try {
      type = data[0];
      x = Character.getNumericValue(data[1]);
      y = Character.getNumericValue(data[2]);
      dir = data[3];
    } catch (Exception e) {
      return false;
    }
    if (type != Carrier.TYPE && type != Battleship.TYPE && type != Cruiser.TYPE
        && type != Submarine.TYPE && type != Destroyer.TYPE) {
      return false;
    }
    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) {
      return false;
    }
    if (dir != Ship.DIRECTION_HORIZONTAL && dir != Ship.DIRECTION_VERTICAL) {
      return false;
    }
    return true;
  }

  /**
   * Responsible for updating the grids after the player fires on the agent. If location fired upon
   * contains a ship and has not yet been fired upon, the agent grids will be marked with a 'X' and
   * the ship at the location will be damaged. If the ship has been sunk then the result will be
   * added to the message queue to notify the player and the number of ships sunk by the player will
   * be incremented. If the player hits water then the only the hidden agent grid will be updated to
   * show water at the location.
   * 
   * @param coords The coordinates fired upon by the player.
   */
  public void fireOnAgent(BattleshipCoordinate coords) {
    if (agentGrid[coords.getY()][coords.getX()] != 'X'
        && agentGrid[coords.getY()][coords.getX()] != '~') {
      char type = agentGrid[coords.getY()][coords.getX()];
      agentGrid[coords.getY()][coords.getX()] = 'X';
      agentGridHidden[coords.getY()][coords.getX()] = 'X';
      ArrayList<Ship> ships = agentShips.get(type);
      for (Ship ship : ships) {
        if (ship.containsCoordinate(coords.getX(), coords.getY())) {
          String result = ship.damageShip();
          if (result != null) {
            messageQueue.add(result);
          }
          if (ship.isDestroyed()) {
            agentShipsSunk++;
          }
        }
      }
    } else if (agentGrid[coords.getY()][coords.getX()] == '~') {
      agentGridHidden[coords.getY()][coords.getX()] = '~';
    }
  }

  /**
   * Responsible for updating the grids after the agent fires on the player. If location fired upon
   * contains a ship and has not yet been fired upon, the player grid will be marked with a 'X' and
   * the ship at the location will be damaged. If the ship has been sunk then the result will be
   * added to the message queue to notify the player and the number of ships sunk by the agent will
   * be incremented. If the position contains water or has already been fired upon, the position
   * will simply be marked with an 'X' on the player grid.
   * 
   * @param x The x coordinate being fired upon.
   * @param y The y coordinate being fired upon.
   * @return An instance of FireResult which contains whether or not a ship was hit and if the ship
   *         was sunk.
   */
  public FireResult fireOnPlayer(int x, int y) {
    if (playerGrid[y][x] != '~' && playerGrid[y][x] != 'X') {
      char type = playerGrid[y][x];
      playerGrid[y][x] = 'X';
      ArrayList<Ship> ships = playerShips.get(type);
      for (Ship ship : ships) {
        if (ship.containsCoordinate(x, y)) {
          String result = ship.damageShip();
          if (result != null) {
            messageQueue.add(result);
          }
          if (ship.isDestroyed()) {
            playerShipsSunk++;
          }
          return new FireResult(true, ship.isDestroyed());
        }
      }
      return new FireResult(false, false);
    }
    playerGrid[y][x] = 'X';
    return new FireResult(false, false);
  }

  /**
   * Populates the provided grid with ships at random positions.
   * 
   * @param grid The Grid to be populated with ships.
   */
  private void populateGridRandomly(char[][] grid) {
    Random random = new Random();

    Ship ship = new Carrier();
    for (int i = 0; i < 8; i++) {
      if (i == 0) {
        ship = new Carrier();
      } else if (i == 1 || i == 2) {
        ship = new Battleship();
      } else if (i == 3 || i == 4 || i == 5) {
        ship = new Cruiser();
      } else if (i == 6 || i == 7) {
        ship = new Submarine();
      }
      while (!ship.place(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE),
          random.nextInt(2) == 0 ? Ship.DIRECTION_VERTICAL : Ship.DIRECTION_HORIZONTAL, grid)) {
        //do nothing
      }
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

  /**
   * Prints the player grid to the console.
   */
  public void printPlayerGrid() {
    for (int i = 0; i < GRID_SIZE; i++) {
      System.out.println(Arrays.toString(playerGrid[i]));
    }
  }

  /**
   * Prints both the player grid and the hidden agent grid to the console.
   */
  public void printFullGrids() {
    StringBuilder builder = new StringBuilder();
    builder.append("\n");
    for (int i = 0; i < 73; i++) {
      builder.append("-");
    }
    builder.append("\n");
    builder.append("|");
    for (int i = 0; i < 71; i++) {
      builder.append(" ");
    }
    builder.append("|");
    builder.append("\n");
    builder.append("|");
    for (int i = 0; i < 13; i++) {
      builder.append(" ");
    }
    builder.append("AGENT");
    for (int i = 0; i < 34; i++) {
      builder.append(" ");
    }
    builder.append("PLAYER");
    for (int i = 0; i < 13; i++) {
      builder.append(" ");
    }
    builder.append("|");
    builder.append("\n");
    builder.append("|");
    for (int i = 0; i < 71; i++) {
      builder.append(" ");
    }
    builder.append("|");
    builder.append("\n");
    for (int i = 0; i < GRID_SIZE; i++) {
      builder.append("| ");
      builder.append(Arrays.toString(agentGridHidden[i]));
      builder.append("         ");
      builder.append(Arrays.toString(playerGrid[i]));
      builder.append(" |");
      builder.append("\n");
    }
    for (int i = 0; i < 73; i++) {
      builder.append("-");
    }
    builder.append("\n");
    for (String message : messageQueue) {
      builder.append(message);
      builder.append("\n");
    }
    messageQueue.clear();
    builder.append("\n");
    System.out.print(builder.toString());
  }

  /**
   * Represents the result returned to the agent after firing upon the player. Has information about
   * whether or not a ship was hit and if so, whether or not it was sunk.
   * 
   * @author Alexander Thieler
   */
  class FireResult {

    public final boolean hitShip;
    public final boolean sunkShip;

    public FireResult(boolean hitShip, boolean sunkShip) {
      this.hitShip = hitShip;
      this.sunkShip = sunkShip;
    }

  }
}
