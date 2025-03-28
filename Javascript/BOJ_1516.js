const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream("./input/BOJ_1516.txt"),
  output: process.stdout
});

// BFS를 위한 Queue와 Node
class Node { 
  constructor(data) { 
    this.data = data;
    this.next = null;
  }
}
class Queue { 
  constructor() { 
    this.size = 0;
    this.head = null;
    this.tail = null;
  }
  append(data) { 
    const dataNode = new Node(data);
    if (this.size === 0) {
      this.head = dataNode;
      this.tail = dataNode;
    } else { 
      this.tail.next = dataNode;
      this.tail = this.tail.next;
    }
    this.size++;
  }
  pop() { 
    if (this.size === 0) return -1;
    const returnData = this.head.data;
    this.head = this.head.next;
    this.size--;
    return returnData;
  }
  isEmpty() { 
    return this.size === 0;
  }
}

// buildTime[i][0] : i번 건물을 짓는데 걸리는 시간
// buildTime[i][1] : i번 건물을 짓기 전, 먼저 지어져야 하는 건물들을 모두 짓는데 걸리는 시간
// fanOut[i]: i번 건물이 먼저 지어져야 하는 건물들의 번호를 기록하는 배열
// fanIn[i]: i번 건물을 짓기 전 먼저 지어져야 하는 건물들의 개수를 기록하는 배열
let ln = 0, N = 0, buildTime = [], fanOut = [], fanIn = [];

const queue = new Queue();

rl.on('line', (line) => {
  if (ln === 0) {
    N = Number(line);
    buildTime = new Array(N).fill().map(_ => new Array(2).fill(0));
    fanIn = new Array(N).fill(0);
    fanOut = new Array(N).fill().map(_ => new Array());
  } else { 
    const now = ln - 1;
    const splitLine = line.split(" ").map(Number);
    buildTime[now][0] = splitLine[0];
    for (let i = 1; i < splitLine.length - 1; i++) { 
      // now번 건물을 짓기 위해 먼저 지어야 할 건물들의 fanOut 배열에 now번을 추가
      fanOut[splitLine[i] - 1].push(now);
      // now번 건물을 짓기 위해 먼저 지어야 할 건물들의 개수 증가
      fanIn[now]++;
    }
    if (fanIn[now] === 0) { 
      // now번 건물을 짓기 위해 먼저 지어야 할 건물이 없는 경우, 해당 건물을 '지을 수 있는 건물 Queue'에 추가
      queue.append(now);
    }
  }
  ln++;
});

rl.on('close', () => { 
  // '현재 지을 수 있는 건물 번호' 들로 이루어진 Queue가 빌 때까지 원소를 꺼내어 건물 짓기 반복
  while (!queue.isEmpty()) { 
    // 현재 건물 번호 now
    const now = queue.pop();
    // now번 건물을 짓기 위한 비용은 'now번 건물만 짓는데 필요한 시간' 
    // + 'now번 건물을 짓기 위해 먼저 지어야 할 건물들 중 가장 시간이 많이 드는 건물의 건축 시간' 임
    const nowBuildTime = buildTime[now][0] + buildTime[now][1];
    // now번 건물을 먼저 지어야 하는 건물들의 번호(fanOut[now]의 원소들)를 순회
    for (const nextB of fanOut[now]) { 
      // 해당 건물의 '먼저 지어야 할 건물 수' 감소
      fanIn[nextB]--;
      // '해당 건물을 짓기 위해 먼저 지어야 할 건물들 중 가장 시간이 많이 드는 건물의 건축 시간' 비교 및 갱신
      buildTime[nextB][1] = Math.max(buildTime[nextB][1], nowBuildTime);
      if (fanIn[nextB] === 0) { 
        // 해당 건물의 '먼저 지어야 할 건물 수'가 0이 되었다면 해당 건물도 '현재 지을 수 있는 건물 번호' 큐에 추가
        queue.append(nextB);
      }
    }
  }
  let answer = '';
  for (let i = 0; i < N; i++) { 
    answer += `${(buildTime[i][0] + buildTime[i][1])}\n`;
  }
  console.log(answer);
})