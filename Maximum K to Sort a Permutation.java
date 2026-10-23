class Solution {
    public int sortPermutation(int[] nums) {
        int k = Integer.MAX_VALUE;
        boolean sorted = true;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                sorted = false;
                k &= nums[i];
                k &= i;
            }
        }

        return sorted ? 0 : k;
    }
}
