define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Chart = require('chart');
	var echart = require('echart');
	var ClsDiligentPie = require('diag/common/ClsDiligentPie');
	var StuDiligentPie = require('diag/common/StuDiligentPie');

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
				data : ['正常提交', '延迟提交', '未提交']
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
				name : '正常提交',
				type : 'bar',
				stack : '总量',
				data : []
			}, {
				name : '延迟提交',
				type : 'bar',
				stack : '总量',
				data : []
			}, {
				name : '未提交',
				type : 'bar',
				stack : '总量',
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
					text : '整体勤奋报告'
				},
				series : [{
					data : [{
						name : '正常提交',
						value : normal
					}, {
						name : '延迟提交',
						value : delay
					}, {
						name : '未提交',
						value : unsubmit
					}]
				}]
			});
		},
		bindEvent : function() {
			var _this = this;
			this.chartNode.on(echart.config.EVENT.CLICK, function(param) {
				var item = _this.dataList[param.dataIndex];

				StuDiligentPie.load({
					title : {
						text : '<' + item.studentName + '>作业勤奋报告'
					},
					series : [{
						data : [{
							name : '正常提交',
							value : item.normal
						}, {
							name : '延迟提交',
							value : item.delay
						}, {
							name : '未提交',
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
