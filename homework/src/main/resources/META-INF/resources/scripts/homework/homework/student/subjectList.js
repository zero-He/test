$(function () {
    //初始化数据
    initData();
});

/**
 * 初始化页面数据
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017/4/10 16:08
 */
function initData() {
    $.ajax({
        "url": "/auth/hd/queryStudentSubjectListData.htm",
        "type": "POST",
        "dataType": "JSON",
        success: function (result) {
            var res = $.parseJSON(result);
            if (res.success) {
                var htmlTmpl = $("#myHomeworkContext_tpl").html();
                var template = Handlebars.compile(htmlTmpl);
                var resultData = res.datas.subjectList;
                for (var i = 0; i < res.datas.subjectList.length; i++) {
                    if (resultData[i].submitNum > 99) {
                        resultData[i].submitNum = resultData[i].submitNum + "+";
                    }
                    if (resultData[i].bugFixNum > 99) {
                        resultData[i].bugFixNum = resultData[i].bugFixNum + "+";
                    }
                }
                var content = template(resultData);
                $(".template-context").html(content);
            }
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });
}
