let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin"
  : "./input/BOJ_1253.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = input[1].split(" ").map(Number)
  .sort((a, b) => a > b ? 1 : (a == b ? 0 : -1));
let goodCount = 0;

for (let i = 0; i < N; i++) { 
  let left = 0;
  let right = N-1;
  while (left < right) { 
    const sum = arr[left] + arr[right];
    if (sum == arr[i]) { 
      if (i != left && i != right) {
        goodCount++;
        break;
      } else { 
        if (i == left) {
          left++;
          continue;
        } else { 
          right--;
          continue;
        }
      }
    }
    if (sum > arr[i]) {
      right--;
    } else { 
      left++;
    }
  }
}
console.log(goodCount)