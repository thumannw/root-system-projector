package thw.rootsproj.projection.walk;

import java.util.Arrays;
import java.util.Random;

import thw.rootsproj.Constants;
import thw.rootsproj.projection.RootSystemProjector;
import thw.rootsproj.utils.ArrayHelper;
import thw.rootsproj.utils.Vector;

public class TorusWalk extends AbstractWalker {

	private int numPlaneParams;
	private Random randomGen;

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
		double[] result = new Vector(initialParams).add(this.velocity).getCoordinates();
		return result;
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
