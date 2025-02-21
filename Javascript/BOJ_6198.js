let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_6198.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = new Array(N).fill(0);
for (let i = 0; i < N; i++) { 
  arr[i] = +input[i + 1];
}
let count = 0;
const stack = [arr[0]];
for (let i = 1; i < N; i++) { 
  if (arr[i] >= stack[stack.length - 1]) {
    // now >= head
    while (stack.length > 0 && arr[i] >= stack[stack.length - 1]) { 
      count += stack.length - 1;
      stack.pop();
    }
    stack.push(arr[i]);
  } else { 
    // now < head
    stack.push(arr[i]);
  }
}
while (stack.length > 0) { 
  count += stack.length - 1;
  stack.pop();
}
console.log(count);