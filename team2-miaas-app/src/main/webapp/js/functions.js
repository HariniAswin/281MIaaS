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
	        		+ '<td>' + rraObj.ramHours + '</td>'
	        		+ '<td>' + rraObj.cpuHours + '</td>'
	        		+ '<td>' + rraObj.storageHours + '</td>'
	        		+ '<td>' + rraObj.ramCost + '</td>'
	        		+ '<td>' + rraObj.cpuCost + '</td>'
	        		+ '<td>' + rraObj.storageCost + '</td>'
	        		+ '<td>' + rraObj.resourceType + '</td>'
	        		+ '<td>' + rraObj.status + '</td>'
	        		+ '</tr>'
	        );
	        
	    }
		
		$('#resourceRequests').html(html.join(''));
	}
	
});

func.populateUsers = (function(data) {
	
	if(data !== 'undefined') {
		
		var html = [];
		
		for(var i=0;i<data.length;i++){
	        var user = data[i];
	        
	        html.push('<tr>' 
	        		+ '<td> <a href="/html/userRequests.html?userName=' + user.userName + '">' + user.userName + '</a></td>'
	        		+ '<td>' + user.numberOfRequests + '</td>'
	        		+ '</tr>'
	        );
	    }
		$('#usersList').html(html.join(''));
	}
	
});

func.populateUserRequests = (function(userName, data) {
	
	$('#userNameId').html(userName);
	if(data !== 'undefined') {
		
		$('#totalRamHours').html(data.totalRamHours);
		$('#totalCPUHours').html(data.totalCPUHours);
		$('#totalDiskHours').html(data.totalDiskHours);
		$('#totalRamCost').html(data.totalRamCost);
		$('#totalDiskCost').html(data.totalDiskCost);
		$('#totalCPUCost').html(data.totalCPUCost);
		$('#totalCost').html(data.totalCost);
		
		var userResources = data.userResources;
		
		var html = [];
		
		for(var i=0;i<userResources.length;i++){
	        var rraObj = userResources[i];
	        
	        html.push('<tr class="odd gradeX">' 
	        		+ '<td>' + rraObj.name + '</td>'
	        		+ '<td>' + rraObj.os + '</td>'
	        		+ '<td>' + rraObj.ram + '</td>'
	        		+ '<td>' + rraObj.cpu + '</td>'
	        		+ '<td>' + rraObj.storage + '</td>'
	        		+ '<td>' + rraObj.userName + '</td>'
	        		+ '<td>' + rraObj.assignedHost + '</td>'
	        		+ '<td>' + rraObj.assignedCloud + '</td>'
	        		+ '<td>' + rraObj.ramHours + '</td>'
	        		+ '<td>' + rraObj.cpuHours + '</td>'
	        		+ '<td>' + rraObj.storageHours + '</td>'
	        		+ '<td>' + rraObj.ramCost + '</td>'
	        		+ '<td>' + rraObj.cpuCost + '</td>'
	        		+ '<td>' + rraObj.storageCost + '</td>'
	        		+ '<td>' + rraObj.resourceType + '</td>'
	        		+ '<td>' + rraObj.status + '</td>'
	        		+ '</tr>'
	        );
	        
	    }
		
		$('#resourceRequests').html(html.join(''));
	}
	
});

func.getUrlVars = (function() {
	var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
});