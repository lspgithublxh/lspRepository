$ns("accordion.views");

$import("mx.containers.Accordion");
$import("mx.controls.Button");

/**
 * 本类是一个Accordion的具体类，即是一个它的具体子类，并且加了额外的属性和方法..这也是MainView.js的真正含义。它是一个容器....也可以不是容器，而是一个容器的视图对象
 * 所以viewController是容器控制器
 * 困难：不能直接使用accordion容器，只能使用View
 */
accordion.views.MainView = function(){
//	var me = $extend("mx.containers.Accordion");
	var me = new mx.containers.Accordion({
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
	me.panels["panel1"].addControl(button1);
	me.panels["panel2"].addControl(button2);
	me.panels["panel3"].addControl(button3);
	me.endOfClass(arguments);//arguments
	return me;
}