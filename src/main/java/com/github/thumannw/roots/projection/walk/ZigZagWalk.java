package com.github.thumannw.roots.projection.walk;

import com.github.thumannw.roots.Constants;
import com.github.thumannw.roots.projection.RootSystemProjector;
import com.github.thumannw.roots.utils.Vector;

import java.util.Random;

public class ZigZagWalk extends AbstractWalker {

    private final Random randomGen;

    public ZigZagWalk(RootSystemProjector projector) {
        super(projector);
        this.randomGen = new Random();
    }

    @Override
    protected double[] nextParams(double[] previousParams) {
        Vector previousV = new Vector(previousParams);
        int l = previousParams.length;
        Vector randomV;
        while (true) {
            // random vector should be different from the previous one
            if (!(randomV = this.randomVect(l)).equals(previousV)) break;
        }
        Vector diff = randomV.subtract(previousV);
        diff.normed();
        diff.multiplyWith(Constants.WALK_SPEED);
        Vector result = previousV.add(diff);
        return result.getCoordinates();
    }

    private Vector randomVect(int length) {
        double[] result = new double[length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.randomGen.nextDouble();
        }
        return new Vector(result);
    }

    @Override
    protected int getWaitMillis() {
        return Constants.WALKER_WAIT_MILLIS;
    }

    @Override
    protected double[] resetParams(double[] params) {
        double[] result = new double[params.length];
        for (int i = 0; i < result.length; ++i) {
            double val = params[i];
            double floored = Math.floor(val);
            double newVal = val - floored;
            result[i] = newVal;
        }
        return result;
    }

}
