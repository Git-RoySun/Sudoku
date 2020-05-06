import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Sudoku {
    private static final int BOARDSIZE = 9;
    private int board[][] = new int[BOARDSIZE][BOARDSIZE];
    private File boardFile;
    private Scanner boardFileReader;

    private boolean undo = false;
    private int inputRow =0;
    private int inputCol =0;
    private int input =0;

    public Sudoku(){
    }//Sudoku constructor

    public int initialise(String _fileName){
        boolean skip = false;
        try{
            if(_fileName.length()>0)
                boardFile = new File(_fileName);
            else
                boardFile = new File("sudoku.txt");
            Scanner boardFileChecker = new Scanner(boardFile);
            boardFileChecker.useDelimiter("\n");
            boardFileReader = new Scanner(boardFile);
            boardFileReader.useDelimiter("~|\n");
            int rows=0;
            while(boardFileChecker.hasNext()){
                String line = boardFileChecker.next();
                StringTokenizer st = new StringTokenizer(line,"~");
                if(st.countTokens()!=9){
                    skip = true;
                    return 1;
                }
                else{
                    while(st.hasMoreTokens()){
                        if(!st.nextToken().matches("^[1-9*]$")){
                            skip = true;
                            return 1;
                        }
                    }
                }
                rows++;
            }
            if(rows!=9){
                skip = true;
                return 1;
            }
            if(!skip) {
                int row = 0, col = 0;
                while (boardFileReader.hasNext()) {
                    String fileInput = boardFileReader.next();
                    if (!fileInput.equals("*") && !fileInput.equals("")) {
                        board[row][col] = Integer.parseInt(fileInput);
                    }
                    col += 1;
                    if (col == 10) {
                        col = 0;
                        row += 1;
                    }
                }
                if(checkWin())
                    return 3;
                else if(checkLose())
                    return 4;
                else
                    return 0;
            }
        }
        catch (FileNotFoundException e) {
            return 2;
        }
        return 0;
    }
    private boolean checkRow(int _row, int _input){
        boolean valid = true;
        for(int x=0;x<board[_row].length;x++){
            if(board[_row][x]==_input){
                valid = false;
                break;
            }
        }
        return valid;
    }
    private boolean checkCol(int _col, int _input){
        boolean valid = true;
        for(int x=0;x<board.length;x++){
            if(board[x][_col]==_input){
                valid = false;
                break;
            }
        }
        return valid;
    }
    private boolean checkSquare(int _row, int _col, int _input){
        boolean valid = true;
        int sqrRow = (_row/3)*3;
        int sqrCol = (_col/3)*3;
        for(int x = sqrRow; x<sqrRow+3;x++){
            for(int y=sqrCol;y<sqrCol+3;y++){
                if(board[x][y]==_input){
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }
    private boolean checkRange(int _input){
        return _input>0&&_input<10;
    }
    private boolean checkSpot(int _row, int _col){
        return board[_row][_col]==0;
    }
    private boolean checkLegal(int _row, int _col, int _input){
        if(checkSpot(_row,_col)&&checkRange(_input)){
            return checkRow(_row,_input)&&checkCol(_col,_input)&&checkSquare(_row,_col,_input);
        }
        else
            return false;
    }
    public boolean checkWin(){
        boolean win = true;
        for(int x = 0;x<board.length;x++){
            for(int y = 0;y<board[x].length;y++){
                if (board[x][y] == 0) {
                    win = false;
                    break;
                }
            }
        }
        return win;
    }
    public boolean checkLose(){
        boolean lose = true;
        for(int x = 0;x<board.length;x++){
            for(int y = 0;y<board[x].length;y++){
                if(board[x][y]==0){
                    for(int z=1;z<10;z++){
                        if(checkLegal(x,y,z)){
                            lose = false;
                            break;
                        }
                    }
                }
            }
        }
        return lose;
    }
    public int makeMove(int _row, int _col, int _input){
            if(!checkRange(_input))
                return 1;
            else if (!checkSpot(_row,_col))
                return 2;
            else if(!checkCol(_col,_input))
                return 3;
            else if(!checkRow(_row,_input))
                return 4;
            else if(!checkSquare(_row,_col,_input))
                return 5;
            else {
                inputRow = _row;
                inputCol = _col;
                input = _input;
                board[inputRow][inputCol] = input;
                undo = true;
                return 0;
            }
    }
    public boolean undoMove(){
        if(undo){
            board[inputRow][inputCol] = 0;
            undo = false;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean saveGame(){
        try {
            FileWriter boardFileWriter = new FileWriter(boardFile,true);
            FileWriter eraser = new FileWriter(boardFile);
            eraser.write("");
            eraser.close();
            for(int x=0;x<BOARDSIZE;x++){
                for(int y=0;y<BOARDSIZE;y++){
                    if(board[x][y]==0){
                        boardFileWriter.write("*~");
                    }
                    else {
                        boardFileWriter.write(board[x][y]+"~");
                    }
                }
                boardFileWriter.write("\n");
            }
            boardFileWriter.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public int getBoard(int _row, int _col){
        return board[_row][_col];
    }
}
