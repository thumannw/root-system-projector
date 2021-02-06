package thw.rootsproj;

import javafx.application.Application;
import javafx.stage.Stage;
import thw.rootsproj.rootsys.RootSystem;
import thw.rootsproj.ui.SceneBuilder;

public class ApplicationMain extends Application {

	private static Stage applicationStage;
	private static RootSystem.Type currentRootSystem;

	@Override
	public void start(Stage primaryStage) {
		applicationStage = primaryStage;
		applicationStage.setTitle("Root System Projector " + Constants.VERSION);
		applicationStage.setResizable(false);

		setupScene(Constants.STARTUP_ROOTS);

		applicationStage.show();
	}

	public static void setupScene(RootSystem.Type type) {
		currentRootSystem = type;

		SceneBuilder sceneBuilder = new SceneBuilder(type);
		applicationStage.setScene(sceneBuilder.getScene());
		applicationStage.sizeToScene();

		System.gc();
	}

	public static RootSystem.Type getCurrentRootSystem() {
		return currentRootSystem;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
