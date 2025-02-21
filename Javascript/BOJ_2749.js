let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2749.txt"
).toString().trim().split('\n');
const DIVIDER = 1_000_000;
// 문제에서 주어질 수 있는 N의 최댓값인 1,000,000,000,000,000,000은
// Number의 안전한 정수 제한인 Number.MAX_SAFE_INTEGER을 넘어 섬
// 따라서 BigInt로 처리
const N = BigInt(input[0]); 
const orig = [[1, 1], [1, 0]];

// 답 아이디어 보고 푼 문제
// 아래 유도식을 토대로 행렬 제곱을 이용해 피보나치 수를 구할 수 있음.
// [[F_n+1, F_n], [F_n, F_n-1]] = [[1, 1], [1, 0]]^n
console.log(fibonacci(N)[0][1] % DIVIDER);

function fibonacci(n) {
  if (n === 1n) { 
    return orig;
  }
  console.log(n);
  const tmp = fibonacci(n / 2n);
  if (n % 2n === 1n) {
    return multi(multi(tmp, tmp), orig);
  } else { 
    return multi(tmp, tmp);
  }
}
function multi(left, right) { 
  const arr = new Array(2).fill().map(_ => new Array(2).fill(0));
  for (let i = 0; i < 2; i++) { 
    for (let j = 0; j < 2; j++) { 
      for (let k = 0; k < 2; k++) { 
        arr[i][j] += left[i][k] * right[k][j];
        arr[i][j] %= DIVIDER;
      }
    }
  }
  console.log(arr);
  return arr;
}