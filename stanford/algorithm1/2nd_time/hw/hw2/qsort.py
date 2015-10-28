import math
#This partition uses first element as pivot
def partition1(q, l, r):
    pivotPos = choosePivot(q,l,r)
    swap(q, l, pivotPos)
    pivot = q[l];
    #print "pivot is " + str(pivot)
    i = l + 1;
    for j in range(l+1, r+1):
        if (q[j] < pivot):
            swap(q, i, j)
            i += 1;
    swap(q, i-1, l)
    #print q[l:r+1]
    return i-1;
    
def qcount (q, l, r):
    res = 0
    if l >= r:
        res = 0
    else:
        pivotpos = partition1(q,l,r)
        r1 = qcount(q,l,pivotpos-1);
        r2 = qcount(q,pivotpos+1,r);
        res = r - l + r1 + r2;
    return res
        
def swap(p, i, j):
    tmp = p[i]
    p[i] = p[j]
    p[j] = tmp

def choosePivot(a, l, r):
    #return r
    return median3(a,l,r);

def median3(a,l,r):
    res = -1
    medianPos = int(math.floor((l+r)/2))
    if a[l] > a[medianPos]:
        if a[medianPos] > a[r]:
            res = medianPos
        elif a[l] > a[r]:
            res = r
        else:
            res = l
    else:
        if a[medianPos] < a[r]:
            res = medianPos
        elif a[r] < a[l]:
            res = l
        else:
            res = r
    return res




fname = "input.txt"
p = []
with open(fname) as f:
    content = f.readlines()  
for line in content:
    p.append(int(line))

res = qcount(p, 0, len(p) -1)
print "count is " + str(res)
#print p