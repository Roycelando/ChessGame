package Pieces;


public class Rook extends Piece{
    public boolean castling = true;

    public Rook(int xPos, int yPos, boolean isWhite) {
        super(xPos, yPos, isWhite,Rank.ROOK);
    }

    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {
        if(destX>-1&&destY>-1&&destX<8&&destY<8) {
           if(isWhite()? (chessBoard[destX][destY]==null || chessBoard[destX][destY].isWhite() == false)
                   && (((destX-getXPos() <0||destX-getXPos()>0) && destY-getYPos() == 0) ||
                   (destX-getXPos() ==0 && (destY-getYPos() <0||destY-getYPos()>0)))
                   : (chessBoard[destX][destY]==null || chessBoard[destX][destY].isWhite())
                   && (((destX-getXPos() <0||destX-getXPos()>0)
                   && destY-getYPos() == 0) || (destX-getXPos() ==0
                   && (destY-getYPos() <0||destY-getYPos()>0)))){

               if(destX-getXPos()>0 && destY-getYPos() == 0){ // if the piece is moving down
                  // System.out.println("down");
                   for(int i = getXPos()+1; i<destX; i++){
                       if(chessBoard[i][getYPos()] != null){// makes sure there are no pieces in between
                           return false;
                       }

                   }
                   return true;
               }
               else if(destX-getXPos()<0 && destY-getYPos() == 0){ // if the piece is moving up
                  // System.out.println("up");
                   for(int i = getXPos()-1; i>destX; i--){
                       if(chessBoard[i][getYPos()] != null){// makes sure there are no pieces in between
                           return false;
                       }

                   }
                   return true;
               }

                else if(destY-getYPos()>0 && destX-getXPos() == 0){ //moving right check
                  // System.out.println(" right");
                   for(int i = getYPos()+1; i<destY; i++){
                       if(chessBoard[getXPos()][i] != null){// makes sure there are no pieces in between
                           return false;
                       }

                   }
                   return true;
               }
               else if(destY-getYPos()<0 &&destX-getXPos() == 0){ //moving left check
                   //System.out.println("left");
                   for(int i = getYPos()-1; i>destY; i--){
                       if(chessBoard[getXPos()][i] != null){// makes sure there are no pieces in between
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
        castling = false;

    }
    public boolean canCastle(){
        return castling;
    }
    @Override
    public double getValue(){
        return 10;
    }
}
