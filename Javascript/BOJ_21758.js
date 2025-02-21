// 답 아이디어 보고 푼 문제
const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_21758.txt"),
  output: process.stdout,
});

let ln = 0;
let arr = [], dp = [];
let N = 0, MAXIMUM = 0;
rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      N = Number(line);
      dp = new Array(N).fill(0);
      break;
    case 1:
      arr = line.split(" ").map(Number);
      dp[0] = arr[0];
      for (let i = 1; i < N; i++) {
        dp[i] = arr[i] + dp[i - 1];
      }
      break;
  }
  ln++;
});

rl.on('close', () => { 
  for (let i = 1, Nn = N - 1; i < Nn; i++) { 
    console.log("====================================================");
    // 1. 맨 왼쪽에 벌, 맨 오른쪽에 벌통이 있고, 가운데 벌만 이동하는 케이스
    // console.log(`벌[0], 벌[${i}], 벌통[${Nn}]`);
    // console.log(dp[Nn] * 2 - dp[0] - dp[i] - arr[i]);
    MAXIMUM = Math.max(MAXIMUM, dp[Nn] * 2 - dp[0] - dp[i] - arr[i]);
    // 2. 맨 왼쪽에 벌통, 맨 오른쪽에 벌이 있고, 가운데 벌만 이동하는 케이스
    // console.log(`벌통[0], 벌[${i}], 벌[${Nn}]`);
    // console.log(dp[Nn] + dp[i] - arr[i]*2 - arr[Nn]);
    MAXIMUM = Math.max(MAXIMUM, dp[Nn] + dp[i] - arr[i] * 2 - arr[Nn]);
    // 3. 맨 왼쪽에 벌, 맨 오른쪽에 벌이 있고, 가운데 벌통만 이동하는 케이스
    // console.log(`벌[0], 벌통[${i}], 벌[${Nn}]`);
    // console.log(dp[Nn] - arr[0] + arr[i] - arr[Nn]);
    MAXIMUM = Math.max(MAXIMUM, dp[Nn] - arr[0] + arr[i] - arr[Nn]);
  }
  console.log(MAXIMUM);
})