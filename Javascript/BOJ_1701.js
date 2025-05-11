// 이틀 붙잡고 모르겠어서 다른 풀이보고 푼 문제
// 원 아이디어 ---------------------------
// 1. [첫자리까지 뺀 문자열(1 ~ n), 둘째짜리까지 뺀 문자열(2 ~ n), ... , 마지막자리만 쓴 문자열 (n ~ n)]
//  에 대한 KMP 배열(pi)를 만듦
// 2. 이후 변수 i(2, 3, 4, ... , n-1까지 패턴 문자열의 범위)를 정하는 반복문을 사용
// 2-1. 내부에서 변수 j(0, 1, ..., n - i까지 자를 패턴 문자열의 시작점)를 정하는 반복문을 사용
// 2-2. j ~ j+i 만큼의 패턴 문자열을 자른 뒤, j + 1 부터 n까지 자른 "대상 문자열"에 대해 비교 수행
// 2-3. 비교 중, 패턴 문자열과 동일한 문자열이 발견(즉, j~ j+i와 같은 문자열이 j+1 이후에 발견)될 경우
//  그 부분 문자열이 문장 내에 2번 이상 등장했다는 것이므로 true 반환
// 3. true 반환된 경우, "최장 부분 문자열 길이"를 i로 갱신
// ---------------------------------------
// 다른 풀이 ------------------------------
// 1. [전체 문자열(0 ~ n), 첫자리까지 뺀 문자열(1 ~ n), 둘째짜리까지 뺀 문자열(2 ~ n), ... , 마지막자리만 쓴 문자열 (n ~ n)]
//  에 대한 KMP 배열(pi)를 만듦
// 2. pi를 만들면서, 등장한 수 중 가장 큰 수(A) 반환
//  그 수는 [i ~ n] 중 일치하는 접두 부분 문자열, 접미 부분 문자열 중 가장 긴 길이를 반환하는 것
// 3. A 들 중 가장 큰 수(MAX)를 출력

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1701.txt'),
  output: process.stdout
});

let str = "", n = 0, maximum = 0;

rl.on('line', (line) => {
  str = line;
  n = str.length;
});

rl.on('close', () => {
  let MAX = 0;
  for (let i = 0; i < n; i++) { 
    MAX = Math.max(MAX, initKMP(i, n));
  }
  console.log(MAX);
});

function initKMP(subStartIdx, subEndIdx) { 
  const len = subEndIdx - subStartIdx;
  const arr = new Array(len).fill(0);
  let rtn = 0;
  for (let i = 1, j = 0; i < len; i++) { 
    while (j > 0 && str[i + subStartIdx] != str[j + subStartIdx])
      j = arr[j - 1];
    if (str[i + subStartIdx] == str[j + subStartIdx])
      arr[i] = ++j;
    if (rtn < arr[i])
      rtn = arr[i];
  }
  return rtn;
};