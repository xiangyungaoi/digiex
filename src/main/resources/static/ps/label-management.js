$(function () {
    getLabelList();
});

/**
 * 获取标签列表
 */
function getLabelList() {
    doRequestwithnoheader({},"get",urlData.labelList,function (res) {
        $("#label-info").html(template("label-info-list", {"data":res.data}));

        /*添加标签*/
        $(".js-add").on("click",function(){
            var type = $(this).data("type");
            var title;
            if (type === "add") {
                title = "新增标签"
            }
            layer.open({
                title:title,
                area:["610px","260px"],
                btn:false,
                move:false,
                closeBtn:0,
                skin:"Height412",
                content:template("add-main",{}),
                success:function (layero, index) {
                    $(".js-sure").on("click",function () {
                        var manage = {};
                        if(helper.isEmpty($(layero).find("input[name='label-name']").val())){
                            layer.msg("标签名称不能为空！");
                            return false;
                        }
                        manage.feature = $(layero).find("input[name='label-name']").val();
                        doRequestwithnoheader({"feature":manage.feature}, "post", urlData.addLabel, function (data) {
                            layer.msg("新增成功");
                            layer.close(index);
                            getLabelList();
                        }, function (res) {});
                    });
                    $(".js-cancel").on("click",function () {

                    });
                }
            })
        });
        /*删除标签*/
        $(".js-delete-label").on("click",function () {
            var id = $(this).data("id");
            var feature = $(this).data("feature");
            layer.open({
                title:'删除',
                area:["480px","238px"],
                btn:false,
                move:false,
                closeBtn:0,
                skin:"Height155",
                content:template("delete-main",{}),
                success:function (layero, index) {
                    $(layero).find("#delete-label-feature").html(feature);
                    $(".js-delete-sure").on("click",function () {
                        doRequestwithnoheader({id:id},"post",urlData.deleteLabelById,function (data) {
                            layer.msg("删除成功");
                            getLabelList();
                        },function (res) {})
                    });
                }
            })
        });
    });
}