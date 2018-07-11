package com.bj58.fang.url;
import com.bj58.spat.wos.ClientConfig;
import com.bj58.spat.wos.WOSClient;
import com.bj58.spat.wos.meta.InsertOnly;
import com.bj58.spat.wos.request.DelRequest;
import com.bj58.spat.wos.request.GetFileLocalRequest;
import com.bj58.spat.wos.request.GetFileRequest;
import com.bj58.spat.wos.request.GetFileUrlRequest;
import com.bj58.spat.wos.request.UploadFileRequest;
import com.bj58.spat.wos.request.UploadSliceFileRequest;
import com.bj58.spat.wos.request.PrecheckUploadFileRequest;
import com.bj58.spat.wos.request.SetFileTtlRequest;
import com.bj58.spat.wos.sign.Credentials;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//上传资源的域名
		String uploadDomain = "testv1.wos.58dns.org";
		//下载资源的域名
		String downDomain = "testv1.wos.58dns.org";
		//获取token的域名
		String tokenserverDomain = "tokentest.wos.58dns.org";
		String appId = "ACbXwVuTsRqAf";
		String bucket = "test";
		String secretId = "Qu5Wx4aMATJoi95QaAbEDiPslE76hpcX";
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setUploadWosEndPointDomain(uploadDomain);
		clientConfig.setDownWosEndPointDomain(downDomain);
		clientConfig.setTokenServerDomain(tokenserverDomain);
		Credentials cred = new Credentials(appId, secretId);
		
		WOSClient client = new WOSClient(clientConfig, cred);
		
		//上传文件，默认不覆盖，覆盖只针对小文件，大文件分片不存在覆盖
		//该接口既支持小文件上传，也支持分片文件上传，对这两种上传方式做了封装
		//如果上传文件大小小于4M，则是小文件上传，否则是分片上传
		//分片文件上传时，使用的是config中的slicesize进行分片的
		String fileName = "1.jpg";
		String localFilePath = "D:\\head.jpg";//"src/test/resources/1.jpg";
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket, fileName, localFilePath);
		//设置覆盖参数
		//uploadFileRequest.setInsertOnly(InsertOnly.OVER_WRITE);
		String uploadFileRet = client.uploadFile(uploadFileRequest);
		System.out.println("upload file ret:" + uploadFileRet);
		
		//下载文件，只支持通过url下载，这个url是上传成功时返回的url
		String url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/1.jpg"; //普通url，针对bucket为公有读
		localFilePath = "data/1.jpg";
		GetFileRequest getFileRequest = new GetFileRequest(url);
		String getFileRet = client.getFileLocal(getFileRequest, localFilePath);
		System.out.println("get file ret:" + getFileRet);
		
		//下载文件
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test1-9fe6fb87/11.jpg";//特殊url，针对bucket为私有读
		localFilePath = "data/11.jpg";
		getFileRequest = new GetFileRequest(url);
		getFileRet = client.getFileLocal(getFileRequest, localFilePath);
		System.out.println("get file ret:" + getFileRet);
		//删除文件，只支持通过url下载，这个url是上传成功时返回的url
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/1.jpg";
		DelRequest delRequest = new DelRequest(url);
		String delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		//使用小文件接口进行上传
		fileName = "2.jpg";
		localFilePath = "src/test/resources/2.jpg";
		UploadFileRequest uploadSingleFileRequest = new UploadFileRequest(bucket, fileName, localFilePath);
		uploadFileRet = client.uploadSingleFile(uploadSingleFileRequest);
		System.out.println("upload single file ret:" + uploadFileRet);
		
		//不要使用这个下载接口，未来会废弃掉，直接使用url下载的函数
		localFilePath = "data/2.jpg";
		GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucket, fileName, localFilePath);
		String getFileLocalRet = client.getFileLocal(getFileLocalRequest);
		System.out.println("get file local Ret:" + getFileLocalRet);
		
		//删除文件
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/2.jpg";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		//使用分片文件上传接口进行上传
		fileName = "3.jpg";
		localFilePath = "src/test/resources/3.jpg";
		int slicesize = 1048576;
		UploadSliceFileRequest sliceFileRequest = new UploadSliceFileRequest(bucket, fileName, localFilePath, slicesize);
		uploadFileRet = client.uploadSliceFile(sliceFileRequest);
		System.out.println("upload slice file ret:" + uploadFileRet);
		
		//删除文件
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/3.jpg";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		//获取资源的url
		fileName = "1.jpg";
		GetFileUrlRequest getFileUrlRequest = new GetFileUrlRequest(bucket, fileName);
		String getFileUrlRet = client.getFileAccessUrl(getFileUrlRequest);
		System.out.println("get file access url:" + getFileUrlRet);
		
		//通过预检接口上传文件，默认不覆盖，覆盖只针对小文件，大文件分片不存在覆盖
		//该接口既支持小文件上传，也支持分片文件上传，对这两种上传方式做了封装
		//如果上传文件大小小于4M，则是小文件上传，否则是分片上传
		//分片文件上传时，使用的是config中的slicesize进行分片的
		fileName = "4.jpg";
		localFilePath = "src/test/resources/4.jpg";
		PrecheckUploadFileRequest precheckUploadFileRequest = new PrecheckUploadFileRequest(bucket, fileName, localFilePath);
		uploadFileRet = client.precheckUploadFile(precheckUploadFileRequest);
		System.out.println("precheck upload file ret:" + uploadFileRet);
		
		//使用小文件预检接口进行上传
		fileName = "5.jpg";
		localFilePath = "src/test/resources/5.jpg";
		PrecheckUploadFileRequest preUploadSingleFileRequest = new PrecheckUploadFileRequest(bucket, fileName, localFilePath, 0);
		uploadFileRet = client.precheckUploadSingleFile(preUploadSingleFileRequest);
		System.out.println("precheck upload single file ret:" + uploadFileRet);
		
		//删除文件
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/4.jpg";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/5.jpg";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		//使用分片文件预检接口进行上传
		fileName = "bigfile";
		localFilePath = "src/test/resources/bigfile";
		slicesize = 1048576;
		PrecheckUploadFileRequest preUploadSliceFileRequest = new PrecheckUploadFileRequest(bucket, fileName, localFilePath, slicesize);
		uploadFileRet = client.precheckUploadSliceFile(preUploadSliceFileRequest);
		System.out.println("precheck upload slice file ret:" + uploadFileRet);
		 
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/bigfile";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
		//带ttl上传文件,UploadFileRequest,UploadSliceFileRequest,PrecheckUploadFileRequest使用方法相同。
		fileName = "7.jpg";
		localFilePath = "src/test/resources/7.jpg";
	    uploadFileRequest = new UploadFileRequest(bucket, fileName, localFilePath);
		//设置ttl参数,单位小时，最小为168.
		uploadFileRequest.setTtl(168);
		uploadFileRet = client.uploadFile(uploadFileRequest);
		System.out.println("ttl upload file ret:" + uploadFileRet);
		
		//设置文件TTL接口，对于已经上传的文件，可以再次调用该接口改变TTL值
        fileName = "7.jpg";
        int ttl = 240;
        SetFileTtlRequest setFileTtlRequest = new SetFileTtlRequest(bucket, fileName, ttl);
        String setFileTtlRet = client.setFileTtl(setFileTtlRequest);
        System.out.println("setFileTtl file ret:" + setFileTtlRet);
		
		url = "http://testv1.wos.58dns.org/ACbXwVuTsRqAf/test/7.jpg";
		delRequest = new DelRequest(url);
		delFileRet = client.delFile(delRequest);
		System.out.println("del file ret:" + delFileRet);
		
	}
}

