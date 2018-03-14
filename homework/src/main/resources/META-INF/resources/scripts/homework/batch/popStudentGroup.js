define(function(require,exports,module){
	var $ = require('jquery');
	var ko = require('knockout');
	var _ = require('underscore');
	var dialog = require('dialog');
	var utils= require('utils');
	var CST = {
			postUrl: window.ctx + '/auth/teacher/batch/loadStudentGroup.htm?type='+window.bsType
	};
	
	function TableVM(){
		var self = this;
		self.groups = ko.observableArray();
		this.loadMsg = ko.observable('数据加载中……');
		self.setGroups();
	};
	
	TableVM.prototype.setGroups = function(){
		var self = this;
		var req= $.ajax({
			type: 'post',
			url: CST.postUrl,
		});
		req.then(function(obj){
			self.groups(obj.datas.groups || []);
			if(obj.datas.groups.length == 0){
				self.loadMsg('对不起，您无相关联学生可以布置作业');
			}
			self.backInfo();
		},function(msg){
			utils.Notice.alert(msg);
		});
	};
	
	TableVM.prototype.backInfo = function(){
		var self = this;
		var studentGroupIds = window.BACKINFO || [];
		_.each(studentGroupIds,function(n){
			if(n.groups.length == 0){
				var $obj =$('#classId_' + n.classId + (n.courseSetId ? '_' + n.courseSetId : ''));
				$obj.prop('checked',true);
			    if($('input[data-gradeName="' + n.gradeName + '"]:not(:checked)').size()>0){
			    	$('input[data-grade="' + n.gradeName + '"]').attr('checked',false);
			    }
			    else{
			        $('input[data-grade="' + n.gradeName + '"]').attr('checked',true);
			    }
				
			}else{
				_.each(n.groups || [] ,function(m){
					$('#groupId_' + m.groupId).prop('checked',true);
				});
			}
		});
		
		 $(".suf-ass-icon").parents("tr").nextUntil('tr:not(.grade-class)').hide();
	};
	
	TableVM.prototype.saveStudentGroup = function(){
		var self = this;
		var result = [];
		_.each(self.groups() || [] ,function(m){
			_.each(m.grades||[],function(n){
				var checkedInfo ={classId: n.classId,className: n.className,classType: n.type,
						schoolId: n.schoolId,courseSetId: n.courseSetId,gradeName: n.gradeName, groups: []};
				var checked = $('#classId_' + n.classId + (n.courseSetId ? '_' + n.courseSetId : '')).prop('checked');
				if(checked){
					//如果选择班级
					result.push(checkedInfo);
				}else{
					//如果选择分组
					var isAdd = false;
					_.each(n.studentGroupList || [],function(g){
						var gChecked = $('#groupId_' + g.groupId).prop('checked');
						if(gChecked){
							var info = {};
							info.groupId = g.groupId;
							info.groupName = g.groupName;
							checkedInfo.groups.push(info);
							isAdd = true;
						}
					});
					if(isAdd){
						result.push(checkedInfo);
					}
				}
			});
		});
		dialog.close(null,result);
	};
	
	TableVM.prototype.close = function(){
		dialog.close();
	};
	
	TableVM.prototype.choGroup = function(classId){
		$('input[data-class="' + classId + '"]').prop('checked',false);
		return true;
	};
	
	TableVM.prototype.unChoGroup = function(classId){
		var self = this;
		$('input[data-group="' + classId + '"]').prop('checked',false);
		return true;
	};
	
	TableVM.prototype.checkClasses = function(obj,gradeName){
		return true;
	};
	
	ko.applyBindings(new TableVM());
	
	
	$(".pop-groups").live("click",function(){
		//var gradeName=$(this).children("input[type='checkbox']").data("grade");//data-grade
		//$('input[data-gradeName="' + gradeName + '"]').attr('checked',!!$(this).find("input[type='checkbox']").attr("checked"));
		
	});
	
	$(".pop-grades").live("click",function(){
		
//		     var gradeName=$(this).children("input[type='checkbox']").attr("data-gradeName");
//		    
//		     if($('input[data-gradeName="' + gradeName + '"]:not(:checked)').size()>0){
//			    	$('input[data-grade="' + gradeName + '"]').attr('checked',false);
//			    }
//			    else{
//			        $('input[data-grade="' + gradeName + '"]').attr('checked',true);
//			    }
			
	});

  
	
	$("tr:has(.suf-ass-icon)").live("click",function(){
	     _this=this;
	    // $(_this).toggleClass('suf-ass-icon-active');
    	 //$(_this).parents("tr").nextUntil('tr:not(.grade-class)').slideToggle("fast");
    	 
    	     $(_this).find(".suf-ass-icon").toggleClass('suf-ass-icon-active');
    	     $(_this).nextUntil('tr:not(.grade-class)').slideToggle("fast");
           
	});
	
   
           


    
	
});