import java.util.ArrayList;

public class SudokuValidator {
    private static int n;
    public static int[][] sudokuBoard;
    int m = (int) Math.sqrt(n);
    private static ArrayList<Boolean> resultsForTheCurrentThreadsApproach;
    public static boolean[] allApproachesValidation = new boolean[3];
    public SudokuValidator(int[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        n= sudokuBoard.length;
        resultsForTheCurrentThreadsApproach = new ArrayList<>();
    }

    // checkValidCol
    static class ColumnValidator11Threads implements Runnable{
        public ColumnValidator11Threads(){
        }
        public void run(){
            boolean result;
            for(int i=0; i<n; i++){
                result =result && checkValidCol(i);
            }
            resultsForTheCurrentThreadsApproach.add(result);
        }
    }

    static class RowValidator11Threads implements Runnable{
        public RowValidator11Threads(){
        }
        public void run(){
            boolean result;
            for(int i=0; i<n; i++){
                result =result && checkValidRow(i);
            }
            resultsForTheCurrentThreadsApproach.add(result);
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
        long startTime = System.nanoTime();
        Thread rowThread = new Thread(RowValidator11Threads());
        Thread columnThread = new Thread(ColumnValidator11Threads());
        Thread gridThreads[n];
        rowThread.start();
        columnThread.start();
        int index=0;
        for (int row = 0; row < n; row += m) {
            for (int col = 0; col < n; col += m) {
                gridThreads[index] = new Thread(new GridValidator(row, col));
                gridThreads[index].start();
                index++;
            }
        }
        rowThread.join();
        columnThread.join();
        for(int i=0; i<index; i++){
            gridThreads[i].join();
        }
        long endTime = System.nanoTime();
        boolean flag = true;
        for(boolean iterator : resultsForTheCurrentThreadsApproach){
            flag = flag && iterator;
        }
        allApproachesValidation[1] = flag;
        long totalTime = startTime-endTime;
        System.out.println("Total Time: "+totalTime);
        System.out.println("Is Valid: "+flag);
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
