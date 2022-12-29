package Test;

import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Rook;
//import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

public class rookTest {

    public rookTest(){
        movementTest(false);
        //movementTest(true);
        //captureTest(true);
        //captureTest(false);
    }
    public void movementTest(boolean colour){
        System.out.println(colour? "White rook test": "Black rook test\n");
        Piece[][] chessBoard= new Piece[8][8];

        //Test one moving vertically
       chessBoard[7][1] = new Pawn(7,1,colour);
       chessBoard[7][0] = new Rook(7,0,colour);
        System.out.println("Test set 1: moving vertically\n");

        System.out.println("Testing if I can move rook to a position blocked by a pawn");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(7,1,chessBoard)? "Test Failed: piece is blocking ": "Test passed: piece blocking");







        System.out.println("\nTesting if I can move to the square after removing the pawn");
       chessBoard[2][0] = null;
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(1,0,chessBoard)? "Passed": "Failed");


        System.out.println("\nTesting if I can move upward to an empty square");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(3,0,chessBoard)? "Passed": "Failed");

        System.out.println("\nTest set two: moving horizontally\n");

       chessBoard[7][5] = new Pawn(7,5, colour);
        System.out.println("Testing if I can move rook to a position blocked by a white pawn");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(7,6,chessBoard)? "Test Failed: piece is blocking ": "Test passed: piece blocking");


        System.out.println("\nTesting if I can move to the square after removing the pawn");
       chessBoard[7][5] = null;
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(7,6,chessBoard)? "Passed": "Failed");

        System.out.println("\nTesting if I can move horizontally to an empty square");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(7,7,chessBoard)? "Passed": "Failed");

        //diagonal test

        System.out.println("\nTesting that I cant move diagonally");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(6,1,chessBoard)? "Failed": "Passed");
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(5,2,chessBoard)? "Failed": "Passed");


    }

    public void captureTest(boolean colour){
        System.out.println(colour? "White rook test": "Black rook test");

        System.out.println("This tes puts the rook on (0,0) the and the opposite coloured pawns on (0,7), (7,7),and(7,0) we then test if the rook can successfully capture each piece");
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[0][0] = new Rook(0,0, colour);
       chessBoard[0][7] = new Pawn(0,7,!colour);
       chessBoard[7][7] = new Pawn(7,7,!colour);
       chessBoard[7][0] = new Pawn(7,0,!colour);

        // Testing if  I can capture 3 pawns located on the corner of the boards
        System.out.println(((Rook)chessBoard[0][0]).isLegalMove(0,7,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[0][0]).updatePosition(0,7);
       chessBoard[0][7] =chessBoard[0][0];
       chessBoard[0][0] = null;
        System.out.println(((Rook)chessBoard[0][7]).isLegalMove(7,7,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[0][7]).updatePosition(7,7);
       chessBoard[7][7] =chessBoard[0][7];
       chessBoard[0][7] = null;
       // System.out.println("X position: "+((Rook)chessBoard[7][7]).getXPos()+ " Y postion: "+ ((Rook)chessBoard[7][7]).getYPos());
        System.out.println(((Rook)chessBoard[7][7]).isLegalMove(7,0,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[7][7]).updatePosition(7,0);
       chessBoard[7][0] =chessBoard[7][7];
       chessBoard[7][7] = null;
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(0,0,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[7][0]).updatePosition(0,0);
       chessBoard[0][0] =chessBoard[7][0];
       chessBoard[7][0] = null;


        System.out.println("\nThis tes puts the rook on (0,0) the and the opposite coloured pawns on (7,0), (7,7),and(0,7) we then test if the rook can successfully capture each piece");

       chessBoard[0][0] = new Rook(0,0, colour);
       chessBoard[7][0] = new Pawn(0,7,!colour);
       chessBoard[7][7] = new Pawn(7,7,!colour);
       chessBoard[0][7] = new Pawn(7,0,!colour);

        // Testing if  I can capture 3 pawns located on the corner of the boards
        System.out.println(((Rook)chessBoard[0][0]).isLegalMove(7,0,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[0][0]).updatePosition(7,0);
       chessBoard[7][0] =chessBoard[0][0];
       chessBoard[0][0] = null;
        System.out.println(((Rook)chessBoard[7][0]).isLegalMove(7,7,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[7][0]).updatePosition(7,7);
       chessBoard[7][7] =chessBoard[7][0];
       chessBoard[7][0] = null;
        // System.out.println("X position: "+((Rook)chessBoard[7][7]).getXPos()+ " Y postion: "+ ((Rook)chessBoard[7][7]).getYPos());
        System.out.println(((Rook)chessBoard[7][7]).isLegalMove(7,0,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[7][7]).updatePosition(0,7);
       chessBoard[0][7] =chessBoard[7][7];
       chessBoard[7][7] = null;
        System.out.println(((Rook)chessBoard[0][7]).isLegalMove(0,0,chessBoard)? "Passed" :"failed");
        ((Rook)chessBoard[0][7]).updatePosition(0,0);
       chessBoard[0][0] =chessBoard[0][7];
       chessBoard[0][7] = null;
     }

    public static void main(String args[]){
        new rookTest();

    }

}
