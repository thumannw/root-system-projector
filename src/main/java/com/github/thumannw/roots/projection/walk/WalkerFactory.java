package com.github.thumannw.roots.projection.walk;

import com.github.thumannw.roots.projection.RootSystemProjector;

import java.lang.reflect.InvocationTargetException;

public enum WalkerFactory {

    ZIGZAG(ZigZagWalk.class),
    TOROIDAL(TorusWalk.class);

    private final Class<? extends AbstractWalker> implClass;

    WalkerFactory(Class<? extends AbstractWalker> implClass) {
        this.implClass = implClass;
    }

    public AbstractWalker create(RootSystemProjector projector) {
        try {
            return this.implClass.getConstructor(RootSystemProjector.class).newInstance(projector);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new AssertionError(e);
        }
    }

    public boolean isSameInstanceAs(AbstractWalker walker) {
        if (walker == null) {
            return false;
        }
        return this.implClass.isAssignableFrom(walker.getClass());
    }

}
