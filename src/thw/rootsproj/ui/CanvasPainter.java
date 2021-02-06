package thw.rootsproj.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import thw.rootsproj.Constants;
import thw.rootsproj.utils.Point;

public class CanvasPainter {

	private GraphicsContext gc;

	private boolean drawPoints = true;
	private boolean drawLines = true;

	private double translate;
	private double scale;
	private double pointRadius;
	private double lineWidth;
	private double topLeft;
	private double hSize;
	private double wSize;

	public CanvasPainter(Canvas canvas, double scale) {
		this.gc = canvas.getGraphicsContext2D();

		this.scale = scale;
		this.translate = Constants.CANVAS_SIZE / 2d;
		this.gc.translate(this.translate, this.translate);
		this.gc.scale(this.scale, this.scale);
		this.pointRadius = Constants.DEFAULT_POINT_RADIUS / this.scale;
		this.lineWidth = Constants.LINE_WIDTH / this.scale;
		this.gc.setLineWidth(this.lineWidth);
		this.gc.setStroke(Constants.LINE_COLOR);
		this.topLeft = -this.translate / this.scale;
		this.hSize = canvas.getHeight() / this.scale;
		this.wSize = canvas.getWidth() / this.scale;

		this.clearCanvas();
	}

	public void paint(Point[] frame) {
		this.clearCanvas();

		if (this.drawLines) {
			for (Point p : frame) {
				this.gc.strokeLine(0d, 0d, p.getX(), p.getY());
			}
		}

		if (this.drawPoints) {
			this.gc.setFill(Constants.POINT_COLOR);
			double pr = this.pointRadius;
			double pr2 = 2 * pr;
			for (Point p : frame) {
				this.gc.fillOval(p.getX() - pr, p.getY() - pr, pr2, pr2);
			}
		}
	}

	public void clearCanvas() {
		this.gc.setFill(Color.WHITE);
		this.gc.fillRect(this.topLeft, this.topLeft, this.wSize, this.hSize);
	}

	public void setDrawPoints(boolean drawPoints) {
		this.drawPoints = drawPoints;
	}

	public void setDrawLines(boolean drawLines) {
		this.drawLines = drawLines;
	}

	public void setPointRadius(int value) {
		this.pointRadius = value / this.scale;
	}

}
