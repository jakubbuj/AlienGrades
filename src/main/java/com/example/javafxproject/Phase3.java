import java.util.*;


public class Main{

  public static void main(String[] args) {
  
    //SETTINGS
      //general
        final int STUDENT_ID = 1000243;
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
      final double[][] arrayOfCurrentGrades = Methods.File_To_Array("CurrentGrades.csv");
      final String[][] arrayOfStudentInfo = Methods.File_To_Array_String("StudentInfo.csv");

    //checking if student has properties
      if(arrayOfStudentInfo==null){
        System.out.println("Student without properties");
        return;
      }
      System.out.println("course: "+COURSE);
      System.out.println("properties: "+Arrays.toString(arrayOfStudentInfo[STUDENT_ID]));
      System.out.println("expected: "+ arrayOfCurrentGrades[STUDENT_ID][COURSE]);

    //splitting data into trainingsets
      int[] training = DataPreparation.getTrainingIndexes(arrayOfCurrentGrades, COURSE);
      if(training.length==0){
        System.out.println("no training data available");
        return;
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
      System.out.println();

      
    //visualisising tree
      if (PRINT_TREE) tree.printTree();
      if (PRINT_FOREST) forest.printTree();

    //testing the trees
    TreeTests test = new TreeTests(arrayOfCurrentGrades, arrayOfStudentInfo);
    System.out.println("tree accuracy: "+test.getAccuracy(tree, COURSE)); //accuracy for this tree on this course
    ForestTests ftest = new ForestTests(arrayOfCurrentGrades, arrayOfStudentInfo);
    System.out.println("forest accuracy: "+ftest.getAccuracy(forest, COURSE));
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


