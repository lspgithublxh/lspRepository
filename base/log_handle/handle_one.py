#coding=utf-8
import logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                    datefmt='%a, %d %b %Y %H:%M:%S', filename='d:/log/trace.log', filemode='a')
logging.info('ss')

from logging.handlers import TimedRotatingFileHandler
def test2():
    logger = logging.getLogger('someone')
    logger.setLevel(logging.INFO)
    logger.info('this is a message')
    ch = TimedRotatingFileHandler("d:/log/test.log", when='D', encoding='UTF-8')
    ch.setLevel(logging.INFO)
    logger.addHandler(ch)
    logger.error('this a error')

def test3():
    logger = logging.getLogger('someone')
    logger.setLevel(logging.INFO)
    ch = TimedRotatingFileHandler("d:/log/test.log", when='D', encoding='UTF-8')
    ch.setLevel(logging.INFO)
    logger.addHandler(ch)
    logger.error('this a error\r'+'\r')

if __name__ == '__main__':
    # for i in [2,2,2,22,2,2]:
    #     test3()
    for i in [1,10]:
        test3()