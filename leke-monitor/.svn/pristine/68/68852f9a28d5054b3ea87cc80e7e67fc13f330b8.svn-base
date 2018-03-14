<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平板设备清单</title>
    <%@ include file="/pages/common/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${assets}/styles/common/global.css">
    <link rel="stylesheet" href="${assets}/styles/application/application.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
    <div class="g-bd">
    <%@ include file="/pages/navigate/navigate.jsp"%>
        <div class="g-mn">
            <div class="c-application">
                <div class="m-tab">
                    <ul>
                        <li><a href="${initParam.noticeServerName}/auth/common/lepad/school/appList.htm?schoolId=${schoolId}&spm=104032">应用管理</a></li>
                        <li><a href="${initParam.noticeServerName}/auth/common/lepad/school/whiteNameList.htm?schoolId=${schoolId}&spm=104032">网址导航</a></li>
                        <li><a href="${initParam.noticeServerName}/auth/common/lepad/school/accessLog.htm?schoolId=${schoolId}&spm=104032">访问记录</a></li>
                        <li class="active"><a href="${ctx}/auth/common/padcon/list.htm?spm=104032&schoolId=${schoolId}">平板设备清单</a></li>
                    </ul>
                    <div class="operation">
                        <span>${cSchoolName}</span>
                    </div>
                </div>
                <form action="" method="post" id="form">
                <div class="m-search-box">
                    <div class="item">
                        <label class="title">乐号：</label>
                        <input type="text"  id="jLekeNo" name="loginName" class="u-ipt u-ipt-nm">
                        <label class="title">姓名：</label>
                        <input type="text" id="jUserName" name="userName" class="u-ipt u-ipt-nm">
                        <label class="title">ROOT状态：</label>
                        <select name="root" id="jRelId" class="u-select u-select-nm">
                            <option value="">不限</option>
                            <option value="1">异常</option>
                            <option value="0">正常</option>
                        </select>
                    </div>
                    <div class="item">
                    	<label class="title">系统版本：</label>
                        <input type="text" name="version" class="u-ipt u-ipt-nm">
                        <label class="title">mac地址：</label>
                        <input type="text" name="mac" class="u-ipt u-ipt-nm">
                        <label class="title">imei：</label>
                        <input type="text" name="imei" class="u-ipt u-ipt-nm">
		                <input type="hidden" name="schoolId" value="${schoolId}">
                        <input type="button" id="jButtonId" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
                        <div class="operation">
                        	<a href="export.htm?schoolId=${schoolId}" class="u-btn u-btn-nm u-btn-bg-orange">导出全部</a>
                        </div>
                    </div>
                </div>
                <div class="m-table m-table-center">
                    <table>
                        <tr>
                            <th>乐号（姓名）</th>
                            <th>系统版本</th>
                            <th>班级</th>
                            <th>ROOT状态</th>
                            <th>设备型号及IMEI号</th>
                            <th>MAC地址</th>
                            <th>操作</th>
                        </tr>
                        <tbody id="page">
						</tbody>
                    </table>
                    <div class="page" id="divPage"></div>
                </div>
                </form>
            </div>
        </div>
        
 <textarea id="table_tpl" style="display: none;">
	{{#dataList}}
		<tr>
			<td>{{loginName}}({{userName}})</td>
			<td>{{version}}</td>
			<td>{{className}}</td>
			<td>{{rooted}}</td>
			<td class="j-imei" data-i="{{imei}}">{{imei}}</td>
			<td>{{mac}}</td>
			<td><a href="detail.htm?imei={{imei}}&schoolId=${schoolId }&loginName={{loginName}}">查看</a></td>
		</tr>
	{{/dataList}}
</textarea>
<textarea id="userList_tpl" style="display: none;">
	<div class="m-table m-table-center">
	<table>
		<thead>
			<tr>
			    <th>乐号</th>
			    <th>姓名</th>
			    <th>时间<th>
			</tr>
		</thead>
		<tbody id="page">
		{{#list}}
			<tr>
				<td>{{loginName}}</td>
				<td>{{userName}}</td>
				<td>{{tsStr}}</td>
			</tr>
		{{/list}}
		</tbody>
	</table>
	</div>
</textarea>
    </div>
    <script type="text/javascript">
			seajs.use('monitor/pages/common/padcon/list');
	</script>
</body>
</html>
