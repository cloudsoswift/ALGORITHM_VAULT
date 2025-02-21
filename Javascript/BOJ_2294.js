const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2294.txt"
).toString().trim().split('\n').map(str => str.trim());
const [n, k] = input[0].split(" ").map(Number);
const dp = new Array(k + 1).fill(100_000_000);
const coins = new Array(n).fill(0);
for (let i = 0; i < n; i++) { 
  coins[i] = +input[i + 1];
}
dp[0] = 0;
for (let i = 1; i <= k; i++) { 
  for (let j = 0; j < n; j++) { 
    if (i - coins[j] < 0) continue;
    if (dp[i] <= dp[i - coins[j]] + 1) continue;
    dp[i] = dp[i - coins[j]] + 1;
  }
}
console.log(dp[k] === 100_000_000 ? -1 : dp[k]);