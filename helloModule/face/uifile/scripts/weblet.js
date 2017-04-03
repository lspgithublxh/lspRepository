
$import("mx.containers.HSplit");				
$import("mx.containers.Container");				
$import("mx.controls.ToolBar");				
$import("mx.datacontrols.DataGrid");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.PageNaviBar");				
$import("mx.windows.Window");				
$import("uifile.views.DeptGridView");				
$import("uifile.views.DeptGridViewController");				
$import("uifile.views.DeptGridViewUserController");				
$import("mx.datacontrols.DataForm");
$import("mx.datacontainers.FormEntityContainer");				
$import("uifile.views.DeptFormView");				
$import("uifile.views.DeptFormViewController");				
$import("uifile.views.DeptFormViewUserController");				

mx.weblets.WebletManager.register(
{
    id: "uifile",
    name: "uifile",
    requires: [],
    onload: function (e) {
		
    }, 
    onstart: function (e) {
    	var mvc = new uifile.views.DeptGridViewUserController();
		e.context.rootViewPort.setViewController(mvc);
    }
});