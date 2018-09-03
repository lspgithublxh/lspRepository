package com.bj58.fang.codeGenerate.json2json;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CodeGenerate2 {

	public static void main(String[] args) {
//		String content = "{\"data\":[{\"clickActionType\":\"ershoufang-click\",\"imgUrl\":\"https://img.58cdn.com.cn/fangrs/img/e3f57bdb386b68053bc26889ee89f988.png\",\"jumpAction\":{\"action\":\"pagetrans\",\"content\":{\"action\":\"pagetrans\",\"actiontype\":\"zhengzu\",\"cateid\":\"12\",\"list_name\":\"ershoufang\",\"logParam\":\"ershoufang\",\"meta_url\":\"\",\"pagetype\":\"list\",\"title\":\"二手房\",\"tradeline\":\"house\"},\"tradeline\":\"house\"},\"reuseId\":\"house-mainBusiness\",\"showActionType\":\"ershoufang-click\",\"text\":\"二手房\",\"type\":\"house-mainBusiness\"},{\"clickActionType\":\"shangpu-click\",\"imgUrl\":\"https://img.58cdn.com.cn/fangrs/img/e3f57bdb386b68053bc26889ee89f988.png\",\"jumpAction\":{\"action\":\"pagetrans\",\"content\":{\"action\":\"pagetrans\",\"actiontype\":\"sptitle\",\"cateid\":\"14\",\"list_name\":\"shangpucs\",\"logParam\":\"shangpu\",\"meta_url\":\"\",\"pagetype\":\"list\",\"title\":\"商铺\",\"tradeline\":\"house\"},\"tradeline\":\"house\"},\"reuseId\":\"house-mainBusiness\",\"showActionType\":\"shangpu-click\",\"text\":\"商铺\",\"type\":\"house-mainBusiness\"},{\"clickActionType\":\"xiezilou-click\",\"imgUrl\":\"https://img.58cdn.com.cn/fangrs/img/e3f57bdb386b68053bc26889ee89f988.png\",\"jumpAction\":{\"action\":\"pagetrans\",\"content\":{\"action\":\"pagetrans\",\"actiontype\":\"sychushou\",\"cateid\":\"13\",\"list_name\":\"zhaozu\",\"logParam\":\"xiezilou\",\"meta_url\":\"\",\"pagetype\":\"list\",\"title\":\"写字楼\",\"tradeline\":\"house\"},\"tradeline\":\"house\"},\"reuseId\":\"house-mainBusiness\",\"showActionType\":\"xiezilou-click\",\"text\":\"写字楼\",\"type\":\"house-mainBusiness\"}]}\r\n";
//		String content = "{\"pagetype\":\"list\",\"param\":{\"pagetype\":\"list\",\"cateid\":\"12\",\"list_name\":\"ershoufang\",\"title\":\"智能安选\",\"url\":\"https://appfang.58.com/api/list\",\"filterParams\":\"{\\\"tese\\\":\\\"param12175\\\",\\\"anxuan\\\":\\\"1\\\",\\\"param12175\\\":\\\"1\\\"}\"},\"tradeline\":\"house\",\"type\":\"wbmain\"}";
//		String content = "{     \"type\": \"container-oneColumn\",     \"items\": [         {             \"type\": \"house-itemTitle\",             \"title\": \"视频看房\",             \"buttonText1\": \"查看全部\",             \"jumpAction\": {                 \"action\": \"pagetrans\",                 \"tradeline\": \"house\",                 \"content\": {                     \"meta_url\": \"https://apphouse.58.com/api/list\",                     \"action\": \"pagetrans\",                     \"actiontype\": \"zfgerenfangyuan\",                     \"tradeline\": \"house\",                     \"title\": \"出租\",                     \"pagetype\": \"list\",                     \"list_name\": \"chuzu\",                     \"cateid\": \"37031\",                     \"logParam\": \"grfy\",                     \"is_backtomain\": false,                     \"params\": {                         \"list_page_type\": \"anxuan\"                     }                 }             },             \"divider\": \"area\",             \"clickActionType\": \"\"         }     ] }";
//		String content = "{     \"type\": \"10\",     \"showActionType\": \"\",     \"logParam\": \"\",     \"style\": {         \"pageWidth\": 150,         \"scrollMarginLeft\": 20,         \"scrollMarginRight\": 20,         \"hGap\": 10,         \"pageRatio\": 0.4545,         \"padding\": [             0,             0,             0,             0         ]     },     \"items\": [         {             \"title\": \"两居·85㎡·南北\",             \"subTitle\": \"460万\",             \"thirdTitle\": \"5.5万/㎡\",             \"imgUrl\": \"https://pic1.58cdn.com.cn/dwater/fang/big/n_v2f5f62940187f466a936fb759f9cf739c_958528a55175691b.jpg?wt=%40%E5%88%98%E5%85%88%E7%94%9F&ws=7af4735f89b800fb25130cb8ef213280\",             \"topIconUrl\": \"https://pic2.58cdn.com.cn/nowater/fangfe/n_v294963089508645a7a6a257c706370f66.png\",             \"centerIconUrl\": \"https://pic3.58cdn.com.cn/nowater/fangfe/n_v2ec51dc2829bf4dd7b86f368684593941.png\",             \"bottomIconUrl\": \"https://pic3.58cdn.com.cn/nowater/fangfe/n_v2f6e4e99bef304fac98df5f708f3c00e8.png\",             \"clickActionType\": \"\",             \"type\": \"houseVideo\",             \"jumpAction\": {                 \"action\": \"pagetrans\",                 \"tradeline\": \"house\",                 \"content\": {                     \"action\": \"pagetrans\",                     \"full_path\": \"1,8\",                     \"infoID\": 35264882595776,                     \"infoSource\": \"\",                     \"list_name\": \"zufang\",                     \"local_name\": \"\",                     \"pagetype\": \"detail\",                     \"recomInfo\": true,                     \"recomlog\": \"seq=1F21A2D209E03B09A021D252D40E4B0F&scene=gulbcanxuan&recallno=228&rankno=2&ruleno=1&viewno=1&imei=863187030676440&recalldetail=4&type=2&id=35264882595776&pos=1\",                     \"title\": \"详情\",                     \"use_cache\": \"\",                     \"userID\": 56265027692566,                     \"data_url\": \"http://apphouse.58.com/api/detail\"                 }             }         },         {             \"imgUrl\": \"http://img.58cdn.com.cn/fangrs/img/60ff697a7599828e67c00cda179bf54f.png\",             \"clickActionType\": \"\",             \"type\": \"houseVideo\",             \"jumpaction\": {                 \"action\": \"pagetrans\",                 \"tradeline\": \"house\",                 \"content\": {                     \"meta_url\": \"https://apphouse.58.com/api/list\",                     \"action\": \"pagetrans\",                     \"actiontype\": \"zfgerenfangyuan\",                     \"tradeline\": \"house\",                     \"title\": \"出租\",                     \"pagetype\": \"list\",                     \"list_name\": \"chuzu\",                     \"cateid\": \"37031\",                     \"logParam\": \"grfy\",                     \"is_backtomain\": false,                     \"params\": {                         \"list_page_type\": \"anxuan\"                     }                 }             }         }     ] }";
//		String content = "{     \"type\": \"container-oneColumn\",     \"style\": {         \"padding\": [             20,             20,             20,             20         ],         \"bgColor\": \"#F6F6F6\"     },     \"items\": [         {             \"clickActionType\": \"\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"common\",                 \"title\": \"温馨一居\",                 \"url\": \"https://fangfe.58.com/toy/active/1535013126468\"             },             \"imgUrl\": \"https://pic6.58cdn.com.cn/anjuke_58/74891a0f006a94110cd5a061af9c07f7?w=670&h=150&crop=1\",             \"style\": {                 \"aspectRatio\": \"4.467\"             },             \"type\": \"house-image\"         }     ],     \"showActionType\": \"\" }";
//		String content = "{     \"type\": \"container-oneColumn\",     \"items\": [         {             \"type\": \"house-itemTitle\",             \"title\": \"推荐经纪人\",             \"buttonText1\": \"查看全部\",             \"jumpAction\": {                 \"action\": \"pagetrans\",                 \"tradeline\": \"house\",                 \"content\": {                     \"meta_url\": \"https://apphouse.58.com/api/list\",                     \"action\": \"pagetrans\",                     \"actiontype\": \"zfgerenfangyuan\",                     \"tradeline\": \"house\",                     \"title\": \"出租\",                     \"pagetype\": \"list\",                     \"list_name\": \"chuzu\",                     \"cateid\": \"37031\",                     \"logParam\": \"grfy\",                     \"is_backtomain\": false,                     \"params\": {                         \"list_page_type\": \"anxuan\"                     }                 }             },             \"clickActionType\": \"\"         }     ] }";
//		String content = "{     \"type\": \"10\",     \"showActionType\": \"\",     \"logParam\": \"\",     \"style\": {         \"pageWidth\": 140,         \"scrollMarginLeft\": 15,         \"scrollMarginRight\": 15     },     \"items\": [         {             \"rating\": 1,             \"subTitle\": \"我爱我家\",             \"title\": \"赵佳丽\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"link\",                 \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\",                 \"title\": \"熊猫公寓\"             },             \"type\": \"houseBroker\",             \"thirdTitle\": \"望京区域\",             \"iconUrl\": \"https://pic1.58cdn.com.cn/gongyu/n_v1bkujjd2cfebvsi7agz5a_cdebc1e04c8f0db9.jpg\"         },         {             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"link\",                 \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\",                 \"title\": \"熊猫公寓\"             },             \"type\": \"houseBroker\",             \"imgUrl\": \"https://pic2.58cdn.com.cn/nowater/fangfe/n_v25f4109ab865140bdb01dc316c7038883.png\"         }     ] }";
//		String content = "{     \"type\": \"container-oneColumn\",     \"items\": [         {             \"type\": \"house-itemTitle\",             \"title\": \"特色房源\",             \"divider\": \"line\"         }     ] }";
//		String content = "{     \"type\": \"container-twoColumn\",     \"style\": {         \"padding\": [             0,             20,             20,             20         ],         \"hGap\": \"10\"     },     \"items\": [         {             \"clickActionType\": \"\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"common\",                 \"title\": \"温馨一居\",                 \"url\": \"https://fangfe.58.com/toy/active/1535013126468\"             },             \"imgUrl\": \"https://pic3.58cdn.com.cn/nowater/fangfe/n_v2294fda8f729246e9b0f4a4c84fc071c5.png\",             \"type\": \"house-image\",             \"style\": {                 \"aspectRatio\": 1.625             }         },         {             \"clickActionType\": \"\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"common\",                 \"title\": \"超值两居\",                 \"url\": \"https://fangfe.58.com/toy/active/1535014820627\"             },             \"imgUrl\": \"https://pic1.58cdn.com.cn/nowater/fangfe/n_v2911bd7845e1e4c5ea1128da9289cf2a0.png\",             \"type\": \"house-image\",             \"style\": {                 \"aspectRatio\": 1.625             }         }     ],     \"showActionType\": \"\" }";
//		String content = "{\"data\":[{\"clickActionType\":\"xiaohuxing-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1081\":\"0_50\"}}},\"title\":\"小户型\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"zhunxinfang-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"准新房\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"xiaosanju-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1081\":\"70_90\",\"param1574\":\"3\"}}},\"title\":\"小三居\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"jingzhuangliangju-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1080\":\"4\",\"param1574\":\"2\"}}},\"title\":\"精装两居\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"bieshu-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1083\":\"5\"}}},\"title\":\"别墅\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"jingzhuangxiu-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1080\":\"4\"}}},\"title\":\"精装修\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"daidianti-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1085\":\"12_999\"}}},\"title\":\"带电梯\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"dizongjia-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":\"{\\\"param1078\\\":\\\"0_200\\\"}\"}},\"title\":\"低总价\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"},{\"clickActionType\":\"nanbeitongtou-click\",\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"UrlConfig.APP_FANG_List\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1086\":\"6\"}}},\"title\":\"南北通透\",\"url\":\"UrlConfig.APP_FANG_List\"},\"localname\":\"bj\",\"style\":{\"aspectRatio\":1.625},\"type\":\"house-image\"}]}";
//		String content = "{     \"type\": \"container-oneColumn\",     \"showActionType\": \"\",     \"items\": [         {             \"type\": \"houseAXCardInit\",             \"title\": \"房屋安选卡\",             \"subTitle\": \"保真保看 真实在售\",             \"tips\": \"定制我的房屋安选卡\",             \"subTips\": \"根据您的选房喜好，一键智能选好房\",             \"buttonText\": \"马上定制\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"link\",                 \"title\": \"熊猫公寓\",                 \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"             }         }     ] }";
//		String content = "{     \"type\": \"container-oneColumn\",     \"showActionType\": \"\",     \"items\": [         {             \"type\": \"houseAXCard\",             \"title\": \"房屋安选卡\",             \"subTitle\": \"保真保看 真实在售\",             \"settingText\": \"设置\",             \"buttonText\": \"一键选房\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"link\",                 \"title\": \"熊猫公寓\",                 \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"             },             \"axSettingAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"link\",                 \"title\": \"熊猫公寓\",                 \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"             },             \"clickActionType\": \"\",             \"axSettingClickActionType\": \"\",             \"infoList\": [                 {                     \"title\": \"预算：\",                     \"content\": \"200-250万\"                 },                 {                     \"title\": \"面积：\",                     \"content\": \"60-80㎡\"                 },                 {                     \"title\": \"户型：\",                     \"content\": \"一室一厅  两室一厅\"                 },                 {                     \"title\": \"区域：\",                     \"content\": \"望京  回龙观\"                 }             ]         }     ] }";
//		String content = "{\"data\":[     {         \"type\": \"houseBroker\",         \"data\": \"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\"     },     {         \"type\": \"houseFeatureTag\",         \"data\": \"QUxJVlYAAQAA0e4AAAAvAAAArQAAANwAAAAeAAAA/gAAAAAAAAECAAAAAAABAAAAAAABAA9ob3VzZUZlYXR1cmVUYWcAlgAAGQQAL/9sAAAAMFzV8PEAAAAesJhVLv/62tJ3cKy8/////gABUGr73j+AAAAAAasvfja93gQEAAAAAAcJSm59nwAAAA8Dt5eEAAAADx7HC8QAAAAExC06zgAAAA1c1fDx/////oaH0L0AAAAFWEhxwwAAAAV3cKy8/////sCbLjb/0VpAAAAAAQA2RS3SOPC8AAABAQAAAALSOPC8AAgke3RpdGxlfb3eBAQACmp1bXBBY3Rpb24AAAAA\"     },     {         \"type\": \"houseFeatureLayout\",         \"data\": \"QUxJVlYAAQAA2J4AAAAvAAAAaAAAAJcAAAANAAAAqAAAAAAAAACsAAAAAAABAAAAAAABABJob3VzZUZlYXR1cmVMYXlvdXQATgAAAQJc1fDx/////3dwrLz/////AAAAAAAAAAROAlzV8PH////+d3CsvP////8AAgux0rdBIAAAfumMJUEgAAAAAVYFP5BH82+cAAABAQAAAAFH82+cAAcke2RhdGF9AAAAAA==\"     } ]}";
//		String content = "{     \"type\": \"container-twoColumn\",     \"style\": {         \"padding\": [             0,             20,             20,             20         ],         \"hGap\": \"10\"     },     \"items\": [         {             \"clickActionType\": \"\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"common\",                 \"title\": \"温馨一居\",                 \"url\": \"https://fangfe.58.com/toy/active/1535013126468\"             },             \"imgUrl\": \"https://pic3.58cdn.com.cn/nowater/fangfe/n_v2294fda8f729246e9b0f4a4c84fc071c5.png\",             \"type\": \"house-image\",             \"style\": {                 \"aspectRatio\": 1.625             }         },         {             \"clickActionType\": \"\",             \"jumpAction\": {                 \"action\": \"loadpage\",                 \"pagetype\": \"common\",                 \"title\": \"超值两居\",                 \"url\": \"https://fangfe.58.com/toy/active/1535014820627\"             },             \"imgUrl\": \"https://pic1.58cdn.com.cn/nowater/fangfe/n_v2911bd7845e1e4c5ea1128da9289cf2a0.png\",             \"type\": \"house-image\",             \"style\": {                 \"aspectRatio\": 1.625             }         }     ],     \"showActionType\": \"\" }";
//		String content = "{     \"type\": \"container-oneColumn\",     \"style\": {         \"padding\": [             0,             20,             20,             20         ]     },     \"items\": [         {             \"type\": \"houseFeatureLayout\",             \"data\": [                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"带电梯\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 }             ]         }     ] }";
//		String content = "{\"items\":[{\"data\":[{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1085\":\"12_999\"}}},\"pagetype\":\"RN\",\"title\":\"带电梯\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"带电梯\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1083\":\"5\"}}},\"pagetype\":\"RN\",\"title\":\"别墅\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"别墅\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1081\":\"70_90\",\"param1574\":\"3\"}}},\"pagetype\":\"RN\",\"title\":\"小三居\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"小三居\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"pagetype\":\"RN\",\"title\":\"准新房\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"准新房\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1086\":\"6\"}}},\"pagetype\":\"RN\",\"title\":\"南北通透\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"南北通透\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"商住\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"商住\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"底楼带花园\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"底楼带花园\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"住宅\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"住宅\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"满五唯一\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"满五唯一\",\"type\":\"houseFeatureTag\"},{\"jumpAction\":{\"action\":\"loadpage\",\"content\":{\"list_name\":\"ershoufang\",\"meta_url\":\"https://appfang.58.com/api/list\",\"pagetype\":\"list\",\"params\":{\"filterparams\":{\"param1084\":\"0_2\"}}},\"title\":\"小产权\",\"url\":\"https://appfang.58.com/api/list\"},\"title\":\"小产权\",\"type\":\"houseFeatureTag\"}],\"type\":\"houseFeatureLayout\"}],\"style\":{\"padding\":[0,20,20,20]},\"type\":\"container-oneColumn\"}";
		String content = "{     \"bundleid\": \"23\",     \"title\": \"发布人信息\",     \"isfinish\": false,     \"backtoroot\": false,     \"params\": {         \"userID\": \"4507li//9Gpb9EiavYNkUMQycoiJZQL0e8fMjhL4j148bsqIiKgYn7xkZA\",         \"detailUrl\": \"https://appesf.58.com/app/landlord/getDetailInfo\",         \"recomLog\": \"\",         \"hideBar\": 1,         \"infoID\": 35287122825140,         \"list_name\": \"ershoufang\",         \"full_path\": \"1,12\",         \"houseListUrl\": \"https://appesf.58.com/app/landlord/getHouseList\",         \"countType\": \"\",         \"commondata\": {             \"encryption\": \"4507li//9Gpb9EiavYNkUMQycoiJZQL0e8fMjhL4j148bsqIiKgYn7xkZA\",             \"pageSource\": \"ershoufangHome\",             \"cateID\": \"12\",             \"sidDict\": \"\",             \"jump_detail_action\": \"\"         }     } }";
		generate(JSONObject.parseObject(content));
//		Integer i = 0;
//		JSONObject s = new JSONObject();
//		s.put("a", "b");
//		JSONObject sx = (JSONObject) s.clone();//是继承的关系，赋予的关系..没有共享
//		sx.put("ss", "xx");
//		JSONObject s2 = new JSONObject();
//		System.out.println(sx.toJSONString());
//		System.out.println(s.toJSONString());
//		System.out.println(s.hashCode());
//		System.out.println(s2.hashCode());
//		System.out.println(i.getClass().getName());
	}

	private static void generate(JSONObject json) {
		printJSON(json, "data");//
	}
	
	/**
	 * 建立观点：打印角度
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月18日
	 * @Package com.bj58.fang.codeGenerate.json2json
	 * @return void
	 */
	private static void printJSON(JSONObject target, String name) {
		System.out.println(String.format("JSONObject %s = new JSONObject();",  name));
		for(String key : target.keySet()) {
			Object val = target.get(key);
			String type = val.getClass().getName();
			if(type.contains("Integer") || type.contains("Double") || type.contains("Decimal")
					|| type.contains("Long")) {
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, val));
			}else if(type.contains("String")) {
				System.out.println(String.format("%s.put(\"%s\", \"" +"%s\");",name, key, val));
//				System.out.println(name + ".put(" + key + ",\"" + val + "\");");
			}else if(type.contains("JSONObject")) {
				String variblename = key + getName(key);
				printJSON((JSONObject)val, variblename);
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, variblename));
			}else if(type.contains("JSONArray")) {
				JSONArray arr = (JSONArray) val;
				String valuename = key + getName(key);
				printJSONArray(valuename, arr);//name是数组属于的json变量名
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, valuename));
			}
		}
	}
	
	static Map<String, Integer> config = new HashedMap();
	
	private static Integer getName(String key) {
		if(config.containsKey(key)) {
			config.put(key, config.get(key) + 1);
		}else {
			config.put(key, 1);
		}
		return config.get(key);
	}
	
	//编程序，重要是职责分明，各个方法不要做不应该做的，否则程序本身反而复杂了   一点不清晰。。不好构造递归，，不好复合调用-多处调用。。。甚至参数选择都有要求和限制
	
	private static void printJSONArray(String key, JSONArray arr) {//key是数组变量名, 
		System.out.println(String.format("JSONArray %s = new JSONArray();",  key));
		for(int i = 0 ; i < arr.size(); i++) {
			Object a_val = arr.get(i);
			String aname = (key + i + "" + getName(key));
			Object t_val = keyvalueHandle(a_val, aname);//第一个是值，第二个是试着给那个值赋予变量名称， 
			System.out.println(String.format("%s.add(%s);", key, t_val));
		}
	}

	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月18日
	 * @Package com.bj58.fang.codeGenerate.json2json
	 * @return void
	 */
	private static Object keyvalueHandle(Object a_val, String name) {//name是这个值的变量名
		String type = a_val.getClass().getName();
		Object val = null;
		if(type.contains("Integer") || type.contains("Double") || type.contains("Decimal")
				|| type.contains("Long")) {
			val = a_val;
		}else if(type.contains("String")) {
			val = String.format("\"%s\"", a_val);
		}else if(type.contains("JSONObject")) {
			printJSON((JSONObject) a_val, name);
			val = name;
		}else if(type.contains("JSONArray")) {
			String arrName = (name + getName(name));
			printJSONArray(arrName, (JSONArray) a_val);
			val = arrName;
		}
		return val;
	}
	
	
}
