package thw.rootsproj.rootsys.irreducibles;

import java.util.List;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.NoverK;
import thw.rootsproj.utils.Vector;

public class E8 extends AbstractRootSys {

	public E8() {
		this.allHalves();
		this.allInteger();
	}

	private void allHalves() {
		this.addVector(new Vector(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5));
		this.addVector(new Vector(-0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5));

		this.fourPositiveFourNegative();
		this.twoOfOneSixOfTheOther();
	}

	private void fourPositiveFourNegative() {
		NoverK x = new NoverK(8, 4);
		List<double[]> toAdd = x.getSolutions(0.5, -0.5);
		this.addArrays(toAdd);
	}

	private void twoOfOneSixOfTheOther() {
		NoverK x = new NoverK(8, 2);
		List<double[]> twoPos = x.getSolutions(0.5, -0.5);
		this.addArrays(twoPos);
		List<double[]> twoNeg = x.getSolutions(-0.5, 0.5);
		this.addArrays(twoNeg);
	}

	private void allInteger() {
		NoverK x = new NoverK(8, 2);
		List<double[]> posPos = x.getSolutions(1, 1, 0);
		this.addArrays(posPos);
		List<double[]> negNeg = x.getSolutions(-1, -1, 0);
		this.addArrays(negNeg);
		List<double[]> posNeg = x.getSolutions(1, -1, 0);
		this.addArrays(posNeg);
		List<double[]> negPos = x.getSolutions(-1, 1, 0);
		this.addArrays(negPos);
	}

	@Override
	public int getAmbientDimension() {
		return 8;
	}

}
