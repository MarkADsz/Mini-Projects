package com.example.gui_first;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader ProgramListLoader = new FXMLLoader(mainGUI.class.getResource("ProgramChooser.fxml"));
//


        FXMLLoader ProgramExecLoader = new FXMLLoader(mainGUI.class.getResource("ProgramExec.fxml"));
        ProgramExecLoader.setController(new ProgramExecCtrl());
        //ProgramExecLoader.setLocation(mainGUI.class.getResource("ProgramExec.fxml"));
        Parent ProgramExecutorRoot = ProgramExecLoader.load();
        ProgramExecCtrl programExecController=ProgramExecLoader.getController();
        //System.out.println(programExecController);
        Scene ProgramExecScene = new Scene(ProgramExecutorRoot, 600, 400);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("ProgramExec");
        secondaryStage.setScene(ProgramExecScene);
        secondaryStage.show();

        FXMLLoader ProgramListLoader = new FXMLLoader(mainGUI.class.getResource("ProgramChooser.fxml"));
        //ProgramListLoader.setController(new ProgramChooserCtrl());
        //ProgramListLoader.setLocation(mainGUI.class.getResource("ProgramChooser.fxml"));
        Parent ProgramListRoot = ProgramListLoader.load();
        Scene ProgramChooserScene = new Scene(ProgramListRoot, 800, 400);
        //ProgramChooserController programChooserController = programListLoader.getController();
        ProgramChooserCtrl programChooserController = ProgramListLoader.getController();
        programChooserController.setProgramExecutorController(programExecController);
        stage.setTitle("ProgramChooser");
        stage.setScene(ProgramChooserScene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}