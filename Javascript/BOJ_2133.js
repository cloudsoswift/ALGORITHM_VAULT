// 도저히 모르겠어서 점화식 보고 푼 문제
// 처음에는 그림 그려보면서 dp[i] =  3 * dp[i-2] + 2; 라고 생각했으나, 틀림
// 제대로 된 점화식은 dp[i] = 3 * dp[i-2] + 2 * (dp[i-4] + dp[i-6] + ...); 이었음

// 추가 이해
// 3x2 블록을 만들 수 있는 경우의 수는 총 3개, 즉 f[2] = 3임
// 이후 3x4, 3x6은 각각 3x2 블록을 2개, 3개 조합해서 만들 수 있음
// 이는 3 * d[i-2]으로 나타낼 수 있는데, 벽의 앞에서부터 3x0(f[0]), 3x2(f[2]), ... 만큼의 공간을 채우고
// 남은 공간에 3x2 블록 3개 중 하나를 선택해 넣는 경우의 수로 볼 수 있기 때문
// 또한 3x4 부터, 3x2 블록간의 조합 외에도 만들 수 있는 특수한 경우의 형태가 생겨남
// 가령 3x4 에서는
// | - - |  - - - -
// | - - |  | - - |
// - - - -  | - - |
// 라던지,
// 3x6 에서는
// | - - - - |  - - - - - -
// | - - - - |  | - - - - |
// - - - - - -  | - - - - |
// 와 같은식의 경우의 수가 생겨나버리게 됨
// 이는 3x4 이후로 계속 길이를 늘려가며 등장하기 때문에
// 3x4 에서는 벽의 앞에서부터 3x0 만큼의 공간을 채우고 특수한 3x4를 놓는 경우(2 * dp[0])
// 3x6 에서는 벽의 앞에서부터 3x0, 3x2 만큼 공간을 채우고 특수한 3x6, 3x4를 놓는 경우(2 * dp[0] + 2 * dp[2])
// 와 같은 특수한 경우를 추가로 계산해주어야 함
// 따라서 점화식이 `dp[i] = 3 * dp[i-2] + 2 * (dp[i-4] + dp[i-6] + ...)` 가 되는 것

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2133.txt'),
  output: process.stdout,
});

let N = 0;

rl.on('line', (line) => {
  N = Number(line);
});

rl.on('close', () => {

  if (N % 2 === 1) { 
    console.log(0);
    return;
  }

  const dp = new Array(N + 1).fill(0);
  dp[0] = 1;
  dp[2] = 3;
  for (let i = 4; i <= N; i += 2) { 
    dp[i] = 3 * dp[i - 2];
    for (let j = i - 4; j >= 0; j -= 2) { 
      dp[i] += dp[j] * 2;
    } 
  }
  console.log(dp[N]);
});