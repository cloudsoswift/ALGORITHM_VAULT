const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2026.txt'),
  output: process.stdout,
});

let ln = 0, K = 0, N = 0, F = 0, answer = false;
let connections = [];

rl.on("line", (line) => {
  if (ln === 0) {
    [K, N, F] = line.split(" ").map(Number);
    connections = new Array(N + 1).fill().map(_ => []);
  } else { 
    // [번호 작은 학생 -> 번호 큰 학생] 로의 단방향 연결만 기록해놓기 위해 다음과 같이 설정
    const [A, B] = line.split(" ").map(Number).sort((a, b) => a - b);
    connections[A].push(B);
  }
  ln++;
});

rl.on("close", () => {
  for (let i = 1; i <= N; i++) { 
    // 추후 has() 연산 사용 위해 Set으로 다시 만들 되,
    // search에서 친한 친구 오름차순으로 탐색하기 위해 정렬
    connections[i] = new Set(connections[i].sort((a, b) => a - b));
  }
  for (let i = 1; i <= N; i++) { 
    // 친구 수가 K-1 명 이상인 학생만 탐색 (이외의 경우 애초에 K명 학생 뽑는게 불가능)
    if (connections[i].size >= K - 1) { 
      search(i, connections, [i]);
    }
  }
  // 모든 경우의 수 따졌는데 정답 나오지 못한 경우, K명 뽑는게 불가능하므로 -1 출력
  if (!answer) { 
    console.log(-1);
  }
});

function search(now, connections, arr) { 
  // 이미 답인 나온 경우 더 탐색 X
  if (answer) return;

  // K명의 학생 뽑은 경우 출력하고 탈출
  if (arr.length === K) { 
    answer = true;
    console.log(arr.join("\n"));
    return;
  }

  for (const friend of connections[now]) {  
    // 현재 학생과 친한 친구 오름차순 순회

    // "만약 해당 학생의 친구 수"(connections[friend].size)가 
    // "K명의 친구 달성에 필요한 남은 친구 수"(K - arr.length -1) 보다 적은 경우
    // 해당 학생을 포함시켜도 불가능하므로 확인 X
    if (connections[friend].size < (K - arr.length - 1)) continue;
    let isPossible = true;

    for (const ff of arr) { 
      // 현재 선택된 친구들(arr에 포함된 학생들)이 현재 학생(friend)와 모두 친구인지 확인
      if (!connections[ff].has(friend)) { 
        isPossible = false;
        break;
      }
    }
    if (!answer && isPossible) {
      arr.push(friend);
      search(friend, connections, arr);
      arr.pop();
    }
  }
}