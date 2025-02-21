const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_19598.txt"),
  output: process.stdout,
});

class PriorityQueue {
  constructor() {
      this.heap = [];
  }

  // Helper Methods
  getLeftChildIndex(parentIndex) {
      return 2 * parentIndex + 1;
  }

  getRightChildIndex(parentIndex) {
      return 2 * parentIndex + 2;
  }

  getParentIndex(childIndex) {
      return Math.floor((childIndex - 1) / 2);
  }

  hasLeftChild(index) {
      return this.getLeftChildIndex(index)
          < this.heap.length;
  }

  hasRightChild(index) {
      return this.getRightChildIndex(index)
          < this.heap.length;
  }

  hasParent(index) {
      return this.getParentIndex(index) >= 0;
  }

  leftChild(index) {
      return this.heap[this.getLeftChildIndex(index)];
  }

  rightChild(index) {
      return this.heap[this.getRightChildIndex(index)];
  }

  parent(index) {
      return this.heap[this.getParentIndex(index)];
  }

  swap(indexOne, indexTwo) {
      const temp = this.heap[indexOne];
      this.heap[indexOne] = this.heap[indexTwo];
      this.heap[indexTwo] = temp;
  }

  peek() {
      if (this.heap.length === 0) {
          return null;
      }
      return this.heap[0];
  }
  size() { 
    return this.heap.length;
  }
  // Removing an element will remove the
  // top element with highest priority then
  // heapifyDown will be called 
  remove() {
      if (this.heap.length === 0) {
          return null;
      }
      const item = this.heap[0];
      this.heap[0] = this.heap[this.heap.length - 1];
      this.heap.pop();
      this.heapifyDown();
      return item;
  }

  add(item) {
      this.heap.push(item);
      this.heapifyUp();
  }

  heapifyUp() {
      let index = this.heap.length - 1;
      while (this.hasParent(index) && this.parent(index)[1]
          > this.heap[index][1]) {
          this.swap(this.getParentIndex(index), index);
          index = this.getParentIndex(index);
      }
  }

  heapifyDown() {
      let index = 0;
      while (this.hasLeftChild(index)) {
          let smallerChildIndex = this.getLeftChildIndex(index);
          if (this.hasRightChild(index) && this.rightChild(index)[1]
              < this.leftChild(index)[1]) {
              smallerChildIndex = this.getRightChildIndex(index);
          }
          if (this.heap[index][1] < this.heap[smallerChildIndex][1]) {
              break;
          } else {
              this.swap(index, smallerChildIndex);
          }
          index = smallerChildIndex;
      }
  }
}

let ln = 0, N = 0;
let arr = [];
rl.on('line', (line) => {
  if (ln === 0) {
    N = Number(line);
    arr = new Array(N).fill().map(_ => new Array(2).fill(0));
  } else { 
    arr[ln-1] = line.split(" ").map(Number);
  }
  ln++;
});

let MAX = 1;

rl.on('close', () => {
  arr.sort((a, b) => a[0] > b[0] ? 1 : (a[0] === b[0] ? (a[1] > b[1] ? 1 : -1) : -1));
  // 현재 회의가 진행중인 회의를 저장하는 PQ
  // ( 따라서 PQ의 사이즈 = 각 시점에서의 최소 회의실 갯수)
  const pq = new PriorityQueue();
  for (let i = 0; i < N; i++) { 
    if (pq.size() === 0) {
      // 진행중인 회의 없는 경우(즉, i번째 회의 진행하기 위해 이전의 회의가 끝나길 기다리지 않아도 되는 경우)
      pq.add([...arr[i]]);
    } else { 
      // 이미 진행중인 회의가 있는 경우
      while (pq.size() > 0 && pq.peek()[1] <= arr[i][0]) { 
        // 진행중인 회의 중, 끝나는 시간 가장 빠른게 i번째 회의의 시작 시간과 같거나 작은 경우
        // 해당 회의 끝냄
        pq.remove();
      }
      pq.add([...arr[i]]);
      if (pq.size() > MAX) MAX = pq.size();
    }
  }
  console.log(arr);
  console.log(MAX);
  
});