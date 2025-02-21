const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_14940.txt"
).toString().trim().split('\n');
const [n, m] = input[0].split(" ").map(Number);
let start = [0, 0, 0];
const arr = new Array(n).fill().map(_ => new Array(m).fill(0));
for (let i = 0; i < n; i++) { 
  const splitted = input[i + 1].split(" ").map(Number);
  for (let j = 0; j < m; j++) { 
    arr[i][j] = splitted[j];
    if (arr[i][j] === 2) { 
      start = [i, j, 0];
    }
  }
}
const cost = new Array(n).fill().map(_ => new Array(m).fill(Number.MAX_SAFE_INTEGER));
cost[start[0]][start[1]] = 0;
const queue = [start];
// 동 서 남 북
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];

while (queue.length > 0) { 
  const now = queue.shift();
  console.log(now);
  for (let i = 0; i < 4; i++) { 
    const mr = now[0] + dr[i];
    const mc = now[1] + dc[i];
    if (mr < 0 || mc < 0 || mr >= n || mc >= m) continue;
    if (now[2] + 1 >= cost[mr][mc]) continue;
    if (arr[mr][mc] === 0) continue;
    cost[mr][mc] = now[2] + 1;
    queue.push([mr, mc, now[2] + 1]);
  }
}
cost.forEach((line, i) => {
  line.forEach((spot, j) => {
    if (spot === Number.MAX_SAFE_INTEGER) {
      if (arr[i][j] === 1) {
        cost[i][j] = -1;
      } else { 
        cost[i][j] = 0;
      }
    }
  });
  console.log(line.join(' '));
});