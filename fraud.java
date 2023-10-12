public static void cheatingStudents(double[][] studentGradesArray, double gpaThreshold, int occurrenceThreshold) {
    int numStudents = studentGradesArray.length;
    int numCourses = studentGradesArray[0].length;

    // Create an array to store GPAs for all students
    double[] gpas = new double[numStudents];

    // Calculate GPAs for all students
    for (int i = 0; i < numStudents; i++) {
        double[] grades = studentGradesArray[i];
        double gpa = calculateGPA(grades);
        gpas[i] = gpa;
    }

    // Count the occurrences of students with GPAs close to each other
    int occurrenceCount = 0;

    for (int i = 0; i < numStudents - 1; i++) {
        double gpa1 = gpas[i];
        for (int j = i + 1; j < numStudents; j++) {
            double gpa2 = gpas[j];

            // Check if the GPAs are within the specified threshold
            if (Math.abs(gpa1 - gpa2) <= gpaThreshold) {
                occurrenceCount++;
                System.out.println("Potential cheating: Student " + (i + 1) + " (GPA: " + gpa1 +
                        ") and Student " + (j + 1) + " (GPA: " + gpa2 + ")");
            }
        }
    }

    // Trigger an investigation if the occurrence count exceeds the threshold
    if (occurrenceCount >= occurrenceThreshold) {
        System.out.println("Potential cheating detected. Initiate an investigation.");
    }
}

public static double calculateGPA(double[] grades) {
    // Calculate GPA based on your criteria
    // Here, we'll assume a simple average of grades (ignoring missing values)
    double sum = 0.0;
    int count = 0;

    for (double grade : grades) {
        if (grade >= 0.0) {
            sum += grade;
            count++;
        }
    }

    return count > 0 ? sum / count : 0.0; // Avoid division by zero
}