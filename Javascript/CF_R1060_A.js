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
let ln = 0, t = 0, n = 0, k = 0, bi_str = [];

function main() {
  t = Number(readline());
  for (let i = 0; i < t; i++) { 
    [n, k] = readline().split(" ").map(Number);
    bi_str = readline().split("");
    protect();
  }
}
function protect() { 
  let count = 0;
  let is_contain_one = new Array(n).fill(false);
  let one_cnt = 0;
  for (let i = 0; i < n; i++) { 
    // s_1 has no previous elements
    if (i === 0) { 
      if (bi_str[i] === "1") { 
        one_cnt++;
        is_contain_one[i] = true;
      }
    } else if (i < k) {
      if (one_cnt === 0) {
        is_contain_one[i] = true;
      }
      if (bi_str[i] === "1") {
        one_cnt++;
      }
    } else { 
      if (bi_str[i - k] === "1") { 
        one_cnt--;
      }
      if (one_cnt === 0) { 
        is_contain_one[i] = true;
      }
      if (bi_str[i] === "1") { 
        one_cnt++;
      }
    }
  }
  for (let i = 0; i < n; i++) { 
    // 1. s_i = 1
    if (bi_str[i] === "1") { 
      // 2. previous k-1 elements do not contain 1
      if (is_contain_one[i]) { 
        count++;
      }
    }
  }
  console.log(count);
}