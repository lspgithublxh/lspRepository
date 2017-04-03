$ns("dept.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


dept.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	alert("init method");
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/dept/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : dept.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "id"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "deptMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "id", caption: "车辆标识s", editorType: "TextEditor"	},
	            {	name: "name", caption: "车辆名称s", editorType: "TextEditor"	},
	            {	name: "periodOfValidity", caption: "有效期s", editorType: "TextEditor"	},
		        {	name: "color", caption: "车辆颜色s", editorType: "TextEditor"	}
				]
			}),
			
			columns:[
	        {	name: "id", caption: "车辆标识" , editorType: "TextEditor"	},
	        {	name: "name", caption: "车辆名称" , editorType: "TextEditor"	},
	        {	name: "periodOfValidity", caption: "有效期" , editorType: "TextEditor"	},
	        {	name: "color", caption: "车辆颜色" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            create: me.controller._btnNew_onclick,//无则会默认的方式，直接插入空的内容--只是在前端，未向后台发数据
            remove: me.controller._btnDelete_onclick
        });
        
	    //重置toolBar按钮项
        _resetToolBarItems();
        me.addControl(_dataGrid);
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);//代表的也是分隔图标
		_dataGrid.toolBar.removeByName("save");
		//插入编辑按钮
		_dataGrid.toolBar.insertItem(2,"-",true);//这个只是分隔图标
		_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = dept.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};