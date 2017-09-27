from flask import Flask
from flask import request
app = Flask(__name__)

@app.route('/',methods=['GET','POST'])
def home():
    return '<h1>Home</h1>'

@app.route('/firstPage',methods=['get'])
def firstPage():
    return '''<form action="/secondPage",method="POST">
            <p><input name="username" ></p>
            <p><input name="password" type="password" ></p>
            <p><input type="submit">Sign In</button></p>
            </form>
    '''
@app.route('/secondPage',methods=['POST'])
def secondPage():
    if request.form['username']=='admin' and request.form['password']=='password':
        return '<h3>Hello,admin!</h3>'
    return '<h3>Bad username or password</h3>'

if __name__ == '__main__':
    app.run()
