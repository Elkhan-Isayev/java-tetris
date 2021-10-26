package com.encom.simpletetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Tetris extends Application {

//    Variables
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int [][] MESH = new int[XMAX/SIZE][YMAX/SIZE];
    public static int score = 0;


    private static Pane group = new Pane();
    private static Form object;
    private static Scene scene = new Scene(group, XMAX + 150, YMAX);
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect();
    private static int LinesNo = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
