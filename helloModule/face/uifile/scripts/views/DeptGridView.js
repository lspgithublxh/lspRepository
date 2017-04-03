$ns("uifile.views");

uifile.views.DeptGridView=function(){
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
	var _MainToolBar = null;
	var _HSplitArea1 = null;
	var _DataGrid = null;
	var _Window = null;
	
	function _initControls(){
		//---调用初始化函数-----
		_init_HSplit();
		_init_HSplitArea0();
		_init_MainToolBar();
		_init_HSplitArea1();
		_init_DataGrid();
	  
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
	
	function _init_MainToolBar(){		
		_MainToolBar = new mx.controls.ToolBar({
			id:"MainToolBar",
			height:"24",
			direction:"horizontal",
			width:"100%",
			itemAlign:"right",
			layoutConfigs:{},
			items:[
				{id:"NewButton",droppedDown:false,text:"新建",imageKey:"add",height:"20",width:"60",useSymbol:true,onclick:me.controller._NewButton_onclick},
				{id:"DelButton",droppedDown:false,text:"删除",imageKey:"delete",height:"20",width:"60",useSymbol:true,onclick:me.controller._DelButton_onclick},
				{id:"EditButton",droppedDown:false,text:"编辑",imageKey:"edit",height:"20",width:"60",useSymbol:true,onclick:me.controller._EditButton_onclick},
				{id:"PrintButton",droppedDown:false,text:"打印",imageKey:"print",height:"20",width:"60",useSymbol:true,onclick:me.controller._PrintButton_onclick}
			]
		});
		
		_HSplitArea0.addControl(_MainToolBar);
	}
	
	function _init_HSplitArea1(){
		_HSplitArea1 = new mx.containers.Container({
			id:"HSplitArea1",
			layout:"mx.layouts.AbsoluteLayout"
		});
		
		_HSplit.addControl(_HSplitArea1, 1);
	}
	
	function _init_DataGrid(){
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl:uifile.mappath("~/rest/dept/"),
			iscID:"-1",
			primaryKey:"id",
			loadMeta:false
		});
		
		_DataGrid = new mx.datacontrols.DataGrid({
			columns:[
				{id:"id",dataType:"string",readOnly:false,name:"id",width:"120",caption:"id",editorType:"TextEditor",nullable:false},
				{id:"name",dataType:"string",readOnly:false,name:"name",width:"120",caption:"name",editorType:"TextEditor",nullable:false},
				{id:"period_of_validity",dataType:"number",readOnly:false,name:"period_of_validity",dataAlign:"right",width:"120",caption:"period_of_validity",editorType:"NumberEditor",nullable:false,editorOptions:{valueType:"int"}},
				{id:"color",dataType:"string",readOnly:false,name:"color",width:"120",caption:"color",editorType:"DropDownEditor"}
			],
			
			id:"DataGrid",
			height:"100%",
			displayCheckBox:true,
			width:"100%",
			pageSize:20,
			allowEditing:false,
			allowPaging:true,
			layoutConfigs:{},
			pageIndex:1,
			pageNaviBar:new mx.datacontrols.PageNaviBar({
				id:"PageNaviBar",
				height:"24",
				pageSize:20,
				pageIndex:1
			}),
			entityContainer: gridEntityContainer
		});
		
		_DataGrid.on("itemdoubleclick", me.controller._DataGrid_onitemdoubleclick);
		_HSplitArea1.addControl(_DataGrid);
	}
	
	function _init_Window() {		
		if(_Window == null || ((_Window.reusable==false) && _Window.disposed==true)) {
			_Window = uifile.context.windowManager.create({
				entry:true
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