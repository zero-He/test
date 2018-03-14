<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>做作业 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=20171115"/>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>

<div class="g-bd">
    <div class="g-mn">
        <div class="z-resource-detail">
            <div class="videowidth">
                <div id="jPreviewDiv" style="width:800px;"></div>
            </div>
            <div class="briefintroduction">
                <div class="info">
                    <div class="title">简介</div>
                    <ul>
                        <li class="item">
                            <label for="">微课名称：</label><span title="${microcourse.microcourseName}">${microcourse.microcourseName}</span>
                        </li>
                        <li class="item">
                            <label for="">微课格式：</label><span>${type.name}</span>
                        </li>
                        <li class="item">
                            <label for="">学段学科：</label><span title="">${microcourse.schoolStageName}${microcourse.subjectName}</span>
                        </li>
                        <li class="item">
                            <label for="">上传时间：</label><span><fmt:formatDate value="${microcourse.createdOn}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
                        </li>
                    </ul>
                    <c:if test="${microcourse.description != null}">
	                	<div style="padding: 0 20px; text-indent: 2em;">描述：&nbsp;&nbsp;${microcourse.description}</div>
                    </c:if>
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