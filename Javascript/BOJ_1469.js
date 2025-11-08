const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1469.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, X = [], whole_length = 0;
let answer = "";

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    whole_length = N * 2;
  } else { 
    X = line.split(" ").map(Number).sort((a, b) => a - b);
  }
  ln++;
});

rl.on("close", () => {
  search(0, new Array(17).fill(-1), []);
  console.log(answer ? answer : -1);
});

function search(now, pos, set) { 
  if (answer) return;
  if (now === whole_length) { 
    for (const e of X) { 
      if (pos[e] != -2) return;
    }
    answer = set.join(" ");
    return;
  }
  for (const element of X) { 
    if (pos[element] === -1 && whole_length > now + element + 1) {
      // 1. 해당 원소가 수열 S에 등장한 적 없는 경우
      // 수열에 추가하고, 등장 위치를 기록
      set.push(element);
      pos[element] = now;
      search(now + 1, pos, set);
      set.pop();
      pos[element] = -1;
    } else if(pos[element] >= 0) { 
      // 2. 해당 원소가 수열 S에 등장한 적이 있는 경우
      // 2번 조건을 만족하는지 확인
      if (element === now - pos[element] - 1) { 
        const before = pos[element];
        set.push(element);
        pos[element] = -2;
        search(now + 1, pos, set);
        set.pop();
        pos[element] = before;
        return;
      }
    }
  }
}