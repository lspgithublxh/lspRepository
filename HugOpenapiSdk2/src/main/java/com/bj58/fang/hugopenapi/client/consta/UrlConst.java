package com.bj58.fang.hugopenapi.client.consta;

public class UrlConst {

	public static final String HOST = "hugopenapi.58.com";
	public static final String HOUSE_PREX_URL = "http://hugopenapi.58.com/gateway/%s";
	public static final String HOUSE_PREX_URL2 = "http://hugopenapi.58.com/%s";

	public static final String HOUSE_HANDLE_URL = "http://hugopenapi.58.com/gateway/%s?clientId=%s&timeSign=%s&tokenSign=%s";
	public static final String COM_ESF_ADD = "sale/company/posthouse";
	public static final String COM_ESF_UPDATE = "sale/company/posthouse";
	public static final String COM_ZF_ADD = "rent/company/posthouse";
	public static final String COM_ZF_UPDATE = "rent/company/posthouse";
	public static final String BRO_ESF_ADD = "sale/broker/addhouse";
	public static final String BRO_ESF_UPDATE = "sale/broker/updatehouse";
	public static final String BRO_ESF_DELETE = "sale/broker/deletehouse";
	public static final String BRO_ESF_HEYAN = "sale/broker/verify_house";

	public static final String BRO_ZF_ADD = "rent/broker/addhouse";
	public static final String BRO_ZF_UPDATE = "rent/broker/updatehouse";
	public static final String BRO_ZF_DELETE = "rent/broker/deletehouse";

	
	public static final String XQ_PANSHI_MATCH = "unity/community/search";//二手房租房发布时候可以使用
	public static final String CITY_ID_LIST = "city/get_city_list"; //common/company/delete_house
	public static final String PUB_HOUSE_DELETE = "common/company/delete_house";
	public static final String PUB_BROID_GET = "unionbroker/brokerid";
	public static final String PUB_BROID_GETBY_UN = "unionbroker/unionbrokerrelation/getByUserName";
	public static final String PUB_BROID_GETBY_IDEN = "unionbroker/brokerids/query_by_usercard";
	public static final String PUB_BROTELEPHONE_DETAIL = "unionbroker/callparticular";
	public static final String PUB_BROTELEPHONE_DETAILBATCH = "unionbroker/callparticular/batchquery";
	public static final String PUB_HOUSETAG = "sale/getHousetag";//common/company/query_broker_houses
	public static final String PUB_BRO_TELEPHONE_RECORD = "unionbroker/recordurl/batchquery";
	public static final String PUB_FANGCOM_FANGSAN = "common/company/query_broker_houses";
	public static final String PUB_FANGCOM_BROKER_FCOUNT = "common/company/query_broker_houses_count";
	public static final String PUB_FANGCOM_BROKER_FCOUNT2 = "common/company/query_broker_houses_by_type";
	public static final String PUB_FANGCOM_EXISTS = "common/company/check_if_exist_house";
	public static final String PUB_FANGCOM_STATUS = "common/company/query_house_state";
	public static final String PUB_FANGCOM_STATUS_UPDATE = "common/company/update_house_state";
	public static final String PUB_FANGBRO_DELETE = "common/company/delete_broker_house_by_useid";
	public static final String PUB_CURRCITY_FANG_TAG = "sale/getHousetag";
	public static final String PUB_DEPARTMENT_QUERY = "department/query_by_id";
	public static final String PUB_DEPARTMENT_ADD = "department/modify";
	public static final String PUB_EMPLOYEE_ADD = "user_info/modify";
	public static final String PUB_EMPLOYEE_QUERY = "user_info/query_by_id";
	public static final String PUB_EMPLOYEE_DIMISSION = "user_info/dismission";
	public static final String PUB_EMPLOYEE_BATCH_FREEZE = "user_info/batch_freeze";
	public static final String PUB_BIND_SANWANG_ACOUNT = "user_info/add_site_binding";
	public static final String PUB_DELETE_SITE_BINDING = "user_info/delete_site_binding";

	public static final String PUB_ROLE_ADD = "house/role/modify";
	public static final String PUB_DEALRECORD_ADD = "house/deal_record/add";
	public static final String PUB_HOUSEWORK_ADD = "house/work/modify";
	public static final String PUB_SETTING_VIEW_RANGE = "settings/view_range";

	
	public static final String PUB_COM_TO_BROKER = "common/company/post_broker_house";

	
	public static final String GET_TOKEN_URL = "http://hugopenapi.58.com/gateway/token/get_token?clientId=%s&timeSign=%s&signature=%s";
	public static final String REFRESH_TOKEN_URL = "http://hugopenapi.58.com/gateway/token/refresh_token?clientId=%s&timeSign=%s&signature=%s";
	public static final String SALE_COMPANY_URL = "http://hugopenapi.58.com/gateway/sale/company?clientId=%s&timeSign=%s&tokenSign=%s&bianhao=%s";
	
}
