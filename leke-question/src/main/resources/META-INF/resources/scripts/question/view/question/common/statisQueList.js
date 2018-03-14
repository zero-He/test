define(function(require, exports, module) {
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Widget = require('common/base/widget');
	var Bar = require('question/util/queSearchBar');
	var QueService = require('question/service/questionService');
	var DFN = require('homework/sheet.dfn');
	var _loadTpl = $('#loading_tpl').html();
	
	var QueList = Widget.extend({
		options: {
			durl: window.ctx + '/auth/common/question/statisQueListDetails.htm'
		},
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			self._initStatus();
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			
			var $day = self.$('[name=operateDate]');
			$day.on('click', function() {
				WdatePicker({
					dateFmt: 'yyyy-MM-dd',
					readOnly: true,
					onpicked: function() {
						$day.trigger('change');
					},
					oncleared: function() {
						$day.trigger('change');
					}
				});
			});
			self.attrBind($day);
			
			self.on('change:statusType', function() {
				self.doSearch();
			});
			
			self.doSearch();
		},
		_initStatus: function() {
			var self = this;
			var $stat;
			var stat = self.attr('statusType');
			if(stat) {
				$stat = self.$('[data-status="' + stat + '"]');
			}
			if(!$stat || !$stat.length) {
				$stat = self.$('.quesTab:first');
			}
			$stat.addClass('quesTotalon');
			self.attr('statusType', $stat.data('status'));
		},
		events: {
			'click .btnSearch': 'doSearch',
			'click .quesTab': 'changeStatus'
		},
		doSearch: function() {
			var self = this;
			self.attr('curPage', 1);
			self.load();
		},
		load: function() {
			var self = this,
				params = self.attrs();
			var request = $.ajax({
				type: 'post',
				url: self.options.durl,
				data: params,
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
				DFN.init();
			});
		},
		changeStatus: function(evt) {
			var self = this;
			var $li = $(evt.target).closest('.quesTab');
			$li.addClass('quesTotalon').siblings().removeClass('quesTotalon');
			self.attr('statusType', $li.data('status'));
		}
	});
	
	module.exports = QueList;
});