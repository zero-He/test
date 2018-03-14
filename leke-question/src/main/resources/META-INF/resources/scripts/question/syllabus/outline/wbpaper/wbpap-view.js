define(function(require, exports, module) {
	var ko = require('knockout');
	require('./wbpap-wbnode-chooser');
	require('./wbpap-paper-list');
	
	function ViewVM(params) {
		var self = this;
		self.workbook = params.workbook;
		self.wbnode = ko.observable();
	}
	
	ko.components.register('wbpap-view', {
	    template: require('./wbpap-view.html'),
	    viewModel: ViewVM
	});
});