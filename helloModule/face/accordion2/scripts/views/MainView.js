$ns("accordion2.views");

$import("mx.containers.Accordion");
$import("mx.controls.Button");
$import("mx.containers.TabControl");
$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontainers.TreeEntityContainer");
$import("mx.datacontrols.DataTree");
/**
 * 本类是一个Accordion的具体类，即是一个它的具体子类，并且加了额外的属性和方法..这也是MainView.js的真正含义。它是一个容器....也可以不是容器，而是一个容器的视图对象
 * 所以viewController是容器控制器
 * 困难：不能直接使用accordion容器，只能使用View
 * view和viewController实际上是相互绑定的
 */
accordion2.views.MainView = function(){
//	var me = $extend("mx.containers.Accordion");
	var me = $extend(mx.views.View);
	
	var _dataGrid = {};
	var _TabControl = null;
	var base = {};
	var _detailWin;
	var _updatePeriodTimeViewWin;
	base.init = me.init;
	//定义标签名函数
	me.myTabControl = {};
	
	
	
	me.init = function(){
		base.init();
//		me.on("activate", me.controller._onactivate);
//		me.on("deactivate", me.controller._ondeactivate);
		var accordion = new mx.containers.Accordion({
			panels:[
			 {title:"面板1", name: "panel1"},
			 {title:"面板2", name: "panel2"},
			 {title:"面板3", name: "panel3"},
			 {title:"面板4", name: "panel4"},
			 {title:"超市管理", name: "panel5"}
			]
		});
		var button1 = new mx.controls.Button({text:"按钮1"});
		var button2 = new mx.controls.Button({text:"按钮2"});
		var button3 = new mx.controls.Button({text:"按钮3"});
		accordion.panels["panel1"].addControl(button1);
		accordion.panels["panel2"].addControl(button2);
		accordion.panels["panel3"].addControl(button3);
		var data_fx = createTree();//data_form.load();
		accordion.panels["panel5"].addControl(data_fx);
		button1.on("click", function(e){
			//console.log(e);
			//alert(JSON.stringify(e.target.id));
//			$("welcome1")
//			console.log(_TabControl.pages[0]);
//			alert(JSON.stringify(_TabControl.pages[0]));
			//
			//只能遍历找哪个有selected,然后删除。然后给新标签加类。
			//1.方法1
//			console.log(e.target.$e);
//			$("#welcome1").removeClass("selected");
//			$("#welcome2").addClass("selected");
//			$("#welcome3").trigger("click");//可以行
			//2.方法2
		//	$(_TabControl.$e).children(".tab-head").children(".selected").removeClass("selected");
		//	$(_TabControl.$e).children(".tab-body").children(".container").html("<h1>你好</h1>");
//			alert($(_TabControl.$e).children(".tab-body").children(".container").attr("class"));
	//		$("#welcome2").addClass("selected");//tab-body
//			console.log(_TabControl.$e);
//			console.log($(_TabControl.$e).children(".tab-head").children(".selected"));//tab-head
			_TabControl.selectPage("welcome2",true);
//			_TabControl.$body.html("<div style='color:red;'>welcom2</div>");
//			_TabControl.$body.html(_dataGrid.$e);
			service4();
		});
		button2.on("click", function(){
			_TabControl.selectPage("welcome3",true);
			service2();
			
		});
		button3.on("click", function(){
			_TabControl.selectPage("welcome4",true);
			service3();
		});
		
		var xx = 1;
		var service1 = function(){
			if(xx++ == 1)return;
			_TabControl.$body.html("<div style='color:red;'>welcom2</div>");
		}
		
		var service5 = function(){
			alert("标签");
			_TabControl.$body.html("<div><hr/> </div>");
		}
		
		
		
		var service2 = function(){
			_TabControl.$body.html("<div style='color:red;'>welcom3</div>");
			var button6 = new mx.controls.Button({text:"welcom3 yes"});
			button6.$e.children("input").on("click", function(){alert("click button6");});
			_TabControl.$body.html(button6.$e);
		}
		
		var service3 = function(){
			_TabControl.$body.html("<div style='color:red;'>welcom4</div>");
			//$container也是一个$e对象
			var button6 = new mx.controls.Button({text:"yes"});
			button6.$e.children("input").on("click", function(){alert("click button6");});
			_TabControl.$body.html(button6.$e);
			//jquery对象如何转换为一个mx框架中的对象？
		}
		
		var _toolBar = new mx.controls.ToolBar(//实例化工具栏控件
				{
				  items :
				    [
				    {
				        name : "menuGroup",//菜单项的唯一标识
				        toolTip : "菜单分组",
				        imageKey : "new",
				        text : "菜单分组",
				        items :[
				                {
				                	name : "childMenu1", 
				                	toolTip : "子菜单1",
				                	imageKey : "new",
				                	text : "子菜单1",
				                	items:[
											{
												name : "childMenu11", 
												toolTip : "子菜单11",
												imageKey : "new",
												text : "子菜单11"
											}
				                	      ]
				                }
				               ]
				        
				    },
				    "-",//横线表示显示分隔线
				    {
				    	name : "menu1",toolTip : "主菜单1",imageKey : "new",text : "主菜单1"
				    },
				    "-",
				    {
				    	name : "menu2",toolTip : "主菜单2",imageKey : "new",text : "主菜单2"
				    }]
				});
		_toolBar.on("itemclick", function(toolStripItem){alert("hahah");
			console.log(toolStripItem);
		});//
		_toolBar.on("dropdown", function(e){
//			alert("eee");
		});
//		_toolBar.on("click", function(e){
//			alert("click");
//		});
		accordion.panels["panel4"].addControl(_toolBar);
		//实例化布局控件
		var _vSplit = new mx.containers.VSplit({
		    cols:"20%, 80%",//设置分割比例
		    resizable:true
		});
		//将布局添加到当前页面
		_vSplit.addControl(accordion, 0);
		me.addControl(_vSplit);
		
		//实例化标签页控件
		_TabControl = new mx.containers.TabControl({
		    pages:[
		           { text: "欢迎1", name: "welcome1" },
		           { text: "欢迎2", name: "welcome2" },
		           { text: "欢迎3", name: "welcome3" },
		           { text: "欢迎4", name: "welcome4" }
		       ],
		       onselectionchanged:function(e){//e同样是一个一般的对象
		    	   
		    	  switch(e.page.name){
		    	  case "welcome1":
		    		  //无法加载进去,因为每次都会默认执行一遍！
		    		  service1();
//		    		  _TabControl.$body.html("<div style='color:red;'>welcom2</div>");
		    		  break;
		    	  case "welcome2":
		    		   service4();
		    		   break;
		    	  case "welcome3":
		    		  service2();
		    		  break;
		    	  case "welcome4":
		    		  service3();
		    		  break;
		    	  case "page1":
		    		  service5();
		    		  break;
		          default :
		        	  tablControlFunSelec(e.page.name);
		    	      break;
		    	  }
		    	   },
		    	   $body:$("<div style='color:red;'>你好</div>")
		   });
		//动态添加额外的标签
		_TabControl.appendPage("page1","标签1",true);//name, text, closable
		//选中页
		_TabControl.selectPage("welcome1",true);//无返回值
		service1();
//		_TabControl.$body.html("<div style='color:red;'>welcom2</div>");
		//var button6 = new mx.controls.Button({text:"按钮6"});
		//也可以获取当前页selection
		//alert(_TabControl.selection.text);
	//	_TabControl.$body = $("<div style='color:red;'>你好</div>");
	//	console.log(_TabControl.$body.html("<div style='color:red;'>你好</div>"));
		
//		_TabControl.$body.html(_dataGrid.$e);
//		console.log(_dataGrid.$e);
//		console.log("console dataGrid.$e");
		//下面的方法会和其他部分重合，不是内容一部分。
		//var button6 = new mx.controls.Button({text:"按钮6"});
//		_TabControl.addControl(button6);
		//添加到布局中
		_vSplit.addControl(_TabControl,1);
//		initDataGrid();
//		_TabControl.addControl(_dataGrid);//必须放在initDataGrid()方法之后
//		_TabControl.$body.html(_dataGrid.$e);
		//绑定事件
		me.on("activate", function(){
//			_dataGrid.load();//
		});
	}
	
	var service4 = function(){
		//两个都必须同时放在这里，否则切换的时候不会再绑定事件
		_TabControl.$body.html("");//以免延迟的影响
		setTimeout(function(){
			initDataGrid();
			_dataGrid.load();//
			_TabControl.$body.html(_dataGrid.$e);//首次尚未创建完成，所以不能直接调用_TabControl.自己调用自己了！
			//加载之后可以找到？
			//下面的动作证明在这里是获取不到节点的，除非用了异步方法
			//
//			var nodes = $(".bodyTable tbody tr:gt(0)");// table.bodyTable tbody tr:gt(0)

//			 for(var i in nodes.children){
//				 console.log(nodes.children[i]);
//			 }
		}, 1);
		
	}
	me.service4 = service4;
	
	var initDataGrid = function(){
		var restUrl = "~/rest/dept/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : accordion2.mappath(restUrl),
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
            create: me.controller.btn_create,//无则会默认的方式，直接插入空的内容--只是在前端，未向后台发数据
            remove: me.controller.btn_remove,//这两个函数在点击按钮的时候就会先后执行。先create后remove。不知道为什么
            onload:loadDataGrid
        });
        //窗口初始化
        _initDetailWindow();
        _initUpdatePeriodTimeViewWindow();
        //改造datagrid默认产生的toolbar
        upateToolBarItem();
        //数据表格
        
	}
	
	var loadDataGrid = function(){
		setTimeout(function(){
//			var nodes = document.getElementsByClassName("bodyTable");
//			console.log(nodes.length);
//			console.log(nodes[0]);
//			console.log(nodes[0].children[1]);
//			console.log(nodes[0].children[1].children[1]);
			var nn = $(".bodyTable tbody tr");//.bodyTable tbody tr:gt(0)
			for(var i = 0; i < nn.length; i++){//nn[i]是一般的js对象
//				console.log($(nn[i]).children("#periodOfValidity"));
				//动态增加控件的方式也可以
				$(nn[i]).children("#periodOfValidity").css({"background-color":"red"});
				var text = $(nn[i]).children("#periodOfValidity").text();
				var html = "<a href='javascript:;' onclick='myFunction.linkFunction("+$(nn[i]).children("#id").text() + "," + text + ")'>" + text + "</a>";
				$(nn[i]).children("#periodOfValidity").html(html);
//				console.log();
			}
		}, 10);
	}
	
	/**
	 * 标签名函数调用
	 */
	var tablControlFunSelec = function(funName){
		me.myTabControl.funName();
	}
	/**
	 * 标签名函数定义
	 */
	var tabControlFunDefin = function(funName){
		me.myTabControl.funName = function(){
			//必须根据标签名去查后台数据，来找到对应的那个表，来找到数据。。而且需要即使定义表的结构，来动态生成新的表
			//目前就只先找默认的哪张表
			//这就是一个统一的方法
			service4();
		}
	}
	
	var upateToolBarItem = function(){
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		_dataGrid.toolBar.insertItem(2,"-", true);
		_dataGrid.toolBar.insertItem(3,{
			name:"edit",
			text:mx.msg("EDIT"),
			toolTip: mx.msg("EDIT"),
			imageKey : "edit",
			onclick: me.controller.btn_edit
		}, true);
	}
	 /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = accordion2.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }
    
    function _initUpdatePeriodTimeViewWindow(){
    	_updatePeriodTimeViewWin = accordion2.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:280,
			title:"更新有效期"
		});
    }
    
    
    var createTree = function(){
    	var data_tree_c = new mx.datacontainers.TreeEntityContainer({
    		type: "local",
    		data:[
    		      {
    		    	  id:"food",
    		    	  text:"食品类",
    		    	  hasChildren:true,
    		    	  childNodes:[
    		    	          {
    		    	        	  id:"cokocolo",
    		    	        	  text:"可口可乐"
    		    	          } ,
    		    	          {
    		    	        	  id:"baishi",
    		    	        	  text:"百事可乐"
    		    	          } 
    		    	  ]
    		    	  
    		      },
    		      {
    		    	  id:"electronics",
    		    	  text:"电子产品类",
    		    	  hasChildren:true,
    		    	  childNodes:[
    		    	          {
    		    	        	  id:"iphone7s",
    		    	        	  text:"iphone7s"
    		    	          } ,
    		    	          {
    		    	        	  id:"SΛMSUNG_GalaxyS8",
    		    	        	  text:"三星盖乐世S8"
    		    	          } 
    		    	  ]
    		    	  
    		      },
    		      {
    		    	  id:"home",
    		    	  text:"家居类",
    		    	  hasChildren:true,
    		    	  childNodes:[
    		    	          {
    		    	        	  id:"HaierFridge",
    		    	        	  text:"海尔冰箱"
    		    	          } ,
    		    	          {
    		    	        	  id:"Midea_air_conditioner",
    		    	        	  text:"美的空调"
    		    	          } 
    		    	  ]
    		    	  
    		      }
    		      ]
    	});
    	var data_form = new mx.datacontrols.DataTree({
    		entityContainer:data_tree_c,
    		displayCheckBox: true,
    		onselectionchanged: treeSelected
    	});
    	data_form.load();
    	return data_form;
    }
    var tree_help = 1;
    var hashMap = new mx.types.HashMap();
    
	var treeSelected = function(e){
		if(tree_help++ != 1 && e.selection.hasChildren == false){
//			alert(_TabControl.findControl("name",e.selection.id));
//			if(hashMap.hasItem(e.selection.id)){
//				_TabControl.selectPage(e.selection.id,true);//会和主动点击tab一样激活一个事件叫做：onselectionchanged
//			}
			if(!hashMap.hasItem(e.selection.id)){
				hashMap.setItem(e.selection.id,e.selection.text);
				//appendPage之后也不会自动切换到这个页面
				_TabControl.appendPage(e.selection.id,e.selection.text,true);//name, text, closable
				//绑定事件
				tabControlFunDefin(e.selection.id);
			}
			_TabControl.selectPage(e.selection.id,true);
			_TabControl.$body.html("<div style='color:green;'>" + e.selection.text + "</div>");
		}
		
	}

	/**
	 * 加载弹出的价格修改页面
	 */
	myFunction.linkFunction = function(id, text){
		//~/rest/dept/
		//开始构建窗口、弹出窗口、绑定"保存"事件..这些事交给controller管理，controller调用子updateViewController来间接调用updatePeriodTimeView来实现periodView的构建！！
		me.controller.btn_period_time_edit(id, text);
		
	}
	
	
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    me.getUpdatePeriodTimeViewWindow = function(){
    	return _updatePeriodTimeViewWin;
    }
//    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)//arguments
	return me;
}
var myFunction = {};