/**
 * 
 */

$(document).ready(
		function() {
			
			var userName = func.getUrlVars()["userName"];
			
			console.log("UserName : " + userName);

			// Populate Requests
			$.ajax({
				url : "http://localhost:8080/api/resource//user/" + userName,
				dataType : 'json',
				type : 'get',
				contentType : 'application/json',
				success : function(data) {
					console.log("success");
					if (typeof (data.hasErrors) !== 'undefined') {
						if (typeof (data.data) !== 'undefined') {
							func.populateUserRequests(userName, data.data);
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