import java.io.File;
import java.util.*;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) {
       
        String[] course_names = {"JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"};

        Scanner scanner = new Scanner(System.in);
        System.out.println("Student's ID: ");
        int studentID = scanner.nextInt();
        scanner.close();

        double[][] arrayOfCurrentGrades = Methods.File_To_Array("CurrentGrades.csv");
       double[][] arrayOfGraduateGrades = Methods.File_To_Array("bugFreeGraduateGrades.csv");
        String[][] arrayOfStudentInfo = Methods.File_To_Array_String("StudentInfo.csv");

        System.out.println("test");


/** This is the binary decision part
 *
 */

        double[][] trainDatasetCourses;
        double[][] testDatasetCourses;

        // Determine the size for the top (90%) and bottom (10%) arrays
        int totalRowsCourses = arrayOfCurrentGrades.length;
        int topRowsCourses = (int) (totalRowsCourses * 0.9);
        int bottomRowsCourses = totalRowsCourses - topRowsCourses;

        // Create top and bottom arrays
        trainDatasetCourses = new double[topRowsCourses][arrayOfCurrentGrades[arrayOfCurrentGrades.length-1].length];
        testDatasetCourses = new double[bottomRowsCourses][arrayOfCurrentGrades[arrayOfCurrentGrades.length-1].length];

        String[][] trainDatasetAttributes = new String[topRowsCourses][arrayOfStudentInfo[arrayOfStudentInfo.length-1].length];
        String[][] testDatasetAttributes = new String[bottomRowsCourses][arrayOfStudentInfo[arrayOfStudentInfo.length-1].length];
        // Copy data from the original array to the top and bottom arrays
        System.arraycopy(arrayOfCurrentGrades, 0, trainDatasetCourses, 0, topRowsCourses);
        System.arraycopy(arrayOfCurrentGrades, topRowsCourses, testDatasetCourses, 0, bottomRowsCourses);
        System.arraycopy(arrayOfStudentInfo, 0, trainDatasetAttributes, 0, topRowsCourses);
        System.arraycopy(arrayOfStudentInfo, topRowsCourses, testDatasetAttributes, 0, bottomRowsCourses);
        System.out.println("test");


        BinaryDecisionTree tree = new BinaryDecisionTree(2,4);
        System.out.println("test");
        tree.fit(trainDatasetAttributes,trainDatasetCourses,1);
        tree.printTree();
        System.out.println("test final");


        /** STEP 1
         *  - calculatating: mean median spread of grades for Students/Courses
         *  - creating list of honored students Cum Laude
         *  - findind similiarities between courses
         * */


        double[] mmsPerCourse = Methods.MeanMedianStandardDeviation_Course(arrayOfCurrentGrades, 0);

        double[] mmsPerStudent = Methods.MeanMedianStandardDeviation_Student(arrayOfCurrentGrades, 0);

         Methods methodForCommonMeanMedianStandardDeviation = new Methods();
        //double[] mmsCommon = methodForCommonMeanMedianStandardDeviation.common_MMS(arrayOfCurrentGrades, temp, temp);


        /** This method returns an array of five hardest courses based on their grade average. 
         * @param students_grades
         * @param course_names
         * @return
         */
        double[] fiveHardestCourses = Methods.FiveHardestCourses(arrayOfCurrentGrades, course_names);

        /** This method returns an array of five easiest courses based on their grade average. 
         * @param students_grades
         * @param course_names
         * @return
         */
        double[] fiveEasiestCourses = Methods.FiveEasiestCourses(arrayOfCurrentGrades, course_names);


        /** This method finds all cum-laude graduates
         * @param students_grades only for graduated students -->  no support for missing values
         * @return integer array containing studentID's 
         */
        int[] cumLaudeGraduates = Methods.Cum_Laude_Graduates(arrayOfCurrentGrades);


        /** This method allows finding similarities between courses
         * @param students_grades: a 2D-array containing studentID and grades
         * @param numOfCourses number of courses in the array
         * @return 2D array with values of type double that indicate similarity
         */
        double[][] courseSimilarityArray = Methods.Similarity_Array(arrayOfCurrentGrades, arrayOfCurrentGrades[0].length);

        /** STEP 2
         *  -
         *  -
         *  -
         */

        /**
         * prints out the missing grades (still under evaluation)
         * @param missing_grade_table
         * @param courseArray
         */
        boolean[][] missingGrades = Methods.Missing_Grades_Table(arrayOfCurrentGrades);
        Methods.Print_Missing_Grades_Sorted(missingGrades, course_names);
        /*
         * finds student who will graduate soon
         * @param students_grades
         * @return
         */
      int[] soonToBeGraduates = Methods.Soon_Graduates(arrayOfCurrentGrades);

        //looks for students that are eligible to graduate (no failed courses)
        int[] thisYearsGraduates = Methods.Eligible_To_Graduate(arrayOfCurrentGrades);

        /*
         * 
         */
        
        /* STEP 3
        *  -
        *  -
        *  -
        */
        
        
        /* Write a method that checks the difference in scores reached in a given course by
        * students with a given property value. For this, you will need to be able to specify
        * the course as an input to the method, but also a way to define how to separate the
        * students into different groups, e.g., by specifying a property name and a selection
        * or boundary value to apply. You can compare not only average scores, but also the
        * difference in variation between the values 
        */
        double courseMeanByProperty = Methods.courseMeanByProperty(0, null, arrayOfCurrentGrades, arrayOfStudentInfo);
        
        /* Besides the student information from the extra file, do the same using the scores 
        * obtained on previously passed courses
        */
       double CourseMeanByCourse = Methods.predictionByProperty(studentID, 0, arrayOfStudentInfo, arrayOfCurrentGrades);
        
        /* Write code that for a given course, finds the best property to help guess the grade
         * a student will get for that course. In this case, you can define best according to a
         * measure called variance reduction. 
         */
       double passingPrediction = Methods.predictionByCourses(studentID, 0, arrayOfCurrentGrades, arrayOfStudentInfo);
        /**
         * Finds the best property to predict a grade by using variance reduction
         * @param studentID
         * @param predict_course
         * @param student_grades
         * @return
         */
       String  best_property = Methods.Best_Property(studentID, 0, arrayOfStudentInfo,  arrayOfCurrentGrades);

        /* STEP 4
        *  combining prediction stumps to make more accurate predictions
        *  -
        *  -
        */
       //modified the predictionByProperty to incorperate multple properties
        
        //added a prediction based on performance of other courses
        //and means it with the prediction based on property to geta more accurate prediction
        double second_prediction = Methods.predictionByCourses(studentID, 0, arrayOfCurrentGrades, arrayOfStudentInfo);

        /** PHASE 3
         */

        /** Splitting into Training set and Testing set
         */


    }

}

