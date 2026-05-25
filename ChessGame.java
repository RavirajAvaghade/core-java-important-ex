import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Piece {
    String type;
    boolean isWhite;
    int row, col;

    public Piece(String type, boolean isWhite, int row, int col) {
        this.type = type;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public String toString() {
        return (isWhite ? "W" : "B") + type.charAt(0);
    }
}

public class ChessGame extends JFrame {
    private JButton[][] boardButtons = new JButton[8][8];
    private Piece[][] boardPieces = new Piece[8][8];
    private Piece selectedPiece = null;
    private boolean whiteTurn = true;

    public ChessGame() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,8));

        initializeBoard();
        initializePieces();
        updateBoard();
        setVisible(true);
    }

    private void initializeBoard() {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                JButton btn = new JButton();
                btn.setOpaque(true);
                btn.setBorderPainted(false);
                if((i+j)%2==0) btn.setBackground(Color.WHITE);
                else btn.setBackground(Color.GRAY);
                int r = i;
                int c = j;
                btn.addActionListener(e -> buttonClicked(r,c));
                boardButtons[i][j] = btn;
                add(btn);
            }
        }
    }

    private void initializePieces() {
        // Pawns
        for(int j=0;j<8;j++){
            boardPieces[1][j] = new Piece("Pawn", false,1,j); // Black
            boardPieces[6][j] = new Piece("Pawn", true,6,j);  // White
        }
        // Rooks
        boardPieces[0][0] = new Piece("Rook", false,0,0);
        boardPieces[0][7] = new Piece("Rook", false,0,7);
        boardPieces[7][0] = new Piece("Rook", true,7,0);
        boardPieces[7][7] = new Piece("Rook", true,7,7);
        // Knights
        boardPieces[0][1] = new Piece("Knight", false,0,1);
        boardPieces[0][6] = new Piece("Knight", false,0,6);
        boardPieces[7][1] = new Piece("Knight", true,7,1);
        boardPieces[7][6] = new Piece("Knight", true,7,6);
        // Bishops
        boardPieces[0][2] = new Piece("Bishop", false,0,2);
        boardPieces[0][5] = new Piece("Bishop", false,0,5);
        boardPieces[7][2] = new Piece("Bishop", true,7,2);
        boardPieces[7][5] = new Piece("Bishop", true,7,5);
        // Queens
        boardPieces[0][3] = new Piece("Queen", false,0,3);
        boardPieces[7][3] = new Piece("Queen", true,7,3);
        // Kings
        boardPieces[0][4] = new Piece("King", false,0,4);
        boardPieces[7][4] = new Piece("King", true,7,4);
    }

    private void updateBoard() {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Piece p = boardPieces[i][j];
                if(p!=null) boardButtons[i][j].setText(p.toString());
                else boardButtons[i][j].setText("");
            }
        }
    }

    private void buttonClicked(int row, int col) {
        Piece clicked = boardPieces[row][col];
        if(selectedPiece==null){
            if(clicked != null && clicked.isWhite==whiteTurn){
                selectedPiece = clicked;
                boardButtons[row][col].setBackground(Color.YELLOW);
            }
        } else {
            if(isValidMove(selectedPiece,row,col)){
                // move piece
                boardPieces[selectedPiece.row][selectedPiece.col] = null;
                boardPieces[row][col] = selectedPiece;
                selectedPiece.row = row;
                selectedPiece.col = col;
                whiteTurn = !whiteTurn;
            }
            selectedPiece = null;
            // reset colors
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if((i+j)%2==0) boardButtons[i][j].setBackground(Color.WHITE);
                    else boardButtons[i][j].setBackground(Color.GRAY);
                }
            }
            updateBoard();
        }
    }

    private boolean isValidMove(Piece p, int row, int col){
        // basic move validation (without check/checkmate)
        if(p.type.equals("Pawn")){
            int dir = p.isWhite ? -1 : 1;
            if(col==p.col && boardPieces[row][col]==null && row==p.row+dir) return true; // move forward
            if(Math.abs(col-p.col)==1 && row==p.row+dir && boardPieces[row][col]!=null && boardPieces[row][col].isWhite!=p.isWhite) return true; // capture
        } else if(p.type.equals("Rook")){
            if(row==p.row || col==p.col) return true;
        } else if(p.type.equals("Knight")){
            if((Math.abs(p.row-row)==2 && Math.abs(p.col-col)==1)||(Math.abs(p.row-row)==1 && Math.abs(p.col-col)==2)) return true;
        } else if(p.type.equals("Bishop")){
            if(Math.abs(p.row-row)==Math.abs(p.col-col)) return true;
        } else if(p.type.equals("Queen")){
            if(row==p.row || col==p.col || Math.abs(p.row-row)==Math.abs(p.col-col)) return true;
        } else if(p.type.equals("King")){
            if(Math.abs(p.row-row)<=1 && Math.abs(p.col-col)<=1) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new ChessGame();
    }
}
