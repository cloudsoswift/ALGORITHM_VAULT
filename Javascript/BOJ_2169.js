const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2169.txt'),
  output: process.stdout,
});

let N = 0, M = 0, ln = 0;
let map = [];

rl.on('line', (line) => {
  if (ln === 0) {
    [N, M] = line.split(" ").map(Number);
    map = new Array(N).fill().map(_ => new Array(M).fill(0));
  } else { 
    const splited = line.split(" ").map(Number);
    for (let i = 0; i < splited.length; i++) { 
      map[ln - 1][i] = splited[i];
    }
  }
  ln++;
});

rl.on('close', () => {
  // 각 지점에 도달 가능한 경로 중 가치 합이 최대가 되는 경우의 가치 값을 기록하는 배열 dp[i][j]
  const dp = new Array(N).fill().map(_ => new Array(M).fill(0));
  // 시작 위치 (0,0) 초기화
  dp[0][0] = map[0][0];
  // 첫 번째 줄은 (0, 0)에서 오른쪽으로 이동하는 것 밖에 방법이 없음
  // 따라서 [왼쪽 칸의 최대 가치 값 + 현재 칸의 가치] 로 초기화
  for (let i = 1; i < M; i++) { 
    dp[0][i] = dp[0][i - 1] + map[0][i];
  }
  // 두 번째 줄부터 dp 배열 기록 진행
  for (let i = 1; i < N; i++) { 
    // 오른쪽 끝 -> 왼쪽 끝으로 진행했을 때 각 위치까지의 경로 값 중 최댓값 기록하는 임시 배열 tmp
    const tmp = new Array(M).fill(0);
    
    // 현재 줄의 맨 왼쪽은 위에서 내려오는 것 밖에 방법이 없음
    // 따라서 [바로 윗 줄 왼쪽 끝 칸의 최대 가치 값 + 현재 칸의 가치] 로 초기화
    dp[i][0] = dp[i - 1][0] + map[i][0];
    
    // 왼쪽 끝 -> 오른쪽 끝으로 진행했을 때 각 위치까지의 경로 값 중 최댓값 dp 배열에 기록
    // (r, c) 중 c 값은 1부터 시작 (0은 바로 윗줄에서 초기화 해놨고, 아래 식을 사용하려면 c가 1 이상이어야 함)
    for (let j = 1; j < M; j++) { 
      // [왼쪽에서 오거나(dp[i][j-1]), 바로 윗칸에서 오는 경우(dp[i-1][j]) 중 더 큰 값 + 현재 위치의 값] 을 기록
      dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + map[i][j];
    }
    
    // 현재 줄의 맨 오른쪽 역시, 위에서 내려오는 것 밖에 방법이 없음
    // 따라서 [바로 윗 줄 오른쪽 끝 칸의 최대 가치 값 + 현재 칸의 가치] 로 초기화
    tmp[M - 1] = dp[i - 1][M - 1] + map[i][M - 1];
    
    // 오른쪽 끝 -> 왼쪽 끝으로 진행했을 때 각 위치까지의 경로 값 중 최댓값 tmp 배열에 기록
    // (r, c) 중 c 값은 M-2부터 시작 (M-1은 바로 윗줄에서 초기화 해놨고, 아래 식을 사용하려면 c가 M-2 이하여야 함)
    for (let j = M - 2; j >= 0; j--) { 
      // [오른쪽에서 오거나(tmp[j+1]), 바로 윗칸에서 오는 경우(dp[i-1], j) 중 더 큰 값 + 현재 위치의 값] 을 기록
      tmp[j] = Math.max(dp[i - 1][j], tmp[j + 1]) + map[i][j];
    }
    for (let j = 0; j < M; j++) { 
      // 왼쪽 또는 위에서 온 경우(dp[i][j]), 오른쪽 또는 위에서 온 경우(tmp[j]) 값 중 더 큰 값 dp[i][j]에 기록
      dp[i][j] = Math.max(dp[i][j], tmp[j]);
    }
  }
  // 목적지 (N,M)의 값 출력
  console.log(dp[N - 1][M - 1]);
});