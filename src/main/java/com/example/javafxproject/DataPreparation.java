

import java.util.Arrays;

public class DataPreparation {

    public static int[] getTrainingIndexes(double[][] gradesArray, int course){
        int[] indexes = new int[gradesArray.length];
        int counter=0;
        for(int i=0; i<gradesArray.length; i++){
            if(gradesArray[i]!=null && gradesArray[i][course]>0){
                indexes[counter] = i;
                counter++;
            }
        }
        return Arrays.copyOf(indexes, (int)(counter*0.85));
    }
    public static int[] getTestIndexes(double[][] gradesArray, int course){
        int[] indexes = new int[gradesArray.length];
        int counter=0;
        for(int i=0; i<gradesArray.length; i++){
            if(gradesArray[i]!=null && gradesArray[i][course]>0){
                indexes[counter] = i;
                counter++;
            }
        }
        return Arrays.copyOfRange(indexes,(int)(counter*0.85), indexes.length-1);
    }

    /** bootstrapping method
     * @param courseID used to prevent using student with an NG for that course in the samples
     * @param studentArray array containing grades for the students
     * @param sampleSize defines the how samples are in the returned array
     * @return set of ID's which contain the coursew
    */
    public static int[] genBootstrapIndexes(int courseIndex, double[][] array, int size){
        if (array.length==0) {
            return null;
        }
        int[] indexArray = new int[size];

        int counter = 0;
        int attempts = 0;
        while (counter<size && attempts<size*100) {
            int random = (int) (Math.random()*array.length);
            if(array[random][courseIndex]>0){
                indexArray[counter] = random;
                counter++;
            }
            attempts++;
        }
        Arrays.sort(indexArray);
        return indexArray;
    }
}
