<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业 - <locale:message code="common.page.header.leke"/></title>
    <%@ include file="/pages/common/meta.jsp" %>

    <link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
    <style>
        .s-c-yellow {
            color: #FDB314 !important;
        }

        .submitTips {
            display: inline;
        }

        .submitTips .m-tippop {
            display: inline-block;
            margin-left: 8px;
            border: 1px solid #f8785f;
            color: #f8785f !important;
        }

        .submitTips .m-tippop .arrow {
            top: 6px;
        }

        .submitTips .m-tippop em {
            border-right-color: #f8785f;
        }

        .submitTips .m-tippop .msg {
            padding: 2px 5px;
        }
    </style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
    <%@ include file="/pages/navigate/navigate.jsp" %>
    <div class="g-mn">
        <c:set var="SUBNAV" value="homework"/>
        <form id="myHomeworkForm" method="post" autocomplete="off">
            <!-- 隐藏域 -->
            <input type="hidden" id="isSubmit" name="isSubmit"/>
            <input type="hidden" id="bugFixStage" name="bugFixStage"/>
            <input type="hidden" id="ord" value="${ord }"/>
            <!-- 我的作业 -->
            <div class="m-search-box">
            	<div class="operation">
						<a class="u-btn u-btn-at u-btn-bg-turquoise f-pr" target="_blank" href="/auth/student/homework/correctHomeworkList.htm?spm=101003">批改作业
						<span style="position:absolute; top:-10px; right:-10px; width:20px; height:20px; line-height:20px; font-size:12px; text-align:center; color:#fff; background:#f7785f; border-radius:50%;">${correctTotal }</span>
						</a>
				</div>
                <div class="tab">
                    <ul id="jCorrectFlag">
                        <li data-status="1"
                            <c:if test="${ord == 2}">class="cur"</c:if> ><a href="javascript:;"><locale:message code="homework.stuhomework.form.todo"/></a></li>
                        <li data-status="2"
                            <c:if test="${ord == 3}">class="cur"</c:if> ><a href="javascript:;">待订正</a></li>
                        <li data-status="0"
                            <c:if test="${ord == null}">class="cur"</c:if> ><a href="javascript:;"><locale:message code="homework.stuhomework.form.all"/></a></li>
                    </ul>
                </div>
                <div class="item">
                    <label class="title"><locale:message code="homework.homework.subject"/>：</label>
                    <select id="jSubject" class="u-select u-select-nm"></select>
                    <input type="hidden" id="jSubjectId" name="subjectId" value="${subjectId }"/>
                    <label for="" class="title"><locale:message code="homework.homework.homeworktype"/>：</label>
                    <select id="jHomeworkType" name="homeworkType" class="u-select u-select-nm"></select>
                    <label for="" class="title">资源类型：</label>
                    <select id="jResType" name="resType" class="u-select u-select-nm"></select>
                    <label for="" class="title">作业标题：</label>
                    <input name="homeworkName" id="homeworkName" class="u-ipt u-ipt-nm"/>
                </div>
                <div class="item">
                    <label for="" class="title"><locale:message code="homework.stuhomework.form.deadline"/>：</label>
                    <input name="closeTime" id="closeTime" class="u-ipt u-ipt-nm Wdate"/>
                    <locale:message code="homework.homework.form.search.to"/>
                    <input name="closeEndTime" id="closeEndTime" class="u-ipt u-ipt-nm Wdate"/>
                    <input id="ButMyHomework" type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" value="<locale:message code="homework.common.search" />">
                </div>
            </div>


            <!--智能排序-->
            <input type="hidden" name="sort" id="sortId" value="timediff">
            <div class="intell-tab">
                <ul>
                    <li class="intell-tab-li intell-tab-li-on" data-value="timediff">
                        <a ><em>紧急度</em><i class="intell-tab-li-arrow"></i></a>
                    </li>
                    <li class="intell-tab-li" data-value="closeTime">
                        <a ><em>作业截止时间</em><i class="intell-tab-li-arrow"></i></a>
                    </li>

                    <li class="intell-tab-li" data-value="startTime">
                        <a ><em>作业开始时间</em><i class="intell-tab-li-arrow"></i></a>
                    </li>
                </ul>
            </div>

            <div class="z-homework-grade z-homework-grade-stu">
                <ul id="myHomeworkContext">

                </ul>
                <div class="page" id="page">
                </div>
                <div class="m-tiptext m-tiptext-text m-tiptext-normal">
                    <i class="iconfont icon">󰅂 </i>
                    <div class="msg">
                        备注：分数+，分数指作业中客观题的分数，+意味着老师还未批完主观题。如20+，指该作业客观题得分为20，主观题老师还没批完。
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>

