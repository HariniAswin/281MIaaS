/**
 * 
 */

$(document).ready(function() {
	
	$('#submitFormAddHost').click(function() {
		
		var cloud = $('#cloud').val();
		var hostName = $('#hostName').val();
		var os = $('#os').val();
		var totalCPUUnits = $('#totalCPUUnits').val();
		var os = $('#os').val();
		var totalRAM = $('#totalRAM').val();
		var totalStorage = $('#totalStorage').val();
		var resourceType = $('#resourceType').val();
		
		var data = {};
		
		data.hostName = hostName;
		data.os = os;
		data.totalCPUUnits = totalCPUUnits;
		data.totalRam = totalRAM;
		data.totalStorage = totalStorage;
		data.resourceType = resourceType;
		
		console.log("here");
		
		console.log(JSON.stringify(data));
		
		if(cloud === 'undefined' || cloud === '') {
			alert("Please assign a cloud.");
		} else {
			$.ajax({
				url: '/api/cloud/' + cloud + '/host',
				dataType: 'json',
				data: JSON.stringify(data),
				type: 'POST',
				contentType : 'application/json',
				success : function (data) {
					console.log("data : " + data);
					if(typeof(data.hasErrors) !== 'undefined') {
						
						if(data.hasErrors) {
							alert(data.errorMessage);
						} else {
							alert("Successfully added a host of type :" + resourceType + " into cloud : " + cloud);
						}
						
					}
					else {
						alert('Add Host API failed');
					}
					ehealth.cem.util.common.hideLoadingSpinner();
				},
				error : function (data) {
					if(typeof(data.hasErrors) !== 'undefined') {
						if(data.hasErrors) {
							alert(data.errorMessage);
						} else {
							alert('Add Host API failed');
						}
					} else {
						alert('Add Host API failed');
					}
				}
			});
		}
		
		return false;
		
	});
	
});