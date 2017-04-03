$import("accordion22.views.MainViewController");
/**
 * e应该是Weblet对象
 * context即WebContext
 * rootViewPort即继承的ViewPort对象
 */
mx.weblets.WebletManager.register({
	id : "accordion22",
	name: "手风琴单表场景22",
	onload: function(e){ 
		
	},
	onstart: function(e){
		console.log(e);
		var mvc = new accordion22.views.MainViewController();
		e.context.rootViewPort.setViewController(mvc);
	}
});