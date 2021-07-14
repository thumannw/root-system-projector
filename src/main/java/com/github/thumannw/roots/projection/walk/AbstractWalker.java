package com.github.thumannw.roots.projection.walk;

import com.github.thumannw.roots.Constants;
import com.github.thumannw.roots.projection.RootSystemProjector;
import com.github.thumannw.roots.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractWalker {

    private static class FramePackage {
        private List<Point[]> frames;
    }

    private final RootSystemProjector projector;

    private Consumer<Point[]> printFrame;
    private Consumer<int[]> checkPointReached;
    private Consumer<Void> onStop;

    private Thread workerThread;
    private ExecutorService calculatorService;
    private final AtomicBoolean stopFlag;

    public AbstractWalker(RootSystemProjector projector) {
        this.projector = projector;
        this.workerThread = null;
        this.stopFlag = new AtomicBoolean(false);
    }

    public void start() {
        this.stopFlag.set(false);

        this.calculatorService = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        this.workerThread = new Thread(this::run);
        this.workerThread.setDaemon(true);
        this.workerThread.start();
    }

    public void stop() {
        this.stopFlag.set(true);
        this.workerThread = null;
        this.calculatorService.shutdown();
    }

    public void setPrintFrame(Consumer<Point[]> function) {
        this.printFrame = function;
    }

    public void setCheckPointReached(Consumer<int[]> function) {
        this.checkPointReached = function;
    }

    public void setOnStop(Consumer<Void> function) {
        this.onStop = function;
    }

    private void printFrame(Point[] framePoints) {
        if (this.printFrame != null) {
            this.printFrame.accept(framePoints);
        }
    }

    private void checkPointReached(int[] planeParams) {
        if (this.checkPointReached != null) {
            this.checkPointReached.accept(planeParams);
        }
    }

    private void onStop() {
        if (this.onStop != null) {
            this.onStop.accept(null);
        }
    }

    private void run() {
        int numParams = this.projector.numberOfPlaneParams();
        double[] initialParams = new double[numParams];
        Arrays.fill(initialParams, 0d);
        Point[] initialPoints = this.projector.projectRootSystem(initialParams);
        this.printFrame(initialPoints);
        this.checkPointReached(RootSystemProjector.toDiscrete(initialParams));
        double[] previousParams = initialParams;
        double[] nextParams = this.nextParams(previousParams);
        CompletableFuture<FramePackage> framePackageAsync = this.nextFramePackageAsync(previousParams, nextParams);

        while (!this.stopFlag.get()) {
            FramePackage framePackage;
            try {
                framePackage = framePackageAsync.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            previousParams = this.resetParams(nextParams);
            nextParams = this.nextParams(previousParams);
            framePackageAsync = this.nextFramePackageAsync(previousParams, nextParams);
            this.playFrames(framePackage.frames);
            this.checkPointReached(RootSystemProjector.toDiscrete(previousParams));

            // pause if set
            if (this.getWaitMillis() > 0) {
                if (this.stopFlag.get()) {
                    break;
                }
                try {
                    //noinspection BusyWait
                    Thread.sleep(this.getWaitMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.onStop();
    }

    private CompletableFuture<FramePackage> nextFramePackageAsync(double[] previousParams, double[] nextParams) {
        return CompletableFuture.supplyAsync(() -> this.nextFramePackage(previousParams, nextParams), this.calculatorService);
    }

    private FramePackage nextFramePackage(double[] previousParams, double[] nextParams) {
        List<double[]> interpolated = interpolateParams(previousParams, nextParams);
        List<Point[]> frames = interpolated.stream().map(this.projector::projectRootSystem).collect(Collectors.toList());
        FramePackage result = new FramePackage();
        result.frames = frames;
        return result;
    }

    private void playFrames(List<Point[]> frames) {
        for (Point[] frame : frames) {
            this.printFrame(frame);
            try {
                Thread.sleep(Constants.SLEEP_BETWEEN_FRAMES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<double[]> interpolateParams(double[] previousParams, double[] nextParams) {
        List<double[]> result = new ArrayList<>();
        for (int i = 1; i <= Constants.WALK_FRAMES; ++i) {
            double[] interParams = new double[previousParams.length];
            for (int k = 0; k < interParams.length; ++k) {
                double init = previousParams[k];
                double next = nextParams[k];
                double diff = next - init;
                double step = diff / Constants.WALK_FRAMES;
                double inter = init + (i * step);
                interParams[k] = inter;
            }
            result.add(interParams);
        }
        return result;
    }

    protected double[] resetParams(double[] params) {
        return params;
    }

    protected int getWaitMillis() {
        return 0;
    }

    protected abstract double[] nextParams(double[] previousParams);

}
