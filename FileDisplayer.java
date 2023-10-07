import java.io.File;
import java.util.Scanner;
//test??
public class FileDisplayer {

    public static void main(String[] args) {

        try {
        	// Adapt this when you want to read and display a different file.
            String fileName = "CurrentGrades.csv";
            File file=new File(fileName);
            
            // This code uses two Scanners, one which scans the file line per line
            Scanner fileScanner = new Scanner(file);

            int linesDone = 0;
            
            while (fileScanner.hasNextLine() && linesDone < 25) {

            	String line = fileScanner.nextLine();
            	linesDone++;
            	// and one that scans the line entry per entry using the commas as delimiters
            	Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
            	while (lineScanner.hasNext()) {
            		// Separate commands can be used depending on the types of the entries
            		// (i) and (s) are added to the printout to show how each entry is recognized
            		if (lineScanner.hasNextInt()) {
            			int i = lineScanner.nextInt();
            			System.out.print("(i)" + i + " ");
            		} else if (lineScanner.hasNextDouble()) {
            			double d = lineScanner.nextDouble();
            			System.out.print("(d)" + d + " ");
            		} else {
            			String s = lineScanner.next();
            			System.out.print("(s)" + s + " ");
            		}
            	}
            
            	lineScanner.close();
            	System.out.println();
            }
            
            fileScanner.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
