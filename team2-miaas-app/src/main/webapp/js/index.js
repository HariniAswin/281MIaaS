/**
 * 
 */

$(document).ready(
		
		function() {

			console.log("inside main");
			
			// Populate Dashboard
			$.ajax({
				url : "http://localhost:8080/api/cloud/statistics",
				dataType : 'json',
				type : 'get',
				contentType : 'application/json',
				success : function(data) {
					console.log("success");
					if (typeof (data.hasErrors) !== 'undefined') {
						if (typeof (data.data) !== 'undefined') {
							func.populateDashBoard(data.data.cloudsCount,
									data.data.hostsCount, data.data.usersCount,
									data.data.requestsCount);
							func.drawPieChart(data.data);
							func.drawBarChart(data.data);
						} else {
							alert('Get Cloud statistics api failed.');
						}

					} else {
						alert('Get Cloud statistics api failed.');
					}

				},
				error : function(data) {
					console.log("error");
					alert('Get Cloud statistics api failed.');
				}
			});
			
		});

