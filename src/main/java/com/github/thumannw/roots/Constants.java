package com.github.thumannw.roots;

import com.github.thumannw.roots.rootsys.RootSystem;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String VERSION = "v1.4";

    public static final RootSystem.Type STARTUP_ROOTS = RootSystem.Type.F4;

    // walker settings
    public static final int WALK_FRAMES = 400;
    public static final int SLEEP_BETWEEN_FRAMES = 16;
    public static final double WALK_SPEED = 1d;
    public static final int WALKER_WAIT_MILLIS = 500;

    // angle step size
    public static final int ANGLE_RESOLUTION = 1000;
    public static final int SPINNER_CHUNK = 100;

    // display settings
    public static final int CANVAS_SIZE = 800;
    public static final int DEFAULT_POINT_RADIUS = 3;
    public static final int LINE_WIDTH = 1;
    public static final Color POINT_COLOR = Color.BLUE;
    public static final Color LINE_COLOR = Color.GREY;

    // zoom factors
    public static final Map<RootSystem.Type, Integer> PIXELS_PER_UNIT = new HashMap<>();

    static {
        PIXELS_PER_UNIT.put(RootSystem.Type.E8, 270);
        PIXELS_PER_UNIT.put(RootSystem.Type.E7, 270);
        PIXELS_PER_UNIT.put(RootSystem.Type.E6, 270);
        PIXELS_PER_UNIT.put(RootSystem.Type.F4, 270);
        PIXELS_PER_UNIT.put(RootSystem.Type.G2, 150);

        PIXELS_PER_UNIT.put(RootSystem.Type.A2, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A3, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A4, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A5, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A6, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A7, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.A8, 260);

        PIXELS_PER_UNIT.put(RootSystem.Type.B2, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B3, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B4, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B5, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B6, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B7, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.B8, 260);

        PIXELS_PER_UNIT.put(RootSystem.Type.C2, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C3, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C4, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C5, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C6, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C7, 190);
        PIXELS_PER_UNIT.put(RootSystem.Type.C8, 190);

        PIXELS_PER_UNIT.put(RootSystem.Type.D2, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D3, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D4, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D5, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D6, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D7, 260);
        PIXELS_PER_UNIT.put(RootSystem.Type.D8, 260);
    }

}
