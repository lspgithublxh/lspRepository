$ns("accordion2.views");
$import("accordion2.views.DetailView");

accordion2.views.DetailViewController = function(){
	var me = $extend(mx.views.ViewController);
	
	me.getView = function(){
		if(me.view == null){
			me.view = new accordion2.views.DetailView({controller:me, alias:"detailViewController"});
		}
		return me.view;
	}

	return me.endOfClass(arguments);
}