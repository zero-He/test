<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>国庆活动首页</title>
	<link rel="stylesheet" href="${assets}/styles/common/global.css?t=${_t}">
	<link rel="stylesheet" href="${assets}/pages/activity/2017-9-21/styles/style.css?t=${_t}">
	<script>
		var remove_ul = false;
		(function() {
			var UA = navigator.userAgent;
			var isAndroid = /android|adr/gi.test(UA), isIos = /iphone|ipod|ipad/gi.test(UA) && !isAndroid;
			var script = document.createElement('script');
			if (isIos) {
				script.src = '${assets}/scripts/common/mobile/cordova/cordova-ios.js';
				document.head.appendChild(script);
				remove_ul = true;
			} else if(isAndroid){
				script.src = '${assets}/scripts/common/mobile/cordova/cordova-android.js';
				document.head.appendChild(script);
				remove_ul = true;
			}
		})();
		if(navigator.appName == "Microsoft Internet Explorer"&&parseInt(navigator.appVersion.split(";")[1].replace(/[ ]/g, "").replace("MSIE",""))<9){
        	alert("您的浏览器版本过低，请下载IE9及以上版本");
    	}
	</script>
</head>
<body>
<div class="nation-day__wrap">
	<div class="c-nation">
		<div class="c-nation__main">
			<ul class="c-nation__header" id="j_ul">
				<a id="jMissionA" href="#jMission_1">
					<li class="c-nation__header-lists">
							<span class="c-nation__header-s"></span>订正作业
					</li>
				</a>
				<a href="#jMission_2">
					<li class="c-nation__header-listss">
						<span class="c-nation__header-s c-nation__header-ss"></span>消灭错题集
					</li>
				</a>
				<a href="#jMission_3">
					<li class="c-nation__header-listsss">
						<span class="c-nation__header-s c-nation__header-sss"></span>自主练习
					</li>
				</a>
			</ul>
			<div class="c-nation__countdown-wrap">
				<div class="c-nation__countdown-word">距离活动结束仅剩<span>COUNT&nbsp;DOWN</span></div>
				<div class="c-nation__countdown" id="countdown"></div>
			</div>
			<div id="jMission_1" class="c-nation__listone">
				<div class="c-nation__listone-header">
					<div class="c-nation__onetitle-wrap">
						<div class="c-nation__listone-title <c:if test="${ms_1!=null}">active</c:if>">
							<div class="c-nation__onetitle-word">订正作业</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-left"></li>
							<li class="c-nation__listone-item c-nation__listone-right"></li>
						</ul>
					</div>
					<div class="c-nation__onegrade-wrap">
						<div class="c-nation__listone-grade <c:if test="${ms_1!=null}">active</c:if>">
							<div class="c-nation__onegrade-word">s</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-sdec"></li>
						</ul>
					</div>
				</div>
				<div class="c-nation__listone-con <c:if test="${ms_1!=null}">active</c:if>">
					<div class="c-nation__list-state 
						<c:if test="${ms_1==null}">c-nation__list-notunlock</c:if>
						<c:if test="${ms_1.state>1}">c-nation__list-finished</c:if>
						<c:if test="${ms_1.state==1}">c-nation__list-ongoing</c:if>
						"></div>
					<div class="c-nation__listone-conword">10月到来，9月的待订正作业仍未消灭？赶紧来解决上个月的待订正作业吧！订正完成9月1日至9月30日产生的所有待订正作业的同学即可领取80乐豆奖励哦！看看你的订正进度条，是否已经满格了呢？（若9月份未有待订正作业，则无法参与此任务）</div>
					<c:if test="${ms_1!=null}">
						<div id="indicatorContainer">
							<div class="indicator_word">已订正<div id="j_m_1_d" data-i="${ms_1.data.bugfixTotal}">${ms_1.data.finishTotal}</div></div>
						</div>
						<div class="c-nation__getbtn-wrap">
							<div id="jM_1_Link" data-i="${ms_1.state}" class="c-nation__getbtn" style="cursor: pointer;">
								<c:if test="${ms_1.state==1}">立即订正</c:if>
								<c:if test="${ms_1.state==2}">领取奖励</c:if>
								<c:if test="${ms_1.state==3}">已领取</c:if>
							</div>
						</div>
						<c:if test="${(ms_1.data.bugfixTotal-ms_1.data.finishTotal)>0}">
							<div class="c-nation__getnote">还需订正<span>${ms_1.data.bugfixTotal-ms_1.data.finishTotal}</span>份作业，即可领取奖励</div>
						</c:if>
					</c:if>
				</div>
			</div>
			<div id="jMission_2" class="c-nation__listone c-nation__lists-change">
				<div class="c-nation__listone-header">
					<div class="c-nation__onetitle-wrap">
						<div class="c-nation__listone-title <c:if test="${ms_2!=null}">active</c:if>">
							<div class="c-nation__onetitle-word">消灭错题集</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-left"></li>
							<li class="c-nation__listone-item c-nation__listone-right"></li>
						</ul>
					</div>
					<div class="c-nation__onegrade-wrap">
						<div class="c-nation__listone-grade <c:if test="${ms_2!=null}">active</c:if>">
							<div class="c-nation__onegrade-word">ss</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-sdec"></li>
						</ul>
					</div>
				</div>
				<div class="c-nation__listone-con <c:if test="${ms_2!=null}">active</c:if> c-nation__listtwo-con">
					<div class="c-nation__list-state 
						<c:if test="${ms_2==null}">c-nation__list-notunlock</c:if>
						<c:if test="${ms_2.state>1}">c-nation__list-finished</c:if>
						<c:if test="${ms_2.state==1}">c-nation__list-ongoing</c:if>
					"></div>
					<div class="c-nation__listone-conword">解决错题是查漏补缺不可缺少的环节。现在进入我的作业，找到系统推送的9月各学科错题集，完成所有错题推送作业即可领取20乐豆奖励哦！ 各学科错题作业平均达到良好以上可以再次领取30乐豆奖励，达到优秀以上更可再次领取80乐豆奖励！（良好、优秀奖励不叠加，择高领取；若9月份系统未推送给您错题集，则无法参与此任务）</div>
					<div class="c-nation__list-progress">
						<div class="c-nation__progress-box">
							<div class="c-nation__progress-emblem <c:if test="${ms_2!=null&&ms_2.data.isDown}">active</c:if>">
								<div class="c-nation__progress-icon"></div>
							</div>
							<div class="c-nation__progress-target">
								消灭全部错题
							</div>
							<div class="c-nation__progress-btn <c:if test="${ms_2!=null&&ms_2.data.isDown}">active</c:if>">
								+<span>20</span>乐豆
							</div>
						</div>
						<div class="c-nation__progress-box c-nation__progress-boxmargin">
							<div class="c-nation__progress-emblem <c:if test="${ms_2!=null&&ms_2.data.state>0}">active</c:if>">
								<div class="c-nation__progress-icon c-nation__progress-better"></div>
							</div>
							<div class="c-nation__progress-target">
								全部学科获得良好
							</div>
							<div class="c-nation__progress-btn <c:if test="${ms_2!=null&&ms_2.data.state>0}">active</c:if>">
								+<span>30</span>乐豆
							</div>
						</div>
						<div class="c-nation__progress-box">
							<div class="c-nation__progress-emblem <c:if test="${ms_2!=null&&ms_2.data.state>1}">active</c:if>">
								<div class="c-nation__progress-icon c-nation__progress-best"></div>
							</div>
							<div class="c-nation__progress-target">
								全部学科获得优秀
							</div>
							<div class="c-nation__progress-btn <c:if test="${ms_2!=null&&ms_2.data.state>1}">active</c:if>">
								+<span>80</span>乐豆
							</div>
						</div>
					</div>
					<c:if test="${ms_2!=null}">
						<div id="jM_2_Link" data-i="${ms_2.state}" class="c-nation__getbtn c-nation__getbtn-change" style="cursor: pointer;">
							<c:if test="${ms_2.state==1}">立即消灭</c:if>
							<c:if test="${ms_2.state==2}">领取奖励</c:if>
							<c:if test="${ms_2.state==3}">已领取</c:if>
						</div>
					</c:if>
				</div>
			</div>
			<div id="jMission_3" class="c-nation__listone c-nation__lists-change">
				<div class="c-nation__listone-header">
					<div class="c-nation__onetitle-wrap">
						<div class="c-nation__listone-title <c:if test="${ms_3!=null}">active</c:if>">
							<div class="c-nation__onetitle-word">自主练习</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-left"></li>
							<li class="c-nation__listone-item c-nation__listone-right"></li>
						</ul>
					</div>
					<div class="c-nation__onegrade-wrap">
						<div class="c-nation__listone-grade <c:if test="${ms_3!=null}">active</c:if>">
							<div class="c-nation__onegrade-word">sss</div>
						</div>
						<ul class="c-nation__listone-decorate">
							<li class="c-nation__listone-item c-nation__listone-sdec"></li>
						</ul>
					</div>
				</div>
				<div class="c-nation__listone-con <c:if test="${ms_3!=null}">active</c:if>">
					<div class="c-nation__list-state 
						<c:if test="${ms_3==null}">c-nation__list-notunlock</c:if>
						<c:if test="${ms_3_size>0}">c-nation__list-ongoing</c:if>
					"></div>
					<div class="c-nation__listone-conword">假期不忘勤练习！ 活动期间每天来乐课网完成3次以上自主练习，且平均正确率达到60%以上，即可领取当日10乐豆奖励；达到80%以上可领取20乐豆奖励！乐豆奖励仅限当天领取，完成当日任务后别忘了领取乐豆哦。</div>
					<div class="c-nation__listthree-progress">
						<c:forEach var="i" begin="1" end="7">
							<c:set var="ms_3_active" value="false"/>
							<c:forEach var="j" items="${ms_3}">
								<c:if test="${j.day == i}">
									<c:set var="ms_3_active" value="${j.state == 3}"/>
								</c:if>
							</c:forEach>
							<div class="c-nation__threeprog-box <c:if test="${ms_3_active}">active</c:if>">10月${i}日</div>
						</c:forEach>
					</div>
					<c:if test="${ms_3!=null}">
						<div class="c-nation__listword-progress">
							<div class="c-nation__listword-wrap">今日完成自主练习：<span>${ms_3[ms_3_size-1].data.exerciseCount}</span></div>
							<div class="c-nation__listword-wrap">今日练习平均正确率：<span>${ms_3[ms_3_size-1].data.accuracy}%</span></div>
						</div>
						<div id="jM_3_Link" data-i="${ms_3[ms_3_size-1].state}" class="c-nation__getbtn" style="cursor: pointer;">
							<c:if test="${ms_3[ms_3_size-1].state==1}">立即前往</c:if>
							<c:if test="${ms_3[ms_3_size-1].state==2}">领取奖励</c:if>
							<c:if test="${ms_3[ms_3_size-1].state==3}">已领取</c:if>
						</div>
					</c:if>
				</div>
			</div>

		</div>
	</div>

	<!-- ***弹窗**** -->
	<!-- 中奖 -->
	<div class="c-nation__pop" style="display:none;">
		<div class="c-nation__pop-con">
			<h3 class="c-nation__pop-title">领取成功</h3>
			<div class="c-nation__pop-win">恭喜您获得<span>999</span>乐豆</div>
			<div class="c-nation__getbtn c-nation__getbtn-scale">确定</div>
		</div>
		<div class="c-nation__pop-bg"></div>
	</div>
	<!-- 蒙版 -->
	<div class="c-nation__day-mask" id="mask" style="display:none"></div>

</div>
<script>
	seajs.use("homework/activities/index");
</script>
</body>
</html>