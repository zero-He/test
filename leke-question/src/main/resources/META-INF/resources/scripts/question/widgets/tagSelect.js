define(function(require, exports, module) {
	var Select = require('./select');
	var _ = require('common/base/helper');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	var TagSelect = Select.extend({
		options: {
			initKeys: ['officialTagId', 'officialTagName'],
			id: 'officialTagId',
			name: 'officialTagName',
			dataKey: 'officialTag'
		},
		tpl: '[name=officialTagId]',
		init: function() {
			var self = this;
			Select.prototype.init.call(self);
			BasicInfoService.officialTags().always(function(officialTags) {
				self.initSelect(officialTags);
			});
		}
	});
	
	module.exports = TagSelect;
});