package com.github.thumannw.roots.rootsys.irreducibles;

import com.github.thumannw.roots.rootsys.AbstractRootSys;
import com.github.thumannw.roots.utils.NoverK;

import java.util.List;

public class D extends AbstractRootSys {

    private final int degree;

    public D(int degree) {
        this.degree = degree;

        int dim = this.getAmbientDimension();
        NoverK x = new NoverK(dim, 2);
        List<double[]> pp = x.getSolutions(1, 1, 0);
        this.addArrays(pp);
        List<double[]> pm = x.getSolutions(1, -1, 0);
        this.addArrays(pm);
        List<double[]> mp = x.getSolutions(-1, 1, 0);
        this.addArrays(mp);
        List<double[]> mm = x.getSolutions(-1, -1, 0);
        this.addArrays(mm);
    }

    @Override
    public int getAmbientDimension() {
        return this.degree;
    }

}
