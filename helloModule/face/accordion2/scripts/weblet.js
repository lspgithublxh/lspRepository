$import("accordion2.views.MainViewController");
/**
 * e应该是Weblet对象
 * context即WebContext
 * rootViewPort即继承的ViewPort对象
 */
mx.weblets.WebletManager.register({
	id : "accordion2",
	name: "手风琴单表场景2",
	onload: function(e){ 
		
	},
	onstart: function(e){
		console.log(e);
		var mvc = new accordion2.views.MainViewController();
		e.context.rootViewPort.setViewController(mvc);
	}
});