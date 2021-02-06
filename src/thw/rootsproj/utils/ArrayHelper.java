package thw.rootsproj.utils;

import java.util.Arrays;

public class ArrayHelper {

	public static double[] cut(double[] x) {
		return cut(x, 1);
	}

	public static double[] cut(double[] x, int minusDimensions) {
		double[] result = new double[x.length - minusDimensions];
		for (int i = 0; i < result.length; ++i) {
			result[i] = x[i];
		}
		return result;
	}

	public static double[] embed(double[] x) {
		return embed(x, 1);
	}

	public static double[] embed(double[] x, int plusDimensions) {
		double[] result = new double[x.length + plusDimensions];
		for (int i = 0; i < x.length; ++i) {
			result[i] = x[i];
		}
		for (int i = x.length; i < result.length; ++i) {
			result[i] = 0d;
		}
		return result;
	}

	public static double max(double[] x) {
		if (x.length == 0) {
			throw new AssertionError();
		}
		return Arrays.stream(x).max().getAsDouble();
	}

}
