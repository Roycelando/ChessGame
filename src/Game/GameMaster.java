package Game;
/**
 * @author Royce Lando| Joel Liju Jacob
 * @date 18/12/2020
 * @version 2.1
 */

import GUI.ChessBoardWithColoumnsAndRows;
import GUI.IllegalMove;
import GUI.Promotion;
import Pieces.*;

import javax.print.DocFlavor;
import javax.swing.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
public class GameMaster {
    public static Piece[][] board;
    static ChessBoardWithColoumnsAndRows t;
    int pos;
    public static int depthweAt=4;
    public GameMaster(Piece[][] Chessboard,int ogX,int ogY, int newX, int newY) {
        pos = 0;
            boolean flag = false;
                int x, xt;
                int y, yt;
                x=ogX;
                y=ogY;
                xt=newX;
                yt = newY;
                Node p = new Node(Chessboard, null);
                generateMoves(p,true);
                if (Evaluation.evaluation(true, Chessboard) == Double.NEGATIVE_INFINITY) {
                    if(p.children.isEmpty()){ // checks if its checkmate
                        System.out.println("You are checkmated");
                        new IllegalMove("You are checkmated");
                        System.exit(0);
                    }
                }
                else if(p.children.isEmpty()){
                    System.out.println("Stalemate");
                    new IllegalMove("Stalemate");
                    System.exit(0);
                }

                    if (Chessboard[x][y] != null && Chessboard[x][y].isWhite()) {
                        if (Chessboard[x][y].isLegalMove(xt, yt, Chessboard)) {
                            flag = false;
                            Piece holder=Chessboard[xt][yt];
                            boolean enpassant=false;
                            Piece holder2=null;
                            if (Chessboard[x][y].getRank() == Rank.KING && Math.abs(x - xt) == 0 && Math.abs(y - yt) > 1) {
                                Chessboard[x][y].updatePosition(xt, yt);
                                Chessboard[xt][yt] = Chessboard[x][y];
                                Chessboard[x][y] = null;
                                if(Evaluation.evaluation(true,Chessboard)==Double.NEGATIVE_INFINITY){
                                    flag=true;
                                    System.out.println("cannot make that move");
                                    new IllegalMove("Cannot castle");
                                    Chessboard[xt][yt].updatePosition(x,y);
                                    Chessboard[x][y]=Chessboard[xt][yt];
                                    Chessboard[xt][yt]=holder;
                                }
                                else if (yt > 4) {//castling to left
                                    Chessboard[x][7].updatePosition(x, 5);
                                    Chessboard[x][5] = Chessboard[x][7];
                                    Chessboard[x][7] = null;
                                    flag=true;
                                } else {//castling to right
                                    Chessboard[x][0].updatePosition(x, 3);
                                    Chessboard[x][3] = Chessboard[x][0];
                                    flag=true;
                                    Chessboard[x][0] = null;
                                }
                            } else {
                                enPassentReset(Chessboard);
                                Chessboard[x][y].updatePosition(xt, yt);

                                if(yt!=y&&Chessboard[xt][yt]==null&&Chessboard[x][y].getRank()==Rank.PAWN){
                                    holder2=Chessboard[x][yt];
                                    enpassant=true;
                                    Chessboard[x][yt]=null;
                                }
                                Chessboard[xt][yt] = Chessboard[x][y];
                                flag=true;
                                Chessboard[x][y] = null;

                                if(Evaluation.evaluation(true,Chessboard)==Double.NEGATIVE_INFINITY){//leads to a check mate
                                    flag=false;
                                    System.out.println("cannot make that move");
                                    new IllegalMove("This leads to check");
                                    Chessboard[xt][yt].updatePosition(x,y);
                                    Chessboard[x][y]=Chessboard[xt][yt];
                                    Chessboard[xt][yt]=holder;
                                    if(enpassant)Chessboard[x][yt]=holder2;
                                }
                                else if((xt==7||xt==0)&&Chessboard[xt][yt].getRank()==Rank.PAWN){
                                    Promotion pt = new Promotion();
                                    int choice = pt.getResponse();
                                    promotion(xt,yt,Chessboard,choice);
                                }
                            }
                        } else{
                            System.out.println("not legal");
                            new IllegalMove("this is not a legal move");
                        }

                    } else
                        System.out.println("not legally legal");


//            }
        if(flag) {
            p = new Node(Chessboard, null);
            System.out.println("thinking");
            minimax(p, depthweAt, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, false);

            if (p.children.size() == 0) {
                if (Evaluation.evaluation(false, p.Chessboard) == Double.NEGATIVE_INFINITY){
                    System.out.println("You win");
                    new IllegalMove("You win");
                }
                else
                    System.out.println("StaleMate");
                System.exit(0);
            }
            Chessboard = p.children.get(pos < p.children.size() ? pos : p.children.size() - 1).Chessboard;
            board = Chessboard;
            System.out.println(Evaluation.evaluation(false,p.Chessboard));
        }
    }

