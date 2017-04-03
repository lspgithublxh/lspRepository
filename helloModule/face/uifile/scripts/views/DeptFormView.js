$ns("uifile.views");

uifile.views.DeptFormView=function(){
	var me = $extend(mx.views.View);
	var base = {};
	base.init = me.init;
	me.init = function () {
		me.permissionID = "-1";
		base.init();
		_initControls();
	};
	
	//----声明mx组件变量------
	var _HSplit = null;
	var _HSplitArea0 = null;
	var _DetailToolBar = null;
	var _HSplitArea1 = null;
	var _DataForm = null;
	var _Window = null;
	
	function _initControls(){
		//---调用初始化函数-----
		_init_HSplit();
		_init_HSplitArea0();
		_init_DetailToolBar();
		_init_HSplitArea1();
		_init_DataForm();
	  
		me.on("activate", me.controller._onactivate);
	}
	
	//-----定义初始化函数-----
	function _init_HSplit(){
		_HSplit=new mx.containers.HSplit({id:"HSplit",orientation:"horizontal",height:"100%",width:"100%",padding:"0",borderThick:"0",rows:"25,auto"});
		me.addControl(_HSplit);
	}
	
	function _init_HSplitArea0(){
		_HSplitArea0 = new mx.containers.Container({
			id:"HSplitArea0",
			layout:"mx.layouts.AbsoluteLayout"
		});
		
		_HSplit.addControl(_HSplitArea0, 0);
	}
	
	function _init_DetailToolBar(){		
		_DetailToolBar = new mx.controls.ToolBar({
			id:"DetailToolBar",
			height:"24",
			direction:"horizontal",
			width:"100%",
			itemAlign:"right",
			layoutConfigs:{},
			items:[
				{id:"SaveButton",droppedDown:false,text:"保存",imageKey:"save",height:"20",width:"60",useSymbol:true,onclick:me.controller._saveButton_onclick}
			]
		});
		
		_HSplitArea0.addControl(_DetailToolBar);
	}
	
	function _init_HSplitArea1(){
		_HSplitArea1 = new mx.containers.Container({
			id:"HSplitArea1",
			layout:"mx.layouts.AbsoluteLayout"
		});
		
		_HSplit.addControl(_HSplitArea1, 1);
	}
	
	function _init_DataForm(){
		var formEntityContainer = new mx.datacontainers.FormEntityContainer({
			baseUrl:uifile.mappath("~/rest/dept/"),
			iscID:"-1",
			primaryKey:"id",
			loadMeta:false,
			meta:
			[
				{readOnly:false,nullable:false,visible:true,valueType:"string",name:"id",caption:"id"},
				{readOnly:false,nullable:false,visible:true,valueType:"string",name:"name",caption:"name"},
				{readOnly:false,nullable:false,visible:true,valueType:"int",name:"period_of_validity",caption:"period_of_validity"},
				{readOnly:false,nullable:true,visible:true,valueType:"string",name:"color",caption:"color"}
			]
		});
		
		_DataForm = new mx.datacontrols.DataForm({
			id:"DataForm",
			height:"100%",
			width:"100%",
			maxCols:1,
			layoutConfigs:{},
			fields:
			[
				[
					"[默认]",true,
					{id:"id",lineBreak:false,labelWidth:120,readOnly:false,height:"22",name:"id",caption:"id",editorType:"TextEditor",nullable:false},
					{id:"name",lineBreak:false,labelWidth:120,readOnly:false,height:"22",name:"name",caption:"name",editorType:"TextEditor",nullable:false},
					{id:"period_of_validity",lineBreak:false,labelWidth:120,readOnly:false,height:"22",name:"period_of_validity",valueType:"int",caption:"period_of_validity",editorType:"NumberEditor",nullable:false},
					{id:"color",lineBreak:false,labelWidth:120,height:"22",readOnly:false,name:"color",caption:"color",editorType:"DropDownEditor"}
				]
			],
			entityContainer: formEntityContainer
		});
		
		_HSplitArea1.addControl(_DataForm);
	}
	
	function _init_Window() {		
		if(_Window == null || ((_Window.reusable==false) && _Window.disposed==true)) {
			_Window = uifile.context.windowManager.create({
				title:"详细信息",
				reusable:true
			});
		}
		_Window.on("activate", function() {
			_Window.setView(me);
		});
		_Window.on("close", function(e){
		    $.each(_Window.controls, function(i, o){
				o.$e.detach();
			});
		});
	}
	
	me.getWindow = function() {
		_init_Window();
		return _Window;
	};
	
	
	me.findControlById = function(controlId){
		try{
			return me.findControl("id", controlId);
		} catch(err) {
			mx.indicate("info","未找到对应的mx控件:    "+ err.message);
			return null;
		}	
	};
    return me.endOfClass(arguments);
};