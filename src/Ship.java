// Alexander Thieler

public abstract class Ship {

  public final int SIZE;
  public final char TYPE;
  
  public Ship(int size, char type) {
    this.SIZE = size;
    this.TYPE = type;
  }
  
  public boolean place(int x, int y, char dir, char[][] playerGrid) {
    if (dir == 'v') {
      if (y + SIZE > BattleshipGame.GRID_SIZE) return false;
      for (int i = 0; i < SIZE; i++) {
        if (playerGrid[y + i][x] != '~') return false;
      }
      for (int i = 0; i < SIZE; i++) {
        playerGrid[y + i][x] = TYPE;
      }
    } else {
      if (x + SIZE > BattleshipGame.GRID_SIZE) return false;
      for (int i = 0; i < SIZE; i++) {
        if (playerGrid[y][x + i] != '~') return false;
      }
      for (int i = 0; i < SIZE; i++) {
        playerGrid[y][x + i] = TYPE;
      }
    }
    return true;
  }
  
}
