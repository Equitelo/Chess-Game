/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pieces;

import chess.Board;
import java.awt.image.BufferedImage;

public class Queen extends Pieces{
    public Queen (Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col=col;
        this.row=row;
        
        this.xPos=col*board.titleSize;
        this.yPos=row*board.titleSize;
        
        this.isWhite=isWhite;
        this.name="Queen";
        
        this.sprite=sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    
    @Override
    public boolean isValidMovement(int col, int row){
        return this.col == col || this.row == row || Math.abs(this.col - col) == Math.abs(this.row - row);
    }
    
    @Override
    public boolean moveCollidesWithPiece(int col, int row){
        
        if (this.col == col || this.row == row) {
            //left
            if (this.col > col){
                for (int c = this.col - 1; c > col; c--) {
                    if (board.getPiece(c, this.row)!= null) {
                        return true;
                    }
                }
            }

            //right
            if (this.col < col){
                for (int c = this.col + 1; c < col; c++) {
                    if (board.getPiece(c, this.row)!= null) {
                        return true;
                    }
                }
            }

            //up
            if (this.row > row){
                for (int r = this.row - 1; r > row; r--) {
                    if (board.getPiece(this.col, r)!= null) {
                        return true;
                    }
                }
            }

            //down
            if (this.row < row){
                for (int r = this.row + 1; r < row; r++) {
                    if (board.getPiece(this.col, r)!= null) {
                        return true;
                    }
                }
            }
        }else{
            //up left
            if (this.col>col && this.row>row) {
            for (int i = 1; i < Math.abs(this.col-col); i++) {
                if (board.getPiece(this.col - 1, this.row - 1)!=null) {
                    return true;
                }
            }
            }
            //up right
            if (this.col<col && this.row>row) {
                for (int i = 1; i < Math.abs(this.col-col); i++) {
                    if (board.getPiece(this.col + 1, this.row - 1)!=null) {
                        return true;
                    }
                }
            }
            //down left
            if (this.col>col && this.row<row) {
                for (int i = 1; i < Math.abs(this.col-col); i++) {
                    if (board.getPiece(this.col - 1, this.row + 1)!=null) {
                        return true;
                    }
                }
            }
            //down right
            if (this.col<col && this.row<row) {
                for (int i = 1; i < Math.abs(this.col-col); i++) {
                    if (board.getPiece(this.col + 1, this.row + 1)!=null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
