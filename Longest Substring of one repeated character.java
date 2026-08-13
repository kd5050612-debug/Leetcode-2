class Solution {

    static class Node {
        int len;
        int leftChar;
        int rightChar;
        int prefix;
        int suffix;
        int best;

        Node() {
        }

        Node(char c) {
            len = 1;
            leftChar = c - 'a';
            rightChar = c - 'a';
            prefix = 1;
            suffix = 1;
            best = 1;
        }
    }

    Node[] tree;
    char[] s;

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices) {

        this.s = s.toCharArray();

        int n = s.length();
        int k = queryIndices.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            this.s[index] = ch;

            update(1, 0, n - 1, index, ch);

            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int node, int start, int end) {

        if (start == end) {
            tree[node] = new Node(s[start]);
            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
            int node,
            int start,
            int end,
            int index,
            char ch) {

        if (start == end) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(node * 2, start, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, end, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }
    private Node merge(Node a, Node b) {

        Node res = new Node();

        res.len = a.len + b.len;

        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.prefix = a.prefix;
        res.suffix = b.suffix;

        // Entire left segment has same character
        if (a.prefix == a.len && a.rightChar == b.leftChar) {
            res.prefix = a.len + b.prefix;
        }

        // Entire right segment has same character
        if (b.suffix == b.len && a.rightChar == b.leftChar) {
            res.suffix = b.len + a.suffix;
        }

        res.best = Math.max(a.best, b.best);

        // Join suffix of left + prefix of right
        if (a.rightChar == b.leftChar) {
            res.best = Math.max(
                    res.best,
                    a.suffix + b.prefix
            );
        }

        return res;
    }
}
