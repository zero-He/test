define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');

	var TeacherList = {
		_node : null,
		_datas : {},
		_url : ctx + "/auth/common/data/teacherList.htm",
		_optionTpl : '<option value="{{value}}">{{label}}</option>',
		init : function(id, subjectId) {
			this._node = $('#' + id);
			if (subjectId) {
				this.change(subjectId);
			}
		},
		change : function(subjectId) {
			var _this = this;
			if (_this._datas[subjectId]) {
				_this._setData(_this._datas[subjectId]);
				return;
			}
			var html = $.ajax({
				type : 'post',
				async : false,
				url : this._url,
				data : {
					subjectId : subjectId
				}
			}).responseText;

			var res = $.parseJSON(html);
			_this._datas[subjectId] = res.datas;
			_this._setData(res.datas);
		},
		_setData : function(data) {
			var _this = this;
			_this._node.html('');
			$.each(data.teacherList, function() {
				var output = Mustache.render(_this._optionTpl, {
					label : this.userName,
					value : this.id
				});
				_this._node.append(output);
			});
		}
	};

	module.exports = TeacherList;
});
