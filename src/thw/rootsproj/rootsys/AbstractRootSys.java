package thw.rootsproj.rootsys;

import java.util.ArrayList;
import java.util.List;

import thw.rootsproj.utils.Vector;

public abstract class AbstractRootSys implements RootSystem {

	private List<Vector> roots = new ArrayList<>();

	protected void addArrays(List<double[]> arrays) {
		arrays.stream().map(a -> new Vector(a)).forEach(a -> this.roots.add(a));
	}

	protected void addArray(double[] array) {
		this.roots.add(new Vector(array));
	}

	protected void addVectors(List<Vector> vs) {
		this.roots.addAll(vs);
	}

	protected void addVector(Vector v) {
		this.roots.add(v);
	}

	@Override
	public List<Vector> getRoots() {
		return this.roots;
	}

}
