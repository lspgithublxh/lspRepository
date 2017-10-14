import sys
import  math
from texttable import Texttable

if __name__ == '__main__':
    tb = Texttable()
    tb.set_deco(Texttable.HEADER | Texttable.VLINES | Texttable.HLINES | Texttable.BORDER)
    tb.add_rows([
        ["Name","Age","NickName"],
        ["Julia",20,"Lua"],
        ["Acdreamer",22,"Jack"]
    ])
    print(tb.draw())
    file = open('D:\\a.xls','w+') #会创建文件
    file.write(tb.draw())