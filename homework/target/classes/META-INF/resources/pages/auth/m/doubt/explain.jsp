<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>${word}</title>
    <link rel="stylesheet" href="${assets}/styles/mobile/global.css">
    <link rel="stylesheet" href="${assets}/styles/mobile/doubt.css">
    <%@ include file="/pages/common/cordova.jsp"%>
    <style type="text/css">
    	.version-result{float: right;margin-right: 15px;color: #999;max-width: 50%;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;}
    </style>
</head>
<body id="jDroploadId" style="padding-top:0px;" onload="onLoad();">
	<%-- <header class="c-header">
        <a class="back" href="detail.htm?doubtId=${doubtId}&resolved=${resolved}"></a>
        	${word}
        <span id="jSubmit" class="operation">提交</span>
    </header> --%>
    <input id="jDoubtId" type="hidden" value="${doubtId}">
    <input id="jResolved" type="hidden" value="${resolved}">
    <form style="display: none;" id="jForm" action="explain.htm" method="post">
    </form>
    <section class="c-question-edit" style="top:0px">
        <div class="description">
            <textarea name="" id="jDescribe" cols="30" rows="10" placeholder="<c:if test="${currentRoleId==101}">回答内容</c:if><c:if test="${currentRoleId==100}">追问内容</c:if>" maxlength="500"></textarea>
            <span class="count"><i id="jCount">0</i>/<i>500</i></span>
        </div>
        
        <c:if test="${currentRoleId==101}">
	        <div class="choose c-arrow" id="j-choose-version">
				教材版本及章节 <span data-a="${doubt.pressId }" data-b="${doubt.materialId}" data-c="${doubt.materialNodeId}" data-d="${doubt.materialNodeId}" class="version-result">${doubt.materialPathName}</span>
	        </div>
        </c:if>
        <span class="result" style="display: none;" data-subjectid="${doubt.subjectId}"></span>

        <div class="preview">
            <div class="c-media" id="jAudioDiv" style="display: none;">
                <button class="media-btn">
                    <span class="waves">
                        <b class="w1"><i></i></b>
                        <b class="w2"><i></i></b>
                        <b class="w3"><i></i></b>
                    </span>
                </button>
                <span id="jAudioLength" class="length"></span> 
                <span id="jRemoveMedia" style="color:#999" class="length">删除</span> 
            </div>

            <ul>
                <li>
                    <div id="jPhotos" class="photos">
                    </div>
                </li>
                <li>
                    <div id="jCamera" class="camera">
                    </div>
                </li>
                <li>
                    <div class="media" id="j-media"></div>
                </li>
            </ul>
        </div>
        
        <!-- 选择教材版本 -->
        <div class="choose-version c-hidden j-choose-version" data-v="m" data-i="${doubt.schoolStageId}">
            <div class="content" style="bottom: -224px">
                <h5><span class="cancel">取消</span>选择教材版本信息<span class="save">确定</span></h5>
                <div class="lists">
                    <ul>
                        <li class="version-title">教材版本 <span data-v="a" class="selected arrow-up">未选择<span></li>
                        <ul class="version-opts c-hidden" id="jPresses">
                            
                        </ul>
                        <li class="version-title">年级或课本<span data-v="b" class="selected arrow-up">未选择<span></li>
                        <ul class="version-opts c-hidden" id="jMaterials">
                           
                        </ul>
                        <li class="version-title">教材单元<span data-v="c" class="selected arrow-up">未选择<span></li>
                        <ul class="version-opts c-hidden" id="jSectionRange">
                           
                        </ul>
                        <li class="version-title">教材章节<span data-v="d" class="selected arrow-up">未选择<span></li>
                        <ul class="version-opts c-hidden" id="jSectionChapter">
                            
                        </ul>
                    </ul>
                </div>
            </div>
        </div>

        <!-- 录音弹层 -->
        <div class="media-box" id="jRecordDiv">
            <div class="content">
                <h5><span class="cancel">取消</span>添加录音<span class="save">确定</span></h5>
                <div class="tips">
                    <span id="jTips">点击开始录音最长3分钟</span>
                    <span id="jShowMediaTime"></span>
                </div>
                <!-- 操作区 -->
                <table class="operation">
                    <tr>
                        <td>
                            <span id="jRecordAgain" class="record-again">重录</span>
                        </td>
                        <td>
                            <div id="jRecord" class="start-record c-record">
                                <ul>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                </ul>
                            </div>
                            <div id="jPlay" class="play" style="display: none;">
                                <div class="m-circleprogress">
                                    <span class="status"></span>
                                    <div class="l">
                                        <div class="l-circle"></div>
                                    </div>
                                    <div class="r">
                                        <div class="r-circle"></div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </section>
	<div class="m-load" style="display: none;">
	    <i class="loader"></i>
	</div>
	
	<script id="jVersionMaterialsListTpl" type="x-mustache">
		{{#materials}}
			<li data-i="{{materialId}}">{{materialName}}</li>
		{{/materials}}
	</script>
	
	<script id="jVersionPressesListTpl" type="x-mustache">
		{{#presses}}
			<li data-i="{{pressId}}">{{pressName}}</li>
		{{/presses}}
	</script>
	
	<script id="jVersionSectionListTpl" type="x-mustache">
		{{#section}}
			<li data-i="{{materialNodeId}}">{{materialNodeName}}</li>
		{{/section}}
	</script>
	
    <script src="${assets}/scripts/common/mobile/common.js?_t=20171115"></script>
    <script src="/scripts/m/common/nativePlugins.js?_tt=20171115"></script>
    <script src="/scripts/m/common/tools.js?_tt=20171115"></script>
    <script src="/scripts/m/doubt/explain.js?_tt=20171115"></script>
</body>
</html>