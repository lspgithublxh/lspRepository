$ns("accordion2.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("accordion2.views.MainView");
$import("accordion2.views.DetailViewController");
$import("accordion2.views.UpdatePeriodTimeViewController");

accordion2.views.MainViewController = function(){
	var me = $extend(mx.views.ViewController);
	 
	 me.getView = function(){
		 if(me.view == null){
			 me.view = new accordion2.views.MainView({
				 controller:me
			 });
		 }
		 return me.view;
	 }
	 
	 me.getDetailView = function(){
		 if(me.detailView == null){
			 //新的控制器
			 //为什么不直接构造视图？detailView
			 me.detailView = new accordion2.views.DetailViewController().getView();//动态构造
//			 //因为要在事件中直接调用mainView的方法，所以这里来对detailView中form绑定事件.
			 me.detailView.getForm().entityContainer.off("saved", me.refreshDataGrid);
			 me.detailView.getForm().entityContainer.on("saved", me.refreshDataGrid);
		 }
		 return me.detailView;
	 }
	 
	 me.getUpdatePeriodTimeView = function(){
		 if(me.updatePeridTimeView == null){
			 //新的控制器
			 //为什么不直接构造视图？detailView
			 me.updatePeridTimeView = new accordion2.views.UpdatePeriodTimeViewController().getView();//动态构造
//			 //因为要在事件中直接调用mainView的方法，所以这里来对detailView中form绑定事件.
//			 me.getDetailView();//没有必要执行
			 //主要是在save之后的刷新问题
//			 me.detailView.getForm().entityContainer.off("saved", me.refresh_updatePeriodTime);
//			 me.detailView.getForm().entityContainer.on("saved", me.refresh_updatePeriodTime);
			 me.updatePeridTimeView.mainController = me;
		 }
		 return me.updatePeridTimeView;
	 }
	 
	 /**
	  * 创建新的数据行
	  */
	 me.btn_create = function(){
//		alert("create1");
		var p_view = me.getDetailView();
		itemShowWindow(p_view, "表单填写");
	 }
//	 
	 /**
	  * 删除选中的数据行
	  */
	 me.btn_remove = function(){
		var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
	     mx.indicate("info", "请至少勾选一条待删除记录。");
             return;
        }
		if (confirm("您确认删除数据吗？"))
		{
		     v_dataGrid.removeItems(v_dataGrid.getCheckedIDs());
		}
	 }
//	 
	 me.btn_edit = function(){
		 var v_dataGrid = me.view.getDataGrid();
	    	if (v_dataGrid.getCheckedIDs().length == 0)
	        {
	             mx.indicate("info", "请勾选一条待编辑记录。");
	             return;
	        }
	        //多选框勾选记录，判断是否选择多条
	    	if(v_dataGrid.getCheckedIDs().length > 1)
	    	{
	    	       mx.indicate("info", "选定的记录条数不能超过一条。");
	    	       return;
	    	}
	    	var detailView = me.getDetailView();
	    	detailView.objID = v_dataGrid.getCheckedIDs()[0];//选中的那个,这样就会加载这个项目的id
//	    	alert(detailView.objID);
	    	itemShowWindow(detailView, "表单编辑");
	 }
	 
	 me.btn_period_time_edit = function(id, text){
		var period_time_view =  me.getUpdatePeriodTimeView();
		period_time_view.period_time_obj = {time:text, id:id};
//		alert(JSON.stringify(period_time_view.period_time_obj));
		period_time_view.load();
		
//		period_time_view.objID = id;
		 periodTimeShowWindow(period_time_view, "更新有效期");
	 }
	 
	 var itemShowWindow = function(p_view, title){
	    var win = me.view.getDetailWindow();
		p_view.getForm().load(p_view.objID);//加载一个项目的数据，从objID里面读取出来，.同时也包括表格本身,从数据库查出来。。表单方法
		win.setView(p_view);
		win.setTitle(title);
		win.showDialog();
	 }
	 
	 var periodTimeShowWindow = function(view, title){
		 var win = me.view.getUpdatePeriodTimeViewWindow();
		 //使用输入框，而手工存储。不调用form
		 win.setView(view);
		 win.setTitle(title);
		 win.showDialog();
	 }
	 
	 me.refreshDataGrid = function(){
		 me.view.getDetailWindow().hide();
	     me.view.getDataGrid().load();//刷新数据，重新取出全部数据
	 }
	 
	 me.refresh_updatePeriodTime = function(){
		 me.view.getUpdatePeriodTimeViewWindow().hide();
		 me.view.getDataGrid().load();//刷新数据，重新取出全部数据
		 //后面还要加第二列标红
		 me.view.service4();
	 }
	 
	 me.endOfClass(arguments);
	 return me;
}