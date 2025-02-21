let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1194.txt"
).toString().trim().split('\n');
const [N, M] = input[0].split(" ").map(Number);
const map = new Array(N);
const v = new Array(1 << 6);
for (let i = 0; i < v.length; i++) { 
  v[i] = new Array(N);
  for (let j = 0; j < N; j++) { 
    v[i][j] = new Array(M).fill(false);
  }
}
const startPoint = [0, 0];
// [동, 서, 남, 북]
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];
for (let i = 0; i < N; i++) { 
  map[i] = new Array(M).fill(" ");
  for (let j = 0; j < M; j++) { 
    map[i][j] = input[i + 1].at(j);
    if (map[i][j] === "0") {
      startPoint[0] = i;
      startPoint[1] = j;
     }
  }
}
const queue = [];
queue.push([...startPoint, 0]);
let time = 1;
while (queue.length > 0) {
  let queueSize = queue.length;
  while (queueSize-- > 0) { 
    const now = queue.shift();
    for (let i = 0; i < 4; i++) { 
      const mr = now[0] + dr[i];
      const mc = now[1] + dc[i];
      if (mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
      if (v[now[2]][mr][mc]) continue;
      if (map[mr][mc] == '#') continue;
      switch (map[mr][mc]) { 
        case "A":
        case "B":
        case "C":
        case "D":
        case "E":
        case "F":
          if (((1 << map[mr][mc].charCodeAt() - "A".charCodeAt()) & now[2]) != 0) {
            queue.push([mr, mc, now[2]]);
            v[now[2]][mr][mc] = true;
          }
          break;
        case "a":
        case "b":
        case "c":
        case "d":
        case "e":
        case "f":
          const newKeys = now[2] | (1 << (map[mr][mc].charCodeAt() - "a".charCodeAt()));
          queue.push([mr, mc, newKeys]);
          v[newKeys][mr][mc] = true;
          break;
        case "1":
          console.log(time);
          return;
        default:
          queue.push([mr, mc, now[2]]);
          v[now[2]][mr][mc] = true;
      }
    }
  }
  time++;
}
console.log(-1);