define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	
	var setSubjectQuestion = {
			init : function() {
				this.load();
				this.bindEvent();
			},
			
			bindEvent:function() {
				var _this = this;
				$('input[name="save"]').on('click',function(){
					var context=$(this).parent().parent();
					var subjectId=$('input[name="subjectId"]',context).val();
					var questionTypeIds=$('input[name="questionTypeId"]:checked',context);
					var questions='';
					for(var i=0;i<questionTypeIds.length;i++){
						questions+=$(questionTypeIds[i]).val()+',';
					}
					questions=questions.substring(0,questions.length-1);
					if(questions==''){
						utils.Notice.alert('请选择题型');
						return false;
					}
					$.ajax({
						type:'post',
						url:ctx+'/auth/admin/questionType/doSetSubjectQuestion.htm',
						dataType:'json',
						data:{subjectId:subjectId,questionTypeIds:questions},
						success:function(data){
							if(data.success){
								utils.Notice.alert('保存成功');
								return true;
							}else{
								utils.Notice.alert('保存失败');
								return false;
							}
						}
					});
				});
			},
			load:function(){
				$.ajax({
					type:'post',
					url : ctx + '/auth/admin/questionType/findAllSubjectQuestionTypeMap.htm',
					dataType:'JSON',
					success:function(data){
						var list=data.datas.list;
						for(var i=0;i<list.length;i++){
							$('#s_'+list[i]).attr('checked','checked');
						}
					}
				});
			}
	};
	setSubjectQuestion.init();
});
