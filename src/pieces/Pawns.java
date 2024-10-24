/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pieces;

import chess.Board;
import java.awt.image.BufferedImage;

public class Pawns extends Pieces{
    
    public Pawns (Board board, int col, int row, boolean isWhite) {
        super(board);
        
        this.col=col;
        this.row=row;
        
        this.xPos=col*board.titleSize;
        this.yPos=row*board.titleSize;
        
        this.isWhite=isWhite;
        this.name="Pawn";
        
        sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    
    @Override
    public boolean isValidMovement(int col, int row){
        int colorIndex = isWhite ? 1 : -1;
        
        //push pawn 1
        if (this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null) 
            return true;
        //push pawn 2
        if (isFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null) 
            return true;
        //capture left
        if(col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row)!=null)
            return true;
        //capture right
        if(col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row)!=null)
            return true;
        
        // on passant left
        if(board.getTileNum(col, row) == board.onPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex)!=null)
            return true;
        
        // on passant left
        if(board.getTileNum(col, row) == board.onPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex)!=null)
            return true;
        
        return false;
    }
    
}
