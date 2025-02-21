let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2295.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = new Array(N);
const map = new Map();
for (let i = 1; i <= N; i++) { 
  arr[i - 1] = +input[i];
}
let MAX = 0;
for (let i = 0; i < N; i++) { 
  for (let j = 0; j < N; j++) { 
    for (let k = j + 1; k < N; k++) { 
      const sum = arr[i] + arr[j] + arr[k];
      if (map.get(sum) === true && MAX < sum) { 
        MAX = sum;
      }
    }
  }
}
console.log(MAX);