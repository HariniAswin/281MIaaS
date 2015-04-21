var func = {};

func.populateDashBoard = (function(cloudsCount, hostsCount, usersCount, requestsCount) {

	$('#cloudsCount').html(cloudsCount);
	$('#hostsCount').html(hostsCount);
	$('#usersCount').html(usersCount);
	$('#requestsCount').html(requestsCount);

});

func.populateRequests = (function(data) {
	
	if(data !== 'undefined') {
		
		var resourceRequests = data.resourceRequests;
		
		var html = [];
		
		for(var i=0;i<resourceRequests.length;i++){
	        var rraObj = resourceRequests[i];
	        
	        html.push('<tr class="odd gradeX">' 
	        		+ '<td>' + rraObj.name + '</td>'
	        		+ '<td>' + rraObj.os + '</td>'
	        		+ '<td>' + rraObj.ram + '</td>'
	        		+ '<td>' + rraObj.cpu + '</td>'
	        		+ '<td>' + rraObj.storage + '</td>'
	        		+ '<td>' + rraObj.userName + '</td>'
	        		+ '<td>' + rraObj.assignedHost + '</td>'
	        		+ '<td>' + rraObj.assignedCloud + '</td>'
	        		+ '<td>' + rraObj.resourceType + '</td>'
	        		+ '<td>' + rraObj.status + '</td>'
	        		+ '</tr>'
	        );
	        
	    }
		
		$('#resourceRequests').html(html.join(''));
	}
	
});