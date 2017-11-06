import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 *  in the non-OO version.
 */
public class tictactoe {
   // Name-constants to represent the seeds and cell contents
   public static final int EMPTY = 0;
   public static final int CROSS = 1;
   public static final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   public static final int PLAYING = 0;
   public static final int DRAW = 1;
   public static final int CROSS_WON = 2;
   public static final int NOUGHT_WON = 3;
 
   // The game board and the game status
   public static int ROWS = 3, COLS = 3; // number of rows and columns
   public static int[][] board; // game board in 2D array
                                                      //  containing (EMPTY, CROSS, NOUGHT)
   public static int currentState;  // the current state of the game
                                    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
   public static int availableCellsLeft;
   public static int currentPlayer; // the current player (CROSS or NOUGHT)
   public static int currntRow, currentCol; // current seed's row and column
 
   public static Scanner in = new Scanner(System.in); // the input Scanner
 
   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
      // Initialize the game-board and current status
      System.out.print("How big a board do you wish to play?: ");
      int size = in.nextInt();

      initGame(size);
      // Play the game once
      do {
         playerMove(currentPlayer); // update currentRow and currentCol
         updateGame(currentPlayer, currntRow, currentCol); // update currentState
         printBoard();
         // Print message if game-over
         if (currentState == CROSS_WON) {
            System.out.println("'X' won! Bye!");
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' won! Bye!");
         } else if (currentState == DRAW) {
            System.out.println("It's a Draw! Bye!");
         }
         // Switch player
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game-over
   }
 
   /** Initialize the game-board contents and the current states */
   public static void initGame(int size) {
      ROWS = COLS = size;
      board = new int[ROWS][COLS];
      availableCellsLeft = ROWS * COLS;

      for (int row = 0; row < size; ++row) {
         for (int col = 0; col < size; ++col) {
            board[row][col] = EMPTY;  // all cells empty
         }
      }
      currentState = PLAYING; // ready to play
      currentPlayer = CROSS;  // cross plays first
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   public static void playerMove(int theSeed) {
      boolean validInput = false;  // for input validation
      do {

         String playerName = (theSeed == CROSS)? "X" : "O";
         System.out.print("Player '"+playerName+"', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");

         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
            currntRow = row;
            currentCol = col;
            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   public static void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
 
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win

   //optimization from O(N^2) to O(1) runtime
   public static boolean isDraw() {
      //counts down available cells until none are left
      //declare a draw when no empty cells left for play
      availableCellsLeft--;
      return (availableCellsLeft>0)? false : true;
   }

   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */

   //Checks for winning conditions in N size boards, no hard coding of solutions
   public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
        //check col
        for(int i = 0; i < ROWS; i++){
            if(board[currentRow][i] != theSeed)
                break;
            if(i == ROWS-1){
              return true;
            }
        }
        //check row
        for(int i = 0; i < ROWS; i++){
            if(board[i][currentCol] != theSeed)
                break;
            if(i == ROWS-1){
              return true;
            }
        }
        //check diagonal
        if(currentRow == currentCol){
            for(int i = 0; i < ROWS; i++){
                if(board[i][i] != theSeed)
                    break;
                if(i == ROWS-1){
                  return true;
                }
            }
        }
        //check opposite diagonal
        if((currentRow + currentCol) == (ROWS - 1)){
            for(int i = 0;i<ROWS;i++){
                if(board[i][(ROWS-1)-i] != theSeed)
                    break;
                if(i == ROWS-1){
                  return true;
                }
            }
        }
        return false;
   }

   /** Print the game board */
   public static void printBoard() {
      String x_partition = "";
      for(int length =0 ; length<ROWS;++length){
          x_partition+= (length==0)? "---" : "----";
      }

      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            printCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();

         if (row != ROWS - 1) {
            System.out.println(x_partition); // print horizontal partition
         }
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   public static void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print("   "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }
}