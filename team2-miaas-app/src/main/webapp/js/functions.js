var func = {};

func.populateDashBoard = (function(cloudsCount, hostsCount, usersCount,
		requestsCount) {

	$('#cloudsCount').html(cloudsCount);
	$('#hostsCount').html(hostsCount);
	$('#usersCount').html(usersCount);
	$('#requestsCount').html(requestsCount);

});

func.populateCloudStats = (function(data) {
	
	if(data != undefined) {
		
		var cloudRequestStats = data.cloudRequestStats;
		
		var navPillsHtml = [];
		var tabContentHtml = [];
		
		for(var i=0;i<cloudRequestStats.length;i++){
			
			var cloudRequestStat = cloudRequestStats[i];
			
			if(i == 0) {
				navPillsHtml.push('<li class="active"><a href="#' + cloudRequestStat.cloudName + '" data-toggle="tab">' + cloudRequestStat.cloudName + '</a>');
				
				tabContentHtml.push(
						  '<div class="tab-pane fade in active" id="' + cloudRequestStat.cloudName + '">' 
						+ '		<h5>Number of Hosts : ' + cloudRequestStat.numberOfHosts + '</h5>'
						+ '		<h5>Number of Requests Allocated : ' + cloudRequestStat.numberOfRequests + '</h5>'
								// add CPU Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">CPU Utilization (Cores)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-cpu-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
								// add RAM Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">Memory Utilization (MB)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-memory-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
								// add RAM Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">Disk Utilization (GB)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-disk-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
						+ '</div>'
				);
				
			} else {
				navPillsHtml.push('<li><a href="#' + cloudRequestStat.cloudName + '" data-toggle="tab">' + cloudRequestStat.cloudName + '</a>');
				
				tabContentHtml.push(
						  '<div class="tab-pane fade" id="' + cloudRequestStat.cloudName + '">' 
						+ '		<h5>Number of Hosts : ' + cloudRequestStat.numberOfHosts + '</h5>'
						+ '		<h5>Number of Requests Allocated : ' + cloudRequestStat.numberOfRequests + '</h5>'
								// add CPU Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">CPU Utilization (Cores)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-cpu-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
								// add RAM Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">Memory Utilization (MB)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-memory-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
								// add RAM Donut
						+ '		<div class="col-lg-4">'
	                    + '			<div class="panel panel-default">'
	                    + '				<div class="panel-heading">Disk Utilization (GB)</div>'
	                    + ' 			<div class="panel-body">'    
	                    + '					<div style="height:342px;width:304px;" id="morris-disk-donut-chart-' + cloudRequestStat.cloudName + '"></div>'        
	                    + '				</div>'    
	                    + '			</div>'		
						+ '		</div>'
						+ '</div>'
				);
			}
			
		}
		
		$('.nav-pills').html(navPillsHtml.join(''));
		$('.tab-content').html(tabContentHtml.join(''));
		
		// add the donut chart
		for(var i=0;i<cloudRequestStats.length;i++){
			var cloudRequestStat = cloudRequestStats[i];
			
			Morris.Donut({
		        element: 'morris-cpu-donut-chart-' + cloudRequestStat.cloudName,
		        data: [{
		            label: "CPU used",
		            value: cloudRequestStat.cpuAllocated
		        }, {
		            label: "CPU available",
		            value: (cloudRequestStat.totalCPU - cloudRequestStat.cpuAllocated)  
		        }],
		        resize: true
		    });
			
			Morris.Donut({
		        element: 'morris-memory-donut-chart-' + cloudRequestStat.cloudName,
		        data: [{
		            label: "RAM used",
		            value: cloudRequestStat.ramAllocated
		        }, {
		            label: "RAM available",
		            value: cloudRequestStat.totalRAM - cloudRequestStat.ramAllocated
		        }],
		        resize: true
		    });
			
			Morris.Donut({
		        element: 'morris-disk-donut-chart-' + cloudRequestStat.cloudName,
		        data: [{
		            label: "Storage used",
		            value: cloudRequestStat.storageAllocated
		        }, {
		            label: "Storage available",
		            value: cloudRequestStat.totalStorage - cloudRequestStat.storageAllocated
		        }],
		        resize: true
		    });
			
		}
		
	}
	
});

