var DoubtTools = {
	
	//缓存
	dataCache : {},
	
	//选择教材版本
	selectVersion : function(checkSubject){
		
		//定义参数
		var schoolStageId = $(".j-choose-version").data("i");
		var subjectId = null;
		var pressId = null;
		var materialId = null;
		var materialNodeId = null;
		var v = $(".j-choose-version").data("v");
		var isM = v=="m";
		var up = isM?'arrow-up':'c-arrow-up';
    	var down = isM?'arrow-down':'c-arrow-down';
    	var hidden = 'c-hidden';
    	var checkSubject = checkSubject||false;
		//定义方法
		function showChooseVersion() {
			subjectId = $('.result').data("subjectid");
			if(checkSubject&&!subjectId){
				Utils.Notice.alert("请先选择科目和老师");
				return;
			}
        	if(isM){
        		$('.choose-version').show();
        		$('.choose-version .content').animate({
        			bottom: 0
        		});
        	}else{
        		$(".j-choose-version").removeClass(hidden);
        	}
        	var a = $('#j-choose-version .version-result').data("a");
            var b = $('#j-choose-version .version-result').data("b");
            var c = $('#j-choose-version .version-result').data("c");
            var d = $('#j-choose-version .version-result').data("d");
            queryPresses(function(){
        		initChooseLi("#jPresses",a,choosePresses,function(){
        			if(a){
	        			initChooseLi("#jMaterials",b,chooseMaterials,function(){
	        				if(b){
	        					initChooseLi("#jSectionRange",c,chooseSectionRange,function(){
	        						if(c){
	        							initChooseLi("#jSectionChapter",d,chooseSectionChapter);
	        						}
	        					});
	        				}
	        			});
        			}
        		});
            });
        }
		//获取默认值的选择项
		function initChooseLi(m,v,fun,callback){
			$(m).find("li").each(function(){
				var i = $(this).data("i");
				if(i==v){
					fun(this,callback);
				}
			});
		}
		
		//关闭
        function hideChooseVersion() {
        	
        	if(isM){
        		$('.choose-version .content').animate({
        			bottom: '-224px'
        		}, function() {
        			$('.choose-version').hide();
        		});
        	}else{
        		$(".j-choose-version").addClass(hidden);
        	}
        	cleanPresses();
        }
        //选择后操作
        function choseUIMove(v){
        	$(v).addClass('active').siblings().removeClass('active');
            $(v).parent().prev().children('.selected').text($(v).text());
            $(v).parent().prev().children('.selected').data("i",$(v).data("i"));
            $(v).closest("ul").addClass(hidden);
            $(v).closest("ul").prev().children('span').removeClass(down);
            $(v).closest("ul").prev().children('span').addClass(up);
        }
        //查询教材版本
        function queryPresses(callback){
        	var option = {
        		schoolStageId : schoolStageId,
        		subjectId : subjectId	
        	}
			$.post("/auth/common/basicData/presses.htm",option,function(datas){
				var output = Mustache.render($("#jVersionPressesListTpl").html(), datas.datas);
				$("#jPresses").html(output);
				if(callback){
					callback();
				}
			})
		}
        //查询年级或课本
		function queryMaterials(callback){
			var option = {
					schoolStageId : schoolStageId,
					subjectId : subjectId,
					pressId : pressId
	        	}
			$.post("/auth/common/basicData/materials.htm",option,function(datas){
				var output = Mustache.render($("#jVersionMaterialsListTpl").html(), datas.datas);
				$("#jMaterials").html(output);
				if(callback){
					callback();
				}
			})
		}
		//查询年级或课本
		function querySection(callback){
			var option = {
					parentId : materialNodeId,
					materialId : materialId,
					rootId : 0,
					showMode : "section",
					visitMode : "child"
				};
			$.post("/tag/tree/materialTreeDataService/queryTreeNodes.htm",option,function(datas){
				if(callback){
					callback(datas);
				}
			})
		}
		//清除
		function clean(v){
			$(v).prev().children('.selected').text("未选择");
			$(v).prev().children('.selected').data("i","");
			$(v).addClass(hidden);
            $(v).prev().children('span').removeClass(down);
            $(v).prev().children('span').addClass(up);
            $(v).html("");
		}
		//清除教材选择
		function cleanPresses(){
			clean("#jPresses");
			cleanMaterials();
		}
		//清除课本选择
		function cleanMaterials(){
			clean("#jMaterials");
			cleanSectionRange();
		}
		//清除单元选择
		function cleanSectionRange(){
			clean("#jSectionRange");
			cleanSectionChapter();
		}
		//清除章节选择
		function cleanSectionChapter(){
			clean("#jSectionChapter");
		}
		//选择教材
		function choosePresses(v,callback){
        	choseUIMove(v);
        	cleanMaterials();
            pressId = $(v).data("i");
            queryMaterials(callback);
        }
        
		//选择课本
        function chooseMaterials(v,callback){
        	choseUIMove(v);
        	cleanSectionRange();
        	materialId = $(v).data("i");
        	materialNodeId = 0;
        	querySection(function(data){
        		if(data&&data.length>0){
        			materialNodeId = data[0].materialNodeId;
        			querySection(function(data){
        				var datas = {
        						section : data
        				}
        				var output = Mustache.render($("#jVersionSectionListTpl").html(), datas);
        				$("#jSectionRange").html(output);
        				if(callback){
        					callback();
        				}
        			});
        		}
        	});
        }
        //选择单元
        function chooseSectionRange(v,callback){
        	choseUIMove(v);
        	cleanSectionChapter();
        	materialNodeId = $(v).data("i");
            querySection(function(data){
				var datas = {
						section : data
				}
				var output = Mustache.render($("#jVersionSectionListTpl").html(), datas);
				$("#jSectionChapter").html(output);
				if(callback){
					callback();
				}
			});
        }
        //选择章节
        function chooseSectionChapter(v){
        	choseUIMove(v);
        	materialNodeId = $(v).data("i");
        }
		
		//绑定事件
		$('#j-choose-version').click(showChooseVersion);

        // 取消
		$('.j-choose-version .cancel').click(hideChooseVersion);

        // 确定
        $('.j-choose-version .save').click(function() {
            var text = "";
            var $selected = $('.selected');
            $('#j-choose-version .version-result').data("a","");
            $('#j-choose-version .version-result').data("b","");
            $('#j-choose-version .version-result').data("c","");
            $('#j-choose-version .version-result').data("d","");
            if ($selected.length) {
                $selected.each(function (val) {
                	var i = $(this).data("i");
                	if(i){
                		var v = $(this).data("v");
                		$('#j-choose-version .version-result').data(v,i);
                		text += $(this).text();
                	}
                });
            }
            var c = $('#j-choose-version .version-result').data("c");
            var materialNodeId = $('#j-choose-version .version-result').data("d")||c;
            if(materialNodeId){
            	$('#j-choose-version .version-result').text(text.trim());
            	hideChooseVersion();
            }else{
            	Utils.Notice.alert("请选择教材章节");
            }
        });
        
        $('#jPresses').on('click', 'li', function() {
        	choosePresses(this);
        });
        
        $('#jMaterials').on('click', 'li', function() {
        	chooseMaterials(this);
        });
        
        $('#jSectionRange').on('click', 'li', function() {
        	chooseSectionRange(this);
        });
        
        $('#jSectionChapter').on('click', 'li', function() {
        	chooseSectionChapter(this);
        });
        // 选择下拉框
        $('.version-title').on('click', function() {
            if($(this).children('span').hasClass(up)) {
                $(this).children('span').removeClass(up);
                $(this).children('span').addClass(down);
                $(this).next().removeClass(hidden);
            }else {
                $(this).children('span').removeClass(down);
                $(this).children('span').addClass(up);
                $(this).next().addClass(hidden);
            }
        });
	},
    
    /* 选择科目和老师 */
    selectSubjectAndTeacherForPhone: function() {
    	this.loadTeacherSubject();
        $('#j-choose').click(showChooseBox);

        // 取消
        $('.choose-box .cancel').click(hideChooseBox);

        $('.choose-box').click(function() {
            hideChooseBox();
        });

        $('.choose-box .content').click(function(ev) {
            ev.stopPropagation();
        });
        
        // 确定
        $('.choose-box .save').click(function() {
            var subject = '',
                teacher = '';

            var $selected = $('.selected');
            if ($selected.length) {
                subject = $selected.find('.subject').text();
                teacher = $selected.find('.teacher').text();
                subjectId = $selected.data("subjectid");
                teacherId = $selected.data("teacherid");
                $('.result').text(subject + ' ' + teacher);
                $('.result').data("subjectid",subjectId);
                $('.result').data("teacherid",teacherId);
                $('.result').data("subjectname",subject);
                hideChooseBox();
            }
        });

        // 选择
        $('.choose-box .lists').on('click', 'li', function() {
            $(this).addClass('selected').siblings().removeClass('selected');
        });

        function showChooseBox() {
            $('.choose-box').show();
            $('.choose-box .content').animate({
                bottom: 0
            });
        }

        function hideChooseBox() {
            $('.choose-box .content').animate({
                bottom: '-224px'
            }, function() {
                $('.choose-box').hide();
            });
        }
    },

    /* 选择科目和老师 */
	selectSubjectAndTeacherForPad : function() {
		this.loadTeacherSubject();
		$('#j-choose').click(showChooseBox);

		// 取消
		$('.c-choose-box .cancel').click(hideChooseBox);

		// 确定
		$('.c-choose-box .save').click(
				function() {
					var $selected = $('.c-choose-box .cur');
					if ($selected.length == 2) {
						var teacherId = $($selected[0]).data("teacherid")
								|| $($selected[1]).data("teacherid");
						var subjectId = $($selected[0]).data("subjectid")
								|| $($selected[1]).data("subjectid");
						var teacher = $($selected[0]).data("teachername")
								|| $($selected[1]).data("teachername");
						var subject = $($selected[0]).data("subjectname")
								|| $($selected[1]).data("subjectname");
						$('.result').text(subject + ' ' + teacher);
						$('.result').data("subjectid", subjectId);
						$('.result').data("teacherid", teacherId);
						$('.result').data("subjectname", subject);
						hideChooseBox();
					}
				});

		// 选择
		$('.c-choose-box .lists').on('click', 'li', function() {
			$(this).addClass('selected').siblings().removeClass('selected');
		});

		function showChooseBox() {
			$(".teachercon").hide();
			$(".subject .item").eq(0).addClass("cur");
			var subjectId = $(".subject .item").eq(0).data("subjectid");
			$("#subjectTeacher_" + subjectId).show();
			$('.c-choose-box').show();
			$('.c-choose-box .content').animate({
				bottom : 0
			});
		}

		function hideChooseBox() {
			$('.c-choose-box').hide();
			$(".c-choose-box .item").removeClass("cur");
		}
	},

    /* 拍照 -- 预览上传 */
    takePhoto: function() {
    	
        function getPhoto(type, sucessCallback) {
            navigator.camera.getPicture(onSuccess, onError, {
                quality: 50,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType[type]
            });
        
            function onSuccess(imageData) {
                var dataURL = "data:image/jpeg;base64," + imageData;
                compress(dataURL,sucessCallback);
            }
        
            function onError(err) {
                
            }
        }
        
        $("#jPhotos").click(function(){
        	if($(".j-img-preview").length>=10){
				Utils.Notice.alert("图片不能超过10张");
				return;
			}
        	getPhoto("PHOTOLIBRARY",function(base64){
        		var html = '<li><img class="j-img-preview" src="' + base64 + '" alt="" id="preview"><i class="del"></i></li>';
        		if($(".j-img-preview").length>=10){
        			Utils.Notice.alert("图片不能超过10张");
        			return;
        		}
        		$('.preview ul').prepend($(html));
        	});
        });
        
        $("#jCamera").click(function(){
        	if($(".j-img-preview").length>=10){
				Utils.Notice.alert("图片不能超过10张");
				return;
			}
        	getPhoto("CAMERA",function(base64){
        		var html = '<li><img class="j-img-preview" src="' + base64 + '" alt="" id="preview"><i class="del"></i></li>';
        		if($(".j-img-preview").length>=10){
        			Utils.Notice.alert("图片不能超过10张");
        			return;
        		}
        		$('.preview ul').prepend($(html));
        	});
        });
        
        $('.preview ul').on("click",".del",function(){
        	$(this).parent().remove();
        });
    },

    /* 录音 */
    record: function() {
    	var src = "media.m4a";
    	var _this = this;
		var mediaRecord = null;
		var mediaPlay = null;
		var timer = null;
		var recordTime = 0;
		var saverecordTime = 0;
		
		$.fn.circleProgress = function(val) {
			if (val < 0) {
				val = 1;
			}
			
			return this.each(function() {
		        var $lc = $(this).find('.l-circle'),
		            $rc = $(this).find('.r-circle');

		        var angle = 360 * val - 45;    // 初始 -45度角

				if (angle === -45) {
					 $rc.css('transform', 'rotate(' + angle + 'deg)');
					 $rc.css('-webkit-transform', 'rotate(' + angle + 'deg)');
					 $lc.css('transform', 'rotate(' + angle + 'deg)');
					 $lc.css('-webkit-transform', 'rotate(' + angle + 'deg)');
				} else if (angle <= 135 ) {
		            $rc.css('transform', 'rotate(' + angle + 'deg)');
		            $rc.css('-webkit-transform', 'rotate(' + angle + 'deg)');
		        } else {
		            angle = angle - 180;
		            $rc.css('transform', 'rotate(135deg)');
		            $rc.css('-webkit-transform', 'rotate(135deg)');
		            $lc.css('transform', 'rotate(' + angle + 'deg)');
		            $lc.css('-webkit-transform', 'rotate(' + angle + 'deg)');
		        }
		    });
		};
		
		function showChooseBox() {
			if(!$("#j-media").parent().hasClass("media-forbidden")){
				$('#jRecordDiv').show();
				if($("#jRecordDiv .content").length>0){
					$('#jRecordDiv .content').animate({
						bottom: 0
					});
				}
			}
        }
		
        function hideChooseBox() {
        	if($("#jRecordDiv .content").length>0){
        		$('#jRecordDiv .content').animate({
        			bottom: '-224px'
        		}, function() {
        			$('#jRecordDiv').hide();
        		});
        	}else{
        		$('#jRecordDiv').hide();
        	}
            recordAgain();
        }
        
		function recordAgain(){
			stopMedia();
			$('#jRecord').show();
			$('#jRecord').removeClass('c-recording').addClass('c-record');
        	$('#jRecordAgain').hide();
        	$('#jPlay').hide()
        	$('.m-circleprogress').circleProgress(0);
        	$("#jTips").show();
        	$("#jShowMediaTime").html("");
        	if(mediaPlay){
        		mediaPlay.stop();
        		mediaPlay = null;
        		$('.m-circleprogress').circleProgress(0);
				$(".media-btn").removeClass("do-wave");
        	}
        	recordTime = 0;
		}
		
		function recordBegian(){
			$('#jRecord').removeClass('c-record').addClass('c-recording');
			$('#jRecordAgain').hide();
			$('#jPlay').hide()
            recordTime = 0;
            timer = setInterval(function() {
            	recordTime++;
            	var miao = recordTime%60;
    			var fen = parseInt(recordTime/60);
    			if(fen>=3){
    				clearInterval(timer);
    				timer = null;
    				recordStop();
    			}
    			var str = "";
    			if(fen){
    				str += fen+"'";
    			}
    			str += miao+"''";
    			$('#jTips').hide();
    			$("#jShowMediaTime").html(str);
            }, 1000);
            mediaRecord = new MediaRecord();
            mediaRecord.record(src);
		}
		
		function recordStop(){
			$('#jPlay').show()
			$('#jRecordAgain').show();
			$('#jRecord').hide();
			stopMedia();
		}
		
		function stopMedia(){
			if(mediaRecord){
        		mediaRecord.stop();
        	}
        	if(timer){
        		clearInterval(timer);
        	}
		}
		
		$('#j-media').click(showChooseBox);
        // 取消
        $('#jRecordDiv .cancel').click(hideChooseBox);
        // 确定
        $('#jRecordDiv .save').click(function() {
        	if(!recordTime){
        		Utils.Notice.alert("请录音");
        		return;
        	}
        	saverecordTime = recordTime;
        	$("#jAudioDiv").show();
        	$("#jAudioLength").html($("#jShowMediaTime").html());
            hideChooseBox();
            $("#j-media").parent().addClass("media-forbidden");
        });
        // 重新录音
        $("#jRecordAgain").click(recordAgain);
        // 开始录音
        $('#jRecord').on('click',function(){
        	if($(this).hasClass("c-recording")){
				recordStop();
			}else{
				recordBegian();
			}
        });
        // 播放录音
        $('#jPlay').click(function(){
        	if(recordTime){
        		if(!mediaPlay){
    				mediaPlay = new MediaPlay();
    				$('.m-circleprogress').circleProgress(0);
    				mediaPlay.play(src,recordTime,function(position) {
    					var progress = position / (recordTime * 1000);
    					$('.m-circleprogress').circleProgress(progress);
    				},function() {
    					$('.m-circleprogress').circleProgress(-1);
    					mediaPlay = null;
    				});
    				$('#jPlay').removeClass('play').addClass('playing');
    			}
        	}
        });
        $("#jRemoveMedia").click(function(){
        	recordTime = 0;
        	$("#jAudioDiv").hide();
        	$("#j-media").parent().removeClass("media-forbidden");
        });
        $(".media-btn").click(function(){
        	if(!mediaPlay){
				mediaPlay = new MediaPlay();
				mediaPlay.play(src,saverecordTime,function(position) {
				},function() {
					$(".media-btn").removeClass("do-wave");
					mediaPlay = null;
				});
			}
        });
    },
    /* 手写板 */
	handbord : function() {
		var _this = this;
		$('#j-handbord').click(showChooseBox);

		// 取消
		$('.c-handbord .cancel').click(hideChooseBox);

		function showChooseBox() {
			if($(".j-img-preview").length>=10){
				Utils.Notice.alert("图片不能超过10张");
				return;
			}
			$('.c-handbord').show();
		}

		function hideChooseBox() {
			$('.c-handbord').hide();
			handboard.clean.bind(handboard)();
		}
		var handboard = new Handboard({
			canvas : document.querySelector('#board'),
			width : 748,
			height : 426,
			lineWidth : '3',
			lineColor : '#000'
		});

		/* 生成图片 */
		$('#jSaveHandBord').click(function() {
			var dataUrl = handboard.getBase64.bind(handboard)();
			if(dataUrl){
	            var html = '<li><img class="j-img-preview" src="' + dataUrl + '" alt="" class="" id="preview"><i class="del"></i></li>';
	            if($(".j-img-preview").length>=10){
	            	Utils.Notice.alert("图片不能超过10张");
        			return;
        		}
	            $('.preview ul').prepend($(html));
			}
			hideChooseBox();
		});

		$('#clean').click(handboard.clean.bind(handboard));
	},
	audioBase64FileUpload : function(){
		var _this = this;
		var mission = new Mission(function(){
			if(MediaData&&$("#jAudioLength").html()){
				_this.base64DateUpload(MediaData,"/auth/"+MOrHd()+"/upload/audioUpload.htm",function(evt){
					try{
						var data = JSON.parse(evt.target.responseText);
						if(data&&data.p&&data.p.success){
							mission.back(data.p.datas.url0);
						}
					}catch(e){
						mission.back();
					}
				});
			}else{
				mission.back();
			}
		});
		return mission;
	},
	imgBase64FileUpload : function(){
		var _this = this;
		var imgs = $(".preview img");
		var promiseArray = [];
		for(var i=0;i<imgs.length;i++){
			var src = imgs[i].src;
			var mission = new Mission(
				(function(src) {
					return function (){
						_this.base64DateUpload(src,"/auth/"+MOrHd()+"/upload/imageUpload.htm",function(evt){
							try{
								var data = JSON.parse(evt.target.responseText);
								if(data&&data.p&&data.p.success){
									mission.back("<img src='"+data.p.datas.url1+"'/>");
								}else{
									mission.back();
								}
							}catch(e){
								mission.back();
							}
						},function(e){
							mission.back();
						});
					}
				})(src)
		    );
			promiseArray.push(mission);
			


			
		}
		return promiseArray;
	},base64DateUpload : function(data,url,success,err){
		var base64Date = data.split(",");
		var xhr = new XMLHttpRequest(),
        fromData = new FormData();
        fromData.append("file", base64Date[1]);
        xhr.addEventListener("load", function(evt){
        	try{
        		if(success){
        			success(evt)
        		}
        	}catch(e){
        		if(err){
        			err(e);
        		}
        	}
        }, false);
        xhr.open("post",url, true);
        xhr.send(fromData);
	},
	loadTeacherSubject:function(){
		var subjectMap = {};
		var schoolStages = Leke.userPref.authority.schoolStages;
		if(schoolStages){
			for(var i=0;i<schoolStages.length;i++){
				var subjects = schoolStages[i].subjects;
				if(subjects){
					for(var j=0;j<subjects.length;j++){
						var subject = subjects[j];
						delete subject.isSelected;
						subjectMap[subject.subjectId]=subject;
					}
				}
			}
		}
		var subjectList = [];
		for(var subject in subjectMap){
			subjectList.push(subjectMap[subject]);
		}
		
		var data = DoubtTools.dataCache["Teachers"];
		if(data){
			executeData(data);
		}else{
			$.post("getTeachers.htm",{data:JSON.stringify({subjectList:subjectList})},function(data){
				DoubtTools.dataCache["Teachers"] = data;
				executeData(data);
			});
		}
		
		function executeData(data){
			
			var output = Mustache.render($("#jListTpl").html(), data.datas);
			$("#jListBody").append(output);
			var subjectId = $('.result').data("subjectid");
			var teacherId = $('.result').data("teacherid");
			if(subjectId&&teacherId){
				// 选择
				$('.choose-box .lists li').each(function(){
					var _subjectId = $(this).data("subjectid");
					var _teacherId = $(this).data("teacherid");
					if(_subjectId==subjectId&&_teacherId==teacherId){
						$(this).addClass('selected').siblings().removeClass('selected');
						var subject = $(this).find('.subject').text();
		                var teacher = $(this).find('.teacher').text();
						$('.result').text(subject + ' ' + teacher);
					}
				});
			}
		}
	},
	commonEvent : function(){
		$("#jDescribe").on("keyup change paste input", function() {
			var txt = $(this).val();
			if (txt) {
				$("#jCount").html(txt.length);
			}else{
				$("#jCount").html("0");
			}
		});
		$(".media-btn").click(function() {
			var audio = $(".do-wave").siblings()[0];
			if (audio) {
				audio.pause();
			}
			$(".media-btn").removeClass("do-wave");
			$(this).addClass("do-wave");
			audio = $(this).siblings()[0];
			if (audio) {
				audio.loop = false;
				audio.addEventListener('ended', function () {  
				     $(".media-btn").removeClass('do-wave');
				}, false);
				audio.play();
			}
		});
	},
	doubtSubmit : function(datas,fun){
		$(".m-load").show();
		var jDescribe = trim($("#jDescribe").val())||"";
		jDescribe = jDescribe.replace(/([\uE000-\uF8FF]|\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDDFF])/g, '');
		if(jDescribe){
			jDescribe += "<br>";
		}
		var schoolStageId = $(".j-choose-version").data("i");
		var pressId = $('#j-choose-version .version-result').data("a");
		var materialId = $('#j-choose-version .version-result').data("b");
		var materialNodeId = $('#j-choose-version .version-result').data("d")||$('#j-choose-version .version-result').data("c");
		var materialPathName = $('#j-choose-version .version-result').text();
		var promiseArray = DoubtTools.imgBase64FileUpload();
		var duration = $("#jAudioLength").html()||"";
		var audioMission = DoubtTools.audioBase64FileUpload();
		promiseArray.push(audioMission);
		new Mission().all(promiseArray).run(function(result) {
			var doubtAudio = null;
			for(var i in result){
				var d = result[i];
				if(d.indexOf("<img")<0){
					doubtAudio = d;
					result.splice(i, 1)
				}
			}
			var content = jDescribe +result.join("<br>");
			if(content||doubtAudio){
				datas["duration"] = duration;
				if(materialNodeId){
					datas["schoolStageId"] = schoolStageId;
					datas["pressId"] = pressId;
					datas["materialId"] = materialId;
					datas["materialNodeId"] = materialNodeId;
					datas["materialPathName"] = materialPathName;
				}
				fun(datas,content,doubtAudio);
			}else{
				Utils.Notice.alert("请输入内容");
				$("#jDescribe").val("");
				$("#jCount").html(0);
				$(".m-load").hide();
				return;
			}
		});
	}
};

