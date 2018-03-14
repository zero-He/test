define(function (require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var DateUtil = require('common/base/date');
	var Utils = require('utils');

	var StudentBehaviorComm = {
		fmt: "yyyy-MM-dd",

		saveGlobalParam: function (gradeId, subjectId, classId, startDate, endDate, dateText) {
			if (gradeId != null) {
				store.set("behaviorGradeId", gradeId);
			}
			if (subjectId != null) {
				store.set("behaviorSubjectId", subjectId);
			}
			if (classId != null) {
				store.set("behaviorClassId", classId);
			}
			if (startDate != null) {
				store.set("behaviorStartDate", startDate);
			}
			if (endDate != null) {
				store.set("behaviorEndDate", endDate);
			}
			if (dateText != null) {
				store.set("behaviorDateText", dateText);
			}
		},

		clearBrowerCache: function () {
			var behaviorEndDate = store.get("behaviorEndDate");
			if (behaviorEndDate !== undefined) {
				//计算出相差天数
				var days = Math.floor(((new Date()).getTime() - new Date(behaviorEndDate).getTime()) / (24 * 3600 * 1000));
				if (days > 1) {
					store.clearAll();
				}
			}
		},

		getParam: function () {
			var param = {
				gradeId: $("#gradeId").val(),
				subjectId: $("#subjectId").val(),
				startDate: $("#startDate").val(),
				endDate: $("#endDate").val(),
				trendType: $("#trendType").val(),
				compType: $("#compType").val(),
				orderAttr: $("#orderAttr").val(),
				orderType: $("#orderType").val(),
				teacherId: $("#teacherId").val(),
				teacherName: $("#teacherName").val(),
				studentId: $("#studentId").val(),
				classId: $("#classId").val(),
				isPrepared: $("#isPrepared").val(),
				lessonStatus: $("#lessonStatus").val(),
				trendSelect: $('#trendSelect').val(),
				compareSelect: $('#compareSelect').val()
			};
			return param;
		},

		getMonthDays: function (nowYear, myMonth) {
			var monthStartDate = new Date(nowYear, myMonth, 1);
			var monthEndDate = new Date(nowYear, myMonth + 1, 1);
			var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
			return days;
		},

		getCurrentDate: function () {
			var now = new Date();
			var nowDay = now.getDate(); //当前日
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getFullYear(); //当前年
			return DateUtil.format(new Date(nowYear, nowMonth, nowDay), this.fmt);
		},

		getDateByDelta: function (srcDate, delta) {
			var now = new Date(srcDate);
			now.setDate(now.getDate() + delta);
			var nowDay = now.getDate();
			var nowMonth = now.getMonth();
			var nowYear = now.getFullYear();
			return DateUtil.format(new Date(nowYear, nowMonth, nowDay), this.fmt);
		},

		getStartEndDate: function () {
			var now = new Date();
			var nowDayOfWeek = now.getDay() || 7;  //今天是本周的第几天
			var nowDay = now.getDate(); //当前日
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getFullYear(); //当前年
			var nextYear = now.getFullYear() + 1; //下一年

			//获得本周的开始、结束日期
			var weekStartDate = DateUtil.format(new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1), this.fmt);
			var weekEndDate = StudentBehaviorComm.getDateByDelta(now, -1);

			//获得本月的开始、结束日期
			var monthStartDate = DateUtil.format(new Date(nowYear, nowMonth, 1), this.fmt);
			var monthEndDate = StudentBehaviorComm.getDateByDelta(now, -1);

			//获取本学期的开始、结束日期
			var termUpstartDate = "-02-01";
			var termDownstartDate = "-08-01";

			var termStartDate;
			var termEndDate;
			if (nowMonth >= 2 && nowMonth <= 6) {
				termStartDate = nowYear + termUpstartDate;
				termEndDate = StudentBehaviorComm.getDateByDelta(now, -1);
			} else {
				termStartDate = nowYear + termDownstartDate;
				termEndDate = StudentBehaviorComm.getDateByDelta(now, -1);
			}

			starEndDate = {
				weekStartDate: weekStartDate,
				weekEndDate: weekEndDate,
				monthStartDate: monthStartDate,
				monthEndDate: monthEndDate,
				termStartDate: termStartDate,
				termEndDate: termEndDate,
			};

			return starEndDate;
		},

		initDateSelect: function () {
			var startEndDate = this.getStartEndDate();

			if (this.getCurrentDate() == startEndDate.termStartDate) {
				$("#term").hide();
				$("#startDate").val("9999-12-01");
				$("#endDate").val("9999-12-31");
				$("#dateText").html("本学期（学期第一天无历史数据）");
			} else {
				$("#term").show();
				$("#term").html("本学期（" + startEndDate.termStartDate + "--" + startEndDate.termEndDate + "）");
			}

			if (this.getCurrentDate() == startEndDate.monthStartDate) {
				$("#month").hide();
			} else {
				$("#month").show();
				$("#month").html("本月（" + startEndDate.monthStartDate + "--" + startEndDate.monthEndDate + "）");
				$("#startDate").val(startEndDate.monthStartDate);
				$("#endDate").val(startEndDate.monthEndDate);
				$("#dateText").html($("#month").html());
			}

			if (this.getCurrentDate() == startEndDate.weekStartDate) {
				$("#week").hide();
			} else {
				$("#week").show();
				$("#week").html("本周（" + startEndDate.weekStartDate + "--" + startEndDate.weekEndDate + "）");
			}

			var reg = /toShowStatSum.htm/;
			if (store.get("behaviorStartDate") && store.get("behaviorEndDate") && store.get("behaviorDateText") && !reg.test(window.location.href)) {
				$("#startDate").val(store.get("behaviorStartDate"));
				$("#endDate").val(store.get("behaviorEndDate"));
				$("#dateText").html(store.get("behaviorDateText"));
			}
		},

		loadQueryParamPro: function (callBack) {
			$.post("/auth/provost/teachingMonitor/comm/loadQueryParamPro.htm", {gradeId: store.get("behaviorGradeId"), classType: true}, function (json) {
				var data = json.datas;
				if (data) {
					Handlebars.render("#gradeTpl", data, "#gradeId");
					$("#graId").val(store.get("behaviorGradeId") === null ? data.gradeList[0].gradeId : store.get("behaviorGradeId"));
					if (store.get("behaviorGradeId")) {
						$("#gradeId").val(store.get("behaviorGradeId"));
					} else {
						$("#gradeId").val("");
					}

					Handlebars.render("#subjectTpl", data, "#subjectId");
					Handlebars.render("#classTpl", data, "#classId");

					if (typeof callBack === "function") {
						if (store.get("behaviorSubjectId")) {
							$("#subjectId").val(store.get("behaviorSubjectId"));
						} else {
							$("#subjectId").val("");
						}
						if (store.get("behaviorClassId")) {
							$("#classId").val(store.get("behaviorClassId"));
						} else {
							$("#classId").val("");
						}
						callBack(StudentBehaviorComm.getParam());
					} else {
						store.set("behaviorSubjectId", "");
						store.set("behaviorClassId", "");
					}
				}
			});
		},

		quickQuerySelect: function (callBack1, callBack2) {
			$("#ulDate").on("click", "li", function () {
				Utils.Notice.mask.create();
				var startEndDate = StudentBehaviorComm.getStartEndDate();
				if ("month" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.monthStartDate);
					$("#endDate").val(startEndDate.monthEndDate);
					$("#dateText").html($(this).html());
				} else if ("week" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.weekStartDate);
					$("#endDate").val(startEndDate.weekEndDate);
					$("#dateText").html($(this).html());
				} else if ("term" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.termStartDate);
					$("#endDate").val(startEndDate.termEndDate);
					$("#dateText").html($(this).html());
				}
				StudentBehaviorComm.loadStatOrDtlData(callBack1, callBack2);
			});

			//年级改变事件
			$("#gradeId").change(function (e) {
				Utils.Notice.mask.create();
				$('#graId').val($('#gradeId').val());
				$("#compGrade").show();
				$("#compType").val($('#compType').val());
				store.set("behaviorGradeId", $('#gradeId').val());
				StudentBehaviorComm.loadQueryParamPro(e);
				$("#classId").val("");
				$("#subjectId").val("");
				$("#claId").val("");
				$("#subId").val("");
				StudentBehaviorComm.loadStatOrDtlData(callBack1, callBack2);
			});

			//班级改变事件
			$("#classId").change(function () {
				Utils.Notice.mask.create();
				$('#claId').val($('#classId').val());
				StudentBehaviorComm.loadStatOrDtlData(callBack1, callBack2);
			});

			//学科改变事件
			$("#subjectId").change(function () {
				Utils.Notice.mask.create();
				$('#subId').val($('#subjectId').val());
				StudentBehaviorComm.loadStatOrDtlData(callBack1, callBack2);
			});

			//自定义时间确定触发
			$("#submit").on("click", function () {
				Utils.Notice.mask.create();
				var startTime = $('#customStart').val();
				var endTime = $("#customEnd").val();

				if (!startTime || !endTime) {
					Utils.Notice.alert("开始日期、结束日期都不能为空!");
					return false;
				} else if (new Date(startTime) > new Date(endTime)) {
					Utils.Notice.alert("开始日期不能大于结束日期!");
					return false;
				}

				if (!startTime || !endTime) {
					Utils.Notice.alert("开始日期、结束日期都不能为空!");
					return false;
				} else if (new Date(startTime) > new Date(endTime)) {
					Utils.Notice.alert("开始日期不能大于结束日期!");
					return false;
				}

				$("#startDate").val(startTime);
				$("#endDate").val(endTime);
				$("#dateText").html(startTime + "--" + endTime);
				StudentBehaviorComm.loadStatOrDtlData(callBack1, callBack2);
			});
		},

		//概览或明细数据获取
		loadStatOrDtlData: function (callBack1, callBack2) {
			if ($("#stat").hasClass("c-analyse__sitem--cur")) {
				callBack1(StudentBehaviorComm.getParam());
			}
			if ($("#detail").hasClass("c-analyse__sitem--cur")) {
				callBack2(StudentBehaviorComm.getParam());
			}
		},

		statClick: function (callBack) {
			//概览
			$("#stat").on("click", function () {
				Utils.Notice.mask.create();
				$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
				$("#statData").show();
				$("#dtlData").hide();
				if (callBack) {
					callBack(StudentBehaviorComm.getParam());
				}
			});
		},

		exportClick: function (param) {
			//导出
			$("#export").on("click", function () {
				var form = $("form");
				form.attr("action", param);
				form.submit();
			});
		},

		detailClick: function (callBack) {
			//明细
			$("#detail").on("click", function () {
				Utils.Notice.mask.create();
				$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
				$("#dtlData").show();
				$("#statData").hide();
				if (callBack) {
					callBack(StudentBehaviorComm.getParam());
				}
			});

			//排序点击事件
			$(".m-sorting").on("click", function () {
				if ($(this).hasClass("m-sorting-desc")) {
					$(this).removeClass("m-sorting-desc").addClass("m-sorting-asc");
					$("#orderType").val("asc");
				} else {
					$(this).removeClass("m-sorting-asc").addClass("m-sorting-desc");
					$("#orderType").val("desc");
				}
				$("#orderAttr").val($(this).attr("id"));
				$(this).siblings().removeClass("m-sorting-asc").removeClass("m-sorting-desc");

				if (callBack) {
					callBack(StudentBehaviorComm.getParam());
				}
			});
		},

		trendClick: function (callBack) {
			//点击日周月切换走势
			$("#trend").on("click", "a", function () {
				if ("byDay" == $(this).attr("id")) {
					$("#trendType").val("day");
				} else if ("byWeek" == $(this).attr("id")) {
					$("#trendType").val("week");
				} else if ("byMonth" == $(this).attr("id")) {
					$("#trendType").val("month");
				}
				$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');

				if (callBack) {
					callBack(StudentBehaviorComm.getParam());
				}
			});

		}

	};

	module.exports = StudentBehaviorComm;

});