define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	
	function TagDialogVM(params) {
		self.tags = params;
	}
	
	ko.components.register('que-tag-dialog', {
	    template: require('./que-tag-dialog.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TagDialogVM(params, componentInfo.element);
	    	}
	    }
	});
});