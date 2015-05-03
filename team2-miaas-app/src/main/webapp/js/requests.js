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
			
			$( document ).on( "click", "button.deleteInstance", function() {
				var id = $(this).val();
				
				if(confirm("Are you sure you want to delete this instance ?") == true) {
					$.ajax({
						url : "http://localhost:8080/api/resource/deallocate/" + id,
						dataType : 'json',
						type : 'delete',
						contentType : 'application/json',
						success : function(data) {
							console.log("success");
							window.location.reload(true);
						},
						error : function(data) {
							console.log("error");
							alert('Get Resources api failed.');
						}
					});
				} else {
					// do nothing
				}
				
				
			});
			

		});