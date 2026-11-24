class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 1;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            long gcd = 0;
            long lcm = 1;

            for (int j = i; j < n; j++) {
                prod *= nums[j];
                gcd = gcd(gcd, nums[j]);
                lcm = (lcm / gcd(lcm, nums[j])) * nums[j];

                if (prod == gcd * lcm) {
                    ans = Math.max(ans, j - i + 1);
                }

                if (prod > 1000000000L) break;
            }
        }
        return ans;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
