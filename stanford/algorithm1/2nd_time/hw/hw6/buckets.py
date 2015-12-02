#from __future__ import division
import time
class bucket(object):
    def __init__(self, v):
        
        self.inputmin = 100000;
        self.inputmax = -100000;
        self.resultDict = {}
        self.arrayDict = {}
        with open(v) as lines:
             for line in lines:
                 n = int(line)
                 if n > self.inputmax:
                     self.inputmax = n
                 if n < self.inputmin:
                     self.inputmin = n
        
        bucketmax = self.getBucket(self.inputmax)
        bucketmin = self.getBucket(self.inputmin)
        
        with open(v) as lines:
             for line in lines:
                 n = int(line)
                 self.insert(self.getBucket(n) , n)
        self.count()
    
    def insert(self, bucket, number):
        #print "buckt is" + str(bucket)
        if self.arrayDict.has_key(bucket) is False:
            self.arrayDict[bucket] = {};
        if self.arrayDict[bucket].has_key(number):
            count = self.arrayDict[bucket][number]
            self.arrayDict[bucket][number] = count + 1
        else:
            self.arrayDict[bucket][number] = 1;
     
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
        return res
    
    def getFreq(self, number):
        bucket = self.getBucket(number)
        res = 0;
        if not self.arrayDict.has_key(bucket):
            res = 0
        elif self.arrayDict[bucket].has_key(number):
            res = self.arrayDict[bucket][number]
        else:
            res = 0;
            
        return res
        
    def count(self):
        res = 0;
        bucketmax = self.getBucket(self.inputmax)
        bucketmin = self.getBucket(self.inputmin)
        
        sumarray = set(range(-10000,10001))
        
        print "set size is " + str(len(sumarray))
        
        for bucket in self.arrayDict.keys() :
            #print "bucket is " + str(bucket)
            if self.arrayDict.has_key(bucket) is False:
                continue
            
            for number in self.arrayDict[bucket].keys():
                a1Freq = self.getFreq(number)
                removeset = set();
                for sum in sumarray:
                    a2 = sum - number
                    a2Freq = self.getFreq(a2)
                    if a2Freq != 0:
                        removeset.add(sum)
                sumarray = sumarray -   removeset   
                #if   len(sumarray) 
                if len(sumarray) % 100 == 0:
                    print "set size is " + str(len(sumarray))
        res = len(len(sumarray))
        print "res is " + str(res)
        return res

start = time.time()
test = bucket("2sums.txt")
end = time.time()
print end - start
