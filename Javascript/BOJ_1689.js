const readline = require('readline');
const fs = require('fs');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1689.txt'),
  output: process.stdout,
});

let ln = 0, N = 0;
let arr = [];

class PriorityQueue { 
  constructor() {
    this.heap = [];
  }

  peek() { 
    if (this.heap.length === 0) return null;
    return this.heap[0];
  }
  enqueue(item) { 
    this.heap.push(item);
    this.heapifyUp();
  }

  dequeue() { 
    if (this.heap.length === 0) return null;
    const item = this.heap[0];
    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap.pop();
    this.heapifyDown();
    return item;
  }
  
  heapifyUp() { 
    // (방금 원소가 삽입된)
    // 배열의 마지막 위치(this.heap[this.heap.length - 1])에서부터 위로 탐색해나가며
    // Heap 조건을 유지하도록 하는 작업
    let idx = this.heap.length - 1;
    while (this.hasParent(idx) && this.parent(idx) > this.heap[idx]) { 
      const pIdx = this.getParentIdx(idx);
      this.swap(pIdx, idx);
      idx = pIdx;
    }
  }

  heapifyDown() { 
    // 루트(this.heap[0])에서부터 아래로 탐색해나가며
    // Heap 조건을 유지하도록 하는 작업
    let idx = 0;
    while (this.hasLeftChild(idx)) { 
      let smallerChildIdx = this.getLeftChildIdx(idx);
      if (this.hasRightChild(idx) && (this.rightChild(idx) < this.leftChild(idx))) { 
        smallerChildIdx = this.getRightChildIdx(idx);
      }
      if (this.heap[idx] < this.heap[smallerChildIdx]) break;
      else this.swap(idx, smallerChildIdx);
      idx = smallerChildIdx;
    }
  }

  // 헬퍼 메서드

  getLeftChildIdx(parentIdx) { 
    return 2 * parentIdx + 1;
  }
  getRightChildIdx(parentIdx) { 
    return 2 * parentIdx + 2;
  }
  getParentIdx(childIdx) { 
    return Math.floor((childIdx - 1) / 2);
  }

  hasLeftChild(idx) { 
    return this.getLeftChildIdx(idx) < this.heap.length;
  }
  hasRightChild(idx) { 
    return this.getRightChildIdx(idx) < this.heap.length;
  }
  hasParent(idx) { 
    return this.getParentIdx(idx) >= 0;
  }

  leftChild(idx) { 
    return this.heap[this.getLeftChildIdx(idx)];
  }
  rightChild(idx) { 
    return this.heap[this.getRightChildIdx(idx)];
  }
  parent(idx) { 
    return this.heap[this.getParentIdx(idx)];
  }

  swap(idxA, idxB) { 
    const tmp = this.heap[idxA];
    this.heap[idxA] = this.heap[idxB];
    this.heap[idxB] = tmp;
  }
}

rl.on('line', (line) => {
  if (N === 0) {
    N = Number(line);
    arr = new Array(N).fill().map(_ => new Array(2).fill(0));
  } else { 
    const splitString = line.split(" ").map(Number);
    arr[ln - 1][0] = splitString[0];
    arr[ln - 1][1] = splitString[1];
  }
  ln++;
});

rl.on('close', () => {
  // 선분 A, B에 대해 s가 낮은 선분부터, 만약 s가 같다면 e가 낮은 선분부터 앞서 등장하도록 오름차순 정렬함
  arr.sort((a, b) => a[0] < b[0] ? -1 : (a[0] > b[0] ? 1 : (a[1] < b[1] ? -1 : (a[1] === b[1] ? 0 : 1))));
  const pq = new PriorityQueue();

  let answer = 0;

  for (let i = 0; i < N; i++) { 
    const [s, e] = arr[i];
    // Priority Queue 안에 있는 원소(선분의 e 들) 중 현재 선분의 s 이하인 값을 모두 뽑아냄
    for (let p = pq.peek(); p !== null && p <= s; p = pq.peek()) { 
      pq.dequeue();
    }
    // 이후, PQ안에 현재 선분의 e 추가
    pq.enqueue(e);
    // PQ안에 있는 원소의 개수가 현재 시점에서 겹친 선분들의 개수 이므로
    // "최대로 많이 겹치는 선분들의 개수" 갱신
    answer = Math.max(answer, pq.heap.length);
  }
  console.log(answer);
});