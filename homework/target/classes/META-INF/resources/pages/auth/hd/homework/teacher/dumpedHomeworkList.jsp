<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>回收站</title>
	<meta id="homework-meta" charset="UTF-8" content="width=1920px, initial-scale=0.5, maximum-scale=0.5, user-scalable=0.5" name="viewport">
    <script src="https://static.leke.cn/scripts/common/mobile/rem.js"></script>
	<link rel="stylesheet" href="${assets }/styles/mobile/global.css" />
	<link rel="stylesheet" href="${assets }/styles/mobile/homework/homeworkpad.css" />
	<style>
	html, body{
		height:100%;
	}
	.c-teacher-work-list{
		box-sizing: border-box;
    	height: 100%;
    	overflow-y: auto;
	}
	</style>
</head>
<body>

	<!--下拉菜单-->
	<nav class="c-pullDown-tab">
	    <div class="opt">
	        <span class="item" id="jHomeworkOpt">作业类型：所有类型</span>
	        <ul class="select-list" id="jHomeworkList" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); display: none;">
	            <li class="select-item" data-i="0">所有类型</li>
	            <li class="select-item" data-i="3">课时作业</li>
	            <li class="select-item" data-i="6">推送作业</li>
	            <li class="select-item" data-i="5">点播作业</li>
	        </ul>
	    </div>
	    <div class="opt">
	        <span class="item" id="jResTypeOpt">资源类型：全部</span>
	        <ul class="select-list" id="jResTypeList" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); display: none;">
	            <li class="select-item">全部</li>
                <li class="select-item">课件</li>
                <li class="select-item">微课</li>
                <li class="select-item">试卷</li>
	        </ul>
	    </div>
	</nav>

	<form id="jForm">
		<input type="hidden" name="homeworkType" id="jHomeworkType" value=""/>
		<input type="hidden" name="resType" id="jResType" value=""/>
	</form>
	<!--作业列表详情-->
	<article class="c-teacher-work-list c-recycle-bin" id="jScrollArea">
	    <ul class="list-box" id="jScrollContEle">
	        
	    </ul>
	</article>
	
	<script  type="x-mustache" id="jTpl">
		{{#dataList}}
			<li class="list-item">
	            <div class="c-work-type 
				{{#cif 'this.resType == 1'}}
					c-courseware
				{{/cif}}
				{{#cif 'this.resType == 2'}}
					c-microgramme
				{{/cif}}
				{{#cif 'this.resType == 3'}}
					c-text-paper
				{{/cif}}
				item"></div>
	            <div class="work-name item">
	                <p class="name item-p">
	                    	{{homeworkName}}
	                </p>
	                <p class="item-note-p color-9">
						{{#cif 'this.resType != 3'}}
	        				<span class="c-work-tips">
								{{#cif 'this.paperId == null'}}
								{{homeworkTypeStr}}
								{{/cif}}
								{{#cif 'this.paperId != null'}}
								{{resTypeStr}}
								{{/cif}}
							</span>
						{{/cif}}
						{{#cif 'this.resType == 3'}}
							<span class="c-work-tips">
{{#cif 'this.paperId == null'}}
{{homeworkTypeStr}}
{{/cif}}
{{#cif 'this.paperId != null'}}
{{resTypeStr}}
{{/cif}}
</span>
	        				{{#cif 'this.status != 2'}}
	        					{{#cif '!this.isOpenAnswer'}}
	        						<span class="c-work-tips">公布答案</span>
								{{else}}
									<span class="c-work-tips color-orange border-color-orange">已公布答案</span>
	        					{{/cif}}
								{{#cif 'this.subjective'}}
	        						{{#cif '!this.isSelfCheck' }}
	        							<span class="c-work-tips">不做批改</span>
									{{else}}
										<span class="c-work-tips color-orange border-color-orange">不做批改</span>
	        						{{/cif}}
								{{/cif}}
							{{/cif}}
						{{/cif}}
	                </p>
	            </div>
	            <div class="work-time item">
	                <p class="time item-p">
	                    截止：<span>{{closeTimeStr}}</span>
	                </p>
	                <p class="item-note-p color-9">
	                    班级：<span>{{classNameStr}}</span>
	                </p>
	            </div>
				{{#cif 'this.resType != 3'}}
	            <div class="work-num item">
	                <p class="time item-p">
	                    已查看：<span>{{finishNumStr}}</span>/<span>{{totalNum}}</span>
	                </p>
	            </div>
				{{/cif}}
				{{#cif 'this.resType == 3'}}
				<div class="work-num item">
	                <p class="time item-p">
	                    上交：<span>{{finishNumStr}}</span>/<span>{{totalNum}}</span>
	                </p>
				{{#avgScore}}
	                <p class="item-note-p color-9">
	                    平均分：<span class="color-thin-orange">{{this}}分</span>
	                </p>
				{{/avgScore}}
	            </div>
	            <div class="work-correct item">
	                <p class="time item-p">
	                    批改：<span>{{correctNumStr}}</span>/<span>{{finishNumStr}}</span>
	                </p>
	            </div>
				{{/cif}}
	            <input type="button" class="task-li-btn task-li-btn-red over-delete j-delete" data-i="{{homeworkId}}" value="永久删除">
	            <input type="button" class="task-li-btn task-li-btn-home j-recover" data-i="{{homeworkId}}" value="恢复">
	        </li>
		{{/dataList}}
	</script>
	<leke:pref />
	<script src="${assets }/scripts/common/mobile/common.js"></script>
	<script src="${assets }/scripts/common/handlebars.min.js"></script>
	<script src="/scripts/hd/homework/teacher/dumpedHomeworkList.js"></script>
</body>
</html>