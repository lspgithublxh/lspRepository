

def ipCheck(ip):
    parts = ip.split('.')
    if parts.__len__() != 4:
        return False
    try:
        for part in parts:
            if int(part) < 0 or int(part) > 255:
                return False
    except Exception as e:
        print e
        return False
    if parts[0].startswith('0'): return False
    return True