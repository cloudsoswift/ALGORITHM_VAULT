'use strict';

process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString = '';
let currentLine = 0;


process.stdin.on('data', inputStdin => {
  inputString += inputStdin;
});

process.stdin.on('end', _ => {
  inputString = inputString.trim().split('\n').map(string => {
    return string.trim();
  });
  
  main();    
});

function readline() {
  return inputString[currentLine++];
}
let t = 0, n = 0, a_arr = [], b_arr = [], p_len = 0;
let prime_arr = [];
function main() {
  // t <= 10,000
  t = Number(readline());
  prime_arr = primes(200000);
  p_len = prime_arr.length;
  for (let i = 0; i < t; i++) { 
    // n <= 200,000, a_i <= 200,000
    n = Number(readline());
    a_arr = readline().split(" ").map(Number);
    b_arr = readline().split(" ").map(Number);
    console.log(calc_NCTG());
  }
}

function calc_NCTG() { 
  const mark = new Map();
  // 1. plain a_i
  for (let i = 0; i < n; i++) { 
    let v = a_arr[i];
    for (let j = 0; j < p_len; j++) { 
      if (v % prime_arr[j] === 0) { 
        mark.set(prime_arr[j], (mark.get(prime_arr[j]) || 0) + 1);
        if (mark.get(prime_arr[j]) > 1) { 
          return 0;
        }
        while (v % prime_arr[j] === 0) v /= prime_arr[j];
      }
    }
    // 만약 447 이하의 소수들롤 최대한 나눈 후에도 수가 남아있다면,
    // 그는 새로운 소수이므로 map에 기록
    if (v > 1) { 
      mark.set(v, (mark.get(v) || 0) + 1);
      if (mark.get(v) > 1) return 0;
    }
  }
  // 2. a_i + 1
  for (let i = 0; i < n; i++) { 
    let v = a_arr[i] + 1;
    for (let j = 0; j < p_len; j++) { 
      if (v % prime_arr[j] === 0) { 
        if (mark.get(prime_arr[j]) >= 1) { 
          return 1;
        }
        while (v % prime_arr[j] === 0) v /= prime_arr[j];
      }
    }
    if (v > 1 && mark.get(v) >= 1) return 1;
  }
  return 2;
}

// 에라토스테네스의 체를 이용해 root(200,000) (~= 447) 까지의 수 중 소수 구함
// (어차피 200,000 이하의 수 들 중, 최대공약수는 root(200,000) 이하일 수 밖에 없음
// ex) 447 이상의 수 중 소수인 449에 대해,
//  a_i = 449, a_j = 449 * a 라고 했을때, gcd(a_i, a_j) = 449 인 케이스가 존재하려면 a_j = 449 * 449, 즉 449^2이어야 하는데
// 449^2는 201601로 a 배열 원소의 최대 범위인 200,000을 넘음)
function primes(n) {
  const len = Math.pow(n, 1 / 2);
  const result = [];
  const arr = new Array(n).fill(false);
  for (let i = 2; i <= len; i++) { 
    if (arr[i]) continue;
    result.push(i);
    for (let j = i; j <= len; j += i) { 
      arr[j] = true;
    }
  }
  return result;
}