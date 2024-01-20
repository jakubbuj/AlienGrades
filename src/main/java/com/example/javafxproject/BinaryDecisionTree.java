package com.example.javafxproject;

//for javafx
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class BinaryDecisionTree {
    private Node root; // The root of the decision tree
    // Stopping conditions
    private int minimumSplit;
    private int maxDepth;


    public BinaryDecisionTree(int minimumSplit, int maxDepth) {
        // Initialize the root of the tree
        this.root = null;
        // Set stopping conditions
        this.minimumSplit = minimumSplit;
        this.maxDepth = maxDepth;
    }


    public Node buildTree(String [][] specificCourseArray, int currDepth ){
        String[][] X = extractAttributes(specificCourseArray);
        double[] Y = extractCourse(specificCourseArray);
        int numSamples = X.length;
        int numFeatures = X[0].length;
        String [][][] bestSplit = getBestSplit(specificCourseArray, numSamples, numFeatures);

        // Split until stopping conditions are met
        if (numSamples >= minimumSplit && currDepth <= maxDepth) {
            // Find the best split
            bestSplit = getBestSplit(specificCourseArray, numSamples, numFeatures);
            // Check if information gain is positive
            if (bestSplit != null && bestSplit[2] != null && Double.parseDouble(bestSplit[2][0][2]) > 0) {
                // Recur left
                Node leftSubtree = buildTree(bestSplit[0], currDepth + 1);
                // Recur right
                Node rightSubtree = buildTree(bestSplit[1], currDepth + 1);
                // Return decision node
                return new Node(
                        Integer.parseInt(bestSplit[2][0][0]),
                        bestSplit[2][0][1],
                        leftSubtree,
                        rightSubtree,
                        Double.parseDouble(bestSplit[2][0][2])
                );
            }
        }


        // Compute leaf node
        double leafValue = calculateLeafValue(Y);
        // Return leaf node
        return new Node(leafValue);
    }

    public String [][] extractAttributes(String [][] specificCourseArray) {
        // Extract features from the dataset
        int numColumns = specificCourseArray[0].length;
        int numFeatures = numColumns-1;
        String [][] features = new String [specificCourseArray.length][numFeatures];
        for (int i = 0; i < specificCourseArray.length; i++) {
            for (int j = 0; j < numFeatures; j++) {
                features[i][j] = specificCourseArray[i][j];
            }
        }
        return features;
    }

    public double[] extractCourse(String[][] specificCourseArray) {
        // Extract labels from the dataset for the specified course index
        int numColumns = specificCourseArray[0].length;
        int numCoursePredicted = 1; // Assuming only one label column per course
        double[] coursegrades = new double[specificCourseArray.length];
        // Extract only the label corresponding to the specified course index
        for (int i = 0; i < specificCourseArray.length; i++) {
            coursegrades[i] = Double.parseDouble(specificCourseArray[i][numColumns-numCoursePredicted]);
        }
        return coursegrades;
    }
    public static String[][][] split(String[][] specificCourseArray, int featureIndex, String threshold) {
        // Get the specific column
        String[] column = Methods.getColumn(specificCourseArray, featureIndex);

        // Initialize arrays for left and right datasets
        String[][] leftDataset = new String[specificCourseArray.length][];
        String[][] rightDataset = new String[specificCourseArray.length][];

        // Initialize counters for left and right datasets
        int leftCount = 0;
        int rightCount = 0;

        // Check if the column is convertible to a double array
        if (Methods.isConvertibleToDoubleArray(column)) {
            // Convert the column to a double array
            double[] doubleColumn = Methods.convertToDoubleArray(column);
            double numThreshold = Double.parseDouble(threshold);

            // Iterate through the dataset
            for (int i = 0; i < specificCourseArray.length; i++) {
                String[] row = specificCourseArray[i];
                // Check the condition for splitting
                if (doubleColumn[i] <= numThreshold) {
                    leftDataset[leftCount++] = row.clone(); // Clone to avoid modifying the original array
                } else {
                    rightDataset[rightCount++] = row.clone();
                }
            }
        } else {
            // If conversion is not possible, iterate through the dataset
            // Iterate through the dataset
            for (int i = 0; i < specificCourseArray.length; i++) {
                String[] row = specificCourseArray[i];
                // Check if the column string is equal to the threshold
                if (column[i].equals(threshold)) {
                    leftDataset[leftCount++] = row.clone(); // Clone to avoid modifying the original array
                } else {
                    rightDataset[rightCount++] = row.clone();
                }
            }
        }

        // Trim arrays to remove null entries
        leftDataset = Arrays.copyOf(leftDataset, leftCount);
        rightDataset = Arrays.copyOf(rightDataset, rightCount);

        // Return the split datasets
        return new String[][][]{leftDataset, rightDataset};
    }

    public String [][][] getBestSplit(String[][] specificCourseArray, int numSamples, int numFeatures) {
        String [][][] bestSplit = new String[3][][];
        double maxVarRed = Double.MIN_VALUE;

        // Loop over all features
        for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
            // Get unique values of the current feature
            Set<String> uniqueFeatureValues = new HashSet<>();
            for (int i = 0; i < numSamples; i++) {
                uniqueFeatureValues.add(specificCourseArray[i][featureIndex]);
            }

            // Loop over all unique feature values
            for (String threshold : uniqueFeatureValues) {
                // Get the current split
                String [][][] splitResult = split(specificCourseArray, featureIndex, threshold);
                String [][] datasetLeft = splitResult[0];
                String [][] datasetRight = splitResult[1];

                // Check if children are not null
                if (datasetLeft.length > 0 && datasetRight.length > 0) {
                    // Extract labels (y, left_y, right_y)
                    double[] y = extractCourse(specificCourseArray);
                    double[] leftY = extractCourse(datasetLeft);
                    double[] rightY = extractCourse(datasetRight);

                    // Compute variance reduction
                    double currVarRed = varianceReduction(y, leftY, rightY);

                    if (currVarRed > maxVarRed) {
                        bestSplit[0] = splitResult[0];
                        bestSplit[1] = splitResult[1];
                        String[][] A = {{ Integer.toString(featureToIndex(threshold)), threshold, String.valueOf(varianceReduction(y, leftY, rightY)) }};
                        bestSplit[2] = A;
                        maxVarRed = currVarRed;
                    }
                }
            }
        }
        return bestSplit;
    }

    public int featureToIndex(String feature){
        ArrayList<String> Suruna = new ArrayList<String>(Arrays.asList(new String[]{"nulp","doot","lobi"}));
        ArrayList<String> Hurni = new ArrayList<String>(Arrays.asList(new String[]{"nothing","low","medium","full","high"}));
        ArrayList<String> Lal =new ArrayList<String>( Arrays.asList(new String[]{"1 star","2 stars","3 stars","4 stars","5 stars"}));
        
        if(Suruna.contains(feature)) return 0;
        if(Hurni.contains(feature)) return 1;
        if(Lal.contains(feature)) return 3;
        try {
            Integer.parseInt(feature);
            return 3;
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }

    }

    public double varianceReduction(double[] parentY, double[] leftY, double[] rightY) {
        double weightLeft = (double) leftY.length / parentY.length;
        double weightRight = (double) rightY.length / parentY.length;

        double varianceParent = calculateVariance(parentY);
        double varianceLChild = calculateVariance(leftY);
        double varianceRChild = calculateVariance(rightY);

        double reduction = varianceParent - (weightLeft * varianceLChild + weightRight * varianceRChild);

        return reduction;
    }

    public double calculateVariance(double[] array) {
        double mean = Methods.calculateMean(array);
        double sumSquaredDifferences = 0.0;

        for (int i = 0; i < array.length; i++) {
            double difference = array[i] - mean;
            sumSquaredDifferences += difference * difference;
        }

        return sumSquaredDifferences / array.length;
    }
    public double calculateLeafValue(double[] Y) {
        double mean = Methods.calculateMean(Y);
        return mean;
    }

    public void printTree() {
        printTree(root, "");
    }

    public void printTree(Node tree, String indent) {
        if (Double.isNaN(tree.getValue())) {
            System.out.println("X_" + tree.getAttributeName() + " <= " + tree.getThreshold() + " ? " + tree.getVarRed());
            System.out.print(indent + "left: ");
            printTree(tree.getLeft(), indent + "  ");
            System.out.print(indent + "right: ");
            printTree(tree.getRight(), indent + "  ");

                      return;  // Exit the method after printing the decision condition
        } else {
            System.out.println(tree.getValue());
        }
    }

    public void fit(String [][] attributesArray, double[][] coursesArray,int courseIndex) {
        // Concatenate features and the course we want to build the tree with
        String [][] specificCourseArrayDataset = concatenateArrays(attributesArray, coursesArray, courseIndex);

        // Build the tree
        this.root = buildTree(specificCourseArrayDataset, 0);
    }

    private String[][] concatenateArrays(String[][] attributeArray, double[][] coursesArray, int courseIndex) {
        if (attributeArray.length != coursesArray.length) {
            throw new IllegalArgumentException("Arrays must have the same number of rows.");
        }

        int numRows = attributeArray.length;
        int numTargetColumns = attributeArray[attributeArray.length-1].length;

        String[][] specificCourseArrayDataset = new String[numRows][numTargetColumns + 1];

        // Copy the attributeArray to the specificCourseArrayDataset
        for (int i = 0; i < numRows; i++) {
            System.arraycopy(attributeArray[i], 0, specificCourseArrayDataset[i], 0, numTargetColumns);
        }

        // Add the specified column from the coursesArray to the specificCourseArrayDataset as strings
        for (int i = 0; i < numRows; i++) {
            specificCourseArrayDataset[i][numTargetColumns] = Double.toString(coursesArray[i][courseIndex]);
        }

        return specificCourseArrayDataset;
    }

    public double makePrediction(String[] x, Node tree) {
        // Check if the current node is a leaf node (value is not null)
        if (!Double.isNaN(tree.getValue())) {
            return tree.getValue();
        }

        // Extract the feature value for the current node from the input dataset
        String featureVal = x[tree.getAttributeIndex()];

        // Check if the feature value is a numeric value)
        if (isDouble(featureVal)) {
            double numericFeatureVal = Double.parseDouble(featureVal);
            double numThreshold = Double.parseDouble(tree.getThreshold());
            if (numericFeatureVal <= numThreshold) {
                // Recur into the left subtree if the condition is true
                return makePrediction(x, tree.getLeft());
            } else {
                // Recur into the right subtree if the condition is false
                return makePrediction(x, tree.getRight());
            }
        } else {
            // If the feature value is a String, check if it equals the node's threshold
            if (featureVal.equals(tree.getThreshold())) {
                // Recur into the left subtree if the condition is true
                return makePrediction(x, tree.getLeft());
            } else {
                // Recur into the right subtree if the condition is false
                return makePrediction(x, tree.getRight());
            }
        }
    }

    public double[] predict(String[][] X) {
        // Create an array to store predictions
        double[] predictions = new double[X.length];

        // Iterate through each data point
        for (int i = 0; i < X.length; i++) {
            // Make a prediction for the current data point using the decision tree
            predictions[i] = makePrediction(X[i], root);
        }

        // Return the array of predictions
        return predictions;
    }

    public double predict(String[] properties){
        return makePrediction(properties, root);
    }

    private boolean isDouble(String s){
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    Pane binaryTreePane = new Pane();
   
    public void printTreeVisualisation( int x,int y) {
        printTreeVisualisation(root, " ", x, y, true);
    }
    public void printTreeVisualisation(Node tree, String indent, int x,int y,boolean firstInit) {
       
        if (Double.isNaN(tree.getValue())) {
            if(firstInit){  // checks if method is runned first time, prevents reapeating root
                Rectangle rootRect = new Rectangle(100, 40, Color.YELLOWGREEN);
                rootRect.setStroke(Color.YELLOWGREEN);
                rootRect.setArcWidth(10);
                rootRect.setArcHeight(10);
                rootRect.setLayoutX(x);
                rootRect.setLayoutY(y);
    
                Text rootText = new Text(String.valueOf(
                    "X_" + tree.getAttributeName() +  "<= " + tree.getThreshold() + "?" +
                    "\nVariance:" +tree.getVarRed())
                    );
                rootText.setTextAlignment(TextAlignment.CENTER);
                rootText.setLayoutX(x);
                rootText.setLayoutY(y);

                binaryTreePane.getChildren().addAll(rootRect, rootText );

                printTreeVisualisation(tree.getLeft(), indent + "  ",x-=100,y+=50, false);
                printTreeVisualisation(tree.getRight(), indent + "  ",x-= 100,y+=50, false);

            }else{ // nodes
                Rectangle leftRect = new Rectangle(100, 40, Color.GREEN);
                leftRect.setStroke(Color.BLACK);
                leftRect.setArcWidth(10);
                leftRect.setArcHeight(10);
                leftRect.setLayoutX(x-100);
                leftRect.setLayoutY(y+50);
    
                Text leftText = new Text(String.valueOf(
                    "X_" + tree.getAttributeName() +  "<= " + tree.getThreshold() + "?" +
                    "\nVariance:" +tree.getVarRed())
                    );
                leftText.setTextAlignment(TextAlignment.CENTER);
                leftText.setLayoutX(x-100);
                leftText.setLayoutY(y+50);
                
                Rectangle rightRect = new Rectangle(100, 40, Color.RED);
                rightRect.setStroke(Color.BLACK);
                rightRect.setArcWidth(10);
                rightRect.setArcHeight(10);
                rightRect.setLayoutX(x+100);
                rightRect.setLayoutY(y+50);
    
                Text rightText = new Text(String.valueOf(
                    "X_" + tree.getAttributeName() +  "<= " + tree.getThreshold() + "?" +
                    "\nVariance:" +tree.getVarRed())
                    );
                
                rightText.setTextAlignment(TextAlignment.CENTER);
                rightText.setLayoutX(x+100);
                rightText.setLayoutY(y+50);

                binaryTreePane.getChildren().addAll(leftRect,leftText,rightRect,rightText );
            }
            System.out.println("X_" + tree.getAttributeName() + " <= " + tree.getThreshold() + " ? " + tree.getVarRed());

            System.out.print(indent + "left: ");
            printTreeVisualisation(tree.getLeft(), indent + "  ",x-=100,y+=50, false);


            System.out.print(indent + "right: ");
            printTreeVisualisation(tree.getRight(), indent + "  ",x+=100,y+=50, false);

            // Exit the method after printing the decision condition
        } else { // leaf
            //prints final prediction
            Rectangle finalRect = new Rectangle(100, 40, Color.BLUE);
            finalRect.setStroke(Color.BLACK);
            finalRect.setArcWidth(10);
            finalRect.setArcHeight(10);
            finalRect.setLayoutX(x);
            finalRect.setLayoutY(y+50);

            Text finalText = new Text(String.valueOf(
                "X_" + tree.getAttributeName() +  "<= " + tree.getThreshold() + "?" +
                "\nVariance:" +tree.getVarRed())
                );
            finalText.setTextAlignment(TextAlignment.CENTER);
            finalText.setLayoutX(x);
            finalText.setLayoutY(y+50);

            binaryTreePane.getChildren().addAll(finalRect,finalText);

            System.out.println(tree.getValue());
            
        }
        
    }

    public Pane getPaneTreeVisualisation(){
        return binaryTreePane;
    }
}





