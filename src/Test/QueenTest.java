package Test;

import Pieces.Piece;
import Pieces.Queen;

public class QueenTest {

    public QueenTest(){
        movementTest(true);

    }

    public void movementTest(boolean colour){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[7][0] = new Queen(7,0,colour);
        System.out.println(chessBoard[7][0].isLegalMove(0,0,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[7][0].isLegalMove(7,7,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[7][0].isLegalMove(0,7,chessBoard)?"Passed":"Failed");

    }

    public static void main(String[] args){
        new QueenTest();
    }
}
