const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1477.txt'),
  output: process.stdout,
})

let ln = 0, M = 0, N = 0, L = 0;
let arr = [];
rl.on('line', (line) => { 
  if (ln === 0) {
    [N, M, L] = line.split(" ").map(Number);
    // N은 0 이상 50 이하이므로 휴게소 입력이 주어지지 않을 수 있음
    if (N === 0) arr = [0, L];
  } else { 
    arr = line.split(" ").map(Number);
    arr.sort((a, b) => a > b ? 1 : (a === b ? 0 : -11));
    arr = [0, ...arr, L];
  }
  ln++;
})

rl.on('close', () => {
  let low = 0, max = L, less = L;
  loop:
  while (low <= max) { 
    let count = M;
    let before = 0;
    const mid = Math.floor((low + max) / 2);
    for (let i = 0; i < arr.length; i++) { 
      if (before + mid < arr[i]) {
        while (before + mid < arr[i] && count > 0) { 
          count--;
          before += mid;
        }
        if (before + mid < arr[i]) { 
          low = mid + 1;
          continue loop;
        }
        if (before < arr[i]) before = arr[i];
        if (count < 0) {
          low = mid + 1;
          continue loop;
        }
        
      } else { 
        before = arr[i];
      }
    }
    if (mid < less) {
      less = mid;
      max = mid - 1;
    }
  }
  console.log(less);
});