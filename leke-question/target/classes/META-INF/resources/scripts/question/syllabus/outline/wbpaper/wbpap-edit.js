define(function(require, exports, module) {
	var ko = require('knockout');
	require('./wbpap-wbnode-chooser');
	
	function EditVM(params) {
		var self = this;
		self.outline = params.outline;
		self.olnode = ko.observable();
	}
	
	ko.components.register('wbpap-edit', {
	    template: require('./wbpap-edit.html'),
	    viewModel: EditVM
	});
});
