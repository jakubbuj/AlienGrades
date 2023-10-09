import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class App{

    public static void main(String[] args) {
        
        double[][] graduate_grades = File_To_Array("GraduateGrades.csv");
        double[][] current_grades = File_To_Array("CurrentGrades.csv");

        //only used for printing the arrays
        for(int i=0; i<graduate_grades.length; i++){
            if(graduate_grades[i] == null){continue;}
            System.out.println(i+" "+Arrays.toString(graduate_grades[i]));
		}
        System.out.println();
        for(int i=0; i<current_grades.length; i++){
            if(current_grades[i] == null){continue;}
            System.out.println(i+" "+Arrays.toString(current_grades[i]));
		}
    }

    public static double[][] File_To_Array(String fileName){
        try {
            double[][] studentGradesArray = new double[0][]; 
            File file=new File(fileName);
            Scanner fileScanner = new Scanner(file);

            int numOfCourses = 30;
            int studentID = -1; //small value to initialize
            int arrayLength=-1; //small value to initialize

            int linesDone = 0;
            while (fileScanner.hasNextLine() && linesDone <= 5) {
            	String line = fileScanner.nextLine();
            	linesDone++;
                int courseIndex = 0;    //keeps track of the course

            	// and one that scans the line entry per entry using the commas as delimiters
            	Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
            	while (lineScanner.hasNext()) {
            		if (lineScanner.hasNextInt()) {
            			studentID = lineScanner.nextInt();

                        //If the index (indicated by studentID) is larger than the length of the array:
                        //  create a larger array with length studentID+1
                        if(arrayLength<studentID){
                            arrayLength = studentID;
                            studentGradesArray = Arrays.copyOf(studentGradesArray, studentID+1);
                        }
                        //initialises only the studentID's we need to save resources
                        studentGradesArray[studentID] = new double[numOfCourses];

            		} else if (lineScanner.hasNextDouble()) {
            			double d = lineScanner.nextDouble();
                        studentGradesArray[studentID][courseIndex]=d;
                        courseIndex++;
            		} else {
            			String s = lineScanner.next();
                        if(s.equals("NG")){
                            studentGradesArray[studentID][courseIndex]=-1;
                        }
                        courseIndex++;
            		}
            	}
            	lineScanner.close();
            }
            fileScanner.close();
            //prints the grades array per row with student index

            return studentGradesArray;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    /**calculates the mean, median and spread for a course
     * @param course: index of the grade corresponding to the course for a student
     * @return double array: containing {mean, median, spread}
     */
    public static double[] MMS_Course(double[][] studentGradesArray, int course){
        double[] mms = new double[3]; 
        int numOfStudents = studentGradesArray.length;

        //puts all grades for one course in a single array
        double[] courseGrades = new double[numOfStudents];
        for(int i=0; i<numOfStudents; i++){
            courseGrades[i] = studentGradesArray[i][course];
        }
        System.out.println(Arrays.toString(courseGrades));

        //mean
        double total=0;
        for (double grade : courseGrades) {
            total += grade;
        }
        double mean= total/numOfStudents;
        mms[0] = mean;

        //sorting array from lowest to highest grade (needed for median and spread)
        Arrays.sort(courseGrades);
        
        //median
        double median;
        if(numOfStudents%2==0){ 
            median =  courseGrades[numOfStudents/2];
        }else{
            int div2 = numOfStudents/2;
            median = (courseGrades[div2] + courseGrades[div2-1])/2;
        } 
        mms[1] = median;
        
        //spread
        double min = courseGrades[0];
        double max = courseGrades[numOfStudents-1];
        double spread = max - min;
        mms[2] = spread;

        return mms;
    }
}

