const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_22866.txt'),
  output: process.stdout,
});

// 건물 개수 N과 각 건물의 높이 기록하는 배열 buildings
let N = 0;
let buildings = [];

rl.on('line', (line) => {  
  if (N === 0) {
    N = Number(line);
  } else { 
    buildings = line.split(" ").map(Number);
  }  
});

rl.on('close', () => {
  // buildingCount[i][0] = i+1번째 건물에서 볼 수 있는 건물의 개수
  // buildingCount[i][1] = i+1번째 건물과 가장 가까운 건물 중 건물 번호가 가장 작은 건물의 번호
  const buildingCount = new Array(N).fill().map(_ => [0, N + 5]);

  let stack = [];
  // 1번째 ~ N번째 까지 각 건물에서 왼쪽으로 봤을때 볼 수 있는 건물 카운팅
  for (let i = 0; i < N; i++) { 
    if (stack.length > 0) {
      // i + 1 번 건물 왼쪽방향 건물 중 i + 1 번 건물보다 높이가 같거나 낮은 건물들은 싹다 제외
      while (stack.length > 0 && buildings[stack.at(-1)] <= buildings[i]) { 
        stack.pop();
      }
      buildingCount[i][0] += stack.length;
      if (stack.length > 0) { 
        buildingCount[i][1] = stack.at(-1);
      }
    }
    stack.push(i);
  }
  stack = [];
  // N번째 ~ 1번째까지 각 건물에서 오른쪽으로 봤을때 볼 수 있는 건물 카운팅
  for (let i = N - 1; i >= 0; i--) { 
    if (stack.length > 0) { 
      
      // i + 1 번 건물 오른쪽방향 건물 중 i + 1 번 건물보다 높이가 같거나 낮은 건물들은 싹다 제외
      while (stack.length > 0 && buildings[stack.at(-1)] <= buildings[i]) { 
        stack.pop();
      }
      buildingCount[i][0] += stack.length;
      if (stack.length > 0) { 
        if (calcDiff(i, stack.at(-1)) < calcDiff(i, buildingCount[i][1])) { 
          // 기존에 기록되어 있던 "거리가 가장 가까운 건물의 번호"가 있다면, 이는 i보다 왼쪽인 값이기 때문에
          // 무조건 stack.at(-1) (i+1번째 건물에서 오른쪽으로 봤을때 가장 가까운 건물)보다 건물 번호가 낮음
          // 따라서 stack.at(-1) + 1 번 건물이 "거리가 가장 가까운 건물의 번호"로 기록될려면 기존 값보다 i + 1 번 건물과 더 가까워야 함
          buildingCount[i][1] = stack.at(-1);
        }
      }
    }
    stack.push(i);
  }
  let answer = "";
  for (let i = 0; i < N; i++) { 
    // 볼 수 있는 건물 개수가 1 이상이어야 "거리가 가장 가까운 건물의 번호"까지 함께 출력
    if (buildingCount[i][0] > 0) {
      answer += `${buildingCount[i][0]} ${buildingCount[i][1]+1}\n`;
    } else { 
      answer += "0\n";
    }
  }
  console.log(answer);
});

// from번 건물과 to번 건물 사이의 거리 반환하는 함수
function calcDiff(from, to) { 
  return Math.abs(from - to);
}