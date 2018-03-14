define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Chart = require('chart');
	var echart = require('echart');
	var ClsDiligentPie = require('diag/common/ClsDiligentPie');
	var StuDiligentPie = require('diag/common/StuDiligentPie');
	var I18n = require('i18n');
	I18n.init('diag');

	var s_normalsubmit = $.i18n.prop('diag.common.echarts.option.normalsubmit');
	var s_delaysubmit = $.i18n.prop('diag.common.echarts.option.delaysubmit');
	var s_notsubmit = $.i18n.prop('diag.common.echarts.option.notsubmit');
	var s_gross = $.i18n.prop('diag.common.echarts.option.gross');
	var s_subtext = $.i18n.prop('diag.common.echarts.option.subtext');
	var s_title = $.i18n.prop('diag.common.echarts.diligent.title');

	var TeaDiligentBar = $.extend({}, Chart, {
		chartId : "teaDiligent",
		formId : 'searchForm',
		dataList : [],
		sublinkParams : '',
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			grid : {
				y : 30
			},
			legend : {
				data : [s_normalsubmit, s_delaysubmit, s_notsubmit]
			},
			dataZoom : {
				show : true,
				realtime : true
			},
			color : Chart.Color.SUBMIT,
			toolbox : {
				show : true,
				orient : 'vertical',
				y : 'center',
				feature : {
					magicType : {
						show : true,
						type : ['stack', 'tiled']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
				formatter : function(param) {
					var str = param[0][1];
					for (var i = 0; i < param.length; i++) {
						str += "<br/>" + param[i][0] + "：" + param[i][2] + "%";
					}
					return str;
				}
			},
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}%'
				}
			}],
			xAxis : [{
				type : 'category',
				data : []
			}],
			series : [{
				name : s_normalsubmit,
				type : 'bar',
				stack : s_gross,
				data : []
			}, {
				name : s_delaysubmit,
				type : 'bar',
				stack : s_gross,
				data : []
			}, {
				name : s_notsubmit,
				type : 'bar',
				stack : s_gross,
				data : []
			}]
		},
		rawDataCallback : function(data) {
			if (!data.dataList) {
				ClsDiligentPie.destory();
				StuDiligentPie.destory();
				return;
			}
			var normal = 0, delay = 0, unsubmit = 0;
			$.each(data.dataList, function() {
				normal += this.normal;
				delay += this.delay;
				unsubmit += this.unsubmit;
			});

			this.dataList = data.dataList;

			ClsDiligentPie.load({
				title : {
					text : s_title
				},
				series : [{
					data : [{
						name : s_normalsubmit,
						value : normal
					}, {
						name : s_delaysubmit,
						value : delay
					}, {
						name : s_notsubmit,
						value : unsubmit
					}]
				}]
			});
		},
		bindEvent : function() {
			var _this = this;
			this.chartNode.on(echart.config.EVENT.CLICK, function(param) {
				var item = _this.dataList[param.dataIndex];
				var sublink = 'stuDiligentDtl.htm?studentId=' + item.studentId + '&studentName='
						+ encodeURIComponent(encodeURIComponent(item.studentName)) + _this.sublinkParams;
				StuDiligentPie.load({
					title : {
						text : '<' + item.studentName + '>' + s_title,
						subtext : s_subtext,
						sublink : sublink
					},
					series : [{
						data : [{
							name : s_normalsubmit,
							value : item.normal
						}, {
							name : s_delaysubmit,
							value : item.delay
						}, {
							name : s_notsubmit,
							value : item.unsubmit
						}]
					}]
				});
			});
		},
		load2 : function() {
			this.load();
			StuDiligentPie.destory();
		},
		init2 : function() {
			ClsDiligentPie.init();
			TeaDiligentBar.init();
			StuDiligentPie.init();
			ClsDiligentPie.connects = [TeaDiligentBar, StuDiligentPie];
			TeaDiligentBar.connects = [ClsDiligentPie, StuDiligentPie];
			StuDiligentPie.connects = [TeaDiligentBar, ClsDiligentPie];
		}
	});

	module.exports = TeaDiligentBar;

});
