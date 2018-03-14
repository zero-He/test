define(function(require, exports, module) {
	var $ = require('jquery');
	var tree = require('tree');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var I18n = require('homework/common/i18n');
	var stageUtils = require('homework/exercise/stageUtils.js');
	var s_alert = $.i18n.prop('homework.exercise.knowledge.alert');
	var knowledgeSetting = {
		async : {
			service : "knowledgeTreeDataService",
			autoParam : ['knowledgeId=parentId'],
			otherParam : {
				rootId : 0,
				visitMode : "child"
			}
		},
		view : {
			selectedMulti : true
		},
		edit : {
			enable : true
		},
		data : {
			key : {
				name : 'knowledgeName'
			},
			simpleData : {
				enable : true,
				idKey : 'knowledgeId',
				pIdKey : 'parentId',
				rootPId : 0
			}
		},
		check : {
			enable: false,
			chkStyle: "checkbox",
			chkboxType: { "Y": "", "N": "" }
		},
		callback : {
			onCheck : function(e, treeId, treeNode) {
				var checkedNodes = $.fn.zTree.getZTreeObj("knowledgeTree").getCheckedNodes(true);
				Exercise.knowledge = [];
				$.each(checkedNodes,function(i,n){
					Exercise.knowledge.push({id:n.knowledgeId,name:n.knowledgeName});
				});
				console.log(JSON.stringify(Exercise.knowledge));
				if(Exercise.knowledge.length > 5){
					utils.Notice.alert('最多选择5个知识点');
				}
			}
		}
	};

	var Exercise = {
		selection : null,
		knowledge : [],
		init : function() {
			this.binEvent();
			this.initSubject();
			this.initTree();
			this.difficultySel();
		},
		initSubject : function() {
			var _this = this;
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'stg_sbj',
				caption : false,
				onChange : function(selection) {
					Exercise.selection = selection;
					_this.initTree();
					Exercise.knowledge = [];
				}
			});
			stageUtils.doAboveStageData('jStageSubject',function(){
				$('#jStageSubject').change();
			});
		},
		initTree : function() {
			$.extend(knowledgeSetting.async.otherParam, {
				subjectId : Exercise.selection.subjectId,
				schoolStageId : Exercise.selection.schoolStageId
			});
			tree.createTree("#knowledgeTree", knowledgeSetting);
		},
		binEvent : function() {
			var _this = this;
			$('#sub_Knowledge').click(function() {
				var level = '';//$('#j_diffLevel').val();
			/*	if(level ==''){
					utils.Notice.alert('请选择习题难度');
					return false;
				}*/
				if (Exercise.knowledge.length == 0) {
					utils.Notice.alert(s_alert);
					return false
				}else if(Exercise.knowledge.length > 5){
					utils.Notice.alert('最多选择5个知识点');
					return false;
				}
				var exerciseType = 2;
				var exerciseName =$.map(Exercise.knowledge,function(n){
					return n.name;
				});
				var relId = $.map(Exercise.knowledge,function(n){
					return n.id;
				});
				var schoolStageId = Exercise.selection.schoolStageId;
				var schoolStageName = Exercise.selection.schoolStageName;
				var subjectId = Exercise.selection.subjectId;
				var subjectName = Exercise.selection.subjectName;

				window.parent.fromSubmit(exerciseType, exerciseName.join(','), relId, schoolStageId, schoolStageName, subjectId, subjectName,level);
			});
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
