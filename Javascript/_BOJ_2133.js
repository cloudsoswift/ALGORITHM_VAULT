const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2133.txt'),
  output: process.stdout,
})

let N = 0;
let dp = [];

rl.on("line", (line) => { 
  N = Number(line);
})

rl.on("close", () => { 
  if (N % 2 !== 0) { 
    // 벽 크기가 홀수인 경우 가능한 경우의 수가 존재하지 않음
    console.log(0);
    return;
  }
  const dp = 
})