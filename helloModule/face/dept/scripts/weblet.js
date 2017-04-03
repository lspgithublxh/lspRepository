$import("dept.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dept",
    name: "单表场景",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new dept.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});