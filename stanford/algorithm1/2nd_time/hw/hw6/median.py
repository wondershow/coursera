from _heapq import heappop
from _heapq import heappush


class median(object):
    def __init__(self, v):
        self.minheap = minpq();
        self.maxheap = maxpq();
        self.totalMedian = 0;
        self.size = 0;
        
        with open(v) as lines:
             for line in lines:
                 print int(line)
                 self.inertPQs(int(line))
                 self.size += 1
                 self.totalMedian += self.median()
                 print "median is " + str(self.median())

        print self.totalMedian % 10000
        
    def inertPQs(self, key):
        #print "maxpqsize : " + str(self.maxheap.heapsize())
        #print "minpqsize : " + str(self.minheap.heapsize()) 
        if self.maxheap.heapsize() == 0:
            self.maxheap.push(key)
        else:
            maxpqMax = self.maxheap.peek();
            if key < maxpqMax:
                self.maxheap.push(key)
            else:
                self.minheap.push(key)
            
            while self.maxheap.heapsize() < self.minheap.heapsize():
                self.maxheap.push(self.minheap.pop()) 
                
            while self.maxheap.heapsize() > self.minheap.heapsize() + 1: 
                self.minheap.push(self.maxheap.pop())
                
            #print "maxpqsize : " + str(self.maxheap.heapsize())
            #print "minpqsize : " + str(self.minheap.heapsize()) 
            
            #print "end "
        
    def median(self):
        return self.maxheap.peek()
    
    
class maxpq(object):
    
    def __init__(self):
        self.heap = [];
        self.size = 0;
        
    def push(self, key):
        heappush(self.heap, (-key, key))
        self.size += 1
    
    def pop(self):
        if self.size == 0:
            return None
        neg, pos = heappop(self.heap)
        self.size -= 1
        return pos
    
    def peek(self):
        res = self.pop()
        if res is not None:
            self.push(res)
        return res
    
    def heapsize(self):
        #print  "insider = " + str(self.size)
        return self.size
        
class minpq(object):
    
    def __init__(self):
        self.heap = [];
        self.size = 0;
        
    def push(self, key):
        heappush(self.heap, (key, key))
        self.size += 1;
    
    def pop(self):
        if self.size == 0:
            return None
        neg, pos = heappop(self.heap)
        self.size -= 1
        return pos
    
    def peek(self):
        res = self.pop()
        if res is not None:
            self.push(res)
        else:
            res = 0
        return res
    
    def heapsize(self):
        return self.size
    
test = median("median.txt")