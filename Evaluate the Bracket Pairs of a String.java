
class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();

        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder answer = new StringBuilder();

        int i = 0;

        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                int j = i + 1;

                while (j < s.length() && s.charAt(j) != ')') {
                    j++;
                }

                String key = s.substring(i + 1, j);

                if (map.containsKey(key)) {
                    answer.append(map.get(key));
                } else {
                    answer.append('?');
                }

                i = j + 1;
            } else {
                answer.append(s.charAt(i));
                i++;
            }
        }

        return answer.toString();
    }
}
