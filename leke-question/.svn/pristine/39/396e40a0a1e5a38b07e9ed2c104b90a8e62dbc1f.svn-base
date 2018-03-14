define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	
	function KnowledgeTPLVM(params) {
		self.knowledges = params.knowledges;
	}
	
	ko.components.register('que-knowledge-TPL', {
	    template: require('./que-knowledge-TPL.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new KnowledgeTPLVM(params, componentInfo.element);
	    	}
	    }
	});
});