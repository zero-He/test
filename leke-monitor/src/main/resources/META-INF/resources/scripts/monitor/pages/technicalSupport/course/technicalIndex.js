define(function(require,exports,module){
	var $ = require('jquery');
	var ko = require("knockout");
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	var Mustache = require('mustache');
	var LekeDate = require('common/base/date');
	var ROLES = require('core-business/roles');
	var ZeroClipboard = require('common/ui/ui-zeroclipboard/ZeroClipboard');
	var Base64 = require("base64");
	var baseService = require('core-business/service/basicInfoService');
	require('monitor/component/technical-support-total');
	require('common/knockout/bindings/datetext');
	require('common/ui/ui-address/ui-market');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	var WdatePicker = require('date');
	var technicalSupport = {
		init: function() {
			var self = this;
			self.subjects = ko.observableArray();
			self.subjectName = ko.observable();
			this.bindEvents();
			this.loadData();
		},
		loadData: function(){
			var self = this;
			
			baseService.subjects().done(function(subjects){
				self.subjects(subjects);
			});
			self.getData();
		},
		bindEvents: function(){
			var self=this;
			self.bindAreSchool();
			//展开收齐事件
			$('.j-btn').on('click', 'i', function () {
				var $this = $(this),
				$time = $this.prev(),
				$conItem = $this.parents('tr').next('tr');
				if ($conItem.is(':visible')) {
					$this.removeClass('up').addClass('down');
					$conItem.hide();
					$time.text($conItem.find('.time').text());
				} else {
					$this.removeClass('down').addClass('up');
					$conItem.show();
					$time.text('');
				}
			});
			//前一天
			$('#dayBefore').on('click',function(){
				$('#today').removeClass('cur');
				self.addDay(-1);
			});
			//后一天
			$('#dayAfter').on('click',function(){
				$('#today').removeClass('cur');
				self.addDay(1);
			});
			//今天
			$('#today').on('click',function(){
				$('#today').addClass('cur');
				self.setToday();
			});
			
			//gjx 选择日期
			$('#showDate').on('click',function(){
				WdatePicker({
//					dateFmt:'yyyy-MM-dd',
					readOnly:true,
					onpicked:function(){
						self.nowDate._d=new Date($(this).text());
						self.getData();
						return true;
					}
				});
				
			});
			//查询
			$('#searchBut').on('click',function(){
				self.getData();
			});
		},
		getData: function(){
			var self=this;
			$('#showDate').html(LekeDate.of(self.nowDate).format());
			var query = self.toJS();
			//加载课表
			http.postJSON(ctx+'/auth/technicalSupport/course/findCourseSingleByDay.htm', query).then(function(data){
					data.items=_.filter(data.items,function(n){
						return !n.isDeleted;
					});
					self.insertHtml(data.items);
					self.readyCopy();
			});
		},
		toJS: function(){
			var self = this;
			var query = self.query;
			var result = {};
			var sch = query.school;
			if(sch) {
				query.schoolId = sch.schoolId;
				result.schoolIds = [sch.schoolId];
			} else {
				var area = query.area;
				if(area) {
					result.area = area;
				}
			}
			var schoolNature = $('#schoolNature').val();
			if(schoolNature != ''){
				result.schoolNature =  schoolNature;
			}
			var intDay = self.nowDate.format().replace('-','').replace('-','');
			result.startDay = intDay;
			result.endDay = intDay;
			result.schoolNature = $('#schoolNature').val();
			result.subjectName = self.subjectName();
			result.teacherName = $('#teacherName').val();
			return result;
		},
		nowDate: LekeDate.of($('#myDate').val()),
		addDay: function(num){
			var self=this;
			self.nowDate=self.nowDate.add(num, 'd');
			self.getData();
		},
		setToday: function(){
			var self=this;
			self.nowDate=LekeDate.of(window.currentSystemTime);
			self.getData();
		},
		insertHtml: function(items){
			//替换字符插入html
			var self = this;
			var classState = $('#classState').val();
			var course = {am:{dataList:[]},pm:{dataList:[]},nt:{dataList:[]}};
			_.each(items,function(item){
				if(item.schoolName != null && item.schoolName.length>10){
					item.schoolNameStr = item.schoolName.substring(0,10)+'...';
				}else{
					item.schoolNameStr = item.schoolName;
				}
				item.state = '';//状态
				item.stateStr = '';//状态格式化
				item.startTimeStr = LekeDate.format(item.startTime,'HH:mm');//格式化时间
				item.endTimeStr = LekeDate.format(item.endTime,'HH:mm');//格式化时间
				self.calculateClassTime(item);
				if(classState == '' || classState == item.state){
					//格式化在线
					if(item.isOnline){
						item.isOnlineStr = 'cur';
					}else{
						item.isOnlineStr = '';
					}
					if(item.period == 'AM'){	
						course.am.dataList.push(item);
					}else if (item.period == 'PM'){	
						course.pm.dataList.push(item);
					}else if (item.period == 'NT'){
						course.nt.dataList.push(item);
					}
				}
			});
			//上午
			course.am.dataList = self.group(course.am.dataList);
			var sOutput = Mustache.render($('#jSuListTpl').val(),course.am);
			$('#amTbody').html(sOutput);
			//下午
			course.pm.dataList = self.group(course.pm.dataList);
			sOutput = Mustache.render($('#jSuListTpl').val(),course.pm);
			$('#pmTbody').html(sOutput);
			//晚上
			course.nt.dataList = self.group(course.nt.dataList);
			sOutput = Mustache.render($('#jSuListTpl').val(),course.nt);
			$('#ntTbody').html(sOutput);
		},
		group: function(dataList){
			var gList = _.groupBy(dataList||[],'schoolId');
			var keys = _.pluck(dataList||[],'schoolId');
			var maps = _.uniq(keys);
			var rList = [];
			_.each(maps,function(n){
				 var sList = _.sortBy(gList[n],'startTime');
				 rList = _.union(rList, sList);
			});
			return rList;
		},
		calculateClassTime: function(item){
			var self=this;
			var startTime=new Date(item.startTime);
			var endTime=new Date(item.endTime);
			var currentTime=new Date(window.currentSystemTime);
			var sumMs=currentTime.getTime()-endTime.getTime();//毫秒
			if(item.isEnded || sumMs>900000){
				//课堂结束且超过15分钟 已结束
				item.state=-1;
				item.stateStr="已结束";
				item.stateClass="done";
			}else if(currentTime>=startTime){
				//上课中
				item.state=0;
				item.stateStr="上课中";
				item.stateClass="";
			}else{
				//未开始
				item.state=1;
				var residue=startTime.getTime()-currentTime.getTime();
				var seconds=Math.floor(residue/1000);//秒
				var minutes=Math.floor(seconds/60);//分钟
				var hours=Math.floor(minutes/60);//小时
				var days=Math.floor(hours/24);//天
				var months=Math.floor(days/30);//月
				var record=0;
				if(months>0){
					record++;
					item.stateStr=months+'月 ';
				}
				if(days>0){
					record++;
					days=days-months*30;
					item.stateStr=item.stateStr+days+'天 ';
				}
				if(hours>0&&record<2){
					record++;
					hours=hours-days*24;
					item.stateStr=item.stateStr+hours+'小时 ';
				}
				if(minutes>0&&record<2){
					record++;
					minutes=minutes-hours*60;
					item.stateStr=item.stateStr+minutes+'分 ';
				}
				if(item.stateStr==''){
					item.stateStr='1分钟';
				}
				item.stateStr=item.stateStr+ "后开始";
				item.stateClass="";
				var hour=Math.floor(residue/1000/60/60);
				if(hour<=24){
					item.disabled=true;
				}else{
					item.disabled=false;
				}
			}
			item.desktopUrl=self.calcEnterCourse(item);
		},
		calcEnterCourse: function(item){
			var host = $('#host').val();
			var beike = $('#beike').val();
			var ticket = $('#ticket').val();
			var roleId=Leke.user.currentRoleId;
			var userId=Leke.user.userId;
			var courseType = item.courseType;
			var desktopUrl = Leke.domain.lessonServerName + '/auth/common/tutor/videoFlex.htm?courseSingleId=' + item.csId + '&roleId=' + roleId + '&userId=' + userId +'&ticket=' + ticket + '&courseType=' + courseType;
			if(item.state===-1){
				if(item.isRecord){
					var html = '<a class="link" id="hrefreplayClass'+item.csId+'" href="'+Leke.domain.lessonServerName+'/api/w/tutor/replay.htm?courseSingleId='+item.csId+'&userId='+userId+'&roleId='+roleId+'" target="_blank">查看录像</a> ';
					if (roleId == 110) {
						var udata = {};
						udata.courseSingleId = item.csId;
						udata.date = new Date();
						html += '<br/><span class="j-copy" style="cursor: pointer;" data-clipboard-text="' + Leke.domain.lessonServerName
						+ '/unauth/lesson/replay.htm?udata=' + Base64.enc.Base64.stringify(Base64.enc.Utf8.parse(JSON.stringify(udata))) + '">复制链接</span> ';
					}
					return html;
				}else{
					return '无录像';
				}
			}else if(item.state===0){
				return '<a class="link" id="hrefdesktop'+item.csId+'" target="_blank" href="'+desktopUrl+'">进入课堂</a>';
			}else if(item.state===1){
				if(item.disabled){
					return '<a class="link" id="hrefdesktop'+item.csId+'" target="_blank" href="'+desktopUrl+'">进入课堂</a>';
				}else{
					return'<em class="done">进入课堂</em>';
				}
			}
		},
		query:{},
		bindAreSchool: function(){
			var self = this;
			self.$sbox = $('.j-search-box');
			var roleId = Leke.user.currentRoleId;
			self.isPresident=roleId == ROLES.PRESIDENT;//是否是校长
			if(roleId == ROLES.SELLER||self.isPresident) {
				// hide search box
				self.$sbox.hide();
			} else {
				// init area select
				self.$a = self.$sbox.find('.j-area-select');
				if(roleId != ROLES.EDUCATIONDIRECTOR) { // 除教育局长外
					self.$a.market({
						onSelect : function(obj) {
							self.query.area = obj ;
						}
					});
				} else {
					self.$sbox.find('.j-area-label').hide();
				}
				
				// init school select
				self.$sch = self.$sbox.find('.j-school-select');
				self.$sch.autocomplete({
					url: Leke.ctx + '/auth/common/data/querySchoolLike.htm',
					nameKey: 'schoolName',
					onChange: function(school) {
						self.query.school = school;
					}
				});
			}
		},
		readyCopy : function() {
			var clip = new ZeroClipboard(
					$('.j-copy'),
					{
						moviePath : '/scripts/common/ui/ui-zeroclipboard/ZeroClipboard.swf'
					});
			// 复制内容到剪贴板成功后的操作
			clip.on('complete', function(client, args) {
				utils.Notice.alert('复制链接成功');
			});
		}
	};
	technicalSupport.init();
	ko.applyBindings(technicalSupport);
});