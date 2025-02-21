// const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.stdin,
  // input: fs.createReadStream("./input/BOJ_2836.txt"),
  output: process.stdout,
});

let ln = 0, arr = [], N = 0, M = 0;
let minimum = 0;

rl.on('line', (line) => {
  if (ln === 0) {
    [N, M] = line.split(" ").map(Number);
    arr = new Array(N).fill().map(_ => new Array(2).fill(0));
    minimum = M;
  } else { 
    arr[ln - 1] = line.split(" ").map(Number);
  }
  ln++;
});

rl.on('close', () => {
  // 수상 택시 도착지가 승차지보다 먼 경우, 즉 arr[i][0] < arr[i][1] 인 경우는
  // 상근이가 M까지 가는데 있어 이동거리를 늘리는데 기여하지 않으므로 고려 대상에서 제외
  // 0에서 M까지 가는걸 거스르게 하는, 즉 arr[i][0] > arr[i][1] 인 경우만 고려해야 함.
  arr = arr.filter(v => v[0] > v[1]).sort((a, b) => a[0] > b[0] ? 1 : (a[0] === b[0] ? (a[1] > b[1] ? 1 : a[1] === b[1] ? 0 : -1) : -1));
  
  // 거스르는 친구가 없으면 별도 계산 X
  if(arr.length > 0) {
      let [from, to] = arr[arr.length - 1];
      for (let i = arr.length - 2; i >= 0; i--) { 
        const [f, t] = arr[i];
        if (f >= to) {
          // arr[i][0] >= arr[i+1][1] 인 경우, 즉 arr[i]를 포함해서 한 번에 처리할 수 있는 경우
          // 포함시킴
          to = Math.min(t, to);
        } else { 
          // 그렇지 않으면 현재 묶음에 포함하지 않고 기존의 묶음 최단 거리에 합산 후
          minimum += (from - to) * 2;
          // 새롭게 묶음 정의
          from = f;
          to = t;
        }
      }
      minimum += (from - to) * 2;
  }
  console.log(minimum);
});