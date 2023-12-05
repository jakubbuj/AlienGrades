package org.openjfx;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;

public class CourseOrder{
    private String[] courses_names = {"JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"};
    private double[][] student_grades = Methods.File_To_Array("src\\main\\resources\\CurrentGrades.csv");
    
    private Stage window;
    private Scene mainScene;
    
    public CourseOrder(Stage window, Scene mainScene){
        this.mainScene = mainScene;
        this.window = window;
    }

    public Scene getScene(){
        //right bar with settings
        VBox rightPane = new VBox();
        rightPane.setPrefWidth(200);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        Button toMain = new Button("Return");
        toMain.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        rightPane.getChildren().addAll(toMain);

    //Bargraph

    //setting
        BorderPane layout = new BorderPane();
        layout.setRight(rightPane);
        layout.setCenter(GraphCourseOrder());

        return new Scene(layout);
    }
    

    private static BarChart<String,Number> GraphCourseOrder(){
        XYChart.Series students = new XYChart.Series();
        students.setName("xyz");
        int[] studentPerCourse = Methods.Students_Per_Course(student_grades);
        int[] orderedCoursID = Methods.Index_Sort(studentPerCourse);
        //find max num of students and make yaxis 
        for (int i =0; i<orderedCoursID.length; i++) {
            int index = orderedCoursID[i];
            students.getData().add(new XYChart.Data(courses_names[index], studentPerCourse[index]));
        }
        CategoryAxis xAxis = new CategoryAxis();
        int max = studentPerCourse[orderedCoursID[orderedCoursID.length-1]];
        int stepSize = (max/1000)*10;
        NumberAxis yAxis = new NumberAxis(0.0,max, stepSize);//sets the y axis and its range
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Order of courses");
        xAxis.setLabel("Course");
        yAxis.setLabel("Number of students");
        bc.getData().add(students);
        return bc;
    }
}
