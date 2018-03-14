define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	
	var SetGradeSubject = {
			fInit : function() {
				this.fBindEvent();
			},
			
			fBindEvent:function() {
				var _this = this;
				$('#bAdd').on('click', function(){
					_this.doAdd();
				});
				$('#bSave').on('click', function(){
					_this.doSave();
				});
				$('#resultId').on('click', '.cbGradeSubject', $.proxy(_this, 'removeLabel'));
			},
			
			doAdd:function() {
				var _this = this;
				var $gs = $('#gradeId').find(':checked');
				if(!$gs.length) {
					utils.Notice.alert('请选择年级！');
					return false;
				}
				var $ss = $('#subjectId').find(':checked');
				if(!$ss.length) {
					utils.Notice.alert('请选择科目！');
					return false;
				}
				var olds = [];
				$('#resultId').find('.dGradeSubject').each(function() {
					olds.push($(this).data('group'));
				});
				
				var contents = '';
				$gs.each(function() {
					var $g = $(this), gid = $g.val(), gnm = $g.data('gnm') || '';
					$ss.each(function() {
						var $s = $(this), sid = $s.val(), snm = $s.data('snm') || '';
						var group = gid + '_' + sid;
						if($.inArray(group, olds) === -1) {
							contents += '<div class="dGradeSubject" data-group="' + group + '"><a href="javascript:void(0)" class="cbGradeSubject" value="'+ group +'">'+ gnm +' - '+ snm +'<strong><b>×</b>删除</strong></a></div>';
						}
					});
				});
				$('#resultId').append(contents);
				
				$gs.prop('checked', false);
				$ss.prop('checked', false);
			},
			
			removeLabel:function(evt) {
				var _this = this, $ipt = $(evt.target);
				if(!$ipt.prop('checked')) {
					$ipt.closest('.dGradeSubject').remove();
				}
			},
			
			doSave:function() {
				var gsList = [];
				$('#resultId').find('.dGradeSubject').each(function() {
					gsList.push($(this).data('group'));
				});
				
				if(gsList == null){
					return;
				}else{
					var arrayObj = [];
					for(var i=0; i<gsList.length; i++){
						var id = $('#id').val();
						var getGradeId = null;
						var getSubjectId = null;
						var ki = gsList[i].indexOf('_');
						getGradeId = gsList[i].slice(0,ki);
						getSubjectId = gsList[i].slice(ki+1,gsList[i].length);
						arrayObj.push('{"gradeId":'+ getGradeId +',"subjectId":'+ getSubjectId +'}');
					}
					if(arrayObj.length === 0) {
						utils.Notice.alert('请增加年级科目！');
						return;
					}
					var jsonObj = '[';
					jsonObj += arrayObj.join(',');
					jsonObj += ']';
					$.ajax({
						type : 'post',
						url : ctx + '/auth/admin/user/ajax/addGradeSubject.htm',
						data : {
							jsonStr: jsonObj,
							id: id
						},
						dataType : 'json',
						success : function(json) {
							var msg = json && json.message;
							if(json.success) {
								utils.Notice.alert('设置成功！');
							} else {
								utils.Notice.alert(msg || '修改失败');
							}
							window.location.href = ctx + '/auth/admin/user/setCheckRange.htm';
						}
					});
				}
			}
	};
	
	SetGradeSubject.fInit();
});
