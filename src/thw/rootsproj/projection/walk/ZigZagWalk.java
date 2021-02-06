package thw.rootsproj.projection.walk;

import java.util.Random;

import thw.rootsproj.Constants;
import thw.rootsproj.projection.RootSystemProjector;
import thw.rootsproj.utils.Vector;

public class ZigZagWalk extends AbstractWalker {

	private Random randomGen;

	public ZigZagWalk(RootSystemProjector projector) {
		super(projector);
		this.randomGen = new Random();
	}

	@Override
	protected double[] nextParams(double[] previousParams) {
		Vector previousV = new Vector(previousParams);
		int l = previousParams.length;
		Vector randomV = null;
		while ((randomV = this.randomVect(l)).equals(previousV)) {
			// random vector should be different from the previous one
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
