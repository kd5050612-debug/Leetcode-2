class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int length = m + n - 1;
        if(length % 2 != 0){
            return false;
        }
        if (grid[0][0] == ')'){
            return false;
        }if (grid[m - 1][n - 1] == '('){
            return false;
        }
        boolean[][][] dp = new boolean[m][n][length+1];
        dp[0][0][1] = true;

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(i == 0 && j == 0){
                    continue;
                }
                for(int balance = 0; balance <= length; balance++){
                    boolean reachable = false;

                    if(i > 0 && dp[i - 1][j][balance]){
                        reachable = true;
                    }
                    if(j > 0 && dp[i][j-1][balance]){
                        reachable = true;
                    }if(!reachable){
                        continue;
                    }
                    int newBalance;

                    if (grid[i][j]=='('){
                        newBalance = balance + 1;

                    }else{
                        newBalance = balance - 1;
                    }
                    if(newBalance < 0){
                        continue;
                    }
                    dp[i][j][newBalance] = true;
                }
            }
        }
        return dp[m-1][n - 1][0];
    }
}
