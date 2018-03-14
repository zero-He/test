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
	var s_correct = $.i18n.prop('diag.common.echarts.data.correct');

	var MultiChoice = {
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
				formatter : "{b}：{c}" + s_person
			},
			grid : {
				x : 40,
				y : 20,
				x2 : 20,
				y2 : 40
			},
			color : ['#99bf6c'],
			xAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}' + s_person
				},
				splitLine : {
					show : false
				},
				boundaryGap : [0, 0.01]
			}],
			yAxis : [{
				type : 'category',
				splitLine : {
					show : false
				},
				splitArea : {
					show : false
				},
				data : []
			}],
			series : [{
				type : 'bar',
				center : ['80%', '80%'],
				barCategoryGap : '37%',
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'right',
							formatter : '{c}' + s_person
						}
					}
				},
				data : []
			}]
		},
		render : function(domNode, data, qid) {
			var yAxisData = [];
			var serieData = [];
			var rights = [];
			$.each(data, function(i, el) {
				if (el.name == 'RIGHT') {
					yAxisData.push(s_correct);
				} else {
					yAxisData.push(el.name);
				}
				if (el.isRight) {
					rights.push(el.name);
				}
				serieData.push(el.value);
			});
			var opts = $.extend(true, {}, this.option, {
				yAxis : [{
					data : yAxisData,
					axisLabel : {
						formatter : function(name) {
							if (Leke.user.currentRoleId == 100) {
								return name;
							}
							return rights.indexOf(name) >= 0 ? '√ ' + name : name;
						}
					}
				}],
				series : [{
					data : serieData
				}]
			});
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
			chart.on(echart.config.EVENT.CLICK, function(param) {
				Dialog.open({
					size : 'lg',
					title : '学生名单',
					tpl : Mustache.render(tpl, names)
				})
			});
		}
	}

	module.exports = MultiChoice;
});
