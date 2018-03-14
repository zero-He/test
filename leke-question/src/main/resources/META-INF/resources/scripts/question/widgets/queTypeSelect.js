define(function(require, exports, module) {
	var _ = require('common/base/helper');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var Select = require('./select');
	
	var QueTypeSelect = Select.extend({
		options: {
			initKeys: ['questionTypeId', 'subjectId'],
			linkKeys: ['questionTypeId', 'questionTypeName', 'hasSub', 'subjective', 'template', 'subTypes', 'questionType'],
			id: 'questionTypeId',
			name: 'questionTypeName',
			dataKey: 'questionType'
		},
		tpl: '[name=questionTypeId]',
		init: function() {
			var self = this, ops = self.options;
			var linkKeys = ops.linkKeys || ops.initKeys || [];
			Select.prototype.init.call(self);
			
			self.on('change:subjectId', function() {
				_.each(linkKeys, function(key) {
					self.attr(key, null);
				});
				self._initTypes();
			});
			self._initTypes();
		},
		_initTypes: function() {
			var self = this;
			var sid = self.attr('subjectId');
			var dfd = BasicInfoService.questionTypes(sid);
			dfd.always(function(queTypes) {
				self.initSelect(queTypes);
			});
		}
	});
	
	module.exports = QueTypeSelect;
});