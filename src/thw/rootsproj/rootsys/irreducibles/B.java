package thw.rootsproj.rootsys.irreducibles;

import java.util.List;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.NoverK;
import thw.rootsproj.utils.Vector;

public class B extends AbstractRootSys {

	private int degree;

	public B(int degree) {
		this.degree = degree;

		D d = new D(degree);
		List<Vector> fromD = d.getRoots();
		this.addVectors(fromD);
		int dim = this.getAmbientDimension();
		NoverK x = new NoverK(dim, 1);
		List<double[]> plus = x.getSolutions(1, 0);
		this.addArrays(plus);
		List<double[]> minus = x.getSolutions(-1, 0);
		this.addArrays(minus);
	}

	@Override
	public int getAmbientDimension() {
		return this.degree;
	}

}
