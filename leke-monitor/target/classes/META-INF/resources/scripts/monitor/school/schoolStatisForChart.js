define(function(require,exports,module){
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Utils = require('utils');
	var Echart = require('echart');
	
	var SsChart = {
			
			fInit : function() {
				this.fBindEvent();
				this.fLoadSsForTimeChart();
			},
		
			
			fBindEvent : function() {
				var _this = this;
				
				$('.j-type-tab').on('click', 'li', function() {
					var $this = $(this);
					$this.siblings().removeClass('active');
					$this.addClass('active');
					
					var divId = $this.data('id');
					if (divId == 'jTimeDiv') {
						$('#jAreaDiv').attr('class', 'f-dn');
						$('#jTimeDiv').attr('class', 'f-db');
					} else {
						$('#jTimeDiv').attr('class', 'f-dn');
						$('#jAreaDiv').attr('class', 'f-db');
						var bIsLoad = $('#jTimeDiv').val();
						if (bIsLoad == 0) {
							_this.fLoadSsForAreaChart();
							$('#jTimeDiv').val(1);
						}
					}
					
				});
				
				
				var startTime = $('#jStartTime').val();
				$('#jStartTime').click(function(){
					WdatePicker({
						dateFmt:'yyyy-MM',
						minDate:startTime,
						maxDate:$('#jEndTime').val() || '%y-%M-%d',
						readOnly:true,
						isShowClear:false
					});
				});
				
				$('#jEndTime').click(function(){
					WdatePicker({
						dateFmt:'yyyy-MM',
						minDate:$('#jStartTime').val(),
						maxDate:'%y-%M-%d',
						readOnly:true,
						isShowClear:false
					});
				});
				
				$('#jTimeChartSelect').click(function(){
					_this.fLoadSsForTimeChart();
				});
				
				$('#jAreaChartSelect').click(function(){
					_this.fLoadSsForAreaChart();
				});
			
			},
			
			fLoadSsForTimeChart : function() {
				var _this = this;
				var startStr = $('#jStartTime').val();
				var endStr = $('#jEndTime').val();
				$.ajax({
					type : 'post',
					url : ctx + '/auth/platformAdmin/school/loadSchoolStatisForTimeChart.htm',
					data : {
						'startStr' : startStr,
						'endStr' : endStr
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						var monArr = json.datas.monArr;
						var schArr = json.datas.schArr;
						var tchArr = json.datas.tchArr;
						var stuArr = json.datas.stuArr;
						_this.fShowChart('time', 'jTimeSchool', '', monArr, schArr, '学校', 'schoolListByTime.htm?timeStr=');
						_this.fShowChart('time', 'jTimeTeacher', '', monArr, tchArr, '教师', 'schoolUserListByTime.htm?roleId=101&timeStr=');
						_this.fShowChart('time', 'jTimeStudent', '', monArr, stuArr, '学生', 'schoolUserListByTime.htm?roleId=100&timeStr=');
					}
				});
			}, 
			
			
			fLoadSsForAreaChart : function() {
				var _this = this;
				var areasStr = $('#jAreas').val();
				$.ajax({
					type : 'post',
					url : ctx + '/auth/platformAdmin/school/loadSchoolStatisForAreaChart.htm',
					data : {
						'areasStr' : areasStr
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						var areaIdArr = json.datas.areaIdArr;
						var areaNameArr = json.datas.areaNameArr;
						var schArr = json.datas.schArr;
						var tchArr = json.datas.tchArr;
						var stuArr = json.datas.stuArr;
						_this.fShowChart('area', 'jAreaSchool', areaIdArr, areaNameArr, schArr, 
								'学校', 'schoolListByArea.htm?areasStr='+areasStr+'&areaId=');
						_this.fShowChart('area', 'jAreaTeacher', areaIdArr, areaNameArr, tchArr, 
								'教师', 'schoolUserListByArea.htm?roleId=101&areasStr='+areasStr+'&areaId=');
						_this.fShowChart('area', 'jAreaStudent', areaIdArr, areaNameArr, stuArr, 
								'学生', 'schoolUserListByArea.htm?roleId=100&areasStr='+areasStr+'&areaId=');
					}
				});
			},
			
			
			fShowChart : function(type, divId, idArr, xArr, dataArr, titleStr, typeUrl) {
				var myEchart = Echart.init($('#' + divId)[0]).setOption({
					title : {
				        text: titleStr,
				        x:'center'
				    },
				    tooltip : {
						trigger : 'axis'
					},
					toolbox : {
						show : true
					},
				    xAxis : [{
				    	type : 'category',
				    	data : xArr
				    }],
				    yAxis : [{
						type : 'value'
					}],
				    series : [
				              {name:titleStr, type:'bar', data:dataArr}
				    ]
				});
				if (type == 'time') {
					myEchart.on(Echart.config.EVENT.CLICK, function(param) {
						window.open(window.ctx + '/auth/platformAdmin/school/' + typeUrl + param.name, '_blank');
					});
				} else {
					myEchart.on(Echart.config.EVENT.CLICK, function(param) {
						var index = param.dataIndex;
						window.open(window.ctx + '/auth/platformAdmin/school/' + typeUrl + idArr[index]
							+ '&areaName=' + param.name, '_blank');
					});
				}
			}
			
			
	};

	SsChart.fInit();
});
