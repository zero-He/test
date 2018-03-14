define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var WdatePicker = require('date');
	var koutils = require('common/knockout/koutils');
	var datepicker = require('common/knockout/bindings/datepicker');
	var http = require('common/base/http');
	var Service = require('core-business/service/basicInfoService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	function Query(params) {
		this.schoolStageId = ko.observable();
	};

	function ListVM(params, element) {
		var self = this;
		self.query = new Query({});
		var data = self.data= _.extend({}, params.data);
		self.inputStatisList = ko.observableArray();
		self.taskTotalAmount = ko.observable(0);
		self.checkTotalAmount = ko.observable(0);
		self.effectiveTotalAmount = ko.observable(0);
		self.schoolStages = ko.observableArray();//学段
		self.schoolStagesNototal = ko.observableArray();//学段
		self.schoolStageId = ko.observable();//学段
		self.subjects = ko.observableArray();
		self.time = ko.observable('week');
		//学段ID
		self.questionTotalAmount = ko.observable(0);
		self.workbookTotalAmount = ko.observable(0);
		self.coursewareTotalAmount = ko.observable('');
		self.paperTotalAmount = ko.observable(0);
		self.microcourseTotalAmount = ko.observable(0);
		self.beikepkgTotalAmount = ko.observable(0);
		self.questionAmount = ko.observableArray();
		self.workbookAmount = ko.observableArray();
		self.coursewareAmount = ko.observableArray();
		self.microcourseAmount = ko.observableArray();
		self.paperAmount = ko.observableArray();
		self.beikepkgAmount = ko.observableArray();
		self.questionStageAmount = ko.observableArray();
		self.workbookStageAmount = ko.observableArray();
		self.coursewareStageAmount = ko.observableArray();
		self.paperStageAmount = ko.observableArray();
		self.microcourseStageAmount = ko.observableArray();
		self.beikepkgStageAmount = ko.observableArray();
		self.totalAmount = ko.observableArray();
		
		self.albumTotal = ko.observable(0);//专辑总数
		
		self.loadSchoolStages();
		self.iniTotalAmount();
		self.iniTotalBeikeAmount();
		self.iniTotalPaperAmount();
		
		self.disposables = [];
		/*self.disposables.push(ko.computed(function() {
			//self.loadSubjects();
			self.iniStageAmount();
			self.iniBeikeStageAmount();
			self.iniPaperStageAmount();
		}));*/
		
		self.disposables.push(ko.computed(function() {
			self.loadSubjects(self.time());
		}));
		
		$('#aWeekList').click(function() {
			self.loadSubjects('week');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		$('#aMonthList').click(function() {
			self.loadSubjects('month');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		$('#aTotalList').click(function() {
			self.loadSubjects('total');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		
		self.initAlbumTotal();
		
	};
	
	ListVM.prototype.loadSchoolStages = function() {
		var self = this;
		Service.schoolStages().done(function(stages) {
			self.schoolStagesNototal(stages);
			var tem ={};
			tem.schoolStageName = '总计';
			tem.schoolStageId = 9999;
			stages.push(tem);
			self.schoolStages(stages);
			self.iniAmount();
			self.iniBeikeAmount();
			self.iniPaperAmount();
			
		}).fail(function() {
			self.schoolStages([]);
			self.schoolStagesNototal([]);
		});
	};
	
	ListVM.prototype.loadSubjects = function(time) {
		var self = this;
		if (self.schoolStageId != self.query.schoolStageId() || time != self.time()){
			var schoolStageId = self.schoolStageId = self.query.schoolStageId();
			self.time(time);
			self.refreshSubjects(schoolStageId, time);
		}
		
	};
	
	ListVM.prototype.refreshSubjects = function(schoolStageId, time) {
		var self = this;
		BasicInfoService.subjects(schoolStageId).done(function(subjects) {
			self.subjects(subjects);
			_.each(self.subjects() ||{}, function(r) {
				r.amount = 0;
			});
			self.iniStageAmount(time);
			self.iniBeikeStageAmount(time);
			self.iniPaperStageAmount(time);
		}).fail(function() {
			self.subjects([]);
		});
	}
	
	ListVM.prototype.iniTotalAmount = function() {
		var self = this;
		//var query = koutils.peekJS(self.query);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/findTotalAmount.htm',
			data : '',
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				self.questionTotalAmount(listjson.questionTotalAmount + '题');
				self.workbookTotalAmount(listjson.workbookTotalAmount + '本');
				var isl =  listjson.inputStatisList || [];
			}
		});
	};
	
	ListVM.prototype.iniAmount = function() {
		var self = this;
		//var query = koutils.peekJS(self.query);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/queryQuestionAmount.htm',
			data : '',
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				self.questionAmount(self.arrayProc(listjson.questionAmount));
				self.workbookAmount(self.arrayProc(listjson.workbookAmount));
				
				//var isl =  listjson.inputStatisList || [];
				/*self.inputStatisList(isl);
				self.query.startDate(listjson.startDate || '');
				self.query.endDate(listjson.endDate || '');
				
				var ta = 0;
				var ca = 0;
				var ea = 0;
				for(var i=0; i < isl.length; i++){
					ta += isl[i].taskAmount;
					ca += isl[i].checkAmount;
					ea += isl[i].effectiveAmount;
				}
				self.taskTotalAmount(ta);
				self.checkTotalAmount(ca);
				self.effectiveTotalAmount(ea);*/
			}
		});
	};
	
	ListVM.prototype.arrayProc = function(array) {
		var self = this;
		var totalAmount = 0;
		questionArray = [];
		_.each(self.schoolStages() ||{}, function(s) {
			if (s.schoolStageId != 9999){
				var length = questionArray.length;
				_.each(array ||{}, function(r) {
					if (r.schoolStageId == s.schoolStageId){
						totalAmount = totalAmount + r.inputAmount;
						questionArray.push(r);
						return true;
					}
				});
				
				if (length == questionArray.length){
					var tem = {};
					questionArray.push(tem);	
				}
			}
		});
		
		var tem2 = {};
		tem2.inputAmount = totalAmount;
		questionArray.push(tem2);
		
		return questionArray;
	}
	
	ListVM.prototype.arraySubjectProc = function(array) {
		var self = this;
		questionArray = [];
		_.each(self.subjects() ||{}, function(s) {
			var length = questionArray.length;
			_.each(array ||{}, function(r) {
				if (r.subjectId == s.subjectId && length == questionArray.length){
					questionArray.push(r);
					s.amount = s.amount + r.inputAmount;
					return true;
				}
			});
			
			if (length == questionArray.length){
				var tem = {};
				questionArray.push(tem);		
			}
		});
		
		var subjects = self.subjects();
		self.subjects(null);
		self.subjects(subjects);
		return questionArray;
	}
	
	ListVM.prototype.iniStageAmount = function(time) {
		var self = this;
		var a = self.query.schoolStageId();
		var param = self.dateProc(time)
		var query = koutils.peekJS(param);
		//var query = koutils.peekJS(self.query);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/queryStageQuestionAmount.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				/*
				_.each(self.subjects() ||{}, function(r) {
					r.amount = 0;
				});*/
				self.questionStageAmount(self.arraySubjectProc(listjson.questionStageAmount));
				self.workbookStageAmount(self.arraySubjectProc(listjson.workbookStageAmount));
				/*self.inputStatisList(isl);
				self.query.startDate(listjson.startDate || '');
				self.query.endDate(listjson.endDate || '');
				
				var ta = 0;
				var ca = 0;
				var ea = 0;
				for(var i=0; i < isl.length; i++){
					ta += isl[i].taskAmount;
					ca += isl[i].checkAmount;
					ea += isl[i].effectiveAmount;
				}
				self.taskTotalAmount(ta);
				self.checkTotalAmount(ca);
				self.effectiveTotalAmount(ea);*/
			}
		});
	};
	
	var DM = Leke.domain;
	var BEIKE_SVR = DM.beikeServerName;
	var PAP_SVR = DM.paperServerName;
	var API_TOTAL_BK = BEIKE_SVR + '/auth/common/courseware/jsonp/queryTotalBeikeDetails.htm';
	var API_BK = BEIKE_SVR + '/auth/common/courseware/jsonp/queryBeikeDetails.htm';
	var API_BK_STG = BEIKE_SVR + '/auth/common/courseware/jsonp/queryBeikeStageDetails.htm';
	var API_TOTAL_PAP = PAP_SVR + '/auth/common/paper/jsonp/queryTotalPaperDetails.htm';
	var API_PAP = PAP_SVR + '/auth/common/paper/jsonp/queryPaperDetails.htm';
	var API_PAP_STG = PAP_SVR + '/auth/common/paper/jsonp/queryPaperStageDetails.htm';
	
	ListVM.prototype.iniTotalBeikeAmount = function() {
		var self = this;
		var dataArray = {};
		var req = http.jsonp(API_TOTAL_BK,{dataJson: JSON.stringify(dataArray)});
		req.then(function(datas){
			self.coursewareTotalAmount(datas.coursewareTotalAmount + '份');
			self.microcourseTotalAmount(datas.microcourseTotalAmount + '份');
			self.beikepkgTotalAmount(datas.beikepkgTotalAmount + '份');
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniBeikeAmount = function() {
		var self = this;
		var dataArray = {};
		var req = http.jsonp(API_BK,{dataJson: JSON.stringify(dataArray)});
		req.then(function(datas){
			self.coursewareAmount(self.arrayProc(datas.coursewareAmount));
			self.microcourseAmount(self.arrayProc(datas.microcourseAmount));
			self.beikepkgAmount(self.arrayProc(datas.beikepkgAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniBeikeStageAmount = function(time) {
		//var dataArray = koutils.peekJS(self.query);
		var self = this;
		var data = {};
		//data.schoolStageId = self.query.schoolStageId();
		var param = self.dateProc(time);
		var req = http.jsonp(API_BK_STG,{dataJson: JSON.stringify(param)});
//		var req = http.jsonp(API_BK_STG,{dataJson: JSON.stringify(data)});
		req.then(function(datas){
			self.coursewareStageAmount(self.arraySubjectProc(datas.coursewareStageAmount));
			self.microcourseStageAmount(self.arraySubjectProc(datas.microcourseStageAmount));
			self.beikepkgStageAmount(self.arraySubjectProc(datas.beikepkgStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniTotalPaperAmount = function() {
		var self = this;
		var dataArray = {};
		var req = http.jsonp(API_TOTAL_PAP,{dataJson: JSON.stringify(dataArray)});
		req.then(function(datas){
			self.paperTotalAmount(datas.paperTotalAmount + '份');;
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniPaperAmount = function() {
		var self = this;
		var dataArray = {};
		var req = http.jsonp(API_PAP,{dataJson: JSON.stringify(dataArray)});
		req.then(function(datas){
			self.paperAmount(self.arrayProc(datas.paperAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniPaperStageAmount = function(time) {
		//var dataArray = koutils.peekJS(self.query);
		var self = this;
		var data = {};
		//data.schoolStageId = self.query.schoolStageId();
		var param = self.dateProc(time);
		var req = http.jsonp(API_PAP_STG,{dataJson: JSON.stringify(param)});
		//var req = http.jsonp(API_PAP_STG,{dataJson: JSON.stringify(data)});
		req.then(function(datas){
			self.paperStageAmount(self.arraySubjectProc(datas.paperStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.dateProc = function(time){
		var self = this;
		var data = {};
		data.schoolStageId = self.query.schoolStageId();
		if (time == 'month'){
			data.startDate = getMonthStartDate();
			data.endDate = getMonthEndDate();
		}else if(time == 'week'){
			data.startDate= getWeekStartDate();
			data.endDate = getWeekEndDate();
		}else{
			
		}
		
		return data;
	}
	
	var now = new Date(); //当前日期
	var nowDayOfWeek = now.getDay(); //今天本周的第几天
	var nowDay = now.getDate(); //当前日
	var nowMonth = now.getMonth(); //当前月
	var nowYear = now.getYear(); //当前年
	nowYear += (nowYear < 2000) ? 1900 : 0; //
	var lastMonthDate = new Date(); //上月日期
	
	function getWeekStartDate() { 
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek); 
		return formatDate(weekStartDate); 
	}

	function getWeekEndDate() { 
		var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)); 
		return formatDate(weekEndDate); 
	} 

	function getMonthStartDate(){ 
		var monthStartDate = new Date(nowYear, nowMonth, 1); 
		return formatDate(monthStartDate); 
	} 
	
	function getMonthEndDate(){ 
		var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth)); 
		return formatDate(monthEndDate); 
	} 
	
	function getMonthDays(myMonth) {
		var monthStartDate = new Date(nowYear, myMonth, 1);
		var monthEndDate = new Date(nowYear, myMonth + 1, 1);
		var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
		return days;
	}
	
	function formatDate(date) {
		var myyear = date.getFullYear();
		var mymonth = date.getMonth() + 1;
		var myweekday = date.getDate();
		if (mymonth < 10) {
			mymonth = "0" + mymonth;
		}
		if (myweekday < 10) {
			myweekday = "0" + myweekday;
		}
		return (myyear + "-" + mymonth + "-" + myweekday);
	}
	/**
	 * 查看详细
	 */
	/*ListVM.prototype.viewUserCheckAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/check/userCheckAmount.htm?userId=' + data.userId + '&userName=' + data.userName + '&startDate=' + self.query.startDate() + '&endDate=' + self.query.endDate();
	};*/
	
	ListVM.prototype.initAlbumTotal = function(){
		var self = this;
		var req = http.post(Leke.ctx + '/auth/admin/questionStatis/ajax/getAlbumTotal.htm',{});
		req.then(function(datas){
			self.albumTotal((datas.albumTotal || 0) + '个');
		},function(msg){
			utils.Notice.alert(msg || '加载专辑总数错误！');
		});
	};
	
	ListVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('que-home-amount-list', {
	    template: require('./que-home-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});