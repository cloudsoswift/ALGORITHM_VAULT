// DFS로 풀려했으나, 시간 초과로 문제 분류 확인하고 BFS + 이진 탐색으로 다시 푼 문제

// 1. 공장이 위치한 두 섬 중 임의로 하나를 출발점으로 설정함.
// 2. 출발점과 연결된 다리 중 가장 중량제한이 높은 다리의 중량제한을 right으로,
//    가장 중량제한이 낮은 다리의 중량제한을 left로 해 이진 탐색
// 3. left와 right의 중간 값인 mid를 임의의 중량 제한 값으로 설정해, 중량제한이 해당 값 이상인 다리들만 사용했을때
//    출발점(from)에서 도착점(to)에 도착할 수 있는지 확인
// 4. 이진 탐색을 마쳤을 때, right 값이 가능한 최대 중량제한이므로 이를 출력
//    (이진 탐색의 조건문인 left <= right 를 탈출했다는 것은 무조건 "left = mid + 1" 문 때문에 탈출한 것이므로
//     right은 mid 값일 수 밖에 없음)

// BFS를 위한 Queue와 Node
class Node { 
  constructor(data) { 
    this.data = data;
    this.next = null;
  }
}
class Queue { 
  constructor() { 
    this.size = 0;
    this.head = null;
    this.tail = null;
  }
  append(data) { 
    const dataNode = new Node(data);
    if (this.size === 0) {
      this.head = dataNode;
      this.tail = dataNode;
    } else { 
      this.tail.next = dataNode;
      this.tail = this.tail.next;
    }
    this.size++;
  }
  pop() { 
    if (this.size === 0) return -1;
    const returnData = this.head.data;
    this.head = this.head.next;
    this.size--;
    return returnData;
  }
  isEmpty() { 
    return this.size === 0;
  }
}

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_1939.txt"),
  output: process.stdout,
});

let ln = 0, N = 0, M = 0, from = 0, to = 0;
let bridges = [];
rl.on('line', (line) => {
  if (ln === 0) {
    [N, M] = line.split(" ").map(Number);
    bridges = Array.from({ length: N + 1 }, () => new Map());
  } else if (ln <= M) {
    const [A, B, C] = line.split(" ").map(Number);
    bridges[A].set(B, Math.max(bridges[A].get(B) || 0, C));
    bridges[B].set(A, Math.max(bridges[B].get(A) || 0, C));
  } else { 
    [from, to] = line.split(" ").map(Number);
  }
  ln++;
});

rl.on('close', () => {
  const costs = new Array(N + 1).fill(0);
  costs[from] = Number.MAX_SAFE_INTEGER;
  const targets = Array.from(bridges[from].entries());
  targets.sort((a, b) => b[1] - a[1]);
  let left = 0, right = targets[0][1];
  while (left <= right) { 
    const mid = Math.floor((left + right) / 2);
    if (BFS(mid, targets)) {
      left = mid + 1;
    } else { 
      right = mid - 1;
    }
  }
  console.log(right);
});

function BFS(weight) { 
  const visited = new Array(N + 1).fill(false);
  visited[from] = true;
  const queue = new Queue();
  queue.append([from, Number.MAX_SAFE_INTEGER]);
  while (!queue.isEmpty()) { 
    let queueSize = queue.size;
    while (queueSize-- > 0) { 
      const [now, c] = queue.pop();
      for (const [next, cost] of bridges[now].entries()) { 
        if (cost < weight) continue;
        if (visited[next]) continue;
        visited[next] = true;
        queue.append([next, Math.min(c, cost)]);
      }
    }
  }
  return visited[to];
}