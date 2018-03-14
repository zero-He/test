define(function(require, exports, module) {
	var $ = require('jquery');
	
	var nodeTypes = {
		'material': 1,
		'section': 2,
		'knowledge': 3
	};
	
	var TreeUtil = {
		addHoverDom: function(node, options) {
			var ops = $.extend({
				type: 'add',
				title: '添加节点',
				cb: function() {}
			}, options);
			var tid = node.tId, type = ops.type;
			var btnId = 'btn_' + type + '_' + tid;
			if (!$('#' + btnId).length) {
				var $btn = $('<span class="button"></span>').attr({
					id: btnId,
					title: ops.title
				}).addClass(type);
				$('#' + tid + '_span').after($btn);
				$btn.on('click', ops.cb);
			}
		},
		getTypeFilter: function(type) {
			return function(data) {
				data.nocheck = nodeTypes[type] != data.nodeType;
			};
		},
		statisNameFilter: function(nameKey) {
			nameKey = nameKey || 'materialNodeName';
			return function(data) {
				var name = data[nameKey] || '';
				var count = data.queCount;
				if(count !== undefined && count !== null) {
					name += ' ( ' + count + ' )';
				}
				data.name = name;
			};
		},
		recurFilter: function(datas, filter) {
			if(datas && datas.length) {
				$.each(datas, function(i, data) {
					filter(data);
					if(data.children) {
						TreeUtil.recurFilter(data.children, filter);
					}
				});
			}
			return datas;
		}
	};
	
	module.exports = TreeUtil;
});