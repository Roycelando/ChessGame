package Pieces;

public class Queen extends Piece{

    public Queen(int xPos, int yPos, boolean isWhite) {
        super(xPos, yPos, isWhite, Rank.QUEEN);

    }

    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {
        Rook movement1 = new Rook(getXPos(), getYPos(), isWhite());
        Bishop movement2 = new Bishop(getXPos(), getYPos(), isWhite());
        if(movement1.isLegalMove(destX, destY, chessBoard)|| movement2.isLegalMove(destX, destY, chessBoard)){
            return true;
        }
        return false;
    }

    @Override
    public void updatePosition(int destX, int destY) {
        setXpos(destX);
        setYpos(destY);
    }

    @Override
    public double getValue(){
        return 14;
    }
}
