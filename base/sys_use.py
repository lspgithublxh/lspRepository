#coding=utf-8
import sys
import utils.util1

def main():
    print sys.argv

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('utf-8')
    print sys.getdefaultencoding()
    main()
