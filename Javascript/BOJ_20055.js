let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_20055.txt"
).toString().trim().split('\n');
const [N, K] = input[0].split(" ").map(Number);
const belt_len = N * 2;
let conveyorBelt = input[1].split(" ").map(Number);
let robotsBelt = new Array(belt_len).fill(0);
const robots = {};
let from = 0, to = N - 1, time = 1;
function forward(v) { 
  return (v + 1) % belt_len;
}
let count = 0;
while (true) { 
  // 1. 벨트가 각 칸의 로봇과 함께 한 칸씩 회전
  from = (from - 1 + belt_len) % belt_len;
  to = (to - 1 + belt_len) % belt_len;
  if (robotsBelt[to] != 0) { 
    delete robots[robotsBelt[to]];
    robotsBelt[to] = 0;
  }

  // 2. 먼저 벨트에 올라간 로봇부터, 회전 방향으로 한 칸씩 이동 가능하면 이동. 못하면 가만히 있기
  for (const i in robots) { 
    // 벨트에서 내려간 로봇이면 건너뛰기
    let pos = forward(robots[i]);
    if (robotsBelt[pos] === 0 && conveyorBelt[pos] > 0) { 
      robotsBelt[robots[i]] = 0;
      conveyorBelt[pos]--;
      if (conveyorBelt[pos] === 0) count++;
      if (pos === to) { 
        delete robots[i];
        continue;
      }
      robots[i] = pos;
      robotsBelt[pos] = i;
    }
  }
  // 3. 올리는 위치에 있는 칸 내구도 1 이상이면 로봇 올리기
  if (conveyorBelt[from] > 0) { 
    robotsBelt[from] = time;
    robots[time] = from;
    conveyorBelt[from]--;
    if (conveyorBelt[from] === 0) count++;
  }
  // 4. 내구도 0인 칸 개수 K개 이상이면 종료
  if (count >= K) break;
  time++;
}
console.log(time);