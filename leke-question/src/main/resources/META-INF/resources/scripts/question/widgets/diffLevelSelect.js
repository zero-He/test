define(function(require, exports, module) {
	var _ = require('common/base/helper');
	var CST = require('question/service/cst');
	var Select = require('./select');
	
	var DiffLevelSelect = Select.extend({
		options: {
			initKeys: ['diffLevel', 'levelName'],
			id: 'diffLevel',
			name: 'levelName',
			dataKey: 'dl'
		},
		tpl: '[name=diffLevel]',
		init: function() {
			var self = this;
			Select.prototype.init.call(self);
			self.initSelect(CST.diffLevels);
		}
	});
	
	module.exports = DiffLevelSelect;
});