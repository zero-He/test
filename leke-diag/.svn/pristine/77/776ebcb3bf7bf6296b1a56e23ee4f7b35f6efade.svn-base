define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var PeriodDate = require('diag/common/PeriodDate');
	var I18n = require('i18n');
	I18n.init('diag');
	
	
	var ClassType = {
		_node : null,
		_nodeCon : null,
		itemTpl : '<ul class="m-tab"><li data-classtype="2" class="active"><a>'+$.i18n.prop('diag.common.takeclass')+'</a></li><li data-classtype="1"><a>'+$.i18n.prop('diag.common.adminclass')+'</a></li></ul>',
		init : function() {
			this._node = $('#jClassType');
			this._nodeCon = $('#jClassTypeCon');
			if (this.hasCourse()) {
				this._nodeCon.append(this.itemTpl);
				this.bindEvent();
			}
			ClassList.change("2");
		},
		hasCourse : function() {
			var html = $('#jCourseListTpl').html();
			html = $.trim(html);
			return html.length > 0;
		},
		bindEvent : function() {
			var _this = this;
			_this._nodeCon.on('click', 'li', function() {
				if ($(this).hasClass('active') == false) {
					$('li.active', _this._nodeCon).removeClass('active');
					$(this).addClass('active');
					var classType = $(this).data('classtype');
					$('#jTimeSpan').toggle(classType === 1);
					if (classType === 2) {
						$('#jStartTime').val('');
						$('#jEndTime').val('');
					} else {
						PeriodDate.init('jStartTime', 'jEndTime');
					}
					_this._node.val(classType);
					_this.onChange(classType);
				}
			});
		},
		onChange : function(classType) {
			ClassList.change(classType);
			TeacherClassList.onClassTypeChange(classType);
		}
	};

	var ClassList = {
		_node : null,
		init : function() {
			this._node = $('#jClassId');
		},
		change : function(classType) {
			this._node.html('');
			if (classType == '1') {
				this._node.append($('#jClassListTpl').html());
			} else {
				this._node.append($('#jCourseListTpl').html());
			}
		}
	};

	var TeacherClassList = {
		init : function() {
			ClassList.init();
			ClassType.init();
		},
		onClassTypeChange : function() {
		}
	}

	module.exports = TeacherClassList;
});
