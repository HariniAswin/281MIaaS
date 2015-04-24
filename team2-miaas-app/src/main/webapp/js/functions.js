var func = {};

func.populateDashBoard = (function(cloudsCount, hostsCount, usersCount,
		requestsCount) {

	$('#cloudsCount').html(cloudsCount);
	$('#hostsCount').html(hostsCount);
	$('#usersCount').html(usersCount);
	$('#requestsCount').html(requestsCount);

});

func.populateRequests = (function(data) {

	if (data !== 'undefined') {

		var resourceRequests = data.resourceRequests;

		var html = [];

		for (var i = 0; i < resourceRequests.length; i++) {
			var rraObj = resourceRequests[i];

			html.push('<tr class="odd gradeX">' + '<td>' + rraObj.name
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
					+ rraObj.status + '</td>' + '</tr>');

		}

		$('#resourceRequests').html(html.join(''));
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

			html.push('<tr class="odd gradeX">' + '<td>' + rraObj.name
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
					+ rraObj.status + '</td>' + '</tr>');

		}

		$('#resourceRequests').html(html.join(''));
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
			content : "%p.0%, %s", // show percentages, rounding to 2 decimal
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

func.drawBarChart1 = (function(data) {
	// Flot Bar Chart

	var cloudUsageStats = data.cloudUsageStats;

	var data = [];

	var ticks = [];

	if (cloudUsageStats !== 'undefined') {

		for ( var i in cloudUsageStats) {

			var item = cloudUsageStats[i];

			ticks.push([ i, item.cloudName ]);

			data.push({
				data : [ i, item.usageIndex ],
				color : item.cloudName
			});
		}

	}

	var barOptions = {
		series : {
			bars : {
				show : true,
				barWidth : 43200000
			}
		},
		xaxis : {
			ticks : ticks
		},
		grid : {
			hoverable : true
		},
		legend : {
			show : false
		},
		tooltip : true,
		tooltipOpts : {
			content : "x: %x, y: %y"
		}
	};

	var barData = {
		label : "bar",
		data : data
	};
	$.plot($("#flot-bar-chart"), [ barData ], barOptions);

});