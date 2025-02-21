let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input.txt"
).toString().trim().split('\n');
const N = Number(input[0]);
let dp = Array(N + 2).fill(0);
let MAX = 0;
let leastMAX = 0;
for (let i = 1; i <= N; i++) { 
  const splitted = input[i]?.split(" ") || [];
  const cost = +splitted[0];
  const earning = +splitted[1];
  if (dp[i] > leastMAX) leastMAX = dp[i];
  if (i + cost <= N + 1) {
    dp[i + cost] = Math.max(dp[i+cost], leastMAX + earning);
    if (dp[i + cost] > MAX) MAX = dp[i + cost];
  };
}
console.log(MAX);