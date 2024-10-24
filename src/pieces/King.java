/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pieces;

import chess.Board;
import chess.Move;
import java.awt.image.BufferedImage;

public class King extends Pieces{
    public King (Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col=col;
        this.row=row;
        
        this.xPos=col*board.titleSize;
        this.yPos=row*board.titleSize;
        
        this.isWhite=isWhite;
        this.name="King";
        
        this.sprite=sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    
    @Override
    public boolean isValidMovement(int col, int row){
        return Math.abs((col - this.col) * (row - this.row)) == 1 || (Math.abs(col - this.col) + Math.abs(row - this.row))==1 || canCastle(col, row);
    }
    
    private boolean canCastle(int col, int row){
        
        if (this.row == row) {
            if (col == 6) {
                Pieces rook = board.getPiece(7, row);
                if (rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPiece(5, row) == null &&
                           board.getPiece(6, row) == null &&
                           !board.checkScanner.isKingCheck(new Move(board, this, 5, row));
                }
            }else if(col == 2){
                Pieces rook = board.getPiece(0, row);
                if (rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPiece(3, row) == null &&
                           board.getPiece(2, row) == null &&
                            board.getPiece(1, row) == null &&
                           !board.checkScanner.isKingCheck(new Move(board, this, 3, row));
                }
            }
        }
        
        return false;
    }
}
