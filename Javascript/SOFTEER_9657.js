const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/SOFTEER_9657.txt'),
  output: process.stdout,
});

// 격자의 크기 n, m 과 환경 파괴범의 수 count
let n = 0, m = 0, ln = 0, count = 0;
// 두 개의 공격 정보 담는 배열 waves
let waves = [];
let map = [];

rl.on('line', (line) => {
  if (ln === 0) {
    [n, m] = line.split(" ").map(Number);
    map = new Array(n).fill().map(_ => new Array(m).fill(false));
  } else if (ln <= n) {
    const splited = line.split(" ");

    for (let i = 0; i < m; i++) { 
      // 환경 파괴범이 있는 곳은 true, 없는 곳은 false
      if (splited[i] === "1") { 
        map[ln - 1][i] = true;
        count++;
      } else {
        map[ln - 1][i] = false;
      }
    }
  } else { 
    const splited = line.split(" ").map(Number);
    waves.push(splited);
  }
  ln++;
});

rl.on('close', () => {
  waves.forEach(([L, R]) => { 
    loop:
    for (let i = L - 1; i < R; i++) { 
      for (let j = 0; j < m; j++) { 
        if (map[i][j]) { 
          map[i][j] = false;
          count--;
          continue loop;
        }
      }
    }
  })
  console.log(count);
  
})