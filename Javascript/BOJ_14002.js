const fs = require("fs");
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_14002.txt")
  .toString().trim().split("\n");
const N = +input[0];
const splitted = input[1].split(" ");
const dp = Array(N+1).fill(0);
const dpPos = Array(N).fill(0);
const arr = Array(N).fill(0);
let maxLength = 0;
for (let i = 0; i < N; i++) { 
  const inputNum = +splitted[i];
  arr[i] = inputNum;
  if (maxLength === 0 || dp[maxLength - 1] < inputNum) {
    dpPos[i] = maxLength;
    dp[maxLength++] = inputNum;
  } else { 
    let left = 0;
    let right = maxLength-1;
    while (left < right) { 
      const mid = Math.floor((left + right) / 2);
      if (dp[mid] < inputNum) {
        left = mid + 1;
      } else { 
        right = mid;
      }
    }
    dp[left] = inputNum;
    dpPos[i] = left;
  }
}
dp.fill(100_000_000);
for (let i = N - 1; i >= 0; i--) { 
  const pos = dpPos[i];
  if (dp[pos+1] > arr[i]) { 
    dp[pos] = arr[i];
  }
}
console.log(maxLength);
console.log(dp.slice(0, maxLength).join(" "));