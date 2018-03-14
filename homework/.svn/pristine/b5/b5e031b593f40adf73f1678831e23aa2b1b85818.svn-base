	 /*
	  *  前端交互
	 * */
	var interaction = {
	    init : function () {
	    	var _this = this;
	    	_this.initMeta();
	    	//选择作业-选择学生-选择时间
	        $('.assign-hd-item').click(function(){
	            var $index=$(this).index();
	            var $dom=$('.assign-flow');
	            if(!$dom.hasClass('assign-flow-time') && $index!=0){
	                return;
	            }
	            switch ($index) {
	                //选择试卷
	                case 0 :
	                    $dom.removeClass('assign-flow-student').removeClass('assign-flow-time');
	                    break;
	                //选择学生
	                case 2 :
	                    $dom.addClass('assign-flow-student').removeClass('assign-flow-time');
	                    break;
	                //选择时间
	                case 4 :
	                    $dom.addClass('assign-flow-time').removeClass('assign-flow-student');
	                    break;
	            }
	        })

	        //下一步
	        $('.assign-bottom-btn-item').on('click', function () {
	            var $index = $(this).index();
	            var $dom=$('.assign-flow');
	            var event = this;
	            switch ($index) {
	                case 0 :
	                    win.snapshotData('resource');
	                    if(FORM_DATA.sections[0].resources.length ==0){
	                    	Utils.Notice.print('请选择作业',2000);
	                    	return;
	                    }
	                    $dom.addClass('assign-flow-student');
	                    break;
	                case 1 :
	                	if(FORM_DATA.sections[0].classes.length == 0 || FORM_DATA.sections[0].classes[0].users.length == 0){
	                		Utils.Notice.print('请选择班级学生',2000);
	                		return;
	                	}
	                	//win.renderCheckedData();
	                    $dom.addClass('assign-flow-time').removeClass('assign-flow-student');
	                    break;
	                case 2 :
	                	if(FORM_DATA.sections[0].resources.length ==0){
	                    	Utils.Notice.print('请选择作业',2000);
	                    	return;
	                    }
	                	if(FORM_DATA.sections[0].classes.length == 0 || FORM_DATA.sections[0].classes[0].users.length == 0){
	                		Utils.Notice.print('请选择班级学生',2000);
	                		return;
	                	}
	                
	                	win.snapshotData('submit',event);
	                    break;
	            }
	        });
	    },
	    initMeta : function (){
	        var $devicePixelRatio=window.devicePixelRatio;
	        if($devicePixelRatio==1.5){
	            $('#homework-meta').attr('content','width=device-width,initial-scale=0.65, maximum-scale=0.65, minimum-scale=0.65')
	        }else{
	            $('#homework-meta').attr('content','width=1920px,initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5')
	        }
	    }
};

	/*
	 * 参照 LayerAssign 对象
	 * */
