// naive하게 풀었으나 복잡한 케이스에 시간초과 발생한 문제
// 실제로 모든 노드를 root로 생각한 뒤, 아래로 뻗쳐나가면서 홀짝 또는 역홀짝 노드가 쭉 이어지는지 계산함
// 이럴경우 nodes가 최대 40만개까지 가능하므로 시간 복잡도가 높게 나올 수 밖에 없음

// 이를 보완하고자 각 노드에 대해, [ 해당 노드가 루트일 때 홀짝 여부, 해당 노드가 리프일 때 홀짝 여부] 를 배열로 저장한 뒤
// 각 트리에 대해, 해당 트리를 구성하는 노드들을 순회하며 먼저 각 노드들이 리프 노드일 때의 홀짝/역홀짝 노드 카운트를 계산함
// 그리고 각 노드들을 기준으로 하는 순회를 진행하며, 해당 노드가 루트일 때 홀짝/역홀짝 여부를 계산함
// 홀짝/역홀짝 노드 카운트에서 루트 노드의 리프일 경우 홀짝/역홀짝 카운트를 빼주고 역의 위치에 1을 더해주었을 때,
// 홀짝/역홀짝 노드 카운트 중 한 값은 0, 나머지 값은 트리 내 노드 개수라면 해당 트리가 홀짝 또는 역홀짝 트리를 이룰 수 있다는 것이므로
// 카운트 증가시킴

// 나는 위 풀이로 풀었지만, 더 간략하게는 각 노드의 차수와 union-find 정보만 저장하고,
// 트리 내에서, 리프일 때 홀짝인 노드 / 리프일 때 역홀짝인 노드가 각각 하나만 있을 경우(즉, h_count === 1 또는 r_h_count === 1),
// 역홀짝 트리 개수 / 홀짝 트리 개수를 증가 시키는 형태로 최적화 할 수 있음

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/PRG_30_388354.txt'),
  output: process.stdout,
});

let nodes = [], edges = [], ln = 0;

rl.on("line", (line) => {
  if (ln === 0) {
    nodes = line.split(" ").map(Number);
  } else { 
    const edge = line.split(" ").map(Number);
    edges.push(edge);
  }
  ln++;
});

rl.on("close", () => { 
  console.log(solution(nodes, edges));
})
function solution(nodes, edges) {
  // 각 노드 번호를 index로 하고, 인접 리스트 배열을 원소로 하는 배열
  const nodeList = new Array(1_000_001);
  const hol_or_rev_hol = new Array(1_000_001);
  for (const node of nodes) { 
    nodeList[node] = [];
    // [해당 노드가 루트일 때, 해당 노드가 루트가 아닐 때]
    // 1: 홀짝 / 2: 역홀짝
    hol_or_rev_hol[node] = [0, 0];
  }
  for (const [A, B] of edges) { 
    nodeList[A].push(B);
    nodeList[B].push(A);
  }
  let hol = 0, rev_hol = 0;
  const is_done = new Uint8Array(1_000_001);
  for (const node of nodes) { 
    if (is_done[node]) continue;
    const tree = [];
    let h_count = 0, r_h_count = 0;
    is_done[node] = true;
    const queue = [node];
    while (queue.length > 0) { 
      const n = queue.pop();
      tree.push(n);
      hol_or_rev_hol[n][0] = (n % 2 == nodeList[n].length % 2) ? 1 : 2;
      hol_or_rev_hol[n][1] = (n % 2 == (nodeList[n].length - 1) % 2) ? 1 : 2;
      if (hol_or_rev_hol[n][1] == 1) h_count++;
      else r_h_count++;
      for (const next of nodeList[n]) { 
        if (is_done[next]) continue;
        is_done[next] = true;
        queue.push(next);
      }
    }
    for (const n of tree) { 
      // 노드 n을 루트로 하는 트리라고 할 때,
      // 루트가 아닐때 홀짝인 경우
      if (hol_or_rev_hol[n][1] == 1) {
        h_count--;
        r_h_count++;
        if (h_count === 0 && r_h_count === tree.length) rev_hol++;
        h_count++;
        r_h_count--;
      } else { 
        // 루트가 아닐때 역홀짝인 경우
        h_count++;
        r_h_count--;
        if (h_count === tree.length && r_h_count === 0) hol++;
        h_count--;
        r_h_count++;
      }
    }
  }
  return [hol, rev_hol];
}