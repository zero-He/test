define(function(require,exports,module){
	var $ = require('jquery');
	var ko = require('knockout');
	var http = require('common/base/http');
	var _ = require('underscore');
	var utils = require('utils');
	
	var List = require('./common/ListVM');
	
	var LOAD_DIR = Leke.domain.repositoryServerName +  '/auth/admin/jsonp/leke/paper/details.htm';
	var COPY_DIR = Leke.domain.paperServerName +  '/auth/common/paper/jsonp/doAddSection.htm';
	
	function ListVM(params,element){
		var self = this;
		self.$el = $(element);
		params.LOAD_DIR = LOAD_DIR;
		params.COPY_DIR = COPY_DIR;
		self.init(params);
		
	};
	
	_.extend(ListVM.prototype, new List());
	
	ListVM.prototype.open = function($data){
		window.open(Leke.domain.paperServerName + '/auth/common/paper/view.htm?paperId=' + $data.resId);
	};
	
	ListVM.prototype.toJS = function(){
		var self = this;
		var materialNode = self.origMaterialNode();
		var  result = [];
		if(!materialNode){
			utils.Notice.alert('选择要绑定的教材章节！');
			return false;
		}
		var resIds = self._selectedResIds();
		if(!resIds.length){
			utils.Notice.alert('选择要绑定的试卷！');
			return false;
		}
		_.each(resIds,function(resId){
			result.push({
				paperId: resId,
				pressId: materialNode.pressId,
				materialId: materialNode.materialId,
				materialNodeId: materialNode.materialNodeId,
				path: materialNode.path
			});
		});
		return result;
	};
	
	ko.components.register('mat-paper-list', {
	    template: require('./mat-res-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});