define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var dialog = require('dialog');
	var Chart = require('chart');
	var Mustache = require('mustache');
	var I18n = require('diag/common/i18n');

	var s_person = $.i18n.prop('diag.common.echarts.option.person');

	var Pie = $.extend({}, Chart, {
		chartId : "hwScoreStat",
		option : {
			title : {
				text : $.i18n.prop('diag.homework.chartpie.option.title'),
				x : 'left'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'center'
			},
			color : Chart.Color.SCORE,
			toolbox : {
				show : true,
				feature : {
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			series : [{
				type : 'pie',
				radius : '65%',
				center : ['55%', '55%'],
				selectedMode : 'single',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}：{c}' + s_person
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'inner',
							formatter : "{b}\n {d}% ({c}" + s_person + ")"
						}
					}
				}
			}]
		},
		rawDataCallback : function(data) {
			Statistics.homeworkDtlList = data.homeworkDtlList;
			var preIndex = 0, preScore = -1;
			$.each(Statistics.homeworkDtlList, function(index, homeworkDtl) {
				if (preScore != homeworkDtl.score) {
					homeworkDtl.rank = index + 1;
					preIndex = index + 1;
					preScore = homeworkDtl.score;
				} else {
					homeworkDtl.rank = preIndex;
				}
			});
			Statistics.listShow();
			Statistics.rateShow(data.rateMap);
		}
	});

	var Statistics = {
		params : {},
		homeworkDtlList : [],
		orderBy : 0,
		init : function() {
			var params = $('#jChartParams').html();
			Pie.params = this.params = json.parse(params);
			Pie.init();
			this.bindEvent();
	
		},
		bindEvent : function (){
			$('#jExport').click(function(){
				var url = ctx+"/auth/teacher/homework/export.htm?homeworkId="+$(this).data('id');
			$('#iframes').attr('src',url);
			});
		},
		rateShow : function(rateMap) {
			if (rateMap) {
				$('#jRateA').text(rateMap.rateA || '0');
				$('#jRateB').text(rateMap.rateB || '0');
				$('#jRateC').text(rateMap.rateC || '0');
			}
		},
		listShow : function() {
			if (this.homeworkDtlList == null || this.homeworkDtlList.length == 0) {
				$('#jHwScoreList').hide();
				$('#jExport').hide();
			} else {
				$('#jHwScoreList').show();
				$('#jExport').show();
			}
			// A
			var alist = this.grep(this.params.a, this.params.total + 1);
			this.render(1, alist, this.params.a, this.params.total, '#99B958');
			// B
			var blist = this.grep(this.params.b, this.params.a);
			this.render(2, blist, this.params.b, this.params.a, '#4E7FBB');
			// C
			var clist = this.grep(this.params.c, this.params.b);
			this.render(3, clist, this.params.c, this.params.b, '#7E63A0');
			// D
			var dlist = this.grep(0, this.params.c);
			this.render(4, dlist, 0, this.params.c, '#BE4F4C');
		},
		render : function(index, list, min, max, color) {
			if (list.length == 0) {
				return;
			}
			$.each(list, function(index, homeworkDtl) {
				homeworkDtl.score = Utils.Number.toFixed(homeworkDtl.score, 1);
				if (index == 0) {
					homeworkDtl.marge = Mustache.render($("#jHomeworkMargeTpl").html(), {
						max : max,
						min : min,
						color : color,
						size : list.length
					});
				}
			});
			var output = Mustache.render($("#jHomeworkListTpl").html(), {
				dataList : list
			});
			$('#jHwScoreListBody' + index).html(output);
			//查找当前学生成绩
			$('#J_studentRank').find('tr').each(function(){
				var stuId=$(this).data('stu');
				if(stuId==Leke.user.userId){
					$(this).find('.rank').addClass('s-c-orange').nextAll().addClass('s-c-orange');
					$('#J_stuRank').text($(this).find('.rank').text());
					$('#J_stuScore').text($(this).find('.totalScore').text());
					return false;
				}
			});
		},
		grep : function(min, max) {
			return $.grep(this.homeworkDtlList, function(element, i) {
				return element.score >= min && element.score < max;
			});
		}
	};

	Statistics.init();
	

});
