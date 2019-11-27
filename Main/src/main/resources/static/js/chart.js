var myChart = echarts.init(document.getElementById('main'));
var xAxisData = [];
var data1 = [];
var data2 = [];
$.ajaxSettings.async = false;
$.get("/statis?examTypeId=2", function(result){
	for(var i = 0; i < result.length; i++){
		 xAxisData.push('i' + result[i]['itemId']);
		 data1.push(result[i]['value']);
	//	 data2.push(result[i]['value']);
	}
});
var a = 1;
setInterval(function(){
$.get("/statis?examTypeId=2", function(result){
    data1 = [];
	for(var i = 0; i < result.length; i++){
		 xAxisData.push('i' + result[i]['itemId']);
		 data1.push(result[i]['value']);
	}
	
	a = a+1;
	var option = {
		series: [{
        name: 'priority',
        type: 'bar',
        data: data1,
        animationDelay: function (idx) {
            return idx * 10;
        }
    }]
	};
	myChart.setOption(option);
});
	
}, 20000);
option = {
    title: {
        text: '用户优先级'
    },
    legend: {
        data: ['priority'],
        align: 'left'
    },
    toolbox: {
        // y: 'bottom',
        feature: {
            magicType: {
                type: ['stack', 'tiled']
            },
            dataView: {},
            saveAsImage: {
                pixelRatio: 2
            }
        }
    },
    tooltip: {},
    xAxis: {
        data: xAxisData,
        silent: false,
        splitLine: {
            show: false
        }
    },
    yAxis: {
    },
    series: [{
        name: 'priority',
        type: 'bar',
        data: data1,
        animationDelay: function (idx) {
            return idx * 10;
        }
    }],
    animationEasing: 'elasticOut',
    animationDelayUpdate: function (idx) {
        return idx * 5;
    }
};

myChart.setOption(option);