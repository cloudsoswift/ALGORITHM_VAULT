// 원 아이디어
// 1. 배열 dp와 cost 를 만든다
// 1.1 dp[i][j] = i~j 파일들을 합쳤을 때, 합쳐진 파일의 최소 비용
// 1.2 cost[i][j]= i~j 파일들을 합치는데 드는 모든 비용의 합이 최소인 값
// 2. 0 ~ K-1파일들에 대하여 range를 1~K까지 늘리며 순회한다.
// 3. 0 ~ K-range까지 순회(i)하며 i ~ i+range 번 파일의 합의 최소 비용을 구한다.
// 이때, i~ i+range 사이를 순회하는 변수 j를 만들어, i~j 파일들을 합친 것과 j+1 ~ i+range 파일들을 합친 것, 둘을 합치는 비용을 구해 최솟값 갱신을 한다.

// 참고한 최적 아이디어
// 1. 0 ~ K-1 파일들에 대한 누적합을 계산한다.
// 2. 위처럼 0 ~ K-1 파일들에 대한 range / 0 ~ K-range 파일들에 대한 i / i부터 i+range 까지에 대한 j 총 3중 포문을 사용한다.
// 3. 이때, 기존의 dp[i][i+range]와 dp[i][j] + dp[j+1][i+range] + 누적합(i+range ~ i-1) 중 더 작은 값을 dp[i][i+range] 로 갱신한다.

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream("./input/BOJ_11066.txt"),
  output: process.stdout
});
let ln = 0, T = 0, K = 0, files = [];
let answer = "";

rl.on('line', (line) => {
  if (ln === 0) {
    T = Number(line);
  } else { 
    if (ln % 2 === 1) {
      K = Number(line);
      files = new Array(K + 1).fill(0);
    } else { 
      const arr = line.split(" ").map(v => Number(v));
      for (let i = 0; i < K; i++) { 
        files[i + 1] = arr[i];
      }
      answer += doCalc();
      answer += '\n';
    }
  }
  ln++;
});

rl.on('close', () => { 
  console.log(answer);
})

function doCalc() { 
  const dp = new Array(K + 1).fill().map(_ => new Array(K + 1).fill(0));
  for (let i = 1; i <= K; i++) { 
    files[i] += files[i-1];
  }
  for (let range = 1; range < K; range++) { 
    for (let i = 1, len1 = K - range; i <= len1; i++) { 
      // i ~ i+range 값을 무한대에 가까운 값으로 초기화
      dp[i][i + range] = 100_000_000;
      // i+range ~ i 까지의 파일 크기 구간 합
      const v = files[i + range] - files[i - 1];
      for (let j = i, len2 = i + range; j < len2; j++) {
        // {[i ~ j] 파일과 + [j+1 ~ i+range] 파일의 누적 비용}(dp[i][j] + dp[j+1][i+range]) + i+range ~ i 까지의 파일 크기 구간 합
        // 과 기존의 dp[i][i+range] 값 중 더 낮은 값으로 갱신
        dp[i][i + range] = Math.min(dp[i][i + range], dp[i][j] + dp[j+1][i+range] + v);
      }
    }
  }
  return dp[1][K];
}