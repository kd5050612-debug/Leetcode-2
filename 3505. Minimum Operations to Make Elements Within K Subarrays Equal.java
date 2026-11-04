
class Solution {

    class SlidingMedian {
        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();

        int lowSize = 0;
        int highSize = 0;

        long lowSum = 0;
        long highSum = 0;

        void add(int num) {
            if (low.isEmpty() || num <= low.lastKey()) {
                low.put(num, low.getOrDefault(num, 0) + 1);
                lowSize++;
                lowSum += num;
            } else {
                high.put(num, high.getOrDefault(num, 0) + 1);
                highSize++;
                highSum += num;
            }

            balance();
        }

        void remove(int num) {
            if (low.containsKey(num)) {
                removeValue(low, num);
                lowSize--;
                lowSum -= num;
            } else {
                removeValue(high, num);
                highSize--;
                highSum -= num;
            }

            balance();
        }

        void removeValue(TreeMap<Integer, Integer> map, int num) {
            int count = map.get(num);

            if (count == 1) {
                map.remove(num);
            } else {
                map.put(num, count - 1);
            }
        }

        void balance() {
            int target = (lowSize + highSize + 1) / 2;

            while (lowSize > target) {
                int num = low.lastKey();

                removeValue(low, num);
                lowSize--;
                lowSum -= num;

                high.put(num, high.getOrDefault(num, 0) + 1);
                highSize++;
                highSum += num;
            }

            while (lowSize < target) {
                int num = high.firstKey();

                removeValue(high, num);
                highSize--;
                highSum -= num;

                low.put(num, low.getOrDefault(num, 0) + 1);
                lowSize++;
                lowSum += num;
            }
        }

        long getCost() {
            long median = low.lastKey();

            return median * lowSize - lowSum
                    + highSum - median * highSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;
        int windows = n - x + 1;

        long[] cost = new long[windows];

        SlidingMedian sm = new SlidingMedian();

        // Calculate the first window
        for (int i = 0; i < x; i++) {
            sm.add(nums[i]);
        }

        cost[0] = sm.getCost();

        // Calculate remaining sliding windows
        for (int i = 1; i < windows; i++) {
            sm.remove(nums[i - 1]);
            sm.add(nums[i + x - 1]);

            cost[i] = sm.getCost();
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        // Zero selected windows require zero operations
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {

                // Skip the current element
                dp[j][i] = dp[j][i - 1];

                // Select a window ending at i - 1
                if (i >= x && dp[j - 1][i - x] != INF) {

                    long take = dp[j - 1][i - x]
                            + cost[i - x];

                    dp[j][i] = Math.min(dp[j][i], take);
                }
            }
        }

        return dp[k][n];
    }
}
