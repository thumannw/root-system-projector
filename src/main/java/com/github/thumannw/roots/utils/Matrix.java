package com.github.thumannw.roots.utils;

import java.util.List;

public class Matrix {

    protected int numRows;
    protected int numCols;
    protected double[][] coords; // double[columns][rows]

    public int getNumberOfRows() {
        return this.numRows;
    }

    public int getNumberOfColumns() {
        return this.numCols;
    }

    public Vector multiply(Vector vector) {
        if (this.getNumberOfColumns() != vector.getDimension()) {
            throw new AssertionError();
        }

        double[] resultCoords = new double[this.getNumberOfRows()];
        for (int row = 0; row < resultCoords.length; ++row) {
            double coord = 0d;
            for (int col = 0; col < vector.getDimension(); ++col) {
                coord = coord + (this.coords[col][row] * vector.getCoordinates()[col]);
            }
            resultCoords[row] = coord;
        }

        return new Vector(resultCoords);
    }

    public Vector getLastColumn() {
        return new Vector(this.coords[this.coords.length - 1]);
    }

    public static Matrix fromRows(List<Vector> rows) {
        if (rows.isEmpty()) {
            throw new AssertionError();
        }
        int first = rows.get(0).getDimension();
        for (int i = 1; i < rows.size(); ++i) {
            if (first != rows.get(i).getDimension()) {
                throw new AssertionError();
            }
        }

        Matrix result = new Matrix();
        result.numRows = rows.size();
        result.numCols = rows.get(0).getDimension();
        result.coords = new double[result.numCols][result.numRows];
        for (int rowIdx = 0; rowIdx < rows.size(); ++rowIdx) {
            double[] row = rows.get(rowIdx).getCoordinates();
            for (int colIdx = 0; colIdx < row.length; ++colIdx) {
                result.coords[colIdx][rowIdx] = row[colIdx];
            }
        }
        return result;
    }

}
