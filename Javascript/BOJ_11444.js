const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_11444.txt'),
  output: process.stdout,
});

// 문제에서 주어질 수 있는 N의 최댓값인 1,000,000,000,000,000,000은
// Number의 안전한 정수 제한인 Number.MAX_SAFE_INTEGER을 넘어 섬
// 따라서 BigInt로 처리

let N = 0;
const DIVIDER = 1_000_000_007n, orig = [[1n, 1n], [1n, 0n]];

rl.on("line", (line) => {
  N = BigInt(line);
});

// 답 아이디어 보고 푼 문제
// 아래 유도식을 토대로 행렬 제곱을 이용해 피보나치 수를 구할 수 있음.
// [[F_n+1, F_n], [F_n, F_n-1]] = [[1, 1], [1, 0]]^n

rl.on("close", () => {
  console.log((fibonacci(N)[0][1] % DIVIDER).toString());
});

function fibonacci(n) {
  if (n === 1n) { 
    return orig;
  }
  const tmp = fibonacci(n / 2n);
  if (n % 2n === 1n) {
    return multi(multi(tmp, tmp), orig);
  } else { 
    return multi(tmp, tmp);
  }
}
function multi(left, right) { 
  const arr = new Array(2).fill().map(_ => new Array(2).fill(0n));
  for (let i = 0; i < 2; i++) { 
    for (let j = 0; j < 2; j++) { 
      for (let k = 0; k < 2; k++) { 
        arr[i][j] += left[i][k] * right[k][j];
        arr[i][j] %= DIVIDER;
      }
    }
  }
  return arr;
}