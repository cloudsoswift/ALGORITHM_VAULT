const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2437.txt'),
  output: process.stdout,
});

let N = 0;
let balances = [], dp = [];

rl.on('line', (line) => {
  if (N === 0) {
    N = Number(line);
  } else { 
    balances = line.split(" ").map(Number);
    // 무게추들을 무게 오름차순으로 정렬
    balances.sort((a, b) => a > b ? 1 : (a === b ? 0 : -1));
    // "dp[i] = 0번째 무게추 ~ i번째 무게추 무게의 합"을 기록하는 배열 dp를 초기화하기 위해 reduce 사용
    // 누계값(previousValue, pv, 여기서는 dp 배열)에 현재 값(currentValue, 여기서는 balance[i])에다가
    // 마지막으로 추가된 값(즉, dp[i-1], 여기서는 pv[pv.length-1])ㅁ
    // 을 push한 배열을 반환하는 형태로 reduce 동작
    dp = balances.reduce((pv, cv) => {
      pv.push(cv + (pv[pv.length - 1] || 0));
      return pv;
    }, []);
  }
});

rl.on('close', () => {
  if (balances[0] > 1) { 
    // 저울 추 중 가장 가벼운 추의 무게가 1 초과라면, 측정할 수 없는 양의 정수 중 최솟값은 1임
    console.log(1);
    return;
  }
  for (let i = 1; i < N; i++) { 
    // 만약 무게순으로 정렬된 저울 추 중 i번째와 i-1번째 추의 무게 차가 1 이상인 경우,
    // 즉, 무게가 연속되지 않는 경우
    if (balances[i] - balances[i-1] > 1) { 
      // 만약 0번째~ i-1번째 까지의 무게추를 조합해 i번째 무게추의 무게를 조합해낼 수 있으면 통과
      if (dp[i - 1] >= balances[i]) continue;
      // !!!놓친 부분!!!
      // 만약 0번째 ~ i-1번째 까지의 무게추 조합한 무게 + 1이 i번째 무게추의 무게와 같다면
      // i번째 무게추 하나로도 가능하므로 통과
      if (dp[i - 1] + 1 === balances[i]) continue;
      // 그렇지 않다면, 0번째 ~ i-1번째 까지의 무게추를 조합한 무게(dp[i-1]) + 1이 측정할 수 없는 최솟값
      console.log(dp[i-1], balances[i]);
      
      console.log(dp[i - 1] + 1);
      return;
    }
  }
  // 위의 경우를 모두 통과했다면, 무게추들을 전부 조합한 무게(dp[N-1]) + 1 이 측정할 수 없는 최솟값
  console.log(dp[N-1] + 1);
});