package GUI;

import javax.swing.*;

public class IllegalMove extends JFrame {
    /**
     *  this is used to create a pop up with the given message.
     * @param message the message that you want to display.
     */
    public IllegalMove(String message){
        JOptionPane.showMessageDialog(null,message,"This is not right!",JOptionPane.ERROR_MESSAGE);
    }
}
