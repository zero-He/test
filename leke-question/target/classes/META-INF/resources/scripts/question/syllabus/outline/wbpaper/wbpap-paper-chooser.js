define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var ListVM = require('repository/common/ListVM');
	var uResGroupService = require('resgroup/service/uResGroupService');
	
	function ChooseVM(params, element) {
		var self = this;
		
		self.init({
			loadUrl: function(query) {
				var scope = query.shareScope;
				if(scope == 4) {
					return Leke.ctx + '/auth/common/paper/favorite/details.htm';
				} else {
					return Leke.ctx + '/auth/common/paper/personal/details.htm';
				}
			},
			qkeys: ['shareScope', 'userResGroupId', 'title'],
			initQuery: {
				shareScope: 1,
				title: ''
			}
		});
		
		self.shareScopes = [{
			scope: 1,
			name: '我的试卷'
		}, {
			scope: 4,
			name: '我的收藏'
		}];
		
		var RES_TYPE_PAPER = 2;
		self.paperUserResGroups = ko.observableArray();
		uResGroupService.findUserResGroup(RES_TYPE_PAPER).then(function(datas) {
			self.paperUserResGroups(datas.groups || []);
		});
		self.userResGrps = ko.pureComputed(function() {
			var shareScope = self.query.shareScope();
			var all = self.paperUserResGroups();
			return _.filter(all, function(item) {
				return item.shareScope == shareScope;
			});
		});
		
		params.onSelect(function() {
			var dfd = $.Deferred(), p = dfd.promise();
			var paps = self.records();
			var result = _(self.records()).filter(function(r) {
				return r.selected();
			}).map(function(r) {
				return _.pick(r, ['paperId', 'title']);
			});
			if(!result.length) {
				dfd.reject('请先选择试卷');
			} else {
				dfd.resolve(result);
			}
			return p;
		});
		
		self.afterInit();
	}
	
	_.extend(ChooseVM.prototype, new ListVM());
	
	ChooseVM.prototype.processRecords = function(datas) {
		var records = datas.records || [];
		_.each(records, function(r) {
			r.selected = ko.observable(false);
			var tp = r.paperType;
			if(tp == 2) {
				r.typeStr = r.paperAttachmentId ? '答题卡(有试卷)' : '答题卡(无试卷)';
			} else {
				r.typeStr = '普通试卷';
			}
			r.subjectiveStr = r.subjective ? '是' : '否';
			r.handwrittenStr = r.handwritten ? '是' : '否';
			r.viewUrl = Leke.domain.paperServerName + '/auth/common/paper/view.htm?paperId=' + r.paperId;
		});
		return records;
	};
	
	ko.components.register('wbpap-paper-chooser', {
	    template: require('./wbpap-paper-chooser.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ChooseVM(params, componentInfo.element);
	    	}
	    }
	});
});