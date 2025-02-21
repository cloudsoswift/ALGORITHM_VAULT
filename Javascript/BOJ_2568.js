const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: fs.createReadStream('./input/BOJ_2568.txt'),
  // input: process.stdin,
  output: process.stdout,
});

let ln = 0, N = 0, arr = [];
rl.on('line', (line) => { 
  if (ln === 0) {
    N = Number(line);
    arr = new Array(N).fill().map(_ => new Array(2).fill(0));
  } else { 
    const splited = line.split(" ").map(Number);
    arr[ln - 1][0] = splited[0];
    arr[ln - 1][1] = splited[1];
  }
  ln++;
})

rl.on('close', () => { 
  arr.sort((a, b) => a[0] > b[0] ? 1 : (a[0] === b[0] ? 0 : -1));
  // LIS[0] : 가장 긴 증가 부분 수열의 i 번째 순서 숫자 기록하는 배열
  // LIS[1] : i 번째로 임력된 숫자가 증가  부분 수열에서 몇 번째 자리인지 기록하는 배열
  const LIS = new Array(N).fill().map(_ => new Array(2).fill(0));
  // count: LIS 길이
  let count = 1;
  LIS[0][0] = arr[0][1];
  LIS[1][0] = 0;
  for (let i = 1; i < N; i++) { 
    if (LIS[0][count - 1] < arr[i][1]) {
      // 현재 LIS의 마지막 값보다 현재 값(arr[i][1])이 더 큰 경우
      // 해당 값 LIS에 포함
      LIS[0][count] = arr[i][1];
      LIS[1][i] = count++;
    } else { 
      // 그렇지 않을 경우 이분 탐색을 통해 arr[i][1]으로 대체할 수 있는 곳 탐색
      let left = 0, right = count;
      while (left < right) { 
        const mid = Math.floor((left + right) / 2);
        if (LIS[0][mid] < arr[i][1]) left = mid + 1;
        else right = mid;
      }
      LIS[0][left] = arr[i][1];
      LIS[1][i] = left;
    }
  }
  // [전봇대 갯수 - LIS 길이]가 잘라야 할 전깃줄 최소 갯수
  console.log(N - count);
  const result = [];
  let count_downer = count - 1;
  for (let i = N-1; i >= 0; i--) { 
    // 뒤에서부터 앞으로 탐색하며, 
    if (LIS[1][i] === count_downer) {
      // 정방향으로 봤을 때 정상적인 LIS에 포함되는 값이라면 스킵
      count_downer--;
    } else { 
      // 그렇지 않은 값은 교차하는 전깃줄이므로 해당 전깃줄 포함
      result.push(arr[i][0]);
    }
  }
  let result_string = "";
  for (let i = result.length - 1; i >= 0; i--) { 
    result_string += result[i];
    result_string += '\n';
  }
  console.log(result_string);
})