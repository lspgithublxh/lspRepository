def application(environ, start_response):
    print(environ)
    start_response('200 OK',[('Content-type','text/html')])#返回list<tuple>
    return ['Hello,World!'.encode('utf-8')]
    # return r'Hello,World!'.encode('utf-8')