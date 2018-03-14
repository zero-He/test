define(function(require, exports, module) {
	var Select = require('./select');
	var _ = require('common/base/helper');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	var StageSelect = Select.extend({
		options: {
			initKeys: ['schoolStageId', 'schoolStageName'],
			id: 'schoolStageId',
			name: 'schoolStageName',
			dataKey: 'stage'
		},
		tpl: '[name=schoolStageId]',
		init: function() {
			var self = this;
			Select.prototype.init.call(self);
			BasicInfoService.schoolStages().always(function(stages) {
				self.initSelect(stages);
			});
		}
	});
	
	module.exports = StageSelect;
});