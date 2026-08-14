class Solution {
    public int maximumLengthSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int answer = 0;
        for(int right = 0; right < s.length(); right++){
         char ch = s.charAt(right);
        map.put(ch, map.getOrDefault(ch, 0)+1);
        while(map.get(ch)>2){
            char leftChar = s.charAt(left);
            map.put(leftChar, map.get(leftChar) - 1);
            left++; 
        }  
        answer = Math.max(answer, right - left + 1);
         }
         return answer;
}
}
