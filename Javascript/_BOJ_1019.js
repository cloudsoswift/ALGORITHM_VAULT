let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1019.txt"
).toString().trim().split('\n');
const N = Number(input[0]);
const count = new Array(10).fill(0);
