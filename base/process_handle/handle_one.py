#coding=utf-8
import subprocess
import multiprocessing
#进程操作目的：
#创建一个新进程：
#引用其他进程：
# print subprocess.check_output(['date','-u'])
# print subprocess.call('excel')
# def process_handle(name):
#     print 'name is :', name
#
# multiprocessing.freeze_support()
# pro = multiprocessing.Process(target=process_handle, args=('lishaoping',))
# pro.start()

#查看windows上的进程和子进程(pname, pid, cpu 运行时间)
import  win32com.client
wmi = win32com.client.GetObject('winmgmts:')
for p in sorted(wmi.InstancesOf('win32_process'), key=lambda p : p.name.upper()):
    print p.name, p.Properties_('ProcessId'),int(p.Properties_('UserModeTime').value) + int(p.Properties_('KernelModeTime').value)
    children = wmi.ExecQuery('Select * from win32_process where ParentProcessId=%s' %p.Properties_('ProcessId'))
    for child in children:
        print '\t', child.name , child.Properties_('ProcessId'), int(child.Properties_('UserModeTime').value) + int(child.Properties_('KernelModeTime').value)
