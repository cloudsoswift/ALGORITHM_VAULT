const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2629.txt'),
  output: process.stdout,
});

// 추의 개수 N, 확인하고자 하는 구슬들의 개수 K
let ln = 0, N = 0, K = 0;
let weights = [], beads = [], dp = [];
// 모든 추의 무게 합 whole_weights, 무게 합의 절반 half_weights
let whole_weights = 0, half_weights = 0;

rl.on('line', (line) => {
  switch (ln) { 
    case 0:
      N = Number(line);
      whole_weights = 0;
      break;
    case 1:
      // 각 무게 추의 무게들의 합을 구해 whole_weights와 half_weights 초기화
      weights = line.split(" ").map((v) => { const nv = Number(v); whole_weights += nv; return nv; });
      half_weights = Math.round(whole_weights / 2);
      dp = new Array(whole_weights + 1).fill(false);
      dp[0] = true;
      break;
    case 2:
      K = Number(line);
      break;
    case 3:
      beads = line.split(" ").map(Number);
      break;
  }
  ln++;
});

rl.on('close', () => {
  // dp를 통해 가능한 무게추 조합 기록
  for (let i = 0; i < N; i++) { 
    // whole_weights -> 현재 무게 추 무게(weights[i]) 까지 내림차순으로 탐색
    for (let j = whole_weights; j >= weights[i]; j--) { 
      if (dp[j - weights[i]]) { 
        dp[j] = true;
      }
    }
  }
  const answer = [];
  for (let i = 0; i < K; i++) {
    if (dp[beads[i]]) { 
      // i번째 구슬과 똑같은 무게가 무게 추들을 조합해 나올 수 있는 경우
      answer.push("Y");
      continue;
    }
    if (beads[i] > whole_weights) { 
      // i번째 구슬의 무게가 모든 무게 추의 무게 합 보다 큰 경우
      answer.push("N");
      continue;
    }
    for (let j = 0, len = Math.min(half_weights, whole_weights - beads[i]); j <= len; j++) { 
      // { 모든 무게 추의 무게 합의 절반, 모든 무게 추의 무게 합 - i번째 구슬의 무게 } 중 더 낮은 값까지 j를 증가해나가며 탐색
      // 1. half_weights이 더 작은 경우 -> 검사를 { j + beads[i] = X } 로 하기 때문에, j가 half_weights를 넘어버리면 이를 만족할 수 있는 X가 존재할 수 없음
      // 2. whole_weight - beads[i]가 더 작은 경우 -> 검사를 { j + beads[i] = X } 로 하기 때문에, j가 whole_weights에서 beads[i]를 뺀 값을 넘어버리면
      //  X가 whole_weight 값 이상이어야 함 -> 존재할 수 없음
      if (dp[j]) { 
        // 만약 무게 j가 가능하다면
        const added = j + beads[i];
        // beads[i]에 j를 더함
        if (dp[added]) { 
          // { i 번째 구슬 + 무게추(들)의 합  = 나머지 무게 추(들)의 합 } 이 가능한 경우
          answer.push("Y");
          break;
        }
      }
    }
    // 모든 경우 찾아봐도 i번 째 구슬의 무게를 알 수 없는 경우 = 위 반복문에서 answer에 "Y"를 추가하지 않아, answer 배열의 길이가 i와 같을 때
    if (answer.length === i) answer.push("N");
  }
  console.log(answer.join(" ")); 
});