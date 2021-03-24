package com.glch.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpclientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpclientUtil.class);

	private RequestConfig requestConfig = RequestConfig.custom()
	.setSocketTimeout(600000)
	.setConnectTimeout(600000)
	.setConnectionRequestTimeout(600000)
	.build();
	private static HttpclientUtil instance = null;
	
	private SSLClient httpConn;
	
	public HttpclientUtil() {
		if (httpConn == null) {
			try {
				httpConn = new SSLClient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static HttpclientUtil getInstance(){
		if(instance==null){
			instance = new HttpclientUtil();
		}
		return instance;
	}
	
	/*
	 * @param httpUrl
	 * @param params 参数，格式：key1=value1&key2=value2
	 */
	public String sendHttpPost(String httpUrl, String params){
		HttpPost post = new HttpPost(httpUrl);
		try{
			StringEntity entity = new StringEntity(params,"UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			post.setEntity(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendHttpPost(post);
	}
	public String sendHttpPost(String httpUrl,StringEntity entity){
		HttpPost post = new HttpPost(httpUrl);
		try{
			post.setEntity(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendHttpPost(post);
	}
	public String sendHP(HttpPost post){
		return sendHttpPost(post);
	}
	private String sendHttpPost(HttpPost post){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try{
			httpClient = HttpClients.createDefault();
			post.setConfig(requestConfig);
			response = httpClient.execute(post);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(response!=null){
					response.close();
				}
			}catch(Exception e){
				//e.printStackTrace();
			}
			try{
				if(httpClient!=null){
					httpClient.close();
				}
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		return responseContent;
	}
	
	public JSONObject doPost(String url, Map<String,String> map) throws JSONException {
    	String charset ="utf-8";
    	
    	JSONObject json = new JSONObject();
    	
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            
            StringBuffer reqSb = new StringBuffer() ;
            reqSb.append("---------上送报文----------\n") ;
            reqSb.append("url:").append(url).append("\n") ;
            reqSb.append("params:").append(map).append("\n") ;
            log.info(reqSb.toString()) ;
            
            HttpResponse response = this.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){
        	json.put("State", "-1") ;
        	json.put("msg", "系统异常") ;
        	log.error(ex.getMessage()) ;
        	return json ;
        }  
        
        StringBuffer respSb = new StringBuffer() ;
        respSb.append("---------返回报文----------\n") ;
        respSb.append("url:").append(url).append("\n") ;
        respSb.append("params:").append(result).append("\n") ;
        log.info(respSb.toString()) ;
        
        json = JSONObject.parseObject(result);
        
        return json;  
    } 
	
	
	/**
	 * @param url postUrl
	 * @param map ParamMap
	 * @param returnType "String"=java.lang.String  "JSONObject"=org.json.JSONObject "JSONArray"=org.json.JSONArray
	 * @return
	 * @throws JSONException
	 */
	public <T>T doPost(String url,Map<String,String> map,T returnType) throws JSONException{ 
    	String charset ="utf-8";
    	
    	JSONObject json = new JSONObject();
        HttpPost httpPost = null;  
        String result = null;  
        JSONArray jsonArr = new JSONArray();
        String type = returnType.getClass().getName();
        if(!type.equals(JSONObject.class.getName().toString())&&
        	!type.equals(String.class.getName().toString())&&
        	!type.equals(JSONArray.class.getName().toString())) {
        	return (T) new String("仅支持以下3种格式返回的returnType---> " +
        			"\"String\":java.lang.String  \"JSONObject\":org.json.JSONObject \"JSONArray\":net.sf.json.JSONArray");
        }
        try{  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
//            StringBuffer reqSb = new StringBuffer() ;
//            reqSb.append("---------上送报文----------\n") ;
//            reqSb.append("url:").append(url).append("\n") ;
//            reqSb.append("params:").append(map).append("\n") ;
//            log.info(reqSb.toString()) ;
            
            HttpResponse response = this.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        } catch(Exception ex){
        	if(type.equals(JSONObject.class.getName().toString())) {
        		json.put("State", "-1") ;
            	json.put("msg", "系统异常") ;
            	json.put("e", ex);
            	log.error(ex.getMessage());
            	return (T)json;
        	} else if(type.equals(String.class.getName().toString())) {
        		return (T)ex.toString();
        	} else if(type.equals(JSONArray.class.getName().toString())) {
        		json.put("State", "-1") ;
            	json.put("msg", "系统异常") ;
            	json.put("e", ex);
        		jsonArr = JSONArray.parseArray(json.toString());
        		return (T)ex.toString();
        	}
        }  
        
//        StringBuffer respSb = new StringBuffer() ;
//        respSb.append("---------返回报文----------\n") ;
//        respSb.append("url:").append(url).append("\n") ;
//        respSb.append("params:").append(result).append("\n") ;
//        log.info(respSb.toString()) ;
//        if(result.length()==0){
//        	return null;
//        }
        if(type.equals(JSONObject.class.getName().toString())) {
        	json = JSONObject.parseObject(result);
        	return (T)json;
    	} else if(type.equals(String.class.getName().toString())) {
    		return (T)result;
    	} else if(type.equals(JSONArray.class.getName().toString())) {
    		jsonArr = JSONArray.parseArray(result);
    		return (T)jsonArr;
    	} else{
    		 return null;  
    	}
    } 
	
	public HttpResponse execute(HttpUriRequest request) throws Exception {
		if (httpConn == null) {
			httpConn = new SSLClient();
		}
		HttpParams params = httpConn.getParams();
		if(params==null){
			params = new BasicHttpParams();
		}
		//连接超时时间
		HttpConnectionParams.setConnectionTimeout(params, 120*1000);
		//sockect连接超时时间60秒
		HttpConnectionParams.setSoTimeout(params, 120*1000);
		httpConn.setParams(params);
		HttpResponse httpResponse = httpConn.execute(request);
		return httpResponse;
	}
	
	public static void main(String[] args) {
		//测试
		String result = HttpclientUtil.getInstance().sendHttpPost("http://localhost:8080/JiZhenInterface/servlet/interface", "username=admin&password=ff8080815804529b01582e54d28b0034&param=aaa");
		System.out.println(result);
	}
}
