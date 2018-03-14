<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>做作业 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}"/>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>

<div class="g-bd">
    <div class="g-mn">
        <div class="z-resource-detail" style="margin: 0;">
            <div class="videowidth" style="width: 800px;">
                <div id="jPreviewDiv" style="width:800px;"></div>
            </div>
            <div class="briefintroduction">
                <div class="info">
                    <div class="title">简介</div>
                    <ul>
                        <li class="item">
                            <label for="">课件名称：</label><span title="${homework.homeworkName}">${homework.homeworkName}</span>
                        </li>
                        <li class="item">
                            <label for="">课件格式：</label><span>${type.name}</span></li>
                        <li class="item">
                            <label for="">学段学科：</label><span>${courseware.schoolStageName}${homework.subjectName}</span>
                        </li>
                        <li class="item">
                            <label for="">上传时间：</label><span><fmt:formatDate value="${homework.createdOn}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="global-toolbar-note-params" value="${globalNoteParams}" />

<script>
window.Csts = ${Csts};
seajs.use('homework/homework/doWork2');
</script>
</body>
</html>