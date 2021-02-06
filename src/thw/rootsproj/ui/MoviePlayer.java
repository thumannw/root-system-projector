package thw.rootsproj.ui;

import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import thw.rootsproj.projection.walk.AbstractWalker;
import thw.rootsproj.projection.walk.WalkerFactory;
import thw.rootsproj.utils.Point;

public class MoviePlayer {

	private class UIFeeder extends AnimationTimer {

		private AtomicReference<Point[]> nextFrame = new AtomicReference<>(null);

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

	private SceneBuilder parent;
	private AbstractWalker walker;
	private UIFeeder uiFeeder;

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
		this.walker.setPrintFrame(x -> this.printFrame(x));
		this.walker.setCheckPointReached(x -> this.checkPointReached(x));
		this.walker.setOnStop(x -> this.onStop());
	}

	private void checkPointReached(int[] planeParams) {
		Platform.runLater(() -> this.parent.setPlaneParams(planeParams));
	}

	private void printFrame(Point[] framePoints) {
		this.uiFeeder.pushFrame(framePoints);
	}

	private void onStop() {
		Platform.runLater(() -> this.parent.controlsOnMovieStopped());
		Platform.runLater(() -> this.uiFeeder.stop());
	}

}
