import java.util.ArrayList;

public class SudokuValidator {
    private static int n;
    public static int[][] sudokuBoard;
    private static ArrayList<Boolean> resultsForTheCurrentThreadsApproach;
    public static boolean[] allApproachesValidation = new boolean[3];
    public SudokuValidator(int[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        n= sudokuBoard.length;
        resultsForTheCurrentThreadsApproach = new ArrayList<>();
    }

    // checkValidCol
    static class ColumnValidator implements Runnable{

        public ColumnValidator(){
        }
        public void run(){
            // call the checkValidCol
        }
    }
    public static boolean checkValidCol(int colNumber){
        boolean [] seen = new boolean[n];
        for(int i = 0;i < n;i++){
            if(seen[sudokuBoard[i][colNumber] - 1] == true) return false;
            seen[sudokuBoard[i][colNumber] - 1] = true;
        }
        return true;
    }
    //checkValidRow

    // edit ff
    public static boolean checkValidRow(int[] sudokuBoardRow){
        boolean [] seen = new boolean[n];
        for(int i = 0;i < n;i++){
            if(seen[sudokuBoardRow[i] -1]  == true) return false;
            seen[sudokuBoardRow[i] - 1] = true;
        }
        return true;

    }

    //checkValidGrid
    public static boolean checkValidGrid(int col, int row){
        int m = (int) Math.sqrt(n); // grid length
        boolean seen[] = new boolean[n];
        for(int i = row;i < row + m;i++){
            for(int j = col;j < col+m;j++){
                if(seen[sudokuBoard[i][j] -1] == true) return false;
                seen[sudokuBoard[i][j]] = true;
            }
        }
        return true;
    }

    public static void checkValidSudokuNaiveApproach(){
        // naive approach

        boolean result = false;
        /*
            col loops
            //checkValidCol

         */
        // loop valid row
        // loop valid grid


        allApproachesValidation[0] = result;
    }

    public static void checkValidSudokuMultithreadingApproachUsing11Threads(){
        resultsForTheCurrentThreadsApproach.clear();

        boolean flag = true;
        for(boolean iterator : resultsForTheCurrentThreadsApproach){
            flag = flag && iterator;
        }
        allApproachesValidation[1] = flag;
    }


    public static void checkValidSudokuMultithreadingApproachUsing27Threads(){
        resultsForTheCurrentThreadsApproach.clear();
        long startTime = System.nanoTime();
        boolean validThread[] = new boolean[27];

        //threads //
        // naive approach

        long endTime = System.nanoTime();
        boolean flag = true;
        for(boolean iterator : validThread){
            flag = flag && iterator;
        }
        allApproachesValidation[2] = flag;
        long totalTime = startTime-endTime;
        System.out.println("Total Time: "+totalTime);
        System.out.println("Is Valid");
    }


}
