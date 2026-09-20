class Solution {
    public int reverseDegree(String s) {
        int answer = 0;

        for(int i = 0; i<s.length(); i++){
            int reversePosition = 26 - (s.charAt(i) - 'a');
            answer += reversePosition * (i + 1);


        }
        return answer;
    }
}
