package Game;

import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Rank;

public class Evaluation {
    public static double evaluation(boolean whiteToPlay, Piece[][] ChessBoard){
        double value=0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(ChessBoard[i][j]!=null){
                    ChessBoard[i][j].setNumAttacked(0);
                    ChessBoard[i][j].setNumDefended(0);
                    ChessBoard[i][j].setNumbeingattack(0);

                }
            }
        }
        for (int i = 0; i < ChessBoard.length; i++) {
            for (int j = 0; j < ChessBoard[i].length; j++) {

                if (ChessBoard[i][j] != null) {//not empty

                    int counter = ((whiteToPlay && ChessBoard[i][j].isWhite()) ||
                            (!whiteToPlay && !ChessBoard[i][j].isWhite())) ? 1 : -1;
                    value = value + (whiteToPlay ? (ChessBoard[i][j].isWhite() ? ChessBoard[i][j].getValue() : -ChessBoard[i][j].getValue()) :
                            (ChessBoard[i][j].isWhite() ? -ChessBoard[i][j].getValue() : ChessBoard[i][j].getValue()));
                    if(value==Double.NEGATIVE_INFINITY)break;
                    double val=0;

                    switch (ChessBoard[i][j].getRank()) {
                        case PAWN:
                            val = (squaresPawn(i, j, ChessBoard, ChessBoard[i][j].isWhite())*counter);
                            break;
                        case BISHOP:
                            val = (squaresBishop(i, j, ChessBoard,ChessBoard[i][j].isWhite())*counter);
                            break;
                        case KNIGHT:
                            val = (squaresKnight(i, j, ChessBoard,ChessBoard[i][j].isWhite())*counter);
                            break;
                        case ROOK:
                            val = (squaresRook(i, j, ChessBoard,ChessBoard[i][j].isWhite())*counter);
                            break;
                        case QUEEN:
                            val =  (squaresQueen(i, j, ChessBoard,ChessBoard[i][j].isWhite())*counter);
                            break;
                        case KING:
                            val = (squaresKing(i, j, ChessBoard,ChessBoard[i][j].isWhite())*counter);
                            break;
                    }//to see which one is what and to see how much can it control
                    value = value + val==Double.POSITIVE_INFINITY?1.7:val;
//                    value = value/10;
                }

            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8; j++) {
                if(ChessBoard[i][j]!=null){
                            value =value+ ((ChessBoard[i][j].getNumDefended()-ChessBoard[i][j].getNumbeingattack()));
                            value=value+ChessBoard[i][j].getNumAttacked();
                }
            }
        }

        return value;
    }

    /**
     * This checks if the king is in check if the position given is a king
     * @param i This is the x position the square we are checking.
     * @param j This is the y position of the square we are checking
     * @param Chessboard This is the chessboard we are checking for the check
     * @param whiteToPlay This is whose turn it is to play
     * @param counter this is a random variable which servers no purpose
     * @return positive infinity if the king is in check or 0
     */
    public static double ifCheckKing(int i, int j , Piece[][] Chessboard,boolean whiteToPlay,int counter){
        if(Chessboard[i][j]!=null&&((whiteToPlay&&!Chessboard[i][j].isWhite()&&Chessboard[i][j].getRank()==Rank.KING)||
                (!whiteToPlay&&Chessboard[i][j].isWhite()&&Chessboard[i][j].getRank()==Rank.KING)))
                return Double.POSITIVE_INFINITY;
        else
            return 0;
    }

    /**
     *
     * @param i this is the x position of the pawn
     * @param j this is the y position of the pawn
     * @param Chessboard this is the chessboard that is in question
     * @return returnThis which is the number of area the pawn is controlling
     */
    public static double squaresPawn(int i,int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=0;//the place it started
        int counter = Chessboard[i][j].isWhite()?-1:1;
        boolean testflag=false;

        if(counter<0?i+counter==0:i+counter==7){//this is the ranking up solution
            if(Chessboard[i][j].isLegalMove(i+counter,j,Chessboard)) {
                returnThis = returnThis + 1;
                testflag=true;
            }
            if(Chessboard[i][j].isLegalMove(i+counter,j-1,Chessboard)) {
                returnThis = !testflag?(returnThis + 1):returnThis;
                testflag=true;
                returnThis = returnThis + ifCheckKing(i+counter,j-1,Chessboard,whiteToPlay,counter);
                Chessboard[i][j].incrementNumAttacked(Chessboard[i+counter][j-1]);
            }
            if(Chessboard[i][j].isLegalMove(i+counter,j+1,Chessboard)) {
                returnThis = !testflag?(returnThis + 1):returnThis;
                returnThis = returnThis + ifCheckKing(i+counter,j+1,Chessboard,whiteToPlay,counter);
                Chessboard[i][j].incrementNumAttacked(Chessboard[i+counter][j+1]);
            }
        }
        if(Chessboard[i][j].isLegalMove(i+counter,j,Chessboard))
            returnThis=returnThis+0.2+((!Chessboard[i][j].isWhite()?i:8-i)/2.0);
        if(((Pawn)Chessboard[i][j]).getExtraStep()){
            if(Chessboard[i][j].isLegalMove(i+(2*counter),j,Chessboard))
                returnThis=returnThis+0.2;
        }
        if(counter<0?i+counter>0:i+counter<7){
            if(j-1>-1&&j+1<8){
                if(Chessboard[i+counter][j-1]!=null&&(Chessboard[i][j].isWhite()?!Chessboard[i+counter][j-1].isWhite():Chessboard[i+counter][j-1].isWhite())) {
                    returnThis=returnThis+0.2+((!Chessboard[i][j].isWhite()?i:8-i)/2.0);
                    returnThis = returnThis + ifCheckKing(i+counter,j-1,Chessboard,whiteToPlay,counter);
                    Chessboard[i][j].incrementNumAttacked(Chessboard[i+counter][j-1]);
                }
                if(Chessboard[i+counter][j+1]!=null&&(Chessboard[i][j].isWhite()?!Chessboard[i+counter][j+1].isWhite():Chessboard[i+counter][j+1].isWhite())) {
                    returnThis = returnThis + ifCheckKing(i+counter,j+1,Chessboard,whiteToPlay,counter);
                    returnThis=returnThis+0.2+((!Chessboard[i][j].isWhite()?i:8-i)/2.0);
                    Chessboard[i][j].incrementNumAttacked(Chessboard[i+counter][j+1]);
                }
            }
        }

        if(i+counter<8&&i+counter>-1&&j-1<8&&j-1>-1) {
            if (Chessboard[i+counter][j-1]!=null&&(whiteToPlay?Chessboard[i + counter][j - 1].isWhite():!Chessboard[i + counter][j - 1].isWhite())){
                Chessboard[i+counter][j-1].incrementDefended();
            }
        }
        if(i + counter < 8 && i + counter > -1 && j + 1 < 8) {
            if (Chessboard[i+counter][j+1]!=null&&(whiteToPlay?Chessboard[i + counter][j + 1].isWhite():!Chessboard[i + counter][j + 1].isWhite())){
                Chessboard[i+counter][j+1].incrementDefended();
            }
        }
        return returnThis;
    }
    /**
     * This gives the number of squares a bishop is controlling
     *
     * @param i x  cooridates of that bishop and the board it self, in addition to who is
     * @param j y coorinates of the bishop
     * @return the number the bishop given holds
     */
    public static double squaresBishop(int i, int j, Piece[][] Chessboard,boolean whiteToPlay){
        double returnThis=0;//this to mark it control the square it is on
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                int temp1=i;
                int temp2=j;
                if(k!=0&&l!=0)
                    while(Chessboard[i][j].isLegalMove(temp1+k,temp2+l,Chessboard)){
                           returnThis=returnThis+0.5;
                           if(Chessboard[temp1+k][temp2+l]!=null){
                               Chessboard[i][j].incrementNumAttacked(Chessboard[temp1+k][temp2+l]);
                               Chessboard[temp1+k][temp2+l].incrementNumattacked(Chessboard[i][j]);
                           }
                            temp1=temp1+k;
                            temp2=temp2+l;
                        returnThis=returnThis+ifCheckKing(temp1,temp2,Chessboard,whiteToPlay,0);
                        }
                if(temp1+k<8&&temp1+k>-1&&temp2+l<8&&temp2+l>-1){
                    if(Chessboard[temp1+k][temp2+l]!=null&&(whiteToPlay?Chessboard[temp1 + k][temp2 + l].isWhite():!Chessboard[temp1 + k][temp2 + l].isWhite())){
                        Chessboard[temp1+k][temp2+l].incrementDefended();
                    }
                }
            }
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
    public static double squaresRook(int i,int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=0;
        for (int l = -1; l < 2; l++) {
            int tempx=i;
            int tempy=j;
            if(l!=0) {
                while (Chessboard[i][j].isLegalMove(tempx + l, tempy, Chessboard)) {
                    returnThis=returnThis+0.6;
                    if(Chessboard[tempx+l][tempy]!=null){
                        Chessboard[i][j].incrementNumAttacked(Chessboard[tempx+l][tempy]);
                        Chessboard[tempx+l][tempy].incrementNumattacked(Chessboard[i][j]);
//                        returnThis=returnThis+Chessboard[tempx+l][tempy].getValue();
                    }
                    tempx=tempx+l;
                    returnThis=returnThis+ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
                }//end of loop
                if(tempx+l<8&&tempx+l>-1&&tempy<8&&tempy>-1){
                    if(Chessboard[tempx + l][tempy] != null && (whiteToPlay?Chessboard[tempx + l][tempy].isWhite():!Chessboard[tempx + l][tempy].isWhite())){
                        Chessboard[tempx+l][tempy].incrementDefended();
                    }
                }
                tempx=i;
                tempy=j;
                while (Chessboard[i][j].isLegalMove(tempx, tempy + l, Chessboard)) {
                    returnThis=returnThis+0.6;
                    if(Chessboard[tempx][tempy+l]!=null){
                        Chessboard[i][j].incrementNumAttacked(Chessboard[tempx][tempy+l]);
                        Chessboard[tempx][tempy+l].incrementNumattacked(Chessboard[i][j]);
//                        returnThis=returnThis+Chessboard[tempx][tempy+l].getValue();
                    }
                    tempy=tempy+l;
                    returnThis=returnThis+ifCheckKing(tempx,tempy,Chessboard,whiteToPlay,0);
                }//end of loop
                if(tempx<8&&tempx>-1&&tempy+l<8&&tempy+l>-1){
                    if(Chessboard[tempx][tempy+l] != null && (whiteToPlay?Chessboard[tempx][tempy+l].isWhite():!Chessboard[tempx][tempy+l].isWhite())){
                        Chessboard[tempx][tempy+l].incrementDefended();
                    }
                }
            }
        }
        return returnThis;
    }

    /**
     *
     * @param i this is the x position of the knight
     * @param j this is the y position of the knight
     * @param Chessboard this is the chess board
     * @return returnThis returns the number of teh squares the knight controls
     */
    public static double squaresKnight(int i, int j, Piece[][] Chessboard,boolean whiteToPlay){
        double returnThis=1;
        int s,u;
        s=0;
        u=0;
        for (int k = 0; k < 4; k++) {
            switch (k){
                case 0:
                    s=1;
                    u=1;
                    break;
                case 1:
                    s=-1;
                    u=-1;
                    break;
                case 2:
                    s=-1;
                    u=1;
                    break;
                case 3:
                    s=1;
                    u=-1;
                    break;
            }
            for (int l = 1; l <= 2; l++) {
                for (int m = 1; m <= 2; m++) {
                    if(Chessboard[i][j].isLegalMove(i+(l*s),j+(m*u),Chessboard)){
                        if(Chessboard[i+(l*s)][j+(m*u)]!=null){
                            Chessboard[i][j].incrementNumAttacked(Chessboard[i+(l*s)][j+(m*u)]);
                            Chessboard[i+(l*s)][j+(m*u)].incrementNumattacked(Chessboard[i][j]);
//                            returnThis=returnThis+Chessboard[i+(l*s)][j+(m*u)].getValue();
                        }
                        returnThis=returnThis+0.3;
                            returnThis = returnThis + ifCheckKing(i+(l*s),j+(m*u),Chessboard,whiteToPlay,0);
                    }
                    if(i+(l*s)<8&&i+(l*s)>-1&&j+(m*u)<8&&j+(m*u)>-1&&Chessboard[i+(l*s)][j+(m*u)]!=null)Chessboard[i+(l*s)][j+(m*u)].incrementDefended();
                }
            }
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
    public static double squaresQueen(int i, int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=1;

        returnThis=returnThis+squaresBishop(i,j,Chessboard,whiteToPlay)+squaresRook(i,j,Chessboard,whiteToPlay);


        return returnThis;
    }
    /**
     *
     * @param i this is the x position of the king
     * @param j this is the y positio of the king
     * @param Chessboard this is the chess board itself
     * @return returnThis returns the number of squares controlled by the queen
     */
    public static  double squaresKing(int i, int j, Piece[][] Chessboard, boolean whiteToPlay){
        double returnThis=1;
        int tempx=i;
        int tempy=j;
        tempx=tempx-1;
        tempy=tempy-1;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(tempx+k<8&&tempx+k>-1&&tempy+l<8&&tempy+l>-1)
                    if(k!=1||l!=1)
                    if(Chessboard[tempx+k][tempy+l]==null||(Chessboard[i][j].isWhite()?
                            !Chessboard[tempx+k][tempy+l].isWhite():Chessboard[tempx+k][tempy+l].isWhite())) {
                        returnThis=returnThis+ifCheckKing(tempx+k,tempy+l,Chessboard,whiteToPlay,0);
                        if(Chessboard[tempx+k][tempy+l]!=null) {
                            Chessboard[i][j].incrementNumAttacked(Chessboard[tempx + k][tempy + l]);
                            Chessboard[tempx+k][tempy+l].incrementNumattacked(Chessboard[i][j]);
                        }
                    }
            }
        }
        return returnThis;
    }
}
