$(function(){
var myChart = echarts.init(document.getElementById('recommendTimes'));
var xAxisData = [];
var data1 = [];
var data2 = [];
$.ajaxSettings.async = false;
var params = location.href.split('?')[1];
$.get("/recomemndTimes?" + params, function(result){
	for(var i = 0; i < result.length; i++){
		 xAxisData.push('i' + result[i]['itemId']);
		 data1.push(result[i]['value']);
	//	 data2.push(result[i]['value']);
	}
});
var a = 1;
setInterval(function(){
$.get("/recomemndTimes?" + params, function(result){
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
        text: '问题推荐次数'
    },
    legend: {
        data: ['recommendTimes'],
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
        name: 'recommendTimes',
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
})