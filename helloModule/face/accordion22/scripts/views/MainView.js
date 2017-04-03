$ns("accordion22.views");

$import("mx.containers.Accordion");
$import("mx.controls.Button");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");

/**
 * 本类是一个Accordion的具体类，即是一个它的具体子类，并且加了额外的属性和方法..这也是MainView.js的真正含义。它是一个容器....也可以不是容器，而是一个容器的视图对象
 * 所以viewController是容器控制器
 * 困难：不能直接使用accordion容器，只能使用View
 */
accordion22.views.MainView = function(){
	 var me = $extend(mx.views.View);
	    var base = {};
	    base.init = me.init;
	    me.init = function()
	    {
	        base.init();
	        _initControls();
	    };
	    
	    function _initControls()
	    {
	    	alert("init method");
		    _initDataGrid();
	    }
	    
	    function _initDataGrid()
	    {
	    	var accordion = new mx.containers.Accordion({
	    		panels:[
	    		 {title:"面板1", name: "panel1"},
	    		 {title:"面板2", name: "panel2"},
	    		 {title:"面板3", name: "panel3"},
	    		 {title:"面板4", name: "panel4"}
	    		]
	    	});
	    	var button1 = new mx.controls.Button({text:"按钮1"});
	    	var button2 = new mx.controls.Button({text:"按钮2"});
	    	var button3 = new mx.controls.Button({text:"按钮3"});
	    	accordion.panels["panel1"].addControl(button1);
	    	accordion.panels["panel2"].addControl(button2);
	    	accordion.panels["panel3"].addControl(button3);
	    	me.addControl(accordion);
	    	
	    }
	    
		me.endOfClass(arguments)
	    return me;
}