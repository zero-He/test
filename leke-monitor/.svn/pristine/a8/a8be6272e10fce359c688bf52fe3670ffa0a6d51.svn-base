define(function(require,exports,module){
	var $ = require('jquery');
	var ko=require('knockout');
	var _ = require('underscore');
	function Total(params, element){
		var self=this;
		self.currentSchoolCount=ko.observable(0);
		self.dailySchoolCount=ko.observable(0);
		self.currentLessonCount=ko.observable(0);
		self.dailyLessonCount=ko.observable(0);
		self.currentActualStuCount=ko.observable(0);
		self.dailyExpectStuCount=ko.observable(0);
		self.dailyActualStuTimes=ko.observable(0);
		self.dailyExpectStuTimes=ko.observable(0);
		var ops=_.extend(params.options);
		self.postUrl=ops.postUrl;
		self.load();
	};
	Total.prototype.load=function(){
		var self=this;
		$.ajax({
			type:'post',
			url:ctx+self.postUrl,
			dataType:'json',
			success:function(data){
				var stat=data.datas.stat;
				self.currentSchoolCount(stat.currentSchoolCount);
				self.dailySchoolCount(stat.dailySchoolCount);
				self.currentLessonCount(stat.currentLessonCount);
				self.dailyLessonCount(stat.dailyLessonCount);
				self.currentActualStuCount(stat.currentActualStuCount);
				self.dailyExpectStuCount(stat.dailyExpectStuCount);
				self.dailyActualStuTimes(stat.dailyActualStuTimes);
				self.dailyExpectStuTimes(stat.dailyExpectStuTimes);
			}
		});
		
	};
	ko.components.register('technical-support-total',{
		template:require('./technical-support-total.html'),
		viewModel:{createViewModel:function(params,componetInfo){
				return new Total(params,componetInfo);
			}
		}
	});
});