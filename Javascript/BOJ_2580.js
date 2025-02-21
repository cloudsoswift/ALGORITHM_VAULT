const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_2580.txt"),
  output: process.stdout,
});

let ln = 0, isDone = false;
const sudoku = new Array(9).fill().map(_ => new Array(9).fill(0));
const N = 9;
const spotToFill = [];
// 3x3 정사각형 9개에 대해 각각 어떤 숫자가 이미 적혀 있는지 비트로 기록하는 정수 배열
const divCondition = new Array(3).fill().map(_ => new Array(3).fill(0));
// 3x3 정사각형 9개에 대해 각각 어떤 숫자가 가능한지(= 안 적혀있는지) 기록하는 3차원 배열
const possibleCaseByDiv = new Array(3).fill().map(_ => new Array(3).fill().map(_ => []));
// 각 열마다 어떤 숫자가 이미 적혀 있는지 기록하는 배열
const verticalCondition = new Array(N).fill(0);
// 각 행마다 어떤 숫자가 이미 적혀 있는지 기록하는 배열
const horizontalCondition = new Array(N).fill(0);

rl.on("line", (line) => {
  splited = line.split(" ").map(Number);
  for (let i = 0; i < N; i++) { 
    sudoku[ln][i] = splited[i];
    // 빈 칸(0)인 경우, 메워야 하는 칸으로 배열에 기록
    if (splited[i] === 0) spotToFill.push([ln, i]);
    else {
      // 비어있지 않은 칸이면 
      const v = 1 << splited[i];
      // 각 3x3 정사각형에 이미 적혀있다고 비트로 기록
      divCondition[Math.floor((ln) / 3)][Math.floor(i / 3)] |= v;
      // i 열에 기록
      verticalCondition[i] |= v;
      // ln 행에 기록
      horizontalCondition[ln] |= v;
    }
  }
  ln++;
});

rl.on("close", () => {
  findDivision();
  doFill(0);
});

// spotToFill에 기록된 좌표들 하나씩 가능한 케이스 시도
function doFill(step) {
  // 이미 가능한 케이스 발견했으면 종료
  if (isDone) return;
  if (step === spotToFill.length) { 
    // spotToFill에 기록된 좌표들 온전한 스도쿠 이루는 형태로 모두 메운 경우
    isDone = true;
    // 스도쿠 출력
    console.log(sudoku.map((v) => v.join(" ")).join('\n'));
    return;
  }
  // 현재 step index의 좌표
  const now = spotToFill[step];
  // 현재 step index의 좌표에 가능한 케이스들 꺼내와서 탐색
  const nowCase = possibleCaseByDiv[Math.floor(now[0] / 3)][Math.floor(now[1] / 3)];
  for (const c of nowCase) { 
    if (checkHorizontally(now[0], c) && checkVertically(now[1], c) && checkDivison(now[0], now[1], c)) { 
      // 횡, 열, 3x3 모두 중복되지 않는 케이스인 경우
      // 전부 해당 값으로 기록
      horizontalCondition[now[0]] |= 1 << c;
      verticalCondition[now[1]] |= 1 << c;
      divCondition[Math.floor((now[0]) / 3)][Math.floor(now[1] / 3)] |= 1 << c;
      sudoku[now[0]][now[1]] = c;
      // 이후 다음 스텝으로
      doFill(step + 1);
      // 기록해놓은 값들 복원
      horizontalCondition[now[0]] &= ~(1 << c);
      verticalCondition[now[1]] &= ~(1 << c);
      divCondition[Math.floor((now[0]) / 3)][Math.floor(now[1] / 3)] &= ~(1 << c);
    }
  }
}

function checkVertically(i, v) { 
  return (verticalCondition[i] & 1 << v) === 0;
}

function checkHorizontally(j, v) { 
  return (horizontalCondition[j] & 1 << v) === 0;
}

function checkDivison(i, j, v) { 
  return (divCondition[Math.floor((i) / 3)][Math.floor(j / 3)] & 1 << v) === 0;
}

// 3x3 정사각형 9개에 대해 각각 어떤 숫자가 가능한지 미리 기록하는 함수
function findDivision() {
  for (let i = 0; i < 3; i++) { 
    for (let j = 0; j < 3; j++) { 
      for (let k = 1; k <= 9; k++) { 
        if ((divCondition[i][j] & 1 << k) === 0) possibleCaseByDiv[i][j].push(k); 
      }
    }
  }
}