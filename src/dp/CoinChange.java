package dp;

/*
    https://leetcode.com/problems/coin-change/
 */
public class CoinChange {

    /* f(n) -> minimum no of coins required to form sum = n
        Before taking a coin -> check the remaining sum is possible or not
        If sum is not possible -> will have Integer.MAX_VALUE */

    //TC -> O(n*k) SC -> O(N)
    public int coinChange(int[] coins, int amount) {
        int[] amounts = new int[amount + 1];
        amounts[0] = 0;
        for (int i = 1; i <= amount; i++) {
            amounts[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin < 0 || amounts[i - coin] == Integer.MAX_VALUE)
                    continue;
                amounts[i] = Math.min(amounts[i], 1 + amounts[i - coin]);
            }
        }
        return amounts[amount] == Integer.MAX_VALUE ? -1 : amounts[amount];
    }


    public int coinChangePath(int[] coins, int amount) {
        int[] amounts = new int[amount + 1];
        amounts[0] = 0;
        int[] coinPath = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            amounts[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin < 0 || amounts[i - coin] == Integer.MAX_VALUE)
                    continue;

                if ((1 + amounts[i - coin]) < amounts[i]) {
                    amounts[i] = 1 + amounts[i - coin];
                    coinPath[i] = coin;
                }
            }
        }

        if (amounts[amount] == Integer.MAX_VALUE)
            return -1;

        int tmp = amount;
        while (tmp > 0) {
            System.out.print(coinPath[tmp] + " ");
            tmp = tmp - coinPath[tmp];
        }
        System.out.println();
        return amounts[amount];
    }


    public static void main(String[] args) {
        CoinChange coinChange = new CoinChange();

        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        System.out.println(coinChange.coinChangePath(coins, amount)); //3 (11 = 5 + 5 + 1)

        coins = new int[]{2};
        amount = 3;
        System.out.println(coinChange.coinChange(coins, amount)); //-1

        coins = new int[]{1};
        amount = 0;
        System.out.println(coinChange.coinChange(coins, amount)); //0

        coins = new int[]{1};
        amount = 1;
        System.out.println(coinChange.coinChange(coins, amount)); //1

        coins = new int[]{1};
        amount = 2;
        System.out.println(coinChange.coinChange(coins, amount)); //2

    }
}
