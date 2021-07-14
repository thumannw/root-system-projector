package com.github.thumannw.roots.rootsys;

import com.github.thumannw.roots.utils.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRootSys implements RootSystem {

    private final List<Vector> roots = new ArrayList<>();

    protected void addArrays(List<double[]> arrays) {
        arrays.stream().map(Vector::new).forEach(this.roots::add);
    }

    protected void addVectors(List<Vector> vs) {
        this.roots.addAll(vs);
    }

    protected void addVector(Vector v) {
        this.roots.add(v);
    }

    @Override
    public List<Vector> getRoots() {
        return this.roots;
    }

}
