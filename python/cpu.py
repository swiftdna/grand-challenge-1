# Importing the library
# from time import sleep
from re import X
import psutil
 
thisdict = {}
timetaken = 0
# Calling psutil.cpu_precent() for 4 seconds
# while True:
#     # print('The CPU usage is: ', psutil.cpu_percent(4))
#     print('The CPU usage')
#     # sleep(0.05)

try:
    while True:
        timetaken += 1
        x = psutil.cpu_percent(0.5)
        print('The CPU usage is: ', x)
        thisdict[timetaken] = x
except KeyboardInterrupt:
    # clean up
    print(thisdict)
    with open('cpuload.csv', 'w') as f:
        for key in thisdict.keys():
            f.write("%s~%s\n"%(key,thisdict[key]))