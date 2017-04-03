$ns("accordion.views");

$import("accordion.views.MainView");

accordion.views.MainViewController = function(){
	 var me = $extend(MXComponent);
	 
	 me.getView = function(){
		 if(me.view = null){
			 me.view = new accordion.views.MainView({
				 controller:me
			 });
		 }
		 return me.view;
	 }
	 
	 me.endOfClass(arguments);
	 return me;
}