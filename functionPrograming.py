
def log(text):
    def decorator(func):
        def wrapper(*args,**kwargs):
            print('{0},{1}'.format(text, func.__name__))
            return func(*args,**kwargs)
        return wrapper
    return decorator

@log('excute')
def test():
    print('test method...{0},{b}')

test()