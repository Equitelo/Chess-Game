package chess;

import pieces.Pieces;

public class CheckScanner {
    Board board;
    
    public CheckScanner (Board board) {
        this.board=board;
    }
    
    public boolean isKingCheck(Move move){
        Pieces king = board.findKing(move.piece.isWhite);
        assert king != null;
        
        int kingCol = king.col;
        int kingRow = king.row;
        
        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")){
        
            kingCol = move.newCol;
            kingRow = move.newRow;
            
        }
        
        return hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) || //up
               hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0) || //right
               hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1) || //down
               hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) || //left
                
               hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) || //up left
               hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) || //up right
               hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) || //down right
               hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) || //down left
                
               hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||
               hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow) ||
               hitByKing(king, kingCol, kingRow);
               
    }
    
    private boolean hitByRook(int col, int row, Pieces king, int kingCol, int kingRow, int colValue, int rowValue){
        
        for (int i = 1; i < 8; i++) {
            if (kingCol + (i * colValue) == col && kingRow + (i * rowValue) == row) {
                break;
            }
            
            Pieces piece = board.getPiece(kingCol + (i * colValue), kingRow + (i * rowValue));
            
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                else{
                    break;
                }   
            }
        }
        return false;
    }
    
    private boolean hitByBishop(int col, int row, Pieces king, int kingCol, int kingRow, int colValue, int rowValue){
        
        for (int i = 1; i < 8; i++) {
            if (kingCol - (i*colValue) == col && kingRow - (i*rowValue) == row) {
                
                break;
                
            }
            
            Pieces piece = board.getPiece(kingCol - (i*colValue), kingRow - (i*rowValue));
            
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king)&& (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }else{
                    break;
                }
            }
            
        }
        return false;
    }
    
    private boolean hitByKnight(int col, int row, Pieces king, int kingCol, int kingRow){
        return checkKight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
               checkKight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
               checkKight(board.getPiece(kingCol + 2, kingRow - 2), king, col, row) ||
               checkKight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
               checkKight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
               checkKight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
               checkKight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
               checkKight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }
    
    private boolean checkKight(Pieces p, Pieces k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }
    
    private boolean hitByKing(Pieces king, int kingCol, int kingRow){
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
               checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
               checkKing(board.getPiece(kingCol, kingRow - 1), king) ||
               checkKing(board.getPiece(kingCol - 1, kingRow), king) ||
               checkKing(board.getPiece(kingCol + 1, kingRow), king) ||
               checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
               checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
               checkKing(board.getPiece(kingCol, kingRow + 1), king);
    }
    
    private boolean checkKing(Pieces p, Pieces k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }
    
    private boolean hitByPawn(int col, int row, Pieces king, int kingCol, int kingRow){
        int  colorValue = king.isWhite ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorValue), king, col, row) || 
               checkPawn(board.getPiece(kingCol - 1, kingRow + colorValue), king, col, row);
    }
    
    private boolean checkPawn(Pieces p, Pieces k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }
    
    public boolean isGameOver(Pieces king){
        for(Pieces piece: board.pieceList){
            if (board.sameTeam(piece, king)) {
                board.selectedPiece = piece == king ? king : null;
                for(int row = 0; row < board.rows; row++){
                    for (int col = 0; col < board.columns; col++) {
                        Move move = new Move(board, piece, col, row);
                        if(board.isValidMove(move)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
}
