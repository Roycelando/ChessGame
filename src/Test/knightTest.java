package Test;


import Pieces.Knight;
import Pieces.Piece;
//import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import javax.crypto.spec.PSource;
import java.sql.SQLOutput;

public class knightTest {

    public knightTest(){
        //movementTest(false);
        //captureTest(false);
    }

    public void movementTest(boolean colour){
        System.out.println(colour?"White piece test\n":"Blakc piece test\n");
        Piece[][] chessBoard= new Piece[8][8];

        //test 1 basic movement
        System.out.println("trying to move knight to (2,5),(4,5),(,5,4),(5,2),(4,1),(2,1),(1,2),(1,4)");
       chessBoard[3][3] = new Knight(3,3,colour);
        System.out.println(chessBoard[3][3].isLegalMove(2,5,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(4,5,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(5,4,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(5,2,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(4,1,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(2,1,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(1,2,chessBoard)?"Passed":"Failed");
        System.out.println(chessBoard[3][3].isLegalMove(1,4,chessBoard)?"Passed":"Failed");

        //illegal move test
        System.out.println("\nTrying to make illegal moves");
        System.out.println(chessBoard[3][3].isLegalMove(6,0,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(1,1,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(2,2,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(4,4,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(1,5,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(4,4,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(5,5,chessBoard)?"Failed":"Passed");
        System.out.println(chessBoard[3][3].isLegalMove(5,1,chessBoard)?"Failed":"Passed");


    }

    public void captureTest(boolean colour){

        Piece[][] chessBoard= new Piece[8][8];

        System.out.println("trying to capture knights on (1,4), (2,5), (,4,5), (5,4), (5,2), (4,1), (2,1), (1,2)");
       chessBoard[3][3] = new Knight(3,3,colour);

        //
       chessBoard[1][4] = new Knight(1,4,!colour);
       chessBoard[2][5] = new Knight(2,5,!colour);
       chessBoard[4][5] = new Knight(4,5,!colour);
       chessBoard[5][4] = new Knight(5,4,!colour);
       chessBoard[5][2] = new Knight(5,2,!colour);
       chessBoard[4][1] = new Knight(4,1,!colour);
       chessBoard[2][1] = new Knight(2,1,!colour);
       chessBoard[1][2] = new Knight(1,2,!colour);

        //trying to caputre pieces
        System.out.println((chessBoard[3][3]).isLegalMove(1,4,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(2,5,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(4,5,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(5,4,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(5,2,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(4,1,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(2,1,chessBoard)?"Passed":"Failed");
        System.out.println((chessBoard[3][3]).isLegalMove(1,2,chessBoard)?"Passed":"Failed");

       chessBoard[1][4] = new Knight(1,4,colour);
       chessBoard[2][5] = new Knight(2,5,colour);
       chessBoard[4][5] = new Knight(4,5,colour);
       chessBoard[5][4] = new Knight(5,4,colour);
       chessBoard[5][2] = new Knight(5,2,colour);
       chessBoard[4][1] = new Knight(4,1,colour);
       chessBoard[2][1] = new Knight(2,1,colour);
       chessBoard[1][2] = new Knight(1,2,colour);
        System.out.println();

        //illegal capture test
        System.out.println((chessBoard[3][3]).isLegalMove(1,4,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(2,5,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(4,5,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(5,4,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(5,2,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(4,1,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(2,1,chessBoard)?"Failed":"Passed");
        System.out.println((chessBoard[3][3]).isLegalMove(1,2,chessBoard)?"Failed":"Passed");

    }

    public static void main(String[] args){
        new knightTest();
    }
}
