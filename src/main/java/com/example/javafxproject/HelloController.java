package com.example.javafxproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.*;

import javafx.scene.canvas.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Pair;


public class HelloController {
    private static final Insets margin = new Insets(10, 10, 10, 10);
    private static final double[][] graduate_grades = Methods.File_To_Array("src/main/resources/com/example/javafxproject/GraduateGrades.csv");
    private static final double[][] current_grades = Methods.File_To_Array("src/main/resources/com/example/javafxproject/CurrentGrades.csv");
    private static final String[][] student_properties = Methods.File_To_Array_String("src/main/resources/com/example/javafxproject/StudentInfo.csv");
    public static int[] cumLaudeArray = Methods.Cum_Laude_Graduates(graduate_grades);
    private static final String[] courses_names = {"JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"};
    private static AnchorPane initializeScene() {
        AnchorPane root = new AnchorPane();
        Insets bgInsets = new Insets(0);
        BackgroundFill bgFill = new BackgroundFill(Color.WHITE, null, bgInsets);
            root.setBackground(new Background(bgFill));
        InnerShadow innerShadow = new InnerShadow(127, Color.LIGHTGRAY);
            root.setEffect(innerShadow);
        return root;
    }

    public static void switchToMainScene(Stage stage) throws IOException {
        System.out.println("switchToMainScene");    //testing if method calls

        //Creating a root AnchorPane with added visual effects
        AnchorPane root = HelloController.initializeScene();


        //Creating and alignment of the main screen text
        Text selectYourChoice = new Text();
            selectYourChoice.setText("Select Your Choice");
            selectYourChoice.setX(520);
            selectYourChoice.setY(280);
            selectYourChoice.setFont(Font.font("Verdana", 24));

        //Creating and alignment of X-Axis Line
        Line xAxis = new Line();
            xAxis.setStartX(520);
            xAxis.setStartY(285);
            xAxis.setEndX(760);
            xAxis.setEndY(285);
            xAxis.setStrokeWidth(1.5);
            Line xArrow1 = new Line(760, 285, 755, 280);
            Line xArrow2 = new Line(760, 285, 755, 290);


        //Creating and alignment of Y-Axis Line
        Line yAxis = new Line();
            yAxis.setStartX(520);
            yAxis.setStartY(285);
            yAxis.setEndX(520);
            yAxis.setEndY(530);
            yAxis.setStrokeWidth(1.5);
            Line yArrow1 = new Line(520, 530, 515, 525);
            Line yArrow2 = new Line(520, 530, 525, 525);

        Line value1 = new Line(517, 310, 523, 310);
        Line value2 = new Line(517, 340, 523, 340);
        Line value3 = new Line(517, 370, 523, 370);
        Line value4 = new Line(517, 400, 523, 400);
        Line value5 = new Line(517, 430, 523, 430);
        Line value6 = new Line(517, 460, 523, 460);
        Line value7 = new Line(517, 490, 523, 490);

        Group chart = new Group(yArrow1, yArrow2, yAxis, xArrow1, xArrow2, xAxis);
        chart.getChildren().addAll(value1, value2, value3, value4, value5, value6, value7);

        //Creating and adjusting a Vbox where the choice buttons will be placed
        VBox vBox = new VBox();
            vBox.setPrefHeight(245);
            vBox.setPrefWidth(240);
            vBox.setPadding(margin);
            vBox.setSpacing(5);

        //Creating selection menu
        MenuButton cumLaudeMenuButton = new MenuButton("Cum-Laude Graduates");
            MenuItem cumLaudeBarGraph = new MenuItem("Bar Graph");
                cumLaudeBarGraph.setOnAction(event -> {
                    try {
                        switchToCumLaudeBarChart(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                           //Assigning Action to switch scenes after clicking
            MenuItem cumLaudePieChart = new MenuItem("Pie Chart");
                cumLaudePieChart.setOnAction(event -> {
                    try {
                        switchToCumLaudePieChart(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                           //Assigning Action to switch scenes after clicking
        cumLaudeMenuButton.getItems().addAll(cumLaudeBarGraph, cumLaudePieChart);                                       //Adding MenuItems to MenuButton

        MenuButton courseDifficultyMenuButton = new MenuButton("Course Difficulty");
            MenuItem courseDifficultyHistogram = new MenuItem("Histogram");
                courseDifficultyHistogram.setOnAction(event -> {
                    try {
                        switchToCourseDifficultyHistogram(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                  //Assigning Action to switch scenes after clicking
            MenuItem courseDifficultyLinear = new MenuItem("Linear Graph");
                courseDifficultyLinear.setOnAction(event -> {
                    try {
                        switchToCourseDifficultyLinear(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                     //Assigning Action to switch scenes after clicking
        courseDifficultyMenuButton.getItems().addAll(courseDifficultyHistogram, courseDifficultyLinear);                //Adding MenuItems to MenuButton

        MenuButton courseOrderMenuButton = new MenuButton("Course Order");
            MenuItem courseOrderHistogram = new MenuItem("Histogram");
                courseOrderHistogram.setOnAction(event -> {
                    try {
                        switchToCourseOrderHistogram(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                       //Assigning Action to switch scenes after clicking
        courseOrderMenuButton.getItems().addAll(courseOrderHistogram);                               //Adding MenuItems to MenuButton

        MenuButton gradesDistributionMenuButton = new MenuButton("Grades Distribution");
            MenuItem gradesDistributionBoxPlot = new MenuItem("Box Plot");
                gradesDistributionBoxPlot.setOnAction(event -> {
            try {
                switchToGradesDistributionBoxPlot(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        gradesDistributionMenuButton.getItems().addAll(gradesDistributionBoxPlot);

        MenuButton courseAverageMenuButton = new MenuButton("Course Average");
                MenuItem courseAverageHistogram = new MenuItem("Histogram");
                    courseAverageHistogram.setOnAction(event -> {
                        try {
                            switchToCourseAverageHistogram(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });                                                  //Assigning Action to switch scenes after clicking
                MenuItem courseAverageLinear = new MenuItem("Linear Graph");
                    courseAverageLinear.setOnAction(event -> {
                        try {
                            switchToCourseAverageLinear(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });                                                 
            courseAverageMenuButton.getItems().addAll(courseAverageHistogram, courseAverageLinear);                //Adding MenuItems to MenuButton


        MenuButton courseSimilarityMenuButton = new MenuButton("Course Similarity");
            MenuItem courseSimilarityScatterPlot = new MenuItem("Scatter Plot");
                courseSimilarityScatterPlot.setOnAction(event -> {
                    try {
                        switchToCourseSimilarityScatterPlot(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });                                                  //Assigning Action to switch scenes after clicking
        courseSimilarityMenuButton.getItems().addAll(courseSimilarityScatterPlot/*, courseSimilarityLinear*/);                //Adding MenuItems to MenuButton
        
        MenuButton binaryTreeMenuButton = new MenuButton("Binary Tree");
        MenuItem binaryTreeVisualisation = new MenuItem("Visualisation");
            binaryTreeVisualisation.setOnAction(event -> {
                try {
                    switchToBinaryTree(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });                                                  //Assigning Action to switch scenes after clicking
         binaryTreeMenuButton.getItems().addAll(binaryTreeVisualisation/*, courseSimilarityLinear*/);                //Adding MenuItems to MenuButton
    
                
        Button exitButton = new Button("Quit");
            exitButton.setOnAction(actionEvent -> System.exit(0));

        vBox.getChildren().addAll(cumLaudeMenuButton, courseDifficultyMenuButton, courseOrderMenuButton, courseSimilarityMenuButton, gradesDistributionMenuButton, courseAverageMenuButton,binaryTreeMenuButton, exitButton);


        AnchorPane.setTopAnchor(vBox, 285.0);
        AnchorPane.setLeftAnchor(vBox, 520.0);

        root.getChildren().addAll(selectYourChoice, chart, vBox);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Alien-Student Grades");
        stage.show();
    }

    private static void switchToCumLaudeBarChart(Stage stage) throws IOException {
        System.out.println("switchToCumLaudeBarChart");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
            bPane.setMinSize(1280,800);
            bPane.setPadding(margin);

        String[] temp = {"Above", "Below"};
        //Non cum laude
        int NonCL = graduate_grades.length - cumLaudeArray.length;
        //Percentage calculator
        double PercentageCL = Math.round(((float) cumLaudeArray.length * 100 / (float)graduate_grades.length)/.01);
        PercentageCL /= 100;
        double PercentageGR = 100.0 - PercentageCL;

        //Bar chart axis
        CategoryAxis xAxisBarChart = new CategoryAxis();
            xAxisBarChart.setLabel("Cum-Laude");
        NumberAxis yAxisBarChart = new NumberAxis(0 , graduate_grades.length ,500);
            yAxisBarChart.setLabel("Number of students");
        NumberAxis yAxisBarChartP = new NumberAxis(0 , 100 ,10);
            yAxisBarChartP.setLabel("Percentage of students");

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxisBarChart, yAxisBarChart);
            barChart.setTitle("Students that graduated Cum-Laude");

        //Adding data to BarChart
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName(temp[0]);
            series1.getData().add(new XYChart.Data<>(temp[0], cumLaudeArray.length));
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName(temp[1]);
            series2.getData().add(new XYChart.Data<>(temp[1], NonCL));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            series3.setName(temp[0]);
            series3.getData().add(new XYChart.Data<>(temp[0], PercentageCL));
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
            series4.setName(temp[1]);
            series4.getData().add(new XYChart.Data<>(temp[1], PercentageGR));

        //Button
        Button buttonNumbers = new Button("Numbers");
            buttonNumbers.setOnAction(e-> {
                bPane.setCenter(null);
                BarChart<String, Number> barChartN = new BarChart<>(xAxisBarChart, yAxisBarChart);
                barChartN.setTitle("Students that graduated Cum-Laude");
                barChartN.getData().addAll(series1, series2);
                barChartN.setBarGap(-192);
                barChartN.setLegendVisible(false);
                barChartN.setAnimated(false);
                bPane.setCenter(barChartN);
            });
        Button buttonPercentage =new Button("Percentage");
            buttonPercentage.setOnAction(e-> {
                bPane.setCenter(null);
                BarChart<String, Number> barChartP = new BarChart<>(xAxisBarChart, yAxisBarChartP);
                barChartP.setTitle("Students that graduated Cum-Laude");
                barChartP.getData().addAll(series3, series4);
                barChartP.setBarGap(-192);
                barChartP.setLegendVisible(false);
                barChartP.setAnimated(false);
                bPane.setCenter(barChartP);
            });

        Button backButton = new Button("Go back");
            backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        HBox filtersBox = new HBox(buttonNumbers, buttonPercentage);
            HBox.setHgrow(filtersBox, Priority.ALWAYS);
            filtersBox.setAlignment(Pos.BOTTOM_RIGHT);
            filtersBox.setSpacing(10);
        HBox buttonsBox = new HBox(backButton, filtersBox);

        //Setting the data to bar chart
        barChart.getData().addAll(series1, series2);
        barChart.setBarGap(-192);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        bPane.setCenter(barChart);
        bPane.setBottom(buttonsBox);
        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Cum-Laude Bar Chart");
        stage.show();
    }

    private static void switchToCumLaudePieChart(Stage stage) throws IOException {
        System.out.println("switchToCumLaudePieChart");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
        bPane.setMinSize(1280,800);
        bPane.setPadding(margin);

        //Non cum laude
        int NonCL = graduate_grades.length - cumLaudeArray.length;
        //Percentage calculator
        double PercentageCL = Math.round(((float) cumLaudeArray.length * 100 / (float)graduate_grades.length)/.01);
        PercentageCL = PercentageCL/100;
        double PercentageGR = 100.0 - PercentageCL;


        //Preparing ObservableList object
        ObservableList<PieChart.Data> pieChartDataPercentage = FXCollections.observableArrayList(
                new PieChart.Data("Above Cum-Laude "+ PercentageCL +"%" , cumLaudeArray.length),
                new PieChart.Data("Below Cum-Laude " +PercentageGR + "%", NonCL));
        ObservableList<PieChart.Data> pieChartDataNumber = FXCollections.observableArrayList(
                new PieChart.Data("Above Cum-Laude "+ cumLaudeArray.length , cumLaudeArray.length),
                new PieChart.Data("Below Cum-Laude " +NonCL, NonCL));

        //Creating a Pie chart
        PieChart pieChart = new PieChart(pieChartDataPercentage);
        PieChart pieChartNumbers = new PieChart((pieChartDataNumber));

        //Setting the title of the Pie chart
        pieChart.setTitle("Cum-Laude students");
        pieChartNumbers.setTitle("Cum-Laude students");

        //setting the direction to arrange the data
        pieChart.setClockwise(true);
        pieChartNumbers.setClockwise(true);

        //Setting the length of the label line
        pieChart.setLabelLineLength(30);
        pieChartNumbers.setLabelLineLength(30);

        //Setting the labels of the pie chart visible
        pieChart.setLabelsVisible(true);
        pieChartNumbers.setLabelsVisible(true);

        //Setting the start angle of the pie chart
        pieChart.setStartAngle(256);
        pieChartNumbers.setStartAngle(256);

        //Button
        Button buttonNumbers = new Button("Percentage");
        buttonNumbers.setOnAction(e-> {
            bPane.setCenter(null);
            bPane.setCenter(pieChart);});
        Button buttonPercentage =new Button("Numbers");
        buttonPercentage.setOnAction(e-> {
            bPane.setCenter(null);
            bPane.setCenter(pieChartNumbers);});


        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        HBox filtersBox = new HBox(buttonNumbers, buttonPercentage);
            HBox.setHgrow(filtersBox, Priority.ALWAYS);
            filtersBox.setAlignment(Pos.BOTTOM_RIGHT);
            filtersBox.setSpacing(10);
        HBox buttonsBox = new HBox(backButton, filtersBox);

        bPane.setCenter(pieChart);
        bPane.setBottom(buttonsBox);
        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Cum-Laude Pie Chart");
        stage.show();
    }

    private static void switchToCourseDifficultyHistogram(Stage stage) throws IOException {
        System.out.println("switchToCourseDifficultyHistogram");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
            bPane.setMinSize(1280,800);
            bPane.setPadding(margin);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(5, 10, 1);
        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
        int number_of_courses = courses_names.length;
        double[] course_means = new double[number_of_courses];
        String[] course_names_sorted = new String[number_of_courses];


        barChart.setTitle("Course Means implying it's difficulty");
        xAxis.setLabel("Courses");
        yAxis.setLabel("Means");

        //Filling the array course_means with means
        for (int course = 0; course < number_of_courses; course++) {
            double[] mms = Methods.MeanMedianStandardDeviation_Course(graduate_grades, course);
            double mean = mms[0];
            course_means[course] = mean;
        }

        double[][] IDS = Methods.Sort_With_Id(course_means, false);
        double[] mean = new double[number_of_courses];
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        //Sorting the courses and creating the bar graph
        for(int course = 0; course < number_of_courses; course++) {
            course_names_sorted[course] = courses_names[(int)(IDS[1][course])];
            mean[course] = course_means[course];

            series.getData().add(new XYChart.Data<>(course_names_sorted[course], mean[course]));
            series.setName(course_names_sorted[course]);
        }

        barChart.setMaxSize(1152,720);
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
        barChart.setBarGap(0);

        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        bPane.setCenter(barChart);
        bPane.setBottom(backButton);
        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Difficulty Histogram");
        stage.show();
    }

    private static void switchToCourseDifficultyLinear(Stage stage) throws IOException {
        System.out.println("switchToCourseDifficultyLinear");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
        bPane.setMinSize(1280,800);
        bPane.setPadding(margin);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(5, 10, 1);
        LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        int number_of_courses = courses_names.length;
        double[] course_means = new double[number_of_courses];
        String[] course_names_sorted = new String[number_of_courses];
        lineChart.setTitle("Course Means implying it's difficulty");
        xAxis.setLabel("Courses");
        yAxis.setLabel("Means");

        //Filling the array course_means with means
        for (int course = 0; course < number_of_courses; course++) {
            double[] mms = Methods.MeanMedianStandardDeviation_Course(graduate_grades, course);
            double mean = mms[0];
            course_means[course] = mean;
        }

        double[][] IDS = Methods.Sort_With_Id(course_means, false);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        //Sorting the courses and creating the bar graph
        for(int course = 0; course < number_of_courses; course++) {
            course_names_sorted[course] = courses_names[(int)(IDS[1][course])];
            double mean = course_means[course];

            series.getData().add(new XYChart.Data<>(course_names_sorted[course], mean));
            series.setName(course_names_sorted[course]);
        }

        lineChart.setMaxSize(1152, 720);
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);

        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        bPane.setCenter(lineChart);
        bPane.setBottom(backButton);
        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Difficulty Linear Graph");
        stage.show();
    }

    private static void switchToCourseOrderHistogram(Stage stage) throws IOException {
        System.out.println("switchToCourseOrderHistogram");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
        bPane.setMinSize(1280,800);
        bPane.setPadding(margin);

        Button backButton = new Button("Go back");
            backButton.setOnAction(event -> {
                try {
                    switchToMainScene(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        XYChart.Series<String, Number> studentsY1 = new XYChart.Series<>();
            studentsY1.setName("Year 1");
        XYChart.Series<String, Number> studentsY2 = new XYChart.Series<>();
            studentsY2.setName("Year 2");
        XYChart.Series<String, Number> studentsY3 = new XYChart.Series<>();
            studentsY3.setName("Year 3");

        int[] studentsPerCourse = Methods.Students_Per_Course(current_grades);
        int[] orderedCoursesID = Methods.Index_Sort(studentsPerCourse);

        //find max num of students and make yaxis
        for (int i = orderedCoursesID.length - 1; i >= 0; i--) {
            int index = orderedCoursesID[i];
            if (studentsPerCourse[index] < 400 )
                studentsY3.getData().add(new XYChart.Data<>(courses_names[index], studentsPerCourse[index]));
            else if(studentsPerCourse[index] > 400 && studentsPerCourse[index] < 700)
                studentsY2.getData().add(new XYChart.Data<>(courses_names[index], studentsPerCourse[index]));
            else
                studentsY1.getData().add(new XYChart.Data<>(courses_names[index], studentsPerCourse[index]));
        }

        CategoryAxis xAxis = new CategoryAxis();
        int max = studentsPerCourse[orderedCoursesID[orderedCoursesID.length - 1]];
        int stepSize = (max / 1000) * 100;
        NumberAxis yAxis = new NumberAxis(0, max, stepSize);//sets the y-axis and its range
        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Order of courses");
        xAxis.setLabel("Course");
        yAxis.setLabel("Number of students");
        bc.setMaxSize(1152, 720);
        bc.setBarGap(-16);
        bc.setCategoryGap(5);
        bc.getData().addAll(studentsY1, studentsY2, studentsY3);

        bPane.setCenter(bc);
        bPane.setBottom(backButton);
        root.getChildren().add(bPane);


        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Order Histogram");
        stage.show();

    }

    private static void switchToGradesDistributionBoxPlot(Stage stage) throws IOException {
        System.out.println("switchToGradesDistributionBoxPlot");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BoxPlotScene(1280, 800).getScene();
        bPane.setPadding(margin);

        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        bPane.setBottom(backButton);
        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Grades Distribution Box Plot");
        stage.show();
    }

    // Jesse's code
    static class BoxPlot {

        //class that holds all properties need to create a boxplot item
        static class Item{
            public Item(String label, double min,double q1, double q2, double q3, double max){
                this.label = label;
                this.min = min;
                this.q1 = q1;
                this.q2 = q2;
                this.q3 = q3;
                this.max = max;
            }

            private String label;
            private double min;
            private double q1;
            private double q2;
            private double q3;
            private double max;
        }

        //class that translates coordinates onto a canvas of a given size
        class Coordinates {
            public Coordinates(double x, double y){
                this.x=x;
                this.y=y;
            }

            public double getX(){
                return -canvas.getWidth()/(xMax - xMin)*(xMin -x);
            }
            public double getY(){
                return canvas.getHeight() + canvas.getHeight()/(yMax - yMin)*(yMin -y);
            }

            private double x;
            private double y;
        }

        public BoxPlot(Canvas canvas){
            this.canvas = canvas;
            gc = canvas.getGraphicsContext2D();
        }

        public void draw(){
            if(data==null||data.size()==0) {System.out.println("Empty plot"); return;}

            double boxWidth = 0.4;

            //setting up grid of items
            xMin = 0 -2;
            yMin = -2;//getMinValue() -2;
            xMax = data.size() +1;
            yMax = 11;



            //drawing the X-axis
            Line(new Coordinates(0, yMin +2), new Coordinates(xMax -0.5, yMin +2));
            int count=0;
            for (double i = xMin +2; i<= xMax -1 ; i++) {
                Line(new Coordinates(i, yMin +1.9), new Coordinates(i, yMin +2.1));
            }
            Coordinates nameX = new Coordinates((xMin +1+ xMax)/2, -1.5);

            //drawing the Y-axis
            Line(new Coordinates(0, yMin +2), new Coordinates(0, yMax -1));
            for (double i = yMin +2; i<= yMax -1 ; i++) {
                Line(new Coordinates(-0.1,i), new Coordinates(0.1,i));
                drawText(Double.toString(i), new Coordinates(-0.5,i));
            }
            Coordinates nameY = new Coordinates(-1.5, ((yMin + yMax+1)/2));



            gc.save();
            gc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            gc.fillText(xLabel, nameX.getX(), nameX.getY());
            gc.rotate(-90);
            gc.fillText(yLabel, -nameY.getY(), nameY.getX());
            gc.restore();

            //plots all items

            for(int i=0; i<data.size();){
                Item item = data.get(i);
                i++;
                if(data.size()>=12){
                    if(i%2==0){
                        drawText(item.label, new Coordinates(i, yMin +1.5));

                    } else{
                        drawText(item.label, new Coordinates(i, yMin +1));
                    }
                }else{
                    drawText(item.label, new Coordinates(i, yMin +1.5));
                }
                gc.save();
                gc.setFill(Color.CADETBLUE);
                Line(new Coordinates(i-boxWidth/2, item.min), new Coordinates(i+boxWidth/2, item.min));
                Line(new Coordinates(i-boxWidth/2, item.max), new Coordinates(i+boxWidth/2, item.max));
                Line(new Coordinates(i, item.min), new Coordinates(i, item.max));
                Box(new Coordinates(i-boxWidth/2, item.q3), boxWidth, item.q3-item.q1);
                gc.restore();
            }
        }

        //adds an item to the dataset
        public void add(Item item){
            data.add(item);
        }

        //sets the label for the Y-axis
        public void setXLabel(String s){
            xLabel = s;
        }

        //sets the label for the Y-axis
        public void setYLabel(String s){
            yLabel = s;
        }

        //draws a line on the canvas according to the coordinateGrid
        public void Line(Coordinates A, Coordinates B){
            gc.strokeLine(A.getX(), A.getY(), B.getX(), B.getY());
        }

        //draws a rectangle on the canvas according to the coordinateGrid
        public void Box(Coordinates A, double w, double h){
            gc.fillRect(A.getX(),A.getY(), canvas.getWidth()/(xMax - xMin)*w, canvas.getHeight()/(yMax - yMin)*h);
        }

        //draws text on the canvas according to the coordinateGrid
        public void drawText(String s, Coordinates c){
            gc.save();
            gc.setFont(Font.font( "Verdana", 12));
            gc.fillText(s, c.getX()-s.length()*5/2, c.getY() );
            gc.restore();
        }

        //finds the maximum value in the plot
        private double getMaxValue(){
            double max = 0;
            for (Item item : data) {
                if(max<item.max) max=item.max;
            }
            return max;
        }

        //finds the minimum value in the plot
        private double getMinValue(){
            double min = 0;
            for (Item item : data) {
                if(min<item.min) min=item.min;
            }
            return min;
        }

        public void clear(){
            data = new ArrayList<>();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        private Canvas canvas;
        private GraphicsContext gc;

        private double xMin;
        private double yMin;
        private double xMax;
        private double yMax;

        private String xLabel;
        private String yLabel;

        private ArrayList<Item> data = new ArrayList<>(); //holds all items
    }
    // Jesse's code
    static class BoxPlotScene {
        private Canvas canvas;
        private BoxPlot plot;
        private ListView<String> selection;
        private Pair[] coursePairs = getPairs();

        BoxPlotScene(double width, double height) throws IOException {
            canvas = new Canvas(width-220,height-46);
            plot = new BoxPlot(canvas);
        }

        public BorderPane getScene(){
            //right bar with settings
            VBox rightPane = new VBox();
            rightPane.setMinWidth(200);
            rightPane.setMaxWidth(200);
            rightPane.setAlignment(Pos.CENTER);

            ComboBox<String> dropdown_1 = new ComboBox<>();
            for (String course : courses_names) {dropdown_1.getItems().add(course);}

            selection = new ListView<>();
            selection.getItems().addAll(courses_names);
            selection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            plot.setXLabel("courses");
            plot.setYLabel("grades");


            Label label = new Label("Course Selection");
                label.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

            Button confirm = new Button("Confirm");
                confirm.setOnAction(e-> setFilters());

            Label disclaimer = new Label("Control-click: Select multiple courses" +
                    "\nShift-click:Select a range");
                disclaimer.setFont(Font.font("Verdana", 12));
                disclaimer.setWrapText(true);

            rightPane.setSpacing(10);
            rightPane.setPadding(margin);
            rightPane.getChildren().addAll(label, selection, disclaimer, confirm);

            //Bar Graph

            //setting
            BorderPane layout = new BorderPane();
                layout.setPadding(margin);
                layout.setRight(rightPane);
                layout.setCenter(canvas);
                layout.setMaxSize(1280, 800);

            return layout;

        }

        private void setFilters(){
            plot.clear();
            ObservableList<String> courses;
            courses = selection.getSelectionModel().getSelectedItems();


            for (String label : courses) {
                for (Pair course : coursePairs) {
                    if( course.getKey().equals(label)){
                        double[] d = Methods.MeanMedianStandardDeviation_Course(graduate_grades, (int)course.getValue());

                        for (double x : d) {
//                            System.out.print(x +" - ");
                            if(x==-1) continue;
                        }
//                        System.out.println();
                        double q3 = (d[0]+d[2]*0.6745>10)? (q3 = 10 ): d[0]+d[2]*0.6745;
                        double q1 = d[0]-d[2]*0.6745;
                        double[] minmax = Methods.courseMinMax((int)course.getValue(), graduate_grades);
                        plot.add(new BoxPlot.Item((String) (course.getKey()), minmax[0], q1, d[0], q3, minmax[1]));
                    }

                }
            }

            plot.draw();
        }

        private Pair[] getPairs(){
            Pair[] p = new Pair[courses_names.length];
            for(int i=0; i<courses_names.length; i++){
                p[i] = new Pair<String,Integer>(courses_names[i], i);
            }
            return p;
        }
    }

    // Kordian's code (Witch Czarek's influence)
    private static void switchToCourseSimilarityScatterPlot(Stage stage) throws IOException {
        System.out.println("switchToCourseSimilarityHistogram");

        AnchorPane root = initializeScene();
        Group graph = new Group();
        VBox basic_gui = new VBox();
            basic_gui.setSpacing(5);
        BorderPane bPane = new BorderPane();
            bPane.setMinSize(1280,800);
            bPane.setPadding(margin);

        int[] options = {0, 1};

        //Creating filters for the graph
        Label text1 = new Label("Change courses");
            text1.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        Button testButton = new Button("Go back");
            testButton.setOnAction(event -> {
                try {
                    switchToMainScene(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        HBox filterButtons = new HBox();
            filterButtons.setSpacing(5);
            filterButtons.setPadding(margin);
            filterButtons.setAlignment(Pos.CENTER);
        VBox rightTop = new VBox(text1, filterButtons);
            rightTop.setAlignment(Pos.BOTTOM_CENTER);
            rightTop.setPadding(margin);
        VBox rightBottom = displayCourseSimilarityScatterPlot(graph, graduate_grades, courses_names, options[0], options[1]);
            rightBottom.setAlignment(Pos.TOP_LEFT);
            rightBottom.setPadding(margin);
        VBox bPaneRightSide = new VBox();
            bPaneRightSide.setAlignment(Pos.CENTER);
            bPaneRightSide.setMinWidth(300); // 25% of window size
            bPaneRightSide.setMaxWidth(300); // 25% of window size
            bPaneRightSide.setPadding(margin);
            bPaneRightSide.getChildren().addAll(rightTop, rightBottom);

        ComboBox<String> dropdown_1 = new ComboBox<>();
        for (String course : courses_names) {dropdown_1.getItems().add(course);}
            dropdown_1.setOnAction(event -> {
                graph.getChildren().clear();
                bPane.getChildren().remove(graph);
                options[0] = dropdown_1.getSelectionModel().getSelectedIndex();
                rightBottom.getChildren().clear();
                rightBottom.getChildren().add(displayCourseSimilarityScatterPlot(graph, graduate_grades, courses_names, options[0], options[1]));
                bPane.getChildren().add(graph);
            });

        ComboBox<String> dropdown_2 = new ComboBox<>();
        for (String course : courses_names) {dropdown_2.getItems().add(course);}
            dropdown_2.setOnAction(event -> {
                graph.getChildren().clear();
                bPane.getChildren().remove(graph);
                options[1] = dropdown_2.getSelectionModel().getSelectedIndex();
                rightBottom.getChildren().clear();
                rightBottom.getChildren().add(displayCourseSimilarityScatterPlot(graph, graduate_grades, courses_names, options[0], options[1]));
                bPane.getChildren().add(graph);
            });
        filterButtons.getChildren().addAll(dropdown_1, dropdown_2);


        bPane.setBottom(testButton);
        bPane.setCenter(graph);
        bPane.setRight(bPaneRightSide);

        root.getChildren().add(bPane);

        //Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Similarity Scatter Plot");
        stage.show();
    }

    // Kordian's code
    private static VBox displayCourseSimilarityScatterPlot(Group graph, double[][] grades, String[] courses, int course_1, int course_2) {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Frequency");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Grades");


        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName(courses[course_1]);
        XYChart.Series dataSeries2 = new XYChart.Series();
            dataSeries2.setName(courses[course_2]);

        // Creating data series and calculating distances between data points

        int[] Frequency_of_grades = new int[10];
        int[] Frequency_of_grades_compare = new int[10];
        int[] Frequency_distances = new int[10];
        for (int j = 0; j < 2; j++) {
            int course_num;
            if (j == 0) {
                course_num = course_1;
            } else {
                course_num = course_2;
            }
            // calculating frequencies of grades. [0] stands for grade 1, [9]-stands for grade 10
            for (int i = 1; i < grades.length; i++) {
                Frequency_of_grades[(int) grades[i][course_num] - 1] = Frequency_of_grades[(int) grades[i][course_num] - 1] + 1;
            }
            // filling Frequency_distances
            if (course_num == course_1) {
                for (int i = 5; i < 10; i++) {
                    Frequency_of_grades_compare[i] = Frequency_of_grades[i];
                }
            } else {
                for (int i = 5; i < 10; i++) {
                    Frequency_distances[i] = Math.abs(Frequency_of_grades[i] - Frequency_of_grades_compare[i]);
                }
            }
            for (int i = 5; i < 10; i++) {
                if (course_num == course_1) {
                    dataSeries1.getData().add(new XYChart.Data(Frequency_of_grades[i], i + 1));
                } else if (course_num == course_2){
                    dataSeries2.getData().add(new XYChart.Data(Frequency_of_grades[i], i + 1));
                }
            }

            for (int i = 0; i < Frequency_of_grades.length; i++) {
                Frequency_of_grades[i] = 0;
            }
        }

        // creating explanation text showing distances
        int Frequency_distances_mean = 0;
        for (int i = 5; i < 10; i++) {
            Frequency_distances_mean += Frequency_distances[i];
        }
        Frequency_distances_mean /= 5;

        scatterChart.getData().addAll(dataSeries1, dataSeries2);
        scatterChart.setPrefSize(960, 600); // 75% of window size

        Text text = new Text(
                        "Difference in frequency:" +
                        "\nGrade 6.0 = " + Frequency_distances[5] +
                        "\nGrade 7.0 = " + Frequency_distances[6] +
                        "\nGrade 8.0 = " + Frequency_distances[7] +
                        "\nGrade 9.0 = " + Frequency_distances[8] +
                        "\nGrade 10.0 = " + Frequency_distances[9] +
                        "\n\nAverage difference = " + Frequency_distances_mean);
            text.setFont(Font.font("Verdana"));

        Text text2 = Methods.simillarityStamps(graduate_grades);
            text2.setFont(Font.font("Verdana"));
        graph.getChildren().addAll(scatterChart);

        return new VBox(text, text2);
    }

    private static void switchToCourseAverageHistogram(Stage stage) throws IOException {
        System.out.println("switchToCourseAverageHistogram");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
            bPane.setMaxSize(1280,800);
            bPane.setMinSize(1280,800);
            bPane.setPadding(margin);


        //BarGraph
        CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Courses");
        NumberAxis yAxis = new NumberAxis(0.0,10, 0.5);
            yAxis.setLabel("Mean score");
        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
            barChart.setTitle("Course means by property");
            barChart.setMaxSize(1152, 720);
            barChart.setAnimated(false);
            barChart.setBarGap(0);
            barChart.setCategoryGap(5);

        //right bar with settings
        VBox rightPane = new VBox();
            rightPane.setPrefWidth(200);
            rightPane.setAlignment(Pos.BOTTOM_CENTER);
            rightPane.setSpacing(5);
            rightPane.setPadding(margin);

        //Suruna
        Label surunaLabel = new Label("Suruna");
            surunaLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox lobi = new CheckBox("lobi");
        CheckBox nulp = new CheckBox("nulp");
        CheckBox doot = new CheckBox("doot");
        VBox surunaGroup = new VBox(lobi, nulp, doot);
        rightPane.getChildren().addAll(surunaLabel, surunaGroup);

        //Hurni
        Label hurniLabel = new Label("Hurni level");
            hurniLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox nothing = new CheckBox("nothing");
        CheckBox low = new CheckBox("low");
        CheckBox medium = new CheckBox("medium");
        CheckBox high = new CheckBox("high");
        CheckBox full = new CheckBox("full");
        VBox hurniGroup = new VBox(nothing, low, medium, high, full);
        rightPane.getChildren().addAll(hurniLabel, hurniGroup);

        //Volta
        Label voltaLabel = new Label("Volta");
            voltaLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox one = new CheckBox("1 star");
        CheckBox two = new CheckBox("2 stars");
        CheckBox three = new CheckBox("3 stars");
        CheckBox four = new CheckBox("4 stars");
        CheckBox five = new CheckBox("5 stars");
        VBox voltaGroup = new VBox(one, two, three, four, five);
        rightPane.getChildren().addAll(voltaLabel, voltaGroup);

        //Lal count
        Group lalCount = new Group();
        Label lalCountLabel = new Label("Lal count (60-100)");
            lalCountLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        TextField lalRangeA = new TextField(); lalRangeA.setPromptText("min-max");
        TextField lalRangeB = new TextField(); lalRangeB.setPromptText("min-max");
        TextField lalRangeC = new TextField(); lalRangeC.setPromptText("min-max");
        lalCount.getChildren().addAll(lalRangeA, lalRangeB, lalRangeC);
        rightPane.getChildren().addAll(lalCountLabel, lalRangeA, lalRangeB, lalRangeC);

        //courses
        Label coursesLabel = new Label("Course selection");
            coursesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        ListView<String> courseSelection = new ListView<>();
        courseSelection.getItems().addAll(courses_names);
        courseSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightPane.getChildren().addAll(coursesLabel, courseSelection);

        //all checkboxes
        CheckBox[] filterBoxes = {
                lobi, nulp, doot,                       //suruna
                nothing, low, medium, high, full,       //hurni
                one, two, three, four, five             //volta
        };
        TextField[] txtField ={ lalRangeA, lalRangeB, lalRangeC};

        //confirm filters
        Button applyFiltersButton = new Button("Apply Filters");
        HBox applyFilters = new HBox(applyFiltersButton);
            applyFilters.setAlignment(Pos.BOTTOM_RIGHT);
            HBox.setHgrow(applyFilters, Priority.ALWAYS);
        applyFiltersButton.setOnAction(e->{
            barChart.getData().clear();
            ObservableList<String> selectedCourses =  courseSelection.getSelectionModel().getSelectedItems();
            //loop over properties except lal count
            for (int i=0; i<filterBoxes.length; i++ ) {
                if(!filterBoxes[i].isSelected()) continue;
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                String property = filterBoxes[i].getText();
                series.nameProperty().set( property );
                //loop over courses
                for (String courseName : selectedCourses) {
                    int courseId = Arrays.asList(courses_names).indexOf(courseName);
                    double mean = Methods.courseMeanByProperty(courseId, property, current_grades, student_properties);
                    series.getData().add(new XYChart.Data<>(courseName, mean));
                }
                barChart.getData().add(series);
            }

            //LalCount
            for(TextField field : txtField){
                String txt = field.getText();
                if(txt.matches("\\d{2}-\\d{2}") || txt.matches("\\d{2}-\\d{3}")){
                    String[] s = txt.split("-");
                    int min = Integer.parseInt(s[0]);
                    int max = Integer.parseInt(s[1]);
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    String property = "Lal("+txt+")";
                    series.nameProperty().set( property );

                    for (String courseName : selectedCourses) {
                        int courseId = Arrays.asList(courses_names).indexOf(courseName);
                        series.getData().add(new XYChart.Data<>(courseName, CourseMeanByLalCount(courseId, min, max)));
                    }
                    barChart.getData().add(series);
                }
            }
        });


        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        HBox back = new HBox(backButton);
            back.setAlignment(Pos.BOTTOM_LEFT);
            HBox.setHgrow(back, Priority.ALWAYS);

        HBox bottomGroup = new HBox(back, applyFilters);
            bottomGroup.setAlignment(Pos.BOTTOM_CENTER);

        bPane.setRight(rightPane);
        bPane.setCenter(barChart);
        bPane.setBottom(bottomGroup);
        root.getChildren().add(bPane);

        //Showing the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Average Bar Chart");
        stage.show();
    }
    private static void switchToCourseAverageLinear(Stage stage) throws IOException {
        System.out.println("switchToCourseAverageLinear");

        AnchorPane root = initializeScene();
        BorderPane bPane = new BorderPane();
            bPane.setMaxSize(1280,800);
            bPane.setMinSize(1280,800);
            bPane.setPadding(margin);


        //LineChart
        CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Courses");
        NumberAxis yAxis = new NumberAxis(0.0,10, 0.5);
            yAxis.setLabel("Mean score");
        LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
            lineChart.setTitle("Course means by property");
            lineChart.setMaxSize(1152, 720);
            lineChart.setAnimated(false);

        //right bar with settings
        VBox rightPane = new VBox();
            rightPane.setPrefWidth(200);
            rightPane.setAlignment(Pos.BOTTOM_CENTER);
            rightPane.setSpacing(5);
            rightPane.setPadding(margin);

        //Suruna
        Label surunaLabel = new Label("Suruna");
            surunaLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox lobi = new CheckBox("lobi");
        CheckBox nulp = new CheckBox("nulp");
        CheckBox doot = new CheckBox("doot");
        VBox surunaGroup = new VBox(lobi, nulp, doot);
        rightPane.getChildren().addAll(surunaLabel, surunaGroup);

        //Hurni
        Label hurniLabel = new Label("Hurni level");
            hurniLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox nothing = new CheckBox("nothing");
        CheckBox low = new CheckBox("low");
        CheckBox medium = new CheckBox("medium");
        CheckBox high = new CheckBox("high");
        CheckBox full = new CheckBox("full");
        VBox hurniGroup = new VBox(nothing, low, medium, high, full);
        rightPane.getChildren().addAll(hurniLabel, hurniGroup);

        //Volta
        Label voltaLabel = new Label("Volta");
            voltaLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        CheckBox one = new CheckBox("1 star");
        CheckBox two = new CheckBox("2 stars");
        CheckBox three = new CheckBox("3 stars");
        CheckBox four = new CheckBox("4 stars");
        CheckBox five = new CheckBox("5 stars");
        VBox voltaGroup = new VBox(one, two, three, four, five);
        rightPane.getChildren().addAll(voltaLabel, voltaGroup);

        //Lal count
        Group lalCount = new Group();
        Label lalCountLabel = new Label("Lal count (60-100)");
            lalCountLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        TextField lalRangeA = new TextField(); lalRangeA.setPromptText("min-max");
        TextField lalRangeB = new TextField(); lalRangeB.setPromptText("min-max");
        TextField lalRangeC = new TextField(); lalRangeC.setPromptText("min-max");
        lalCount.getChildren().addAll(lalRangeA, lalRangeB, lalRangeC);
        rightPane.getChildren().addAll(lalCountLabel, lalRangeA, lalRangeB, lalRangeC);

        //courses
        Label coursesLabel = new Label("Course selection");
            coursesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        ListView<String> courseSelection = new ListView<>();
        courseSelection.getItems().addAll(courses_names);
        courseSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightPane.getChildren().addAll(coursesLabel, courseSelection);

        //all checkboxes
        CheckBox[] filterBoxes = {
                lobi, nulp, doot,                       //suruna
                nothing, low, medium, high, full,       //hurni
                one, two, three, four, five             //volta
        };
        TextField[] txtField = {lalRangeA, lalRangeB, lalRangeC};

        //confirm filters
        Button applyFiltersButton = new Button("Apply Filters");
        HBox applyFilters = new HBox(applyFiltersButton);
            applyFilters.setAlignment(Pos.BOTTOM_RIGHT);
            HBox.setHgrow(applyFilters, Priority.ALWAYS);
        applyFiltersButton.setOnAction(e->{
            lineChart.getData().clear();
            ObservableList<String> selectedCourses =  courseSelection.getSelectionModel().getSelectedItems();
            //loop over properties except lal count
            for (int i=0; i<filterBoxes.length; i++ ) {
                if(!filterBoxes[i].isSelected()) continue;
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                String property = filterBoxes[i].getText();
                series.nameProperty().set( property );
                //loop over courses
                for (String courseName : selectedCourses) {
                    int courseId = Arrays.asList(courses_names).indexOf(courseName);
                    double mean = Methods.courseMeanByProperty(courseId, property, current_grades, student_properties);
                    series.getData().add(new XYChart.Data<>(courseName, mean));
                }
                lineChart.getData().add(series);
            }

            //LalCount
            for(TextField field : txtField){
                String txt = field.getText();
                if(txt.matches("\\d{2}-\\d{2}") || txt.matches("\\d{2}-\\d{3}")){
                    String[] s = txt.split("-");
                    int min = Integer.parseInt(s[0]);
                    int max = Integer.parseInt(s[1]);
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    String property = "Lal("+txt+")";
                    series.nameProperty().set( property );

                    for (String courseName : selectedCourses) {
                        int courseId = Arrays.asList(courses_names).indexOf(courseName);
                        series.getData().add(new XYChart.Data<>(courseName, CourseMeanByLalCount(courseId, min, max)));
                    }
                    lineChart.getData().add(series);
                }
            }
        });


        Button backButton = new Button("Go back");
        backButton.setOnAction(event -> {
            try {
                switchToMainScene(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        HBox back = new HBox(backButton);
            back.setAlignment(Pos.BOTTOM_LEFT);
            HBox.setHgrow(back, Priority.ALWAYS);

        HBox bottomGroup = new HBox(back, applyFilters);
            bottomGroup.setAlignment(Pos.BOTTOM_CENTER);

        bPane.setRight(rightPane);
        bPane.setCenter(lineChart);
        bPane.setBottom(bottomGroup);
        root.getChildren().add(bPane);

        //Showing the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Course Average Linear Chart");
        stage.show();
    }
    private static double CourseMeanByLalCount(int courseID, int min, int max){
        double sum = 0;
        int numOfStudents = 0;
        for(int ID=0; ID<student_properties.length; ID++){
            if( student_properties[ID] == null) {continue;}

            double grade = current_grades[ID][courseID];
            int lal = Integer.parseInt( student_properties[ID][2] );
            if( lal >= min && lal < max && grade != -1) {
                numOfStudents++;
                sum += grade;
            }
        }
        return (numOfStudents>0)? sum/numOfStudents : 0;
    }

    //Kordian's code
    private static void switchToBinaryTree(Stage stage) throws IOException {
        System.out.println("switchToBinaryTree");

        AnchorPane root = initializeScene();
        
        //options container 
        VBox vbox = new VBox();
        vbox.setPrefWidth(260);  
        vbox.setPrefHeight(800); 

        // Options
        Label OptionsLabel = new Label("Options ");
        OptionsLabel.setStyle("-fx-font-size: 24;");
        // student atributes
        Label StudentOptionsLabel = new Label("Student atributes: ");
        StudentOptionsLabel.setStyle("-fx-font-size: 20;");
        Label SVlabel = new Label("Suruna Value: ");
        Label HLlabel = new Label(" Hurni Level: ");
        Label Vlabel = new Label("Volta: ");
        Label Lclabel = new Label("Lal Count: ");
        // Course
        Label COURSELabel = new Label(" Course: ");
        
        //tree / forset
        Label treeOptionsLabel = new Label("Tree options: ");
        treeOptionsLabel.setStyle("-fx-font-size: 20;");
        Label DEPTHLabel = new Label("tree Depth ");
        Label SPLITSLabel = new Label("Number of splits (keep at 2):");
        Label forsetOptionsLabel = new Label("Forest options ");
        forsetOptionsLabel.setStyle("-fx-font-size: 20;");
        Label FOREST_SIZELabel = new Label("Data training size (Boostrapping): ");
        Label BOOTSTRAP_SIZELabel  = new Label("Size of trainingdata that a tree is trained on:");
        Label FOREST_VARLabel   = new Label("Number of variables a tree randomly selects:");

        // student atributes
        ChoiceBox<String> SurunaValueChoiceBox = new ChoiceBox<>();
        SurunaValueChoiceBox.getItems().addAll("nulp", "doot", "lobi");
        SurunaValueChoiceBox.setValue("nulp");
        ChoiceBox<String> HurniLevelChoiceBox = new ChoiceBox<>();
        HurniLevelChoiceBox.getItems().addAll("full", "high", "medium", "low", "nothing");
        HurniLevelChoiceBox.setValue("full");
        ChoiceBox<String> VoltaChoiceBox = new ChoiceBox<>();
        VoltaChoiceBox.getItems().addAll("1 star", "2 star", "3 star", "4 star", "5 star");
        VoltaChoiceBox.setValue("1 star");
        TextField textFieldLalCount = new TextField();
        textFieldLalCount.setMaxWidth(200);
        textFieldLalCount.setPromptText("22");
        // course
        ChoiceBox<String> COURSEChoiceBox = new ChoiceBox<>();
        COURSEChoiceBox.getItems().addAll("JTE-234","ATE-003","TGL-013","PPL-239","WDM-974","GHL-823","HLU-200","MON-014","FEA-907","LPG-307","TSO-010","LDE-009","JJP-001","MTE-004","LUU-003","LOE-103","PLO-132","BKO-800","SLE-332","BKO-801","DSE-003","DSE-005","ATE-014","JTW-004","ATE-008","DSE-007","ATE-214","JHF-101","KMO-007","WOT-104");
        COURSEChoiceBox.setValue("JTE-234");
        //tree / forest
        ChoiceBox<String> DEPTHChoiceBox = new ChoiceBox<>();
        DEPTHChoiceBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8");
        DEPTHChoiceBox.setValue("3");
        ChoiceBox<String> SPLITSChoiceBox = new ChoiceBox<>();
        SPLITSChoiceBox.getItems().addAll("1", "2", "3","4","5");
        SPLITSChoiceBox.setValue("2");
        TextField textFieldFOREST_SIZE = new TextField();
        textFieldFOREST_SIZE.setMaxWidth(200);
        textFieldFOREST_SIZE.setPromptText("100");
        TextField textFieldBOOTSTRAP_SIZE = new TextField();
        textFieldBOOTSTRAP_SIZE.setMaxWidth(200);
        textFieldBOOTSTRAP_SIZE.setPromptText("25");
        TextField textFieldFOREST_VAR = new TextField();
        textFieldFOREST_VAR.setMaxWidth(200);
        textFieldFOREST_VAR.setPromptText("3");

        Button submitButton = new Button("Submit");

         // visulaisation
         Pane p = new Pane();
         p.setPrefWidth(1000);  
         p.setPrefHeight(1000); 
 
         //Container Left-Right
         HBox hbox = new HBox(10);
         hbox.setPadding(new Insets(10));
         vbox.setMargin(OptionsLabel, new Insets(10, 0, 10, 0));
         vbox.setMargin(StudentOptionsLabel, new Insets(10, 0, 5, 0));
         vbox.setMargin(treeOptionsLabel, new Insets(20, 0, 5, 0));
         vbox.setMargin(forsetOptionsLabel, new Insets(20, 0, 5, 0));
         hbox.getChildren().addAll(vbox);
         // main container
         root.getChildren().addAll(hbox);

         vbox.getChildren().addAll(
            OptionsLabel,StudentOptionsLabel,
                //student options
            SVlabel,SurunaValueChoiceBox,
            HLlabel,HurniLevelChoiceBox,
            Vlabel, VoltaChoiceBox,
            Lclabel,textFieldLalCount, 
                // course
            COURSELabel,COURSEChoiceBox,
                // tree/forest
            treeOptionsLabel,
            DEPTHLabel,DEPTHChoiceBox,
            SPLITSLabel,SPLITSChoiceBox,
            forsetOptionsLabel,
            FOREST_SIZELabel,textFieldFOREST_SIZE,
            BOOTSTRAP_SIZELabel ,textFieldBOOTSTRAP_SIZE,
            FOREST_VARLabel,textFieldFOREST_VAR,
            
            submitButton
        );

        //event listeners for options
        submitButton.setOnAction(event -> {
            p.getChildren().addAll(drawTree(
                    SurunaValueChoiceBox.getValue(),
                    HurniLevelChoiceBox.getValue(),
                    VoltaChoiceBox.getValue(),
                    Integer.parseInt(textFieldLalCount.getText())//,
                    // COURSEChoiceBox.getValue(),
                    // Integer.parseInt(DEPTHChoiceBox.getValue()),
                    // Integer.parseInt(SPLITSChoiceBox.getValue()),
                    // Integer.parseInt(textFieldFOREST_SIZE.getText()),
                    // Integer.parseInt(textFieldBOOTSTRAP_SIZE.getText()),
                    // Integer.parseInt(textFieldFOREST_VAR.getText())
                ));
                hbox.getChildren().addAll(p);
                //root.getChildren().addAll(hbox);
        });
       

       
    
        // Displaying the stage
        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Binary Tree Visualisation");
        stage.show();
    }

  
    // Drawing tree 
   private static Pane drawTree
   (
    //student atr parameters
    String AtrSurunaValue,String AtrHurniLevel,String AtrVolta,int AtrLalCount//,
    //course 
    //String COURSE_ID,
    //tree/forest
    //int DEPTH,  int SPLITS, int FOREST_SIZE,int BOOTSTRAP_SIZE,int FOREST_VAR
    ) {

    //SETTINGS
      //general
      final int STUDENT_ID = 1002347;
      final int COURSE = 1;  //courses without data: 10, 16, 19

    //tree 
      final int DEPTH = 3; 
      final int SPLITS = 2; //number of splits --> keep at 2
      final boolean PRINT_TREE = false; //commandline visualisation

    //forest
      final int FOREST_SIZE = 50; //how many trees a forest contains
      final int FOREST_DEPTH = 3; //depth for every tree in the forest
      final int FOREST_SPLITS = 2; //number of splits --> keep at 2
     final int BOOTSTRAP_SIZE = 25; //size of trainingdata that a tree is trained on
      final int FOREST_VAR =  3; //number of variables a tree randomly selects
      final boolean PRINT_FOREST = true; //cmd line visualisation of the first tree in the forest

  //loading files
    final double[][] arrayOfCurrentGrades = Methods.File_To_Array("src/main/resources/com/example/javafxproject/CurrentGrades.csv");
    final String[][] arrayOfStudentInfo = Methods.File_To_Array_String("src/main/resources/com/example/javafxproject/StudentInfo.csv");
   


  //checking if student has properties
    if(arrayOfStudentInfo==null){
      System.out.println("Student without properties");
      return null; 
    }
    System.out.println("course: "+COURSE);
    System.out.println("properties: "+Arrays.toString(arrayOfStudentInfo[STUDENT_ID]));
    System.out.println("expected: "+ arrayOfCurrentGrades[STUDENT_ID][COURSE]);

  //splitting data into trainingsets
    int[] training = DataPreparation.getTrainingIndexes(arrayOfCurrentGrades, COURSE);
    if(training.length==0){
      System.out.println("no training data available");
      return null;
    }
    double[][] trainDatasetCourses = new double[training.length][];
    String[][] trainDatasetAttributes = new String[training.length][];
    for(int i=0; i<training.length; i++){
      int ID = training[i];
      trainDatasetCourses[i] = arrayOfCurrentGrades[ID];
      trainDatasetAttributes[i] = arrayOfStudentInfo[ID];
    }
  

  //setting up the tree
    BinaryDecisionTree tree = new BinaryDecisionTree(SPLITS,DEPTH);
    RandomForest forest = new RandomForest( FOREST_SPLITS, FOREST_DEPTH, BOOTSTRAP_SIZE, FOREST_SIZE, FOREST_VAR);
  //fitting data onto tree
    tree.fit(trainDatasetAttributes,trainDatasetCourses,COURSE);
    forest.fit(trainDatasetAttributes, trainDatasetCourses, COURSE);
  //making a prediction
    System.out.println("tree prediction: "+ tree.predict(arrayOfStudentInfo[STUDENT_ID]));
    System.out.println("forest prediction: "+ forest.predict(arrayOfStudentInfo[STUDENT_ID]));
  //testing the trees
  TreeTests test = new TreeTests(arrayOfCurrentGrades, arrayOfStudentInfo);
  System.out.println("tree accuracy: "+test.getAccuracy(tree, COURSE)); //accuracy for this tree on this course
  ForestTests ftest = new ForestTests(arrayOfCurrentGrades, arrayOfStudentInfo);
  System.out.println("forest accuracy: "+ftest.getAccuracy(forest, COURSE));

    
  //visualisising tree
  if (PRINT_TREE) tree.printTree();
  if (PRINT_FOREST) forest.printTree();
  tree.printTreeVisualisation(500,20);
  return tree.getPaneTreeVisualisation();

  // //finding best depth --> 3
  // test.testDepth(0, 6);
  // //finding best forestsize --> 50
  // ftest.testForestSize(0, 100, 10);
  // //finding best forestdepth --> 3
  // ftest.testForestDepth(1, 6);
  // //finding best bsize --> 25-35
  // ftest.testBootstrapSize(10, 50, 10);
  // //finding best amount of var --> 3
  // ftest.testVariables(1,4);
  
   }
}