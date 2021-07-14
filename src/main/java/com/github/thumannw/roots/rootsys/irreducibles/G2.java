package com.github.thumannw.roots.rootsys.irreducibles;

import com.github.thumannw.roots.rootsys.AbstractRootSys;
import com.github.thumannw.roots.utils.Matrix;
import com.github.thumannw.roots.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class G2 extends AbstractRootSys {

    public G2() {
        List<Vector> diagBase = orthogonalBase();
        diagBase.forEach(Vector::normed); // ONB
        Matrix trafo = Matrix.fromRows(diagBase);
        List<Vector> result = createDiagRoots().stream().map(trafo::multiply).collect(Collectors.toList());
        this.addVectors(result);
    }

    private static List<Vector> orthogonalBase() {
        List<Vector> result = new ArrayList<>();
        result.add(new Vector(1, -1, 0));
        result.add(new Vector(0.5, 0.5, -1));
        return result;
    }

    private static List<Vector> createDiagRoots() {
        List<Vector> result = new ArrayList<>();
        result.add(new Vector(1, -1, 0));
        result.add(new Vector(-1, 1, 0));
        result.add(new Vector(1, 0, -1));
        result.add(new Vector(-1, 0, 1));
        result.add(new Vector(0, 1, -1));
        result.add(new Vector(0, -1, 1));
        result.add(new Vector(2, -1, -1));
        result.add(new Vector(-2, 1, 1));
        result.add(new Vector(1, -2, 1));
        result.add(new Vector(-1, 2, -1));
        result.add(new Vector(1, 1, -2));
        result.add(new Vector(-1, -1, 2));
        return result;
    }

    @Override
    public int getAmbientDimension() {
        return 2;
    }

}
