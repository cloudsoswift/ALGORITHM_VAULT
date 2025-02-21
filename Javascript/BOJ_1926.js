let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1926.txt"
).toString().trim().split('\n');
const [n, m] = input[0].split(" ").map(Number);
const map = Array(n);
const v = Array(n);
// [동, 서, 남, 북]
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];
for (let i = 1; i <= n; i++) { 
  map[i - 1] = Array(m);
  v[i - 1] = Array(m).fill(false);
  const splited = input[i].split(" ");
  for (let j = 0; j < m; j++) { 
    map[i - 1][j] = +splited[j];
  }
}
const queue = [];
let maxSize = 0;
let count = 0;
for (let i = 0; i < n; i++) { 
  for (let j = 0; j < m; j++) { 
    if (map[i][j] == 1 && !v[i][j]) { 
      count++;
      let size = 1;
      v[i][j] = true;
      queue.push([i, j]);
      while (queue.length > 0) { 
        const now = queue.pop();
        for (let k = 0; k < 4; k++) { 
          const mr = now[0] + dr[k];
          const mc = now[1] + dc[k];
          if (mr < 0 || mc < 0 || mr >= n || mc >= m) continue;
          if (v[mr][mc] || map[mr][mc] == 0) continue;
          v[mr][mc] = true;
          size++;
          queue.push([mr, mc]);
        }
      }
      if (size > maxSize) maxSize = size;
    }
  }
}
console.log(count);
console.log(maxSize);