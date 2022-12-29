package Test;

import java.io.File;
import java.util.Scanner;
import Pieces.*;

import javax.print.DocFlavor;

/**
 * edited 01/12/2020
 */
/*
important thing to note, the pieces for white are capital and black are small
 */
public class RunThis {
    /**
     *
     * @param whiteToPlay keeps track of if the perspective is white's or black
     * @param ChessBoard the game borad it self
     * @return a value which represent by how much the prepective is winning or losing, if in check, the value is infinity or negative infinity on perspective
     */
    public  double evaluation(boolean whiteToPlay,Piece[][] ChessBoard){
        double value=0;
        char[] Pieces = new char[]{'P','B','N','R','Q','K'};
        double[] piecesVals=new double[]{1,3, 4, 5, 7, 10};//P, B, N, R, Q, K

        for (int i = 0; i < ChessBoard.length; i++) {
            for (int j = 0; j < ChessBoard[i].length; j++) {
                if (ChessBoard[i][j] != null) {//not empty
                    int counter = ((whiteToPlay && ChessBoard[i][j].isWhite()) ||
                            (!whiteToPlay && !ChessBoard[i][j].isWhite())) ? 1 : -1;


                    value = value + (whiteToPlay ? (ChessBoard[i][j].isWhite() ? ChessBoard[i][j].getValue() : -ChessBoard[i][j].getValue()) :
                            (ChessBoard[i][j].isWhite() ? -ChessBoard[i][j].getValue() : ChessBoard[i][j].getValue()));
                    switch (ChessBoard[i][j].getRank()) {
                        case PAWN:
                            value = value + (squaresPawn(i, j, ChessBoard, whiteToPlay)*counter);
                            break;
                        case BISHOP:
                            value = value + (squaresBishop(i, j, ChessBoard,whiteToPlay)*counter);
                            break;
                        case KNIGHT:
                            value = value + (squaresKnight(i, j, ChessBoard,whiteToPlay)*counter);
                            break;
                        case ROOK:
                            value = value + (squaresRook(i, j, ChessBoard,whiteToPlay)*counter);
                            break;
                        case QUEEN:
                            value = value + (squaresQueen(i, j, ChessBoard,whiteToPlay)*counter);
                            break;
                        case KING:
                            value = value + (squaresKing(i, j, ChessBoard,whiteToPlay)*counter);
                            break;
                    }//to see which one is what and to see how much can it control

                }
            }
        }
        return value;
    }

    /**
     *
     * @param i x position of the piece
     * @param j
     * @param Chessboard
     * @param whiteToPlay
     * @param counter
     * @return
     */
    public double ifCheckKing(int i, int j, Piece[][] Chessboard, boolean whiteToPlay,int counter){
       return (
                (whiteToPlay&&!Chessboard[i][j].isWhite())||
                        (!whiteToPlay&&Chessboard[i][j].isWhite())&&
                (Chessboard[i+counter][j-1].getRank()==Rank.KING
                )
       )?
                Double.POSITIVE_INFINITY: (
                        (
                                whiteToPlay&&Chessboard[i][j].isWhite()
                                )||(
                                        !whiteToPlay&&!Chessboard[i][j].isWhite())
                                &&Chessboard[i+counter][j-1].getRank()==Rank.KING)?Double.POSITIVE_INFINITY:0;
    }

