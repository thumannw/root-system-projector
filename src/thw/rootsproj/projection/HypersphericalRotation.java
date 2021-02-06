package thw.rootsproj.projection;

import static thw.rootsproj.utils.ArrayHelper.cut;
import static thw.rootsproj.utils.ArrayHelper.embed;

import thw.rootsproj.utils.Matrix;

// http://math.stackexchange.com/questions/1364495/haar-measure-on-son#1365121

public class HypersphericalRotation extends Matrix {

	private HyperVectFactory hyperVectFactory;

	public HypersphericalRotation(double... angles) {
		int n = angles.length;
		this.numCols = n + 1;
		this.numRows = n + 1;

		this.hyperVectFactory = new HyperVectFactory();

		this.coords = new double[n + 1][];
		this.coords[n] = this.hyperVectFactory.create(angles).getCoordinates();
		this.coords[n - 1] = U(angles);
		for (int i = n - 2; i >= 0; --i) {
			int k = n - 1 - i;
			double[] cut = cut(angles, k);
			double[] U = U(cut);
			this.coords[i] = embed(U, k);
		}

		this.hyperVectFactory = null; // let GC clean
	}

	private double[] U(double[] angles) {
		double[] cut = cut(angles);
		double[] hyper = this.hyperVectFactory.create(cut).getCoordinates();
		double[] result = embed(hyper);
		int n = angles.length;
		double theta_n = angles[n - 1];
		double sin = Math.sin(theta_n);
		double cos = Math.cos(theta_n);
		result[n] = -sin;
		for (int i = 0; i < n; ++i) {
			result[i] = cos * result[i];
		}
		return result;
	}

}
