define(function (require, exports, module) {
    var $ = require('jquery');
    var WdatePicker = require('date');
    var Page = require('page');
    var Utils = require('utils');
    var HomeworkType = require("homework/common/homeworkType");
    var ResType = require("homework/common/resType");
    require('core-business/widget/jquery.leke.stgGrdSbjSelect');

    var Handlebars = require("common/handlebars");
    var tipsTemp = '<div class="m-tippop m-tippop-lt"><span class="msg">{{message}}</span><div class="arrow"><em></em><i></i></div></div>';
    Handlebars.registerHelper("fmtSubmitStatusTip", function (obj) {
        if (obj.submitStatus == 2) {
            return tipsTemp.replace('{{message}}', '迟交');
        } else if (obj.submitStatus == null || obj.submitStatus == 0) {
            if (Leke.currentSystemTime > obj.closeTime) {
                return tipsTemp.replace('{{message}}', '已超时');
            } else if (obj.closeTime - Leke.currentSystemTime < 24 * 60 * 60 * 1000) {
                var msg = MyHomeworkList.millisecondToDate(obj.closeTime - Leke.currentSystemTime);
                if (msg != '') {
                    return tipsTemp.replace('{{message}}', '仅剩' + msg);
                }
                return '';
            }
        }
    });

    /*Handlebars.registerHelper("fmtTeacherName", function (obj) {
     var teacherNameArr = $.splitName(obj.teacherName);
     return teacherNameArr[0] + "老师";
     });*/

    var MyHomeworkList = {
        page: null,
        init: function () {
            var ord = $('#ord').val();
            if (ord == 2) {
                $('#isSubmit').val('0');
                $('#bugFixStage').val('');
            } else if (ord == 3) {
                $('#bugFixStage').val('1');
            }
            this.loadMyHomeworkList();
            this.binEvent();
        },
        millisecondToDate: function (msd) {
            var time = parseFloat(msd) / 1000;
            if (null != time && "" != time) {
                if (time >= 60 && time < 60 * 60) {
                    time = parseInt(time / 60.0) + "分钟";
                }
                else if (time >= 60 * 60 && time < 60 * 60 * 24) {
                    var hour = parseInt(time / 3600.0);
                    var minute = parseInt((time - hour * 3600 ) / 60);
                    time = hour + '小时';
                    if (minute > 0) {
                        time += minute + '分钟';
                    }
                }
                else {
                    time = "";
                }
            }
            return time;
        },
        loadMyHomeworkList: function () {
            this.page = Page.create({
                id: 'page',
                url: ctx + '/auth/student/homeworkDtl/getMyHomeworkList.htm',
                pageSize: 10,
                buttonId: 'ButMyHomework',
                formId: 'myHomeworkForm',
                tips:'您暂无作业哦~',
                tipsId: 'f_emptyDataContainer',
                callback: function (datas) {
                    Handlebars.render("#myHomeworkContext_tpl", datas.page, "#myHomeworkContext");
                }
            });
        },
        binEvent: function () {
            var _this = this;
            var sbjId = $('#jSubjectId').val();
            $('#jHomeworkType').change(function () {
                _this.page.loadData();
            });
            $('#jResType').change(function () {
                _this.page.loadData();
            });
            $('#jSubject').stgGrdSbjSelect({
                type: 'sbj',
                onChange: function (selection) {
                    $('#jSubjectId').val(selection.subjectId);
                }
            });
            if (sbjId != '') {
                $('#jSubject').val('sbj_' + sbjId);
                $('#jSubjectId').val(sbjId);
            }
            $('#closeTime').click(function () {
                WdatePicker({
                    maxDate: $('#closeEndTime').val(),
                    isShowClear: true,
                    readOnly: true
                });
            });
            $('#closeEndTime').click(function () {
                WdatePicker({
                    minDate: $('#closeTime').val(),
                    isShowClear: true,
                    readOnly: true
                });
            });
            $('#submitTime').click(function () {
                WdatePicker({
                    maxDate: $('#submitEndTime').val(),
                    isShowClear: true,
                    readOnly: true
                });
            });
            $('#submitEndTime').click(function () {
                WdatePicker({
                    minDate: $('#submitTime').val(),
                    isShowClear: true,
                    readOnly: true
                });
            });
            $('#jCorrectFlag li').click(function () {
                var status = $(this).data('status');
                _this.changeStatusActive(status);
                if (status == '1') {
                    //待完成
                    $('#isSubmit').val('0');
                    $('#bugFixStage').val('');
                } else if (status == '2') {
                    //待订正
                    $('#isSubmit').val('');
                    $('#bugFixStage').val('1');
                } else {
                    $('#isSubmit,#isCorrect,#bugFixStage').val('');
                }
                $('#correctFlag').val(status);
                _this.reloadPage();
            });

            $('.intell-tab-li').click(function () {
                $('.intell-tab-li').removeClass("intell-tab-li-on");
                $(this).addClass("intell-tab-li-on");
                $("#sortId").val($(this).attr("data-value"));
                _this.page.loadData();
            });

            HomeworkType.render($('#jHomeworkType'));
            ResType.render($('#jResType'));

            //分析click事件
            $(document).on('click', '.J_analysis', function () {
                var homeworkId = $(this).data('homeworkid');
                var _this = this;
                $.ajax({
                    url: Leke.domain.homeworkServerName + '/auth/teacher/homework/validateAnalysis.htm?homeworkId=' + homeworkId,
                    async: false,
                    success: function (data) {
                        if (!data.success) {
                            Utils.Notice.alert(data.message);
                            isAllow = false;
                        } else {
                            window.open($(_this).data('href'));
                        }
                    }
                });
            });
            $(document).on('click', '.jHomeworkTitle', function () {
                var id = $(this).data('id');
                /*var restype = $(this).data('restype');*/

                var isdo = $(this).data('status') != 2 && $(this).data('submittime') == '';
                var href = Leke.domain.homeworkServerName + '/auth/student/homework/viewWork.htm?homeworkDtlId=' + id;
                if (isdo) {
                    href = Leke.domain.homeworkServerName + '/auth/student/homework/doWork.htm?homeworkDtlId=' + id;
                }
                window.open(href);
            });
        },
        reloadPage: function () {
            this.page.options.curPage = 1;
            this.page.loadData();
        },
        changeStatusActive: function (status) {
            $('#jCorrectFlag li').removeClass('cur');
            $('#jCorrectFlag li[data-status=' + status + ']').addClass('cur');
        }
    };
    MyHomeworkList.init();

    /**
     * 区分姓和名
     * @Author LIU.SHITING
     * @Version 1.0.0
     * @Date 2017/4/5 17:25
     */
    $.extend({
        splitName: function (fullname) {
            var hyphenated = ['欧阳', '太史', '端木', '上官', '司马', '东方', '独孤', '南宫', '万俟', '闻人', '夏侯', '诸葛', '尉迟', '公羊', '赫连', '澹台', '皇甫',
                '宗政', '濮阳', '公冶', '太叔', '申屠', '公孙', '慕容', '仲孙', '钟离', '长孙', '宇文', '城池', '司徒', '鲜于', '司空', '汝嫣', '闾丘', '子车', '亓官',
                '司寇', '巫马', '公西', '颛孙', '壤驷', '公良', '漆雕', '乐正', '宰父', '谷梁', '拓跋', '夹谷', '轩辕', '令狐', '段干', '百里', '呼延', '东郭', '南门',
                '羊舌', '微生', '公户', '公玉', '公仪', '梁丘', '公仲', '公上', '公门', '公山', '公坚', '左丘', '公伯', '西门', '公祖', '第五', '公乘', '贯丘', '公皙',
                '南荣', '东里', '东宫', '仲长', '子书', '子桑', '即墨', '达奚', '褚师'];
            var vLength = fullname.length;
            var lastname = '', firstname = '';//前为姓,后为名
            if (vLength > 2) {
                var preTwoWords = fullname.substr(0, 2);//取命名的前两个字,看是否在复姓库中
                if ($.inArray(preTwoWords, hyphenated) > -1) {
                    lastname = preTwoWords;
                    firstname = fullname.substr(2);
                } else {
                    lastname = fullname.substr(0, 1);
                    firstname = fullname.substr(1);
                }
            } else if (vLength == 2) {//全名只有两个字时,前一个为姓,后一下为名
                lastname = fullname.substr(0, 1);
                firstname = fullname.substr(1);
            } else {
                lastname = fullname;
            }
            return [lastname, firstname];
        }
    });
});