    /**
     *
     * @param i this is the x position of the pawn
     * @param j this is the y position of the pawn
     * @param Chessboard this is the chessboard that is in question
     * @return returnThis which is the number of area the pawn is controlling
     */
    public double squaresPawn(int i,int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=1;//the place it started
        int counter = Chessboard[i][j].isWhite()?-1:1;


        if(counter<0?i+counter==0:i+counter==7){//this is the ranking up solution
            if(Chessboard[i][j].isLegalMove(i+counter,j,Chessboard))
                returnThis=returnThis+9;
            else if(Chessboard[i][j].isLegalMove(i+counter,j-1,Chessboard)) {
                returnThis = returnThis + 9;
                returnThis = returnThis + ifCheckKing(i,j,Chessboard,whiteToPlay,counter);

            }
            else if(Chessboard[i][j].isLegalMove(i+counter,j+1,Chessboard)) {
                returnThis = returnThis + 9;
                returnThis = returnThis + ifCheckKing(i,j,Chessboard,whiteToPlay,counter);
            }
        }
        if(Chessboard[i][j].isLegalMove(i+counter,j,Chessboard))
            returnThis++;
        if(((Pawn)Chessboard[i][j]).getExtraStep()){
            if(Chessboard[i][j].isLegalMove(i+(2*counter),j,Chessboard))
                returnThis++;
        }
        if(counter<0?i+counter>0:i+counter<7){
            if(j-1>-1&&j+1<8){
                if(Chessboard[i+counter][j-1]!=null&&!Chessboard[i+counter][j-1].isWhite()) {
                    returnThis++;
                    returnThis = returnThis + ifCheckKing(i,j,Chessboard,whiteToPlay,counter);
                }
                if(Chessboard[i+counter][j+1]!=null&&!Chessboard[i+counter][j+1].isWhite()) {
                    returnThis = returnThis + ifCheckKing(i,j,Chessboard,whiteToPlay,counter);
                    returnThis++;
                }
            }
        }



        return returnThis;
    }
    /**
     * this gives the number of squares a bishop is controling
     * @param i x  cooridates of that bishop and the board it self, in addition to who is
     * @param j y coorinates of the bishop
     * @return the number the bishop given holds
     */
    public double squaresBishop(int i, int j, Piece[][] Chessboard,boolean whiteToPlay){
        double returnThis=1;//this to mark it control the square it is on
        int tempx=i;
        int tempy=j;
        while(Chessboard[i][j].isLegalMove(tempx-1,tempy-1,Chessboard)){//upleft

            returnThis++;
            tempx--;
            tempy--;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempx=i;
        tempy=j;
        while (Chessboard[i][j].isLegalMove(tempx-1,tempy+1,Chessboard)){//up right
            returnThis++;

            tempx--;
            tempy++;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempx=i;
        tempy=j;
        while (Chessboard[i][j].isLegalMove(tempx+1,tempy+1,Chessboard)){//down right
            returnThis++;

            tempx--;
            tempy++;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempx=i;
        tempy=j;
        while(Chessboard[i][j].isLegalMove(tempx+1,tempy-1,Chessboard)){// down left
            returnThis++;

            tempx++;
            tempy--;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        return returnThis;
    }

    /**
     * '
     * @param i this is the x position of the rook
     * @param j this is the y position of the rook
     * @param Chessboard this is the chess board
     * @return returnThis returns the number of squares the rook controls
     */
    public double squaresRook(int i,int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=1;
        int tempx=i;
        int tempy=j;
        while(Chessboard[i][j].isLegalMove(tempx-1,tempy,Chessboard)){
            returnThis++;
            tempx--;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempx=i;
        while(Chessboard[i][j].isLegalMove(tempx,tempy-1,Chessboard)){
            returnThis++;
            tempy--;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempy=j;
        while(Chessboard[i][j].isLegalMove(tempx+1,tempy,Chessboard)){
            returnThis++;
            tempx++;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempx=i;
        while(Chessboard[i][j].isLegalMove(tempx,tempy+1,Chessboard)){
            returnThis++;
            tempy++;
            returnThis = returnThis + ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
        }
        tempy=j;
        return returnThis;
    }

    /**
     *
     * @param i this is the x position of the knight
     * @param j this is the y position of the knight
     * @param Chessboard this is the chess board
     * @return returnThis returns the number of teh squares the knight controls
     */
    public double squaresKnight(int i, int j, Piece[][] Chessboard,boolean whiteToPlay){
        double returnThis=1;
        if(Chessboard[i][j].isLegalMove(i-2,j-1,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i-2,j-1,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i-2,j+1,Chessboard)) {
            returnThis = returnThis + ifCheckKing(i-2,j+1,Chessboard,whiteToPlay,0);
            returnThis++;
        }
        if(Chessboard[i][j].isLegalMove(i-1,j-2,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i-1,j-2,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i-1,j+2,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i-1,j+2,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i+1,j-2,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i+1,j-2,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i+1,j+2,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i+1,j+2,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i+2,j-1,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i+2,j-1,Chessboard,whiteToPlay,0);
        }
        if(Chessboard[i][j].isLegalMove(i+2,j+1,Chessboard)) {
            returnThis++;
            returnThis = returnThis + ifCheckKing(i+2,j+1,Chessboard,whiteToPlay,0);
        }

        return returnThis;
    }

    /**
     *
     * @param i this is the x position fo the queen
     * @param j this is the y position fo the queen
     * @param Chessboard this is the chess board it self
     * @return returnThis returns the number of squares controlled by the queen
     */
    public double squaresQueen(int i, int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=1;

        returnThis=returnThis+squaresBishop(i,j,Chessboard,whiteToPlay);
        returnThis=returnThis+squaresRook(i,j,Chessboard,whiteToPlay);

        return returnThis;
    }

    /**
     *
     * @param i this is the x position of the king
     * @param j this is the y positio of the king
     * @param Chessboard this is the chess board itself
     * @return returnThis returns the number of squares controlled by the queen
     */
    public double squaresKing(int i, int j, Piece[][] Chessboard, boolean whiteToPlay){
        int returnThis=1;
        int tempx=i;
        int tempy=j;
        tempx=tempx-1;
        tempy=tempy-1;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(Chessboard[i][j].isLegalMove(tempx+k,tempy+l,Chessboard))
                    returnThis++;
            }
        }
        return returnThis;
    }
    public static void main(String[] args) {
        Piece[][] Chessboard = new Piece[8][8];
        File f = new File("test");
        try {
            Scanner s = new Scanner(f);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    char temp = s.next().charAt(0);
                    switch (Character.toLowerCase(temp)){
                        case 'r': Chessboard[i][j]=new Rook(i,j,Character.isUpperCase(temp));
                            break;
                        case 'n':Chessboard[i][j]=new Knight(i,j,Character.isUpperCase(temp));
                            break;
                        case 'b':Chessboard[i][j]=new Bishop(i,j,Character.isUpperCase(temp));
                            break;
                        case 'q':Chessboard[i][j]=new Queen(i,j,Character.isUpperCase(temp));
                            break;
                        case 'k':Chessboard[i][j]=new King(i,j,Character.isUpperCase(temp));
                            break;
                        case 'p':Chessboard[i][j]=new Pawn(i,j,Character.isUpperCase(temp));
                            break;
                        default:Chessboard[i][j]=null;
                    }
                }
            }
        } catch (Exception exn) {

        }
//        Chessboard[6][4].updatePosition(5,4);//whites move
        RunThis r = new RunThis();
        System.out.println(r.evaluation(true,Chessboard));

    }
}
