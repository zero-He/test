<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>答题卡上传记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/abnormal.css?t=${_t}" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-uploadinfo-head">
                <h5>作业详情<span> | </span></h5>
                <span class="item">作业标题：<b>${homeworkName}</b></span>
                <span class="item">作业份数：<b>${totalNum}</b></span>
            </div>
			<form id="jPageForm" method="post" class="m-search-box">
				<label class="title">乐号/学号：</label>
				<input type="text" id="jLekeNo" name="lekeNo" class="u-ipt u-ipt-sm">
				<label class="title">姓名：</label>
				<input type="text" id="jUserName" name="userName" class="u-ipt u-ipt-sm">
				<input class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" type="button" value="查询">
			</form>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>乐号</th>
							<th>学号</th>
							<th>姓名</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
			</div>
			<div class="m-tips" id="jNoDataMsg" style="display: none;"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>
	<leke:pref />
</body>
<script id="jPageTpl" type="text/handlebars">
{{#.}}
<tr>
	<td>{{#cif 'this.lekeNo != null && this.lekeNo != "-1"'}}{{lekeNo}}{{else}}--{{/cif}}</td>
	<td>{{#cif 'this.stuno != null && this.stuno != "-1"'}}{{stuno}}{{else}}--{{/cif}}</td>
	<td>{{userName}}</td>
	<td class="operation">
		<a href="${ctx}/auth/common/sheet/bookInfo.htm?bookId={{id}}" target="_blank">查看</a>
	</td>
</tr>
{{/.}}
</script>
<script>
	var bookList = ${bookJson};
	seajs.use('homework/sheet/bookList');
</script>
</html>
