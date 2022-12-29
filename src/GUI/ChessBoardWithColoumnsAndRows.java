package GUI;

import Game.GameMaster;
import Pieces.Piece;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class ChessBoardWithColoumnsAndRows extends JFrame{

    int sourceX, sourceY, destX, destY, clicked;
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    File oldFile ;
    private JLabel depth;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";

    public ChessBoardWithColoumnsAndRows() {
        clicked =0;
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton New = new JButton("New");
        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    File f = new File("test");
                    GameMaster.instantiateBoard(f);
                    oldFile=f;
                    reInitalize();
//                    System.out.println("here");
                }
                catch (Exception ex){
                    ex.getStackTrace();
                }
            }
        });
        tools.add(New);
        JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                    GameMaster.instantiateBoard(chooser.getSelectedFile());
                    oldFile=chooser.getSelectedFile();
                    reInitalize();
                }
            }
        });
        tools.add(load);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File saver = new File("saved");
                    String chessboardy = "";

                        FileWriter fw = new FileWriter("saved");
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if(GameMaster.board[i][j]==null)chessboardy=chessboardy+". ";
                                else chessboardy=chessboardy+GameMaster.board[i][j];
                            }
                            chessboardy=chessboardy+"\n";
                        }
                    System.out.println(chessboardy);
                        fw.write(chessboardy);
                        fw.close();
                        new IllegalMove("Save file");
                   if(false) {
                        System.out.println("File exist");//overwriting issue
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        tools.add(save);

        JButton restore = new JButton("Restore");
        restore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMaster.instantiateBoard(oldFile);
                reInitalize();
            }
        });
        tools.add(restore);
        JButton change_depth=new JButton("Change the depth");
        change_depth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepthChanger dc = new DepthChanger();
//                System.out.println(dc.getDepth());
                GameMaster.updateDepth(dc.getDepth());
                reInitalize();
            }
        });
        tools.add(change_depth);

        tools.addSeparator();
        JButton b4 = new JButton("Resign");
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IllegalMove("Good bye");
                System.exit(0);
            }
        });
        tools.add(b4);

        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                int finalIi = ii;
                int finalJj = jj;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            if(clicked ==0&&GameMaster.board[finalIi][finalJj]!=null&&GameMaster.board[finalIi][finalJj].isWhite()){
                                sourceX = finalIi;
                                sourceY = finalJj;
                                clicked=clicked+1;
                                b.setBackground(Color.CYAN);

                            }
                            else if(clicked == 1 ){
                                destX = finalIi;
                                destY = finalJj;
                                System.out.println(destX+" "+destY);
                                clicked =0;
                                // sendToGameMaster
                                new GameMaster(GameMaster.board,sourceX,sourceY,destX,destY);
                                reInitalize();
                            }

                    }
                });
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..



                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
//                ImageIcon i=null;
                if(GameMaster.board[ii][jj]!=null) {
                    icon = new ImageIcon(image(GameMaster.board[ii][jj]));
                }
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.decode("#CDBDB1"));
                } else {
                    b.setBackground(Color.decode("#855E42"));
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        //fill the chess board
        depth=new JLabel("Depth :"+GameMaster.depthweAt);
        chessBoard.add(depth);
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            //chessBoardSquares[5][5].
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (8-ii),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }
    public  void reInitalize(){
        depth.setText("Depth : "+GameMaster.depthweAt);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
//                ImageIcon i=null;
                if(GameMaster.board[i][j]!=null) {
                    icon = new ImageIcon(image(GameMaster.board[i][j]));
                }
                chessBoardSquares[j][i].setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1)
                        //) {
                        || (j % 2 == 0 && i % 2 == 0)) {
                    chessBoardSquares[j][i].setBackground(Color.decode("#CDBDB1"));
                } else {
                    chessBoardSquares[j][i].setBackground(Color.decode("#855E42"));
                }
            }

        }
    }
    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }
    public BufferedImage image(Piece p){
        String path = "src/Pieces/ChessIcons/"+(p.isWhite()?"WhitePieces/":"BlackPieces/");
        switch(p.getRank()){
            case PAWN:path=path+"chess-pawn.png";
                break;
            case KNIGHT:path=path+"chess-knight.png";
                break;
            case BISHOP:path=path+"chess-bishop.png";
                break;
            case QUEEN:path=path+"chess-queen.png";
                break;
            case ROOK:path=path+"chess-rook.png";
                break;
            case KING:path=path+"chess-king.png";
                break;
        }

        BufferedImage returnThis= null;

        try {
            returnThis = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnThis;
    }
    public static void main(String[] args) {
        GameMaster.instantiateBoard(null);
        Runnable r = new Runnable() {

            @Override
            public void run() {
                ChessBoardWithColoumnsAndRows cb =
                        new ChessBoardWithColoumnsAndRows();
                JFrame f = new JFrame("ChessProject3p71");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

}
