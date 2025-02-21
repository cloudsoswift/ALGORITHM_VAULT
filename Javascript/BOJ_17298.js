let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./BOJ_17298.txt")
  .toString().trim().split("\n");
const N = +input[0];
const arr = input[1].split(" ") || [];
let answer = Array(N);
let stack = [];
for (let i = 0; i < N; i++) { 
  console.log(i + " , " + stack);
  if (stack.length == 0) {
    stack.push([i, +arr[i]]);
  } else { 
    console.log(i + " > " + stack[stack.length - 1][1] + ", " +  arr[i]);
    while (stack.length > 0 && stack[stack.length - 1][1] < arr[i]) { 
      console.log(stack.length);
      const p = stack.pop();
      answer[p[0]] = arr[i];
    }
    stack.push([i, +arr[i]]);
  }
}
for (let i = 0; i < N; i++) { 
  if (answer[i] === undefined) answer[i] = -1;
}
console.log(answer.join(" "));