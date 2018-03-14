define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	
	var ExerciseSetting = {
			init : function() {
				this.bindEvent();
				this.loadExerciseSettingList();
			},
			
			bindEvent : function() {
				var _this = this;
				
				_this.cutCon1('cutsub1','click','quesTotalon');
				
				$('#bSave').click(function() {
					_this.setExerciseSetting();
				});
				
				$('#dataForm').on('change', '.stat-data', function() {
					_this.checkData($(this));
				});
				
			},
			
			checkData : function(myself) {
				var reg = /^(?:0|[1-9][0-9]?|[1-9][0-9][0-9]?|1000)$/;
				if (myself.val() == '') {
					myself.removeClass('input-error');
					return;
				}
				if(!reg.test(myself.val())){
					utils.Notice.alert("请输入0-1000的整数！");
					myself.addClass('input-error');
					return false;
				} else {
					myself.removeClass('input-error');
				}
			},
			
			
			
			setExerciseSetting : function() {
				var _this = this;
				if ($(".input-error").length > 0) {
					utils.Notice.alert("题量设置有误");
					return false;
				}
				 _this.getData('synchronize');
				var soStr =gradeMapStr;
				_this.getData('knowledge');
				var kdStr = gradeMapStr;
				$('#bSave').attr('disabled','true');
				$.ajax({
					type : 'post',
					url : ctx + '/auth/provost/exerciseSetting/setExerciseSetting.htm',
					data : {
						'soStr' : soStr,
						'kdStr' : kdStr
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						//utils.Notice.alert(json.message);
						$('#bSave').attr('disabled',false);
						 location.reload();
					}
				});
			},
			
			
			cutCon1 : function(myId,way,className){ //传入需要切换的父级ID、切换方式（onmouseover/onclick）
				var _this = this;
				var $navSub = $('#' + myId).children('li');
				if (way == 'click') {
					$navSub.click(function(){
						value = $navSub.index(this);
						_this.readSub(myId,className,$navSub,value);	
					});
				}
				if (way == 'mouseover') {	
					$navSub.mouseover(function(){
						value = $navSub.index(this);
						_this.readSub(myId,className,$navSub,value);
					});
				}
			},
			
			readSub : function (myId,className,$navSub,value){
				$('#' + myId + 'Con' + value).show().siblings('div').hide();
				$navSub.eq(value).addClass(className).siblings().removeClass(className);
			},
			
			
			loadExerciseSettingList : function() {
				var _this = this;
				$.ajax({
					type : 'post',
					url : ctx + '/auth/provost/exerciseSetting/loadExerciseSettingList.htm',
					data : {
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						_this.showData(json.datas.soESMap, 'synchronize', json.datas.qtsMap);
						_this.showData(json.datas.kdESMap, 'knowledge', json.datas.qtsMap);
					}
				});
				
			},
			
			getData : function(dataClass) {
				gradeMapStr = '';
				var dqtId = 0;
				var defultNum = 0;
				var $tbodyList = $('.'+dataClass);
				for (var i = 0; i < $tbodyList.length; i++) {//遍历标签内的tbody
					//var gradeId = $tbodyList.eq(i).children('.iGradeId').val();
					var gradeId = $tbodyList.eq(i).find('.iGradeId').val();
					var sedArr = [];
					
					$tbodyList.eq(i).find('.stat-data').each(function(){//遍历单元格
						
						var questionTypeId = $(this).attr('questionTypeId');
						if (dqtId != questionTypeId) {
							dqtId = questionTypeId;
						}
						var subjectId = $(this).attr('subjectId');
						if (subjectId == 0) {
							defultNum = $(this).val() || 0;
						}
						var dataId = $(this).attr('dataId') || 0;
						var key = questionTypeId + '-' + subjectId + '-' + dataId;
						var num = $(this).val() || defultNum;
						sedArr.push(key + '-' + num);
					});
					gradeMapStr = gradeMapStr + ';' + gradeId + ':' + sedArr.toString();
				}
				gradeMapStr = gradeMapStr.substring(1);
			},
			
			showData : function(dataMap, dataClass, qtsMap) {
				var $tbodyList = $('.'+dataClass);
				
				if (dataMap == '') { //处理未设置过前的页面显示
					for (var i = 0; i < $tbodyList.length; i++) { 
						$tbodyList.eq(i).find('.stat-data').each(function(){
							var questionTypeId = $(this).attr('questionTypeId');
							var subjectId = $(this).attr('subjectId');
							var key = questionTypeId + '-' + subjectId;
							var haved = qtsMap[key];
							if (haved != 'true' && subjectId != 0) {//对应科目无该题型
								$(this).removeClass('stat-data').addClass('f-dn').attr('disabled','true');
							}
						});
					}
					return;
				}
				
				//分标签组织渲染数据
				for (var i = 0; i < $tbodyList.length; i++) {
					//组织数据
					//var gradeId = $tbodyList.eq(i).children('.iGradeId').val();
					var gradeId = $tbodyList.eq(i).find('.iGradeId').val();
					var esList = dataMap[gradeId];
					var dataMapShow={};
					for(var j = 0; j < esList.length; j++){
						var key = esList[j].questionTypeId + '-0';
						var value = esList[j].defaultNum;
						dataMapShow[key] = value + '-' + esList[j].settingId;;//存储默认值
						var esdList = esList[j].exerciseSettingDtlList;
						for (var k = 0; k < esdList.length; k++) {
							var key = esList[j].questionTypeId + '-' + esdList[k].subjectId;
							var value = esdList[k].questionNum + '-' + esdList[k].settingDtlId;
							dataMapShow[key]=value;
						}
					}
					
					//渲染数据
					$tbodyList.eq(i).find('.stat-data').each(function(){
						//获取各个值
						var questionTypeId = $(this).attr('questionTypeId');
						var subjectId = $(this).attr('subjectId');
						var key = questionTypeId + '-' + subjectId;
						var haved = qtsMap[key];
						if (haved == 'true' || subjectId == 0) {
							if (dataMapShow[key] != undefined) {
								var valueArr = dataMapShow[key].split('-');
								//给对应的格子赋值
								$(this).val(valueArr[0]);
								$(this).attr('dataId', valueArr[1]);
							}
						} else {
							$(this).removeClass('stat-data').addClass('f-dn').attr('disabled','true');
						}
						
					});
				}
			}
	};
	
	ExerciseSetting.init();
});
