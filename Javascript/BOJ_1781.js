// 답 보고 푼 문제
// 답의 아이디어
// ==========================
// 1. 먼저 문제들을 데드라인 일자가 낮은 것 부터 높은 순으로 정렬한다.
// 2. 높은 수(컵라면 개수)가 앞으로 오는 우선순위 큐를 만든다.
// 3. 문제를 하나씩 탐색하면서, 현재 카운트한 일자보다 높은 데드라인의 문제면 PQ에 넣고 일자를 올린다
// 3-1. 현재 카운트한 일자보다 낮은 데드라인의 문제면, PQ에 있는 문제를 대체할 수 있는지
//  (즉, PQ의 가장 작은 컵라면 개수보다 큰 지) 확인하고 그렇다면 대체한다.
let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1781.txt"
).toString().trim().split('\n');

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
      while (this.hasParent(index) && this.parent(index)
          > this.heap[index]) {
          this.swap(this.getParentIndex(index), index);
          index = this.getParentIndex(index);
      }
  }

  heapifyDown() {
      let index = 0;
      while (this.hasLeftChild(index)) {
          let smallerChildIndex = this.getLeftChildIndex(index);
          if (this.hasRightChild(index) && this.rightChild(index)
              < this.leftChild(index)) {
              smallerChildIndex = this.getRightChildIndex(index);
          }
          if (this.heap[index] < this.heap[smallerChildIndex]) {
              break;
          } else {
              this.swap(index, smallerChildIndex);
          }
          index = smallerChildIndex;
      }
  }
}

const N = +input[0];
let arr = Array.from({ length: N }, () => []);
let anspq = new PriorityQueue();
for (let i = 1; i <= N; i++) { 
  const inline = input[i].split(" ") || [];
  // 배열에 [데드라인, 컵라면 개수] 형태로 삽입
  arr[i - 1][0] = +inline[0];
  arr[i - 1][1] = +inline[1];
}
// 데드라인 오름차순 -> 같다면 컵라면 개수 내림차순으로 정렬한다.
arr.sort((a, b) => a[0] > b[0] ? 1 : (a[0] == b[0] ? (a[1] < b[1] ? 1 : (a[1] == b[1] ? 0 : -1)) : -1));
// 총 합 저장할 변수
let sum = 0;
// 일자 카운트할 변수
let time = 1;
for (let i = 0; i < N; i++) {
  // 배열에서 문제를 꺼낸다
  const p = arr[i];
  // 해당 문제 데드라인이 현재 일자보다 크거나 같으면
  if (time <= p[0]) {
    // PQ에 삽입하고, 현재 일자를 늘린다
    anspq.add(p[1]);
    time++;
  } else { 
    // 해당 문제 데드라인이 현재 일자보다 작으면
    // PQ에 있는 가장 작은 컵라면 개수보다 높은지 확인한다.
    if (anspq.peek() < p[1]) { 
      // 만약 PQ에 있는 가장 작은 컵라면 개수보다 높으면
      // 해당 개수를 큐에서 꺼내고, 현재 개수를 넣는다.
      anspq.remove();
      anspq.add(p[1]);
    }
  }
}
while (anspq.peek() != null) { 
  sum += anspq.remove();
}
console.log(sum);