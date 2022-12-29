package Pieces;

import Game.Evaluation;
import Game.GameMaster;
import Game.Node;

public class King extends Piece{
    public boolean castling;
    public King(int xPos, int yPos, boolean isWhite) {

        super(xPos, yPos, isWhite, Rank.KING);
        castling = true;
    }

    @Override
    public boolean isLegalMove(int destX, int destY, Piece[][] chessBoard) {

        if (destX > -1 && destY > -1 && destX < 8 && destY < 8) {
            if (((Math.abs(destX - getXPos()) == 1 && Math.abs(destY - getYPos()) == 0)
                    || (Math.abs(destX - getXPos()) == 0 && Math.abs(destY - getYPos()) == 1)
                    || (Math.abs(destX - getXPos()) == 1 && Math.abs(destY - getYPos()) == 1))
                    && (isWhite() ? (chessBoard[destX][destY] == null || !chessBoard[destX][destY].isWhite()) :
                    (chessBoard[destX][destY] == null || chessBoard[destX][destY].isWhite()))) {


                Piece[][] tempBoard = GameMaster.copyOf(chessBoard);
//                System.out.println(destX+" "+destY);
//                System.out.println(getXPos()+ " "+getYPos());
                tempBoard[destX][destY] = tempBoard[getXPos()][getYPos()];
                tempBoard[getXPos()][getYPos()] = null;


                if (Evaluation.evaluation(isWhite(), tempBoard) == Double.NEGATIVE_INFINITY) {
                    tempBoard[destX][destY] = null;
                    return false;
                }

                return true;
            }
            //this means can long side castle
            if (isWhite() ? destX == 7 : destX == 0) {
                if (destY == 2) {
                    if (canCastle() &&
                            (getXPos() - 4) > -1 &&
                            chessBoard[getXPos()][getYPos() - 1] == null &&
                            chessBoard[getXPos()][getYPos() - 2] == null &&
                            chessBoard[getXPos()][getYPos() - 3] == null &&
                            chessBoard[getXPos()][getYPos() - 4] != null &&
                            (chessBoard[getXPos()][getYPos()].isWhite() ? chessBoard[getXPos()][getYPos() - 4].isWhite() : !chessBoard[getXPos()][getYPos() - 4].isWhite()) &&
                            chessBoard[getXPos()][getYPos() - 4].getRank() == Rank.ROOK &&
                            ((Rook) chessBoard[getXPos()][getYPos() - 4]).canCastle() &&
                            Evaluation.evaluation(chessBoard[getXPos()][getYPos()].isWhite(), chessBoard) != Double.NEGATIVE_INFINITY
                    ) {
                        Piece[][] temp = GameMaster.copyOf(chessBoard);
                        int i = getXPos();
                        int j = getYPos();
                        temp[i][j - 1] = temp[i][j];
                        temp[i][j] = null;
                        //still have to check if there are two pieces between
                        if (Evaluation.evaluation(chessBoard[i][j].isWhite(), temp) != Double.NEGATIVE_INFINITY) {
                            temp[i][j - 2] = temp[i][j - 1];
                            temp[i][j - 1] = null;
                            if (Evaluation.evaluation(chessBoard[i][j].isWhite(), temp) != Double.NEGATIVE_INFINITY) {
                                return true;
                            }
                        }
                    }
                }
                if (destY == 6) {
                    if (canCastle() &&
                            (getYPos() + 3) < 8 &&
                            chessBoard[getXPos()][getYPos() + 1] == null &&
                            chessBoard[getXPos()][getYPos() + 2] == null &&
                            chessBoard[getXPos()][getYPos() + 3] != null &&
                            (chessBoard[getXPos()][getYPos()].isWhite() ? chessBoard[getXPos()][getYPos() + 3].isWhite() : !chessBoard[getXPos()][getYPos() + 3].isWhite()) &&
                            chessBoard[getXPos()][getYPos() + 3].getRank() == Rank.ROOK &&
                            ((Rook) chessBoard[getXPos()][getYPos() + 3]).canCastle() &&
                            Evaluation.evaluation(chessBoard[getXPos()][getYPos()].isWhite(), chessBoard) != Double.NEGATIVE_INFINITY
                    ) {
                        Piece[][] temp;
                        int i = getXPos();
                        int j = getYPos();
                        temp = GameMaster.copyOf(chessBoard);
                        temp[i][j + 1] = temp[i][j];
                        temp[i][j] = null;
                        //still have to check if there are two pieces between
                        if (Evaluation.evaluation(chessBoard[i][j].isWhite(), temp) != Double.NEGATIVE_INFINITY) {
                            temp[i][j + 2] = temp[i][j + 1];
                            temp[i][j + 1] = null;
                            if (Evaluation.evaluation(chessBoard[i][j].isWhite(), temp) != Double.NEGATIVE_INFINITY) {
                                return true;

                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean canCastle(){
        return castling&&(isWhite()?(getXPos()==7&&getYPos()==4):(getXPos()==0&&getYPos()==4));
    }

    @Override
    public void updatePosition(int destX, int destY) {
        setXpos(destX);
        setYpos(destY);
        castling = false;
    }

    @Override
    public double getValue(){
        return 0;
    }
}
