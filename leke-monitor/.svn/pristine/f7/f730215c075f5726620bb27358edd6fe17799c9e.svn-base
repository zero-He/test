define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var My97 = require('date');
	var _=require('underscore');
	var Utils = require('utils');
	var LekeDate=require('common/base/date');
	var historyCourse = {
			init : function() {
				var seacheDate=LekeDate.of(window.currentSystemTime);
				$('#startDay').val(seacheDate.add(-1,'M').add(-1,'d').format());
				$('#endDay').val(seacheDate.add(-1,'d').format());
				this.loadHistoryCourse();
				this.bindEvents();
			},
			bindEvents:function(){
				$('#startDay').click(function(){
					My97({
						dateFmt:'yyyy-MM-dd',
						minDate:'#F{$dp.$D(\'endDay\',{M:-6});}',
						maxDate:'#F{$dp.$D(\'endDay\');}',
						readOnly:true
					});
				});
				//||$dp.$DV(\'%y-%M-%d\',{M:0,d:-1})
				$('#endDay').click(function(){
					My97({
						dateFmt:'yyyy-MM-dd',
						maxDate:'#F{$dp.$D(\'startDay\',{M:6}) || $dp.$DV(\'%y-%M-%d\',{d:-1})}',
						readOnly:true
					});
				});
				$('#btn_search').click(function(){
					historyCourse.loadHistoryCourse();
				});
			},
			loadHistoryCourse : function() {
				var startDay=$('#startDay').val().replace('-','').replace('-','');
				var endDay=$('#endDay').val().replace('-','').replace('-','');
				if(startDay==''){
					Utils.Notice.alert('请选择开始日期');
				}
				if(endDay==''){
					Utils.Notice.alert('请选择结束日期');
				}
				if(parseInt(startDay, 10)>parseInt(endDay, 10)){
					Utils.Notice.alert('开始日期不能大于结束日期');
					return false;
				}
				$.ajax({
					type:'post',
					url:ctx + '/auth/technicalSupport/course/coursePlatformDailys.htm',
					data:{startDay:startDay,endDay:endDay},
					dataType : "json",
					success:function(datas){
						datas.datas.stats=_.map(datas.datas.stats,function(arr){
							 var day=arr.day.toString();
							arr.day=day.substr(0,4)+'-'+day.substr(4,2)+'-'+day.substr(6,2);
							return arr;
						});
						var sOutput = Mustache.render($('#jSuListTpl').val(), datas.datas);
						$('#jSuListTbody').html(sOutput);
						if(datas.datas.stats.length==0){
							$('#jDivPage').html('<div class="tips f-tac">对不起，没有您要查询的数据</div>');
						}
					}
				});
				
			}
	};
	historyCourse.init();
});

