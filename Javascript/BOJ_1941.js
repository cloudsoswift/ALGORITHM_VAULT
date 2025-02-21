const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1941.txt").
  toString().trim().split("\n");
// 격자의 한 축 크기 N
const N = 5;
// 칠공주 구성원은 최대 7명
const MAX_COUNT = 7;
// bfs 탐색에 사용할 [동, 서, 남, 북] 배열
const dr = [0, 0, 1, -1];
const dc = [1, -1, 0, 0];
// [i][j]의 학생이 S인지 Y인지 기록하는 배열
const map = Array(N);
// [i][j]의 학생이 칠공주로 선택되었는지 여부 기록하는 배열
const v = Array(N);
for (let i = 0; i < N; i++) { 
  map[i] = Array(N);
  v[i] = Array(N).fill(false);
  for (let j = 0; j < N; j++) { 
    map[i][j] = input[i].at(j);
  }
}
// 칠공주 결성 가능한 경우의 수 기록하는 변수
let NoC = 0;

// 수열 탐색 시작
permutation(0, map, 0, 0, v);
console.log(NoC);

/* 
  now: 현재 탐색중인 학생 번호(이를 5로 나눈 값이 r, 5로 나눈 나머지 값이 c)
  map: 25명의 학생들 기록된 맵
  scount: 현재까지 선택된 학생 중 S(이다솜파)가 몇명인지
  count: 현재까지 선택된 학생 수
  visited: 현재까지 선택된 학생 기록한 5x5 배열
*/
function permutation(now, map, scount, count, visited) { 
  // 현재 남은 탐색가능한 학생 수가, 7명을 고르기 위해 남은 학생 수 보다 작은 경우 
  // 남은 학생을 다 선택해도 7명이 안되므로 탈출
  if (25 - now < MAX_COUNT - count) return;
  if (count === 7) { 
    // 칠공주 중 S(이다솜파)가 4명 미만인 경우 탈출
    if (scount < 4) return;
    // bfs에 사용할 배열
    const queue = [];
    // bfs에 사용할 방문 배열
    const tmp_visited = Array(N).fill().map(() => Array(N).fill(false));
    for (let i = 0; i < N; i++) { 
      for (let j = 0; j < N; j++) { 
        if (visited[i][j]) { 
          // [i][j] 학생이 칠공주로 선택된 경우
          // 인접한 학생 중 다른 칠공주로 선택된 학생들을 bfs로 탐색하며
          queue.push([i, j]);
          tmp_visited[i][j] = true;
          // 현재 bfs에서 확인한 학생의 수 카운팅하는 변수
          let tmp_count = 1;
          while (queue.length > 0) { 
            const now = queue.pop();
            for (let k = 0; k < 4; k++) { 
              const mr = now[0] + dr[k];
              const mc = now[1] + dc[k];
              if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
              if (tmp_visited[mr][mc] || !visited[mr][mc]) continue;
              tmp_visited[mr][mc] = true;
              queue.push([mr, mc]);
              tmp_count++;
            }
          }
          // bfs 탐색이 끝났을 때, 탐색하며 확인한 학생 수 !== 7 인 경우, 즉 칠공주 끼리 인접하지 않은 경우
          // 그냥 탈출, 7이면 칠공주 끼리 인접해있다는 뜻이므로 경우의 수 카운팅
          if (tmp_count == 7) NoC++;
          return;
        }
      }
    }
    return;
  }
  const r = Math.floor(now / 5);
  const c = now % 5;
  // 현재 학생을 칠공주에 포함시킨 경우
  visited[r][c] = true;
  permutation(now + 1, map, map[r][c] === "S" ? scount + 1 : scount, count + 1, visited);
  // 현재 학생을 칠공주에 포함시키지 않은 경우
  visited[r][c] = false;
  permutation(now + 1, map, scount, count, visited);
}