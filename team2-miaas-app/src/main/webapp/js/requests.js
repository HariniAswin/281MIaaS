$(document).ready(
		function() {

			// Populate Requests
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