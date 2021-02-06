package thw.rootsproj.rootsys;

import java.util.List;
import java.util.function.Supplier;

import thw.rootsproj.rootsys.irreducibles.A;
import thw.rootsproj.rootsys.irreducibles.B;
import thw.rootsproj.rootsys.irreducibles.C;
import thw.rootsproj.rootsys.irreducibles.D;
import thw.rootsproj.rootsys.irreducibles.E6;
import thw.rootsproj.rootsys.irreducibles.E7;
import thw.rootsproj.rootsys.irreducibles.E8;
import thw.rootsproj.rootsys.irreducibles.F4;
import thw.rootsproj.rootsys.irreducibles.G2;
import thw.rootsproj.utils.Vector;

// https://en.wikipedia.org/wiki/Root_system
// https://en.wikipedia.org/wiki/E8_lattice
// https://en.wikipedia.org/wiki/F4_(mathematics)#F4_lattice
// https://en.wikipedia.org/wiki/G2_(mathematics)

public interface RootSystem {

	public static enum Type {
		E8(() -> new E8()),
		E7(() -> new E7()),
		E6(() -> new E6()),
		F4(() -> new F4()),
		G2(() -> new G2()),

		A2(() -> new A(2)),
		A3(() -> new A(3)),
		A4(() -> new A(4)),
		A5(() -> new A(5)),
		A6(() -> new A(6)),
		A7(() -> new A(7)),
		A8(() -> new A(8)),

		B2(() -> new B(2)),
		B3(() -> new B(3)),
		B4(() -> new B(4)),
		B5(() -> new B(5)),
		B6(() -> new B(6)),
		B7(() -> new B(7)),
		B8(() -> new B(8)),

		C2(() -> new C(2)),
		C3(() -> new C(3)),
		C4(() -> new C(4)),
		C5(() -> new C(5)),
		C6(() -> new C(6)),
		C7(() -> new C(7)),
		C8(() -> new C(8)),

		D2(() -> new D(2)),
		D3(() -> new D(3)),
		D4(() -> new D(4)),
		D5(() -> new D(5)),
		D6(() -> new D(6)),
		D7(() -> new D(7)),
		D8(() -> new D(8));

		private Supplier<RootSystem> supplier;
		private Type(Supplier<RootSystem> supp) {
			this.supplier = supp;
		}
		private Type() {
			this.supplier = null;
		}
		public RootSystem create() {
			return this.supplier.get();
		}
	}

	public List<Vector> getRoots();
	public int getAmbientDimension();

}