function trim(str){ 
	//删除左右两端的空格 
	if(str){
		return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	return;
}

function MOrHd(){
	return window.location.href.indexOf("/hd/")>0?"hd":"m";
}

/**
 * 图片压缩
 * @param  {[type]} res      [图片]
 * @param  {[type]} fileSize [图片的大小]
 */
function compress(res, callback) {
    var img = new Image(),
        maxW = 640; // 设置最大宽度

    img.onload = function () {
        var cvs = document.createElement( 'canvas'),
            ctx = cvs.getContext( '2d');

        if(img.width > maxW) {
            img.height *= maxW / img.width;
            img.width = maxW;
        }

        cvs.width = img.width;
        cvs.height = img.height;

        ctx.clearRect(0, 0, cvs.width, cvs.height);
        ctx.drawImage(img, 0, 0, img.width, img.height);

        var dataURL = cvs.toDataURL( 'image/png', 0.2);
        callback(dataURL);
    };

    img.src = res;
}

(function() {
    window.Mission = Mission = function(fun) { 
        this.fun = fun;
        this.parent = null;
        this.count = 0;
        this.finish = 0;
        this.result = [];
        this.missions = null;
        this.missionsRun = false;
        this.hasRun = false;
    };
    Mission.prototype={ 
		run : function(fun){
			if(fun){
				this.fun = fun;
			}
			if(!this.missionsRun&&this.missions){
				this.missionsRun = true;
				for (i = 0 ; i < this.count; i++){
					this.missions[i].run();
				}
			}
			if(this.count == this.finish){
				if(this.fun&&!this.hasRun){
					this.hasRun = true;
					this.fun(this.result);
				}
			}
		},
		all : function(missions){
			this.missions = missions;
			this.count = missions.length;
			for (i = 0 ; i < this.count; i++){
				this.missions[i].setParent(this);
			}
			return this;
		},
		setParent : function(parent){
			this.parent = parent;
		},
		back : function(val){
			if(val){
				this.parent.result.push(val);
			}
			this.parent.finish++;
			this.parent.run();
		}
    }
    Mission.version = "1.0"; 
})();