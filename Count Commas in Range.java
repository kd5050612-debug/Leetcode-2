class Solution {
    public int countCommas(int n) {
        if(n < 1000){
            return 0;
        }
        if(n < 1000000){
            return n - 999;
        }
        return (n - 999) + (n - 999999);
    }
}