func.populateRequests = (function(data) {

	if (data !== 'undefined') {

		var resourceRequests = data.resourceRequests;

		var html = [];

		for (var i = 0; i < resourceRequests.length; i++) {
			var rraObj = resourceRequests[i];

			html.push('<tr class="gradeA">' + '<td>' + rraObj.name
					+ '</td>' + '<td>' + rraObj.os + '</td>' + '<td>'
					+ rraObj.ram + '</td>' + '<td>' + rraObj.cpu + '</td>'
					+ '<td>' + rraObj.storage + '</td>' + '<td>'
					+ rraObj.userName + '</td>' + '<td>' + rraObj.assignedHost
					+ '</td>' + '<td>' + rraObj.assignedCloud + '</td>'
					+ '<td>' + rraObj.ramHours + '</td>' + '<td>'
					+ rraObj.cpuHours + '</td>' + '<td>' + rraObj.storageHours
					+ '</td>' + '<td>' + rraObj.ramCost + '</td>' + '<td>'
					+ rraObj.cpuCost + '</td>' + '<td>' + rraObj.storageCost
					+ '</td>' + '<td>' + rraObj.resourceType + '</td>' + '<td>'
					+ rraObj.status + '</td>' 
					+ '<td><button type="submit" id = "terminateInstance" class="btn btn-default deleteInstance" value="'+ rraObj.id+'">Terminate</button></td>' 
					+ '</tr>');

		}

		$('#resourceRequests').html(html.join(''));
		
		$('#dataTables-example').DataTable({
			responsive: true,
			 "scrollX": true
		});
	}

});

func.populateUsers = (function(data) {

	if (data !== 'undefined') {

		var html = [];

		for (var i = 0; i < data.length; i++) {
			var user = data[i];

			html.push('<tr>'
					+ '<td> <a href="/html/userRequests.html?userName='
					+ user.userName + '">' + user.userName + '</a></td>'
					+ '<td>' + user.numberOfRequests + '</td>' + '</tr>');
		}
		$('#usersList').html(html.join(''));
		
		$('#dataTables-example').DataTable({
			responsive: true,
			 "scrollX": true
		});
	}

});

func.populateUserRequests = (function(userName, data) {

	$('#userNameId').html(userName);
	if (data !== 'undefined') {

		$('#totalRamHours').html(data.totalRamHours);
		$('#totalCPUHours').html(data.totalCPUHours);
		$('#totalDiskHours').html(data.totalDiskHours);
		$('#totalRamCost').html(data.totalRamCost);
		$('#totalDiskCost').html(data.totalDiskCost);
		$('#totalCPUCost').html(data.totalCPUCost);
		$('#totalCost').html(data.totalCost);

		var userResources = data.userResources;

		var html = [];

		for (var i = 0; i < userResources.length; i++) {
			var rraObj = userResources[i];

			html.push('<tr class="gradeA">' + '<td>' + rraObj.name
					+ '</td>' + '<td>' + rraObj.os + '</td>' + '<td>'
					+ rraObj.ram + '</td>' + '<td>' + rraObj.cpu + '</td>'
					+ '<td>' + rraObj.storage + '</td>' + '<td>'
					+ rraObj.userName + '</td>' + '<td>' + rraObj.assignedHost
					+ '</td>' + '<td>' + rraObj.assignedCloud + '</td>'
					+ '<td>' + rraObj.ramHours + '</td>' + '<td>'
					+ rraObj.cpuHours + '</td>' + '<td>' + rraObj.storageHours
					+ '</td>' + '<td>' + rraObj.ramCost + '</td>' + '<td>'
					+ rraObj.cpuCost + '</td>' + '<td>' + rraObj.storageCost
					+ '</td>' + '<td>' + rraObj.resourceType + '</td>' + '<td>'
					+ rraObj.status + '</td>' 
					+ '<td><button type="submit" id = "terminateInstance" class="btn btn-default deleteInstance" value="'+ rraObj.id +'">Terminate</button></td>' 
					+ '</tr>');

		}

		$('#resourceRequests').html(html.join(''));
		
		$('#dataTables-example').DataTable({
			responsive: true,
			 "scrollX": true
		});
	}

});

