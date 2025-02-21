const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_9663.txt'),
  output: process.stdout,
})

// 체스판의 한 축의 길이 N, [i]열의 몇 번 행에 퀸이 놓였는지 기록하는 배열 board[], 경우의 수 세는 count
let N = 0, board = [], count = 0;

rl.on('line', (line) => {
  N = Number(line);
  board = new Array(N).fill(0);
});

rl.on('close', () => {
  doQueen(0);
  console.log(count);
});

function doQueen(depth) { 
  if (depth === N) { 
    count++;
    return;
  }
  for (let i = 0; i < N; i++) { 
    // (depth, i) 위치에 배치 가능한지 확인
    let possible = true;
    for (let j = 0; j < depth; j++) { 
      // 이미 배치된 0 ~ depth - 1 번 열들 확인
      if (board[j] === i || (Math.abs(depth - j) === Math.abs(i - board[j]))) { 
        // 1) j 열에 배치된 퀸이 i행 인 경우 => 불가능
        // 2) j 열에 배치된 퀸(j, board[j])이 (depth, i)의 대각선에 있는 경우,
        // 즉, depth와 j의 차이 === i와 board[j] 인 경우 => 불가능
        possible = false;
        break;
      }
    }
    if (possible) { 
      board[depth] = i;
      doQueen(depth + 1);
    }
  }
}