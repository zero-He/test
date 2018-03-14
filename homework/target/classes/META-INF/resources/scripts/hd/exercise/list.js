Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  
 var TrainList = {
            init: function(){
                this.proviso();
                this.reset();
                this.closeOption();
            },
            proviso : function(){
                var that = this;
                var gosearch = $('.gosearch');
                gosearch.each(function(index, el) {
                    $(this).on('click',function(){
                        var self = this;
                        var proviso = $(self).find('.proviso');
                        if(!$(self).hasClass('active')){
                            $(self).addClass('active');
                            $(self).siblings().removeClass('active').find('.proviso').hide();
                            proviso.show();
                        }else{
                            $(self).removeClass('active');
                            proviso.hide();
                        }
                    });
                    that.chooseOption(this);
                });
            },
            chooseOption: function(optParent,selectedFunc) {
                var self = this;
                var optParent = optParent;
                var name = $(optParent).find('.name');
                var option = $(optParent).find('li');

                option.click(function() {
                    var optVal = $(this).text();
                    name.text(optVal);
                    //重置查询条件
                    var $option = $(this).find('a');
                    ExerciseList.queryParams[$option.data('param')] = $option.data('v');
                    ExerciseList.refreshList();
                });
            },
            reset: function(){
                $('.all').click(function(){
                    $('.gosearch').find('.name').each(function(index, el) {
                        var defaulted = $(this).data('defaulted');
                        $(this).text(defaulted);
                        //重置查询条件
    	                ExerciseList.queryParams.subjectId = null;
    	                ExerciseList.queryParams.timeRangeDays = null;
    	                ExerciseList.queryParams.startTime = null;
    	                ExerciseList.queryParams.endTime = null;
    	                ExerciseList.refreshList();
                    });
                })
            },closeOption: function() {
                $(window).click(function(ev){
                    var target = ev.target;
                    if($(target).parents('.c-headsearch').length === 0){
                        $('.gosearch').removeClass('active');
                        $('.proviso').hide();
                    }
                })
            }
}


