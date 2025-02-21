const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_2470.txt"),
  output: process.stdout,
});

let ln = 0, N = 0, arr = [], left = 1_000_000_000, right = 1_000_000_000, sum = left + right;
rl.on('line', (line) => {
  switch (ln) { 
    case 0:
      N = Number(line);
      break;
    case 1:
      arr = line.split(" ").map(Number);
      arr.sort((l, r) => l > r ? 1 : (l === r ? 0 : -1));
      let l = 0, r = arr.length - 1;
      while (l < r) { 
        const s = arr[l] + arr[r];
        if (Math.abs(s) < sum) { 
          sum = Math.abs(s);
          left = arr[l];
          right = arr[r];
        }
        if (s < 0) {
          l++;
        } else { 
          r--;
        }
      }
      break;
  }
  ln++;
});

rl.on('close', () => {
  console.log(left + " " + right);
});