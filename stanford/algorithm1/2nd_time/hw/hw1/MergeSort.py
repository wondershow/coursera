# Use a merge sort to calculate the
# number of inversions in an array    
def merge(p, lo, mid, high):
    i = 0;
    j = 0;
    k = lo;
    p1 = p[lo : mid + 1];
    p2 = p[mid + 1 : high + 1];
    inversions = 0;
    
    while i < len(p1) and j< len(p2):
        if (p1[i] <= p2[j]):
            p[k] = p1[i]
            k += 1
            i += 1
        else:
            p[k] = p2[j]
            k += 1
            j += 1
            inversions += len(p1) - i
    while (i < len(p1)):
        p[k] = p1[i]
        k += 1
        i += 1
    while (j < len(p2)):
        p[k] = p2[j]
        k += 1;
        j += 1;
    return inversions;
    
def sortcount(p, lo, hi):
    totalcount = 0 
    if lo >= hi:
        totalcount = 0
    else:
        c1 = 0
        c2 = 0
        c3 = 0
        
        mid = (hi + lo)/2
        c1 = sortcount(p, lo, mid)
        c2 = sortcount(p, mid + 1, hi)
        c3 = merge(p, lo, mid, hi)
        totalcount = c1 + c2 + c3 
    return totalcount  

fname = "input.txt"
p = []
with open(fname) as f:
    content = f.readlines()  
for line in content:
    p.append(int(line))



print ("inversion is " + str(sortcount(p, 0, len(p)-1)))