$ns("accordion2.views");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.controls.ToolBar");
/**
 * 这里不用再声明名称空间
 */
accordion2.views.DetailView = function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	
	var _toolBar;
	var _form;
	
	me.init = function(){
		base.init();
		initControls();
	}
	
	var initControls = function(){
		initToolBars();
		initDataForm();
		me.on("activate", function(){alert("activate detailView");});
	}
	
	var initToolBars = function(){
		_toolBar = new mx.controls.ToolBar({
            alias:"deptDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "deptDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", function(){_form.save();});
        me.addControl(_toolBar);
	}
	
	var initDataForm = function(){
        var restUrl = "~/rest/dept/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : accordion2.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "id"
        });
        
        _form = new mx.datacontrols.DataForm({
			alias:"deptDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "id", caption: "车辆标识2", editorType: "TextEditor", visible:false},
	        {	name: "name", caption: "车辆名称2", editorType: "TextEditor"},
	        {	name: "periodOfValidity", caption: "有效期2", editorType: "TextEditor"},
		    {	name: "color", caption: "车辆颜色2", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer
        });
        
        me.addControl(_form);
	}
	
	me.getForm = function(){
		return _form;
	}
	
	me.getToolBar = function(){
		return _toolBar;
	}
	
	me.endOfClass(arguments)
    return me;
}