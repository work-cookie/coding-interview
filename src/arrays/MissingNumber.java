package arrays;

/* https://leetcode.com/problems/missing-number */

public class MissingNumber {

    // TC -> O(n) SC -> O(1)
    public static int missingNumber(int[] nums) {
        int sum = 0;
        for (int no : nums)
            sum += no;
        int expected = (nums.length * (nums.length + 1)) >> 1;
        return expected - sum;
    }

    public static void main(String[] args) {
        System.out.println(missingNumber(new int[]{0})); // 1
        System.out.println(missingNumber(new int[]{1})); // 0
        System.out.println(missingNumber(new int[]{1, 3, 0})); // 2
    }

}
