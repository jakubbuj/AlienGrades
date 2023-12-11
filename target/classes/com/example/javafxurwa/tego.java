package com.example.javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.Arrays;

public class HelloApplication extends Application {
    public static double [] [] GradutesArray = Methods.File_To_Array("src\\main\\java\\GraduateGrades.csv");
    public static int[] CLarray = Methods.Cum_Laude_Graduates(GradutesArray);
    @Override
    public void start(Stage stage) {
        //Non cum laude
        int NonCL = GradutesArray.length - CLarray.length;
        //Percentage calculator
         double PercentageCL = Math.round(((float)CLarray.length * 100 / (float)GradutesArray.length)/.01);
         PercentageCL = PercentageCL/100;
         double PercentageGR = 100.0 - PercentageCL;


        //Preparing ObservableList object
        ObservableList<PieChart.Data> pieChartDataPercentage = FXCollections.observableArrayList(
                new PieChart.Data("Cum laude "+ PercentageCL +"%" , CLarray.length),
                new PieChart.Data("Not Cum laude " +PercentageGR + "%", NonCL));
        ObservableList<PieChart.Data> pieChartDataNumber = FXCollections.observableArrayList(
                new PieChart.Data("Cum laude "+ CLarray.length , CLarray.length),
                new PieChart.Data("Not Cum laude " +NonCL, NonCL));



        //Creating a Pie chart
        PieChart pieChartNode = new PieChart(pieChartDataPercentage);
        PieChart pieChartNodeNumbers = new PieChart((pieChartDataNumber));

        //Setting the title of the Pie chart
        pieChartNode.setTitle("Cum laude students");
        pieChartNodeNumbers.setTitle("Cum laude students");

        //setting the direction to arrange the data
        pieChartNode.setClockwise(true);
        pieChartNodeNumbers.setClockwise(true);

        //Setting the length of the label line
        pieChartNode.setLabelLineLength(30);
        pieChartNodeNumbers.setLabelLineLength(30);

        //Setting the labels of the pie chart visible
        pieChartNode.setLabelsVisible(true);
        pieChartNodeNumbers.setLabelsVisible(true);

        //Setting the start angle of the pie chart
        pieChartNode.setStartAngle(256);
        pieChartNodeNumbers.setStartAngle(256);

        //Creating a Group object
        Group pieChartGroup = new Group(pieChartNode);

        //Button
        Button button = new Button("Percentage");
        Button buttonPercentage =new Button("Numbers");
        HBox pieChartGraph = new HBox(button, buttonPercentage);
        button.setOnAction(e-> {
            pieChartGraph.getChildren().remove(pieChartNodeNumbers);
            HBox.setHgrow(pieChartNode, Priority.ALWAYS);
        pieChartGraph.getChildren().add(pieChartNode);});
        buttonPercentage.setOnAction(e-> {
            pieChartGraph.getChildren().remove(pieChartNode);
            HBox.setHgrow(pieChartNodeNumbers, Priority.ALWAYS);
            pieChartGraph.getChildren().add(pieChartNodeNumbers);});
        //Centralising it


        //Creating a scene object
        Scene pieChartScene = new Scene(pieChartGraph, 1280, 800);

        //Setting title to the Stage
        stage.setTitle("Pie chart");
        stage.setResizable(false);

        //Displaying the contents of the stage
        stage.show();



------------------------------------------------------------------------------------------------------------------------



        //Bar chart axis
        CategoryAxis xAxisBarChart = new CategoryAxis(FXCollections.<String>observableArrayList(Arrays.asList("Students")));
            xAxisBarChart.setLabel("category");

        NumberAxis yAxisBarChart = new NumberAxis(0 ,GradutesArray.length ,500);
            yAxisBarChart.setLabel("Number");


        //Creating the Bar chart
        BarChart<String, Number> barChartNode = new BarChart<>(xAxisBarChart, yAxisBarChart);
            barChartNode.setTitle("Cum laude students");
        BarChart<String, Number> barChartNodePercentage = new BarChart<>(xAxisBarChart, yAxisBarChart);
            barChartNodePercentage.setTitle("Cum laude students");

        //Adding data to BarChart
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName("Cum laude");
            series1.getData().add(new XYChart.Data<>("Students", CLarray.length));
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName("Not Cum laude");
            series2.getData().add(new XYChart.Data<>("Students", NonCL));
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            series3.setName("Cum laude");
            series3.getData().add(new XYChart.Data<>("Students", PercentageCL));
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
            series4.setName("Not Cum laude");
            series4.getData().add(new XYChart.Data<>("Students",PercentageGR));

        //Setting the data to bar chart
        barChartNode.getData().addAll(series1, series2);
        barChartNodePercentage.getData().addAll(series3,series4);

        //Creating group for bar chart
        Group barChartGroup = new Group(barChartNode);

        //Creating an Hbox for BarChart
        HBox barChartGraph = new HBox(button, buttonPercentage);
        button.setOnAction(e-> {
            barChartGraph.getChildren().remove(barChartNodePercentage);
            HBox.setHgrow(barChartNode, Priority.ALWAYS);
            barChartGraph.getChildren().add(barChartNode);
                });
        buttonPercentage.setOnAction(e-> {
                    barChartGraph.getChildren().remove(barChartNode);
                    HBox.setHgrow(barChartNodePercentage, Priority.ALWAYS);
                    barChartGraph.getChildren().add(barChartNodePercentage);
                });

        //Setting scene for BarChart
        Scene barChartScene = new Scene(barChartGraph ,1280, 800);

        //Set the scene
        stage.setScene(barChartScene);

    }
    public static void main(String args[]){
        launch(args);

    }
    public static int add(int tem){
        return tem++;
    }
}