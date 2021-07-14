package com.github.thumannw.roots.rootsys.irreducibles;

import com.github.thumannw.roots.rootsys.AbstractRootSys;
import com.github.thumannw.roots.utils.NoverK;
import com.github.thumannw.roots.utils.Vector;

import java.util.List;

public class B extends AbstractRootSys {

    private final int degree;

    public B(int degree) {
        this.degree = degree;

        D d = new D(degree);
        List<Vector> fromD = d.getRoots();
        this.addVectors(fromD);
        int dim = this.getAmbientDimension();
        NoverK x = new NoverK(dim, 1);
        List<double[]> plus = x.getSolutions(1, 0);
        this.addArrays(plus);
        List<double[]> minus = x.getSolutions(-1, 0);
        this.addArrays(minus);
    }

    @Override
    public int getAmbientDimension() {
        return this.degree;
    }

}
