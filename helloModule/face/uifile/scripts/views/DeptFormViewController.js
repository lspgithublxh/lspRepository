$ns("uifile.views");

//uifile、DeptFormView在编译时将自动替换为实际值，设计过程中不要随意修改

uifile.views.DeptFormViewController = function(){
	var me = $extend(mx.views.ViewController);
	me.typeName="uifile.views.DeptFormViewController";
	me.getController=function(key){
		return me.getLinkViewController("uifile.views."+key+"Controller");
	};
	me.getView = function(){
		if (me.view == null){
			me.view = new uifile.views.DeptFormView({ controller: me });
		}
		return me.view;
	};
	
	
	var detailForm = null;
	me._onactivate = function(e){
		detailForm = me.getView().findControlById("DataForm");
		var mainViewController = me.getController("DeptGridView");
		var dataGrid = mainViewController.getView().findControlById("DataGrid");
		detailForm.entityContainer.on("saved", function(e){
			me.getView().getWindow().hide();
			dataGrid.load();
		});
		
	};

	//事件处理函数写在这里
	me._saveButton_onclick = function() {
		detailForm.save();
	};
	
	
	
	return me.endOfClass(arguments);
};