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
let t = 0, n = 0, k = 0, a_arr = [];

function main() {
  t = Number(readline());
  for (let i = 0; i < t; i++) { 
    n = Number(readline());
    a_arr = readline().split(" ").map(Number);
    make_zigzag();
  }
}

function make_zigzag() { 
  let cost = 0;
  const cumul_sum = new Array(n).fill(0);
  for (let i = 0; i < n; i++) { 
    // 1. write cumulative sum to do max(a_1, ..., a_i)
    if (i === 0) {
      cumul_sum[i] = a_arr[i];
    } else { 
      cumul_sum[i] = Math.max(cumul_sum[i - 1], a_arr[i]);
    }
    // 2. i is even
    // -> b_{i-1} < b_i ( > b_{i-1} )
    // so do operation 1
    if (i % 2 === 1) { 
      a_arr[i] = cumul_sum[i];
    }
  }
  // 1. i > 0 and i is odd
  // -> b_{i-1} > b_i < b_{i+1}
  if (a_arr[0] >= a_arr[1]) { 
    cost = a_arr[0] - a_arr[1] + 1;
  }
  // so do operation 2 until meet the condition
  for (let i = 2; i < n; i += 2) { 
    const dist = Math.min(a_arr[i - 1], (i + 1 < n ? a_arr[i + 1] : a_arr[i - 1])) - 1;
    if (dist < a_arr[i]) { 
      cost += (a_arr[i] - dist);
    }
  }
  console.log(cost);
}