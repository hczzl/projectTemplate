package com.glch.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/*
 * 短信接口工具
 */
@Component
public class PhoneMsgUtil{
	
	private static String msgServerUrl;
	
	public static String getMsgServerUrl() {
		return msgServerUrl;
	}

	@Value("${phoneMsg.msgServerUrl}")
	public void setMsgServerUrl(String msgServerUrl) {
		PhoneMsgUtil.msgServerUrl = msgServerUrl;
	}

	//这个发送短信信息是不用绑定服务ip的
	public static Map<String,Object> sendQtMsg(String phoneNumber,String msg){
		Service service = new Service();
		Call call;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(msgServerUrl!=null && msgServerUrl.equalsIgnoreCase("http://no")){
			System.out.println("测试环境不发短信");
			return resultMap;
		}
		String result = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(msgServerUrl));
			call.setOperationName(new QName("http://service.ds12110.ds12110.dscomm.com/","sendShortMessage"));
			call.addParameter("arg0",
					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("arg1",
					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("arg2",
					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("arg3",
					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			Object[] obj = new Object[]{"112123123123131",phoneNumber,msg,"111111111111"};
			result = (String) call.invoke(obj);
			System.out.println(result); // <?xml version="1.0" encoding="UTF-8"?><results serialNo ="369525"  result="0" />
			Document docResult = DocumentHelper.parseText(result);
			Element root = docResult.getRootElement();
			String resultResult = root.attributeValue("result");
			if("0".equals(resultResult)){
				resultMap.put("state", 1); // 1表示发送成功
			}else{
				resultMap.put("state", 2);  // 2表示未发送成功
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送短信失败!原因:" + result);
			resultMap.put("state", 2); // 2表示未发送成功
		}
		resultMap.put("result", result);
		return resultMap;
	}
}
