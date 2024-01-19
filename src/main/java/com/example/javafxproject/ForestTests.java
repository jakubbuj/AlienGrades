package com.example.javafxproject;

public class ForestTests {
    private double[][] grades;
    private String[][] properties;
    private int forestDepth;
    private int splits;
    private int bootstrapSize;
    private int forestSize;
    private int variablesNum;

    public ForestTests(double[][] grades, String[][] properties){
        this.grades = grades;
        this.properties = properties;
        forestDepth = 3;
        splits = 2;
        bootstrapSize = 25;
        forestSize = 50;
        variablesNum = 2;
    }

    public void testVariables(int  min, int max){
        System.out.println("FOREST NUMBER OF VARIABLES TESTING");
        for(int var=min; var<=max; var++){
            double sumError = 0;
            double tested = 0;
            RandomForest forest = new RandomForest(splits, forestDepth, bootstrapSize, forestSize, var);
            for(int course=0; course<grades[grades.length-1].length; course++){
                int[] indexes = DataPreparation.getTrainingIndexes(grades, course);
                if(indexes.length==0) continue;
                double[][] testGrades = new double[indexes.length][];
                String[][] testProperties = new String[indexes.length][];
                for(int i=0;i<indexes.length; i++){
                    testGrades[i] = grades[indexes[i]];
                    testProperties[i] = properties[indexes[i]];
                }

                forest.fit(testProperties, testGrades, course);
                sumError += getAccuracy(forest,course);
                tested++;
            }
            System.out.println("#variables: "+var+" -- accuracy: "+sumError/tested);
        }
        System.out.println();
    }

    public void testForestDepth(int min, int max){
        System.out.println("FOREST DEPTH TESTING");
        for(int depth=min; depth<=max; depth++){
            double sumError = 0;
            double tested = 0;
            RandomForest forest = new RandomForest(splits, depth, bootstrapSize, forestSize, variablesNum);
            for(int course=0; course<grades[grades.length-1].length; course++){
                int[] indexes = DataPreparation.getTrainingIndexes(grades, course);
                if(indexes.length==0) continue;
                double[][] testGrades = new double[indexes.length][];
                String[][] testProperties = new String[indexes.length][];
                for(int i=0;i<indexes.length; i++){
                    testGrades[i] = grades[indexes[i]];
                    testProperties[i] = properties[indexes[i]];
                }

                forest.fit(testProperties, testGrades, course);
                sumError += getAccuracy(forest,course);
                tested++;
            }
            System.out.println("depht: "+depth+" -- accuracy: "+sumError/tested);
        }
        System.out.println();
    }

    public void testBootstrapSize(int min, int max, int increment){
        System.out.println("BOOTSTRAP SIZE TESTING");
        for(int size=min; size<=max; size+=increment){
            double sumError=0;
            double sizesTested=0;
            RandomForest forest = new RandomForest(splits, forestDepth, size, forestSize, variablesNum);
            for(int course=0; course<grades[grades.length-1].length; course++){
                int[] indexes = DataPreparation.getTrainingIndexes(grades, course);
                if(indexes.length==0) continue;
                double[][] trainGrades = new double[indexes.length][];
                String[][] trainProperties = new String[indexes.length][];
                for(int i=0;i<indexes.length; i++){
                    trainGrades[i] = grades[indexes[i]];
                    trainProperties[i] = properties[indexes[i]];
                }
                forest.fit(trainProperties,trainGrades,course);
                sumError += getAccuracy(forest, course);
                sizesTested++;
            }
            System.out.println("size: "+size+" -- accuracy: "+sumError/sizesTested);
        }
        System.out.println();
    }

    public void testForestSize(int min, int max, int increment){
        System.out.println("FOREST SIZE TESTING");
        for(int size=min; size<=max; size+=increment){
            double sumError=0;
            double sizesTested=0;
            RandomForest forest = new RandomForest(splits, forestDepth, bootstrapSize, size, variablesNum);
            for(int course=0; course<grades[grades.length-1].length; course++){
                int[] indexes = DataPreparation.getTrainingIndexes(grades, course);
                if(indexes.length==0) continue;
                double[][] trainGrades = new double[indexes.length][];
                String[][] trainProperties = new String[indexes.length][];
                for(int i=0;i<indexes.length; i++){
                    trainGrades[i] = grades[indexes[i]];
                    trainProperties[i] = properties[indexes[i]];
                }
                forest.fit(trainProperties,trainGrades,course);
                sumError += getAccuracy(forest, course);
                sizesTested++;
            }
            System.out.println("size: "+size+" -- accuracy: "+sumError/sizesTested);
        }
        System.out.println();
    }

    public void setDefaults(int splits, int depth, int bootstrapSize, int forestSize, int variablesNum){
        this.splits=splits;
        this.forestDepth=depth;
        this.bootstrapSize=bootstrapSize;
        this.forestSize=forestSize;
        this.variablesNum=variablesNum;
    }
    
    public double getAccuracy(RandomForest forest, int course){
        int[] indexes = DataPreparation.getTestIndexes(grades, course);
        double[][] testGrades = new double[indexes.length][];
        String[][] testProperties = new String[indexes.length][];
        for(int i=0;i<indexes.length; i++){
            testGrades[i] = grades[indexes[i]];
            testProperties[i] = properties[indexes[i]];
        }
        double sumError =0;
        double predictions =0;
        for (int student=0; student<testGrades.length; student++) {
            if(testGrades[student]!=null&&testGrades[student][course]>0){
                 sumError+= Math.abs(testGrades[student][course] - forest.predict(testProperties[student]));
                 predictions++;
            }
        }
        double accuracy = (predictions>0)? sumError/predictions : 0;
        return accuracy;
    
    }
}
