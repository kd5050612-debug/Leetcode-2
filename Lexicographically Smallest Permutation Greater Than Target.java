import java.util.Arrays;

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        boolean[] validPrefix = new boolean[n + 1];
        validPrefix[0] = true;

        int[] tempFreq = freq.clone();
        for (int i = 0; i < n; i++) {
            int c = target.charAt(i) - 'a';
            if (tempFreq[c] > 0) {
                tempFreq[c]--;
                validPrefix[i + 1] = true;
            } else {
                break;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (!validPrefix[i]) {
                continue;
            }

            int[] remFreq = freq.clone();
            for (int j = 0; j < i; j++) {
                remFreq[target.charAt(j) - 'a']--;
            }

            int targetChar = target.charAt(i) - 'a';
            int chosenChar = -1;

            for (int c = targetChar + 1; c < 26; c++) {
                if (remFreq[c] > 0) {
                    chosenChar = c;
                    break;
                }
            }

            if (chosenChar != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(target, 0, i);
                sb.append((char) ('a' + chosenChar));
                remFreq[chosenChar]--;

                for (int c = 0; c < 26; c++) {
                    while (remFreq[c] > 0) {
                        sb.append((char) ('a' + c));
                        remFreq[c]--;
                    }
                }

                return sb.toString();
            }
        }

        return "";
    }
}
