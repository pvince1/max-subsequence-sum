/*
COMP285: Proj1 2/26
Author: (Jacob) Powell Vince
Email: jvince@sandiego.edu
Description: Program takes in an input text file with a sequence of numbers, runs
either one of or all four algorithms to find the maximum subsequence sum, and returns
the algorithm runtime(s) and max subsequence sum.
*/

import java.util.*;
//import java.io.*;
//import java.lang.*;

public class Proj1 {

  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {

    while(true) {
      // prompt user for txt file
      System.out.print("\nEnter input text file name: ");
      // read command line user input
      String inputText = scanner.nextLine();
      // read in filename
      File inputFile = new File(inputText);

      if (inputFile.canRead()) {
        // print out algorithm menu
        System.out.print("Choose MSS Algorithm:\n"+
        "1) 3 nested loops sum all possible subsequences - O(n^3)\n"+
        "2) 2 nested loops sum all possible subsequences - O(n^2)\n"+
        "3) Recursive calls sum all possible subsequences - O(nlogn)\n"+
        "4) 1 loop sums all possible subsequences - O(n)\n"+
        "5) Run all algorithms\n\n"+
        "Enter Option: ");
        int inputAlg = Integer.parseInt(scanner.nextLine()); //save algorithm choice
        // readFileIntoArray takes in file, returns int array
        int[] numberSequence = readFileIntoArray(inputFile);
        System.out.printf("Sequence Size: %d\n", numberSequence.length);
        // case statement for algorithm choice
        if (numberSequence != null) {
          switch (inputAlg) {
            case 1: int maxSum1 = MSSAlgorithm1(numberSequence);
                    break;
            case 2: int maxSum2 = MSSAlgorithm2(numberSequence);
                    break;
            case 3: int maxSum3 = MSSAlgorithm3(numberSequence);
                    break;
            case 4: int maxSum4 = MSSAlgorithm4(numberSequence);
                    break;
            case 5: int[] maxSums = new int[4];
                    maxSums[0] = MSSAlgorithm1(numberSequence);
                    maxSums[1] = MSSAlgorithm2(numberSequence);
                    maxSums[2] = MSSAlgorithm3(numberSequence);
                    maxSums[3] = MSSAlgorithm4(numberSequence);
                    break;
            default:
                    break;
          }
        }
      }
      else {
        System.out.println("Unable to read input text file\n");
      }
    }
  }

  public static int[] readFileIntoArray(File inputFile) {
    //try to read in file (entire number sequence should be one line)
    try {
      FileReader fileReader = new FileReader(inputFile);

      // wrap FileReader in BufferedReader
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      if (line != null) {
        String[] stringSequence = line.split("\\ "); //split string into string array
        int[] numberSequence = new int[stringSequence.length];
        for (int i = 0; i < stringSequence.length; i++) {
          numberSequence[i] = Integer.parseInt(stringSequence[i]); //set int array
        }
        return numberSequence;
      }

    }
    //necessary catch exceptions (already asserted inputFile.canRead()==true)
    catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + inputFile + "'");
    }
    catch(IOException ex) {
        System.out.println("Error reading file '" + inputFile + "'");
    }
    return null;
  }

  public static int MSSAlgorithm1(int[] numberSequence) {
    long startTime = System.nanoTime(); //start timer
    int sequenceLength = numberSequence.length;
    int maxSum = 0;

    // O(n^3)
    for (int i = 0; i < sequenceLength; i++) { //loop from 0 to n
      if (numberSequence[i]>0) { //don't start sequence on negative number
        for (int j = i; j < sequenceLength; j++) { // loop from i to n
          if (numberSequence[j]>0) { //don't end sequence on negative number
            int sum = 0;
            for (int k = i; k <= j; k++){ //loop from i to j
              sum += numberSequence[k];
            }
            if (sum > maxSum)
              maxSum = sum;
          }
        }
      }
    }
    long endTime = System.nanoTime() - startTime; //end timer
    System.out.printf("Algorithm1 Time: %d\t MaxSum: %d\n", endTime, maxSum);
    return maxSum;
  }

  public static int MSSAlgorithm2(int[] numberSequence) {
    long startTime = System.nanoTime(); //start timer
    int sequenceLength = numberSequence.length;
    int maxSum = 0;

    // O(n^2)
    for (int i = 0; i < sequenceLength; i++) { //loop from 0 to n
      if (numberSequence[i]>0) { //don't start sequence on negative number
        int sum = 0;
        for (int j = i; j < sequenceLength; j++) { //loop from i to n
              sum += numberSequence[j];
              if (sum > maxSum)
                maxSum = sum;
        }
      }
    }
    long endTime = System.nanoTime() - startTime; //end timer
    System.out.printf("Algorithm2 Time: %d\t MaxSum: %d\n", endTime, maxSum);
    return maxSum;
  }

  public static int MSSAlgorithm3(int[] numberSequence) {
    long startTime = System.nanoTime(); //start timer
    //call to recursive function O(nlogn)
    int max = maxSumRec(numberSequence, 0, numberSequence.length-1);
    long endTime = System.nanoTime() - startTime; //end timer
    System.out.printf("Algorithm3 Time: %d\t MaxSum: %d\n", endTime, max);
    return max;
  }

  private static int maxSumRec(int[] numberSequence, int low, int high) {
    //base case; right = left
    if (low == high) {
      if (numberSequence[low] > 0)
        return numberSequence[low];
      else
        return 0;
    }

    int middle = (high+low)/2;
    int maxLeftSum = maxSumRec(numberSequence, low, middle); //recursive call for left half
    int maxRightSum = maxSumRec(numberSequence, middle+1, high); //recursive call for right half

    int maxLeftBoundSum=0,maxRightBoundSum=0,sum=0;
    //loop finding max subsequence from middle to left
    for (int i=middle; i>=low; i--) {
      sum += numberSequence[i];
      if (sum > maxLeftBoundSum)
        maxLeftBoundSum = sum;
    }
    sum = 0;
    //loop finding max subsequence from middle to right
    for (int j=middle+1; j<=high; j++) {
      sum += numberSequence[j];
      if (sum > maxRightBoundSum)
        maxRightBoundSum = sum;
    }
    //return max from recursive calls, or max from whole sequence
    return max3(maxLeftSum, maxRightSum, maxLeftBoundSum + maxRightBoundSum);
  }

  //returns the max of three ints for use in maxSumRex()
  public static int max3(int x, int y, int z) {
    if (x>=y && x>=z)
      return x;
    else if (y>=z)
      return y;
    else
      return z;
  }

  public static int MSSAlgorithm4(int[] numberSequence) {
    long startTime = System.nanoTime(); //start timer
    int sum = 0, maxSum = 0;
    //loop through numberSequence once, finding max subsequence O(n)
    for (int i = 0; i < numberSequence.length; i++) {
      sum += numberSequence[i];
      if (sum>maxSum)
        maxSum = sum;
      else if (sum < 0)
        sum = 0;
    }
    long endTime = System.nanoTime() - startTime; //end timer
    System.out.printf("Algorithm4 Time: %d\t MaxSum: %d\n", endTime, maxSum);
    return maxSum;
  }

}
