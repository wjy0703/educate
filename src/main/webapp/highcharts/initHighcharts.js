function initHighcharts(type, render, title, subtitle, json){
	if(type == "pie"){
		initPieHighcharts(render, title, subtitle, json);
	}else if(type == "column"){
		initColumnHighcharts(render, title, subtitle, json);
	}else{
		initColumnHighcharts(render, title, subtitle, json);
	}
}
function initColumnHighcharts(render, title, subtitle, json){
	var chart;
	var options = {
	     chart: {
		      renderTo: render,//图表的页面显示容器
		      defaultSeriesType: 'column',//图表的显示类型（line,spline,scatter,splinearea bar,pie,area,column）
		      marginRight: 125,//上下左右空隙
		      marginBottom: 25 //上下左右空隙
	     },
	     title: {
		      text: title,//主标题
		      x: -20 //center
	     },
	     subtitle: {
		      text: subtitle,//副标题
		      x: -20 //center
	     },
	     xAxis: {   //横坐标
	     },
	     yAxis: {
	    	 allowDecimals: false,
	    	 min: 0,
		       title: {
		       		text: '金额' //纵坐标
	     	   }
		      
   		 },
   		tooltip: {
            shared: true,
            crosshairs: true
        },
		  plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
		     legend: {
			      layout: 'vertical',
			      align: 'right',
			      verticalAlign: 'top',
			      x: -10,
			      y: 100,
			      borderWidth: 0
		     },
		     series: []
   	};
	
	 var data = eval("["+json+"]");
	 options.xAxis=data[data.length-1];
    for(var i=0;i<data.length-1;i++){
     	 options.series.push(data[i]);
    }
    chart = new Highcharts.Chart(options);
}
function initPieHighcharts(render, title, subtitle, json){
	var chart;
	var options = {
			chart: {
			      renderTo: render,//图表的页面显示容器
			      defaultSeriesType: 'pie',//图表的显示类型（line,spline,scatter,splinearea bar,pie,area,column）
			      marginRight: 125,//上下左右空隙
			      marginBottom: 25 //上下左右空隙
		     },
		     title: {
			      text: title,//主标题
			      x: -20 //center
		     },
		     subtitle: {
			      text: subtitle,//副标题
			      x: -20 //center
		     },
		     xAxis: {   //横坐标
		     },
		     yAxis: {
			       title: {
			       		text: '金额' //纵坐标
		     	   },
			      plotLines: [{  //标线属性
				       value: 0,
				       width: 1,
				       color: 'red'
			      }]
  		 },
  		 tooltip: {
               formatter: function() {
                   return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
               }
           },
           plotOptions: {
               pie: {
                   allowPointSelect: true,
                   cursor: 'pointer',
                   dataLabels: {
                       enabled: false
                   },
                   showInLegend: true
               }
           },
		     legend: {
			      layout: 'vertical',
			      align: 'right',
			      verticalAlign: 'top',
			      x: -10,
			      y: 100,
			      borderWidth: 0
		     },
		     series: []
   	};
	
	 var data = eval("["+json+"]");
	 options.xAxis=data[data.length-1];
    for(var i=0;i<data.length-1;i++){
     	 options.series.push(data[i]);
    }
    chart = new Highcharts.Chart(options);
}
function initLineHighcharts(render, title, subtitle, json){
	var chart;
	var options = {
		     chart: {
			      renderTo: render,//图表的页面显示容器
			      defaultSeriesType: 'spline',//图表的显示类型（line,spline,scatter,splinearea bar,pie,area,column）
			      marginRight: 125,//上下左右空隙
			      marginBottom: 25 //上下左右空隙
		     },
		     title: {
			      text: title,//主标题
			      x: -20 //center
		     },
		     subtitle: {
			      text: subtitle,//副标题
			      x: -20 //center
		     },
		     xAxis: {   //横坐标
		     },
		     yAxis: {
			       title: {
			       		text: '金额' //纵坐标
		     	   },
			      plotLines: [{  //标线属性
				       value: 0,
				       width: 1,
				       color: 'red'
			      }]
   		 },
		     tooltip: {
               shared: true,
               crosshairs: true
           },

			  plotOptions: {
               line: {
                   dataLabels: {
                       enabled: true
                   },
                   enableMouseTracking: false
               }
           },
		     legend: {
			      layout: 'vertical',
			      align: 'right',
			      verticalAlign: 'top',
			      x: -10,
			      y: 100,
			      borderWidth: 0
		     },
		     series: []
   	};
	
	 var data = eval("["+json+"]");
	 options.xAxis=data[data.length-1];
    for(var i=0;i<data.length-1;i++){
     	 options.series.push(data[i]);
    }
    chart = new Highcharts.Chart(options);
}