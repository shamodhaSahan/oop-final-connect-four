package lk.ijse.dep.service;
public class AiPlayer extends Player {
    public AiPlayer(Board newBoard) {
        super(newBoard);
    }
    @Override
    public void movePiece(int col){
    //Using Ai with minimax
        Piece[][] pieces = board.getPieces();
        int maxEval= (int) Double.NEGATIVE_INFINITY;
        int row = 0;
        for (int lpCol=0; lpCol<board.NUM_OF_COLS; lpCol++){
            for (int plRow=0; plRow<board.NUM_OF_ROWS; plRow++){
                if (pieces[lpCol][plRow] == Piece.EMPTY){
                    board.updateMove(lpCol,plRow,Piece.GREEN);
                    int heuristicVal = minimax(0,false);
                    board.updateMove(lpCol,plRow,Piece.EMPTY);
                    if (heuristicVal > maxEval){
                        maxEval=heuristicVal;
                        col=lpCol;
                        row=plRow;
                    }
                }
            }
        }
        board.updateMove(col,row,Piece.GREEN);

    /*
    Using random with do while loop
      do {
            col= (int) (Math.random()*6);
        }while (!board.isLegalMove(col));
        board.updateMove(col,Piece.GREEN);
    */

        BoardUI boardUI= board.getBoardUI();
        boardUI.update(col,false);
        Winner winner=board.findWinner();
        if(winner!=null){
            boardUI.notifyWinner(winner);
        }else {
            if(board.existLegalMove()){
                boardUI.notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }
    private int minimax(int depth, boolean maximizingPlayer){
        Piece[][] pieces = board.getPieces();
        Winner winner = board.findWinner();
        if (depth == 4 || winner != null){
            if (winner == null) {
                return 0;
            }
            if (winner.getWinningPiece() == Piece.GREEN){
                return 1;
            }
            if (winner.getWinningPiece() == Piece.BLUE){
                return -1;
            }
        }
        if (maximizingPlayer){
            int maxEval = (int) Double.NEGATIVE_INFINITY;
            for (int row=0; row<board.NUM_OF_ROWS; row++){
                for (int col=0; col<board.NUM_OF_COLS; col++){
                    if (pieces[col][row] == Piece.EMPTY){
                        board.updateMove(col,row,Piece.GREEN);
                        int heuristicVal = minimax(depth+1,false);
                        board.updateMove(col,row,Piece.EMPTY);
                        maxEval = Math.max(heuristicVal,maxEval);
                    }
                }
            }
            return maxEval;
        }else {
            int minEval= (int) Double.POSITIVE_INFINITY;
            for (int row=0;row<board.NUM_OF_ROWS;row++){
                for (int col=0;col<board.NUM_OF_COLS;col++){
                    if (pieces[col][row] == Piece.EMPTY){
                        board.updateMove(col,row,Piece.BLUE);
                        int heuristicVal = minimax(depth+1,true);
                        board.updateMove(col,row,Piece.EMPTY);
                        minEval = Math.min(heuristicVal,minEval);
                    }
                }
            }
            return minEval;
        }
    }
}