    public static void updateDepth(int depth){
        depthweAt=depth;
    }
    public static int convertChar(char c){
        int chara = (int)c;
        if(c>=97 && c<= 104){
                if(chara%8 == 0){
                    return 7;
                }
                return (chara%8)-1;
        }
        return -1;
    }

    /**
     * this function promotes a given pown to the choice given
     * 0 for rook
     * 1 for knight
     * 2 for bishop
     * 3 for queen
     * @param i this is the x cooridnate
     * @param j this is the y coordinate
     * @param Chessboard
     * @param choice
     */
    public void promotion(int i , int j, Piece[][] Chessboard,int choice){
        switch (choice){
            case 0:
                Chessboard[i][j]=new Rook(i,j,Chessboard[i][j].isWhite());
                ((Rook)Chessboard[i][j]).castling=false;
                break;
            case 1:
                Chessboard[i][j]=new Knight(i,j,Chessboard[i][j].isWhite());
                break;
            case 2:
                Chessboard[i][j]=new Bishop(i,j,Chessboard[i][j].isWhite());
                break;
            case 3:
                Chessboard[i][j]=new Queen(i,j,Chessboard[i][j].isWhite());
                break;
        }
    }

    /**
     * this function is the minmax function
     * @param position the current position we are at
     * @param depth how much deep we want to look
     * @param alpha this is the alpha which starts as negative infinity
     * @param beta starts as positive infinity for the min player
     * @param maxplayer to know if it is min or max playing
     * @param whiteToPlay who's perspective we are evaluating the board from
     * @return this returns the value of the best child at depth given. However, there is a global variable which is changed according to which moves child is choosen.
     */
    public double minimax(Node position, int depth, double alpha, double beta, boolean maxplayer,boolean whiteToPlay){
        if(depth==0) {
            double temp=Evaluation.evaluation(whiteToPlay, position.Chessboard);
            if(temp==Double.NEGATIVE_INFINITY)temp=Double.MAX_VALUE*-1;
            return temp;
        }
        if(maxplayer){
            double maxVal = Double.NEGATIVE_INFINITY;
            int temp = 0;
            generateMoves(position,whiteToPlay);
            for (Node s :
                    position.children) {

                double eval= minimax(s, depth-1,alpha,beta,false,whiteToPlay);
                if(Evaluation.evaluation(!whiteToPlay,s.Chessboard)==Double.NEGATIVE_INFINITY) {
                    if (depth==depthweAt) {
                        if (s.children.isEmpty()) {
                            pos = temp ;
                            return 0;
                        }
                    }
                }
                s.children=null;
                if(Math.max(maxVal,eval)>maxVal&&depth==depthweAt){
                    pos=temp;
                }
                maxVal=Math.max(maxVal,eval);
                alpha=Math.max(alpha,eval);
                if(beta<=alpha ){
                    break;
                }
                temp++;
            }
            return maxVal;
        }
        else{
            double minEval = Double.POSITIVE_INFINITY;
            generateMoves(position,!whiteToPlay);

            for (Node s :
                    position.children) {
                double eval = minimax(s, depth - 1, alpha, beta, true, whiteToPlay);
                s.children=null;
                minEval=Math.min(minEval,eval);
                beta = Math.min(beta,eval);
                if(beta<=alpha)break;
            }
            if(minEval==Double.POSITIVE_INFINITY)minEval=Double.MAX_VALUE;
            return minEval;
        }
}
    /**
     * this just prints the board nicely for debugging purposes.
     * @param Chessboard the chess board to be printed
     */
    public static void printer(Piece[][] Chessboard){
        for (int i = -1; i <8 ; i++) {
            char temp = 'A';
            System.out.print(i==-1?"  |":(char)(temp+i)+" ");
        }
        System.out.println();
        for (int i = 0; i <=8; i++) {
            System.out.print("--");
        }
        System.out.println();
        int counter = 8;
        for (int i = 0; i < 8; i++) {
            for (int j = -1; j < 8; j++) {
                if(j!=-1){
                    if (Chessboard[i][j] != null) {
                        System.out.print(Chessboard[i][j]);
                    } else
                        System.out.print(". ");
                }
                else{
                    System.out.print(counter+" |");
                    counter--;
                }
            }
            System.out.println();
        }
    }

