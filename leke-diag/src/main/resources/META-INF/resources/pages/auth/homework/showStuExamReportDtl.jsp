<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
    <link rel="stylesheet" href="${assets}/styles/homework/ts-pc.css">
    <title>成绩单</title>
    <%@ include file="/pages/common/meta.jsp"%>
  </head>
  <body>
  	<%@ include file="/pages/header/header.jsp"%>
    <div class="m-bg"></div>
    <div class="g-bd">
      <div class="g-sd">
    <%@ include file="/pages/navigate/navigate.jsp"%>

      </div>

      <div class="g-mn">
        <div class="transcripts">
          <div class="ts-head">
            <div id="studentName" class="ts-stu-name">
            </div>
            <div class="ts-stu-call">
             	 同学：
            </div>
          </div>
          <div class="ts-body">
            <div id="greatings" class="ts-talk">
            </div>
            <div class="ts-tab">
              <table>
              	<thead>
	                <tr>
	                  <th width="20%">学科</th>
	                  <th width="20%">成绩</th>
	                  <th width="20%">平均分</th>
	                  <th width="20%">最高分</th>
	                  <th width="20%">最低分</th>
	                </tr>
                </thead>
                <tbody id="jtbodyData"></tbody>
              </table>
            </div>
          </div>
          <div class="ts-foot">
            <div class="ts-paper-tip">
              	备注：平均分、最高分、最低分为班级维度统计。如有疑问请联系班主任。
            </div>
            <div id="schoolName" class="ts-sch-tag">
            </div>
            <div id="createdOn" class="ts-paper-time">
            </div>
          </div>
        </div>
      </div>
    </div>
<input type="hidden" id="examReportId" name="examReportId" value="${examReportId}" />
<input type="hidden" id=studentId name="studentId" value="${studentId}" />

 <script id="stuExamReportDtlTpl" type="x-mustache">
{{#dataList}}
	<tr>
		<td>{{subjectName}}</td>
		<td>{{score}}</td>
		<td>{{avgScore}}</td>
		<td>{{maxScore}}</td>
		<td>{{minScore}}</td>
	</tr>
{{/dataList}}
</script>

<script src="https://static.leke.cn/scripts/common/jquery-1.12.0.min.js" charset="utf-8"></script>
<script type="text/javascript">
      $(function () {
        var talkHei = $('.ts-talk').height();
        var talkTop = $('.ts-talk').position().top;
        var talkHeptoB = talkHei + talkTop;
        $('.ts-body').css('padding-top', talkHeptoB + 'px');
      })
</script>

<script type="text/javascript">
	seajs.use('diag/homework/showStuExamReportDtl');
</script>
  </body>
</html>
