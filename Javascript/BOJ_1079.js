// TODO: 최적화 여지가 있으니 추후 개선할 것

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1079.txt'),
  output: process.stdout,
});

let N = 0, ln = 0, eunjin = 0;
let R = [[]], sin_count = [];
let MAXIMUM_DAY = 0;
rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    R = new Array(N).fill().map(_ => new Array(N).fill(0));
  } else if (ln === 1) {
    sin_count = line.split(" ").map(Number);
  } else if (ln === N + 2) { 
    eunjin = Number(line);
  } else { 
    const split = line.split(" ").map(Number);
    for (let i = 0, len = split.length; i < len; i++) { 
      R[ln - 2][i] = split[i];
    }
  }
  ln++;
});

rl.on("close", () => {
  doMafia(0, N, 0, sin_count);
  console.log(MAXIMUM_DAY);
});

/*
  @param day 현재 밤을 몇 번 지났는지 나타내는 변수
  @param remainPeople 현재 남아있는 인원 수
  @param whoIsDead 각 참가자들이 살아있는지 죽었는지 기록하는 bitmask(i번째 비트가 1이면 i번 참가자 사망)
  @param sin_count 각 참가자의 유죄 지수를 기록하는 배열
**/
function doMafia(day, remainPeople, whoIsDead, sin_count) { 
  if (remainPeople % 2 === 0) {
    // 참가자가 짝수 -> 밤
    MAXIMUM_DAY = Math.max(MAXIMUM_DAY, day + 1);
    for (let i = 0; i < N; i++) { 
      // 이번 밤에 죽일 사람 (i) 정하기
      // i는 은진이나, 이미 죽은 사람은 선택할 수 없음
      if (i === eunjin || (whoIsDead & (1 << i)) > 0) continue;
      // 예상되는 다음날 낮에 죽을 사람 deadGuy
      let deadGuy = -1, deadCrimeNumber = 0;
      const copy = [...sin_count];
      for (let j = 0; j < N; j++) { 
        // 이번에 죽일 사람 or 이미 죽은 사람은 유죄 지수 갱신 안함
        if (i === j || (whoIsDead & (1 << j)) > 0) continue;
        copy[j] += R[i][j];
        if (copy[j] > deadCrimeNumber) {
          deadGuy = j;
          deadCrimeNumber = copy[j];
        }
      }
      if (deadGuy != eunjin) {
        doMafia(day + 1, remainPeople - 1, whoIsDead | (1 << i), copy);
      }
    }
  } else { 
    // 참가자가 홀수 -> 낮
    // 유죄 지수가 가장 높은 사람을 죽임
    let deadGuy = eunjin, deadCrimeNumber = sin_count[eunjin];
    for (let j = 0; j < N; j++) { 
      // 이미 죽은 사람은 선택 X
      if ((whoIsDead & (1 << j)) > 0) continue;
      if (sin_count[j] > deadCrimeNumber) {
        deadGuy = j;
        deadCrimeNumber = sin_count[j];
      } else if (sin_count[j] === deadCrimeNumber && j < deadGuy) { 
        deadGuy = j;
        deadCrimeNumber = sin_count[j];
      }
    }
    if (deadGuy !== eunjin) { 
      doMafia(day, remainPeople - 1, whoIsDead | (1 << deadGuy), sin_count);
    }
  }
}