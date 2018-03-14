define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');

	var ClassList = {
		_node : null,
		_datas : {},
		_url : ctx + "/auth/common/data/classList.htm",
		_optionTpl : '<option value="{{value}}">{{label}}</option>',
		init : function(id, gradeId) {
			this._node = $('#' + id);
			if (gradeId) {
				this.change(gradeId);
			}
		},
		change : function(gradeId) {
			var _this = this;
			if (_this._datas[gradeId]) {
				_this._setData(_this._datas[gradeId]);
				return;
			}
			var html = $.ajax({
				type : 'post',
				async : false,
				url : this._url,
				data : {
					gradeId : gradeId
				}
			}).responseText;

			var res = $.parseJSON(html);
			_this._datas[gradeId] = res.datas;
			_this._setData(res.datas);
		},
		_setData : function(data) {
			var _this = this;
			_this._node.html('');
			$.each(data.classList, function() {
				var output = Mustache.render(_this._optionTpl, {
					label : this.className,
					value : this.classId
				});
				_this._node.append(output);
			});
		}
	};

	module.exports = ClassList;
});
