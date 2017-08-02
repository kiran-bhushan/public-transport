<html>
<head>
  <title>Washington Metropolitan Area Transit</title>
  <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
<script type="text/javascript"  src="http://maps.google.com/maps/api/js?key=AIzaSyBDmmxMpS0K4Itj5muALjnscQibqSG40Sw"></script>  
<script src="<%= request.getContextPath()%>/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<link href="<%= request.getContextPath() %>/css/ptapp.css" media="screen" type="text/css" rel="stylesheet" />
<script language="JavaScript1.2">
 function showlocation() {
 getAllStations();
 if("geolocation" in navigator){
     navigator.geolocation.getCurrentPosition(callback,errorHandler);
   }else{
     console.log("Browser doesn't support geolocation!");
   }  
  }
function errorHandler(error) {
  switch(error.code) {
    case error.PERMISSION_DENIED:
      alert("User denied the request for Geolocation.");
      break;
    case error.POSITION_UNAVAILABLE:
      alert("Location information is unavailable.");
      break;
    case error.TIMEOUT:
      alert("The request to get user location timed out.");
      break;
    case error.UNKNOWN_ERROR:
      alert("An unknown error occurred.");
      break;
    }
  }
  
function callback(position) {
   var lat = position.coords.latitude;
   var lng = position.coords.longitude;
   displayLocation(lat,lng);
   
}

function displayLocation(latitude,longitude){
    var geocoder;
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(latitude, longitude);

    geocoder.geocode(
        {'latLng': latlng}, 
        function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    var add= results[0].formatted_address ;
                    var  value=add.split(",");

                    var count=value.length;
                    country=value[count-1];
                    state=value[count-2];
                    city=value[count-3];
                    document.getElementById('latitude').innerHTML = latitude;
                    document.getElementById('longitude').innerHTML = longitude;
                    document.getElementById('country').innerHTML = country;
                    document.getElementById('state').innerHTML = state;
                    document.getElementById('city').innerHTML = city;
                }
                else  {
                    alert("address not found");
                }
            }
            else {
                alert("Geocoder failed due to: " + status);
            }
        }
    );
}

function getAllStations(){
   $.ajax({
            type: "POST",
            data: {},
            url: "pt?reqType=publicTransportWS&apiType=getAllStations",
            success: function(responseText){
              $("#stationListId").html(responseText);
            }
           });  
}

function getNextTrains(){
   var stationCode = document.getElementById("stationListId").value;
   //alert("Inside GetNextTrains :"+stationCode);
   if(stationCode == 'Select'){
     alert("Please select station name");
     document.getElementById("stationListId").focus();
   }else{
    $.ajax({
            type: "POST",
            data: {stationCode : stationCode},
            url: "pt?reqType=publicTransportWS&apiType=getNextTrains",
            success: function(responseText){
              $("#nextTrainId").html(responseText);
            }
           }); 
    }       
}


</script>
</head>
<body onload="showlocation()">
<form method="post" action="pt" name="indexPage">
<input type="hidden" name="reqType" value="">
<h4>Assignment Code</h4>
<br/>
 Latitude: <span id="latitude"></span>
 <br/>
 Longitude: <span id="longitude"></span>
 </br>
 Country : <span id="country"></span>
 </br>
 State : <span id="state"></span>
 </br>
 City : <span id="city"></span>
 </br>
 <div class="divtable">
	    <div class="divrow">
		    <div class="divHd">Next Train</div>
       </div>
       <div class="divrow">
		    <div class="divA">All Stations</div>
            <div class="divB"><select name="stations" id="stationListId">
                 <option value="Select">Select Station</option>
                 </select>
             </div>&nbsp;&nbsp;<a href="javascript:getNextTrains()"><h4>Submit</h4></a>
		</div>
 </div>      
 <div id="nextTrainId"></div><!-- Next Trains List -->
 
 </form>
</body>
</html>