class Solution {
    public int numberOfSets(int n, int k) {
        long MOD = 1000000007;

        long[][] dp = new long[n + k][2 * k + 1];

        dp[0][0] = 1;

        for (int i = 1; i < n + k; i++) {
            dp[i][0] = 1;

            for (int j = 1; j <= 2 * k; j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i - 1][j - 1]) % MOD;
            }
        }

        return (int) dp[n + k - 1][2 * k];
    }
}
