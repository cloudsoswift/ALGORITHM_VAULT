// 도저히 모르겠어서 답 보고 푼 문제
// https://degurii.tistory.com/158 참고(사실상 베낌)함
// + Number의 MAX_SAFE_INTEGER(2^53-1) 보다 input의 범위(10^16)가 더 넓으므로
// BigInt로 죄다 도배함

// test input: https://maratona.ime.usp.br/hist/2013/resultados13/data/contest/C/input/
// test output: https://maratona.ime.usp.br/hist/2013/resultados13/data/contest/C/output/

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_9527.txt'),
  output: process.stdout,
})
let A = 0, B = 0;
const dp = new BigInt64Array();
rl.on('line', (line) => {
  [A, B] = line.split(" ").map(BigInt);
});

rl.on('close', () => { 
  // B까지의 누적 합 - (A-1) 까지의 누적합을 구해
  // A~B까지의 1의 갯수 구함
  console.log((getOneCount(B) - getOneCount(A-1n)).toString());
})

// 1 << v 수 까지의 모든 1의 개수 반환하는 함수
function getDp(v) { 
  // dp에 기록해놨으면 해당 값 반환
  if (dp[v] !== undefined) return dp[v];
  // 0이면 1 << 0 까지의 1의 개수가 1개 이므로 1 반환 및 기록
  if (v === 0n) return dp[0n] = 1n;
  // dp에 기록 안해놨으면 구하는데
  // 2 * getDp(v-1) 의 의미) 
  //   v 비트아래의 수는(v - 1비트가 0인 경우 + v - 1 비트가 1인 경우)를 모두 카운트
  //   따라서 getDp(v-1)를 한 뒤 2를 곱함 
  // 1 << v 의 의미)
  //   2 ^ (v) ~ 2 ^ (v+1) 까지의 수는 2 ^ v 개 만큼 v번째 비트가 1임
  //   따라서 1 << v (== 2^v)를 더함
  else return dp[v] = 2n * getDp(v-1n) + BigInt((1n << v));
}

// v까지의 1의 개수 누적 구하는 함수
function getOneCount(v) { 
  // 최대 범위인 10^16 ~= 2^(53.1508)
  // 따라서 2^54부터 비트 내려가며 탐색
  const nDigit = 54n;
  // 답으로 반환할 값
  let ans = v & 1n;
  for (let i = nDigit; i > 0; i--) { 
    // nDigit 번째 부터 1 번째 비트까지 내려가며 탐색
    if (v & BigInt(1n << i)) { 
      // 만약 v의 i번째 비트가 1인 경우
      // 해당 비트 이전(i-1 번째 비트)의 모든 경우의 수(getDp(i-1)) 와
      // 더해줘야 하는 i 번째 비트의 개수(v ~ 1 << i 구간 내 수는 모두 i번 비트가 1이므로)를
      // 합한 값을 ans에 누적
      ans += getDp(i - 1n) + (v - BigInt(1n << i) + 1n);
      // 합한 이후 i 번째 비트 값은 날림(빼버림)
      v -= BigInt(1n << i);
    }
  }
  
  return ans;
}