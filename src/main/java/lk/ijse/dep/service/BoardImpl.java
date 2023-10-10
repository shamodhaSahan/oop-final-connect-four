package lk.ijse.dep.service;
public class BoardImpl implements Board{
    private Piece[][] pieces;
    private BoardUI boardUi;
    public BoardImpl(BoardUI boardUi){
        this.boardUi=boardUi;
        setPieces(new Piece[NUM_OF_COLS][NUM_OF_ROWS]);
        for (int col=0;col<NUM_OF_COLS;col++){
            for (int row=0;row<NUM_OF_ROWS;row++){
                getPieces()[col][row]= Piece.EMPTY;
            }
        }
    }


    @Override
    public Piece[][] getPieces() {
        return pieces;
    }
    @Override
    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUi;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int row=0;row<NUM_OF_ROWS;row++)
            if (getPieces()[col][row]==Piece.EMPTY)
                return row;
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        return findNextAvailableSpot(col) != -1;
    }

    @Override
    public boolean existLegalMove() {
        for (int col=0;col<NUM_OF_COLS;col++)
            for (int row = 0; row < NUM_OF_ROWS; row++)
                if (getPieces()[col][row] == Piece.EMPTY)
                    return false;
        return true;
    }
    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)]=move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row]=move;
    }

    @Override
    public Winner findWinner() {
        for (int row = 0; row<NUM_OF_ROWS ; row++ ){//col of row - row
            for (int col = 0; col<3; col++){
                if (getPieces()[col][row] != Piece.EMPTY && getPieces()[col][row] == getPieces()[col+1][row] && getPieces()[col+1][row] == getPieces()[col+2][row] && getPieces()[col+2][row] == getPieces()[col+3][row]) {
                        return new Winner(getPieces()[col][row], col, row, col + 3, row);
                }
            }
        }
        for (int col = 0; col<NUM_OF_COLS ; col++ ){//row of col - col
            for (int row = 0; row<2; row++){
                if (getPieces()[col][row] != Piece.EMPTY && getPieces()[col][row] == getPieces()[col][row+1] &&
                        getPieces()[col][row+1] == getPieces()[col][row+2] &&  getPieces()[col][row+2] == getPieces()[col][row+3]) {
                        return new Winner(getPieces()[col][row], col, row, col, row + 3);
                }
            }
        }
        return null;
    }
}
