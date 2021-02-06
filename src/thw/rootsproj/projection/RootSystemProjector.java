package thw.rootsproj.projection;

import java.util.List;
import java.util.stream.Stream;

import thw.rootsproj.Constants;
import thw.rootsproj.rootsys.RootSystem;
import thw.rootsproj.utils.Point;
import thw.rootsproj.utils.Vector;

public class RootSystemProjector {

	private RootSystem rootSystem;
	private PlanesProjector planesProjector;

	public RootSystemProjector(RootSystem.Type type) {
		this.rootSystem = type.create();
		int dim = this.rootSystem.getAmbientDimension();
		this.planesProjector = new PlanesProjector(dim);
	}

	public Point[] projectRootSystem(double... planeParams) {
		int dim = this.rootSystem.getAmbientDimension();
		int n1 = dim - 2;
		int n2 = dim - 1;
		if (planeParams.length != (n1 + n2)) {
			throw new AssertionError();
		}

		double[] firstAngles = toAngles(planeParams, 0, n1);
		double[] secondAngles = toAngles(planeParams, n1, n1 + n2);
		return this.project(firstAngles, secondAngles);
	}

	private Point[] project(double[] firstAngles, double[] secondAngles) {
		PlanesProjector.Angles a1 = new PlanesProjector.Angles(firstAngles);
		PlanesProjector.Angles a2 = new PlanesProjector.Angles(secondAngles);
		this.planesProjector.setPlaneAngles(a1, a2);

		List<Vector> roots = this.rootSystem.getRoots();
		Stream<Vector> stream = roots.stream();
		Point[] res = stream.map(r -> this.planesProjector.projectToPlane(r)).toArray(Point[]::new);
		return res;
	}

	private static double[] toAngles(double[] input, int startIndex, int endIndex) {
		double[] result = new double[endIndex - startIndex];
		for (int i = 0; i < result.length; ++i) {
			double x = input[i + startIndex];
			double fullAngle = 2 * Math.PI;
			result[i] = x * fullAngle;
		}
		return result;
	}

	public static double[] fromDiscrete(int[] discreteParams) {
		double[] result = new double[discreteParams.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = (double) discreteParams[i] / (double) Constants.ANGLE_RESOLUTION;
		}
		return result;
	}

	public static int[] toDiscrete(double[] params) {
		int[] result = new int[params.length];
		for (int i = 0; i < result.length; ++i) {
			long integ = Math.round(params[i] * Constants.ANGLE_RESOLUTION);
			long inRange = integ % Constants.ANGLE_RESOLUTION;
			result[i] = (int) inRange;
		}
		return result;
	}

	public int numberOfPlaneParams() {
		int dim = this.rootSystem.getAmbientDimension();
		return 2 * dim - 3;
	}

}
