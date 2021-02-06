package thw.rootsproj.projection;

import static thw.rootsproj.utils.ArrayHelper.cut;
import static thw.rootsproj.utils.ArrayHelper.embed;

import thw.rootsproj.utils.Vector;

// http://math.stackexchange.com/questions/1364495/haar-measure-on-son#1365121

public class HypersphericalVector extends Vector {

	public HypersphericalVector(double... angles) {
		this.coords = coordinatesFromAngles(angles);
	}

	protected double[] coordinatesFromAngles(double[] angles) {
		int n = angles.length;
		double[] result;
		if (n == 0) {
			result = new double[1];
			result[0] = 1d;
		} else if (n == 1) {
			double theta_1 = angles[0];
			result = new double[2];
			result[0] = Math.sin(theta_1);
			result[1] = Math.cos(theta_1);
		} else {
			result = embed(coordinatesFromAngles(cut(angles)));
			double theta_n = angles[n - 1];
			double sin = Math.sin(theta_n);
			double cos = Math.cos(theta_n);
			result[n] = cos;
			for (int i = 0; i < n; ++i) {
				result[i] = sin * result[i];
			}
		}
		return result;
	}

}
