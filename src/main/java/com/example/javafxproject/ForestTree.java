import java.util.HashSet;
import java.util.Set;

public class ForestTree extends BinaryDecisionTree{
    private int numOfAttributes;

    public ForestTree(int minimumSplit, int maxDepth, int numOfAttributes) {
        super(minimumSplit, maxDepth);
        this.numOfAttributes = numOfAttributes;
        //TODO Auto-generated constructor stub
    }
    @Override
    public String [][][] getBestSplit(String[][] specificCourseArray, int numSamples, int numFeatures) {
        String [][][] bestSplit = new String[3][][];
        double maxVarRed = Double.MIN_VALUE;

        // instead of looping over all features select a few random
        Set<Integer> randomAttribute = new HashSet<>();
        while(randomAttribute.size()<numOfAttributes){
            randomAttribute.add((int)(Math.random()*numFeatures));
        }
        

        for (int featureIndex : randomAttribute) {
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
}
