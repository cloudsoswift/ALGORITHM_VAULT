const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: fs.createReadStream('./input/BOJ_7453.txt'),
  // input: process.stdin,
  output: process.stdout,
})

let ln = 0, n = 0, A = [], B = [], C = [], D = [];

rl.on('line', (line) => {
  if (ln === 0) { 
    n = Number(line);
    A = new Array(n).fill(0);
    B = new Array(n).fill(0);
    C = new Array(n).fill(0);
    D = new Array(n).fill(0);
  } else { 
    const splited = line.split(" ").map(Number);
    A[ln - 1] = splited[0];
    B[ln - 1] = splited[1];
    C[ln - 1] = splited[2];
    D[ln - 1] = splited[3];
  }
  ln++;
});

rl.on('close', () => {
  const arrAB = [];
  const arrCD = [];
  let sum = 0;
  for (let i = 0; i < n; i++) { 
    for (let j = 0; j < n; j++) { 
      arrAB.push(A[i] + B[j]);
      arrCD.push(C[i] + D[j]);
    }
  }
  arrAB.sort((a, b) => a > b ? 1 : (a === b ? 0 : -1));
  arrCD.sort((a, b) => a > b ? 1 : (a === b ? 0 : -1));
  for (const v of arrAB) { 
    sum += upper_bound(-v, arrCD) - lower_bound(-v, arrCD);
  }
  console.log(sum);
});

// value 바로 다음으로 큰 값의 index 구하는 함수
function upper_bound(value, arr) { 
  let left = 0, right = arr.length;
  while (left < right) { 
    const mid = Math.floor((left + right) / 2);
    if (arr[mid] > value) right = mid;
    else left = mid + 1;
  }
  return right;
}

// value 값과 같거나 바로 다음으로 큰 값 중 더 앞에 있는 값의 index 구하는 함수
function lower_bound(value, arr) { 
  let left = 0, right = arr.length;
  while (left < right) { 
    const mid = Math.floor((left + right) / 2);
    if (arr[mid] >= value) right = mid;
    else left = mid + 1;
  }
  return right;
}