const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_14725.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, answer = "";
const rootMap = new Map();

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
  } else { 
    const split_string = line.split(" ");
    const K = Number(split_string[0]);
    let now_map = rootMap;
    for (let i = 0, str = ""; i < K; i++) { 
      str = split_string[i + 1];
      if (!now_map.has(str)) {
        now_map.set(str, new Map());
      }
      now_map = now_map.get(split_string[i + 1]);
    }
  }
  ln++;
});

rl.on('close', () => {
  dfs(rootMap, 0);
  console.log(answer);
});

function dfs(now_map, depth) { 
  // Javascript Map의 Key는 삽입 순서대로 정렬되어 있으므로,
  // 문제에서 요구하는 "같은 층에 여러개의 방이 있을 때에는 사전 순서가 앞서는 먹이 정보가 먼저 나온다" 라는 조건을
  // 달성하기 위해서는 Key의 정렬이 필요
  // 이를 위해, map.keys()를 통해 Key에 대한 MapIterator를 불러온 뒤
  // Array.from()을 통해 배열 형태로 전환하고, sort를 통해 정렬
  const keys = Array.from(now_map.keys()).sort();
  for (const k of keys) { 
    // depth(층) * 2만큼 '-'를 반복한 뒤 현재 층의 먹이 이름 합쳐 정답 문자열에 추가
    answer += `${"-".repeat(depth * 2) + k}\n`;
    const v = now_map.get(k);
    if (v.size > 0) { 
      dfs(v, depth + 1);
    }
  }
}