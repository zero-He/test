define(function(require, exports, module) {
	var $ = require('jquery');
	var echart = require('echart');
	var theme = require('common/ui/ui-echarts/chart-theme');
	var I18n = require('diag/common/i18n');
	var Dialog = require('dialog');
	var Mustache = require('mustache');

	var tpl = '<div class="m-table names"><table><thead><tr><th width="15%">答案</th><th width="85%">学生姓名</th></tr></thead>'
			+ '<tbody>{{#.}}<tr><td>{{label}}</td><td>{{#users}}<span>{{.}}</span>{{/users}}</td></tr>{{/.}}</tbody></table></div>';

	var s_person = $.i18n.prop('diag.common.echarts.option.person');
	var SingleChoice = {
		option : {
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c}" + s_person + " ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'center',
				data : []
			},
			color : ['#99bf6c', '#22beef', '#fbb321', '#f8856f', '#ccc'],
			series : [{
				type : 'pie',
				radius : '65%',
				center : ['60%', '50%'],
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'outer',
							formatter : '{b}：{c}' + s_person
						},
						labelLine : {
							length : 10
						}
					}
				},
				data : []
			}]
		},
		render : function(domNode, data, qid, names) {
			var legendData = [], rights = [];
			$.each(data, function(i, el) {
				legendData.push(el.name);
				if (el.isRight) {
					rights.push(el.name);
				}
			});
			var opts = $.extend(true, {}, this.option, {
				legend : {
					data : legendData,
					formatter : function(name) {
						if (Leke.user.currentRoleId == 100) {
							return name;
						}
						return rights.indexOf(name) >= 0 ? name + ' √' : name;
					}
				},
				series : [{
					data : data
				}]
			});
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
			chart.on(echart.config.EVENT.CLICK, function(param) {
				Dialog.open({
					size : 'lg',
					title : '学生名单',
					tpl : Mustache.render(tpl, names)
				});
			});

		}
	}

	module.exports = SingleChoice;
});
