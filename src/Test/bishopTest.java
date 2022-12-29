package Test;
import Game.GameMaster;
import Pieces.Bishop;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Rook;

public class bishopTest {
    public bishopTest(){
//        movementTest(true);
        System.out.println(GameMaster.convertChar('h'));
        System.out.println(GameMaster.convertChar('g'));
        System.out.println(GameMaster.convertChar('f'));
        System.out.println(GameMaster.convertChar('e'));
        System.out.println(GameMaster.convertChar('d'));
        System.out.println(GameMaster.convertChar('c'));
        System.out.println(GameMaster.convertChar('b'));
        System.out.println(GameMaster.convertChar('a'));


    }

    public void movementTest(boolean colour){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[3][3] = new Bishop(3,3,colour);

        System.out.println("Up-right Test");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(0,6,chessBoard)? "Passed\n":"Failed\n");
        //down left
        System.out.println("down-right Test");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(7,7,chessBoard)? "Passed\n":"Failed\n");
        //up left
        System.out.println("Up-left Test");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(0,0,chessBoard)? "Passed\n":"Failed\n");
        //down right
        System.out.println("down-left Test");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(6,0,chessBoard)? "Passed\n":"Failed\n");

       chessBoard[6][6] = new Rook(6,6,!colour);
       chessBoard[1][1] = new Rook(1,1,!colour);
       chessBoard[5][1] = new Rook(5,1,!colour);
       chessBoard[1][5] = new Rook(1,5,!colour);
        //up right
        System.out.println("Up-right Test with blocking piece");
        GameMaster.printer(chessBoard);
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(0,6,chessBoard)? "Failed\n":"Passed\n");
        //down left
        System.out.println("down-right Test with blocking piece");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(7,7,chessBoard)? "Failed\n":"Passed\n");
        //up left
        System.out.println("Up-left Test with blocking piece");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(0,0,chessBoard)? "Failed\n":"Passed\n");
        //down right
        System.out.println("down-left Test with blocking piece");
        System.out.println(((Bishop)chessBoard[3][3]).isLegalMove(6,0,chessBoard)? "Failed\n":"Passed\n");
    }

    public static void main(String[] args){
        new bishopTest();

    }

}
