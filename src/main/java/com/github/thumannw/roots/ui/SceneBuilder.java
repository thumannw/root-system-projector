package com.github.thumannw.roots.ui;

import com.github.thumannw.roots.ApplicationMain;
import com.github.thumannw.roots.Constants;
import com.github.thumannw.roots.projection.RootSystemProjector;
import com.github.thumannw.roots.projection.walk.WalkerFactory;
import com.github.thumannw.roots.rootsys.RootSystem;
import com.github.thumannw.roots.utils.Point;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SceneBuilder {

    private final RootSystemProjector projector;
    private final CanvasPainter painter;
    private final MoviePlayer player;

    private Scene mainScene;
    private Canvas canvas;
    private final List<Spinner<Integer>> spinners;
    private Button start;
    private Button stop;
    private Button resetButton;
    private ComboBox<RootSystem.Type> rootsCombo;
    private RadioButton radioZigZag;
    private RadioButton radioStraight;

    private boolean movieRunning = false;

    public SceneBuilder(RootSystem.Type rootSystemType) {
        this.projector = new RootSystemProjector(rootSystemType);
        this.spinners = new ArrayList<>();
        this.buildScene();
        double scale = Constants.PIXELS_PER_UNIT.get(rootSystemType);
        this.painter = new CanvasPainter(this.canvas, scale);
        this.player = new MoviePlayer(this);
        this.triggerCanvasPaint();
    }

    private static final int P = 8;
    private static final int V = 5;
    private static final int C = 100;

    private void buildScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(P, P, P, P));
        this.mainScene = new Scene(grid);

        this.canvas = new Canvas(Constants.CANVAS_SIZE, Constants.CANVAS_SIZE);
        StackPane container = new StackPane(this.canvas);
        BorderStroke stroke = new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, null);
        container.setBorder(new Border(stroke));
        grid.add(container, 0, 0);

        VBox controlBox = new VBox(V);
        controlBox.setPadding(new Insets(0, 0, 0, P));
        controlBox.setMaxWidth(C);
        controlBox.setFillWidth(true);
        grid.add(controlBox, 1, 0);
        this.buildComboBox(controlBox);
        addSeparator(controlBox);
        this.buildSpinners(controlBox);
        this.buildResetButton(controlBox);
        addSeparator(controlBox);
        this.buildButtons(controlBox);
        this.buildRadios(controlBox);
        addSeparator(controlBox);
        this.buildPointSizeControls(controlBox);
        this.buildChecks(controlBox);

        this.start.requestFocus();
    }

    private void buildPointSizeControls(VBox controlBox) {
        Label lbl = new Label("POINT SIZE:");
        controlBox.getChildren().add(lbl);

        Spinner<Integer> spinner = new Spinner<>(1, 10, Constants.DEFAULT_POINT_RADIUS);
        spinner.setMaxWidth(66);
        controlBox.getChildren().add(spinner);
        spinner.valueProperty().addListener((obs, oldV, newV) -> {
            this.painter.setPointRadius(newV);
            if (!this.movieRunning) {
                this.triggerCanvasPaint();
            }
        });
    }

    private static void addSeparator(VBox controlBox) {
        Separator sep = new Separator();
        controlBox.getChildren().add(sep);
    }

    private void buildResetButton(VBox controlBox) {
        this.resetButton = new Button();
        this.resetButton.setText("RESET");
        this.resetButton.setOnAction(event -> this.spinners.forEach(s -> s.getValueFactory().setValue(0)));
        this.resetButton.setMaxWidth(Double.MAX_VALUE);
        controlBox.getChildren().add(this.resetButton);
    }

    private void buildComboBox(VBox controlBox) {
        this.rootsCombo = new ComboBox<>();
        RootSystem.Type[] values = RootSystem.Type.values();
        this.rootsCombo.setItems(FXCollections.observableArrayList(values));
        this.rootsCombo.setMaxWidth(Double.MAX_VALUE);
        this.rootsCombo.setVisibleRowCount(25);
        controlBox.getChildren().add(this.rootsCombo);

        this.rootsCombo.setValue(ApplicationMain.getCurrentRootSystem());
        this.rootsCombo.valueProperty().addListener((obs, oldV, newV) -> {
            if (oldV.equals(newV)) {
                return;
            }
            ApplicationMain.setupScene(newV);
        });
    }

    private void buildSpinners(VBox controlBox) {
        String newL = System.getProperty("line.separator");
        String sb = "Keyboard ARROW-UP: Increment by 1" +
                newL +
                "Keyboard ARROW-DOWN: Decrement by 1" +
                newL +
                "Keyboard ARROW-RIGHT: Increment by " +
                Constants.SPINNER_CHUNK +
                newL +
                "Keyboard ARROW-LEFT: Decrement by " +
                Constants.SPINNER_CHUNK;
        Tooltip tt = new Tooltip(sb);

        int numParams = this.projector.numberOfPlaneParams();
        for (int i = 0; i < numParams; ++i) {
            Spinner<Integer> spinner = new Spinner<>(0, Constants.ANGLE_RESOLUTION, 0);
            spinner.setTooltip(tt);
            controlBox.getChildren().add(spinner);
            this.spinners.add(spinner);

            this.addSpinnerListeners(spinner);
        }
    }

    private void addSpinnerListeners(Spinner<Integer> spinner) {
        spinner.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.RIGHT && event.getCode() != KeyCode.LEFT) {
                return;
            }
            int spinnerVal = spinner.getValue();
            int newVal;
            if (event.getCode() == KeyCode.RIGHT) {
                newVal = spinnerVal + Constants.SPINNER_CHUNK;
            } else {
                newVal = spinnerVal - Constants.SPINNER_CHUNK;
            }
            spinner.getValueFactory().setValue(newVal);
        });
        spinner.valueProperty().addListener((obs, oldV, newV) -> {
            if (!this.movieRunning) {
                this.triggerCanvasPaint();
            }
        });
    }

    private void triggerCanvasPaint() {
        int[] planeParams = this.spinners.stream().mapToInt(Spinner::getValue).toArray();
        Point[] frame = this.projector.projectRootSystem(RootSystemProjector.fromDiscrete(planeParams));
        this.painter.paint(frame);
    }

    private void buildButtons(VBox controlBox) {
        this.start = new Button();
        this.start.setText("START");
        this.start.setOnAction(event -> {
            this.controlsOnMovieStart();
            this.player.start();
        });
        this.start.setMaxWidth(Double.MAX_VALUE);
        controlBox.getChildren().add(this.start);

        this.stop = new Button();
        this.stop.setText("STOP");
        this.stop.setOnAction(event -> {
            this.controlsOnMovieStopping();
            this.player.stop();
        });
        this.stop.setMaxWidth(Double.MAX_VALUE);
        this.stop.setDisable(true);
        controlBox.getChildren().add(this.stop);
    }

    private void buildRadios(VBox controlBox) {
        final ToggleGroup group = new ToggleGroup();

        this.radioZigZag = new RadioButton("ZIG-ZAG");
        this.radioZigZag.setToggleGroup(group);
        this.radioZigZag.setSelected(true);

        this.radioStraight = new RadioButton("STRAIGHT");
        this.radioStraight.setToggleGroup(group);

        controlBox.getChildren().add(this.radioZigZag);
        controlBox.getChildren().add(this.radioStraight);
    }

    private void buildChecks(VBox controlBox) {
        CheckBox cbPoints = new CheckBox("POINTS");
        cbPoints.setSelected(true);
        cbPoints.selectedProperty().addListener((obs, oldV, newV) -> {
            this.painter.setDrawPoints(newV);
            if (!this.movieRunning) {
                this.triggerCanvasPaint();
            }
        });
        controlBox.getChildren().add(cbPoints);

        CheckBox cbLines = new CheckBox("LINES");
        cbLines.setSelected(true);
        cbLines.selectedProperty().addListener((obs, oldV, newV) -> {
            this.painter.setDrawLines(newV);
            if (!this.movieRunning) {
                this.triggerCanvasPaint();
            }
        });
        controlBox.getChildren().add(cbLines);
    }

    private void controlsOnMovieStart() {
        this.start.setDisable(true);
        this.stop.setDisable(false);
        this.spinners.forEach(s -> s.setDisable(true));
        this.rootsCombo.setDisable(true);
        this.radioZigZag.setDisable(true);
        this.radioStraight.setDisable(true);
        this.resetButton.setDisable(true);

        this.movieRunning = true;
    }

    private void controlsOnMovieStopping() {
        this.stop.setDisable(true);
    }

    public void controlsOnMovieStopped() {
        this.start.setDisable(false);
        this.stop.setDisable(true);
        this.spinners.forEach(s -> s.setDisable(false));
        this.rootsCombo.setDisable(false);
        this.radioZigZag.setDisable(false);
        this.radioStraight.setDisable(false);
        this.resetButton.setDisable(false);

        this.movieRunning = false;
    }

    public void setPlaneParams(int[] planeParams) {
        for (int i = 0; i < planeParams.length; ++i) {
            int param = planeParams[i];
            Spinner<Integer> spinner = this.spinners.get(i);
            spinner.getEditor().textProperty().set(Integer.toString(param));
            spinner.getValueFactory().setValue(param);
        }
    }

    public void paintFrame(Point[] frame) {
        this.painter.paint(frame);
    }

    public Scene getScene() {
        return this.mainScene;
    }

    public WalkerFactory getWalkerType() {
        if (this.radioZigZag.isSelected()) {
            return WalkerFactory.ZIGZAG;
        } else if (this.radioStraight.isSelected()) {
            return WalkerFactory.TOROIDAL;
        } else {
            throw new AssertionError();
        }
    }

    public RootSystemProjector getProjector() {
        return this.projector;
    }

}
