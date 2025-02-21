const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_15989.txt"
).toString().trim().split('\n');
const N = +input[0];
for (let i = 0; i < N; i++) { 
  const goal = +input[i + 1];
  const dp = new Array(goal + 1).fill(0);
  dp[0] = 1;
  for (let j = 1; j <= 3; j++) { 
    for (let k = j; k <= goal; k++) { 
      dp[k] += dp[k - j];
    }
  }
  console.log(dp[goal]);
}