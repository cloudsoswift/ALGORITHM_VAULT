class Node { 
  constructor(value) { 
    this.value = value;
    this.next = null;
  }
}
class Queue { 
  constructor() { 
    this.head = null;
    this.tail = null;
    this.len = 0;
  }
  size = () => this.len;
  isEmpty = () => this.len === 0;
  enqueue(input) { 
    const node = new Node(input);
    if (this.len === 0) {
      this.head = node;
    } else { 
      this.tail.next = node;
    }
    this.tail = node;
    this.len++;
  }
  dequeue() { 
    if (this.len === 0) {
      return null;
    }
    const res = this.head.value;
    this.head = this.head.next;
    this.len--;

    return res;
  }
}


const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_16920.txt"
).toString().trim().split('\n').map(str => str.trim());
const [N, M, P] = input[0].split(" ").map(Number);
const S = input[1].split(" ").map(Number);
const WALL = 11;
const map = new Array(N).fill().map(_ => new Array(M).fill(0));
const startPos = new Array(P + 1).fill().map(_ => new Array());
const count = new Array(P + 1).fill(0);
// 위, 왼쪽, 오른쪽, 아래
const dr = [-1, 0, 0, 1];
const dc = [0, 1, -1, 0];
let empty_count = N * M;
for (let i = 0; i < N; i++) { 
  const splited = input[i + 2].split("");
  for (let j = 0; j < M; j++) { 
    switch (splited[j]) {
      case ".":
        map[i][j] = 0;
        break;
      case "#":
        map[i][j] = WALL;
        empty_count--;
        break;
      default:
        const num = +splited[j];
        map[i][j] = num;
        startPos[num].push([i, j]);
        count[num]++;
        empty_count--;
     }
  }
}
const v = new Array(N).fill().map(_ => new Array(M).fill(false));
let locked = P;
while (empty_count > 0 && locked > 0) {
  locked = P;
  const queue = new Queue();
  for (let i = 1; i <= P; i++) { 
    while (startPos[i].length > 0) { 
      const sp = startPos[i].pop();
      queue.enqueue([...sp, S[i-1]]);
      v[sp[0]][sp[1]] = true;
    }
    while (!queue.isEmpty()) { 
      let queueSize = queue.size();
      while (queueSize-- > 0) { 
        const now = queue.dequeue();
        const nowNum = map[now[0]][now[1]];
        console.log(now);
        for (let j = 0; j < 4; j++) { 
          const mr = now[0] + dr[j];
          const mc = now[1] + dc[j];
          if (mr >= N || mc >= M || mr < 0 || mc < 0) continue;
          if (v[mr][mc]) continue;
          if (map[mr][mc] === 0) {
            v[mr][mc] = true;
            map[mr][mc] = nowNum;
            count[nowNum]++;
            empty_count--;
            if (now[2] > 1) {
              queue.enqueue([mr, mc, now[2] - 1]);
            } else { 
              startPos[nowNum].push([mr, mc]);
            }
          } else if (map[mr][mc] === nowNum) { 
            v[mr][mc] = true;
            queue.enqueue([mr, mc, now[2]]);
          }
        }
      }
    }
    if (startPos[i].length === 0) locked--;
  }
}
console.log(count.slice(1).join(" "));