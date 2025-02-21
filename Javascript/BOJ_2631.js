let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2631.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = new Array(N).fill(0);
const LIS = new Array(N+1).fill(0);
let LIS_len = 0;
for (let i = 0; i < N; i++) {
  arr[i] = +input[i + 1];
  if (arr[i] > LIS[LIS_len]) {
    LIS[++LIS_len] = arr[i];
  } else { 
    let left = 1, right = LIS_len;
    while (left < right) { 
      const mid = Math.floor((left + right) / 2);
      if (arr[i] > LIS[mid]) {
        left = mid + 1;
      } else { 
        right = mid;
      }
    }
    LIS[left] = arr[i];
  }
}
console.log(N - LIS_len);