package thw.rootsproj.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoverK {

	private static enum Slot {
		EMPTY, OCCUPIED;
	}

	private int N;
	private int K;
	private List<Slot[]> solutions;

	public NoverK(int N, int K) {
		if (N < K) {
			throw new AssertionError();
		}
		this.N = N;
		this.K = K;
		this.solutions = new ArrayList<>();
		this.fillSolutions();
	}

	private void fillSolutions() {
		Slot[] start = new Slot[this.N];
		Arrays.fill(start, 0, this.K, Slot.OCCUPIED);
		Arrays.fill(start, this.K, this.N, Slot.EMPTY);
		this.solutions.add(start);

		Slot[] next = start;
		while ((next = next(next)) != null) {
			this.solutions.add(next);
		}
	}

	private static Slot[] next(Slot[] x) {
		int incrementalIndex = findIncrementalIndex(x);
		if (incrementalIndex < 0) {
			return null;
		}

		Slot[] result = Arrays.copyOf(x, x.length);
		result[incrementalIndex] = Slot.EMPTY;
		result[incrementalIndex + 1] = Slot.OCCUPIED;
		int next = incrementalIndex + 2;
		if (next < result.length) {
			int numTailOccupied = numTailOccupied(x);
			int nextNext = next + numTailOccupied;
			Arrays.fill(result, next, nextNext, Slot.OCCUPIED);
			Arrays.fill(result, nextNext, result.length, Slot.EMPTY);
		}

		return result;
	}

	private static int findIncrementalIndex(Slot[] x) {
		boolean emptyBefore = false;
		for (int i = x.length - 1; i >= 0; --i) {
			Slot current = x[i];
			if (current == Slot.OCCUPIED) {
				if (emptyBefore) {
					return i;
				}
			} else {
				emptyBefore = true;
			}
		}
		return -1;
	}

	private static int numTailOccupied(Slot[] x) {
		int result = 0;
		for (int i = x.length - 1; i >= 0; --i) {
			Slot current = x[i];
			if (current == Slot.OCCUPIED) {
				++result;
			} else {
				break;
			}
		}
		return result;
	}

	public List<double[]> getSolutions(double occupiedValue, double emptyValue) {
		List<double[]> result = new ArrayList<>();
		for (Slot[] sol : this.solutions) {
			double[] transformed = new double[sol.length];
			for (int i = 0; i < sol.length; ++i) {
				if (sol[i] == Slot.OCCUPIED) {
					transformed[i] = occupiedValue;
				} else {
					transformed[i] = emptyValue;
				}
			}
			result.add(transformed);
		}
		return result;
	}

	public List<double[]> getSolutions(double firstOccupiedValue, double secondOccupiedValue, double emptyValue) {
		if (this.K != 2) {
			throw new AssertionError();
		}

		List<double[]> result = new ArrayList<>();
		for (Slot[] sol : this.solutions) {
			double[] transformed = new double[sol.length];
			boolean isFirst = true;
			for (int i = 0; i < sol.length; ++i) {
				if (sol[i] == Slot.OCCUPIED) {
					if (isFirst) {
						transformed[i] = firstOccupiedValue;
						isFirst = false;
					} else {
						transformed[i] = secondOccupiedValue;
					}
				} else {
					transformed[i] = emptyValue;
				}
			}
			result.add(transformed);
		}
		return result;
	}

}
