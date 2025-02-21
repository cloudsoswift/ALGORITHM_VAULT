// 답 아이디어 보고 푼 문제
// CCW를 활용해, 세 빌딩 A, B, C 가 있을때 이 점의 꼭대기를 이은 점이
// 반시계 방향인지(즉, 가운데가 움푹 파였는지) 아닌지를 측정해
// A-C 사이에 가리는 빌딩이 없는지 측정
// CCW 식 -> https://www.acmicpc.net/blog/view/27 참조


const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1027.txt'),
  output: process.stdout,
});

let N = 0;
let buildings = [];

rl.on('line', (line) => {
  if (N === 0) {
    N = Number(line);
  } else { 
    buildings = line.split(" ").map(Number);
  }
});

function ccw(x1, y1, x2, y2, x3, y3) { 
  // 벡터의 외적을 활용해 세 점 A(x1, y1), B(x2, y2), C(x3, y3)를 이은 선이
  // 반시계 방향, 일직선, 시계 방향인지 알아내는 함수
  const v = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
  if (v > 0) {
    // v가 0 초과, 즉 ABC를 이은 선분이 반시계 방향인 경우
    return 1;
  } else if (v === 0) {
    // v가 0, 즉 ABC를 이은 선분이 일직선인 경우
    return 0;
  } else { 
    // v가 0 미만, 즉 ABC를 이은 선분이 시계 방향인 경우
    return -1;
  }
}

rl.on('close', () => {
  let answer = 0;
  for (let i = 0; i < N; i++) { 
    // 고층 빌딩들을 관측할 빌딩 번호 i
    // i에서 관측 가능한 빌딩 갯수 count
    let count = 0;
    frontLoop:
    for (let j = i - 1; j >= 0; j--) { 
      // i 앞에 놓인 빌딩들 탐색
      for (let k = i - 1; k > j; k--) { 
        // 빌딩 i와 j 사이에 있는 빌딩 k를 중간점으로 ccw 계산
        // 만약 중간에 반시계 방향을 만족하지 못하는 k가 존재하는 경우, 다음 j로 넘어감
        if (ccw(j, buildings[j], k, buildings[k], i, buildings[i]) <= 0) continue frontLoop;
      }
      count++;
    }
    rearLoop:
    for (let j = i + 1; j < N; j++) { 
      // i 뒤에 놓인 빌딩들 탐색
      for (let k = i + 1; k < j; k++) { 
        // 빌딩 i와 j 사이에 있는 빌딩 k를 중간점으로 ccw 계산
        // 만약 중간에 반시계 방향을 만족하지 못하는 k가 존재하는 경우, 다음 j로 넘어감
        if (ccw(i, buildings[i], k, buildings[k], j, buildings[j]) <= 0) continue rearLoop;
      }
      count++;
    }
    answer = Math.max(answer, count);
  }
  console.log(answer);
});