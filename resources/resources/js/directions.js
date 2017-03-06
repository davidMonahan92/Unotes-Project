var dcu = new google.maps.LatLng(53.385255,-6.257137999999941);
var map1;
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();

google.maps.event.addDomListener(window, 'load', function () {
	var places = new google.maps.places.Autocomplete(document.getElementById('from'));
	google.maps.event.addListener(places, 'place_changed', function () {
		var place = places.getPlace();
	});
	var places1 = new google.maps.places.Autocomplete(document.getElementById('to'));
	google.maps.event.addListener(places1, 'place_changed', function () {
		var place1 = places1.getPlace();
	});
});

function calculateRoute(from, to) {
	var mapDetails = {
	  zoom: 14,
	  center: dcu,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	// Draw the map
	map1 = new google.maps.Map(document.getElementById("map"), mapDetails);

	var directionsService = new google.maps.DirectionsService();
	var directionsRequest = {
	  origin: from,
	  destination: to,
	  travelMode: google.maps.DirectionsTravelMode.DRIVING,
	  unitSystem: google.maps.UnitSystem.METRIC
	};
	directionsService.route(directionsRequest, function(response, status)
	{
		
		if (status == google.maps.DirectionsStatus.OK)
		{
		  new google.maps.DirectionsRenderer({
				map: map1,
				panel: document.getElementById('directions'),
				directions: response
		  });
		}
		else
		  $("#error").append("Unable to retrieve your route<br />");
	});
	
	
}

$(document).ready(function() {
	// If the browser supports the Geolocation API
	if (typeof navigator.geolocation == "undefined") {
	  $("#error").text("Your browser doesn't support the Geolocation API");
	  return;
	}
	 <!---------------------------------------------------------------------------------------->
	$("#from-link, #to-link").click(function(event) {
		event.preventDefault();
		var addressId = this.id.substring(0, this.id.indexOf("-"));

		navigator.geolocation.getCurrentPosition(function(position) {
			var geocoder = new google.maps.Geocoder();
			
			geocoder.geocode({
			  "location": new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
			},
			
			function(results, status) {
			  if (status == google.maps.GeocoderStatus.OK)
					$("#" + addressId).val(results[0].formatted_address);
			  else
					$("#error").append("Unable to retrieve your address<br />");
			});
		},
		function(positionError){
			$("#error").append("Error: " + positionError.message + "<br />");
		},
		{
			enableHighAccuracy: true,
			timeout: 10 * 1000 // 10 seconds
		});
	});

	$("#calculate-route").submit(function(event) {
	  event.preventDefault();
	  calculateRoute($("#from").val(), $("#to").val());
	});
	


	<!--Make it work on iPhone and Android -->
	function detectBrowser(){ 
		var useragent = navigator.userAgent;
		var mapdiv = document.getElementById("carpool-map");
		
		if(userAgent.indexOf('iPhone') || userAgent.indexOf('Android') !=1){
			mapdiv.style.width = '100%';
			mapdiv.style.height = '100%';
		} 
		else{
			mapdiv.style.width= '600px';
			mapdiv.style.height= '800px';
		}
	}
	
	<!------------------------------------------------->
	
	var fuelCostCalcer = {
		//define the input fields
		inputAr : new Array( 'miles', 'mpg', 'cost_per_litre' ),
		//define the results fields
		resultAr : new Array( 'gallons_used', 'litres_used', 'total_cost' ),
		//function to init object, set listeners, make first results
		init : function()
		{
			var $fi;
			//for each input field, add a change listener
			for( var i in this.inputAr )
			{
				$fi = $('#'+ this.inputAr[i] +'_fi'); //select the field as jQuery object
				//bind the events, triggered when value changes
				$fi.bind( 'keyup change', this.doCalc );
			}
			//trigger change on the last field, to init the result
			$fi.trigger( 'change' );
		},
		//triggerd by field value change
		doCalc : function(eventObj)
		{
			/* CHECK AND SET THE INPUT VARS */

			//the inputVars obj

			var inputVars = {};

			//do input validation

			var isValid = true;

			for( var i in fuelCostCalcer.inputAr )
			{
				var fiId = fuelCostCalcer.inputAr[i]; //get field ID
				var val = $('#'+fiId+'_fi').val(); //get the value from the input field
				//if is in valid value
				if( isNaN(parseFloat(val)) || val < 0 || val > 10000000 ){
					isValid = false;
					break;
				}
				//else set as property in this object
				else
					inputVars[ fiId ] = val;
			}
			/* SET THE RESULT VARS */

			//the resultVars obj

			var resultVars = {};

			//if inputs are valid then calculate the results
			if( isValid )
			{
				var milesPerLitre = inputVars['mpg'] / 4.54609188; //4.5 is the litres in a gallon
				resultVars['gallons_used'] = inputVars['miles'] / inputVars['mpg'];
				resultVars['litres_used'] = inputVars['miles'] / milesPerLitre;
				resultVars['total_cost'] = resultVars['litres_used'] * inputVars['cost_per_litre'];
			}
			//not valid, so set all result vars to 0
			else
			{
				for( var i in fuelCostCalcer.resultAr )
					resultVars[ fuelCostCalcer.resultAr[i] ] = 0;
			}
			/* SET RESULTS IN TO HTML */
			for( var i in fuelCostCalcer.resultAr )
			{
				var laId = fuelCostCalcer.resultAr[i]; //get label/span ID

				$('#'+laId+'_la').text( resultVars[laId].toFixed(2) );
			}
		}
	};
	fuelCostCalcer.init();
});	