let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_9084.txt"
).toString().trim().split('\n');
const T = +input[0];
let j = 1;
while (j < input.length) { 
  const N = +input[j++];
  const splited = input[j++].split(" ").map(Number);
  const M = +input[j++];
  const dp = new Array(M + 1).fill(0);
  dp[0] = 1;
  for (let i = 0; i < splited.length; i++) { 
    for (let k = splited[i]; k <= M; k ++) { 
      dp[k] += dp[k - splited[i]];
    }
  }
  console.log(dp[M]);
}