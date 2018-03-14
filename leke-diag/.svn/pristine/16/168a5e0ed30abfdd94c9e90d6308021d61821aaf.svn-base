define(function(require, exports, module) {
	var $ = require('jquery');
	var WDate = require('date');
	var DateUtil = require('common/base/date');

	function dateToStr(date) {
		return DateUtil.format(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(), "yyyy-MM-dd");
	}

	var PeriodDate = {
		_start : null,
		_end : null,
		init : function(startId, endId) {
			this._start = $('#' + startId);
			this._end = $('#' + endId);
			this._bindEvent();
			this._setDefVal();
		},
		_setDefVal : function() {
			var date = new Date();
			this._end.val(dateToStr(date));
			date.setMonth(date.getMonth() - 1);
			this._start.val(dateToStr(date));
		},
		_bindEvent : function() {
			var _this = this;
			_this._start.click(function() {
				WDate({
					maxDate : _this._end.val(),
					isShowClear : false,
					readOnly : true
				});
			});
			_this._end.click(function() {
				WDate({
					minDate : _this._start.val(),
					isShowClear : false,
					readOnly : true
				});
			});
		}
	};

	module.exports = PeriodDate;
});
