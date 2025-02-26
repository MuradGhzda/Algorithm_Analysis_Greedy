import java.io.*;
import java.util.*;

public class a_2022510029_2022510032_hw3 {
    private static String[][] judgeAssignments;
    private static int[] assignmentCounts;
    private static int numJudges;
    private static int minCost;
    private static String[][] bestAssignments;
    private static int[] bestAssignmentCounts;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the number of judges: "); //1
        numJudges = scanner.nextInt();//1
        System.out.print("Please enter the cost of problem type changing: ");//1
        int changeCost = scanner.nextInt();//1
        List<String> problemTypes = readTxtFile();  // 2n

        System.out.println("The problem types are listed:");//1
        System.out.println(problemTypes);//n

        judgeAssignments = new String[numJudges][problemTypes.size()];//1
        assignmentCounts = new int[numJudges];//1
        bestAssignments = new String[numJudges][problemTypes.size()];//1
        bestAssignmentCounts = new int[numJudges];//1
        minCost = Integer.MAX_VALUE;//1

        assignTypesToJudges(problemTypes, changeCost, 0, new String[numJudges], 0);  //T(n)=m*T(n-1)+mx

        System.out.println("Total cost: " + minCost);//1
        //Total T(n)= m*T(n-1)+mx+2n+c
        //Final O(n)= m*m*m=m^3    (if judges count(x) equals types count(n)

        // Uncomment to see which type was assigned to which judge
        //outputControl();
    }
    //O(n)=2n
    private static List<String> readTxtFile() throws IOException {
        List<String> problemTypes = new ArrayList<>();//1
        try (BufferedReader reader = new BufferedReader(new FileReader("input5.txt"))) {
            String line;//1
            while ((line = reader.readLine()) != null) {//n
                Scanner lineScanner = new Scanner(line);//1
                while (lineScanner.hasNext()) {//2
                    if (lineScanner.hasNextInt()) {//1
                        problemTypes.add(String.valueOf(lineScanner.nextInt()));//1
                    } else {
                        lineScanner.next();//1
                    }
                }
            }
        }
        return problemTypes;
    }
    // T(N) = O(N), where N is the total number of integers in the file
    //T(n)=m*T(n-1)+mx

    private static void assignTypesToJudges(List<String> problemTypes, int changeCost, int problemIndex, String[] lastAssignedType, int currentCost) {
        if (problemIndex == problemTypes.size()) {//1
            // Base case: end of the problem list
            if (currentCost < minCost) {//1
                minCost = currentCost;//1
                for (int i = 0; i < numJudges; i++) {//m*n
                    bestAssignmentCounts[i] = assignmentCounts[i];//1
                    for (int j = 0; j < assignmentCounts[i]; j++) {//n
                        bestAssignments[i][j] = judgeAssignments[i][j];//1
                    }
                }
            }
            return;
        }

        String type = problemTypes.get(problemIndex);//1

        for (int j = 0; j < numJudges; j++) {//m
            int cost = 0;//1
            if (lastAssignedType[j] != null && !lastAssignedType[j].equals(type)) {//1
                cost += changeCost;//1
            } else if (lastAssignedType[j] == null) {//1
                cost += changeCost;//1
            }

            judgeAssignments[j][assignmentCounts[j]] = type;//1
            assignmentCounts[j]++;//1
            String oldType = lastAssignedType[j];//1
            lastAssignedType[j] = type;//1

            assignTypesToJudges(problemTypes, changeCost, problemIndex + 1, lastAssignedType, currentCost + cost);//T/(n-1)

            lastAssignedType[j] = oldType;//1
            assignmentCounts[j]--;//1
        }
    }
    // T(numJudges, P) = O(numJudges^P), where P is the size of problemTypes

// Uncomment to see the best assignments
    private static void outputControl() {
        for (int i = 0; i < numJudges; i++) {
            System.out.print("Judge " + (i + 1) + ": ");
            for (int j = 0; j < bestAssignmentCounts[i]; j++) {
                System.out.print(bestAssignments[i][j] + (j < bestAssignmentCounts[i] - 1 ? ", " : ""));
            }
            System.out.println();
        }
    }
}