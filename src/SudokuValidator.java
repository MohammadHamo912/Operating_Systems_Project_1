import java.util.ArrayList;

public class SudokuValidator {
    private static int n;
    public static int[][] sudokuBoard;
    private static ArrayList<Boolean> resultsForTheCurrentThreadsApproach;
    public static boolean[] allApproachesValidation = new boolean[3];
    public SudokuValidator(int[][] sudokuBoard) {
        SudokuValidator.sudokuBoard = sudokuBoard;
        n= sudokuBoard.length;
        resultsForTheCurrentThreadsApproach = new ArrayList<>();
    }

    // checkValidCol
    static class ColumnValidator11Threads implements Runnable{
        public ColumnValidator11Threads(){
        }
        public void run(){
            boolean result = true;
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
            boolean result = true;
            for(int i=0; i<n; i++){
                result =result && checkValidRow(sudokuBoard[i]);
            }
            resultsForTheCurrentThreadsApproach.add(result);
        }
    }

    static class ColumnValidator27Threads implements Runnable{
        int col;

        public ColumnValidator27Threads(int col){
            this.col = col;

        }
        public void run(){
            resultsForTheCurrentThreadsApproach.add(checkValidCol(col));
        }
    }
    static class RowValidator27Threads implements Runnable{
        int row;
        public RowValidator27Threads(int row){
            this.row = row;
        }
        public void run(){
            resultsForTheCurrentThreadsApproach.add(checkValidRow(sudokuBoard[row]));
        }
    }

    //Grid validator will be in-common for the 11Thread approach and the 27 thread approach
    static class GridValidator implements Runnable{
        int row,col;
        public GridValidator(int row,int col){
            this.row = row;
            this.col =col;
        }
        public void run(){
            resultsForTheCurrentThreadsApproach.add(checkValidGrid(row,col));
        }
    }

    public static boolean checkValidCol(int colNumber){
        boolean [] seen = new boolean[n];
        for(int i = 0;i < n;i++){
            if(seen[sudokuBoard[i][colNumber] - 1]) return false;
            seen[sudokuBoard[i][colNumber] - 1] = true;
        }
        return true;
    }
    //checkValidRow

    // edit ff
    public static boolean checkValidRow(int[] sudokuBoardRow){
        boolean [] seen = new boolean[n];
        for(int i = 0;i < n;i++){
            if(seen[sudokuBoardRow[i] -1]) return false;
            seen[sudokuBoardRow[i] - 1] = true;
        }
        return true;

    }

    //checkValidGrid
    public static boolean checkValidGrid(int col, int row){
        int m = (int) Math.sqrt(n); // grid length
        boolean[] seen = new boolean[n];
        for(int i = row;i < row + m;i++){
            for(int j = col;j < col+m;j++){
                if(seen[sudokuBoard[i][j] -1]) return false;
                seen[sudokuBoard[i][j] -1] = true;
            }
        }
        return true;
    }

    public static void checkValidSudokuNaiveApproach(){
        resultsForTheCurrentThreadsApproach.clear();
        // naive approach
        long startTime = System.nanoTime();
        int m = (int) Math.sqrt(n);
        for(int i =0;i < n;i++){
            resultsForTheCurrentThreadsApproach.add(checkValidRow(sudokuBoard[i]));
        }

        for (int i =0;i<n;i++){
            resultsForTheCurrentThreadsApproach.add(checkValidCol(i));
        }
        for(int i =0;i <n;i+=m){
            for(int j =0;j < n;j+=m){
                resultsForTheCurrentThreadsApproach.add(checkValidGrid(i,j));
            }
        }

        long endTime = System.nanoTime();

        boolean flag = true;
        for(boolean iterator : resultsForTheCurrentThreadsApproach){
            flag = flag && iterator;
        }
        allApproachesValidation[0] = flag;
        long totalTime = endTime - startTime;
        System.out.println("Total Time: "+totalTime);
        System.out.println("Is Valid: "+flag);


     }


    public static void checkValidSudokuMultithreadingApproachUsing11Threads(){
        resultsForTheCurrentThreadsApproach.clear();


        long startTime = System.nanoTime();
        Thread rowThread = new Thread(new RowValidator11Threads());
        Thread columnThread = new Thread(new ColumnValidator11Threads());
        Thread[] gridThreads = new Thread[n];
        rowThread.start();
        int m =(int) Math.sqrt(n);
        columnThread.start();
        int index=0;
        for (int row = 0; row < n; row += m) {
            for (int col = 0; col < n; col += m) {
                gridThreads[index] = new Thread(new GridValidator(row, col));
                gridThreads[index].start();
                index++;
            }
        }

        for(int i=0; i<index; i++){
            try {
                gridThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            rowThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            columnThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        boolean flag = true;
        for(boolean iterator : resultsForTheCurrentThreadsApproach){
            flag = flag && iterator;
        }
        allApproachesValidation[1] = flag;
        long totalTime = endTime - startTime;
        System.out.println("Total Time: "+totalTime);
        System.out.println("Is Valid: "+flag);
    }


    public static void checkValidSudokuMultithreadingApproachUsing27Threads(){
        resultsForTheCurrentThreadsApproach.clear();
        long startTime = System.nanoTime();

        Thread[] rowThreads = new Thread[9];
        Thread[] colThreads = new Thread[9];
        Thread[] gridThreads = new Thread[9];
        int j = 0;
        int m = (int) Math.sqrt(n);
        for(int i = 0;i < n;i++){
            rowThreads[i] = new Thread(new ColumnValidator27Threads(i));
            rowThreads[i].start();
            colThreads[i] = new Thread(new RowValidator27Threads(i));
            colThreads[i].start();
            if(i%m ==0){
                for(int col = 0;col < n;col +=m){
                    gridThreads[j] = new Thread(new GridValidator(i,col));
                    gridThreads[j++].start();
                }
            }
        }

        for(int i =0;i < n;i++){
            try {
                rowThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                colThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }try {
                gridThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        long endTime = System.nanoTime();
        boolean flag = true;
        for(boolean iterator : resultsForTheCurrentThreadsApproach){
            flag = flag && iterator;
        }
        allApproachesValidation[2] = flag;
        long totalTime = endTime-startTime;
        System.out.println("Total Time: "+totalTime);
        System.out.println("Is Valid: "+ flag);
    }


}
