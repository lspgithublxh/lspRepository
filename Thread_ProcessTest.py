import os,time,random
from multiprocessing import Process, Pool
def run_proc(name):
    print('Run child process {0}，{1}'.format(name,os.getpid()))

def task(name):
    print('Run task {0},{1}'.format(name,os.getpid()))
    start = time.time()
    time.sleep(random.random() * 3)
    end = time.time()
    print('Task {0} runs {1} seconds'.format(name,end - start))

if __name__ == '__main__':
    print('Parent process is {0}'.format(os.getpid()))
    p = Process(target=run_proc, args=('test',))
    print('Process will start')
    p.start()
    p.join()
    print('Process end.')
    print('Process Pool start:-----------')
    p = Pool()
    for i in range(5):
        p.apply_async(task,args=(i,))
    print('Waiting for all process done')
    p.close()
    p.join()
    print('All process done.')
