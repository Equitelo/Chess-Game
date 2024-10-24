/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import pieces.Pieces;

public class Input extends MouseAdapter{

    Board board;
    
    public Input(Board board){
       this.board=board;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
        int col = e.getX()/board.titleSize;
        int row = e.getY()/board.titleSize;
        
        Pieces PieceXY = board.getPiece(col, row);
        
        if (PieceXY != null) {
            board.selectedPiece = PieceXY;
        }
        
        board.repaint();
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX()/board.titleSize;
        int row = e.getY()/board.titleSize;
        
        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
            
            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.titleSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.titleSize;
            }
            
        }
        
        board.selectedPiece = null;
        board.repaint();
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        if (board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.titleSize/2;
            board.selectedPiece.yPos = e.getY() - board.titleSize/2;
            
            board.repaint();
        }
        
    }
    
}
