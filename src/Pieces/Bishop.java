package Pieces;


public class Bishop extends Piece {
    public Bishop(int xPos, int yPos, boolean isWhite) {
        super(xPos, yPos, isWhite, Rank.BISHOP);
    }


    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {
        if(destX>-1&&destY>-1&&destX<8&&destY<8) {
            if(isWhite()? (chessBoard[destX][destY]==null || chessBoard[destX][destY].isWhite() == false)
                   && Math.abs(destX-getXPos()) == Math.abs(destY-getYPos())
                    :(chessBoard[destX][destY]==null || chessBoard[destX][destY].isWhite())
                   &&Math.abs(destX-getXPos()) == Math.abs(destY-getYPos())){

                int diff = Math.abs(destX-getXPos());

                if(destX-getXPos() >0 && destY-getYPos() >0){ //down right
                   // System.out.println("down right");
                    for(int i =1; i<diff; i++){
                        if(chessBoard[getXPos()+i][getYPos()+i] != null){
                            return false;
                        }
                    }
                    return true;
                }

                else if(destX-getXPos() <0 && destY-getYPos() <0){ // up left
                    //System.out.println("up left");
                    for(int i =1; i<diff; i++){
                        if(chessBoard[getXPos()-i][getYPos()-i] != null){
                            return false;
                        }
                    }
                    return true;

                }
               else if( destX-getXPos() <0 && destY-getYPos()>0){ // up right
                    //System.out.println("up right");
                    for(int i =1; i<diff; i++){
                        if(chessBoard[getXPos()-i][getYPos()+i] != null){
                            return false;
                        }
                    }
                    return true;

                }
                else if( destX-getXPos() >0 && destY-getYPos()<0){ // down left
                    //System.out.println("down left");
                    for(int i =1; i<diff; i++){
                        if(chessBoard[getXPos()+i][getYPos()-i] != null){
                            return false;
                        }
                    }
                    return true;

                }

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
    public  double getValue(){
        return 6;
    }
}
