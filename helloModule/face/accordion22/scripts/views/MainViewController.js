$ns("accordion22.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("accordion22.views.MainView");

accordion22.views.MainViewController = function(){
    var me = $extend(mx.views.ViewController);
    var base = {};
    me.getView = function()
    {
    	alert("get view");
        if (me.view == null)
        {alert("get view2");
            me.view = new accordion22.views.MainView({ controller: me });
        }
        return me.view;
    };

    me.endOfClass(arguments);
    return me;
}