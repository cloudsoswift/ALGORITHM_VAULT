const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1082.txt'),
  output: process.stdout
});

let ln = 0, N = 0, M = 0;
const P = [];

rl.on("line", (line) => {
  switch (ln) {
    case 0:
      N = Number(line);
      break;
    case 1:
      const split = line.split(" ");
      for (let i = 0; i < N; i++) { 
        P.push(Number(split[i]));
      }
      break;
    case 2:
      M = Number(line);
      break;
  }
  ln++;
});

rl.on("close", () => {
  const dp = new Array(M + 1).fill("");
  for (let i = 1; i <= M; i++) {
    for (let j = 0; j < N; j++) {
      const before = i - P[j];
      // 0에 0을 더하는 것을 고려하는 예외 케이스 처리 
      if (j === 0 && dp[before] == "0") { 
        dp[i] = "0";
      }
      else if (before >= 0 && compare(`${dp[before]}${j}`, dp[i]) === 1) {
        // 숫자 j를 추가할 비용이 있으며, 이전 숫자 문자열(dp[before])에 j를 덧붙인게
        // 비용 i를 사용해 만들 수 있는 숫자 문자열(dp[i]) 값보다 큰 경우
        // dp[before] +  j로 갱신
        dp[i] = `${dp[before]}${j}`;
      }
    }
  }
  console.log(`${dp}`);
});

function compare(A, B) { 
  // 두 숫자 문자열 A, B를 비교하는 함수
  // A > B = 1
  // A == B = 0
  // A < B = -1

  if (A.length > B.length) {
    return 1;
  } else if (A.length < B.length) {
    return -1;
  } else { 
    for (let i = 0, N = A.length; i < N; i++) { 
      if (A[i] > B[i]) {
        return 1;
      } else if (A[i] < B[i]) { 
        return -1;
      }
    }
  }
  return 0;
}