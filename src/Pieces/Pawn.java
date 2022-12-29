package Pieces;



public class Pawn extends Piece{
    private boolean extraStep = true; // indicates if the pawn can move 2 squares
    public boolean enPasseat = false; // indicates if the pawn can be captured enPassant

    public Pawn(int xPos, int yPos, boolean isWhite) {
        super(xPos, yPos, isWhite, Rank.PAWN);
    }


    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {
        if(destX>-1&&destY>-1&&destX<8&&destY<8) { // checks if the square is a legal square

            // logic for moving 2 squares
            if (isWhite() ? (chessBoard[destX][destY] == null
                    && getExtraStep()&& getXPos() == 6
                    && destX - getXPos() == -2
                    && destY - getYPos() == 0 && getXPos() == 6&&chessBoard[destX+1][destY]==null):
                    (chessBoard[destX][destY] == null
                            && getExtraStep()&& getXPos() == 1
                            &&destX - getXPos() == 2
                            && destY - getYPos() == 0&& getXPos() == 1&&chessBoard[destX-1][destY]==null)) { // end of expression

                return true;
            }// end of if

            // logic for moving 1 square
            if (isWhite() ? (chessBoard[destX][destY] == null
                    && destX - getXPos() == -1
                    && destY - getYPos() == 0):
                    (chessBoard[destX][destY] == null
                            &&destX - getXPos() == 1
                            && destY - getYPos() == 0)) { // end of expression

                return true;
            }// end of if

            if(Math.abs(destX-getXPos())==1&&Math.abs(destY-getYPos())==1 && (chessBoard[destX][destY]!=null?(
                    isWhite()?(!chessBoard[destX][destY].isWhite()):
                            (chessBoard[destX][destY].isWhite())
                    ):(chessBoard[getXPos()][destY]!=null&&chessBoard[getXPos()][destY].getRank()==Rank.PAWN&&((Pawn)chessBoard[getXPos()][destY]).getEnPassant())&&(
                            isWhite()?(!chessBoard[getXPos()][destY].isWhite()):(chessBoard[getXPos()][destY].isWhite())
                    ))){
                return true;
            }


            // logic for capturing squares on the diagonal
//            if (chessBoard[destX][destY] != null && isWhite() ?(!chessBoard[destX][destY].isWhite())||(((Pawn)chessBoard[getXPos()][destY]).getEnPassant()) // white can only capture black pieces
//                    &&(Math.abs(destY-getYPos())==1
//                    &&Math.abs(destY-getYPos()) == 1):
//                    (chessBoard[destX][destY]==null&&chessBoard[getXPos()][destY]!=null&&((chessBoard[getXPos()][destY].isWhite() ||(((Pawn)chessBoard[getXPos()][destY]).getEnPassant()))) // black can only capture white pieces
//                            &&Math.abs(destX-getXPos())==1 // black x difference must be 1
//                            &&Math.abs(destY-getYPos())==1
//                           )) { // end of expression
//
//                return true;
//
//            } // end of if

        }

        return false; // illegal move
    }

    @Override
    public double getValue(){
        return 1;
    }

    /**
     * This method updates the pieces position by calling the super classes
     * update position class
     *
     * @param destX, destination x value of the piece
     * @param destY, destination y value of the piece
     */
    @Override
    public void updatePosition(int destX, int destY){
        if(extraStep && isWhite()?destX-getXPos()== -2 && destX == 4: destX-getXPos() ==2 && destX == 3) { // if the pawn moves 2 squares it can be taken en passant
            setXpos(destX);
            setYpos(destY);
           enPasseat = true; // the one and only time you you move twice enpassant is enabled
           extraStep = false;
           return;
        }
        setXpos(destX);
        setYpos(destY);
        extraStep = false; // once moved you can no longer take an extra step
        enPasseat = false;


    }

    /**
     * This method returns the boolean extraStep
     *
     * @return true if the piece can move 2 squares, false otherwise.
     */
    public boolean getExtraStep(){
        return extraStep;
    }

    /**
     * This method returns the boolean enPassant
     *
     * @return true if the piece can be taken enpassant
     */
    public boolean getEnPassant(){
        return enPasseat;
    }

}
