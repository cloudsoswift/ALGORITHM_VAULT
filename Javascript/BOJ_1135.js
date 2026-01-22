// 문제 아이디어 보고 푼 문제

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1135.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, tree = [];
let answer = 0;

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    tree = Array.from({ length: N }, () => []);
  } else { 
    const split = line.split(" ").map(Number);
    for (let i = 1; i < N; i++) { 
      tree[split[i]].push(i);
    }
  }
  ln++;
});

rl.on("close", () => {
  console.log(search(0));
});

function search(now) {
  if (tree[now].length === 0) { 
    return 0;
  }
  const arr = [];
  for (const ff of tree[now]) { 
    arr.push(search(ff));
  }
  arr.sort((a, b) => b - a);
  let rtn = 0;
  for (let i = 0, len = arr.length; i < len; i++) { 
    rtn = Math.max(rtn, i + 1 + arr[i]);
  }
  return rtn;
}