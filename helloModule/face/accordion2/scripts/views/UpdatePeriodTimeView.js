$ns("accordion2.views");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.controls.ToolBar");
$import("mx.rpc.RESTClient");
$import("mx.editors.TextEditor");
$import("mx.editors.NumberEditor");
/**
 * 这里不用再声明名称空间
 */
accordion2.views.UpdatePeriodTimeView = function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	
	var _toolBar;
	var _form;
	
	me.numberEditor = null;
	
	me.period_time_obj = null;//{time:null, id:null};
	
	//独特需要
	me.mainController = null;
	
	me.init = function(){
		base.init();
		initControls();
	}
	
	var initControls = function(){
		initToolBars();
//		initDataForm();
//		me.addControl("请输入有效期");//不可行
		initPeriodTime();
		me.on("activate", function(){});
	}
	
	var initToolBars = function(){
		_toolBar = new mx.controls.ToolBar({
            alias:"deptUpdatePeriodTimeToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "deptUpdatePeriodTimeBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", function(){//专门自己写函数来存储.需要知道id就可以了。所以需要在mainViewController里面实现
        	//RPC进行更新数据库,其中知道id, 新的text。则看后来调用了！！
//        	alert("get :" + me.period_time_obj.id + "---" + me.textEditor.getValue());
        	var client = new mx.rpc.RESTClient();
        	client.get(
        				accordion2.mappath("~/rest/dept/update_period_time"),
        				{id:me.period_time_obj.id,time:parseInt(me.numberEditor.getValue())},
        				function(flag){
//			        			alert(JSON.stringify(flag));	
			});
        	//刷新一下数据
        	me.mainController.refresh_updatePeriodTime();
        });
        me.addControl(_toolBar);
       }

    var initPeriodTime = function(){
    	var label = new mx.controls.Label({
    	    text: "请输入有效期:",
    	    textAlign: "center",
    	    verticalAlign: "middle",
    	    onclick: function(e)
    	    {
    		       alert(e.target.text);
    	    }
    	});
    	me.addControl(label);
    	//
//    	me.textEditor = new mx.editors.TextEditor(
//		{
//		    "width" : "300px",
//		    "hint" : "test" //指定显示的提示文字。
//		});
////    	me.addControl();//文本如何加
//    	me.addControl(me.textEditor);
    	me.numberEditor = new mx.editors.NumberEditor({
    	    min: 0,
    	    increment: 1,
    	    onchanged: function(){}
    	});
    	me.addControl(me.numberEditor);
    }
	
    /**
     * 加载数据
     */
	me.load = function(){
//		alert("load period_id:" + me.period_time_obj.id);
		me.numberEditor.setValue(me.period_time_obj.time);
	}
	
	var initDataForm = function(){
        var restUrl = "~/rest/dept/";
        /* 初始化 EntityContainer */        
//        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
//            baseUrl : accordion2.mappath(restUrl),
//            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
//            primaryKey: "id"
//        });
//        
//        _form = new mx.datacontrols.DataForm({
//			alias:"deptUpdatePeriodTimeDataForm",
//			displayPrimaryKey: false,
//			fields: [
//	        {	name: "periodOfValidity", caption: "有效期2", editorType: "TextEditor"}
//		    ],
//            entityContainer: formEntityContainer
//        });
//        
//        me.addControl(_form);
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