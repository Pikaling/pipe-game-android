package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board;

import java.io.Serializable;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModelFactory;

public class GridModel {

    private int numRows;
    private int numCols;
    private BoardTileModel[][] grid;
    private BoardTileModelFactory boardTileModelFactory;

    public GridModel(BoardTileModelFactory boardTileModelFactory, int numRows, int numCols) {
        this.boardTileModelFactory = boardTileModelFactory;
        this.numRows = numRows;
        this.numCols = numCols;
        grid = new BoardTileModel[numRows][numCols];
    }

    public BoardTileModel[][] getGrid() {
        return grid;
    }

    public void create() {
        for (int rowCounter = 0; rowCounter < numRows; rowCounter++) {
            for (int colCounter = 0; colCounter < numCols; colCounter++) {
                set(new GridPosition(rowCounter, colCounter), boardTileModelFactory.createTileModel());
            }
        }
    }

    public void set(GridPosition gridPosition, BoardTileModel boardTileModel) {
        grid[gridPosition.row()][gridPosition.col()] = boardTileModel;
        boardTileModel.setGridPosition(gridPosition);
    }

    public BoardTileModel get(GridPosition gridPosition) throws GridPositionOutOfBoundsException {
        if (isValidPosition(gridPosition)) {
            return grid[gridPosition.row()][gridPosition.col()];
        } else {
            throw new GridPositionOutOfBoundsException();
        }
    }

    private boolean isValidPosition(GridPosition gridPosition) {
        return gridPosition.row() >= 0 && gridPosition.row() < numRows && gridPosition.col() >= 0 && gridPosition.col() < numCols;
    }

    public static class GridPosition implements Serializable {

        private final int row;
        private final int col;

        public GridPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int row() {
            return row;
        }

        public int col() {
            return col;
        }

        public GridPosition add(GridPosition other) {
            return new GridPosition(this.row + other.row(), this.col + other.col());
        }

        public GridPosition subtract(GridPosition other) {
            return new GridPosition(this.row - other.row(), this.col - other.col());
        }

        public GridPosition inverse() {
            return new GridPosition(this.row * -1, this.col * -1);
        }
    }
}