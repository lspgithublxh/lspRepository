$ns("accordion2.views");
$import("accordion2.views.UpdatePeriodTimeView");

accordion2.views.UpdatePeriodTimeViewController = function(){
	var me = $extend(mx.views.ViewController);
	
	me.getView = function(){
		if(me.view == null){
			me.view = new accordion2.views.UpdatePeriodTimeView({controller:me, alias:"updatePeriodTimeViewController"});
		}
		return me.view;
	}

	return me.endOfClass(arguments);
}