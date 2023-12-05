package com.example.javafxurwa;

import java.io.File;
import java.io.IOException;
import java.util.*;


//common_MMS: What does it do (purpose)?
//Comparing_Courses: unfinished, purpose?
//person correlation (from line 388): ask ary to comment the methods, remove duplicates
//multiple ways to calculate the course mean? which should I use
//Missing_Grades_Table, Print_Missing_Grades_Sorted: duplicate with ary's?
//FiveEasiestCourses, Print_Missing_Grades, Students_Per_Course: look into the sorting of arrays with keeping the indexes

public class Methods {

    public static void main(String[] args) throws IOException{

        double[][] grads = File_To_Array("src/main/resources/com/example/javafxurwa/GraduateGrades.csv");

        System.out.println(Arrays.toString(grads[0]));
        System.out.println(Arrays.toString(Cum_Laude_Graduates(grads)));

    }



	/** This method converts a csv-file into a two-dimensional containing the student grades
     * @param  fileName name of the csv-file
     * @return two-dimensional array of type double
    */
    public static double[][] File_To_Array(String fileName){
        try {
            File file=new File(fileName);
            Scanner fileScanner = new Scanner(file);

            //added
            double[][] studentArray = new double[0][];
            int length = 0;

            int linesDone = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if( !(line.length()>0))continue;
                linesDone++;

                //added code
                if(linesDone==1){
                    continue;
                }
                ArrayList<Double> gradeList = new ArrayList<Double>();
                int studentID = -1;

                // and one that scans the line entry per entry using the commas as delimiters
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                while (lineScanner.hasNext()) {
                    if (lineScanner.hasNextInt() && studentID==-1) {
                        studentID = lineScanner.nextInt();
                    }else if(lineScanner.hasNextDouble()){
                        double d = lineScanner.nextDouble();
                        gradeList.add(d);
                    }else if(lineScanner.hasNext() ){
                        String s = lineScanner.next();
                        if(s.equals("NG")){
                            gradeList.add(-1.0);
                        }
                    }
                }
                //append gradeList to array
                //double[] grades = gradeList.toArray(new double[0]);

                double[] grades = gradeList.stream().mapToDouble(d -> d).toArray();

                if(studentID>=length){
                    length= studentID+1;
                }
                studentArray = Arrays.copyOf(studentArray, length);
                studentArray[studentID] = grades;
                lineScanner.close();
            }
            fileScanner.close();
            //prints the grades array per row with student index

            return studentArray;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
 
    
    /** This method converts a csv-file into a two-dimensional containing the student properties
     * @param  fileName name of the csv-file
     * @return two-dimensional array of type String
    */
    public static String[][] File_To_Array_String(String fileName){
        try {
            File file=new File(fileName);
            Scanner fileScanner = new Scanner(file);

            //added
            String[][] student_info = new String[0][];
            int length = 0;

            int linesDone = 0;
            while (fileScanner.hasNextLine()) {
            	String line = fileScanner.nextLine();
            	linesDone++;
                
                //added code
                if(linesDone==1){
                    continue;
                }
                List<String> propertyList = new ArrayList<String>();
                int studentID = -1;

            	// and one that scans the line entry per entry using the commas as delimiters
            	Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
            	while (lineScanner.hasNext()) {
            		if (lineScanner.hasNextInt() && studentID==-1) {
            			studentID = lineScanner.nextInt();
            		} else {
            			String s = lineScanner.next();
                        propertyList.add(s);
            		}
            	}
                //append propertyList to array
                String[] property = propertyList.toArray(new String[0]);

                if(studentID>=length){
                    length= studentID+1;
                }
                student_info = Arrays.copyOf(student_info, length);
                student_info[studentID] = property;
            	lineScanner.close();
            }
            fileScanner.close();
            //prints the grades array per row with student index

            return student_info;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
 
    /** This method calculates the mean, median and spread for a course with support for missing values
    * @param students_grades: a 2D-array containing studentID and grades
    * @param course: index of the grade corresponding to the course for a student
    * @return double array: containing {mean, median, spread}
    */
    public static double[] MeanMedianStandardDeviation_Course(double[][] students_grades, int course){
        double[] mms = {-1.0, -1.0, -1.0};
        int numOfStudents = 0;
        double[] courseGrades = new double[0];

        for (double[] student : students_grades) {
            if(student==null||student[course]==-1){continue;}
            numOfStudents++;
            courseGrades = Arrays.copyOf(courseGrades, numOfStudents);
            courseGrades[courseGrades.length-1] = student[course];
        }
        Arrays.sort(courseGrades);
        //System.out.println(Arrays.toString(courseGrades));
        if(numOfStudents==0)return mms;

        int len = courseGrades.length;

        //mean
        double sum = 0;
        for (double grade: courseGrades) {
            sum += grade;
        }
        double mean = sum/numOfStudents;
        mms[0] = round(sum/numOfStudents, 2);

        //median
        double median;
        if(len%2==1){ //odd 
            median =  courseGrades[courseGrades.length/2];
        }else{  //even 
            median = (courseGrades[len/2] + courseGrades[len/2-1])/2;
        } 
        mms[1] = round(median, 2);

        // standard deviation
        double devSum = 0;
        for (double grade: courseGrades) {
            devSum += Math.pow((grade - mean), 2);
        }
        double deviation = Math.sqrt(devSum / (courseGrades.length - 1));
        mms[2] = round(deviation, 2);

        return mms;
    }
    
    /** This method calculates the mean, median and standard deviation for a student
    * @param students_grades a 2D-array containing studentID and grades
    * @param studentID index of the student for grade picking
    * @return mean, median and standard deviation for a certain student 
    */
    public static double[] MeanMedianStandardDeviation_Student(double[][] students_grades, int studentID) {
        int amountOfCourses = students_grades[studentID].length;
        double[] mmsForStudent = new double[3];

        double[] studentGrades = new double[amountOfCourses];

        for (int j = 0; j < amountOfCourses; j++) {
            studentGrades[j] = students_grades[studentID][j];
        }

        //mean calculating
        double sum = 0;
                int grade_courses = 0;
                for (double grade : studentGrades) {
                    if(grade!=-1){
                        sum += grade;
                        grade_courses++;
                    }
                }
        double mean = sum / grade_courses;
        mmsForStudent[0] = round(mean, 2); //assigning to a proper index in final results array

        //putting in ascending order for median calculating
        Arrays.sort(studentGrades);
        double median;
        grade_courses = 0; 
        for (double grade : studentGrades) {
                    if(grade!=-1){
                        sum += grade;
                        grade_courses++;}}
        if (grade_courses % 2 == 1) {
            median = studentGrades[grade_courses / 2];
        } else {
            median = (studentGrades[grade_courses / 2 + 1] + studentGrades[grade_courses / 2]) / 2;
        }
        mmsForStudent[1] = round(median, 2); //assigning to a proper index in final results array 

        //deviation calculating
        double deviation = 0;
        grade_courses = 0; 
        for (double mark : studentGrades) {
                    if(mark!=-1){
                        sum += mark;
                        grade_courses++;}}
        for (double marks : studentGrades) {
            deviation += (marks - mean) * (marks - mean);
        }
        deviation = Math.sqrt(deviation / grade_courses);
        mmsForStudent[2] = round(deviation, 2); //assigning to a proper index in final results array

        return mmsForStudent;
    }

    /** This method calculates the common mean, median and standard deviation for chosen student (we chose to just display standard deviation for pot. cheaters)
    * @param students_grades: a 2D-array containing studentID and grades
    * @param selectedStudentIDs: students that we will be checking for common standard deviation
    * @param selectedCourseIDs: courses that we will take into consideration for these students 
    * @return standard deviation for potentially cheating students 
    */
    public static double[] common_MMS(double[][] students_grades, int[] selectedStudentIDs, int[] selectedCourseIDs) {
        int numSelectedStudents = selectedStudentIDs.length;
        double[] commonStats = new double[3];
        int maxCourses = selectedCourseIDs.length;

        double[] selectedGrades = new double[numSelectedStudents * maxCourses];
        double sum = 0;
        int index = 0;

        for (int studentID : selectedStudentIDs) {
            for (int courseID : selectedCourseIDs) {
                if (courseID < students_grades[studentID].length) {
                    selectedGrades[index++] = students_grades[studentID][courseID];
                    sum += students_grades[studentID][courseID];
                }
            }
        }

        double commonMean = sum / (numSelectedStudents * maxCourses);
        commonStats[0] = round(commonMean, 2);

        Arrays.sort(selectedGrades);
        double commonMedian;
        if (selectedGrades.length % 2 == 1) {
            commonMedian = selectedGrades[selectedGrades.length / 2];
        } else {
            commonMedian = ((selectedGrades[selectedGrades.length / 2 + 1] + selectedGrades[selectedGrades.length / 2]) / 2);
        }
        commonStats[1] = round(commonMedian, 2);

        double commonDeviation = 0;
        for (double grade : selectedGrades) {
            commonDeviation += (grade - commonMean) * (grade - commonMean);
        }
        commonDeviation = Math.sqrt(commonDeviation / (numSelectedStudents * maxCourses));
        commonStats[2] = round(commonDeviation, 2);

        return commonStats;
    }  

    /** This method finds all cum-laude graduates
    * @param students_grades only for graduated students -->  no support for missing values
    * @return integer array containing studentID's 
    */
    public static int[] Cum_Laude_Graduates (double[][] students_grades ){
        
        int num_of_honors=0;
        // checking number of the honored students
        for(int i=0; i<students_grades.length;i++){
            if( MeanMedianStandardDeviation_Student(students_grades, i)[0] >=8.00){ //first value in the mms array is mean
                num_of_honors++;
            }
        }
        int[] temp = new int[num_of_honors];
        num_of_honors=0;
        for(int i=0; i<students_grades.length;i++){
            if(MeanMedianStandardDeviation_Student(students_grades, i)[0] >=8.00){
                temp[num_of_honors]=i;
                num_of_honors++;
            }
        }
        int[] honored_students_id = temp;
        return honored_students_id;
    }

    /** This method calculates the similarity of courses by person correlation
    * @param courseA first course
    * @param courseB second course
    * @param students_grades: a 2D-array containing studentID and grades
    * @return double from 0-10 to indicate similarity
    */ 
    public static double Course_Similarity(double[][] students_grades, int courseA, int courseB){
        double differenceSum = 0;
        int numOfStudents = 0;
        for(double[] student: students_grades){
            if(student!=null&&student[courseA]!=-1&&student[courseB]!=-1){
                numOfStudents++;
                differenceSum += Math.abs(student[courseA] - student[courseB]);
            }
        }
        if(numOfStudents==0){return -1;}
        return 10 - round(differenceSum/numOfStudents, 2);
    }

    /** This method allows finding similarities between courses
    * @param students_grades: a 2D-array containing studentID and grades
    * @param numOfCourses number of courses in the array
    * @return 2D array with values of type double that indicate similarity
    */
    public static double[][] Similarity_Array(double[][] students_grades, int numOfCourses){
        double[][] array = new double[numOfCourses][numOfCourses];
        for(int A=0; A<numOfCourses; A++){
            for(int B=A; B<numOfCourses; B++){
                double similarity = round(Course_Similarity(students_grades, A, B),2);
                array[A][B] = similarity;
                array[B][A]  =similarity;
            }
        }
        
        return array;
    }

    /** Calculates the averages for all courses and puts them in an array
     * @param students_grades
     * @return mean score for the courses a double array
     */
    public static double[] Course_Average(double[][] students_grades) {
        double[] average = new double[students_grades[0].length]; //create an array for each average course grade
        int courses = students_grades[0].length; //number of courses
        int grades = students_grades.length; //number of grades for each course

        for (int row = 0; row < courses; row++) {
            for (int column = 0; column < grades; column++) {
                average[row] += students_grades[column][row];
            }
        }

        for (int row = 0; row < courses; row++) {
            average[row] /= grades;
        }

        return average; //return 1d array
    }
    
        /** finding similarities between courses via person correlation : Class Correlation analysis
        * methods need proper commenting, explaining parameters
        */
    public static double calculateMean(double[] values) {
        double sum = 0.0;
        double[] var = values;

        for(int i = 0; i < values.length; ++i) {
        double value = var[i];
        sum += value;
        }

        return sum / (double)values.length;
    }

    public static double calculatingPersonCorrelation(double[] x, double[] y) {
        double meanX = calculateMean(x);
        double meanY = calculateMean(y);
        int n = x.length;
        double sumXY = 0.0;
        double sumXX = 0.0;
        double sumYY = 0.0;

        for(int i = 0; i < n; ++i) {
            double xMinusMean = x[i] - meanX;
            double yMinusMean = y[i] - meanY;
            sumXY += xMinusMean * yMinusMean;
            sumXX += xMinusMean * xMinusMean;
            sumYY += yMinusMean * yMinusMean;
        }
        double correlation = sumXY / (Math.sqrt(sumXX) * Math.sqrt(sumYY));
        double roundedCorrelation = Math.round(correlation * 1000.0) / 1000.0;

        return roundedCorrelation;
    
    }

    /** finds the 5 hardest courses based in their average
     * @param students_grades
     * @param course_names
     * @return  double array with course ID's
     */
    public static double[] FiveHardestCourses(double[][] students_grades, String[] course_names) {
        double[] average = Course_Average(students_grades);
        double[][] sorted_average = Sort_With_Id(average,true);
        double[] five_hardest = new double[5];
        double[] five_hardest_Id = new double[5];
        
        for(int i=0;i<5;i++){
            five_hardest[i]=sorted_average[0][average.length-i -1];
            five_hardest_Id[i]=sorted_average[1][average.length-i -1];
        }
        return five_hardest_Id;
        
    }
   
    /** finds the 5 easiest courses based in their average
     * @param students_grades
     * @param course_names
     * @return  double array with course ID's
     */
    public static double[] FiveEasiestCourses(double[][] students_grades, String[] course_names){
        
        double[] average = Course_Average(students_grades);
        double[][] sorted_average = Sort_With_Id(average,true);
        double[] five_easiest = new double[5];
        double[] five_easiest_Id = new double[5];

        for(int i=0;i<5;i++){
            five_easiest[i]=sorted_average[0][i];
            five_easiest_Id[i]=sorted_average[1][i];
        }
        
        return five_easiest;
    }
    
    /**
     * look id ary's step 2 code does the same
     * @param students_grades
     * @return
     */
    public static boolean[][] Missing_Grades_Table(double[][] students_grades){
        boolean[][] studentGradesPresent = new boolean[0][];
        int groups  = 0;

        for(double[] student : students_grades){
            if(student == null){continue;}
            //convert students grades into array which marks courses as graded 
            boolean[] hasGrade = new boolean[student.length]; //stores if student has grade for course or not
            for(int course=0; course<student.length; course++){
                if(student[course]!=-1){
                    hasGrade[course] = true;
                }
            }

            //checks if hasGrade isn't already in studentGradesPresent
            boolean same = false;
            for(boolean[] group : studentGradesPresent){ 
                if(Arrays.equals(group, hasGrade)){
                    same = true;
                }
            }
            if(!same){
                groups++;
                studentGradesPresent = Arrays.copyOf(studentGradesPresent, groups);
                studentGradesPresent[groups-1] = hasGrade;
            }
        }    
        return studentGradesPresent;       
    }

    /**
     * returns the indexes of an array so that all the elements are ordered
     * @param array
     * @return
     */
    public static int[] Index_Sort(int[] array){
        double[] temp = new double[array.length];

        for(int index=0; index<array.length; index++){
            temp[index] = array[index] + index/100.0; 
        }
        
        Arrays.sort(temp);

        int[] printOrder = new int[temp.length];
        for (int i=0; i<temp.length; i++) {
            double d = temp[i];
            int p = (int) Math.round((d - (int) d) *100);
            printOrder[i] = p;
        }
        return printOrder;
    }

    /** counts how many student follow a course
     * @param students_grades
     * @return array containing index of the courses sorted by # of students 
     */
    public static int[] Students_Per_Course(double[][] students_grades){
        int len = students_grades.length;
        int number_of_courses = students_grades[len-1].length;
        int[] student_per_course = new int[number_of_courses];

        for(int course=0; course<number_of_courses; course++){
            for (double[] student : students_grades) {
                if(student!=null && student[course]!=-1){
                    student_per_course[course]++;
                }
            }
        }
        return student_per_course;
    }

    /**
     * prints out the missing grades (still under evaluation)
     * @param missing_grade_table
     * @param courseArray
     */
    public static void Print_Missing_Grades_Sorted(boolean[][] missing_grade_table, String[] courseArray){

        //stores the amount of grades there are per course
        int[] num_of_grades = new int[missing_grade_table[0].length]; 
        for(int course=0; course<missing_grade_table[0].length; course++){
            for (int group=0; group<missing_grade_table.length; group++) {
                if(missing_grade_table[group][course]==true){
                    num_of_grades[course]++;
                }
            }
        }
        
        //sorts courses based on the amount of grades
        int[] printOrder = Index_Sort(num_of_grades);
        
        System.out.println("\n\nGroups of students classified based on what courses are graded:\n");
        for(int course : printOrder){
            System.out.print(courseArray[course]+ " | ");
            for (boolean[] group : missing_grade_table) {
                if(group[course]){
                    System.out.print(" # ");
                } else {
                    System.out.print(" - ");
                }
                
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * finds student who will graduate soon
     * @param students_grades
     * @return
     */
    public static int[] Soon_Graduates(double[][] students_grades){
        //requirements for graduating:
        int required_courses_passed = 25; 

        int grads = 0;
        int[] soon_graduates = new int[grads];

        //looping over students and checking requirements\
        
        for(int i=0;  i<students_grades.length; i++){
            double[] student = students_grades[i];
            double passing_grade = 6.0;
            int courses_passed = 0;

            if(student!=null){
                for (double course : student) {
                    if(course >= passing_grade){
                        courses_passed++;
                    }
                }
                if(courses_passed>=required_courses_passed){
                    grads++;
                    soon_graduates = Arrays.copyOf(soon_graduates, grads);
                    soon_graduates[grads-1] = i;
                }
            }
        }
        System.out.println("# of graduates: "+ grads);
        return soon_graduates;
        
    }

    /**
     * Finds students that are eligible to graduate (no failed courses, almost all courses completed)
     * @param students_grades
     * @return
     */
    public static int[] Eligible_To_Graduate(double[][] students_grades){
        double passing_grade = 6.0;
        int eligible = 0;
        int[] eligibleID = new int[eligible];

        for(int i=0;  i<students_grades.length; i++){
            double[] student = students_grades[i];

            if(student!=null){
                boolean all_passed = true;
                for (int course=0; course<student.length && all_passed; course++) {
                    double grade = student[course];
                    if(grade < passing_grade && grade != -1){
                        all_passed = false;
                    }
                }
                if(all_passed){
                    eligible++;
                    eligibleID = Arrays.copyOf(eligibleID, eligible);
                    eligibleID[eligible-1] = i;
                }
            }
            
        }
        return eligibleID;
    }

    /** Simple rounding function to specified decimal place
     *  @param k: double to be rounded
     *  @param decimalPlace: specifies the decimal place for rounding
     *  @return double rounded number
    **/
    public static double round(double k, int decimalPlace){
        return Math.round(k*Math.pow(10,decimalPlace) )/Math.pow(10,decimalPlace);
    }
    
    /** String array, and putting array's id s in same order as sorted array
     *  @param arr: array to be sorted
     *  @param order: true : ascending / false : descending
     *  @return 2d array {{sorted array}{sorted array's id s}}
    **/
    public static double[][] Sort_With_Id(double[] arr, boolean order){
       
        double[][] sorted_arr_w_id= new double[2][arr.length];
        double[] id_arr=new double[arr.length];
        for(int i=0; i<id_arr.length;i++){
            id_arr[i]=i;
        }
        boolean change=false;
        double temp;
        //order:
        //true : ascending
        //false : descending
        for(int i=0;i<arr.length-1; i++){
            for(int j=0;j<arr.length-1;j++){
                if((arr[j]<arr[j+1]) && order){ // ascending
                    // 2 values are replaced by each other
                    temp= arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                    change=true;
                }
                else if(arr[j]>arr[j+1] && !order) { //descending
                    temp= arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                    change=true;
                }
                if(change){
                    temp=id_arr[j+1];
                    id_arr[j+1]=id_arr[j];
                    id_arr[j]=temp;
                    change=false;
                }
            }
        }
        for(int i=0;i<sorted_arr_w_id[0].length; i++){
            sorted_arr_w_id[0][i]= arr[i];
            sorted_arr_w_id[1][i]= id_arr[i];
        }
        return sorted_arr_w_id; 
    }

    /**
     * Predicts a grade for a course for a given student based on the performance of that student in other courses
     * @param StudentID
     * @param predict_course
     * @param studentGrades
     * @param studentProperties
     * @return
     */
    public static double predictionByCourses(int StudentID, int predict_course, double[][] studentGrades, String[][] studentProperties){
        String property = Best_Property(StudentID, predict_course, studentProperties, studentGrades);
        double A = courseMeanByProperty(predict_course, property, studentGrades, studentProperties);
        int course = Best_Course(StudentID, predict_course, studentGrades);
        double grade = studentGrades[StudentID][course];
        double lower=0;
        double upper=10;
        if(grade >= 8){
            lower = 8;
            upper = 10;
        }else if(grade >= 6){
            lower = 6;
            upper = 8;
        }else if(grade >= 4){
            lower = 4;
            upper = 6;
        }
        double B = courseMeanByCourse(predict_course,course,lower,upper,studentGrades);
        return (A+B)/2;
    }

    /**
     * Finds the best course to predict a grade by using variance reduction
     * @param studentID
     * @param predict_course
     * @param student_grades
     * @return
     */
    public static int Best_Course(int studentID, int predict_course, double[][] student_grades){
        double[] gradeArray = student_grades[studentID];
        int courseLowestVar=-1;
        double lowestVar=-1;

        
        //loop through all courses (not targetCourse or NG courses) and calculate their variance
        for(int course=0; course<gradeArray.length; course++){
            if(course==predict_course){continue;}
            //defining bounds for predicting
            double grade = student_grades[studentID][course];
            double lower=0;
            double upper=10;
            if(grade >= 8){
                lower = 8;
                upper = 10;
            }else if(grade >= 6){
                lower = 6;
                upper = 8;
            }else if(grade >= 4){
                lower = 4;
                upper = 6;
            }

            if(lowestVar==-1){
                courseLowestVar = course;
                lowestVar = courseVariance(predict_course, course, lower, upper, student_grades);
            }else if(lowestVar>courseVariance(predict_course, course, lower, upper, student_grades)){
                courseLowestVar = course;
                lowestVar = courseVariance(predict_course, course, lower, upper, student_grades);
            }
        }
        return courseLowestVar;
    }

    /**
     * Calculates the mean for a course using students that perform within boundaries for another course
     * @param course_num course we want to calculate the mean of
     * @param compare_course course to find students with certain performance
     * @param lower lower boundary for performance on compare_course
     * @param upper upper boundary for performance on compare_course
     * @param student_grades array containing students and grades
     * @return mean as a double
     */
    public static double courseMeanByCourse(int course_num, int compare_course, double lower, double upper, double[][] student_grades){
            //calculates mean the course
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_grades.length; ID++){ //loops over all the students
                if( student_grades[ID] == null) {continue;} //checks if there's a student with this ID
                double grade_comp = student_grades[ID][compare_course];   //grade for course we compare to   
                double grade = student_grades[ID][course_num]; //grade for course we want to predict
                if( grade_comp != -1 && grade!=-1 && grade_comp >= lower && grade_comp < upper)  //checks if student has is between boundaries, then adds to total
                {   numOfStudents++; 
                    sum += grade;
                }
            }
         double mean = sum/numOfStudents;

        
        return mean;

    }
    
    /**
     * Calculates the variance in grades for a course using students that perform within boundaries for another course
     * @param course_num
     * @param compare_course
     * @param lower
     * @param upper
     * @param student_grades
     * @return
     */
    public static double courseVariance(int course_num, int compare_course, double lower, double upper, double[][] student_grades){
        double mean = courseMeanByCourse(course_num, compare_course, lower, upper, student_grades); 
        
            //calculates mean the course
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_grades.length; ID++){ //loops over all the students
                if( student_grades[ID] == null) {continue;} //checks if there's a student with this ID
                double grade_comp = student_grades[ID][compare_course];   //grade for course we compare to   
                double grade = student_grades[ID][course_num]; //grade for course we want to predict
                if( grade_comp != -1 && grade!=-1 && grade_comp >= lower && grade_comp < upper)  //checks if student has is between boundaries, then adds to total
                {   numOfStudents++;
                    sum += Math.pow((grade-mean), 2);
                }
            }
            mean = sum/numOfStudents;

        
        return mean;

    }
    
    /**
     * Predicts a grade for a course for a given student based on their properties
     * @param StudentID
     * @param predict_course
     * @param studentGrades
     * @param studentProperties
     * @return
     */
    public static double predictionByProperty(int studentID, int course_num, String[][] student_properties, double[][] student_grades){
        String Suruna = student_properties[studentID][0];
        String Hurni = student_properties[studentID][1];
        String Lal = student_properties[studentID][2];
        String Volta = student_properties[studentID][3];

        double varSuruna = propertyVariance(course_num, Suruna, student_grades, student_properties);
        double varHurni = propertyVariance(course_num, Hurni, student_grades, student_properties);
        double varLal = propertyVariance(course_num, Lal, student_grades, student_properties);
        double varVolta = propertyVariance(course_num, Volta, student_grades, student_properties);
        double totalVarRed = varHurni + varSuruna + varLal + varVolta;
        double percentageSuruna= (1-varSuruna/totalVarRed) * courseMeanByProperty(course_num, Suruna, student_grades, student_properties);
        double percentageHurni= (1- varHurni/totalVarRed)  * courseMeanByProperty(course_num, Hurni, student_grades, student_properties);
        double percentageLal= (1- varLal/totalVarRed)  * courseMeanByProperty(course_num, Lal, student_grades, student_properties);
        double percentageVolta= (1 -varVolta/totalVarRed)  * courseMeanByProperty(course_num, Volta, student_grades, student_properties);
        return (percentageLal + percentageVolta + percentageSuruna + percentageHurni)/3;

    }

    public static double[] courseMinMax(int course_num, double[][] student_grades){
        double[] a = {10,0};

        for (double[] student : student_grades) {
            if(student[course_num]==-1) continue;
            if(a[0]>student[course_num]) a[0] = student[course_num];
            if(a[1]<student[course_num]) a[1] = student[course_num];
        }

        return a;
    }
    
    /**
     * Finds the best property to predict a grade by using variance reduction
     * @param studentID
     * @param predict_course
     * @param student_grades
     * @return
     */
    public static String Best_Property(int studentID, int course_num, String[][] student_properties, double[][] student_grades){
        String Suruna = student_properties[studentID][0];
        String Hurni = student_properties[studentID][1];
        String Lal = student_properties[studentID][2];
        String Volta = student_properties[studentID][3];

        double varSuruna = propertyVariance(course_num, Suruna, student_grades, student_properties);
        double varHurni = propertyVariance(course_num, Hurni, student_grades, student_properties);
        double varLal = propertyVariance(course_num, Lal, student_grades, student_properties);
        double varVolta = propertyVariance(course_num, Volta, student_grades, student_properties);
        
        double min = Math.min( Math.min(varSuruna, varHurni), Math.min(varLal, varVolta) );
        if (min == varSuruna) {
            return Suruna;
        }else if (min == varHurni) {
            return Hurni;
        }else if (min == varLal) {
            return Lal;
        }else if (min == varVolta) {
            return Volta;
        }
        return null;
    }

    /**
     * Calculates the variance in grades for a course using students with a certain property
     * @param course_num
     * @param property_value
     * @param student_grades
     * @param student_properties
     * @return
     */
    public static double propertyVariance(int course_num, String property_value, double[][] student_grades, String[][] student_properties){
        double variance;
        double mean = courseMeanByProperty(course_num, property_value, student_grades, student_properties);
        String[][] properties = {
            {"nulp", "doot", "lobi"}, //Suruna value
            {"nothing", "low", "medium", "high", "full"}, //Hurni level
            {}, //Lal count
            {"1 star", "2 stars", "3 stars", "4 stars", "5 stars"} //Volta
        };
        property_value.toLowerCase();
        int propertyIndex = Find_Row(properties, property_value);

            
        if(propertyIndex==2){
            int LalCount = Integer.parseInt(property_value);
            int A=0; //lower boundary
            int B=100; //upper boundary
            if(LalCount >= 90){
                A=90; B=100;
            }else if(LalCount >= 80){
                A=80; B=90;
            }else if(LalCount >= 70){
                A=70; B=80;
            }else if(LalCount >= 60){
                A=60; B=70;
            }
            //calculates the variance of the property value
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_properties.length; ID++){
                if( student_properties[ID] == null) {continue;} //checks if there's a student with this ID
                int value = Integer.parseInt(student_properties[ID][propertyIndex]);
                double grade = student_grades[ID][course_num];
                if( value >= A && value < B && grade != -1) //checks if student has property value
                {   sum += Math.pow((grade-mean), 2);
                    numOfStudents++;
                } 
            }
            variance = sum/numOfStudents;
        } else {

            //calculates the variance of the property value
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_properties.length; ID++){
                if( student_properties[ID] == null) {continue;} //checks if there's a student with this ID
                double grade = student_grades[ID][course_num];
                if( student_properties[ID][propertyIndex].equals(property_value) && grade != -1) //checks if student has property value
                {   sum += Math.pow((grade-mean), 2);
                    numOfStudents++;
                }
                
            }
            variance = sum/numOfStudents;
        }
        return variance;
    }

    /**
     * calculates the mean of a course based on a property value 
     * @param course_num
     * @param property_value
     * @param student_grades
     * @param student_properties
     * @return
     */
    public static double courseMeanByProperty(int course_num, String property_value, double[][] student_grades, String[][] student_properties){
        double mean;
        String[][] properties = {
            {"nulp", "doot", "lobi"}, //Suruna value
            {"nothing", "low", "medium", "high", "full"}, //Hurni level
            {}, //Lal count
            {"1 star", "2 stars", "3 stars", "4 stars", "5 stars"} //Volta
        };
        property_value.toLowerCase();
        int propertyIndex = Find_Row(properties, property_value);

            
        if(propertyIndex==2){
            int LalCount = Integer.parseInt(property_value);
            int A=0; //lower boundary
            int B=0; //upper boundary
            if(LalCount >= 90){
                A=90; B=100;
            }else if(LalCount >= 80){
                A=80; B=90;
            }else if(LalCount >= 70){
                A=70; B=80;
            }else if(LalCount >= 60){
                A=60; B=70;
            }
            //calculates mean the course
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_properties.length; ID++){
                if( student_properties[ID] == null) {continue;} //checks if there's a student with this ID
                double grade = student_grades[ID][course_num];                
                if( LalCount >= A && LalCount < B && grade != -1)  //checks if student has is between Lal boundaries
                {   numOfStudents++; 
                    sum += grade;
                }
            }
            mean = sum/numOfStudents;
        } else {
            //calculates mean the course
            double sum=0; int numOfStudents=0;
            for(int ID=0; ID<student_properties.length; ID++){
                if( student_properties[ID]== null) {continue;} //checks if there's a student with this ID
                double grade = student_grades[ID][course_num];
                if( student_properties[ID][propertyIndex].equals(property_value) && grade != -1) //checks if student has property value
                {   numOfStudents++;
                    sum += grade;
                }
            }
            mean = sum/numOfStudents;

        }
        return mean;

    }
    
    /** helper function
     * finds the row where a string is located
     * @param M
     * @param s
     * @return
     */
    private static int Find_Row(String[][] M, String s){
        for(int row=0; row<M.length; row++){
            for(String element : M[row]){
                if(s.equals(element)){
                    return row;
                }
            }
        }
        return 2;
    }

    

    public static int[][] createIndexOfCoursesArray(double[][] current_grades) {
        int numStudents = current_grades.length;
        int numCourses = current_grades[0].length;

        int[][] indexOfCourses = new int[numStudents][];

        for (int student = 0; student < numStudents; student++) {
            List<Integer> CourseIndices = new ArrayList<>();
            for (int course = 0; course < numCourses; course++) {
                if (current_grades[student][course] != -1) {
                    CourseIndices.add(course);
                }
            }

            indexOfCourses[student] = new int[CourseIndices.size()];
            for (int i = 0; i < CourseIndices.size(); i++) {
                indexOfCourses[student][i] = CourseIndices.get(i);
            }
        }

        return indexOfCourses;
    }

    public static String[][] IndexToCourseNames(int[][] indexOfCourses, String[] courses_names) {
        String[][] groupedCourses = new String[indexOfCourses.length][];

        for (int i = 0; i < indexOfCourses.length; i++) {
            groupedCourses[i] = new String[indexOfCourses[i].length];
            for (int j = 0; j < indexOfCourses[i].length; j++) {
                int courseIndex = indexOfCourses[i][j];
                if (courseIndex >= 0 && courseIndex < courses_names.length) {
                    groupedCourses[i][j] = courses_names[courseIndex];
                }
            }
        }

        return groupedCourses;
    }
}