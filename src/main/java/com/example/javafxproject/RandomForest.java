package com.example.javafxproject;

public class RandomForest {
    private ForestTree[] treeCollection;
    private int bootstrapSize;
    private int variablesNum;
    private int forestSize;
    private int split;
    private int depth;

    public RandomForest(int split, int depth, int bootstrapSize, int forestSize, int variablesNum){
        treeCollection = new ForestTree[forestSize];
        this.bootstrapSize = bootstrapSize;
        this.variablesNum = variablesNum;
        this.forestSize = forestSize;
        this.split =split;
        this.depth =depth ;
    }

    public void fit(String[][] properties,double[][] grades, int course){
        for (int c=0; c<forestSize; c++) {
            treeCollection[c] = new ForestTree(split, depth, variablesNum);
            int[] indexes = DataPreparation.genBootstrapIndexes(course, grades, bootstrapSize);
            double[][] bootstrapCourses = new double[bootstrapSize][];
            String[][] bootstrapProperties = new String[bootstrapSize][];
            for(int i=0; i<bootstrapSize; i++){
                int ID = indexes[i];
                bootstrapCourses[i] = grades[ID];
                bootstrapProperties[i] = properties[ID];
            }
            treeCollection[c].fit(properties, grades, course);
        }
    }

    public double predict(String[] properties){
        double sum=0;
        for (ForestTree tree : treeCollection) {
            double i= (tree!=null)?tree.predict(properties):0;
            sum +=i;
        }
        return sum/forestSize;
    }

    public void printTree(){
        treeCollection[0].printTree();
    }
}
