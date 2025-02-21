const readline = require('readline');
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});
let ln = 0;
let N = 0, M = 0;
let map, visited, roomNumberMap, roomArea;
let roomCount = 1, maximumArea = 0, breakWallArea = 0;
// 동, 서, 남, 북
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];
rl.on('line', (line) => {
  if (ln === 0) {
    [N, M] = line.split(" ").map(Number); 
    map = new Array(M).fill().map(_ => new Array(N).fill(0));
    visited = new Array(M).fill().map(_ => new Array(N).fill(false));
    roomNumberMap = new Array(M).fill().map(_ => new Array(N).fill(0));
    roomArea = [0];
  } else { 
    const splited = line.split(" ").map(Number);
    for (let i = 0; i < N; i++) { 
      map[ln - 1][i] = splited[i];
    }
  }
  ln++;
});
rl.on('close', () => {
  for (let i = 0; i < M; i++) { 
    for (let j = 0; j < N; j++) { 
      if (!visited[i][j]) { 
        visited[i][j] = true;
        const res = search(i, j, roomCount);
        roomArea.push(res);
        maximumArea = Math.max(maximumArea, res);
        roomCount++;
      }
    }
  }
  for (let i = 0; i < M; i++) { 
    for (let j = 0; j < N; j++) { 
      const rn = roomNumberMap[i][j];
      const rn1 = j + 1 < N ? roomNumberMap[i][j + 1] : 0;
      const rn2 = i + 1 < M ? roomNumberMap[i + 1][j] : 0;
      if (rn !== rn1) {
        const sum = roomArea[rn] + roomArea[rn1];
        breakWallArea = Math.max(breakWallArea, sum);
      }
      if (rn !== rn2) { 
        const sum = roomArea[rn] + roomArea[rn2];
        breakWallArea = Math.max(breakWallArea, sum);
      }
    }
  }
  console.log((roomCount-1) + '\n' + maximumArea + '\n' + breakWallArea);
})

function search(r, c, roomNum) { 
  let count = 1;
  roomNumberMap[r][c] = roomNum;
  for (let i = 0; i < 4; i++) { 
    const mr = r + dr[i];
    const mc = c + dc[i];
    if (mr >= M || mc >= N || mr < 0 || mc < 0) continue;
    if (visited[mr][mc]) continue;
    let isPossible = false;
    switch (i) { 
      case 0:
        // 동으로 전진 (= 해당 위치에 서쪽 벽이 없어야 함)
        isPossible = (map[mr][mc] & 1) === 0;
        break;
      case 1:
        // 서로 전진 (= 해당 위치에 동쪽 벽이 없어야 함)
        isPossible = (map[mr][mc] & 4) === 0;
        break;
      case 2:
        // 남으로 전진 (= 해당 위치에 북쪽 벽이 없어야 함)
        isPossible = (map[mr][mc] & 2) === 0;
        break;
      case 3:
        // 북으로 전진 (= 해당 위치에 남쪽 벽이 없어야 함)
        isPossible = (map[mr][mc] & 8) === 0;
        break;
    }
    if (isPossible) { 
      visited[mr][mc] = true;
      count += search(mr, mc, roomNum);
    }
  }
  return count;
}