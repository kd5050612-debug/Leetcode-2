
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] freq = new int[100001];
        long operations = (long) k1 + k2;
        int max = 0;

        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            freq[diff]++;
            max = Math.max(max, diff);
        }

        while (operations > 0 && max > 0) {
            if (freq[max] <= operations) {
                operations -= freq[max];
                freq[max - 1] += freq[max];
                freq[max] = 0;
                max--;
            } else {
                int reduce = (int) operations;
                freq[max] -= reduce;
                freq[max - 1] += reduce;
                operations = 0;
            }
        }

        long answer = 0;

        for (int i = 1; i <= max; i++) {
            answer += (long) i * i * freq[i];
        }

        return answer;
    }
}