    /**
     * this generates the moves for a given node with whose moves we are making.
     * @param parent this contains the link to the parent chessboard from which all other moves are made.
     * @param whiteToPlay This indicates whose turn it is to play.
     */
    public void generateMoves(Node parent, boolean whiteToPlay) {
        LinkedList<Node> children = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (parent.Chessboard[i][j] != null) {
                    if (parent.Chessboard[i][j].isWhite() == whiteToPlay) {
                        switch (parent.Chessboard[i][j].getRank()) {
                            case KING:
                                moveKing(parent.Chessboard,i,j,children);
                                break;
                            case QUEEN:
                                moveQueen(parent.Chessboard, i,j,children);
                                break;
                            case BISHOP:
                                moveBishop(parent.Chessboard,i,j,children);
                                break;
                            case KNIGHT:
                                moveKnight(parent.Chessboard,i,j,children);
                                break;
                            case ROOK:
                                moveRook(parent.Chessboard,i,j,children);
                                break;
                            case PAWN:
                                movePawn(parent.Chessboard, i, j, children);
                                break;
                        }
                    }
                }
            }
        }
        parent.children=children;
    }

    /**
     * this is a helper function for generate moves
     * @param Chessboard the chess board we are making the movement
     * @param i the x position of the chess piece
     * @param j the y position of the chess piece
     * @param children a linked list to add the children to
     */
    public void movePawn(Piece[][] Chessboard, int i , int j , LinkedList<Node> children ){
        int counter = Chessboard[i][j].isWhite()?-1:1;
        boolean isWhite=Chessboard[i][j].isWhite();
        Piece[][] temp;
        if(Chessboard[i][j].isLegalMove(i+counter,j,Chessboard)){

            temp = GameMaster.copyOf(Chessboard);
            temp[i][j].updatePosition(i+counter,j);
            temp[i+counter][j]=temp[i][j];
            temp[i][j]=null;
//            temp[i+counter][j].updatePosition(i+counter,j);
//            ((Pawn)(temp[i+counter][j])).
            if(Chessboard[i+counter][j]==null) {
                if(i+counter==7||i+counter==0)
                for (int k = 0; k < 4; k++) {
                    temp = GameMaster.copyOf(Chessboard);
                    temp[i][j].updatePosition(i + counter, j);
                    temp[i + counter][j] = temp[i][j];
                    temp[i][j] = null;
                    promotion(i + counter, j, temp, k);
                    if (Evaluation.evaluation(isWhite, temp) != Double.NEGATIVE_INFINITY)
                        children.add(new Node(temp, null));
                }

                else if (Evaluation.evaluation(isWhite, temp) != Double.NEGATIVE_INFINITY)
                    children.add(new Node(temp, null));
            }
        }

        if(((Pawn)Chessboard[i][j]).getExtraStep()){
            if(Chessboard[i][j].isLegalMove(i+(2*counter),j,Chessboard)){
                temp = GameMaster.copyOf(Chessboard);
                temp[i][j].updatePosition(i+(2*counter),j);
                temp[i+(2*counter)][j]=temp[i][j];
                temp[i][j]=null;
//                temp[i+(2*counter)][j].updatePosition(i+(2*counter),j);
                if(Evaluation.evaluation(isWhite,temp)!=Double.NEGATIVE_INFINITY)
                children.add(new Node(temp,null));
            }
        }

        for (int k = -1; k < 2; k++) {
            if(k!=0){
                if(Chessboard[i][j].isLegalMove(i+counter,j+k,Chessboard)){
                    temp=GameMaster.copyOf(Chessboard);
                    temp[i][j].updatePosition(i+counter,j+k);
                    if(temp[i+counter][j+k]==null)temp[i][j+k]=null;
                    temp[i+counter][j+k]=temp[i][j];
                    temp[i][j]=null;
                    if((i+counter==7||i+counter==0))
                        for (int l = 0; l < 4; l++) {
                            temp = GameMaster.copyOf(Chessboard);
                            temp[i][j].updatePosition(i+counter,j);
                            temp[i+counter][j+k]=temp[i][j];
                            temp[i][j]=null;
                            promotion(i+counter,j+k,temp,l);
                            if(Evaluation.evaluation(isWhite,temp)!=Double.NEGATIVE_INFINITY)
                                children.add(new Node(temp,null));
                        }
                    else
                    if(Evaluation.evaluation(isWhite,temp)!=Double.NEGATIVE_INFINITY)
                        children.add(new Node(temp,null));
                }
            }
        }
    }

    /**
     * This function is used to reset the enpassent value after each move by each player
     * @param Chessboard this is the chessboard which we are resetting.
     */
    public void enPassentReset(Piece[][] Chessboard){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(Chessboard[i][j]!=null){
                    if(Chessboard[i][j].getRank()==Rank.PAWN){
                        ((Pawn)Chessboard[i][j]).enPasseat=false;
                    }
                }
            }
        }
    }
    /**
     * this moves the bishop in every possible direction given a chessboard and adds it to the children
     * @param Chessboard this is the chessboard that is the current one
     * @param i this is the x position of the current bishop position
     * @param j this is the y position of the current bishop position
     * @param children this is the linked list for the generated moves.
     */
    public void moveBishop(Piece[][] Chessboard, int i, int j, LinkedList<Node> children ) {
        Piece[][] temp;
        boolean isWhite=Chessboard[i][j].isWhite();
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                int temp1=i;
                int temp2=j;
                if(k!=0&&l!=0)
                while(Chessboard[i][j].isLegalMove(temp1+k,temp2+l,Chessboard)){
                    if(Chessboard[temp1+k][temp2+l]==null||Chessboard[temp1+k][temp2+l].getRank()!=Rank.KING) {
                        temp = GameMaster.copyOf(Chessboard);
                        temp[i][j].updatePosition(temp1+k, temp2+l);
                        temp[temp1+k][temp2+l] = temp[i][j];
                        temp[i][j] = null;
                        if(Evaluation.evaluation(isWhite,temp)!=Double.NEGATIVE_INFINITY)
                            children.add(new Node(temp, null));
                        }else break;
                        temp1=temp1+k;
                        temp2=temp2+l;
                }
            }
        }
    }

    /**
     * this moves the knight to every possible direction given a chessboard and adds it to a linked list
     * @param Chessboard the current board
     * @param i the x position of the current position of the bishop
     * @param j the y position of the current position of the bishop
     * @param children the linked list we are adding to
     */
    public void moveKnight(Piece[][] Chessboard,int i, int j, LinkedList<Node> children){
        Piece[][] temp;
        boolean isWhite=Chessboard[i][j].isWhite();
        for (int k = 0; k < 4; k++) {
            int s,u;
            s=0;
            u=0;
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
                        if(Chessboard[i+(l*s)][j+(m*u)]==null||Chessboard[i+(l*s)][j+(m*u)].getRank()!=Rank.KING) {
                            temp = GameMaster.copyOf(Chessboard);
                            temp[i][j].updatePosition(i + (l * s), j + (m * u));
                            temp[i + (l *  s)][j + (m * u)] = temp[i][j];
                            temp[i][j] = null;
                            if(Evaluation.evaluation(isWhite,temp)!=Double.NEGATIVE_INFINITY)
                                children.add(new Node(temp, null));
                        }
                    }
                }
            }
        }
    }

    /**
     * this moves teh rook to every possible direction and adds it to the children linkedlist provided.
     * @param Chessboard this is the chessboard, from which we are creating the children of.
     * @param i this is the x position of the rook which we are moving.
     * @param j this is the y position of the rook which we are moving.
     * @param children this is the linked list  to which we are adding the possible moves of the rook.
     */
    public void moveRook(Piece[][] Chessboard, int i, int j, LinkedList<Node> children){
        boolean isWhite=Chessboard[i][j].isWhite();

        Piece[][] temp;

            for (int l = -1; l < 2; l++) {
                int tempx=i;
                int tempy=j;
                if(l!=0) {
                    while (Chessboard[i][j].isLegalMove(tempx + l, tempy, Chessboard)) {
                        if (Chessboard[tempx + l][tempy] == null || Chessboard[tempx + l][tempy].getRank() != Rank.KING) {
                            temp = GameMaster.copyOf(Chessboard);
                            temp[i][j].updatePosition(tempx + l, tempy);
                            temp[tempx + l][tempy] = temp[i][j];
                            temp[i][j] = null;
                            if (Evaluation.evaluation(isWhite, temp) != Double.NEGATIVE_INFINITY)
                                children.add(new Node(temp, null));
                            tempx = tempx + l;
                        } else break;
                    }//end of loop
                    tempx=i;
                    tempy=j;
                    while (Chessboard[i][j].isLegalMove(tempx, tempy + l, Chessboard)) {
                        if (Chessboard[tempx][tempy + l] == null ||Chessboard[tempx][tempy + l].getRank() != Rank.KING) {
                            temp = GameMaster.copyOf(Chessboard);
                            temp[i][j].updatePosition(tempx, tempy + l);
                            temp[tempx][tempy + l] = temp[i][j];
                            temp[i][j] = null;
                            if (Evaluation.evaluation(isWhite, temp) != Double.NEGATIVE_INFINITY)
                                children.add(new Node(temp, null));
                            tempy = tempy + l;
                        } else break;
                    }//end of loop
                }
            }

    }

    /**
     * this function takes advantage of the movebishop and the move rook function, as a queen basically acts like a bishop and rook as together.
     * @param Chessboard this is the chessboard which is the parent and is used to see which moves can be moved.
     * @param i this is the x position of the queen in question
     * @param j this is the y position of the queen which we are moving
     * @param children this is the list of the possible moves for the queen.
     */
    public void moveQueen(Piece[][] Chessboard, int i, int j, LinkedList<Node> children){
        moveBishop(Chessboard,i,j,children);
        moveRook(Chessboard,i,j,children);
    }

    /**
     * this function generates the moves for a king.
     * @param Chessboard the chessboard from which the moves are made.
     * @param i the x position of the king
     * @param j the y position of the king.
     * @param children this is the list of the all the possible moves from given configuration of the chessboard.
     */
    public void moveKing(Piece[][] Chessboard,int i, int j, LinkedList<Node> children){
        Piece[][] temp;
        boolean isWhite=Chessboard[i][j].isWhite();
        int tempx=i;
        int tempy=j;
        tempx=tempx-1;
        tempy=tempy-1;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(k!=1||l!=1)
                if(Chessboard[i][j].isLegalMove(tempx+k,tempy+l,Chessboard)){
                        temp=GameMaster.copyOf(Chessboard);
                        temp[i][j].updatePosition(tempx+k,tempy+l);
                        temp[tempx+k][tempy+l]=temp[i][j];
                        temp[i][j]=null;
                        if (Evaluation.evaluation(isWhite, temp) != Double.NEGATIVE_INFINITY)
                            children.add(new Node(temp,null));
                    }
            }
        }
        //here might be the castling

        if(((King)Chessboard[i][j]).canCastle()&&
                (j+3)<8&&
                Chessboard[i][j+1]==null&&
                Chessboard[i][j+2]==null&&
                Chessboard[i][j+3]!=null&&
                (Chessboard[i][j].isWhite()?Chessboard[i][j+3].isWhite():!Chessboard[i][j+3].isWhite())&&
                Chessboard[i][j+3].getRank()==Rank.ROOK&&
                ((Rook)Chessboard[i][j+3]).canCastle()&&
                Evaluation.evaluation(Chessboard[i][j].isWhite(),Chessboard)!=Double.NEGATIVE_INFINITY
        ){//if it has not moved
            temp = GameMaster.copyOf(Chessboard);
            temp[i][j+1]=temp[i][j];
            temp[i][j]=null;
            //still have to check if there are two pieces between
            if(Evaluation.evaluation(Chessboard[i][j].isWhite(),temp)!=Double.NEGATIVE_INFINITY){
                temp[i][j+2]=temp[i][j+1];
                temp[i][j+1]=null;
                if(Evaluation.evaluation(Chessboard[i][j].isWhite(),temp)!=Double.NEGATIVE_INFINITY){
                    temp[i][j+1]=temp[i][j+3];
                    temp[i][j+3]=null;
                    temp[i][j+1].updatePosition(i,j+1);
                    temp[i][j+2].updatePosition(i,j+2);
                    children.add(new Node(temp,null));
                }
            }
        }
        if(((King)Chessboard[i][j]).canCastle()&&
                (j-4)>-1&&
                Chessboard[i][j-1]==null&&
                Chessboard[i][j-2]==null&&
                Chessboard[i][j-3]==null&&
                Chessboard[i][j-4]!=null&&
                (Chessboard[i][j].isWhite()?Chessboard[i][j-4].isWhite():!Chessboard[i][j-4].isWhite())&&
                Chessboard[i][j-4].getRank()==Rank.ROOK&&
                ((Rook)Chessboard[i][j-4]).canCastle()&&
                Evaluation.evaluation(Chessboard[i][j].isWhite(),Chessboard)!=Double.NEGATIVE_INFINITY
        ){//this is the long side
            temp = GameMaster.copyOf(Chessboard);
            temp[i][j-1]=temp[i][j];
            temp[i][j]=null;
            //still have to check if there are two pieces between
            if(Evaluation.evaluation(Chessboard[i][j].isWhite(),temp)!=Double.NEGATIVE_INFINITY){
                temp[i][j-2]=temp[i][j-1];
                temp[i][j-1]=null;
                if(Evaluation.evaluation(Chessboard[i][j].isWhite(),temp)!=Double.NEGATIVE_INFINITY){
                    temp[i][j-1]=temp[i][j-4];
                    temp[i][j-4]=null;
                    temp[i][j-1].updatePosition(i,j-1);
                    temp[i][j-2].updatePosition(i,j-2);
                    children.add(new Node(temp,null));
                }
            }
        }
    }
    public static Piece[][] copyOf(Piece[][] Chessboard){
        Piece[][] returnThis= new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                returnThis[i][j]=new P
                if (Chessboard[i][j] != null) {
                    switch (Chessboard[i][j].getRank()) {
                        case KING:
                            returnThis[i][j] = new King(i, j, Chessboard[i][j].isWhite());
                            ((King)returnThis[i][j]).castling=((King)Chessboard[i][j]).castling;
                            break;
                        case QUEEN:
                            returnThis[i][j] = new Queen(i, j, Chessboard[i][j].isWhite());
                            break;
                        case BISHOP:
                            returnThis[i][j] = new Bishop(i, j, Chessboard[i][j].isWhite());
                            break;
                        case KNIGHT:
                            returnThis[i][j] = new Knight(i, j, Chessboard[i][j].isWhite());
                            break;
                        case ROOK:
                            returnThis[i][j] = new Rook(i, j, Chessboard[i][j].isWhite());
                            ((Rook)returnThis[i][j]).castling=((Rook)Chessboard[i][j]).castling;
                            break;
                        case PAWN:
                            returnThis[i][j] = new Pawn(i, j, Chessboard[i][j].isWhite());
                            ((Pawn)returnThis[i][j]).enPasseat=((Pawn)Chessboard[i][j]).enPasseat;
                            break;
                    }
                }
            }
        }
        return returnThis;
    }
    public static void instantiateBoard(File file){
        Piece[][] Chessboard = new Piece[8][8];
    //    File f = new File("testMate");
        try {
            Scanner s = new Scanner(file);
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
        board=Chessboard;
    }
public static void main(String[] args){
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
    board=Chessboard;
//       new GameMaster(Chessboard);
//    System.out.println(Evaluation.evaluation(false,Chessboard));
    System.out.println();
}
}
