define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var LekeWeek = require('common/base/week');
	var LekeDate = require('common/base/date');
	var DAY_OF_WEEK = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
	var PERIODS = [{ id: 'AM', name: '上午', css: 'am-class-total'}, 
	               { id: 'PM', name: '下午', css: 'pm-class-total'}, 
	               { id: 'NT', name: '晚上', css: 'evening-class-total'}];
	var LOGO_CSS = {
		'1': 'label-logo-normal',
		'2': 'label-logo-social',
		'3': 'label-logo-person'
	};
	
	function _toObs(obs, defaults) {
		return ko.isObservable(obs) ? obs : ko.observable(obs || defaults);
	}
	
	function PeriodStat(period, items, dates) {
		var self = this;
		self.period = period;
		var pdailys = PeriodStat.toPDailys(items, dates);
		self.sums = PeriodStat.toSums(pdailys);
		self.rows = PeriodStat.toRows(pdailys);
	}
	
	PeriodStat.toPDailys = function(items, dates) {
		_.each(items, function(item) {
			item.logoCss = LOGO_CSS[item.schoolNature] || '';
		});
		
		var map = _.groupBy(items || [], 'day');
		return _.map(dates, function(d) {
			var pitems = _.sortBy(map[d.day] || [], 'minStartTime');
			return {
				day: d.day,
				items: pitems
			};
		});
	};
	
	PeriodStat.toSums = function(pdailys) {
		return _.map(pdailys, function(pd) {
			var sum = {
				day: pd.day,
				schoolCount: 0,
				lessonCount: 0,
				hours: 0,
				expectStuCount: 0,
				expectStuTimes: 0
			};
			_.each(pd.items, function(item) {
				sum.schoolCount += 1;
				sum.lessonCount += item.lessonCount;
				sum.hours += item.hours;
				sum.expectStuCount += item.expectStuCount;
				sum.expectStuTimes += item.expectStuTimes;
			});
			return sum;
		});
	};
	
	PeriodStat.toRows = function(pdailys) {
		var rowNum = 0;
		_.each(pdailys, function(pd) {
			var len = pd.items.length;
			if(rowNum < len) {
				rowNum = len;
			}
		});
		
		var rows = [], row;
		for(var i = 0; i < rowNum; i++) {
			row = [];
			_.each(pdailys, function(pd) {
				row.push(pd.items[i]);
			});
			rows[i] = row;
		}
		return rows;
	};
	
	function CourseTable(week, dailys, items) {
		var self = this;
		
		var dates = self.dates = CourseTable.toDates(week);
		self.sdailys = CourseTable.toSDailys(dailys, dates);
		self.periodStats = CourseTable.toPeriodStats(items, dates);
	}
	
	CourseTable.toDates = function(week) {
		var startDay = week.startDay();
		
		var dates = [], ld;
		for(var i = 0; i < 7; i++) {
			ld = startDay.add(i, 'd');
			dates.push({
				day: ld.format('yyyyMMdd'),
				date: ld.format('MM-dd'),
				dayOfWeekDesc: DAY_OF_WEEK[i]
			});
		}
		
		return dates;
	};
	
	CourseTable.toSDailys = function(dailys, dates) {
		var map = _.indexBy(dailys || [], 'day');
		return _.map(dates, function(d) {
			var day = d.day;
			var daily = map[day];
			if(!daily) {
				daily = {
					day: day,
					schoolCount: 0,
					lessonCount: 0,
					hours: 0,
					expectStuCount: 0,
					expectStuTimes: 0
				};
			}
			return daily;
		});
	};
	
	CourseTable.toPeriodStats = function(items, dates) {
		var map = _.groupBy(items || [], 'period');
		return _.map(PERIODS, function(p) {
			return new PeriodStat(p, map[p.id], dates);
		});
	};
	
	module.exports = CourseTable;
});