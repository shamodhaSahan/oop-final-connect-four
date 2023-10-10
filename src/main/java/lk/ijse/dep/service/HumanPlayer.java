package lk.ijse.dep.service;

public class HumanPlayer extends Player {
    public HumanPlayer(Board newBoard) {
        super(newBoard);
    }
    @Override
    public void movePiece(int col){
        if(board.isLegalMove(col)){
            board.updateMove(col, Piece.BLUE);
            BoardUI boardUI= board.getBoardUI();
            boardUI.update(col,true);
            Winner winner=board.findWinner();
            if(winner!=null){
               boardUI.notifyWinner(winner);
            }else {
                if(board.existLegalMove()){
                    boardUI.notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }
}
