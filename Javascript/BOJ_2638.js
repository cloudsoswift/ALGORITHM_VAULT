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

let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2638.txt"
).toString().trim().split('\n');
const [N, M] = input[0].split(" ").map(n => Number(n));
const map = new Array(N).fill().map(_ => new Array(M).fill(0));
let cheeseCount = 0, timetaken = 0;
for (let i = 0; i < N; i++) { 
  const splited = input[i + 1].split(" ");
  for (let j = 0; j < M; j++) { 
    map[i][j] = Number(splited[j]);
    if (map[i][j] === 1) cheeseCount++;
  }
}
// 동, 서, 남, 북
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];

const queue = new Queue();
const cheeseQueue = new Queue();
const deleteCheeseQueue = new Queue();
const visited = new Array(N).fill().map(_ => new Array(M).fill(false));

while (cheeseCount > 0) { 
  visited.map(arr => arr.fill(false));
  queue.enqueue([0, 0]);
  visited[0][0] = true;
  // 1. 모눈종이 가장자리부터 시작해 인접한 실내온도 공기들을 visited에 true로 기록한다.
  while (!queue.isEmpty()) { 
    const nowAir = queue.dequeue();
    for (let i = 0; i < 4; i++) { 
      const mr = nowAir[0] + dr[i];
      const mc = nowAir[1] + dc[i];
      if (mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
      if (visited[mr][mc]) continue;
      if (map[mr][mc] === 1) {
        cheeseQueue.enqueue([mr, mc]);
      } else { 
        queue.enqueue([mr, mc]);
      }
      visited[mr][mc] = true;
    }
  }
  // 2. 실내온도 공기와 인접한 치즈들이 녹는 조건을 만족하는지 검사한다.
  while (!cheeseQueue.isEmpty()) { 
    const nowCheese = cheeseQueue.dequeue();
    let nearAirCount = 0;
    for (let i = 0; i < 4; i++) { 
      const mr = nowCheese[0] + dr[i];
      const mc = nowCheese[1] + dc[i];
      if (mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
      if (map[mr][mc] === 1) continue;
      if (visited[mr][mc]) nearAirCount++;
    }
    if (nearAirCount >= 2) {
      deleteCheeseQueue.enqueue(nowCheese);
    }
  }
  while (!deleteCheeseQueue.isEmpty()) { 
    const nowCheese = deleteCheeseQueue.dequeue();
    map[nowCheese[0]][nowCheese[1]] = 0;
    cheeseCount--;
  }
  timetaken++;
}
console.log(timetaken);