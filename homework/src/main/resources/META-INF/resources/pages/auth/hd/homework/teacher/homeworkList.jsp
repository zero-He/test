<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>作业列表</title>
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

	<!--tab页切换-->
	<nav class="c-tab-nav">
	    <div class="box">
	        <section class="item item-on" data-i="all">
	            全部
	        </section>
	        <section class="item" data-i="unfinished">
	            待批改（<span class="num">${unfinishedNum}</span>）
	        </section>
	        <section class="item" data-i="reCorrect">
	            待复批（<span class="num">${reCorrectNum}</span>）
	        </section>
	    </div>
	</nav>
	
	<nav class="c-search-top" style="display: none;">
	    <!--下拉菜单-->
	    <div class="search-type">
	        <div class="c-pullDown-tab">
	            <span class="item">全部</span>
	            <ul class="select-list" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); display: none;">
	                <li class="select-item">全部</li>
	                <li class="select-item">课件</li>
	                <li class="select-item">微课</li>
	                <li class="select-item">试卷</li>
	            </ul>
	        </div>
	    </div>
	    <!--搜索框-->
	    <div class="search-box">
	        <input type="text" placeholder="请输入作业名称" id="jHomeworkNameSearch" class="search-input">
	        <div class="search-btn">
	            <i class="c-input-delete"></i>
	        </div>
	    </div>
	</nav>
	<form id="jForm">
		<input type="hidden" name="homeworkFinishFlag" id="jHomeFinishFlag" value="all"/>
		<input type="hidden" name="homeworkName" id="jHomeworkName" value=""/>
		<input type="hidden" name="resType" id="jResType" value=""/>
	</form>
	<!--作业列表详情-->
	<article class="c-teacher-work-list" id="jScrollArea">
	    <ul class="list-box" id="jScrollContEle">
	        
	    </ul>
	</article>
	
	<!--模态窗口-->
	<article class="cover"></article>
	<article class="modal-box">
	    <ul class="btns-list">
	        <li class="item j-btns-item j-open" data-m="open">
	            公布答案
	        </li>
	        <li class="item j-btns-item j-proofread" data-m="proofread">
	            不做批改
	        </li>
	        <li class="item j-btns-item j-cuijiao" data-m="cuijiao">
	            催交作业
	        </li>
	        <li class="item j-btns-item item-bottom-radius" data-m="invalid">
	            作废
	        </li>
	        <li class="item">
	            取消
	        </li>
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
	        				<span class="c-work-tips">{{paperTypeStr}}</span>
						{{/cif}}
						{{#cif 'this.resType == 3'}}
							<span class="c-work-tips">{{paperTypeStr}}</span>
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
	            <input type="button" class="task-li-btn task-li-btn-home j-more" data-o="{{isOpenAnswer}}" data-s="{{subjective}}" data-c"{{isSelfCheck}}" data-i="{{homeworkId}}" data-t="{{resType}}" value="更多">
	            <input type="button" class="task-li-btn task-li-btn-home" value="查看">
	        </li>
		{{/dataList}}
	</script>
	<script  type="x-mustache" id="jReCorrectTpl">
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
	        				<span class="c-work-tips">{{paperTypeStr}}</span>
						{{/cif}}
						{{#cif 'this.resType == 3'}}
							<span class="c-work-tips">{{paperTypeStr}}</span>
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
	                    待复批：<span>{{#expression 'this.bugFixNum - this.reviewNum'}}{{/expression}}</span>/<span>{{bugFixNum}}</span>
	                </p>
	            </div>
				{{/cif}}
	            <input type="button" class="task-li-btn task-li-btn-home" value="复批">
	        </li>
		{{/dataList}}
	</script>
	<leke:pref />
	<script src="${assets }/scripts/common/mobile/common.js"></script>
	<script src="${assets }/scripts/common/handlebars.min.js"></script>
	<script src="/scripts/hd/homework/teacher/homeworkList.js"></script>
</body>
</html>