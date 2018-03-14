define(function(require, exports, module) {
	var Select = require('./select');
	var _ = require('common/base/helper');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	var SubjectSelect = Select.extend({
		options: {
			initKeys: ['schoolStageId', 'subjectId', 'subjectName'],
			linkKeys: ['subjectId', 'subjectName'],
			id: 'subjectId',
			name: 'subjectName',
			dataKey: 'subject'
		},
		tpl: '[name=subjectId]',
		init: function() {
			var self = this;
			Select.prototype.init.call(self);
			self.on('change:schoolStageId', function() {
				self.attr({subjectId: null, subjectName: null});
				self._initSubject();
			});
			self._initSubject();
		},
		_initSubject: function() {
			var self = this, sid = self.attr('schoolStageId');
			BasicInfoService.subjects(sid).always(function(subjects) {
				self.initSelect(subjects);
			});
		}
	});
	
	module.exports = SubjectSelect;
});