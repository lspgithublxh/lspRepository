$import("accordion.views.MainViewController");
/**
 * e应该是Weblet对象
 * context即WebContext
 * rootViewPort即继承的ViewPort对象
 */
mx.weblets.WebletManager.register({
	id : "accordion",
	name: "手风琴单表场景",
	onload: function(e){ 
		
	},
	onstart: function(e){
		var mvc = new accordion.views.MainViewController();
		e.context.rootViewPort.setViewController(mvc);
	}
});