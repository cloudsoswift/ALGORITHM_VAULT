const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_15681.txt'),
  output: process.stdout,
});

function Node(idx, node) {
  this.idx = idx;
  this.node = node;
 }

let ln = 0, N = 0, R = 0, Q = 0;
// 단일 연결 리스트 형태로 i번 정점과 연결된 정점들 번호를 기록해놓는 배열 node
// i번 정점을 루트로 하는 서브 트리의 정점 개수를 기록하는 배열 subtree_count
let nodes = [], subtree_count = [], queries = [];
let answer = "";

rl.on("line", (line) => {
  if (ln === 0) {
    [N, R, Q] = line.split(" ").map(Number);
    nodes = Array(N + 1).fill(null);
    subtree_count = Array(N + 1).fill(0);
  } else if (ln < N) {
    // 1 <= ln < N
    const [U, V] = line.split(" ").map(Number);
    nodes[U] = new Node(V, nodes[U]);
    nodes[V] = new Node(U, nodes[V]);
  } else { 
    // N < ln
    queries.push(Number(line));
  }
  ln++;
});

rl.on("close", () => {
  // 각 정점을 루트로 한 서브트리 정점 개수 초기화
  find(R, -1);
  let answer = "";
  for (const query of queries) { 
    answer += `${subtree_count[query]}\n`;
  }
  console.log(answer);
});

function find(idx, parent) {
  // 서브트리 정점 개수 기록할 지역 변수 v
  let v = 1;
  for (let now_node = nodes[idx]; now_node !== null; now_node = now_node.node) { 
    // idx 정점과 연결된 정점들 기록한 단일 연결 리스트 순회
    // 부모 노드는 스킵
    if (now_node.idx === parent) continue;
    v += find(now_node.idx, idx);
  }
  return subtree_count[idx] = v;
}