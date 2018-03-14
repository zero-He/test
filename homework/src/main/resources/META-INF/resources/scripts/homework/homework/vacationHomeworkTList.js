define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var Handlebars = require("common/handlebars");
	var _ = require('underscore');
	var ko = require('knockout');
	var date = require('common/base/date');

	
	var MyHomeworkList = {
		iskoBind:false,
		viewModel:{},
		bindModel:function(d){
			
			var _this = this;
			for(var pro in d){
				if(this.viewModel[pro]==undefined){
					this.viewModel[pro]=ko.observable(d[pro]);
				}else{
					this.viewModel[pro](d[pro]);
				}
			}
			
			if(!this.iskoBind){
				ko.applyBindings(this.viewModel);
				ko.computed(function() {
					var curPage = _this.viewModel.curPage();
				});
				this.iskoBind = true;
			}
			
		},
		page : null,
		init : function() {
			this.loadMyHomeworkList();
			this.binEvent();
		},
		users : new Array(),
		loadMyHomeworkList : function() {
			var contextID = "#myHomeworkList";
			var isTecher = $("input[name='isTecher']").val();
			var yearHoliday = $('#jYearHoliday').val();
			if(yearHoliday != undefined && yearHoliday != ''){
				var year = yearHoliday.substring(0, yearHoliday.length - 1);
				var holiday = yearHoliday.substring(yearHoliday.length - 1, yearHoliday.length);
				$("input[name='year']").val(year);
				$("input[name='holiday']").val(holiday);
			}
			var _this = this;
			this.page = Page.create({
				id : 'page',
				url : ctx + '/auth/common/holiday/ajax/loadListByClass.htm',
				pageSize : 10,
				formId : 'myHomeworkForm',
				tipsId:'emptyTip',
				callback : function(datas) {
					var spmList = {
							"100":'100014',
							"102":'102021',
							"101":'101003',
							"103":'103022'
					};
					var ds = datas.page.dataList;
					for(var index=0 ;index<ds.length ;index++){
						d = ds[index];
						
						var isZY = d.type==1;
						d.p = Math.round(d.finish/(d.total==0?1:d.total)*100)+"%";
						
						d.typeName = isZY? "作业": "微课";
						d.firstTime = d.firstTime==null?"--": date.format(d.firstTime,'yyyy-MM-dd HH:mm:ss');
						d.lastTime = d.lastTime==null?"--": date.format(d.lastTime,'yyyy-MM-dd HH:mm:ss');
			
						var roleName = isTecher=="true"? "teacher": "classTeacher";
						var viewName = isZY? "vacationHomeworkDetail": "microHomeworkDetail";
						var spm = '&spm='+ spmList[Leke.user.currentRoleId];
						d.viewUrl = "/auth/"+roleName+"/homework/"+viewName+".htm?id="+d._id + spm;
						
					}
					
					_this.bindModel(datas.page);
				}
			});
		},
		binEvent : function() {
			var _this = this;
			var lis = $('ul.corr li');
			$('ul.corr li').each(function(){
				var li = $(this);
				$(this).click(function(){
					var subjectId = $(this).attr("data-subjectId");
					lis.removeClass("active");
					li.addClass("active");
					
					var clis = $('ul.corrs li');
					clis.hide();
					clis.removeClass("active");
					var cmlis = $('li[data-subjectId="'+ subjectId +'"]',clis.parent());
					cmlis.show();
					var ocmlis = $('li[data-index="0"]',cmlis.parent());
					ocmlis.addClass("active");
					
					var subjectName = $(this).attr("data-name")
					var subjectId = $(this).attr("data-subjectId")

					$("input[name='"+subjectName+"']").val(subjectId);
					_this.reloadPage();
				})
			});
			
			/**
			 * 班级下拉改变事件
			 */
			$('#jClasses').change(function () {
				var classId = $(this).val();
				$("input[name='classId']").val(classId);
				_this.loadMyHomeworkList();
			});
			/**
			 * 寒暑假下拉改变事件
			 * //1:寒假，2：暑假
			 */
			$('#jYearHoliday').change(function () {
				var yearHoliday = $(this).val();
				var year = yearHoliday.substring(0, yearHoliday.length - 1);
				var holiday = yearHoliday.substring(yearHoliday.length - 1, yearHoliday.length);
				$("input[name='year']").val(year);
				$("input[name='holiday']").val(holiday);
				_this.loadMyHomeworkList();
			});

			/**
			 * 催交作业监听
			 */
			$(".operation").on('click', '.u-btn-bg-orange', function () {
				var classId = $('input[name="classId"]').val();
				var subjectId = $('input[name="subjectId"]').val();
				var year = $('input[name="year"]').val();
				var holiday = $('input[name="holiday"]').val();
				if (classId == "" && year != "") {
					Dialog.confirm('请选择班级！');
					return;
				} else if (classId != "" && year == "") {
					Dialog.confirm('请选择寒暑假时间！');
					return;
				} else if (classId == "" && year == "") {
					Dialog.confirm('请选择班级和寒暑假时间！');
					return;
				}
				var data = {
					classId: classId,
					subjectId: subjectId,
					year: year,
					holiday: holiday
				}
				Dialog.confirm("确定要催交作业吗？").done(function (sure) {
					if(sure){
						$.post("/auth/common/holiday/callVacationHomework.htm", data, function (datas) {
							if(datas.success){
								Utils.Notice.alert('作业催交成功！');
							}else{
								Utils.Notice.alert('作业催交失败，请重新催交！');
							}
						});
					}
				});

			});

		},
		reloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		}
	};
	MyHomeworkList.init();
});
