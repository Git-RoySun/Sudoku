import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuInterface {
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        char screen[][] = new char[11][22];
        Sudoku game = new Sudoku();
        String input[]= new String[3];

        for(int x=0;x<screen.length;x++){
            for(int y=0;y<screen[x].length;y++){
                screen[x][y]=' ';
            }
        }
        String filename="";
        boolean validFile = false;
        while(!validFile) {
        System.out.print("Enter file name: ");
        filename = keyboard.nextLine();
            switch (game.initialise(filename)) {
                case 0:
                    validFile=true;
                    System.out.println("Type Q at any time to exit the game, S to save the game or U to undo your last move");
                    break;
                case 1:
                    validFile=false;
                    System.out.println("The file format is incorrect, please try again");
                    break;
                case 2:
                    validFile=false;
                    System.out.println("The file was not found, please try again");
                    break;
                case 3:
                    validFile=false;
                    System.out.println("The puzzle has already been solved, please try another file");
                    break;
                case 4:
                    validFile=false;
                    System.out.println("The puzzle cannot be solved, please try another file");
                    break;
            }
        }

        do{
            int offset=0,count=0;
            for(int x =0;x<screen.length;x++){
                for(int y=0; y<screen[x].length;y++){
                    if(x!=3&&x!=7) {
                        if (y % 2 == 0 && (y != 6 && y != 14)) {
                            if(y<6)
                                screen[x][y] = Character.forDigit(game.getBoard(x+offset, y/ 2),10);

                            else if(y<14)
                                screen[x][y] = Character.forDigit(game.getBoard(x+offset, (y - 2)/ 2),10);

                            else{
                                screen[x][y] = Character.forDigit(game.getBoard(x+offset, (y - 4)/ 2),10);
                            }
                        }
                        else if(y == 6 || y == 14){
                            screen[x][y]='|';
                        }
                    }
                    else{
                        screen[x][y]= '-';
                    }
                    if (screen[x][y] == '0') {
                        System.out.print("*");
                    }
                    else
                        System.out.print(screen[x][y]);
                }
                count++;
                if(count>3) {
                    offset--;
                    count = 0;
                }
                System.out.print("\n");
            }
            String focus[] = {"row: ","col: ","value: "};
            int x=0;
            boolean skip = false;
            while(x<focus.length&&!skip) {
                System.out.print("Enter " + focus[x]);
                input[x] = keyboard.next();
                if (input[x].length() == 1){
                    switch (input[x].charAt(0)) {
                        case 'u':
                        case 'U':
                            if (game.undoMove())
                                System.out.println("Last move has been undone");
                            else
                                System.out.println("Unable to undo last move");
                            skip = true;
                            break;
                        case 's':
                        case 'S':
                            game.saveGame();
                            System.out.println("Game has been saved");
                            skip = true;
                            break;
                        case 'q':
                        case 'Q':
                            System.out.print("See you next time!");
                            System.exit(0);
                            break;
                        default:
                            if(!input[x].matches("^[1-9]$")){
                                System.out.println("Input must be either a number between 1 and 9 or a command character (s, u, q)");
                                skip=true;
                            }
                            else if(x==2){
                                switch (game.makeMove(Integer.parseInt(input[0])-1, Integer.parseInt(input[1])-1, Integer.parseInt(input[2]))){
                                    case 0:
                                        System.out.println("[" + input[0] + ", " + input[1] + "] has been set to " + input[2]);
                                        break;
                                    case 1:
                                        System.out.println("The number is out of range, please enter a number between 1 and 9.");
                                        break;
                                    case 2:
                                        System.out.println("There already is a number there, please try again.");
                                        break;
                                    case 3:
                                        System.out.println( input[2] +" is already present on the same column, please try again.");
                                        break;
                                    case 4:
                                        System.out.println( input[2] +" is already present on the same row, please try again.");
                                        break;
                                    case 5:
                                        System.out.println( input[2] +" is already present on the same 3x3 grid, please try again.");
                                        break;
                                }
                            }
                            break;
                    }
                    x++;
                }
                else{
                    System.out.println("Input must be only 1 character long, please try again");
                }
            }
        }while(!(game.checkWin()||game.checkLose()));

        if(game.checkWin()) {
            game.saveGame();
            System.out.print("Congratulations, you have completed the puzzle");
            System.exit(0);
        }
        else if(game.checkLose()) {
            game.saveGame();
            System.out.print("Sorry, you have failed the puzzle");
            System.exit(0);
        }
    }
}