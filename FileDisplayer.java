
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Testing{

    public static void main(String[] args) {

        double[][] studentGradesArray;

        try {
        	// Adapt this when you want to read and display a different file.
            String fileName = "CurrentGrades.csv";
            File file=new File(fileName);
            
            // This code uses two Scanners, one which scans the file line per line
            Scanner fileScanner = new Scanner(file);

            int numOfCourses = 30;
            int linesDone = 0;
            int studentID = -1;
            studentGradesArray = new double[0][];
            int largestID=-1;

            
            while (fileScanner.hasNextLine() && linesDone <= 5) {

            	String line = fileScanner.nextLine();
            	linesDone++;
                int courseIndex = 0;

            	// and one that scans the line entry per entry using the commas as delimiters
            	Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
            	while (lineScanner.hasNext()) {
            		// Separate commands can be used depending on the types of the entries
            		// (i) and (s) are added to the printout to show how each entry is recognized
            		if (lineScanner.hasNextInt()) {
            			int i = lineScanner.nextInt();
                        studentID=i;
                        //creates a larger array of length studentID
                        if(largestID<studentID){
                            largestID = studentID;
                            studentGradesArray = Arrays.copyOf(studentGradesArray, studentID+1);
                        }
                        //initialises only the studentID's
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

            for(int i=0; i<studentGradesArray.length; i++){
                if(studentGradesArray[i] == null){continue;}
				System.out.println(i+" "+Arrays.toString(studentGradesArray[i]));
			}
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    /**calculates the mean, median and spread for a course
     * @param course: index of the grade corresponding to the course for a student
     * @return double array: containing {mean, median, spread}
     */
    public static double[] mmsCourse(double[][] studentGradesArray, int course){
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




   
