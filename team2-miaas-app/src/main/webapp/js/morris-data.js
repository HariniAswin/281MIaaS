$(function() {



    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "vCPUs running",
            value: 12
        }, {
            label: "vCPU remaining",
            value: 38
        }],
        resize: true
    });
   
 

});
