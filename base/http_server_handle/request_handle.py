#codingg=utf-8
import requests
header = {
"Accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
"Accept-Encoding":"gzip, deflate, br",
"Accept-Language":"zh-CN,zh;q=0.9",
"Cache-Control":"max-age=0",
"Connection":"keep-alive",
"Cookie":"BAIDUID=1927BEB011F4C3D7870CD704D9B7090E:FG=1; PSTM=1520992036; BIDUPSID=716D3440D2A5CD5928CC9A39C356FA8D; BD_UPN=12314753; ispeed_lsm=0; MCITY=-82%3A; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1456_13290_21085_20930; H_WISE_SIDS=122101_102569_108267_103550_122211_120190_121395_118878_118869_118844_118825_118793_122440_107311_122862_117332_121142_122736_122857_122960_122885_122671_120851_116407_122667_122625_110085_122464_121730_122303; plus_cv=1::m:caddfa4f; BD_CK_SAM=1; PSINO=1; BD_HOME=0; H_PS_645EC=49c9AgN%2F4He1jJBAi6fYGaRM9jebKBEJpUCifoJO0G48JyVRU18DOLo5h14",
"Host":"www.baidu.com",
"Upgrade-Insecure-Requests":"1",
"User-Agent":"Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1"
}

response = requests.get("https://www.baidu.com", headers=header, timeout=80)
print response.text

# requests.post("url", data={})