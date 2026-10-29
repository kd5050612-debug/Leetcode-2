
import java.util.*;

class Solution {
    private int LOG;
    private int[][] up;
    private int[] depth, tin, tout, parent, prefixMask;
    private int[] bit;
    private int timer;
    private List<Integer>[] adj;
    private char[] chars;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        chars = s.toCharArray();
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        parent = new int[n];
        prefixMask = new int[n];

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];

        buildTree(n);

        bit = new int[n + 2];

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] p = query.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int delta = (1 << (chars[u] - 'a')) ^ (1 << (c - 'a'));

                if (delta != 0) {
                    update(tin[u] + 1, delta);
                    update(tout[u] + 2, delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int lca = getLCA(u, v);

                int maskU = prefixMask[u] ^ queryBIT(tin[u] + 1);
                int maskV = prefixMask[v] ^ queryBIT(tin[v] + 1);

                int mask = maskU ^ maskV ^ (1 << (chars[lca] - 'a'));

                answer.add(Integer.bitCount(mask) <= 1);
            }
        }

        return answer;
    }

    private void buildTree(int n) {
        int[] stack = new int[n];
        int[] order = new int[n];
        int[] size = new int[n];

        Arrays.fill(parent, -1);

        int top = 0;
        int count = 0;
        timer = 0;

        stack[top++] = 0;
        parent[0] = 0;
        prefixMask[0] = 1 << (chars[0] - 'a');

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer++;
            order[count++] = u;

            for (int v : adj[u]) {
                if (parent[v] != -1) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                prefixMask[v] = prefixMask[u] ^ (1 << (chars[v] - 'a'));
                stack[top++] = v;
            }
        }

        Arrays.fill(size, 1);

        for (int i = count - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
        }

        for (int u = 0; u < n; u++) {
            up[0][u] = parent[u];
        }

        for (int k = 1; k < LOG; k++) {
            for (int u = 0; u < n; u++) {
                up[k][u] = up[k - 1][up[k - 1][u]];
            }
        }
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return parent[u];
    }

    private void update(int index, int delta) {
        while (index < bit.length) {
            bit[index] ^= delta;
            index += index & -index;
        }
    }

    private int queryBIT(int index) {
        int result = 0;

        while (index > 0) {
            result ^= bit[index];
            index -= index & -index;
        }

        return result;
    }
}
