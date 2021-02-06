package thw.rootsproj.rootsys.irreducibles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thw.rootsproj.rootsys.AbstractRootSys;
import thw.rootsproj.utils.Matrix;
import thw.rootsproj.utils.Vector;

public class E6 extends AbstractRootSys {

	public E6() {
		E8 e8 = new E8();
		List<Vector> fromE8 = e8.getRoots();
		Stream<Vector> reduced = fromE8.stream().filter(this::firstThreeCoordsEqual);
		Matrix trafo = getTrafo();
		List<Vector> result = reduced.map(v -> trafo.multiply(v)).collect(Collectors.toList());
		this.addVectors(result);
	}

	private boolean firstThreeCoordsEqual(Vector v) {
		double[] coordinates = v.getCoordinates();
		if (coordinates[0] == coordinates[1] && coordinates[1] == coordinates[2]) {
			return true;
		} else {
			return false;
		}
	}

	private static Matrix getTrafo() {
		List<Vector> rows = new ArrayList<>();
		double x = 1d / Math.sqrt(3);
		rows.add(new Vector(x, x, x, 0, 0, 0, 0, 0));
		rows.add(new Vector(0, 0, 0, 1, 0, 0, 0, 0));
		rows.add(new Vector(0, 0, 0, 0, 1, 0, 0, 0));
		rows.add(new Vector(0, 0, 0, 0, 0, 1, 0, 0));
		rows.add(new Vector(0, 0, 0, 0, 0, 0, 1, 0));
		rows.add(new Vector(0, 0, 0, 0, 0, 0, 0, 1));
		Matrix result = Matrix.fromRows(rows);
		return result;
	}

	@Override
	public int getAmbientDimension() {
		return 6;
	}

}
