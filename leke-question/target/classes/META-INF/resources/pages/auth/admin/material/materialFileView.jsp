<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>电子教材 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/beike/beike.css?t=${_t}"/>
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}"/>

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="z-resource-detail">
				<div class="videowidth"><div id="jPreviewDiv"></div></div>
				<div class="briefintroduction">
                    <div class="info">
                        <div class="title">简介</div>
                        <ul>
                            <li class="item">
                                <label for="">教材名称：</label> 
                                <span title="${material.materialName}">${material.materialName}</span>
                            </li>
                            <li class="item">
                                <label for="">文件格式：</label> <span>PDF</span> </li>
                            <li class="item">
                                <label for="">学段学科：</label>
                                <span>${material.schoolStageName}${material.subjectName}</span>
                            </li>
                            <li class="item">
                                <label for="">上传时间：</label><span><fmt:formatDate value="${assoc.createdOn}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                            </li>
                             <li class="item">
                                <label for="">总页数：</label><span>${assoc.pageCount == null ? 0 : assoc.pageCount}</span> </li>
                        </ul>
                    </div>
                   
                </div>
			</div>
		</div>
	</div>

<script>
	window.materialFileViwCtx = {
			id:'jPreviewDiv',
			userId: ${userId},
			filePath: '${assoc.cwUrl}',
			taskId: '${assoc.taskId}',
			cwSuffix: '${assoc.cwSuffix}',
			pageCount: '${assoc.pageCount}',
			fileName: '${material.materialName}'
	};
	seajs.use('question/view/material/materialFileView');
</script>
</body>
</html>