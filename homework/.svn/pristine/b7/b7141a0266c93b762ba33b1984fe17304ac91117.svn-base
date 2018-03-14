<%@ page pageEncoding="UTF-8"%>
<!-- 左侧列表 -->
<!-- 问题列表 -->
<section id="jDroploadId" class="z-doubt__sidelist">
	<div id="jPageListBody"></div>
</section>
	<script id="jPageListTpl" type="x-mustache">
		{{#dataList}}
			<div class="c-doubter__wrap">
			<div class="c-doubter__remove" data-i="{{doubtId}}">删除</div>
			    <div class="c-doubter" data-i="{{doubtId}}">
			        <div class="c-doubter__header">
			            <div class="c-doubter__doubter">
			                <span class="c-doubter__imgcon">
			                    <img src="${initParam.fileServerName}/{{photo}}" alt="" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'" class="c-doubter__img">
			                </span>
			                <span class="c-doubter__name">{{userName}}</span>
			            </div>
			            <a data-i="{{doubtId}}" class="c-doubter__collect
			            <c:if test="${currentRoleId==101}">
							{{#teacherCollect}}c-doubter__collect--done{{/teacherCollect}}
						</c:if>
						<c:if test="${currentRoleId==100}">
							{{#studentCollect}}c-doubter__collect--done{{/studentCollect}}
						</c:if>"></a>
			        </div>
			        <div class="c-doubter__body">
						<div class="c-doubter__text">
			            {{#doubtContent}}{{doubtContent}}{{/doubtContent}}{{^doubtContent}}请点击查看详情{{/doubtContent}}
						</div>
			        </div>
			        <div class="c-doubter__footer">
			            <div class="c-doubter__info">
			                <span class="c-doubter__type {{doubtCasType}}">
			                   {{sourceStr}}
			                </span>
			                <span class="c-doubter__classname">
			                    {{doubtTitle}}
			                </span>
			            </div>
			            <div class="c-doubter__time">
			                {{explainTimeOnString}}
			            </div>
			        </div>
			    </div>
			</div>
		{{/dataList}}
    </script>
<script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>