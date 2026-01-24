// 등장하는 건물들이 모두 직사각형이기 때문에, 건물의 왼쪽(┌) 과 오른쪽(┐)이 같음.
// 따라서 stack으로 이전보다 y가 오르는 구간의 y를 저장하고, 내려가는 구간에서 방출하는 형태로
// 건물 왼쪽 지점을 관리하여 문제 풂

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1863.txt'),
  output: process.stdout,
});

let ln = 0, n = 0;
const stack = [], building_points = [];

rl.on("line", (line) => { 
  if (ln === 0) {
    n = Number(line);
  } else { 
    building_points.push(line.split(" ").map(Number));
  }
  ln++;
});

rl.on("close", () => {
  let count = 0;
  // 스카이라인 고도변화 지점이 x 오름차순으로 등장한다는 보장 없으므로 일단 x 기준으로 정렬
  building_points.sort((a, b) => a[0] - b[0]);
  for (const [x, y] of building_points) { 
    // 이전에 등장한 변화 지점들 중 가장 최근 등장값보다 크면 stack에 추가
    if (y > 0 && (stack.length === 0 || y > stack.at(-1))) {
      stack.push(y);
    } else { 
      // 최근 등장값보다 작은 경우,
      while (stack.length > 0 && stack.at(-1) > y) { 
        // 현재 지점 y 이하인 지점이 나올때까지 pop 및 건물 카운트 증가
        stack.pop();
        count++;
      }
      if (y > 0 && (stack.length === 0 || y > stack.at(-1))) {
        // 이후, 현재 지점을 stack에 추가할 수 있는지 확인 후 가능하면 추가
        stack.push(y);
      }
    }
  }
  while (stack.length > 0) { 
    stack.pop();
    count++;
  }
  console.log(count);
});