const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_19236.txt'),
  output: process.stdout,
});

const FISHES_COUNT = 16;
let ln = 0, n = 4;
let MAXIMUM_POINT = 0;
const map = new Array(4).fill().map(_ => new Array(4).fill(0));
// 각 물고기의 위치와 방향을 저장하는 배열 position, dir
// index 0에는 상어의 정보가 있음
const position = new Array(17).fill().map(_ => []);
const dir = new Array(17).fill(0);
// [북, 북서, 서, 남서, 남, 남동, 동, 북동]
const dr = [-1, -1, 0, 1, 1, 1, 0, -1];
const dc = [0, -1, -1, -1, 0, 1, 1, 1];

rl.on("line", (line) => { 
  const split = line.split(" ").map(Number);
  for (let i = 0; i < 4; i++) { 
    const num = split[i * 2];
    map[ln][i] = num;
    dir[num] = split[i * 2 + 1] - 1;
    position[num] = [ln, i];
  }
  ln++;
});

rl.on("close", () => {
  // 처음에 상어는 [0, 0] 위치에 있는 물고기를 잡아먹고,
  // 해당 위치에서 시작
  const initial_victim = map[0][0];
  position[initial_victim] = [-1, -1];
  map[0][0] = -1;
  position[0] = [0, 0];
  dir[0] = dir[initial_victim];
  fishes_move(map, position, dir, initial_victim);
  console.log(MAXIMUM_POINT);
});

function fishes_move(map, position, dir, point) { 
  // 1번부터 물고기 번호 오름차순으로 이동 수행
  for (let i = 1; i <= FISHES_COUNT; i++) {
    const [r, c] = position[i];
    // 죽은 물고기는 이동 X
    if (r === -1 && c === -1) continue;
    let d = dir[i];
    for (let j = 0; j < 8; j++) { 
      const md = (d + j) % 8;
      const mr = r + dr[md], mc = c + dc[md];
      // 1. 공간의 경계를 넘는 경우 이동 불가
      if (mr >= n || mc >= n || mr < 0 || mc < 0) continue;
      // 2. 상어가 있는 칸은 이동 불가
      if (map[mr][mc] === -1) continue;
      // 3. 빈 칸인 경우 해당 칸으로 이동
      if (map[mr][mc] === 0) { 
        position[i] = [mr, mc];
        map[mr][mc] = i;
        map[r][c] = 0;
        dir[i] = md;
        break;
      }
      // 4. 물고기가 있는 경우 서로 위치 바꿈
      if (map[mr][mc] >= 1) { 
        swap_fish(map, position, i, map[mr][mc]);
        dir[i] = md;
        break;
      }
    }
  }
  // 이후 상어 움직임
  shark_move(map, position, dir, point);
}

function shark_move(map, position, dir, point) { 
  // 이때까지 잡아먹은 물고기 번호 합(point)이
  // 기존 최댓값(MAXIMUM_POINT) 보다 큰 경우 갱신
  if (point > MAXIMUM_POINT) { 
    MAXIMUM_POINT = point;
  }
  // 현재 상어 위치와 방향
  const [r, c] = position[0];
  const d = dir[0];
  let mr = r + dr[d], mc = c + dc[d];
  // 해당 방향 끝까지 탐색
  while (mr >= 0 && mc >= 0 && mr < n && mc < n) {
    // 만약 물고기 발견한 경우
    if (map[mr][mc] > 0) { 
      const nmap = map.map(inner => [...inner]);
      const nposition = position.map(inner => [...inner]);
      const ndir = [...dir];
      const victim = nmap[mr][mc];
      // map, position, dir 등 배열을 깊은 복사 수행해 전달
      // (상태 수정 -> 다음 상태 탐색(DFS) -> 상태 복원 거치는 대신
      // 애초에 상태들을 복사해 수정한 뒤 다음 탐색의 인자로 넘김)
      // 해당 물고기를 잡아 먹는 경우를 탐색
      nmap[mr][mc] = -1;
      nposition[victim] = [-1, -1];
      nposition[0] = [mr, mc];
      ndir[0] = ndir[victim];
      nmap[r][c] = 0;
      fishes_move(nmap, nposition, ndir, point + victim);
    }
    mr += dr[d];
    mc += dc[d];
  }
}

function swap_fish(map, position, A, B) { 
  // 두 물고기 간 위치 변경하는 함수
  // 변경된 위치를 position, map 배열에 모두 반영
  const temp = [...position[A]];
  position[A] = [...position[B]];
  position[B] = [...temp];
  
  map[position[A][0]][position[A][1]] = A;
  map[position[B][0]][position[B][1]] = B;
}