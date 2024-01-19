public class TreeTests {
    private double[][] grades;
    private String[][] properties;

    public TreeTests(double[][] grades, String[][] properties){
        this.grades = grades;
        this.properties = properties;
    }

    public void testDepth(int min, int max){
        System.out.println("TREE DEPTH TESTING");
        for(int depth=min; depth<max; depth++){
            double sumError = 0;
            double treesTested = 0;
            BinaryDecisionTree tree = new BinaryDecisionTree(2, depth);
            for(int course=0; course<grades[grades.length-1].length; course++){
                int[] indexes = DataPreparation.getTrainingIndexes(grades, course);
                if(indexes.length==0) continue;
                double[][] testGrades = new double[indexes.length][];
                String[][] testProperties = new String[indexes.length][];
                for(int i=0;i<indexes.length; i++){
                    testGrades[i] = grades[indexes[i]];
                    testProperties[i] = properties[indexes[i]];
                }

                tree.fit(testProperties, testGrades, course);
                sumError += getAccuracy(tree,course);
                treesTested++;
            }
            System.out.println("depht: "+depth+" -- accuracy: "+sumError/treesTested);
        }
        System.out.println();
    }

    public double getAccuracy(BinaryDecisionTree tree, int course){
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
                 sumError+= Math.abs(testGrades[student][course] - tree.predict(testProperties[student]));
                 predictions++;
            }
        }
        double accuracy = (predictions>0)? sumError/predictions : 0;
        return accuracy;
    }
}
