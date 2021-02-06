package thw.rootsproj.rootsys.irreducibles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.Matrix;
import thw.rootsproj.utils.Vector;

public class G2 extends AbstractRootSys {

	public G2() {
		List<Vector> diagBase = orthogonalBase();
		diagBase.forEach(v -> v.normed()); // ONB
		Matrix trafo = Matrix.fromRows(diagBase);
		List<Vector> result = createDiagRoots().stream().map(v -> trafo.multiply(v)).collect(Collectors.toList());
		this.addVectors(result);
	}

	private static List<Vector> orthogonalBase() {
		List<Vector> result = new ArrayList<>();
		result.add(new Vector(1, -1, 0));
		result.add(new Vector(0.5, 0.5, -1));
		return result;
	}

	private static List<Vector> createDiagRoots() {
		List<Vector> result = new ArrayList<>();
		result.add(new Vector(1, -1, 0));
		result.add(new Vector(-1, 1, 0));
		result.add(new Vector(1, 0, -1));
		result.add(new Vector(-1, 0, 1));
		result.add(new Vector(0, 1, -1));
		result.add(new Vector(0, -1, 1));
		result.add(new Vector(2, -1, -1));
		result.add(new Vector(-2, 1, 1));
		result.add(new Vector(1, -2, 1));
		result.add(new Vector(-1, 2, -1));
		result.add(new Vector(1, 1, -2));
		result.add(new Vector(-1, -1, 2));
		return result;
	}

	@Override
	public int getAmbientDimension() {
		return 2;
	}

}
