from flask import Flask
from flask import request,render_template
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

@app.route('/main',methods=['GET'])
def main():
    return render_template('form.html')

@app.route('/thirdPage',methods=['POST','GET'])
def templateHandler():
    username = request.form['username']
    print(username)
    password = request.form['password']
    print(password)
    if username == 'admin' and password == 'password':
        return render_template('hello.html',username=username)
    return render_template('error.html',message='Bad username or password',usernmae=username)

if __name__ == '__main__':
    app.run()
