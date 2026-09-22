class Solution {
    int n, k;
    Node[] tree;

    class Node {
        int prod;
        int[] pref;

        Node() {
            pref = new int[k];
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            update(1, 0, n - 1, index, value % k);

            Node result = query(1, 0, n - 1, start, n - 1);

            answer[q] = result.pref[x];
        }

        return answer;
    }

    void build(int node, int left, int right, int[] nums) {
        if (left == right) {
            tree[node] = new Node();

            int rem = nums[left] % k;

            tree[node].prod = rem;
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int left, int right, int index, int value) {
        if (left == right) {
            tree[node] = new Node();

            tree[node].prod = value;
            tree[node].pref[value] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node query(int node, int left, int right, int ql, int qr) {
        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, right, ql, qr);
        }

        Node a = query(node * 2, left, mid, ql, qr);
        Node b = query(node * 2 + 1, mid + 1, right, ql, qr);

        return merge(a, b);
    }

    Node merge(Node a, Node b) {
        Node result = new Node();

        result.prod = (a.prod * b.prod) % k;

        for (int r = 0; r < k; r++) {
            result.pref[r] = a.pref[r];
        }

        for (int r = 0; r < k; r++) {
            int newRemainder = (a.prod * r) % k;
            result.pref[newRemainder] += b.pref[r];
        }

        return result;
    }
}
