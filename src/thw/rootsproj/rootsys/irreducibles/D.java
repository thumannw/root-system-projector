package thw.rootsproj.rootsys.irreducibles;

import java.util.List;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.NoverK;

public class D extends AbstractRootSys {

	private int degree;

	public D(int degree) {
		this.degree = degree;

		int dim = this.getAmbientDimension();
		NoverK x = new NoverK(dim, 2);
		List<double[]> pp = x.getSolutions(1, 1, 0);
		this.addArrays(pp);
		List<double[]> pm = x.getSolutions(1, -1, 0);
		this.addArrays(pm);
		List<double[]> mp = x.getSolutions(-1, 1, 0);
		this.addArrays(mp);
		List<double[]> mm = x.getSolutions(-1, -1, 0);
		this.addArrays(mm);
	}

	@Override
	public int getAmbientDimension() {
		return this.degree;
	}

}
