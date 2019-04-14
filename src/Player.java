//Alexander Thieler

import java.util.Scanner;

public class Player {
  
  private Scanner scanner;
  
  public Player() {
    this.scanner = new Scanner(System.in);
  }
  
  public String getPlayerInput() {
    return scanner.nextLine();
  }
  
  public String[] getShipPlacement() {
    String[] data = new String[4];
    try {
      data[0] = scanner.next();
      data[1] = scanner.next();
      data[2] = scanner.next();
      data[3] = scanner.next();
    } catch (Exception e){
      scanner.nextLine();
      return null;
    }
    scanner.nextLine();
    return data;
  }
  
  public int[] fireOnAgent() {
    int[] coords = new int[2];
    try {
      coords[0] = scanner.nextInt();
      coords[1] = scanner.nextInt();
      if (coords[0] >= BattleshipGame.GRID_SIZE || coords[1] >= BattleshipGame.GRID_SIZE) {
        throw new Exception();
      }
    } catch (Exception e) {
      return null;
    }
    return coords;
  }
  
  public void closeScanner() {
    scanner.close();
  }

}
