package Test;


import Pieces.King;
import Pieces.Piece;

public class kingTest {


    public kingTest(){
        movementTest();
    }


    public void movementTest(){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[3][3] = new King(3,3,true);
       chessBoard[1][3] = new King(1,3,false);

        System.out.println(chessBoard[3][3].isLegalMove(2,3,chessBoard)? "failed": "passed");

    }

    public static void main(String[] args){
        new kingTest();

    }
}
