package com.github.thumannw.roots.rootsys.irreducibles;

import com.github.thumannw.roots.rootsys.AbstractRootSys;
import com.github.thumannw.roots.utils.Matrix;
import com.github.thumannw.roots.utils.NoverK;
import com.github.thumannw.roots.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class A extends AbstractRootSys {

    private final int degree;

    public A(int degree) {
        this.degree = degree;

        List<Vector> diagONB = this.createOrthogonalBasisOfDiagonalSpace(); // not an ONB yet
        diagONB.forEach(Vector::normed); // now it's an ONB
        Matrix diagTrafo = Matrix.fromRows(diagONB);
        List<Vector> diagRoots = this.createDiagRoots();
        List<Vector> result = diagRoots.stream().map(diagTrafo::multiply).collect(Collectors.toList());
        this.addVectors(result);
    }

    private List<Vector> createOrthogonalBasisOfDiagonalSpace() {
        int dim = this.getAmbientDimension() + 1;
        List<Vector> result = new ArrayList<>();
        for (int i = 0; i < dim - 1; ++i) {
            double val = 1d / (i + 1);
            double[] x = new double[dim];
            for (int k = 0; k < x.length; ++k) {
                if (k <= i) {
                    x[k] = val;
                } else if (k == i + 1) {
                    x[k] = -1d;
                } else {
                    x[k] = 0d;
                }
            }
            result.add(new Vector(x));
        }
        return result;
    }

    private List<Vector> createDiagRoots() {
        int dim = this.getAmbientDimension() + 1;
        List<Vector> result = new ArrayList<>();
        NoverK x = new NoverK(dim, 2);
        List<double[]> pm = x.getSolutions(1, -1, 0);
        pm.stream().map(Vector::new).forEach(result::add);
        List<double[]> mp = x.getSolutions(-1, 1, 0);
        mp.stream().map(Vector::new).forEach(result::add);
        return result;
    }

    @Override
    public int getAmbientDimension() {
        return this.degree;
    }

}