func.populateHosts = (function(resourceType, data) {
	
	$('#hosts-title').html(decodeURIComponent(resourceType) + " Hosts");
	
	if(data !== 'undefined') {
		
		var html = [];
		
		for(var i=0;i<data.length;i++){
	        var hostObj = data[i];
	        
	        html.push('<tr>' 
	        		+ '<td>' + hostObj.hostName + '</td>'
	        		+ '<td>' + hostObj.os + '</td>'
	        		+ '<td>' + hostObj.totalCPUs + '</td>'
	        		+ '<td>' + hostObj.totalRAM + '</td>'
	        		+ '<td>' + hostObj.totalStorage + '</td>'
	        		+ '<td>' + hostObj.cpuAllocated + '</td>'
	        		+ '<td>' + hostObj.ramAllocated + '</td>'
	        		+ '<td>' + hostObj.storageAllocated + '</td>'
	        		+ '<td>' + hostObj.cpuUtilization + '</td>'
	        		+ '<td>' + hostObj.memoryUtilization + '</td>'
	        		+ '<td>' + hostObj.diskUtilization + '</td>'
	        		+ '<td>' + hostObj.usageIndex + '</td>'
	        		+ '<td>' + hostObj.cloud + '</td>'
	        		+ '</tr>'
	        );
	        
	    }
		
		$('#listOfHosts').html(html.join(''));
		
		$('#dataTables-example').DataTable({
			responsive: true,
			 "scrollX": true
		});
	}
	
});

func.getUrlVars = (function() {
	var vars = [], hash;
	var hashes = window.location.href.slice(
			window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
});

func.drawPieChart = (function(data) {

	// Flot Pie Chart

	var cloudRequestStats = data.cloudRequestStats;

	var data = [];

	if (cloudRequestStats !== 'undefined') {

		for ( var i in cloudRequestStats) {

			var item = cloudRequestStats[i];

			data.push({
				label : item.cloudName,
				data : item.numberOfRequests,
			});
		}

	}

	var plotObj = $.plot($("#flot-pie-chart"), data, {
		series : {
			pie : {
				show : true
			}
		},
		grid : {
			hoverable : true
		},
		tooltip : true,
		tooltipOpts : {
			content : "%y.0 requests, %s (%p.0%)", // show percentages, rounding to 2 decimal
			// places
			shifts : {
				x : 20,
				y : 0
			},
			defaultTheme : false
		}
	});

});

func.drawBarChart = (function(data) {
	
	var cloudUsageStats = data.cloudUsageStats;
	
	var data = [];
	
	var ticks = [];
	
	if (cloudUsageStats !== 'undefined') {

		for ( var i in cloudUsageStats) {

			var item = cloudUsageStats[i];

			ticks.push([ i, item.cloudName ]);
			
			data.push({ label : item.cloudName, data : [[i, item.usageIndex]]});
			
			data.push({
				data : [ i, item.usageIndex ],
				color : item.cloudName
			});
		}

	}
	
	
	var options = {
		series : {
			stack : 0,
			lines : {
				show : false,
				steps : false
			},
			bars : {
				show : true,
				barWidth : 0.9,
				align : 'center',
			},
		},
		xaxis : {
			ticks : ticks
		},
		yaxis:
	    {
	        min:0.0, max: 1.0 
	    }
	};

	$.plot($("#flot-bar-chart"), data, options);

});

