package com.bj58.fang.hugopenapi.client.util;

public class StringUtils {
	
	public static final String NULLSTR = "";
	
	public static boolean isEmptyString(String str){
		return (str == null || str.trim().length() ==0);
	}
	public static int parseInt(String str, int defaultNum){
		if(!isEmptyString(str) && str.trim().matches("\\d+")){
			return Integer.parseInt(str.trim());
		}
		return defaultNum;
	}
	
	public static void main(String[] args) {
		System.out.println(versionCompare("8.5.0", "8.4.0"));
	}
	
    public static int versionCompare(String oldversion, String curversion) {
        try {
            String versionOldArr[] = oldversion.split("\\.");
            String versionCurArr[] = curversion.split("\\.");
            int len = versionOldArr.length < versionCurArr.length ? versionCurArr.length : versionOldArr.length;
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    if (Integer.parseInt(versionOldArr[i]) == Integer.parseInt(versionCurArr[i])) {
                        continue;
                    } else if (Integer.parseInt(versionOldArr[i]) > Integer.parseInt(versionCurArr[i])) {
                        return 1;
                    } else
                        return -1;
                }
                //比较完成默认版本两个相等
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    public static String blankReplaceNull(String str, String danwei) {
    	return str == null || str.startsWith("null") ? "" : str + danwei;
    }
    
    public static String replaceHttp(String url) {
    	return url != null && !url.contains("https") ? url.replace("http", "https") : url;
    }
}