//业务js
var ExerciseList = {
		queryParams:{
			timeRangeDays:null,
			subjectId:null,
			startTime : null,
			endTime : null,
			pageSize:15,
			curPage :1
		},
		init : function() {
			this.initQueryParams();
			this.binEvent();
			var scrollTopLoc = localStorage.exerciseListTop;
	        $('body').animate({scrollTop:scrollTopLoc});
		},
		binEvent : function() {
			var _this = this;
			$(document).on('click','.j-exercise-do',function(){
				_this.tagScroll();
				var exerciseId = $(this).parent('li').data('exerciseid');
				//回传给平板端
				//String
				//window.auto_practice.doExerciseWork(exerciseId);
				LeKeBridge.sendMessage2Native('doExerciseWork',JSON.stringify({'exerciseId':exerciseId}));
			});
			$(document).on('click','.j-view-report',function(){
				_this.tagScroll();
				var exerciseId = $(this).parent('li').data('exerciseid');
				var title =  $(this).parent('li').data('title');
				var type = $(this).parent('li').data('type');
				var subTitle = _this.subTitle(title,type);	
				//string,string
				//window.auto_practice.viewReport(exerciseId,subTitle);
				LeKeBridge.sendMessage2Native('viewReport',JSON.stringify({'exerciseId':exerciseId,'subTitle':subTitle}));
			});
		},
		tagScroll:function(){
			var scrollTopVal = $('body').scrollTop();
            localStorage.exerciseListTop = scrollTopVal;
		},
		subTitle:function(exerciseName,exerciseType){
			if (exerciseName.indexOf(',') > -1) {
				var ary = exerciseName.split(',');
				exerciseName = ary[0] + "等" + (ary.length)
						+ (exerciseType == 2 ? "个知识点" : "个章节");
			}
			return exerciseName;
		},
		findDataList: function(pageNum){
			var _this = this;
			var queryParams = _this.queryParams;
			if(_this.queryParams.timeRangeDays == 'def'){
				queryParams.startTime = $('#startDate').val();
				queryParams.endTime = $('#endDate').val();
			}
			queryParams.curPage = pageNum;
			var dataList = [];
			$.ajax({
				url:'/auth/hd/student/exercise/ajax/loadList.htm',
				data:queryParams,
				type:'post',
				async:false,
				success:function(json){
						var datas = json.datas.page.dataList;
						if(datas.length == 0 || json.datas.page.totalPage < pageNum){
							return;
						}
						datas.fmtSubmitTime = function(){
							if(this.submitTime == null){
								return '--';
							}
							return new Date(this.submitTime).Format('yyyy-MM-dd hh:mm:ss');
						}
						datas.fmtAccuracy = function(){
							if(this.accuracy == null) return '--';
							var rate = this.accuracy + '%';
							if(this.growth != null){
								if(this.growth ==0){
									return rate +'<i class="sm-trend sm-trend3"></i>';
								}else if(this.growth > 0){
									return rate +'<i class="sm-trend sm-trend1"></i>';
								}else{
									return rate +'<i class="sm-trend sm-trend2"></i>';
								}
							}else{
								return rate;
							}
						}
						datas.fmtDiffLevelName = function(){
							return _this.getLevelName(this.difficultyLevel);
						};
						datas.fmtExerciseType = function (){
							if(this.exerciseType == 1){
								return '章节';
							}
							return '知识点';
						}
						dataList = datas;
				}
			});
			return dataList;
		},
		refreshList : function (){
			this.dropObj.opts.loadUpFn(this.dropObj);
		},
		renderList : function (datas,isAppend){
			var html = Mustache.render($('#j-tmpl-exercises').html(),datas);
			if(isAppend){
				$('#j-body-exercises').append(html);
			}else{
				$('#j-body-exercises').html(html);
			}
		},
		getLevelName : function(level){
			switch (level) {
			case 1:
				return "容易";
			case 2:
				return "较易";
			case 3:
				return "一般";
			case 4:
				return "较难";
			case 5:
				return "困难";
			default:
				return "";
			}
		},
		initQueryParams : function(){
			var _this = this;
			//渲染学科
			var schoolStages = Leke.userPref.authority.schoolStages;
			var datas=[];
			$.each(schoolStages,function(i,stage){
				$.each(stage.subjects,function(j,subj){
					var isExists = false;
					if($.each(datas,function(k,subj2){
						if(subj2.code == subj.subjectId){
							isExists = true;
						}
					}));
					if(!isExists){
						datas.push({code:subj.subjectId,name:subj.subjectName});
					}
				});
			});
		
			datas.sort(function(a,b){
				return a["code"] - b["code"];
			});
			_this.renderParamHtml('subjectId',datas);
			  TrainList.init();
		},
		renderParamHtml : function(paramName,data){
			var temple = '{{#.}}<li><a data-param="{{param}}" data-v="{{code}}">{{name}}</a></li>{{/.}}'
				.replace(/{{param}}/g,paramName);
			var html = Mustache.render(temple,data);
			var $ul = $('.j-ul-'+paramName);
			$ul.html(html);
		}
		
	};
	ExerciseList.init();

	    // 页数
	    var page = 0;
	    // 每页展示个数
	    var size = ExerciseList.queryParams.pageSize;

	    // dropload
	    ExerciseList.dropObj =  $('.j-content').dropload({
	        scrollArea : window,
			autoLoad:true,
			threshold : 50,
	        loadUpFn : function(me){
	        	page = 1;
	        	var datas = ExerciseList.findDataList(page);
            	ExerciseList.renderList(datas,false); 
				me.resetload();
				me.unlock();
				 if(datas.length > size){
					me.noData(false); 
				 }else{
					me.noData(); 
				 }
               
			
	        },
	        loadDownFn : function(me){
	            page++;
	            var datas = ExerciseList.findDataList(page);
	            if(datas.length > 0 ){
	            	ExerciseList.renderList(datas,true); 
                // 如果没有数据
                }
	            if(datas.length == 0 ||datas.length < size){
                    me.lock();
                    me.noData();
                }
               // 每次数据插入，必须重置
               me.resetload();
	        }
	    });

