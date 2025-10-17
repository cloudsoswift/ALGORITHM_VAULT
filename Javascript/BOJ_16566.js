// 답 아이디어 보고 푼 문제 + 바보같이 Array.sort()가 문자열 정렬인지 모르고 그냥 써서 시간 많이 소모

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_16566.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, M = 0, K = 0, answer = [];
let cards = [], opposite = [], parent = [];

rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      [N, M, K] = line.split(" ").map(Number);
      parent = new Array(M+1).fill(0).map((_, i) => i);
      break;
    case 1:
      cards = line.split(" ").map(Number);
      break;
    case 2:
      opposite = line.split(" ").map(Number);
      break;
  }
  ln++;
});

rl.on("close", () => {
  cards.sort((a, b) => a < b ? -1 : (a === b ? 0 : 1));
  for (let i = 0; i < K; i++) { 
    let idx = search_cards(opposite[i]);
    idx = find(idx);
    union(idx, idx + 1);
    answer.push(cards[idx]);
  }
  console.log(answer.join("\n"));
});

function search_cards(target) { 
  let left = 0, right = M - 1;
  while (left <= right) { 
    const mid = Math.floor((left + right) / 2);
    if (cards[mid] <= target) {
      left = mid + 1;
    } else { 
      right = mid - 1;
    }
  }
  return left;
}

// union-find 사용 이유
// 1. search_cards(이분 탐색)를 사용해 이번에 철수가 낸 카드(opposite[i])보다 큰 카드를 찾음
// 2. 해당 카드가 이미 사용된 경우, 그 다음으로 큰 사용가능한 카드를 찾아야 함.
//   만약, 매 번 다음으로 큰 사용가능한 카드를 찾는다면 시간이 오래걸림
// 3. 따라서, union-find를 사용해 parent를 "다음으로 큰, 사용 가능한 카드의 idx"로 설정하면
//   바로 찾을 수 있음

function union(a, b) { 
  const parent_a = find(a), parent_b = find(b);
  if (parent_a !== parent_b)
    parent[parent_a] = parent_b;
}

function find(a) { 
  if (a === parent[a]) return a;
  return parent[a] = find(parent[a]);
}