package chess;

import javax.swing.*;
import java.awt.*;
import pieces.Pieces;
import java.util.ArrayList;
import java.util.stream.Collectors;
import pieces.*;

public class Board extends JPanel{
    public int titleSize = 85;
    
    int rows = 8;
    int columns = 8;
    
    ArrayList<Pieces> pieceList = new ArrayList<>();
    
    public Pieces selectedPiece;
    
    Input input = new Input(this);
    
    public CheckScanner checkScanner = new CheckScanner(this);
    
    public int onPassantTile = -1;
    
    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;
    
    public Board(){
        this.setMinimumSize(new Dimension(1000, 1000));
        this.setPreferredSize(new Dimension(rows*titleSize,columns*titleSize));
        
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        
        this.addPieces();
    }
    
    public Pieces getPiece(int col, int row){
        
        for (Pieces pos : pieceList) {
            if (pos.col == col && pos.row == row) {
                return pos;
            }
        }
        
        return null;
    }
    
    public void makeMove(Move move){
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if(move.piece.name.equals("King")){
            moveKing(move);
        }
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * titleSize;
            move.piece.yPos = move.newRow * titleSize;

            move.piece.isFirstMove = false;

            capture(move.capture);
            
            isWhiteToMove = !isWhiteToMove;
            
            updateGameState();
    }
    
    private void moveKing(Move move) {
        
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            Pieces rook;
            if(move.piece.col < move.newCol){
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * titleSize;
        }
        
    }
    
    private void movePawn(Move move) {
        //on passant
        
        int colorIndex = move.piece.isWhite ? 1 : -1;
        
        if(getTileNum(move.newCol, move.newRow) == onPassantTile){
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2){
            onPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }else{
            onPassantTile = -1;
        }
        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex) {
            promoteMove(move);
        }
    }
    
    public void capture(Pieces piece){
        pieceList.remove(piece);
    }
    
    public boolean isValidMove(Move move){
        
        if(move.piece.isWhite != isWhiteToMove){
            return false;
        }
        
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        
        if(!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }
        
        if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }
        
        if (checkScanner.isKingCheck(move)) {
            return false;
        }
        
        return true;
    }
    
    public boolean sameTeam(Pieces p1, Pieces p2){
        if (p1 == null || p2 == null) {
            return false;
        }
        
        return p1.isWhite == p2.isWhite;
        
    }
    
    public int getTileNum(int col, int row){
        return row * this.rows + col;
    }
    
    Pieces findKing(boolean isWhite){
        for (Pieces piece: pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }
    
    public void addPieces(){
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Pawns(this, 0, 1, false));
        pieceList.add(new Pawns(this, 1, 1, false));
        pieceList.add(new Pawns(this, 2, 1, false));
        pieceList.add(new Pawns(this, 3, 1, false));
        pieceList.add(new Pawns(this, 4, 1, false));
        pieceList.add(new Pawns(this, 5, 1, false));
        pieceList.add(new Pawns(this, 6, 1, false));
        pieceList.add(new Pawns(this, 7, 1, false));
        
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new Pawns(this, 0, 6, true));
        pieceList.add(new Pawns(this, 1, 6, true));
        pieceList.add(new Pawns(this, 2, 6, true));
        pieceList.add(new Pawns(this, 3, 6, true));
        pieceList.add(new Pawns(this, 4, 6, true));
        pieceList.add(new Pawns(this, 5, 6, true));
        pieceList.add(new Pawns(this, 6, 6, true));
        pieceList.add(new Pawns(this, 7, 6, true));
    }
    
    private void updateGameState(){
        
        Pieces king = findKing(isWhiteToMove);
        
        if (checkScanner.isGameOver(king)) {
            if(checkScanner.isKingCheck(new Move(this, king, king.col, king.row))){
                if(isWhiteToMove){
                    JOptionPane.showMessageDialog(null, "Black Wins!");
                }else {
                    JOptionPane.showMessageDialog(null, "White Wins!");
                } 
            }else{
                JOptionPane.showMessageDialog(null, "STALEMATE!");
            }
        } else if (insufficientMaterial(true) && insufficientMaterial(false)) {
            JOptionPane.showConfirmDialog(null, "Insufficient Material");
            isGameOver = true;
        }
    }
    
    private boolean insufficientMaterial(boolean isWhite){
        ArrayList<String> names = pieceList.stream().filter(p -> p.isWhite == isWhite).map(p -> p.name).collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Rook") || names.contains("Pawn")){
            return false;
        }
        return names.size() <3;
    }
    
    @Override
    public void paint(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        //paint board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                g2d.setColor((c + r)%2==0 ? Color.white : Color.orange);
                g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
            }
        }
        //paint highlights
        if (selectedPiece!=null){
        for (int r = 0; r < rows; r++) 
            for (int c = 0; c < columns; c++) {
                if (isValidMove(new Move(this, selectedPiece, c, r))) {
                    g2d.setColor(new Color(0, 255, 0, 128));
                    g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                }
            }
        }
        
        //paint pieces
        for(Pieces pos: pieceList){
            pos.paint(g2d);
        }
        
    }

    private void promoteMove(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }
}
