package Game;

import Pieces.Piece;

import java.util.LinkedList;

public class Node {
    Piece[][] Chessboard;
    LinkedList<Node> children;

    /**
     * this is wrapper class than a node, as this has a reference to the children and it's
     * @param Chessboard the chessboard this node is holding.
     * @param children the link to the children, i.e. all possible moves from the chessboard.
     */
    public Node(Piece[][] Chessboard,LinkedList<Node> children){
        this.Chessboard=Chessboard;
        this.children=children;
    }
}
