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
	var userPrefService = require('core-business/service/userPrefService');
	
	function Query(params) {
		this.schoolStageId = ko.observable();
		this.subjectId = ko.observable();
		this.schoolStageName = ko.observable();
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
		self.schoolStageId = ko.observable();//学段
		self.subjects = ko.observableArray();
		self.schoolStageName = ko.observable();
		self.subjectName = ko.observable();
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
		
		self.totalAmount = ko.observable(0);
		self.groupname = ko.observable();
		self.stagename = ko.observable();
		
		self.questionResearcherAmount = ko.observableArray();
		self.workbookResearcherAmount = ko.observableArray();
		self.coursewareResearcherAmount = ko.observableArray();
		self.paperResearcherAmount = ko.observableArray();
		self.microcourseResearcherAmount = ko.observableArray();
		self.beikepkgResearcherAmount = ko.observableArray();
		
		self.albumTotal = ko.observable(0);//专辑总数
		
		self.researcher = ko.observableArray();

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
			self.loadSubjects();
		}));
		
		self.loadSchoolStages();
		
		$('#aWeekList').click(function() {
			//self.loadSubjectMaterials();
			self.loadResearcher('week');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		$('#aMonthList').click(function() {
			//self.loadKnowledgeList();
			self.loadResearcher('month');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		$('#aTotalList').click(function() {
			//self.loadOfficialTagList();
			self.loadResearcher('total');
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		self._init();
	};
	
	ListVM.prototype._init = function(){
		var self = this;
		self.initAlbumTotal();
	};
	
	ListVM.prototype.loadSubjects = function() {
		var self = this;
		var schoolStageId = self.query.schoolStageId();
		if (self.schoolStages().length != 0 && self.schoolStageId != self.query.schoolStageId()){
			self.schoolStageId = self.query.schoolStageId();
			_.each(self.schoolStages() ||{}, function(r) {
				if (r.schoolStageId == schoolStageId){
					self.query.schoolStageName(r.schoolStageName);
					self.totalAmount(0);
					self.subjects(r.subjects);
					_.each(self.subjects() ||{}, function(s) {
						s.totalAmount = 0;
					});
					self.iniStageAmount();
					self.iniBeikeStageAmount();
					self.iniPaperStageAmount();
					//需求要求一个学科
					self.query.subjectId(r.subjects[0].subjectId);
					self.groupname(self.schoolStages()[0].schoolStageName + r.subjects[0].subjectName +"组数据统计");
					self.stagename(self.schoolStages()[0].schoolStageName + r.subjects[0].subjectName +"学段实时数据");
				}
			});
		}
	};
	
	ListVM.prototype.loadSchoolStages = function() {
		var self = this;
		//var schoolStageId = self.query.schoolStageId();
		userPrefService.userPref().then(function(userPref){
			self.schoolStages(userPref.authority.schoolStages)
			//self.schoolStageId = userPref.authority.schoolStages;
			//暂加
			schoolStageId = self.schoolStages()[0].schoolStageId;
			self.query.schoolStageId(schoolStageId);
			self.query.schoolStageName(self.schoolStages()[0].schoolStageName);
			
			_.each(self.schoolStages() ||{}, function(r) {
				if (r.schoolStageId == schoolStageId){
					self.totalAmount(0);
					self.subjects(r.subjects);
					
					self.iniStageAmount();
					self.iniBeikeStageAmount();
					self.iniPaperStageAmount();
					//需求要求一个学科
					self.query.subjectId(r.subjects[0].subjectId);
					self.loadResearcher('week');
					self.groupname(self.schoolStages()[0].schoolStageName + r.subjects[0].subjectName +"组数据统计");
					self.stagename(self.schoolStages()[0].schoolStageName + r.subjects[0].subjectName +"学段实时数据");
				}
			});
		})
	};
	
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
	
	ListVM.prototype.initAlbumTotal = function(){
		var self = this;
		var req = http.post(Leke.ctx + '/auth/admin/questionStatis/ajax/getAlbumTotal.htm',{});
		req.then(function(datas){
			self.albumTotal((datas.albumTotal || 0) + '个');
		},function(msg){
			utils.Notice.alert(msg || '加载专辑总数错误！');
		});
	};
	
	ListVM.prototype.arrayProc = function(array) {
		var self = this;
		questionArray = [];
		_.each(self.schoolStages() ||{}, function(s) {
			var length = questionArray.length;
			_.each(array ||{}, function(r) {
				if (r.schoolStageId == s.schoolStageId){
					questionArray.push(r);
					return false;
				}
			});
			
			if (length == questionArray.length){
				var tem = {};
				questionArray.push(tem);	
			}
		});
		return questionArray;
	}
	
	ListVM.prototype.arraySubjectProc = function(array) {
		var self = this;
		questionArray = [];
		_.each(self.subjects() ||{}, function(s) {
			var length = questionArray.length;
			_.each(array ||{}, function(r) {
				if (r.subjectId == s.subjectId){
					if (length == questionArray.length){
						//self.totalAmount(self.totalAmount() + r.inputAmount);
						questionArray.push(r);
						if (s.totalAmount){
							s.totalAmount = r.inputAmount + s.totalAmount;
						}else{
							s.totalAmount = r.inputAmount;
						}
						
						return false;
					}
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
	
	ListVM.prototype.arrayResearcherProc = function(array) {
		var self = this;
		questionArray = [];
		_.each(self.researcher() ||{}, function(s) {
			var length = questionArray.length;
			_.each(array ||{}, function(r) {
				if (r.userId == s.id){
					if (length == questionArray.length){
						questionArray.push(r);
						if (s.amount){
							s.amount = s.amount + r.inputAmount;
						}else{
							s.amount = r.inputAmount;
						}
						
					}
				}
			});
			
			if (length == questionArray.length){
				var tem = {};
				questionArray.push(tem);	
			}
		});
		
		var researcher = self.researcher();	
		self.researcher(null);
		self.researcher(researcher);
		return questionArray;
	}
		
	ListVM.prototype.loadResearcher = function(time) {
		var self = this;
		//var query = {};
		var query  = self.dateProc(time);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/user/ajax/findQuestionUserList.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var dataArray = [];
				/*
				_.each(listjson.inputStatisList ||{}, function(r) {
						dataArray.push(r.userId);
				});*/
				
				self.researcher(listjson.userList);
				self.iniResearcherAmount(time);
				self.iniBeikeResearcherAmount(time);
				self.iniPaperResearcherAmount(time);
			}
		});
	};	
		
	ListVM.prototype.iniStageAmount = function() {
		var self = this;
		var tem = self.query.schoolStageId();
		var query = koutils.peekJS(self.query);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/queryStageQuestionAmount.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				self.questionStageAmount(self.arraySubjectProc(listjson.questionStageAmount));
				self.workbookStageAmount(self.arraySubjectProc(listjson.workbookStageAmount));
			}
		});
	};
	
		
	ListVM.prototype.iniResearcherAmount = function(time) {
		var self = this;
		var param = self.dateProc(time)
		var query = koutils.peekJS(param);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/queryResearcherQuestionAmount.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				/*
				_.each(self.researcher() ||{}, function(r) {
					r.amount = 0;
				});*/
				self.questionResearcherAmount(self.arrayResearcherProc(listjson.questionStageAmount));
				self.workbookResearcherAmount(self.arrayResearcherProc(listjson.workbookStageAmount));
			}
		});
	};
	
	var DM = Leke.domain;
	var BEIKE_SVR = DM.beikeServerName;
	var PAP_SVR = DM.paperServerName;
	var API_TOTAL_BK = BEIKE_SVR + '/auth/common/courseware/jsonp/queryTotalBeikeDetails.htm';
	var API_BK = BEIKE_SVR + '/auth/common/courseware/jsonp/queryBeikeDetails.htm';
	var API_BK_STG = BEIKE_SVR + '/auth/common/courseware/jsonp/queryBeikeStageDetails.htm';
	var API_BK_RSC = BEIKE_SVR + '/auth/common/courseware/jsonp/queryResearcherBeikeAmount.htm';
	var API_TOTAL_PAP = PAP_SVR + '/auth/common/paper/jsonp/queryTotalPaperDetails.htm';
	var API_PAP = PAP_SVR + '/auth/common/paper/jsonp/queryPaperDetails.htm';
	var API_PAP_STG = PAP_SVR + '/auth/common/paper/jsonp/queryPaperStageDetails.htm';
	var API_PAP_RSC = PAP_SVR + '/auth/common/paper/jsonp/queryResearcherPaperAmount.htm';
	
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
	
	ListVM.prototype.iniBeikeStageAmount = function() {
		//var dataArray = koutils.peekJS(self.query);
		var self = this;
		var data = {};
		data.schoolStageId = self.query.schoolStageId();
		var req = http.jsonp(API_BK_STG,{dataJson: JSON.stringify(data)});
		req.then(function(datas){
			self.coursewareStageAmount(self.arraySubjectProc(datas.coursewareStageAmount));
			self.microcourseStageAmount(self.arraySubjectProc(datas.microcourseStageAmount));
			self.beikepkgStageAmount(self.arraySubjectProc(datas.beikepkgStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniBeikeResearcherAmount = function(time) {
		var self = this;
		var data = {};
		//data.schoolStageId = self.query.schoolStageId();
		//data.subjectId = self.query.subjectId();
		var param = self.dateProc(time);
		var req = http.jsonp(API_BK_RSC,{dataJson: JSON.stringify(param)});
		req.then(function(datas){
			var a = datas;
			self.coursewareResearcherAmount(self.arrayResearcherProc(datas.coursewareStageAmount));
			self.microcourseResearcherAmount(self.arrayResearcherProc(datas.microcourseStageAmount));
			self.beikepkgResearcherAmount(self.arrayResearcherProc(datas.beikepkgStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	};
	
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
	
	ListVM.prototype.dateProc = function(time){
		var self = this;
		var data = {};
		data.schoolStageId = self.query.schoolStageId();
		data.subjectId = self.query.subjectId();
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
	
	ListVM.prototype.iniPaperStageAmount = function() {
		//var dataArray = koutils.peekJS(self.query);
		var self = this;
		var data = {};
		data.schoolStageId = self.query.schoolStageId();
		var req = http.jsonp(API_PAP_STG,{dataJson: JSON.stringify(data)});
		req.then(function(datas){
			self.paperStageAmount(self.arraySubjectProc(datas.paperStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	}
	
	ListVM.prototype.iniPaperResearcherAmount = function(time) {
		var self = this;
		var data = {};
		//data.schoolStageId = self.query.schoolStageId();
		//data.subjectId = self.query.subjectId();
		var param = self.dateProc(time);
		var req = http.jsonp(API_PAP_RSC,{dataJson: JSON.stringify(param)});
		req.then(function(datas){
			self.paperResearcherAmount(self.arrayResearcherProc(datas.paperStageAmount));
		},function(msg){
			utils.Notice.alert(msg || '请求失败');
		});
	};
	
	
	/**
	 * 查看详细
	 */
	/*ListVM.prototype.viewUserCheckAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/check/userCheckAmount.htm?userId=' + data.userId + '&userName=' + data.userName + '&startDate=' + self.query.startDate() + '&endDate=' + self.query.endDate();
	};*/
	
	ListVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('que-researcher-home-amount-list', {
	    template: require('./que-researcher-home-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});