
class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> {
            int la = intervals.get(a).get(0);
            int lb = intervals.get(b).get(0);

            if (la != lb) {
                return Integer.compare(la, lb);
            }

            return Integer.compare(a, b);
        });

        long[][] data = new long[n][4];

        for (int i = 0; i < n; i++) {
            int idx = order[i];

            data[i][0] = intervals.get(idx).get(0);
            data[i][1] = intervals.get(idx).get(1);
            data[i][2] = intervals.get(idx).get(2);
            data[i][3] = idx;
        }

        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            long right = data[i][1];

            int lo = i + 1;
            int hi = n;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (data[mid][0] > right) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            next[i] = lo;
        }

        long[][] dp = new long[n + 1][5];

        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 4; k++) {
                long skip = dp[i + 1][k];

                long take = data[i][2] + dp[next[i]][k - 1];

                dp[i][k] = Math.max(skip, take);
            }
        }

        List<Integer> answer = new ArrayList<>();

        int i = 0;
        int remaining = 4;

        while (i < n && remaining > 0) {
            long skip = dp[i + 1][remaining];
            long take = data[i][2] + dp[next[i]][remaining - 1];

            if (take >= skip && take == dp[i][remaining]) {
                answer.add((int) data[i][3]);
                i = next[i];
                remaining--;
            } else {
                i++;
            }
        }

        Collections.sort(answer);

        int[] result = new int[answer.size()];

        for (int j = 0; j < answer.size(); j++) {
            result[j] = answer.get(j);
        }

        return result;
    }
}
