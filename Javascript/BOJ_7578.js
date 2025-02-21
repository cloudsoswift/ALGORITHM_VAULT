// 답 아이디어 보고 푼 문제
// ========= 원래 생각한 아이디어 ==========
// 1. B라인의 각 기계들의 번호가 A라인에서 몇 번째(idx)로 나오는지 배열에 기록
// 2. 이들을 기반으로, s~e 구간 내 최소 / 최대 idx를 나타내는 세그먼트 트리 그림
// 3. 이후 1~N까지 탐색하며, i+1 ~ N 까지 구간에서, idx가 현재 i번 기계의 idx보다 낮은 경우 카운트
// ========== 위 방식의 문제점 ===========
// 만약 현재 기계(i번째 기계)의 A라인 idx가 2이고, i+1 ~ N의 A라인 idx가 3, 0, 1 인경우
// 최소 / 최대 세그먼트 트리를 써도 3 또는 1을 뚫고 0까지 도달을 해야하기 때문에 사실상 완탐이 되어버림
// ========== 답 아이디어 ===================
// https://sosoeasy.tistory.com/296
// 위 글을 참고함
// 1. 1~N번 기계 탐색시작
// 2. B[i] 기계의 A라인 값이 idx라고 한다면, idx~N 사이의 구간 합 구해서 카운트(sum)
// 3. 카운트 이후, idx ~ N 까지의 구간 합 1씩 증가시킴(update)
// 이렇게 하면 이전의 케이스 확인한게 이후 케이스에 모두 반영됨


const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: fs.createReadStream('./input/BOJ_7578.txt'),
  // input: process.stdin,
  output: process.stdout,
})

let ln = 0, result = 0, N = 0;
let ALine = [], BLine = [], BSEG = [];
const map = new Map();

rl.on('line', (line) => {
  switch (ln) {
    case 0:
      N = Number(line);
      const tree_len = Math.floor(((N & N - 1)) === 0 ? Math.log2(N) : Math.log2(N) + 1);
      BSEG = new Array(Math.pow(2, tree_len + 1)).fill(0);
      BPos = new Array(N + 1).fill(0);
      break;
    case 1:
      ALine = line.split(" ").map(Number);
      for (let i = 0; i < N; i++) { 
        map.set(ALine[i], i);
      }
      break;
    case 2:
      BLine = line.split(" ").map(Number);
      break;
  }
  ln++;
});

rl.on('close', () => {
  let count = 0;
  for (let i = 1; i <= N; i++) {
    // 1부터 N까지 탐색
    const pos = map.get(BLine[i-1]);
    count += sum(BSEG, 1, pos + 1, N, 1, N);
    update(BSEG, 1, 1, N, pos);
  }
  console.log(count);
  
});

function update(tree, node_num, start, end, idx) { 
  if (end < idx || start > idx) return;
  tree[node_num]++;
  if (start === end) return;
  const mid = Math.floor((start + end) / 2);
  update(tree, node_num * 2, start, mid, idx);
  update(tree, node_num * 2 + 1, mid + 1, end, idx);
}

function sum(tree, node_num, left, right, start, end) { 
  if (end < left || start > right) return 0;
  if (left <= start && end <= right) return tree[node_num];
  const mid = Math.floor((start + end) / 2);
  return sum(tree, node_num * 2, left, right, start, mid) +
    sum(tree, node_num * 2 + 1, left, right, mid + 1, end);
};