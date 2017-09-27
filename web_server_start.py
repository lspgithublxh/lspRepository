import web_server_page
from wsgiref.simple_server import make_server
server = make_server('',8000,web_server_page.application)
print('Server start on port 8000')
server.serve_forever()
