/**
 * 
 */

$(document).ready(
		function() {
			
			var resourceType = func.getUrlVars()["resourceType"];
			
			// Populate Requests
			$.ajax({
				url : "http://localhost:8080/api/cloud/hosts?resourceType=" + resourceType,
				dataType : 'json',
				type : 'get',
				contentType : 'application/json',
				success : function(data) {
					console.log("success");
					if (typeof (data.hasErrors) !== 'undefined') {
						if (typeof (data.data) !== 'undefined') {
							func.populateHosts(resourceType, data.data);
						} else {
							alert('Get Hosts api failed.');
						}

					} else {
						alert('Get Hosts api failed.');
					}

				},
				error : function(data) {
					console.log("error");
					alert('Get Hosts api failed.');
				}
			});

		});