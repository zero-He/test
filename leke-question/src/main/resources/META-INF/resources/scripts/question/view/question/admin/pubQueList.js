define(function(require, exports, module) {
	var $ = require('jquery');
	var tree = require('tree');
	var Widget = require('common/base/widget');
	var TreeUtil = require('question/util/treeUtil');
	var Bar = require('question/util/queSearchBar');
	var QueService = require('question/service/questionService');
	var DFN = require('homework/sheet.dfn');
	var _loadTpl = $('#loading_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			
			self.on('change:materialId', function() {
				self.attr('materialNodeId', null);
				self._initTree();
			});
			
			self.doSearch();
			self._initTree();
		},
		_initTree: function() {
			var self = this, ops = self.options;
			if(self._tree) {
				self._tree.destroy();
				self._tree = null;
			}
			var mid = self.attr('materialId');
			if(mid) {
				self._tree = tree.createTree('#sectionTree', {
					async: {
						url : window.ctx + '/auth/admin/question/statis/materialNodeStatis.htm',
						autoParam: ['materialNodeId=parentId'],
						dataFilter: function(treeId, parentNode, resp) {
							var datas = TreeUtil.recurFilter(resp, TreeUtil.statisNameFilter());
							return datas;
						},
						otherParam: {
							materialId: mid,
							statusType: 99
						}
					},
					view: {
						selectedMulti: false
					},
					callback : {
						onClick : function(e, treeId, treeNode, flag) {
							self.attr('materialNodeId', treeNode.materialNodeId);
							self.doSearch();
						}
					}
				});
			}
		},
		events: {
			'click .btnSearch': 'doSearch'
		},
		doSearch: function() {
			var self = this;
			self.attr('curPage', 1);
			self.load();
		},
		load: function() {
			var self = this,
				params = self.attrs();
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/admin/question/pubQueListDetails.htm',
				data: params,
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
				DFN.init();
			});
		}
	});
	
	new QueList();
});