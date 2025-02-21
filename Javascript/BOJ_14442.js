// [K][N][M] 배열 사용해서 풀려고 했으나 계속 메모리 터져서
// 답 보고 푼 문제

// Javascript 배열의 shift 연산시 O(N)으로 Queue의 poll() 보다 훨씬 느리므로
// Queue 직접 구현해 사용
class Queue { 
  constructor() { 
    this.front = null;
    this.rear = null;
    this.len = 0;
  }
  add(value) { 
    const newNode = new Node(value);
    if (this.len === 0) {
      this.front = newNode;
    } else { 
      this.rear.next = newNode;
    }
    this.rear = newNode;
    this.len++;
  }
  pop() { 
    const rtn = this.front.value;
    if (this.len === 0) {
      this.front = null;
      this.rear = null;
    } else { 
      this.front = this.front.next;
    }
    this.len--;
    return rtn;
  }
  size() { return this.len; }
}
class Node { 
  constructor(value) { 
    this.value = value;
    this.next = null;
  }
}

const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_14442.txt")
  .toString().trim().split("\n");
const [N, M, K] = input[0].split(" ").map(Number);
const map = new Array(N);
const dp = new Array(N);
// [동, 남, 서, 북]
const dr = [0, 1, 0, -1];
const dc = [1, 0, -1, 0];

for (let i = 0; i <= N; i++) {
  dp[i] = new Array(M).fill(K+1);
}

for (let i = 0; i < N; i++) { 
  map[i] = new Array(M);
  for (let j = 0; j < M; j++) { 
    map[i][j] = +input[i+1].at(j);
  }
}
// Queue의 각 Node들에는 [r좌표, c좌표, 부순 벽 개수, 현재까지 경로 비용] 담김
const queue = new Queue();
queue.add([0, 0, 0, 1]);
dp[0][0] = 0;
while (queue.size() > 0) {  
  const now = queue.pop();
  if (now[0] === N - 1 && now[1] === M - 1) { 
    console.log(now[3]);
    return;
  }
  for (let i = 0; i < 4; i++) { 
    const mr = now[0] + dr[i];
    const mc = now[1] + dc[i];
    if (mr >= N || mc >= M || mr < 0 || mc < 0) continue;
    // map[mr][mc] 가 벽이라면 벽 부순 횟수 1 늘어날거고, 그렇지 않다면 그대로
    const mw = now[2] + map[mr][mc];
    if (mw >= dp[mr][mc]) continue;
    dp[mr][mc] = mw;
    queue.add([mr, mc, mw, now[3] + 1]);
  }
}
if (visited[n-1][m-1] === k+1) console.log(-1);