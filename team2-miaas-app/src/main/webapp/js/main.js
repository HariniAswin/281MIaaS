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
			
			// Populate Dashboard
			$.ajax({
				url : "http://localhost:8080/api/resource/list",
				dataType : 'json',
				type : 'get',
				contentType : 'application/json',
				success : function(data) {
					console.log("success");
					if (typeof (data.hasErrors) !== 'undefined') {
						if (typeof (data.data) !== 'undefined') {
							func.populateRequests(data.data);
						} else {
							alert('Get Resources api failed.');
						}

					} else {
						alert('Get Resources api failed.');
					}

				},
				error : function(data) {
					console.log("error");
					alert('Get Resources api failed.');
				}
			});

		});