public class Main {
    public static void main(String[] args) {
        //[][]
        int[][] sudokoBoard9x9Grid = {
                {6,2,4,5,3,9,1,8,7},
                {5,1,9,7,2,8,6,3,4},
                {8,3,7,6,1,4,2,9,5},
                {1,4,3,8,6,5,7,2,9},
                {9,5,8,2,4,7,3,6,1},
                {7,6,2,3,9,1,4,5,8},
                {3,7,1,9,5,6,8,4,2},
                {4,9,6,1,8,2,5,7,3},
                {2,8,5,4,7,3,9,1,6}
        } ;
        int[][] sudokoBoard25x25Grid = {
                {6,2,4,5,3,9,1,8,7},
                {5,1,9,7,2,8,6,3,4},
                {8,3,7,6,1,4,2,9,5},
                {1,4,3,8,6,5,7,2,9},
                {9,5,8,2,4,7,3,6,1},
                {7,6,2,3,9,1,4,5,8},
                {3,7,1,9,5,6,8,4,2},
                {4,9,6,1,8,2,5,7,3},
                {2,8,5,4,7,3,9,1,6}
        } ;
        // 9x9 grid:
        SudokuValidator suduko9x9 = new SudokuValidator(sudokoBoard9x9Grid);
        // naiveApproach 9x9
        suduko9x9.checkValidSudokuNaiveApproach();
        // multithreading 9x9
        suduko9x9.checkValidSudokuMultithreadingApproachUsing11Threads();
        // multithreadingV2 9x9
        suduko9x9.checkValidSudokuMultithreadingApproachUsing27Threads();

/*
        // 25x25 grid:
        SudokuValidator suduko25x25 = new SudokuValidator(sudokoBoard25x25Grid);
        // naiveApproach 25x25
        suduko25x25.checkValidSudokuNaiveApproach();
        // multithreading 25x25
        suduko25x25.checkValidSudokuMultithreadingApproachUsing27Threads();
        // multithreadingV2 25x25
        suduko25x25.checkValidSudokuMultithreadingApproachUsing27Threads();
//*/

    }
}