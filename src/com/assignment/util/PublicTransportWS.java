package com.assignment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.assignment.bean.StationBean;
import com.assignment.bean.StationListBean;
import com.assignment.bean.TrainPredictionBean;
import com.assignment.bean.TrainPredictionListBean;
import com.assignment.controller.ActionExecute;
import com.google.gson.Gson;

public class PublicTransportWS implements ActionExecute{
	Logger log = null;
	String apiKey = "e1eee2b5677f408da40af8480a5fd5a8";

	public PublicTransportWS(){
		log = Logger.getLogger(this.getClass().getName());
	}


	public String processRequest(HttpServletRequest request, HttpServletResponse response) {
		String apiType = request.getParameter("apiType"); 
		String url = null;
		String jsonStr = null;
		String pagePath = null;
		try{
			Gson gson = new Gson(); 
			if("getAllStations".equals(apiType)){
				url = "https://api.wmata.com/Rail.svc/json/jStations?api_key="+apiKey;
				jsonStr = getWebService(url);
				StationListBean bean = gson.fromJson(jsonStr,StationListBean.class);
				List<StationBean> list = bean.getStations();
				
				Collections.sort(list);
				request.setAttribute("stationList",list);
				pagePath = "/jsp/stationList.jsp";
			}else if("getNextTrains".equals(apiType)){
				String stationCode = request.getParameter("stationCode");
				//Sample URL : https://api.wmata.com/StationPrediction.svc/json/GetPrediction/G03?api_key=e1eee2b5677f408da40af8480a5fd5a8
				url = "https://api.wmata.com/StationPrediction.svc/json/GetPrediction/"+stationCode+"?api_key="+apiKey;
				jsonStr = getWebService(url);
				TrainPredictionListBean bean = gson.fromJson(jsonStr,TrainPredictionListBean.class);
				List<TrainPredictionBean> list = bean.getTrains();
				Collections.sort(list);
				request.setAttribute("trainList",list);
				pagePath = "/jsp/nextTrainsList.jsp";

			}
			if(log.isInfoEnabled())log.info("{processRequest}{Api Type :"+apiType+"|URL :"+url+"|JSON Response :"+jsonStr+"}");
			
		}catch(Exception e){
			log.error("Error-processRequest{ApiType :"+apiType+"} :",e);
		}
		return pagePath;
	}


	private String getWebService(String url) {
		String responseStr = null;
		try{
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);
			if(log.isInfoEnabled())log.info("{getWebService}{WMATA URL :"+method.getURI().toString()+"|Response Code :"+statusCode+"-"+method.getStatusLine()+"}");
			if(statusCode == HttpStatus.SC_OK){
				InputStream rstream = null;
				rstream = method.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
				String line;
				StringBuffer responseBuffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					responseBuffer.append(line);
				}
				br.close();
				responseStr = responseBuffer.toString();
				if(log.isInfoEnabled())log.info("{getWebService}{Response String :"+responseStr+"}");
			} 
		}catch(Exception e){
			log.error("Error-getWebService{WMATA URL :"+url+"} :",e);
		}
		return responseStr;
	}

	private String postWebService(String url,HashMap<String,String> paramMap) {
		String responseStr = null;
		try{
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				method.addParameter(entry.getKey(),entry.getValue());
			}
			int statusCode = client.executeMethod(method);
			if(log.isInfoEnabled())log.info("{postWebService}{WMATA URL :"+method.getURI().toString()+"|Response Code :"+statusCode+"-"+method.getStatusLine()+"}");
			if(statusCode == HttpStatus.SC_OK){
				InputStream rstream = null;
				rstream = method.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
				String line;
				StringBuffer responseBuffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					responseBuffer.append(line);
				}
				br.close();
				responseStr = responseBuffer.toString();
				if(log.isInfoEnabled())log.info("{postWebService}{Response String :"+responseStr+"}");
			} 
		}catch(Exception e){
			log.error("Error-postWebService{WMATA URL :"+url+"} :",e);
		}
		return responseStr;
	}

	
}


