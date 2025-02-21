let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2146.txt"
).toString().trim().split('\n');
const N = +input[0];
let mapArr = Array(N);
let vArr = Array(N);
let MAX_LENGTH = 100000;
// [동, 서, 남, 북]
const dr = [ 0, 0, 1, -1];
const dc = [1, -1, 0, 0];
for (let i = 1; i <= N; i++) { 
  mapArr[i - 1] = Array(N).fill(0);
  vArr[i - 1] = Array(N).fill(false);
  const inline = input[i]?.split(" ") || [];
  for (let j = 0; j < N; j++) { 
    mapArr[i - 1][j] = +inline[j];
  }
}
let queue = [];
let findQueue = [];
let islandNumber = 1;
for (let i = 0; i < N; i++) { 
  for (let j = 0; j < N; j++) { 
    if (vArr[i][j] == false && mapArr[i][j] != 0) {
      queue.push([i, j]);
      mapArr[i][j] = islandNumber;
      vArr[i][j] = true;
      while (queue.length > 0) {
        const now = queue.shift();
        let nearbyOcean = false;
        for (let k = 0; k < 4; k++) {
          const mr = now[0] + dr[k];
          const mc = now[1] + dc[k];
          if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
          if (mapArr[mr][mc] == 0) { 
            nearbyOcean = true;
            continue;
          }
          if (vArr[mr][mc] == true) continue;
          vArr[mr][mc] = true;
          queue.push([mr, mc]);
          mapArr[mr][mc] = islandNumber;
        }
        if (nearbyOcean == true) findQueue.push([now[0], now[1]]);
      }
      islandNumber++;
    }
  }
}
function bfs(r, c) { 
  const baseIslandNumber = mapArr[r][c];
  let visitArr = Array(N);
  for (let i = 0; i < N; i++) { 
    visitArr[i] = Array(N).fill(false);
  }
  let bfsQueue = [];
  visitArr[r][c] = true;
  for (let i = 0; i < 4; i++) { 
    const mr = r + dr[i];
    const mc = c + dc[i];
    if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
    if (visitArr[mr][mc] == true) continue;
    bfsQueue.push([mr, mc]);
    visitArr[mr][mc] = true;
  }
  let bridgeLength = 1;
  while (bfsQueue.length > 0) { 
    let queueSize = bfsQueue.length;
    while (queueSize-- > 0) { 
      const now = bfsQueue.shift();
      if (mapArr[now[0]][now[1]] == baseIslandNumber) continue;
      if (mapArr[now[0]][now[1]] != 0) { 
        if (MAX_LENGTH > bridgeLength-1) { 
          MAX_LENGTH = bridgeLength-1;
          return;
        }
      }
      for (let i = 0; i < 4; i++) { 
        const mr = now[0] + dr[i];
        const mc = now[1] + dc[i];
        if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
        if (visitArr[mr][mc] == true) continue;
        bfsQueue.push([mr, mc]);
        visitArr[mr][mc] = true;
      }
    }
    bridgeLength++;
  }
}
findQueue.map((v) => { bfs(...v); });
console.log(MAX_LENGTH);