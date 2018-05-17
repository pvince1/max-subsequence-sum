# max-subsequence-sum
Java Program computes the maximum subsequence sum within a sequence of integers using four different algorithms with varied complexities

Files:
README.md
maxSubseqSum.java

Maximum Subsequence Sum Program takes in a sequence of integers as a text file and measures the time each algorithm takes to compute the maximum subsequence sum.

Algorithm 1 - Algorithm 1 contains three for loops that move through the number sequence array.  
The first iterator (i) starts at the beginning of the array and increments once the second iterator (j)
reaches the end of the array.  The third iterator (k) sums the values between the first (i) and second (j)
iterators and tests whether the sum of values in the subsequence is greater than the current maximum sum. 
With the three for loops iterating through n and subsequences of n, the complexity of Algorithm 1 is O(n^3). 
Algorithm 1 is the least efficient algorithm.

Algorithm 2 - Algorithm 2 is similar to Algorithm 1, but cuts down on one of the for loops. 
In place of the third iterator (k), Algorithm 2 simply adds the sequence values to the sum and compares it to 
the maximum sum as the second iterator (j) moves through the array.  With only two loops iterating through n 
and subsequences of n, the complexity of Algorithm 2 is O(n^2). Algorithm 2 is more efficient than Algorithm 1, 
but less efficient than Algorithm 3 (depending on n).

Algorithm 3 - Algorithm 3 uses a “divide & conquer” method by partitioning the array into two equal halves and 
making recursive calls with the two halves, in time, reaching the base case where the two halves are the same element. 
Additionally, Algorithm 3 loops through each half of the elements to find their respective maximums in case the 
maximum subsequence lies between them.  With recursive calls of n/2 elements, the complexity of Algorithm 3 is O(nlogn). 
Algorithm 3 is more efficient than 1 or 2, but less efficient than 4.

Algorithm 4 - Algorithm 4 uses only loop to iterate through the array.  
Instead of using two or three iterators like Algorithms 1 or 2, Algorithm 4 adds each element to its running sum, 
checks whether the sum is greater than the current maximum, 
resets the sum to 0 if the sum is less than 0 (effectively moving the subsequence start to the next value). 
Algorithm 4 is the most efficient algorithm with a complexity of O(n).
