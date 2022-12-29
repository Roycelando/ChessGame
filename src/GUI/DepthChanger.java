package GUI;

import javax.swing.*;

public class DepthChanger extends JFrame {
    private int depth;

    /**
     * this class is used to just make a pop up for the user input to change the depth of the search.
     */
    public DepthChanger(){
        depth=Integer.parseInt(JOptionPane.showInputDialog(null,"Input the depth"));
    }
    public int getDepth(){
        return depth;
    }
}
