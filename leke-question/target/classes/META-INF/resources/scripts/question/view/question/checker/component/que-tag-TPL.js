define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	
	function TagTPLVM(params) {
		self.tags = params.tags;
	}
	
	ko.components.register('que-tag-TPL', {
	    template: require('./que-tag-TPL.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TagTPLVM(params, componentInfo.element);
	    	}
	    }
	});
});