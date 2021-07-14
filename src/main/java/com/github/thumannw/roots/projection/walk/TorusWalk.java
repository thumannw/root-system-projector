package com.github.thumannw.roots.projection.walk;

import com.github.thumannw.roots.Constants;
import com.github.thumannw.roots.projection.RootSystemProjector;
import com.github.thumannw.roots.utils.ArrayHelper;
import com.github.thumannw.roots.utils.Vector;

import java.util.Arrays;
import java.util.Random;

public class TorusWalk extends AbstractWalker {

    private final int numPlaneParams;
    private final Random randomGen;

    private Vector velocity;

    public TorusWalk(RootSystemProjector projector) {
        super(projector);
        this.numPlaneParams = projector.numberOfPlaneParams();
        this.randomGen = new Random();
    }

    private void initRandomDirection() {
        double[] x = new double[this.numPlaneParams];
        for (int i = 0; i < x.length; ++i) {
            x[i] = this.randomGen.nextDouble();
        }
        this.velocity = new Vector(x);
        this.velocity.normed();
        this.velocity.multiplyWith(Constants.WALK_SPEED);
    }

    @Override
    public void start() {
        this.initRandomDirection();
        super.start();
    }

    @Override
    protected double[] nextParams(double[] initialParams) {
        return new Vector(initialParams).add(this.velocity).getCoordinates();
    }

    @Override
    protected double[] resetParams(double[] params) {
        double max = ArrayHelper.max(params);
        if (max > 1000d) {
            double[] res = new double[params.length];
            Arrays.fill(res, 0d);
            return res;
        } else {
            return params;
        }
    }

}
