from bs4 import  BeautifulSoup



def test(content):
    # print 'test'
    bs = BeautifulSoup(content, 'html.parser')
    title = bs.find_all('title', attrs={})
    for ti in title:
        print ti.string

