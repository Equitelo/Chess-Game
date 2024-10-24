/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pieces;

import chess.Board;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 *
 * @author user
 */
public class Pieces {
    
    public int col, row;
    public int xPos, yPos;
    
    public boolean isWhite;
    public String name;
    public int value;
    
    public boolean isFirstMove = true;
    
    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ChessPiecesSprite.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    protected int sheetScale = sheet.getWidth()/6;
    
    Image sprite;
    
    Board board;
    
    public Pieces(Board board){
        this.board=board;
    }
    
    public boolean isValidMovement(int col, int row){return true;}
    public boolean moveCollidesWithPiece(int col, int row){return false;}
    
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
    
}