<script id="myHomeworkContext_tpl" type="text/x-handlebars-template">
{{#dataList}}
<li class="item">
    <div class="title ">
        <a title = "{{homeworkName}}" data-id="{{homeworkDtlId}}"  data-submittime="{{submitTime}}" data-restype="{{resType}}" data-status="{{status}}" target="_blank" class="name jHomeworkTitle">
        [{{resTypeStr}}] {{homeworkName}}</a>

        {{! 作业状态--------------------------}}
		{{#cif 'this.resType == 3'}}
			{{#cif 'this.submitTime == null'}}
                <div class="notsubmit submitTips">未提交{{{fmtSubmitStatusTip this}}}</div>
            {{else}}
				{{#cif 'this.bugFixStage == null || this.bugFixStage == 0'}}
					{{#cif 'this.correctTime != null'}}<div class="s-c-green submitTips">已批改{{{fmtSubmitStatusTip this}}}</div>	{{/cif}}
					{{#cif 'this.correctTime == null'}}<div class="s-c-green submitTips">待批改{{{fmtSubmitStatusTip this}}}</div>	{{/cif}}
				{{else}}
					<div class="submitTips">
						{{#cif 'this.bugFixStage == 1'}}待订正{{/cif}}
						{{#cif 'this.bugFixStage == 2'}}已订正{{/cif}}
						{{#cif 'this.bugFixStage == 3'}}订正通过{{/cif}}
						{{{fmtSubmitStatusTip this}}}
					</div>
				{{/cif}}
			{{/cif}}
		{{else}}
            {{#cif 'this.submitTime == null'}}
                <div class="notsubmit submitTips">未学习{{{fmtSubmitStatusTip this}}}</div>
            {{else}}
                <div class="submitTips">已学习{{{fmtSubmitStatusTip this}}}</div>
            {{/cif}}
		{{/cif}}

        {{!  操作按钮---------------- }}
		{{! 已作废}}
		{{#cif 'this.status == 2'}}
			<div class="c-btns">已作废</div>
		{{else}}
			<div class="c-btns">
			{{#cif 'this.submitTime == null'}}
			    {{#cif 'this.resType == 3'}}
				    <a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/doWork.htm?homeworkDtlId={{homeworkDtlId}}">答题</a>
				{{else}}
                    <a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/doWork.htm?homeworkDtlId={{homeworkDtlId}}">学习</a>
				{{/cif}}
			{{else}}
				{{#cif 'this.resType == 3'}}
					{{#cif 'this.bugFixStage == 1'}}
						<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/doBugFix.htm?homeworkDtlId={{homeworkDtlId}}">订正</a>
					{{else}}
						<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}">查看</a>
					{{/cif}}
				{{else}}
					<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}">复习</a>
				{{/cif}}
				{{#cif 'this.correctTime != null && this.homeworkType != 6'}}
					<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="${initParam.diagServerName}/auth/student/homework/analysis2.htm?homeworkId={{homeworkId}}">分析</a>
					<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="${initParam.diagServerName}/auth/common/report/homework/person/{{homeworkId}}-{{homeworkDtlId}}.htm">查看报告</a>
				{{/cif}}
			{{/cif}}
			</div>
		{{/cif}}
    </div>

    <div class="tips">
        <span><i class="iconfont">&#xf00ca;</i> {{subjectName}}</span>
        {{#cif 'this.resType == 3'}}
            <span><i class="iconfont">&#xf00ec;</i> 老师：{{{teacherName}}}</span>
            {{#cif 'this.scoreRate == 1'}}
            <span class="full-marks"></span>
            {{else}}
            <span>
                <i class="iconfont"></i>
	            {{#cif 'this.correctTime != null'}}
		            分数：{{num this.score '#0.0' '--'}}
	                {{#cif 'this.score != null'}}
	                {{correctStatusStr}}
	                {{/cif}}
	            {{/cif}}
            </span>
            {{/cif}}
            <p class="time">提交/截止时间：{{submitTimeString}} / {{closeTimeStr}}</p>
        {{else}}
            <span><i class="iconfont">&#xf00ec;</i> 老师： {{{teacherName}}}</span>
            <p class="time">开始/截止时间：{{submitTimeString}} / {{closeTimeStr}}</p>
         {{/cif}}

		<div class="state">
			{{#cif 'this.isOpenAnswer'}} <span class="u-state-label">已公布答案</span> {{/cif}}
			{{#cif 'this.isSelfCheck'}} <span class="u-state-label">不作批改</span> {{/cif}}
		</div>
    </div>
</li>
{{/dataList}}

</script>
<leke:pref/>
<script>
    seajs.use('homework/homework/myHomeworkList');
</script>
</body>
</html>
