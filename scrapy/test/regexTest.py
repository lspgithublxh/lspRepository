import re

regexp = re.compile('\w+')
src = 'sdfsdfds4545---d'
res = regexp.findall(src)
print res
src2 = 'sds(dsf)sfdsf(erer)sdfs'
res = regexp.match(src2)
print res.group(0)
https = "https://45.45.45.45:4454"
http2 = '/host/45.45.45.45'
ip = '23.23.23.23'
ipRe = re.compile('.*?(/)?((\d+\.){3}\d+)')
res = ipRe.match(ip)
print 'ip:', res.group()
print 'ip:', ipRe.match(http2).group(2)
print 'ip:', ipRe.match(https).group(2)

ip2 = re.compile('http(?:s)?\://((\d|\.)+)\:\d+')
mat = ip2.match(https)
print mat.group(1)

str2 = 'port%3A18245%2C18246+'
pRe = re.compile('port%3A(.*)?\+')
res = pRe.match(str2)
print res.group(1)
str2 = 'port:1911,4911 product:Niagara';
pRe = re.compile('port\:([\d,]*)?(\s|$)')
print  pRe.match(str2).group(1)
str2 = 'port:122'
print  pRe.match(str2).group(1)