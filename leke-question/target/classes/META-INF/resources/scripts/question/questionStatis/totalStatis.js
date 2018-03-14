define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	var fillBaseNamesService = require('question/questionStatis/fillBaseNamesService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var stgGrdSbjSelect = require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var TotalStatis = {
			fInit : function() {
				this.fBindEvent();
				this.loadPressList();
				this.loadSubjectMaterials();
			},
			
			fBindEvent:function() {
				var self = this;
				$('#jSearch').click(function() {
					self.loadSubjectMaterials();
				})
				$('#aSchoolStageList').click(function() {
					self.loadSubjectMaterials();
					$(this).addClass('active').siblings().removeClass('active');
				});
				$('#aKnowledegeList').click(function() {
					self.loadKnowledgeList();
					$(this).addClass('active').siblings().removeClass('active');
				});
				$('#aOfficialTag').click(function() {
					self.loadOfficialTagList();
					$(this).addClass('active').siblings().removeClass('active');
				});
				$('#stage').stgGrdSbjSelect({
					type: 'stg',
					data: {},
					onChange: function(ss) {
						var schoolStageId = null;
						if(typeof(ss) != 'undefined') {
							schoolStageId = ss.schoolStageId;
						}
						$('#schoolStageId').val(schoolStageId);
					}
				});
			},
			loadPressList:function() {
				var self = this;
				
				BasicInfoService.presses().done(function(presses) {
					var pressList = {};
					pressList.presses= presses;
					var output = Mustache.render($('#pressId_tpl').val(),pressList);
					$('#jPressId').html(output);
				});
			},
			
			loadSubjectMaterials:function() {
				var nSchoolStageId = $('#schoolStageId').val();
				var nType = $('#jType').find('option:selected').val();
				var nPressId = $('#jPressId').find('option:selected').val();
				if(nSchoolStageId == 0) {
					utils.Notice.alert("请选择学段！");
					$('#materialContext').empty();
					return;
				}
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionStatis/ajax/findQuestionAmountByMaterial.htm',
					data : {
						schoolStageId:nSchoolStageId,
						pressId: nPressId,
						status:nType
					},
					dataType : 'json',
					success : function(json) {
						//渲染begin
						var listjson = json.datas;
						var subjectMaterials = listjson.inputStatisList || [];
						var ssSize = subjectMaterials.length;
						for(var i=0; i<ssSize; i++) {
							if(i % 4 == 0) {
								subjectMaterials[i].sscolour='cware';
							} else if(i % 4 == 1) {
								subjectMaterials[i].sscolour='micclass';
							} else if(i % 4 == 2) {
								subjectMaterials[i].sscolour='task';
							} else if(i % 4 == 3) {
								subjectMaterials[i].sscolour='case';
							}
						}

						var materialoutput = Mustache.render($('#materialContext_tpl').val(),listjson);
						$('#materialContext').html(materialoutput);
						
						//渲染end
						var dataMap={};
						
						//取出所有的记录，subjectId+'-'+materialId作为key，amount作为value放入map
						for(var i=0;i<ssSize;i++) {
							var mqa = subjectMaterials[i].materialQuestionAmounts || [];
							var mqaSize = mqa.length;
							if(mqaSize > 0) {
								var amounts = 0;
								for(var j=0; j< mqaSize; j++) {
									var key = subjectMaterials[i].subjectId +'-'+mqa[j].materialId;
									var value=mqa[j].amount;
									dataMap[key]=value;
									amounts += mqa[j].amount;
								}
								dataMap[ subjectMaterials[i].subjectId +'-0'] = amounts;
							}
						}
						
						//为每个单元格赋值
						$('#materialContext td.stat-data').each(function() {
							//获取各个值
							var subjectId = $(this).attr('subjectId');
							var materialId = $(this).attr('materialId');
							key=subjectId+'-'+materialId;
							value=dataMap[key] || 0;
							//给对应的值赋值
							var valueHtm = '<a href="' + ctx + '/auth/admin/questionStatis/total/knowledgePubQueList.htm?materialId='+ materialId +'&subjectId=' + subjectId + '" class="s-c-turquoise">' + value + '</a>';
							$(this).html(valueHtm);
						});
						var totalAmount = 0;
						$('#materialContext td.mqa-data').each(function() {
							//获取各个值
							var subjectId = $(this).attr('subjectId');
							var materialId = 0;
							key=subjectId+'-'+materialId;
							value=dataMap[key] || 0;
							//给对应的值赋值
							var valueHtm = value;
							$(this).html(valueHtm);
							totalAmount += value;
						});

						$('#jTotalAmount').html('总计：' + totalAmount);
					}
				});
			},
			
			loadKnowledgeList:function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionStatis/ajax/findStatisListByKnowledge.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						//渲染begin
						var listjson = json.datas;
						fillBaseNamesService.inputStatis(listjson.inputStatisList).done(function(list) {
							listjson.inputStatisList = list;
							
							var schoolStageList = listjson.schoolStageList;
							var ssSize = schoolStageList.length;
							for(var i=0; i<ssSize; i++) {
								if(i % 4 == 0) {
									listjson.schoolStageList[i].sscolour='cware';
								} else if(i % 4 == 1) {
									listjson.schoolStageList[i].sscolour='micclass';
								} else if(i % 4 == 2) {
									listjson.schoolStageList[i].sscolour='task';
								} else if(i % 4 == 3) {
									listjson.schoolStageList[i].sscolour='case';
								}
							}
							
							var gradeoutput = Mustache.render($('#knowledgeAmountContext_tpl').val(),listjson);
							$('#knowledgeAmountContext').html(gradeoutput);
							//渲染end
							var dataMap={};
							
							//取出所有的记录，schoolStageId+'-'+subjectId作为key，amount作为value放入map
							for(var i=0;i<listjson.inputStatisList.length;i++) {
								var key = listjson.inputStatisList[i].schoolStageId +'-'+listjson.inputStatisList[i].subjectId;
								var value=listjson.inputStatisList[i].amount;
								dataMap[key]=value;
							}
							
							//为每个单元格赋值
							$('#knowledgeAmountContext td.stat-data').each(function() {
								//获取各个值
								var schoolStageId = $(this).attr('schoolStageId');
								var subjectId = $(this).attr('subjectId');
								key=schoolStageId+'-'+subjectId;
								value=dataMap[key] || 0;
								//给对应的值赋值
								var valueHtm = '<a href="' + ctx + '/auth/admin/questionStatis/total/knowledgePubQueList.htm?schoolStageId='+ schoolStageId +'&subjectId=' + subjectId + '" class="s-c-turquoise">' + value + '</a>';
								$(this).html(valueHtm);
							});
						});
					}
				});
			},
			
			loadOfficialTagList:function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionStatis/ajax/findStatisGroupByOfficialTagId.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						var output = Mustache.render($('#officialTagContext_tpl').val(),listjson);
						$('#officialTagContext').html(output);
					}
				});
			}
	};
	
	TotalStatis.fInit();
});
