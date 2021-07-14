package com.github.thumannw.roots.rootsys;

import com.github.thumannw.roots.rootsys.irreducibles.A;
import com.github.thumannw.roots.rootsys.irreducibles.B;
import com.github.thumannw.roots.rootsys.irreducibles.C;
import com.github.thumannw.roots.rootsys.irreducibles.D;
import com.github.thumannw.roots.utils.Vector;

import java.util.List;
import java.util.function.Supplier;

// https://en.wikipedia.org/wiki/Root_system
// https://en.wikipedia.org/wiki/E8_lattice
// https://en.wikipedia.org/wiki/F4_(mathematics)#F4_lattice
// https://en.wikipedia.org/wiki/G2_(mathematics)

public interface RootSystem {

    enum Type {
        E8(com.github.thumannw.roots.rootsys.irreducibles.E8::new),
        E7(com.github.thumannw.roots.rootsys.irreducibles.E7::new),
        E6(com.github.thumannw.roots.rootsys.irreducibles.E6::new),
        F4(com.github.thumannw.roots.rootsys.irreducibles.F4::new),
        G2(com.github.thumannw.roots.rootsys.irreducibles.G2::new),

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

        private final Supplier<RootSystem> supplier;

        Type(Supplier<RootSystem> supp) {
            this.supplier = supp;
        }

        public RootSystem create() {
            return this.supplier.get();
        }
    }

    List<Vector> getRoots();

    int getAmbientDimension();

}
