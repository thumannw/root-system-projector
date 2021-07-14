package com.github.thumannw.roots.ui;

import com.github.thumannw.roots.projection.walk.AbstractWalker;
import com.github.thumannw.roots.projection.walk.WalkerFactory;
import com.github.thumannw.roots.utils.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicReference;

public class MoviePlayer {

    private class UIFeeder extends AnimationTimer {

        private final AtomicReference<Point[]> nextFrame = new AtomicReference<>(null);

        @Override
        public void handle(long now) {
            Point[] frameToDraw = this.nextFrame.getAndSet(null);
            if (frameToDraw != null) {
                MoviePlayer.this.parent.paintFrame(frameToDraw);
            }
        }

        public void pushFrame(Point[] frame) {
            this.nextFrame.set(frame);
        }

    }

    private final SceneBuilder parent;
    private AbstractWalker walker;
    private final UIFeeder uiFeeder;

    public MoviePlayer(SceneBuilder parent) {
        this.parent = parent;
        this.uiFeeder = new UIFeeder();
    }

    public void start() {
        this.uiFeeder.start();
        this.initWalker(this.parent.getWalkerType());
        this.walker.start();
    }

    public void stop() {
        this.walker.stop();
    }

    private void initWalker(WalkerFactory type) {
        if (type.isSameInstanceAs(this.walker)) {
            return;
        }

        this.walker = type.create(this.parent.getProjector());
        this.walker.setPrintFrame(this::printFrame);
        this.walker.setCheckPointReached(this::checkPointReached);
        this.walker.setOnStop(x -> this.onStop());
    }

    private void checkPointReached(int[] planeParams) {
        Platform.runLater(() -> this.parent.setPlaneParams(planeParams));
    }

    private void printFrame(Point[] framePoints) {
        this.uiFeeder.pushFrame(framePoints);
    }

    private void onStop() {
        Platform.runLater(this.parent::controlsOnMovieStopped);
        Platform.runLater(this.uiFeeder::stop);
    }

}