var FORM_DATA = {sections:[{classes:[],resources:[]}]};
var CLAZZ_USERS= null;
var CLAZZ_NO_USERIDS=[];
var CLAZZ={};
var resJson = $('#j_Def_Resource').val();
var isSaveClick = false;
if(resJson != ''){
	window.Def_Resource =  JSON.parse(resJson);
}else {
	window.Def_Resource = null;
}
var win = {
		queryParams:{
			shareScope:1,
			userResGroupId:null,
			subjectId:null,
			schoolStageId:null,
			isPage:true,
			curPage:1,
			pageSize:10,
			dataSize:null
		},
		tmpl :'{{#.}}<li class="assign-testpaper-bank-li" data-v="{{code}}">{{text}}</li>{{/.}}',
		init:function(){
			interaction.init();
			this.initCstResource();
		    this.bindEvent();
		    var curDate = new LocalDate(parseInt(Leke.currentSystemTime));
		    $('.j-time-start').val(curDate.format('yyyy.MM.dd HH:mm'));
		    var endDate = curDate.add(2,'d');
		    $('.j-time-end').val(endDate.format('yyyy.MM.dd HH:mm'));
		},
		initCstResource :function() {
			var _this = this;
			if(window.Def_Resource != null){
				_this.renderResource(window.Def_Resource);
			}
		},
		renderResource: function(datas){
			var _this = this;
			var html = Mustache.render($('#j-tmpl-resource').html(),datas);
			var resType = datas[0].resType;
			$('.j-resources'+resType).removeClass('resource-list-null');
			$('.j-resources'+resType).append(html);
			_this.snapshotData('resource');
		},
		appendResource: function(datas){
			var dataList = [];
			var exisIds = [];
			$('.j-resources' + datas[0].type +' li').each(function(i,n){
				exisIds.push($(n).data('id'));
			})
			$.each(datas,function(i,n){
				if(exisIds.indexOf(parseInt(n.resId)) == -1){
					dataList.push({suffix:n.typeIcon,resId:n.resId,resName:n.resName,resType:n.type})
				}
			})
			var _this = this;
			var html = Mustache.render($('#j-tmpl-resource').html(),dataList);
			var resType = datas[0].type;
			$('.j-resources'+resType).append(html);
			$('.j-resources'+resType).removeClass('resource-list-null');
			_this.snapshotData('resource');
		},
		bindEvent:function(){
			var _this = this;
			$('.j-btn-paper').click(function(){
				var req = HistoryServicePad.get();
				req.then(function(datas){
					var pref = datas.pref || {}
					Leke.control.openPaper({showHead:true,data: pref,callBack: function(datas){
						_this.appendResource(datas);
					}});
				});
				
			});
			$('.j-btn-courseware').click(function(){
				var $this = $(this);
				var req = HistoryServicePad.get();
				req.then(function(datas){
					var pref = datas.pref || {}
					$this.loadRepository({type:'choseCW',data:pref,callBack:function(datas){
						_this.appendResource(datas);
					}})
				});
				
			});
			$('.j-btn-mic').click(function(){
				var req = HistoryServicePad.get();
				req.then(function(datas){
					var pref = datas.pref || {}
					MicroCourseSelect.select({data: pref,callBack: function(datas){
						_this.appendResource(datas);
					}});
				});
				
			});
			$('.c-assign-single-checkbox').click(function(){
				$(this).toggleClass('c-assign-single-checkbox-false');
				$('.j-openAnswer').toggleClass('f-hide');
				if(!$('.j-openAnswer').hasClass('f-hide')){
					var end = $('.j-time-end').val();
					if(end != '' && $('.j-time-openAnswer').val() == ''){
						var endDate = new LocalDate(new Date(end));
						var openAnswerDate = endDate.add(2,'d');
						$('.j-time-openAnswer').val(openAnswerDate.format('yyyy.MM.dd HH:mm'));
					}
				}
			});
			// 关闭当前页，返回到作业列表页面
			$('.j-window-close').click(function () {
				window.LeKeBridge.sendMessage2Native("close", "");
			});
			//删除资源
			$(document).on('click','.j-resource-del',function(){
				var $parentResUl = $(this).parents('.j-resources');
				$(this).parents('.item').remove();
				if($parentResUl.find('li').length == 0){
					$parentResUl.addClass('resource-list-null');
				}
					
				_this.snapshotData('resource');
				
			})
			
			//班级click
			$(document).on('click','.j-chk-clazz',function(){
			
				var $li = $(this);
				var $chk = $(this).find('.assign-bd-item-checkbox');
				$chk.toggleClass('assign-bd-item-checkbox-on');
				CLAZZ.classId = $li.data('classid');
				CLAZZ.className =  $li.data('classname');
				CLAZZ.classType =  $li.data('classtype');
				$.post('/auth/teacher/assign/users.htm?classId=' +  $li.data('classid'),function(json){
					CLAZZ_USERS = json.datas;
					var groups = json.datas.groups;
					if(groups.length>0){
						groups.unshift({groupId:-1,groupName:'全班'});
					}
					$('.j-ul-clazz-group').html(Mustache.render($('#j-tmpl-clazz-group').html(),groups));
					var users = json.datas.users;
					$('.j-ul-clazz-user').html(Mustache.render($('#j-tmpl-clazz-user').html(),users));
					if($chk.hasClass('assign-bd-item-checkbox-on')){
						$('.j-chk-clazz-group .assign-bd-item-checkbox').first().toggleClass('assign-bd-item-checkbox-on');
						$('.j-chk-clazz-user .assign-bd-item-checkbox').toggleClass('assign-bd-item-checkbox-on');
					}
					_this.snapshotData('student');
					if(groups.length == 0 && users.length == 0){
						$('.j-empty-students').removeClass('f-hide');
					}else{
						$('.j-empty-students').addClass('f-hide');
					}
				})
			});
			//班级分组click
			$(document).on('click','.j-chk-clazz-group',function(){
				var $chk = $(this).find('.assign-bd-item-checkbox');
				$chk.toggleClass('assign-bd-item-checkbox-on');
				var isChecked = $chk.hasClass('assign-bd-item-checkbox-on');
				var groupId= $(this).data('groupid');
				if(groupId > 0){
					$(this).parents('ul').find('.j-chk-clazz-group .assign-bd-item-checkbox').first().removeClass('assign-bd-item-checkbox-on');
					var checkedGroup = [];
					$('.j-chk-clazz-group').each(function(i,group){
						if($(group).data('groupid') > 0 && $(group).find('.assign-bd-item-checkbox').hasClass('assign-bd-item-checkbox-on')){
							checkedGroup.push($(group).data('groupid'));
						}
					});
					if(checkedGroup.length > 0){
						$('.j-ul-clazz-user .assign-bd-item-checkbox').addClass('assign-bd-item-checkbox-on');
						var userIds = [];
						$.each(CLAZZ_USERS.groups,function(i,n){
							if(checkedGroup.indexOf(n.groupId) > -1){
								userIds = userIds.concat(n.userIds);
							}
						});
						var users = $.grep(CLAZZ_USERS.users,function(n){
							if(userIds.indexOf(n.userId)>-1){
								return true;
							}}
						);
						$('.j-ul-clazz-user').html(Mustache.render($('#j-tmpl-clazz-user').html(),users));
						$('.j-chk-clazz-user').each(function(i,n){
							if(CLAZZ_NO_USERIDS.indexOf($(n).data('userid')) == -1){
								$(n).find('.assign-bd-item-checkbox').addClass('assign-bd-item-checkbox-on');
							}
						});
					}else{
						$('.j-ul-clazz-user').html(Mustache.render($('#j-tmpl-clazz-user').html(),CLAZZ_USERS.users));
						CLAZZ_NO_USERIDS = [];
					}
				}else{
					$('.j-ul-clazz-user').html(Mustache.render($('#j-tmpl-clazz-user').html(),CLAZZ_USERS.users));
					CLAZZ_NO_USERIDS = [];
					if(isChecked){
						$('.j-chk-clazz-user').find('.assign-bd-item-checkbox').addClass('assign-bd-item-checkbox-on');
						$('.j-chk-clazz-group').each(function(i,group){
							if($(group).data('groupid') > 0){
								$(group).find('.assign-bd-item-checkbox').removeClass('assign-bd-item-checkbox-on');
							}
						});
					}else{
						$('.j-chk-clazz-user .assign-bd-item-checkbox').removeClass('assign-bd-item-checkbox-on');
					}
				}
				_this.setClazzCheckState();
				_this.snapshotData('student');
			})
			$(document).on('click','.j-chk-clazz-user',function(){
				$(this).find('.assign-bd-item-checkbox').toggleClass('assign-bd-item-checkbox-on');
				var userId = $(this).data('userid');
				if(!$(this).find('.assign-bd-item-checkbox').hasClass('assign-bd-item-checkbox-on') && !$('.j-chk-clazz-group').first().hasClass('assign-bd-item-checkbox-on')){
					CLAZZ_NO_USERIDS.push(userId);
				}
				if($(this).find('.assign-bd-item-checkbox').hasClass('assign-bd-item-checkbox-on') && !$('.j-chk-clazz-group .assign-bd-item-checkbox').first().hasClass('assign-bd-item-checkbox-on')){
					CLAZZ_NO_USERIDS.splice(CLAZZ_NO_USERIDS.indexOf(userId),1);
				}
				_this.setClazzCheckState();
				_this.snapshotData('student');
			})
			
		},
		setClazzCheckState :function(){
			if($('.j-chk-clazz-user .assign-bd-item-checkbox-on').length > 0){
				$('.j-chk-clazz').each(function(i,n){
					if($(n).data('classid') == CLAZZ.classId){
						$(n).find('.assign-bd-item-checkbox').addClass('assign-bd-item-checkbox-on');
					}
				});
			}else{
				$('.j-chk-clazz').each(function(i,n){
					if($(n).data('classid') == CLAZZ.classId){
						$(n).find('.assign-bd-item-checkbox').removeClass('assign-bd-item-checkbox-on');
					}
				});
			}
		},
		snapshotData:function(type,event){
			if(type == 'resource'){
				//保存选择试卷
				FORM_DATA.sections[0].resources = [];
				$('.j-resources .item').each(function(i,n){
					FORM_DATA.sections[0].resources.push({resId : $(n).data('id'),resName : $(n).text(),resType:$(n).data('type')});
				});
			}else if(type == 'student'){
				//保存选择学生信息
				$.each(FORM_DATA.sections[0].classes,function(i,n){
					if(n.classId == CLAZZ.classId){
						FORM_DATA.sections[0].classes.splice(i,1);
					}
				});
				if($('.j-chk-clazz-user .assign-bd-item-checkbox-on').length == 0){
					return;//没有选择学生，什么也不保存
				}
				var curClassInfo = {classId:CLAZZ.classId,className:CLAZZ.className,classType:CLAZZ.classType};
				curClassInfo.groups = [];
				curClassInfo.users =[];
				$('.j-chk-clazz-group .assign-bd-item-checkbox-on').each(function(i,n){
					if($(n).data('groupid') > 0 ){
						curClassInfo.groups.push({groupId:$(n).data('groupid'),groupName:$(n).data('groupname')});
					}
				});
				$('.j-chk-clazz-user').find('.assign-bd-item-checkbox-on').each(function(i,n){
					curClassInfo.users.push({userId:$(n).data('userid'),userName:$(n).data('username')});
				});
				FORM_DATA.sections[0].classes.push(curClassInfo);
			}else if(type == 'submit'){
				if(isSaveClick){
					return;
				}
				//生成布置的作业
				var start = $('.j-time-start').val();
				var close = $('.j-time-end').val();
				if(start == ''){
					Utils.Notice.print('请选择作业开始时间',2000);
					return;
				}
				if(close == ''){
					Utils.Notice.print('请选择作业截止时间',2000);
					return;
				}
				
				FORM_DATA.startTime =new Date(start).getTime();
				FORM_DATA.closeTime = new Date(close).getTime();
				if(FORM_DATA.closeTime <= FORM_DATA.startTime){
					Utils.Notice.print('作业截止时间不能小于开始时间',2000);
					return;
				}
				if($('.j-chk-openAnswerTime').hasClass('c-assign-single-checkbox-false')){
					var openAnswerTime = $('.j-time-openAnswer').val();
					if(openAnswerTime == ''){
						Utils.Notice.print('请设置公布答案时间',2000);
						return;
					}else{
						FORM_DATA.openAnswerTime =new Date(openAnswerTime).getTime();
					}
				}
				var mask = Utils.Notice.mask;
				mask.create("提交中……")


				$.post('/auth/teacher/assign/saveAssign.htm', {
					dataJson : JSON.stringify(FORM_DATA)
				}, function(json) {
					if (json.success) {
						isSaveClick = true;
						Utils.Notice.print('布置成功');
						mask.close();
						if (window.Def_Resource) {
							SourcePlugin.goHomeWork();
						} else {
							window.LeKeBridge.sendMessage2Native(
									"backHomeworkList", "");
						}
					}
				})
			}
		}	
}
win.init();



/*});*/


