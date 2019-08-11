#coding=utf-8
import logging
from logging.handlers import TimedRotatingFileHandler

logHandler = TimedRotatingFileHandler("d:/log/test.log", when="D")
logFormatter = logging.Formatter('%(asctime)s %(name)-12s %(levelname)-8s %(message)s')
logHandler.setFormatter(logFormatter)
logger = logging.getLogger('MyLogger')
logger.addHandler(logHandler)
logger.setLevel(logging.INFO)
