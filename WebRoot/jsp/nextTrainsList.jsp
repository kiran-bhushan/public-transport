
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.assignment.bean.TrainPredictionBean"/> 
<% List trainList = new ArrayList(); 
     if(request.getAttribute("trainList") != null){
      trainList = (ArrayList)request.getAttribute("trainList");
   }
  %>
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
 <% if(trainList.size() == 0){ %> 
   <font color='red'>No Trains scheduled for arrival</font>
 <%}else{%>
   <thead>
      <tr>
          <th class="col0">Cars</th>
          <th class="col1">Destination</th>
          <th class="col2">Minutes</th>
     </tr>
   </thead>
   <tbody>
     
   <% for(int i=0;i<trainList.size();i++){ 
       TrainPredictionBean bean = (TrainPredictionBean)trainList.get(i);
       
   %>   
        <tr>
          <td class="col0"><%=bean.getCar() %></td>
          <td class="col1"><%=bean.getDestinationName() %></td>
          <td class="col2"><%=bean.getMin() %></td>
        </tr>
    <%}} %>
      <font color='green'>***ARR-Arriving / BRD-Boarding / ---Empty</font>
      </tbody>
    </table>