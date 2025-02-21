let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input.txt"
).toString().trim().split('\n');
const T = +input[0];
for (let i = 1; i <= T; i++) {
  const N = +input[i];
  let dp = Array(N + 1).fill(0);
  if (N >= 1) dp[1] = 1;
  if (N >= 2) dp[2] = 1;
  if (N >= 3) dp[3] = 1;
  for (let j = 2; j <= N; j++) { 
    if (j - 1 >= 0) dp[j] += dp[j - 1];
    if (j - 2 >= 0) dp[j] += dp[j - 2];
    if (j - 3 >= 0) dp[j] += dp[j - 3];
  }
  console.log(dp[N]);
}