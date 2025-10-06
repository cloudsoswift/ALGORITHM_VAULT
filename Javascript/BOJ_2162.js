// 선분 교차 검출하는 방법 찾아서 푼 문제
// 참고: https://killerwhale0917.tistory.com/6

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2162.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, lines = [];
let parent = [];

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    parent = new Array(N).fill(0).map((_, i) => i);
  } else { 
    const l = line.split(" ").map(Number);
    lines.push(l);
  }
  ln++;
});

rl.on("close", () => {
  for (let i = 0; i < N; i++) { 
    for (let j = i + 1; j < N; j++) { 
      // 이미 같은 그룹인 경우 교차하는지 확인 X
      if (find(i) === find(j)) continue;
      if (isIntersect(lines[i], lines[j])) { 
        // 교차하는 경우, 같은 그룹으로 연결
        union(i, j);
      }
    }
  }
  const count = new Array(N).fill(0);
  let type_count = 0, MAX = 0;
  for (let i = 0; i < N; i++) { 
    // 이전에 카운트 한 적 없는 그룹이면 그룹 갯수 증가
    if (count[find(i)] === 0) type_count++;
    count[parent[i]]++;
    MAX = Math.max(MAX, count[parent[i]]);
  }
  console.log(type_count);
  console.log(MAX);
});

function union(A, B) {
  // 그룹 처리를 위한 union 연산
  const parentA = find(A);
  const parentB = find(B);
  if (parentA < parentB) {
    parent[parentB] = parentA;
    find(B);
  } else { 
    parent[parentA] = parentB;
    find(A);
  }
}

function find(A) { 
  // 그룹 처리를 위한 find 연산
  if (parent[A] === A) return A;
  else return parent[A] = find(parent[A]);
}

function isIntersect(line1, line2) {
  // 두 선분이 겹치는지 확인하는 함수
  // CCW를 사용하여 각 선 A(p1, p2), B(p3, p4)에 대해 A의 벡터(p1, p2)를 기반으로
  // B의 각 점까지의 벡터((p1, p2, p3), (p1, p2, p4))의 곱(p1p2)과
  // B를 기반으로 A의 각 점까지의 벡터의 곱(p3p4)이 모두 0 이하인 경우,
  // 즉 한 선을 기준으로 다른 선의 각 끝점이 시계, 반시계 방향인 경우 교차하는 상황임을 확인할 수 있음
  // 또한, 만약 두 선분이 일직선 상에 존재하는 경우 p1 <= p3 <= p2 <= p4라면 교차한다고 볼 수 있음

  let p1 = [line1[0], line1[1]];
  let p2 = [line1[2], line1[3]];
  let p3 = [line2[0], line2[1]];
  let p4 = [line2[2], line2[3]];
  
  // line1 기준에서 line2의 각 점을 ccw한 값 곱
  const p1p2 = ccw(...p1, ...p2, ...p3) * ccw(...p1, ...p2, ...p4);
  // line2 기준에서 line1의 각 점을 ccw한 값 곱
  const p3p4 = ccw(...p3, ...p4, ...p1) * ccw(...p3, ...p4, ...p2);
  
  // 두 직선이 일직선 상에 존재하는 경우
  if (p1p2 === 0 && p3p4 === 0) {
    // 두 직선이 겹치는 경우인지 확인
    // p1 > p2인 경우, swap
    if (compare(p1, p2) > 0) { 
      const temp = [...p2];
      p2 = [...p1];
      p1 = temp;
    }
    // p3 > p4인 경우, swap
    if (compare(p3, p4) > 0) { 
      const temp = [...p4];
      p4 = [...p3];
      p3 = temp;
    }
    // p1 - p3 - p2 - p4 순이어야 겹친 것
    return compare(p3, p2) <= 0 && compare(p1, p4) <= 0;
  }
  return p1p2 <= 0 && p3p4 <= 0;
}

function compare(p1, p2) { 
  // 두 점의 비교 수행하는 연산

  // 1. p1의 x값이 p2의 x값보다 크면 -1/작으면 1 반환
  if (p1[0] < p2[0]) return -1;
  if (p1[0] > p2[0]) return 1;

  // 2. p1의 y값이 p2의 y값보다 크면 -1/작으면 1 반환
  if (p1[1] < p2[1]) return -1;
  if (p1[1] > p2[1]) return 1;

  // 3. x, y 값 모두 같으면 0 반환
  return 0;
}

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