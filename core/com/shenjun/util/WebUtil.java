package com.shenjun.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.shenjun.enums.BrowserType;
import com.shenjun.enums.HttpRespEnum;
import com.shenjun.io.HtppSendCallBack;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.system.VarUtils;
import com.shenjun.web.pages.Constants;
import com.shenjun.web.pages.Search;
import com.shenjun.web.thread.Lc;

/**
 * Web容器工具类
 * @author: 沈军
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class WebUtil {
	
	private static final Log log = LogFactory.getLog(WebUtil.class);
	
	/**
	 * 获取请求的内容
	 * @return
	 */
	public static byte[] getReqString(){
		InputStream sis;
		try {
			sis = Struts2Util.getServletRequest().getInputStream();
		
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];int n=0;
			while ((n = sis.read(temp,0,1024)) != -1) {
				bos.write(temp, 0, n);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			log.error("WebUtil getReqString "+e,e.getCause());
		}
		return null;
	}
	
	/**
	 * 获取浏览器的版本
	 * @return
	 */
	public static BrowserType getBrowser(){
		String browser=Lc.getServletRequest().getHeader("User-Agent");
		if(RegexUtil.isFindStr("MSIE", browser)){
			return BrowserType.MSIE;
		}else if(RegexUtil.isFindStr("Chrome", browser)){
			return BrowserType.CHROME;
		}else{
			return BrowserType.OTHER;
		}
	}
	
	
	/**
	 * 获取Web的首地址HTPP://0.0.0.0:00/A
	 * @return  HTPP://0.0.0.0:00/A
	 */
	public static String getWebRoot(){
		/*return "http://"+Struts2Util.getServletRequest().getLocalAddr()+":" +
			  Struts2Util.getServletRequest().getLocalPort()+""+
			  Struts2Util.getServletRequest().getContextPath();*/
		return getWebRoot(Struts2Util.getServletRequest());
	}
	
	/**
	 * 获取Web的首地址 HTPP://0.0.0.0:00/A
	 * @param request
	 * @return  HTPP://0.0.0.0:00/A
	 */
	public static String getWebRoot(HttpServletRequest request){
		
//		return "http://"+request.getRequestURL()+":" +
//				request.getLocalPort()+""+
//				request.getContextPath();
		return request.getRequestURL().toString().replaceAll(request.getRequestURI(), request.getContextPath()+"/");
	}
	
	/**
	 * 获取本地Web的路径
	 * @return C:/tomcat/webapps/app/WEB-INF/classes/
	 */
	public static String getClassesRoot(){
		String path=null;
		try {
			path = WebUtil.class.getResource("").toURI().getPath().replace("com/shenjun/util/", "");
		} catch (URISyntaxException e) {
			log.error(e,e.getCause());
		}
		return path;
	}
	
	public static String getCharacterEncoding(){
		return Lc.getServletRequest().getCharacterEncoding(); 
	}
	
	/**
	 * 获取本地Web的路径
	 * @return C:/tomcat/webapps/app/
	 */
	public static String getLocalWebRoot(){
		return getClassesRoot().replace("WEB-INF/classes/", "");
	}

	/**
	 * Copy查询条件
	 * @param actionContext
	 * @param searchData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Search copySearchMsg(ActionContext actionContext,Object searchData){
		Map sessionMap = actionContext.getSession();
		
		Map map = actionContext.getParameters();

		Search seach = new Search();
		
		if(map.get(Constants.SPLIT_PAGE_CURRENTPAGENO_PARAM)!=null){
			seach.setCurrentPageNo(new Integer(((String[])map.get(Constants.SPLIT_PAGE_CURRENTPAGENO_PARAM))[0]));
		}
		if(map.get(Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM)!=null){
			seach.setPageSize(new Integer(((String[])map.get(Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM))[0]));
		}
		if(map.get(Constants.RESTORE_PARAM)!=null){
			seach.setRestore(((String[])map.get(Constants.RESTORE_PARAM))[0].toString());
		}
		if(searchData!=null){
			sessionMap.put(Constants.SEARCH_PARAM,searchData);
		}
		if(sessionMap.get(Constants.SEARCH_PARAM)!=null){
			seach.setSearchData(sessionMap.get(Constants.SEARCH_PARAM));
		}
		
		return seach;
	}
	
	public static Map<String,String> urlToMap(String url){
		
		return null;
	}
	
	/**
	 * 发送Http数据
	 * @param method see PostMethod|GetMethod
	 * @param httpRespEnum
	 * @param callback
	 * @return
	 */
	public static <T> T httpSendData(HttpMethodBase method,HttpRespEnum httpRespEnum,HtppSendCallBack callback){
		HttpClient client = VarUtils.VarContent(HttpClient.class, null, false);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		client.getParams().setSoTimeout(300*1000);
		Object returnObj=null;
		Integer statusCode=0;
		try {
			statusCode = client.executeMethod(method);
			
			if(statusCode == HttpStatus.SC_OK){
				if(httpRespEnum == HttpRespEnum.JSON){
					returnObj=JSONUtils.fromJson(method.getResponseBodyAsString());
				}else if(httpRespEnum == HttpRespEnum.XML){
					returnObj=XMLUtil.StringToXml(method.getResponseBodyAsString());
				}else if(httpRespEnum == HttpRespEnum.BYTE){
					returnObj=method.getResponseBody();
				}else if(httpRespEnum == HttpRespEnum.Stream){
					if(callback!=null){
						InputStream is = method.getResponseBodyAsStream();
						callback.httpStream(is);
						if(is.available()!=0){
							is.close();
						}
					}
					returnObj="ok";
				}else{
					returnObj=method.getResponseBodyAsString();
				}
			}else{
				log.error("webutil httpPostData error:"+statusCode);
			}
			
		} catch (HttpException e) { 
			log.error("webutil httpSendData HttpException:"+e.getMessage(),e.getCause());
		} catch (IOException e) {
			log.error("webutil httpSendData IOException:"+e.getMessage(),e.getCause());
		}
		method.releaseConnection();
		VarUtils.VarContent(HttpClient.class, client, false);
		return (T)returnObj;
	}

	/**
	 * 发送Http数据，有参数值的
	 * @param url
	 * @param params
	 * @return
	 */
	public static <T> T httpSend(String url,HttpRespEnum httpRespEnum,HtppSendCallBack callback,Object... objects){
		PostMethod myPost = new PostMethod(url);
		if(objects!=null){
			Map<String,Object> params = MapUtils.instanceObj(objects);
			for(Map.Entry<String, Object> entry: params.entrySet()){
				myPost.addParameter(entry.getKey(), entry.getValue()+"");
			}
		}
		return httpSendData(myPost,httpRespEnum,callback);
	}
	/**
	 * 发送Http数据，有参数值的
	 * @param url
	 * @param objects
	 * @return
	 */
	public static String httpSend(String url,Object... objects){
		return httpSend(url,HttpRespEnum.TEXT,null,objects);
	}
	
	/**
	 * 发送Http数据,无参数的，通过主体Body传输
	 * @param url
	 * @param content
	 * @param contentType "application/json"
	 * @param charset "UTF-8"
	 * @return
	 */
	public static  <T> T httpSendBody(String url,HttpRespEnum httpRespEnum,HtppSendCallBack callback,String content, String contentType, String charset){
		PostMethod myPost = new PostMethod(url);
		try {
			myPost.setRequestEntity(new StringRequestEntity(content,contentType,charset));
		} catch (UnsupportedEncodingException e) {
			log.error("webutil httpSendData 2 IOException:"+e.getMessage(),e.getCause());
		}
        return httpSendData(myPost,httpRespEnum,callback);
	}
	/**
	 * 发送Http数据,无参数的，通过主体Body传输
	 * @param url
	 * @param content
	 * @param contentType "application/json"
	 * @param charset "UTF-8"
	 * @return
	 */
	public static String httpSendBody(String url,String content, String contentType, String charset){
		return httpSendBody(url,HttpRespEnum.TEXT,null,content,contentType,charset);
	}
	
	/*public static void main(String[] args) {
		System.out.println("aaabbbcc".substring("".indexOf("?")));
	}*/
}
