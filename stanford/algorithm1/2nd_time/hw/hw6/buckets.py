#from __future__ import division
class bucket(object):
    def __init__(self, v):
        self.arrayMaps = [None] * 20001
        self.inputmin = 100000;
        self.inputmax = -100000;
        with open(v) as lines:
             for line in lines:
                 n = int(line)
                 if n > self.inputmax:
                     self.inputmax = n
                 if n < self.inputmin:
                     self.inputmin = n;
                 self.insert(getBucket(n) , n)
    
    def insert(self, bucket, number):
        if self.arrayMaps[bucket] is None:
            self.arrayMaps[bucket] = {};
        if self.arrayMaps[bucket].contains(number):
            count = self.arrayMaps[bucket][number]
            self.arrayMaps[bucket][number] = count + 1
        else:
            self.arrayMaps[bucket][number] = 1;
            
        
     
    ''' 
    bucket of numbers 
        -39999 to -30000 => bucket[-4]
        -29999 to -20000 => bucket[-3]
        -19999 to -10001 => bucket[-2]
        -10000 to -1     => bucket[-1]
        0 to 10000 => bucket[0]
        10001 to 19999 => bucket[1]
        20000 to 29999 => bucket[2]
        30000 to 39999 => bucket[3]
        notice that bucket 0 and 1 are little bit diff
        from other buckets(range of numbers)
    '''   
    def getBucket(self, number):
        res = 0
        if number >= 0 :
            res = number / 10000;
            if res == 1 and number == 10000:
                res = 0;
        else:
            number = number * -1;
            res = -1 * (number / 10000) - 1;
            if res == -2 and number == 10000:
                res = 1
    
    def getFreq(self, number):
        bucket = self.getBucket(number)
        res = 0;
        if self.arrayMaps[bucket] is None:
            res = 0
        elif self.arrayMaps[bucket].contains(number):
            res = self.arrayMaps[bucket][number]
        else:
            res = 0;
            
        return res
        
    def count(self):
        res = 0;
        bucketmax = self.getBucket(self.inputmax)
        for bucket in [-1, 0]:
            for number in self.arrayMaps[bucket].keys():
                for sum in range(-10000, 100001):
                    gap = number 
            
        
        
        
          


