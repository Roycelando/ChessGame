package Pieces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Piece {
    private BufferedImage image;
    private int xPos, yPos;
    private double numAttacked, numDefended,numbeingattack;
    private Rank type;
    private boolean isWhite;

    /**
     * This constructor sets the pieces x position, y position, colour, and Rank
     *
     * @param xPos, x position of the piece
     * @param yPos, y postion of the piece
     * @param isWhite, the colour of the piece
     * @param type, the Rank of the piece
     */
    public Piece(int xPos, int yPos, boolean isWhite, Rank type) {
        this.type = type;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isWhite = isWhite;
        numAttacked=0;
        numDefended=0;
        numbeingattack=0;
    }

    public BufferedImage getImage(){
        return image;
    }

    /**
     * This method returns the rank of the piece
     *
     * @return returns the rank of the piece
     */
    public Rank getRank(){
        return type;
    };

    /**
     * Tis method returns the x position of the piece
     *
     * @return x position, the x position of the piece
     */
    public int getXPos(){
        return xPos;
    };

    /**
     * This method returns teh y position of the piece
     *
     * @return y position, the y position of the piece
     */
    public int getYPos(){
        return yPos;
    };
    /**
     * This method returns a boolean indicates if the piece is white or not
     *
     * @return isWhite, returns ture if the piece is white and false if black
     */
    public boolean isWhite(){
        return isWhite;
    };

    /**
     *This method returns a boolean to indicates if a move is valid
     *
     * @param destX, destination x position
     * @param destY, destination y postion
     *
     * @return this method returns true if the move is legal
     */
    public abstract boolean isLegalMove(int destX, int destY,Piece[][] chessBoard);

    /**
     *This method is to update the x and y position of the piece
     *
     * @param destX, destination x position
     * @param destY,  destination y position
     */
    public abstract void updatePosition(int destX, int destY);

    /**
     * This method is used to update the x position of the piece
     * @param destX, new x position
     */
    public void setXpos(int destX){
        this.xPos = destX;
    }
    /**
     * This method is used to update the x position of the piece
     * @param destY, new y position
     */
    public void setYpos(int destY){
        this.yPos = destY;
    }

    /**
     * This method is used to get the number of attacked pieces
     *
     * @return
     */
    public double getNumAttacked(){
        return  numAttacked;
    }

    public void setNumAttacked(int num){
        numAttacked=num;
    }
    public void setNumDefended(int num){
        numDefended=num;
    }
    /**
     * This method is sued to the the number of pieces defending this piece
     *
     * @return
     */
    public double getNumDefended() {
        return numDefended;
    }

    /**
     * This method is used to increment the number of pieces attacking this pieces
     */
    public void incrementNumAttacked(Piece attacked){
        numAttacked=numAttacked+attacked.getValue();
    }

    /**
     * This method is used to increment the number of pieces defending this piece
     *
     */
    public void incrementDefended(){
        numDefended++;
    }

    public double getNumbeingattack(){
        return numbeingattack;
    }
    public void setNumbeingattack(int num){
        numbeingattack=num;
    }
    public void incrementNumattacked(Piece attack){
        numbeingattack=numbeingattack-attack.getValue();
    }
    /**
     * @return value of the piece
     */
    public abstract double getValue();
    @Override
    public String toString(){
        switch (getRank()){
            case KING:
                return isWhite()?"K ":"k ";
            case KNIGHT:
                return isWhite()?"N ":"n ";
            case BISHOP:
                return isWhite()?"B ":"b ";
            case QUEEN:
                return isWhite()?"Q ":"q ";
            case ROOK:
                return isWhite()?"R ":"r ";
            case PAWN:
                return isWhite()?"P ":"p ";
        }
        return null;
    }
}
