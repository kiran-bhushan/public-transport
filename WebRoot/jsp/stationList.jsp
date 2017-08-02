<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.assignment.bean.StationBean"/>
<jsp:directive.page import="java.util.ArrayList"/>
<% List stationList = new ArrayList();
   if(request.getAttribute("stationList") != null){
      stationList = (ArrayList)request.getAttribute("stationList");
   }
 %>
 <option value="Select">Select Station</option> 
<%   for(int i=0;i<stationList.size();i++){
        StationBean sBean = (StationBean)stationList.get(i);
        String stationCode = sBean.getCode();
        String stationName = sBean.getName();
%>
     <option value="<%=stationCode%>"><%=stationName%></option>
<%}%>
  
      