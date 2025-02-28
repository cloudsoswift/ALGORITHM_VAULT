// 다른 코드 보고 좀 보완한 코드
// 원 아이디어
// 0. 탐색 범위의 하한과 상한인 bound와 maximum를 0으로 초기화한다.
// 1. 각 동전들을 액면가 오름차순으로 정렬한 뒤 순회한다. (i 사용)
// 1-1. 경우를 따지는 탐색의 하한인 bound를 0으로 초기화한다.
// 2. 현재 동전(i번째)의 갯수만큼 아래 계산을 반복한다. (j 사용)
// 2-1. maximum에 현재 동전의 액면가 만큼 더한다.
// 2-2. maximum부터 bound + 현재 동전 액면가까지 k를 감소시키며 순회한다.
// (maximum은 현재 동전을 j개 사용했을 때 도달할 수 있는 가능한 가장 높은 금액을 의미)
// (ex. 100원짜리 동전 3개가 있을때, 도달할 수 있는 최대 높은 금액은 300원으로, j를 0부터 2까지 증가시키며 maximum을 300까지 증가시킬 수 있음)
// 2-3. 만약 arr[k - 현재 동전 액면가] 가 true면, arr[k] 도 true로 만든다.
// 즉, 현재 금액(k - 현재 동전 액면가)에 도달하는 경우의 수가 있는 경우, 현재 동전을 놓은 금액(k)도 가능하다는 것
// ===================
// 여튼 이런 방식으로 하면 상한-하한 범위 폭이 많이 좁아지므로 빠를 것이라고 생각했으나
// 동전의 갯수만큼 (불가능한 구간도) 계속해서 반복할 수 있기 때문에 시간 복잡도가 좋지 않게 나옴
// 그래서 이러한 방식을 버리고 그냥 "총 금액의 절반"에서 "현재 동전 액면가"까지 한 번씩만 훝고, 
// 만약 가능한 경우 찾았을 때 그때 동전 갯수만큼 경우 따져보도록 바꿈

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1943.txt'),
  output: process.stdout,
});

let cnt = 0;
let coins = [];
let answer = "";
let N = 0, total_value = 0, half_value = 0;
let isPossible = false;

rl.on('line', (line) => {
  if (N === 0) {
    N = Number(line);
    coins = [];
    cnt = 1;
    total_value = 0;
    half_value = 0;
    isPossible = false;
  } else { 
    const split_words = line.split(" ").map(Number);
    coins.push([...split_words]);
    total_value += (split_words[0] * split_words[1]);
    if (cnt === N) { 
      if (total_value % 2 !== 0) {
        // 총액이 홀수라면 애초에 공평하게 반절 나누는게 불가능
        N = 0;
        answer = answer.concat(`0\n`);
        cnt++;
        return;
      }
      half_value = total_value / 2;
      calcPossibility();
      N = 0;
    }
    cnt++;
  }
});

rl.on('close', () => {
  console.log(answer);
});

function calcPossibility() { 
  const arr = new Array(half_value + 1).fill(false);
  arr[0] = true;
  for (let i = 0; i < N; i++) { 
    // 각 종류의 동전들 순회
    const [value, count] = coins[i];
    for (let j = half_value; j >= value; j--) { 
      if (arr[j - value]) {
        // 현재 j-value 원 이나온 경우가 있다면 현재 동전인 value원을 놓는 j도 가능하단 것
        for (let k = 1; k <= count && j + (k - 1) * value <= half_value; k++)
          // 1 ~ count개의 동전 사용한 경우 모두 기록
          arr[j + value * (k-1)] = true;
      }
    }
    if (arr[half_value]) break;
  }
  isPossible = arr[half_value];
  answer = answer.concat(`${isPossible ? "1" : "0"}\n`);
}