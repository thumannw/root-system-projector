package thw.rootsproj.rootsys.irreducibles;

import java.util.List;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.NoverK;
import thw.rootsproj.utils.Vector;

public class F4 extends AbstractRootSys {

	public F4() {
		twoOnes();
		oneOne();
		allHalves();
	}

	private void twoOnes() {
		NoverK x = new NoverK(4, 2);
		List<double[]> posPos = x.getSolutions(1, 1, 0);
		this.addArrays(posPos);
		List<double[]> negNeg = x.getSolutions(-1, -1, 0);
		this.addArrays(negNeg);
		List<double[]> posNeg = x.getSolutions(1, -1, 0);
		this.addArrays(posNeg);
		List<double[]> negPos = x.getSolutions(-1, 1, 0);
		this.addArrays(negPos);
	}

	private void oneOne() {
		NoverK x = new NoverK(4, 1);
		List<double[]> pos = x.getSolutions(1, 0);
		this.addArrays(pos);
		List<double[]> neg = x.getSolutions(-1, 0);
		this.addArrays(neg);
	}

	private void allHalves() {
		this.addVector(new Vector(0.5, 0.5, 0.5, 0.5));
		this.addVector(new Vector(-0.5, -0.5, -0.5, -0.5));
		NoverK x = new NoverK(4, 1);
		List<double[]> oneNeg = x.getSolutions(-0.5, 0.5);
		this.addArrays(oneNeg);
		x = new NoverK(4, 2);
		List<double[]> twoNeg = x.getSolutions(-0.5, 0.5);
		this.addArrays(twoNeg);
		x = new NoverK(4, 3);
		List<double[]> threeNeg = x.getSolutions(-0.5, 0.5);
		this.addArrays(threeNeg);
	}

	@Override
	public int getAmbientDimension() {
		return 4;
	}

}
