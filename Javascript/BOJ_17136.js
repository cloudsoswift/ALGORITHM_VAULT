const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_17136.txt'),
  output: process.stdout,
});

// 최소 색종이 사용 횟수를 나타내는 MINIMUM
let ln = 0, MINIMUM = 100;
// 0과 1이 적힌 종이
const N = 10, map = new Array(N).fill().map(_ => new Array(10).fill(0));
// 크기가 iXi 색종이가 몇 개 남아있는지 나타내는 배열 remain_papers[i]
const remain_papers = new Array(6).fill(5);
rl.on("line", (line) => {
  map[ln] = line.split(" ").map(Number);
  ln++;
});

rl.on("close", () => {
  search(0, 0, 0);
  // 최소 색종이 사용 횟수가 갱신된 경우(즉, 100이 아닌경우) 그를 출력하고,
  // 이외의 경우(즉, 1을 모두 덮는게 불가능한 경우) -1를 출력
  console.log(MINIMUM === 100 ? -1 : MINIMUM);
});

// (r, c)를 왼쪽 위 꼭짓점으로 하는 정사각형의 최대 길이 구하는 함수
function measure_square(r, c) { 
  // 반환할 정사각형 길이 len
  let len = 1;
  for (let i = 1; i <= 5; i++) {
    // 색종이는 종이 경계 밖을 나갈 수 없음
    if (r + i >= N || c + i >= N)
      return len;
    for (let j = 0; j <= i; j++) { 
      // 0이 적힌 칸엔 색종이가 있어선 안 됨
      if (map[r + i][c + j] !== 1 || map[r + j][c + i] !== 1)
        return len;
    }
    len++;
  }
  return len;
}

// 종이의 (r, c)부터 (r+len-1, c+len-1)까지의 정사각형을 주어진 fill_number로 채우는 함수
function cover_square(fill_number, r, c, len) { 
  for (let i = 0; i < len; i++) { 
    for (let j = 0; j < len; j++) { 
      map[r + i][c + j] = fill_number;
    }
  }
}

// 색종이 채우기 진행하는 함수
// 현재 위치 (r, c)와 사용한 색종이 개수 count
function search(r, c, count) { 
  // 최소 색종이 사용 횟수가 갱신됐고, 현재 케이스에서 사용한 색종이 개수 이하인 경우
  // 더 탐색해도 최소 색종이 사용 횟수를 갱신할 수 없으므로 중단
  if (MINIMUM !== 100 && count >= MINIMUM) return;
  for (let i = r; i < N; i++) { 
    for (let j = c; j < N; j++) { 
      if (map[i][j] === 1) { 
        let len = measure_square(i, j);
        for (let k = 5; k > 0; k--) { 
          // 색종이의 길이를 5부터 1까지 줄여가며,
          // 붙일 수 있는 색종이가 있다면 붙임
          if (len >= k && remain_papers[k] > 0) {
            // 지도에 색종이 붙인 부분을 0으로 채우고, 사용가능한 개수 감소
            cover_square(0, i, j, k);
            remain_papers[k]--;
            // 다음 탐색 진행
            search(i, j + k, count + 1);
            // 색종이 붙였던 부분 롤백(1로 다시 채움) 및 사용가능한 개수 원복
            cover_square(1, i, j, k);
            remain_papers[k]++;
          } else if (k === 1 && remain_papers[k] === 0)
            // 만약, 길이 1짜리 색종이를 써야하는데, 해당 색종이를 다 쓴 경우
            // 1을 덮을 수 없으므로 중단
            return;
        }
        // 위 조건문에서 색종이를 채우고, search 함수를 호출해 다음으로 진행하므로
        // 현재 함수에서 i, j를 더 진행하는 것은 의미가 없으므로 중단
        return;
      }
    }
    c = 0;
    if (i === N - 1) { 
      // i 가 종이 끝(가로축)에 다다랐고, 현재 line까지 왔다는 것은 j 역시 종이의 끝(세로축)에 다다랐음을 의미
      // 따라서, MINIMUM 값 갱신
      MINIMUM = Math.min(count, MINIMUM);
    }
  }
}