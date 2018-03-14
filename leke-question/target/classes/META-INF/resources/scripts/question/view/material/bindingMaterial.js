define(function(require,exports,module){
	var ko = require('knockout');
	require('./component/mat-binding');

	ko.applyBindings({config: {
		material: window.materialCtx.material
	}});
});