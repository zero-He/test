define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var Widget = require('common/base/widget');
	
	var Select = Widget.extend({
		options: {
			data: null,
			initKeys: null,
			linkKeys: null,
			id: 'id',
			name: 'name',
			dataKey: 'item',
			prompt: '=请选择=',
			defaultFirst: false
		},
		tpl: '<select></select>',
		init: function() {
			var self = this, $el = self.$el, ops = self.options;
			var initKeys = ops.initKeys;
			var linkKeys = ops.linkKeys || initKeys;
			var data = ops.data || {};
			if(data && initKeys && initKeys.length) {
				self.attr(_.pick(data, initKeys));
			}
			if(linkKeys && linkKeys.length) {
				self.$el.on('change', function() {
					var $op = self.$el.find('option:selected');
					self.linkData($op.data(ops.dataKey));
				});
			}
			self._inited = $.Deferred();
		},
		getInitDfd: function() {
			return this._inited.promise();
		},
		linkData: function(data) {
			var self = this, $el = self.$el, ops = self.options;
			data = data || {};
			var linkKeys = ops.linkKeys || ops.initKeys || [];
			_.each(linkKeys, function(key) {
				var val = (key === ops.dataKey)? data : data[key];
				if(val === undefined) {
					val = null;
				}
				self.attr(key, val);
			});
		},
		initSelect: function(datas) {
			var self = this, $el = self.$el, ops = self.options;
			var initId = _.str(self.attr(ops.id));
			$el.empty();
			if(ops.prompt) {
				$('<option value=""></option>').text(ops.prompt).appendTo($el);
			}
			if(datas && datas.length) {
				if(!initId && ops.defaultFirst) {
					var first = datas[0], id = first[ops.id];
					self.attr(ops.id, id);
					initId = _.str(id);
				}
				$.each(datas, function(i, item) {
					var itemId = _.str(item[ops.id]);
					var $op = $('<option>').attr('value', itemId).text(item[ops.name] || '')
								.data(ops.dataKey, item);
					$op.appendTo($el);
					if(initId && itemId === initId) {
						$op.prop('selected', true);
						self.linkData(item);
					}
				});
			}
			self._inited.resolve();
		}
	});
	
	module.exports = Select;
});