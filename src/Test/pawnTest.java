package Test;


import Game.GameMaster;
import Pieces.Pawn;
import Pieces.Piece;

public class pawnTest {

    public pawnTest(){
//    captureTest();
   // movementTest();
   // moveTwoTest();
    //updateMoveTest();

        Pawn whiteTest = new Pawn(0,0,true);

        System.out.println(whiteTest.getImage());


//        enpassantTest();
    }

    public void captureTest(){
        //------------- White pawn
        //Trying to capture black pieces to the diagonal left and diagonal right of the white pawn

        Piece[][] chessBoard = new Piece[8][8];
        System.out.println("Trying to capture ''black'' pieces to the left and right diagonal of the ''white'' pawn: Passed if isLegal returns true\n");
        chessBoard[4][3] = new Pawn(4,3,true);
        chessBoard[3][2] = new Pawn(3,2,false);
        chessBoard[3][4] = new Pawn(3,4,false);

        System.out.println(chessBoard[4][3].isLegalMove(3,2,chessBoard)? "Capture left: test passed": "Capture left: test failed");
        System.out.println(chessBoard[4][3].isLegalMove(3,4,chessBoard)? "Capture right: test passed\n": "Capture right: test failed\n");

        // The 2 pieces to the left and right of the white pawn are now white, so I shouldn't be able to capture now
        System.out.println("Trying to capture ''white'' pieces to the left and right diagonal of the ''white'' pawn: Passed if isLegal returns false");
       chessBoard[3][2] = new Pawn(3,2,true);
       chessBoard[3][4] = new Pawn(3,4,true);

        System.out.println(chessBoard[4][3].isLegalMove(3,2,chessBoard)? "Capture left: test failed": "Capture left: test passed");
        System.out.println(chessBoard[4][3].isLegalMove(3,4,chessBoard)? "Capture right: test failed\n": "Capture right: test passed\n");

        //------------------- Black pawn
        //Trying to capture black pieces to the diagonal left and diagonal right of the white pawn
        System.out.println("Trying to capture ''white'' pieces to the left and right diagonal of the ''black ''pawn'': Passed if isLegal returns true\n");
       chessBoard[4][3] = new Pawn(4,3,false);
       chessBoard[5][2] = new Pawn(5,2,true);
       chessBoard[5][4] = new Pawn(5,4,true);

        System.out.println(chessBoard[4][3].isLegalMove(5,2,chessBoard)? "Capture right: test passed": "Capture left: test failed");
        System.out.println(chessBoard[4][3].isLegalMove(5,4,chessBoard)? "Capture left: test passed\n": "Capture right: test failed\n");

        // The 2 pieces to the left and right of the white pawn are now white, so I shouldn't be able to capture now
        System.out.println("Trying to capture black pieces to the left and right diagonal of the black pawn: Passed if isLegal returns false");
       chessBoard[3][2] = new Pawn(5,2,false);
       chessBoard[3][4] = new Pawn(5,4,false);

        System.out.println(chessBoard[4][3].isLegalMove(3,2,chessBoard)? "Capture right: test failed": "Capture left: test passed");
        System.out.println(chessBoard[4][3].isLegalMove(3,4,chessBoard)? "Capture left: test failed\n": "Capture right: test passed\n");

    }


    public void movementTest(){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[4][3] = new Pawn(4,3,true); // white piece
       chessBoard[1][0] = new Pawn(1,0,false); // black piece

        //--------------------- White Pawn
        //testing if if I can move a white pawn forward one square successfully
        System.out.println("Testing moving white pawn ");
        System.out.println(chessBoard[4][3].isLegalMove(3,3,chessBoard)? "Moving up one square: passed": "Moving up one square: failed");

        //trying to move the white piece back a square, should return false for test to pass
        System.out.println(chessBoard[4][3].isLegalMove(5,3,chessBoard)? "Moving back one squares: test failed\n": "Moving back one squares: test passed\n");

        //--------------------- Black Pawn
        //testing if if I can move a black pawn forward one square successfully
        System.out.println("Testing moving black pawn");
        System.out.println(chessBoard[1][0].isLegalMove(2,0,chessBoard)? "Moving up one square: passed": "Moving up one square: failed");

        //trying to move the white piece back a square, should return false for test to pass
        System.out.println(chessBoard[1][0].isLegalMove(0,0,chessBoard)? "Moving back one squares: test failed\n": "Moving back one squares: test passed\n");
    }

    public void moveTwoTest(){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[6][3] = new Pawn(6,3,true);
       chessBoard[1][6] = new Pawn(1,6,false);


        System.out.println("Testing move two for white pawn");
        System.out.println(chessBoard[6][3].isLegalMove(4,3,chessBoard)? "Moving up two squares: test passed\n": "Moving up two squares: test failed\n");

        System.out.println("Testing move two for black pawn");
        System.out.println(chessBoard[1][6].isLegalMove(3,6,chessBoard)? "Moving up two squares: test passed\n": "Moving up two squares: test failed\n");
    }

    public void updateMoveTest(){
        Piece[][] chessBoard= new Piece[8][8];
        Pawn a = new Pawn(6,0,true);
       chessBoard[6][0] = a;
        Pawn b = new Pawn(1,7, false);
       chessBoard[1][7] = b;
        System.out.println("Testing white piece movement");

        if(chessBoard[6][0].isLegalMove(4,0,chessBoard)){

            System.out.println("Passed: Moved 2 successfully");
            a.updatePosition(4,0);
           chessBoard[4][0] =chessBoard[6][0];
           chessBoard[6][0] = null;
            System.out.println(a.getEnPassant()?"Passed: Enpassant is true":"Failed: Enpassant is false");

        }

        System.out.println("\nGoing to move the piece 2 squares again");

        if(!chessBoard[4][0].isLegalMove(2,0,chessBoard)){
            System.out.println("Passed: cant move 2 squares twice");
            System.out.println("Gonna move the pawn up one");
            if(chessBoard[4][0].isLegalMove(3,0,chessBoard)){
                System.out.println("Passed:  this is a legal move");
                a.updatePosition(3,0);
               chessBoard[3][0] = a;
               chessBoard[4][0] = null;

            }
            System.out.println(a.getEnPassant()?"Failed: Enpassant should be false":"Passed: Enpassant is false");
        }

    }

    public void enpassantTest(){
        Piece[][] chessBoard= new Piece[8][8];
       chessBoard[3][5] = new Pawn(3,5,true);
       chessBoard[1][6] = new Pawn(1,6,false);
        GameMaster.printer(chessBoard);

        // testing enpasant
        System.out.println(chessBoard[1][6].isLegalMove(3,6,chessBoard)? "Passed": "Failed");
       chessBoard[1][6].updatePosition(3,6);
       chessBoard[3][6] =chessBoard[1][6];
       chessBoard[1][6] = null;
GameMaster.printer(chessBoard);
        System.out.println(chessBoard[3][5].isLegalMove(2,6,chessBoard)? "Passed": "Failed");



    }


    public static void main(String[] args){
        new pawnTest();
    }
}
