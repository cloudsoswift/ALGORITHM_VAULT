// BFS를 위한 Node, Queue 구현
class Node { 
  constructor(data) { 
    this.data = data;
    this.next = null;
  }
}
class Queue { 
  constructor() { 
    this.size = 0;
    this.front = null;
    this.rear = null;
  }
  isEmpty() { 
    return this.size === 0;
  }
  enqueue(data) { 
    const newNode = new Node(data);
    if (this.size === 0) {
      this.front = newNode;
      this.rear = newNode;
    } else { 
      this.rear.next = newNode;
      this.rear = newNode;
    }
    this.size++;
  }
  dequeue() { 
    if (this.isEmpty()) { 
      return null;
    }
    const rtn = this.front;
    this.front = this.front.next;
    this.size--;
    return rtn.data;
  }
}

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_16933.txt'),
  output: process.stdout,
})
let N = 0, M = 0, K = 0;
let ln = 0;
let map = [];
let dp = [];
// 동, 서, 남, 북
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];
  
rl.on('line', (line) => { 
  if (ln === 0) {
    [N, M, K] = line.split(" ").map(Number);
    // 각 위치에 벽이 있으면 1, 없으면 0을 기록하는 배열 map
    map = new Array(N).fill().map(_ => new Array(M).fill(0));
    // 각 위치까지 도달하는데 부숴야 하는 벽의 최솟값 기록하는 배열 dp
    dp = new Array(N).fill().map(_ => new Array(M).fill(K+1));
    dp[0][0] = 0;
  } else { 
    const splited = line.split("");
    for (let i = 0; i < M; i++) { 
      map[ln - 1][i] = splited[i] === "0" ? 0 : 1;
    }
  }
  ln++; 
})

rl.on('close', () => {
  
  const queue = new Queue();
  queue.enqueue([0, 0, 1, 0, false]);

  while (!queue.isEmpty()) { 
    const [r, c, step, break_count, isNight] = queue.dequeue();
    
    if (r === N - 1 && c === M - 1) { 
      // (N, M)에 도착한 경우, 이동한 거리 출력 후 종료
      console.log(step);
      
      return;
    }
    for (let i = 0; i < 4; i++) { 
      const mr = r + dr[i];
      const mc = c + dc[i];
      if (mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
      // 현재까지 벽 부순 횟수 + 이동할 위치의 벽 갯수
      const mw = break_count + map[mr][mc];
      // 그것이 K+1 이상, 즉 해당 위치에 도달하면 벽을 K개 초과해서 부수는 경우는
      // 이동할 수 없으므로 스킵
      if (mw >= dp[mr][mc]) continue;
      if (map[mr][mc] === 1 && isNight) {
        // 밤인데 벽을 부숴야 하는 경우, 즉 대기를 해야하는 경우
        queue.enqueue([r, c, step + 1, break_count, !isNight]);
      } else { 
        // 이외의 경우
        dp[mr][mc] = mw;
        queue.enqueue([mr, mc, step + 1, mw, !isNight]);
      }
    }
  }
  // (N, M)에 도착할 수 없는 경우
  console.log(-1);
})