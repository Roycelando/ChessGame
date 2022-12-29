package Pieces;



public class Knight extends Piece{

    public Knight(int xPos, int yPos, boolean isWhite) {
        super(xPos, yPos, isWhite, Rank.KNIGHT);
    }

    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {
        if(destX>-1&&destY>-1&&destX<8&&destY<8) {// checks if the square is a legal square
            boolean flag = chessBoard[destX][destY]==null||chessBoard[destX][destY].isWhite()==false;
//            boolean flag2=;
            if(isWhite()?(chessBoard[destX][destY]==null // square must be empty
      
                    || chessBoard[destX][destY].isWhite() == false) // or square must hold opposite coloured piece
                    && ((Math.abs(destX-getXPos())==2
                    && Math.abs(destY-getYPos())==1)||(Math.abs(destY-getYPos()) == 2 && Math.abs(destX-getXPos()) == 1))
                    :(chessBoard[destX][destY]==null
                    || chessBoard[destX][destY].isWhite() == true)
                    && ((Math.abs(destX-getXPos())==2
                    && Math.abs(destY-getYPos())==1)||(Math.abs(destY-getYPos()) == 2 && Math.abs(destX-getXPos()) == 1))){
                return true;
            }
        }

        return false;
    }

    @Override
    public void updatePosition(int destX, int destY) {
        setXpos(destX);
        setYpos(destY);
    }

    @Override
    public double getValue() {

        return 6;

    }
}
