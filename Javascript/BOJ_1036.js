const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1036.txt'),
  output: process.stdout
});

const radix = 36;
let N = 0, K = 0, ln = 0;
// [숫자, 가중치]를 저장하는 2차원 배열
// 가중치의 경우, 매우 큰 수가 나올 수 있기 때문에 BigInt 값을 사용(그래서 0n(n을 더하면 BigInt 리터럴 표현식))
const freq = new Array(radix).fill().map(_ => new Array(2).fill(0n));
// [숫자, 가중치] 중 숫자에 해당하는 부분 초기화
for (let i = 0; i < radix; i++) { 
  freq[i][0] = i;
}
// 입력받은 N개의 36진수 저장할 배열 numbers
let numbers = [];

rl.on("line", (line) => {
  if (N === 0) {
    N = Number(line);
    numbers = new Array(N);
  } else if (ln === N + 1) {
    K = Number(line);
  } else { 
    numbers[ln-1] = line.split("");
  }
  ln++;
});

rl.on("close", () => {
  initFrequency();
  // N개의 36진수들에서 등장한 수의 종류 개수를 나타내는 변수
  const total_difference = freq.reduce((pv, cv) => cv[1] > 0 ? pv + 1 : pv);
  // 가중치가 높은 K개의 선택된 수 기록하는 Map
  const chosen_alpha = new Map();
  // 각 자리 별([0] = 첫 번째 자리에 해당하는 수, [1] = 두 번째 자리에 해당하는 수, ...) 수의 합 기록하는 배열
  const count = new Array(51).fill(0);
  if (total_difference <= K) {
    // 등장한 수의 종류 개수가 K 이하라면 등장한 모든 수를 Z로 바꿀 수 있음
    // 그래서 각 자리 별로 35(Z) 만큼 추가
    for (let i = 0; i < N; i++) { 
      for (let j = 0, len = numbers[i].length; j < len; j++) { 
        count[len - j - 1] += 35;
      }
    }
  } else { 
    // 등장한 수의 종류 개수가 K 초과라면, 등장한 수 별로 가중치를 계산하여 K개를 선정해야 함.
    // 따라서 가중치순으로 [숫자, 가중치] 배열들을 내림차순 정렬
    freq.sort((a, b) => a[1] < b[1] ? 1 : (a[1] === b[1] ? 0 : -1));
    // 내림차순으로 K개 뽑아서 Map에 기록
    for (let i = 0; i < K; i++) { 
      chosen_alpha.set(decimalToBase36(freq[i][0]), true);
    }
    for (let i = 0; i < N; i++) { 
      for (let j = 0, len = numbers[i].length; j < len; j++) { 
        // 가중치가 높은 K개의 수 중 하나라면 35로, 그렇지 않다면 그냥 해당 36진수를 10진수로 변경한 값을 numbers 배열에 기록
        if (chosen_alpha.has(numbers[i][j])) numbers[i][j] = 35;
        else numbers[i][j] = base36ToDecimal(numbers[i][j]);
        // 이후, 각 자릿수에 맞는 위치에 그 수만큼 추가
        count[len-j-1] += numbers[i][j];
      }
    }
  }
  const result = [];
  // 현재 자리 수에서 36을 나눠, 다음 자리수로 넘어갈 수를 기록하는 변수 rest
  let rest = 0;
  // 모두 합한 수가 최대 몇자리인지 기록하는 변수 MAXIMUM
  let MAXIMUM = 0;
  for (let i = 0; i <= 50; i++) { 
    // 넘어온 수 or 기록된 현재 자리 수가 있다면 최대 자릿수 갱신
    if (rest > 0 || count[i] > 0) MAXIMUM = i;
    // 넘어온 수가 있다면 현재 자리 수에 반영
    if (rest > 0) count[i] += rest;
    result.push(decimalToBase36(count[i] % 36));
    rest = Math.floor(count[i] / 36);
  }
  // 남아있는 넘어온 수들 전부 반영
  while (rest > 0) { 
    MAXIMUM++;
    result.push(decimalToBase36(rest % 36));
    rest = Math.floor(rest / 36);
  }
  let answer = "";
  for (let i = MAXIMUM; i >= 0; i--) { 
    answer += result[i];
  }
  console.log(answer);
});

// 0 ~ Z까지 숫자 별 가중치 계산하는 함수
function initFrequency() { 
  for (let i = 0; i < N; i++) { 
    for (let j = 0, len = numbers[i].length; j < len; j++) { 
      // 각 자리 별 숫자를 10진수로 바꾼 뒤
      const base36 = base36ToDecimal(numbers[i][j]);
      // "해당 수를 Z로 바꿨을때의 차이 * 자릿 수"를 가중치에 반영
      freq[base36][1] += (BigInt(35 - base36) * BigInt(Math.pow(36, (len - j))));
    }
  }
}

function base36ToDecimal(base36) { 
  if (base36 >= '0' && base36 <= '9') { 
    // 문자가 0 ~ 9인 경우 해당 문자 그대로 정수로 바꾼 값 반환
    return Number(base36);
  }
  return 10 + (base36.charCodeAt(0) - 'A'.charCodeAt(0));
}
function decimalToBase36(decimal) { 
  if (decimal < 10) { 
    return `${decimal}`;
  }
  return String.fromCharCode(65 + (decimal - 10));
}