// `fs.readFileSync` 사용시 한 번에 다 불러와서 메모리 초과가 발생해
// 한 줄씩 읽어들이는 `readline` 사용
const readline = require("readline");
const rl = readline.createInterface({
  input: process.stdin, 

  output: process.stdout,
});

// 가장 작은 값을 먼저 방출하는 PQ
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
  
    // remove 호출시 가장 높은 우선순위를 가지는 top 요소를 삭제하고
    // heapifyDown를 호출함
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
const pq = new PriorityQueue();
// 몇 번째 줄인지 확인하기 위한 카운트
let ln = 0;
rl.on('line', (line) => {
    if (ln === 0) {
        // 첫 번째 줄이면 N을 입력받은 것
        N = Number(line);
    } else { 
        const splited = line.split(" ").map(Number);
        for (let j = 0; j < N; j++) { 
            pq.add(splited[j]);
            if (pq.size() > N) { 
                // PQ가 N개만 저장할 수 있게 N개를 넘은 경우 방출하도록 함
                pq.remove();
            }
        }
    }
    ln++;
}).on('close', () => { 
    console.log(pq.remove());
})