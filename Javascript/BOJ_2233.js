const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_2233.txt"),
  output: process.stdout,
});

let ln = 0, N = 0, binary = "", X = 0, Y = 0;

rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      N = Number(line);
      break;
    case 1:
      binary = line;
      break;
    case 2:
      [X, Y] = line.split(" ").map(Number).sort((a, b) => a - b);
      break;
  }
  ln++;
});

rl.on("close", () => { 
  const stack = [];
  // X번째 등장한 사과의 진입 위치 l, 반환 위치 r
  // Y번째 등장한 사과의 진입 위치 ll, 반환 위치 rr
  let l = binary.length, ll = binary.length, r = 0, rr = 0;
  let initialized = false;
  for (let i = 0, len = binary.length; i < len; i++) { 
    if (i === X-1) {
      // 첫 번째 썩은 사과
      if (binary.at(i) === "0") {
        // 0인 경우, 즉, 현재 진입한 사과인 경우
        l = i;
      } else {
        // 1인 경우, 즉, 현재 방문 끝내고 나오는 사과인 경우
        l = stack.at(-1);
        r = i;
      }
    }
    if(i === Y - 1) {
      // 두 번째 썩은 사과
      if (binary.at(i) === "0") {
        // 0인 경우, 즉, 현재 진입한 사과인 경우
        ll = i;
      } else {
        // 1인 경우, 즉, 현재 방문 끝내고 나오는 사과인 경우
        ll = stack.at(-1);
        rr = i;
      }
    }  
    if (binary.at(i) === "0") {
      // 0인 경우, 즉 현재 사과에 진입하는 경우
      stack.push(i);
    } else { 
      const p = stack.pop();
      if (p === l) {
        // X번째 사과의 반환 위치를 찾은 경우, 기록
        r = i;
      }
      if (p === ll) { 
        // Y번째 사과의 반환 위치를 찾은 경우, 기록
        rr = i;
      }
      if (i >= Y-1 && r > 0 && rr > 0) { 
        // Y번째 사과 초기 정보를 기록했고, (i >= Y-1)
        // X번째 사과 정보를 완전히 기록했고, (r > 0)
        // Y번째 사과 정보를 완전히 기록한 경우 (rr > 0)
        if (!initialized) { 
          //  (l, r), (ll, rr) 두 구간을 포함하는 양 끝(l, r)을 재정의
          l = Math.min(l, ll);
          r = Math.max(r, rr);
          initialized = true;
        }
        if (p <= l && i >= r) { 
          // 현재 구간 (p, i)가 X와 Y 둘다 포함하는 경우 해당 구간 출력하고 종료
          console.log(`${p+1} ${i+1}`);
          return;
        }
      }
    }
  }
});