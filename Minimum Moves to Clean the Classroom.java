class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;
        int litterCount = 0;

        int[][] litterId = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(litterId[i], -1);

            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{startR, startC, energy, 0});
        visited[startR][startC][energy][0] = true;

        int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int[] state = queue.poll();

                int r = state[0];
                int c = state[1];
                int currentEnergy = state[2];
                int mask = state[3];

                if (mask == allCollected) {
                    return moves;
                }

                if (currentEnergy == 0) {
                    continue;
                }

                for (int[] direction : directions) {
                    int nr = r + direction[0];
                    int nc = c + direction[1];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    int newEnergy = currentEnergy - 1;

                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    if (classroom[nr].charAt(nc) == 'L') {
                        int id = litterId[nr][nc];
                        newMask = mask | (1 << id);
                    }

                    if (!visited[nr][nc][newEnergy][newMask]) {
                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(new int[]{
                            nr, nc, newEnergy, newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}
