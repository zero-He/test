define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var $ = require('jquery');
	var http = require('common/base/http');
	var LekeDate = require('common/base/date');
	var LekeWeek = require('common/base/week');
	var utils = require('utils');
	var ROLES = require('core-business/roles');
	var SELLER = 112;//客户经理
	var CourseTable = require('./model/CourseTable');
	
	require('common/knockout/bindings/datetext');
	require('common/ui/ui-address/ui-market');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	
	var SCHOOL_NATURES = [{
		schoolNature: 1,
		schoolNatureName: '基础教育'
	}, {
		schoolNature: 2,
		schoolNatureName: '培训机构'
	}, {
		schoolNature: 3,
		schoolNatureName: '个人入驻'
	}];
	
	var FMT_DATE = 'yyyy-MM-dd';
	var FMT_DAY = 'yyyyMMdd';
	var currentWeek = new LekeWeek();
	
	function dayToDate(day) {
		return LekeDate.parse(day, FMT_DAY).format(FMT_DATE);
	}
	
	function Query(options) {
		var self = this;
		var ops = _.extend({}, options);
		self.week = ko.observable(currentWeek);
		self.area = ko.observable(ops.area);
		self.school = ko.observable(ops.school);
		self.schoolNature = ko.observable();
		self.sellerSchoolId = ko.observable();
		self.isSeller = Leke.user.currentRoleId === SELLER;
		self.weekOfYear = ko.pureComputed(function() {
			return self.week().weekOfYear();
		});
		self.startDate = ko.pureComputed(function() {
			return self.week().startDay().format(FMT_DATE);
		});
		self.endDate = ko.pureComputed(function() {
			return self.week().endDay().format(FMT_DATE);
		});
		self.isCurrentWeek = ko.pureComputed(function() {
			return self.week().compareTo(currentWeek) === 0;
		});
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		var week = self.week.peek();
		result.startDay = week.startDay().format(FMT_DAY);
		result.endDay = week.endDay().format(FMT_DAY);
		if(self.isSeller && self.sellerSchoolId()){
			result.schoolIds = [self.sellerSchoolId()];
			return result;
		}
		var sch = self.school.peek();
		if(sch) {
			result.schoolIds = [sch.schoolId];
		} else {
			var area = self.area.peek();
			if(area) {
				result.area = area;
			}
		}
		var nature = self.schoolNature.peek();
		if(nature) {
			result.schoolNature = nature;
		}
		return result;
	};
	
	function TableVM(params, element) {
		var self = this;
		self.$el = $(element);
		
		
		self.sellerSchools = ko.observableArray();
		
		self.schoolNatures = SCHOOL_NATURES;
		
		var ops = self.options = _.extend({}, TableVM.defaults, params.options);
		self.query = new Query(params.query);
		self.coursetable = ko.observable();
		
		self.$sbox = self.$el.find('.j-search-box');
		
		if(!ops.showArea && !ops.showSchool) {
			// hide search box
			self.$sbox.hide();
		} else {
			if(ops.showArea) {
				self.$a = self.$sbox.find('.j-area-select');
				self.$a.market({
					onSelect : function(obj) {
						self.query.area(obj);
					}
				});
			} else {
				self.$sbox.find('.j-area-label').hide();
			}
			
			if(ops.showSchool) {
				self.$sch = self.$sbox.find('.j-school-select');
				self.$sch.autocomplete({
					url: Leke.ctx + '/auth/common/data/querySchoolLike.htm',
					nameKey: 'schoolName',
					onChange: function(school) {
						self.query.school(school);
					}
				});
			}
		}
		
		if(self.query.isSeller) {
			self.loadSellerSchools();
		}
		self.load();
	}
	
	TableVM.prototype.toPrevWeek = function() {
		var self = this, q = self.query;
		q.week(q.week().prev());
		self.load();
	};
	
	TableVM.prototype.toNextWeek = function() {
		var self = this, q = self.query;
		q.week(q.week().next());
		self.load();
	};
	
	TableVM.prototype.toCurrentWeek = function() {
		var self = this, q = self.query;
		q.week(currentWeek);
		self.load();
	};
	
	TableVM.prototype.load = _.throttle(function() {
		var self = this, ops = self.options;
		var week = self.query.week();
		var query = self.query.toJS();
		var reqDailys = ops.dailyUrl ? http.postJSON(ops.dailyUrl, query) : [{stats:[]}];
		var reqItems = http.postJSON(ops.itemsUrl, query);
		$.when(reqDailys, reqItems).then(function(d1, d2) {
			self.coursetable(new CourseTable(week, d1[0].stats, d2[0].items));

			// 智能浮动
			smartPosition.init({
			    'elem':'.table-tr-title',
			    'fixedShow':'table'
			});
		}, function() {
			utils.Notice.alert('数据获取失败');
			self.coursetable(new CourseTable(week));
		});
	}, 500);
	
	TableVM.prototype.loadSellerSchools =function(){
		var self = this;
		var req =  http.postJSON( Leke.ctx + '/auth/common/data/querySellerSchools.htm');
		req.then(function(rsp){
			self.sellerSchools(rsp.schools || []);
		});
	};
	
	var TECH_SUPPORT_HOME = Leke.ctx + '/auth/technicalSupport/course/technicalIndex.htm';
	
	TableVM.prototype.toDayCourseHome = function(item) {
		var self = this;
		if(item) {
			if(Leke.user.currentRoleId == ROLES.TECHNICALSUPPORT) {
				window.open(TECH_SUPPORT_HOME + '?date=' + dayToDate(item.day));
			}
		}
	};
	
	var SCH_DAY_COURSE_URL = Leke.domain.lessonServerName + '/auth/common/schedule/daily.htm';
	
	TableVM.prototype.toSchoolDayCourse = function(item) {
		if(item) {
			window.open(SCH_DAY_COURSE_URL + '?schoolId=' + item.schoolId + '&nowDate=' + dayToDate(item.day));
		}
	};
	
	TableVM.prototype.dispose = function() {
		var self = this;
//		if(self.$a.length) {
//			self.$a.market('destroy');
//		}
		self.$sch && self.$sch.autocomplete('destroy');
	};
	
	var DIR_TS = Leke.ctx + '/auth/technicalSupport/course/';
	TableVM.defaults = {
		showArea: true,
		showSchool: true,
		showStats: true,
		dailyUrl: DIR_TS + 'dailyCourseStats.htm',
		itemsUrl: DIR_TS + 'courseTables.htm'
	};
	
	ko.components.register('coursetable-week', {
	    template: require('./coursetable-week.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TableVM(params, componentInfo.element);
	    	}
	    }
	});


	// 智能浮动
    var smartPosition={
        init:function(opt){
            this.allTitle=$(opt.elem);
            this.obj=this.allTitle.eq(0);
            this.amObj=this.allTitle.eq(1);
            this.pmObj=this.allTitle.eq(2);
            this.eveningObj=this.allTitle.eq(3);
            /*获得绝对定位的 display属性*/
            this.fixedShow=opt.fixedShow;  
            this.dateTop = this.obj.offset().top;
            /*获取布局定位的高度*/
            this.amTop = this.amObj.offset().top;
            this.pmTop = this.pmObj.offset().top;
            this.eveningTop = this.eveningObj.offset().top;
            /*获取各小计的高度*/
            this.objHeight=this.obj.outerHeight();
            this.amObjHeight=this.amObj.outerHeight();
            this.pmObjHeight=this.pmObj.outerHeight();
            this.eveningObjHeight=this.eveningObj.outerHeight();
            /*获取布局的定位属性*/
            this.temp = this.obj.css("position");    
            this.oldShow=this.obj.css('display');
            this.winScroll();
        },
        winScroll:function(){
            var self=this;
            var totalData=false,totalDataFix=false,amData=false,pmData=false,eveningData=false;
            $(window).scroll(function() {
                self.scrolls = $(this).scrollTop();
                if (self.scrolls > self.dateTop) {
                    if(!totalDataFix){
                        self.fixedPosition(self.obj,0);
                        totalDataFix=true;
                        totalData=false;
                    }
                }else {
                    if(!totalData){
                        self.showOldPosition(self.obj);
                        totalDataFix=false;
                        totalData=true;
                    }
                }
                /*早上的小节*/
                if(self.scrolls >= (self.amTop-self.objHeight) && self.scrolls< self.pmTop-(2*self.amObjHeight+self.objHeight)){
                    if(!amData){
                        self.fixedPosition(self.amObj,self.objHeight);
                        self.showOldPosition(self.pmObj);
                        amData=true;
                        pmData=false;
                        eveningData=false;
                    }
                }
                /*下午的小节*/
                else if(self.scrolls >= self.pmTop-(2*self.amObjHeight+self.objHeight) && self.scrolls< self.eveningTop-(2*self.pmObjHeight+self.objHeight)){
                    if(!pmData){
                        self.showOldPosition(self.amObj);
                        self.fixedPosition(self.pmObj,self.objHeight);
                        self.showOldPosition(self.eveningObj);
                        pmData=true;
                        amData=false;
                        eveningData=false;
                    }
                }
                /*晚上的小节*/
                else if(self.scrolls >= self.pmTop-(2*self.pmObjHeight+self.objHeight) ){
                    if(!eveningData){
                        self.showOldPosition(self.pmObj);
                        self.fixedPosition(self.eveningObj,self.objHeight);
                        eveningData=true;
                        amData=false;
                        pmData=false;
                    }
                }
                else{
                    self.showOldPosition(self.amObj);
                    self.showOldPosition(self.pmObj);
                    self.showOldPosition(self.eveningObj);
                    amData=false;
                    pmData=false;
                    eveningData=false;
                }
            });
        },
        fixedPosition:function($obj,height){
            $obj.css({
                position: "fixed",
                top: (0+height),
                'z-index':2,
                display:this.fixedShow ||'block'
            });
        },
        showOldPosition:function($obj){
            $obj.css({
                position: this.temp,
                top:'none',
                'z-index':1,
                display:this.oldShow
            });
            if(this.temp=='absolute'){
                 $obj.css({
                    top: this.dateTop,
                    'z-index':1,
                    display:this.oldShow
                });
            }
        }
    };
    
});