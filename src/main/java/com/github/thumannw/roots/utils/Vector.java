package com.github.thumannw.roots.utils;

import java.util.Arrays;

public class Vector {

    protected double[] coords;

    public Vector(double... coordinates) {
        this.coords = coordinates;
    }

    public int getDimension() {
        return this.coords.length;
    }

    public double[] getCoordinates() {
        return this.coords;
    }

    public double scalarProduct(Vector other) {
        if (this.getDimension() != other.getDimension()) {
            throw new AssertionError();
        }
        double result = 0d;
        for (int i = 0; i < this.coords.length; ++i) {
            result = result + (this.coords[i] * other.coords[i]);
        }
        return result;
    }

    public void multiplyWith(double scalar) {
        for (int i = 0; i < this.coords.length; ++i) {
            this.coords[i] = this.coords[i] * scalar;
        }
    }

    public void normed() {
        double norm = Math.sqrt(this.scalarProduct(this));
        this.multiplyWith(1d / norm);
    }

    private Vector plusOrMinus(Vector v, boolean plus) {
        if (this.coords.length != v.coords.length) {
            throw new AssertionError();
        }
        double[] resultArr = new double[this.coords.length];
        for (int i = 0; i < resultArr.length; ++i) {
            double first = this.coords[i];
            double second = v.coords[i];
            double result;
            if (plus) {
                result = first + second;
            } else {
                result = first - second;
            }
            resultArr[i] = result;
        }
        return new Vector(resultArr);
    }

    public Vector add(Vector v) {
        return this.plusOrMinus(v, true);
    }

    public Vector subtract(Vector v) {
        return this.plusOrMinus(v, false);
    }

    public Vector extendOneDimension() {
        return new Vector(ArrayHelper.embed(this.coords));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.coords);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector other = (Vector) obj;
        return Arrays.equals(this.coords, other.coords);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.coords);
    }

}
