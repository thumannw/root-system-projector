package com.github.thumannw.roots.rootsys.irreducibles;

import com.github.thumannw.roots.rootsys.AbstractRootSys;
import com.github.thumannw.roots.utils.Matrix;
import com.github.thumannw.roots.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class E7 extends AbstractRootSys {

    public E7() {
        E8 e8 = new E8();
        List<Vector> fromE8 = e8.getRoots();
        Stream<Vector> reduced = fromE8.stream().filter(this::firstTwoCoordsEqual);
        Matrix trafo = getTrafo();
        List<Vector> result = reduced.map(trafo::multiply).collect(Collectors.toList());
        this.addVectors(result);
    }

    private boolean firstTwoCoordsEqual(Vector v) {
        double[] coordinates = v.getCoordinates();
        return coordinates[0] == coordinates[1];
    }

    private static Matrix getTrafo() {
        List<Vector> rows = new ArrayList<>();
        double x = 1d / Math.sqrt(2);
        rows.add(new Vector(x, x, 0, 0, 0, 0, 0, 0));
        rows.add(new Vector(0, 0, 1, 0, 0, 0, 0, 0));
        rows.add(new Vector(0, 0, 0, 1, 0, 0, 0, 0));
        rows.add(new Vector(0, 0, 0, 0, 1, 0, 0, 0));
        rows.add(new Vector(0, 0, 0, 0, 0, 1, 0, 0));
        rows.add(new Vector(0, 0, 0, 0, 0, 0, 1, 0));
        rows.add(new Vector(0, 0, 0, 0, 0, 0, 0, 1));
        return Matrix.fromRows(rows);
    }

    @Override
    public int getAmbientDimension() {
        return 7;
    }

}
