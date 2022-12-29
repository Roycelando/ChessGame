package GUI;

import javax.swing.*;
import java.awt.*;

public class Promotion {
    int response;

    /**
     * this is a pop up which is used to get the rank that, the user wants the promoted piece to be.
     */
    public Promotion(){
        Object[] options = {"Rook","Knight","Bishop","Queen"};
        response = JOptionPane.showOptionDialog(null,"What do you want to promote to?","Promotion",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[3]);
    }
    public int getResponse(){
        return response;
    }
}
