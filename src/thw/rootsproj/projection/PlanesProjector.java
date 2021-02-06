package thw.rootsproj.projection;

import thw.rootsproj.utils.Matrix;
import thw.rootsproj.utils.Point;
import thw.rootsproj.utils.Vector;

public class PlanesProjector {

	public static class Angles {
		private double[] arr;
		public Angles(double... angles) {
			this.arr = angles;
		}
	}

	private int ambientDimension;

	private Vector firstPlaneVector;
	private Vector secondPlaneVector;

	public PlanesProjector(int ambientDimension) {
		this.ambientDimension = ambientDimension;
	}

	public void setPlaneAngles(Angles firstAngles, Angles secondAngles) {
		if (firstAngles.arr.length != this.ambientDimension - 2) {
			throw new AssertionError();
		}
		if (secondAngles.arr.length != this.ambientDimension - 1) {
			throw new AssertionError();
		}

		Vector x = new HypersphericalVector(firstAngles.arr);
		x = x.extendOneDimension();
		Matrix rot = new HypersphericalRotation(secondAngles.arr);
		x = rot.multiply(x);
		Vector y = rot.getLastColumn();

		this.firstPlaneVector = x;
		this.secondPlaneVector = y;
	}

	public Point projectToPlane(Vector vector) {
		double x = this.firstPlaneVector.scalarProduct(vector);
		double y = this.secondPlaneVector.scalarProduct(vector);
		Point result = new Point(x, y);
		return result;
	}

}
