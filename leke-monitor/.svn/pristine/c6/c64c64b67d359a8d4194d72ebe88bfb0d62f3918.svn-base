<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的乐课- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
<link rel="stylesheet" href="${assets}/styles/home/personal-center.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
	<a id="support-link" href="${initParam.supportServerName}/auth/agent/agent.htm" target="_blank" style="f-dn"></a>
	
	<input type="hidden" id="host" value="${initParam.onlineServerName}">
	<input type="hidden" id="ticket" name="ticket" value="${ticket}"/>
	<input type="hidden" id="beike" value="${initParam.beikeServerName}">
	<input type="hidden" id="myDate" value="${myDate}">
<%@ include file="/pages/navigate/navigate.jsp"%>
    <div class="g-mn z-personal-mn">
        <div>
            <div class="z-percenter-mn-wrap">
                <div class="z-percenter-mn-time f-bfc">
                    <span class="welcome"><span class="big">Hello!<span class="orange" id="userName">${userName}</span></span>，欢迎使用乐课网。</span>

                    <div class="date">
                        <div class="left">
                            <p class="y-m-d j-y-m-d"></p>

                            <p class="day-week"><span class="day j-day"></span> <span class="week j-week"></span></p>
                        </div>
                        <div class="right">
                            <p class="chinese-date">农历</p>

                            <p class="chinese-day j-chinese-day"></p>

                            <p class="holiday j-holiday"></p>
                        </div>
                    </div>
                </div>
                <!-- 技术支持 -->
                <div class="support">
                    <div class="do-count" data-bind="component:{
                    	name:'technical-support-total',
                    	params:{
                    		options:{
                    			postUrl:'/auth/technicalSupport/course/currentPlatformCourseStat.htm?callback=?'
                    		}
                    	}
                    }">
                      
                    </div>
                   
                    <div class="table-sheet">
                        <div class="head">
                            <div class="button">
                            	<label class="title" id="showDate">${myDate}</label>
                                <input type="button" id="dayBefore" value="前一天">
                                <input type="button" id="dayAfter" value="后一天">
                                <input type="button" id="today" value="今天" class="cur">
                            </div>
                        </div>
                        <div class="m-search-box j-search-box f-mt20">
							<label class="title j-area-label">地区范围：</label>
							<input type="hidden" name="area" class="j-area-select"/>
							<label class="title">学校：</label>
							<input type="text" name="school" class="u-ipt u-ipt-nm j-school-select"/>
							<label class="title">状态:</label>
							<select id="classState" name="classState" class="u-select  u-select-sm">
								<option value="">全部</option>
								<option value="1">未上课</option>
								<option value="0">上课中</option>
								<option value="-1">已结束</option>
							</select>
							<br><br>
							<label class="title ">学校类型：</label>
							<select name="schoolNature" id="schoolNature"  class="u-select  u-select-sm">
								<option value="">请选择</option>
								<option value="1">基础教育</option>
								<option value="2">培训机构</option>
								<option value="3">个人入驻</option>
							</select>
							<label class="title">学科：</label>
							<select name="subjectName" class="u-select  u-select-sm"
								data-bind="options: subjects,
                       			optionsText: 'subjectName',
                       			optionsValue: 'subjectName',
                      			value: subjectName,
                       			optionsCaption: '请选择'"></select>
							<label class="title">教师名称:</label>
							<input type="text" name="teacherName" id="teacherName" class="u-ipt u-ipt-nm"/>
							<button class="u-btn u-btn-nm u-btn-bg-turquoise" id="searchBut">查询</button>
						</div>
                        <div class="con">
                            <table>
                                <tbody><tr class="con-hd">
                                    <th width="5%"></th>
                                    <th width="95%">
                                        <table class="sub-table">
                                            <tbody><tr>
                                                <td width="15%">学校</td>
                                                <td width="10%">班级名称</td>
                                                <td width="10%">学科</td>
                                                <td width="10%">课堂名称</td>
                                                <td width="15%">老师</td>
                                                <td width="20%">课堂起止时间</td>
                                                <td width="10%">状态</td>
                                                <td width="10%">操作</td>
                                            </tr>
                                        </tbody></table>
                                    </th>
                                </tr>
                                <tr class="con-btn j-btn">
                                    <td colspan="2"><span class=""></span><i class="iconfont up"></i></td>
                                </tr>

                                <tr class="con-item" style="display: table-row;">
                                    <td><span class="time">上午</span></td>
                                    <td>
                                        <table class="sub-table">
                                            <tbody id="amTbody">
                                           
                                        	</tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr class="con-btn j-btn">
                                    <td colspan="2"><span class="">下午</span><i class="iconfont down"></i></td>
                                </tr>

                                <tr class="con-item" style="display: none;">
                                    <td><span class="time">下午</span></td>
                                    <td>
                                        <table class="sub-table">
                                            <tbody id="pmTbody">
                                        </tbody></table>
                                    </td>
                                </tr>

                                <tr class="con-btn j-btn">
                                    <td colspan="2"><span class="">晚上</span><i class="iconfont down"></i></td>
                                </tr>

                                <tr class="con-item" style="display: none;">
                                    <td><span class="time">晚上</span></td>
                                    <td>
                                        <table class="sub-table">
                                            <tbody id="ntTbody">
                                        </tbody></table>
                                    </td>
                                </tr>
                            </tbody></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<textarea id="jSuListTpl"  style="display:none;">
{{#dataList}}
<tr>
<td width="15%"><b title="{{schoolName}}({{schoolCode}})">{{schoolNameStr}}</b></td>
<td width="10%" class="{{stateClass}}">{{clazzName}}</td>
<td width="10%" class="{{stateClass}}">{{subjectName}}</td>
<td width="10%" class="{{stateClass}}">{{csName}}</td>
<td width="20%" class="{{stateClass}}"><b class="dot {{isOnlineStr}}"></b>{{teacherName}}（{{phone}}）</td>
<td width="15%" class="{{stateClass}}">{{startTimeStr}}~{{endTimeStr}}</td>
<td width="10%" class="{{stateClass}}">{{stateStr}}</td>
<td width="10%" class="operation">{{{desktopUrl}}}</td>
</tr>
{{/dataList}}
</textarea>
<script>
 	seajs.use('monitor/pages/technicalSupport/course/technicalIndex');
 	seajs.use(Leke.domain.tutorServerName+'/scripts/tutor/common/getCurDate');
</script>
</body>
</html>