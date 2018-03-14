define(function(require, exports, module) {
	var $ = require('jquery');
	require('core-business/widget/jquery.leke.matNodeSelect');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var I18n = require('homework/common/i18n');
	var utils = require('utils');
	var stageUtils = require('homework/exercise/stageUtils.js');
	var s_alert = $.i18n.prop('homework.exercise.exercise.alert');
	var Exercise = {
		selection : null,
		init : function() {
			this.loadStageSubject();
			this.loadMaterialTree();
			this.bindEvent();
		},
		loadStageSubject : function() {
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'stg_sbj',
				caption : false,
				onChange : function(selection) {
					Exercise.selection = selection;
				}
			});
			stageUtils.doAboveStageData('jStageSubject',function(){
				$('#jStageSubject').change();
			});
		},
		loadMaterialTree : function() {
			$('#jMaterialTree').on('click', function() {
				var $this = $(this);
				$this.matNodeSelect({
					type : 'section',
					multi : true,
					mutable : false,
					data : {
						subjectId : Exercise.selection.subjectId,
						schoolStageId : Exercise.selection.schoolStageId
					},
					onSelect : function(section) {
						var selectNode = [];
						$.each(section,function(i,n){
							selectNode.push(n.path);
						})
						if(selectNode.length > 5){
							utils.Notice.alert('最多选择5个章节');
							return false;
						}
						$this.val(selectNode.join(','));
						Exercise.section = section;
					},
					onClick:'enable'
				});
			});
			$('#jMaterialDel').on('click', function() {
				$('#jMaterialTree').val('');
				Exercise.section = null;
			});
		},
		bindEvent : function() {
			var _this = this;
			$('#jBtnStartTest').click(function() {
				var level = 0 ;
				/*var level = $('#j_diffLevel').val();
				if(level ==''){
					utils.Notice.alert('请选择习题难度');
					return false;
				}*/
				if (Exercise.section == null) {
					utils.Notice.alert(s_alert);
					return false;
				}else if(Exercise.section.length > 5){
					utils.Notice.alert('最多选择5个章节');
					return false;
				}
				var exerciseType = 1;
				var relId = [];
				var materialNodeNames = [];
				for(var i = 0;i<Exercise.section.length ;i++){
					relId.push(Exercise.section[i].materialNodeId);
					materialNodeNames.push(Exercise.section[i].path);
				}
				var exerciseName = materialNodeNames.join(',');
				var schoolStageId = Exercise.selection.schoolStageId;
				var schoolStageName = Exercise.selection.schoolStageName;
				var subjectId = Exercise.selection.subjectId;
				var subjectName = Exercise.selection.subjectName;
				//表单值传给平板端
				//long,string,long[],long,string,long,string,int
				//window.auto_practice.start(exerciseType, exerciseName, JSON.stringify(relId), schoolStageId, schoolStageName, subjectId, subjectName,level);
				LeKeBridge.sendMessage2Native('start', JSON.stringify({
					'exerciseType' : exerciseType,
					'exerciseName' : exerciseName,
					'relId' : JSON.stringify(relId),
					'schoolStageId' : schoolStageId,
					'schoolStageName' : schoolStageName,
					'subjectId' : subjectId,
					'subjectName' : subjectName,
					'level' : level
				}));
			});
			_this.difficultySel();
			
		},
		difficultySel : function(){
            var diffClassSort = ['容易','较易','一般','较难','困难'];

            var $selector = $('.difficulty').children('li');
            var $diffClass = $('.diffclass')

            $selector.each(function(index){
                $(this).on('click',function(){
                    $(this).addClass('full').prevAll().addClass('full');
                    $(this).nextAll().removeClass('full');
                    $diffClass.text(diffClassSort[index]);
                    $('#j_diffLevel').val(index+1);
                });
            })
        }
	};

	Exercise.init();

});
