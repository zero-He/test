var Weekpicker = function(opts) {
  var domVal = opts.output;
  var domValJQ = $(domVal);
  var domArray = domValJQ.val().split('-');

  var domYear = domArray[0].replace(/[^0-9]/ig, '');
  var domWeek;
  if(domArray[1]){
    domWeek = domArray[1].replace(/[^0-9]/ig, '') || undefined;
  }



  var defObj = this.getWeekByDay(new Date());
  this.year = parseInt(domYear) || parseInt(opts.year) || parseInt(defObj.year);
  this.week = parseInt(domWeek) || parseInt(opts.week) || defObj.week;
  this.minYear = parseInt(opts.minYear) || 1900;
  this.maxYear = parseInt(opts.maxYear) || 2099;
  this.weekCount = 52;
  this.output = opts.output;
  this.beginYear = parseInt(opts.beginYear) || this.minYear;
  this.beginWeek = parseInt(opts.beginWeek) || 1;
  this.endYear = parseInt(opts.endYear) || this.maxYear;
  this.endWeek = parseInt(opts.endWeek) || 53;
  var W = this;

}
Weekpicker.prototype.init = function() {
  var W = this;
  setTimeout(function () {
    W.initWeekpickerHtml();
    W.posWeekpicker();
    W.handleEvent();
    W.handleIptItemCtrl();
    W.handleBtnPicker();
    W.handleClearBtn();
    W.handleDocClick();
  }, 70);

};
// 年份默认选择年份
Weekpicker.prototype.getYears = function () {
  var year = this.year;
  var years = new Array();
  for (var i = 5; i >= 0; i--) {
    years.push(year - i);
  }
  for (var j = 1; j <= 4; j++) {
    years.push(year + j);
  }
  return years;
}
// 获取该日期在该年的第几周

Weekpicker.prototype.getWeekByDay = function (date) {
  var objYear = date.getFullYear();
  var objWeek = 1;
  var year = date.getFullYear();
  var firstDay = new Date(year, 0, 1); //该年的第一天
  var firstWeekDay = firstDay.getDay();
  var toFirstWeekEnd = firstWeekDay == 0 ? 0 : 7 - firstWeekDay;
  var firstWeekEndDay = new Date(year, 0, toFirstWeekEnd + 1); //第一个星期的星期天
  var dateToFWEDay = (date.getTime() - firstWeekEndDay.getTime()) / ( 1000 * 60 * 60 * 24);
  if (dateToFWEDay <= 0) {
    objWeek = 1;
  }
  else {
    objWeek = Math.ceil((dateToFWEDay + 7) / 7);
  }
  if (objWeek == 53) {
    var objLastDay = new Date(objYear + 1, 0, 0);
    if (objLastDay.getDay != 0) {
      objYear += 1;
      objWeek = 1;
    }
  }
  return {
    year: objYear,
    week: objWeek
  }
}

// 通过第几周获取该周的开始及结束日期
Weekpicker.prototype.getDayByWeek = function (week) {
  var weekBegin = new Object();
  var weekEnd = new Object();
  var year = this.year;
  var firstDay = new Date(year, 0, 1); //该年的第一天
  var firstWeekDay = firstDay.getDay();
  var toFirstWeekEnd = firstWeekDay == 0 ? 0 : 7 - firstWeekDay;
  var firstWeekEndDay = new Date(year, 0, toFirstWeekEnd + 1); //第一个星期的星期天
  var lastDay = new Date(year + 1, 0, 0);//该年的最后一天
  var lastWeekDay = lastDay.getDay();
  var toLastWeekBegin = lastWeekDay == 0 ? 6 : lastWeekDay - 1;
  var lastWeekBeginDay = new Date(year, 11, lastDay.getDate() - toLastWeekBegin);
  if (week == 1) {
    weekBegin = new Date(firstWeekEndDay.getTime() - (6 * 24 * 60 * 60 * 1000));
    weekEnd = firstWeekEndDay;
  }
  else  if (week <= 52) {
    weekEnd = new Date(firstWeekEndDay.getTime() + (week - 1) * (7 * 24 * 60 * 60 * 1000) );
    weekBegin = new Date(weekEnd.getTime() - (6 * 24 * 60 * 60 * 1000));
  }
  else if (week == 53) {
    weekBegin = lastWeekBeginDay;
    weekEnd = lastDay;
  }
  return {
    weekBegin: weekBegin,
    weekEnd: weekEnd
  }
}
// 添加年份选项
Weekpicker.prototype.addYearOpt = function (years) {
  var W = this;
  var arrYear = years;
  var htmlYear = '';
  $(arrYear).each(function (index, val) {
    if(val > W.maxYear || val < W.minYear){
      htmlYear += '<span class="wp-ipt-item disable">'+ val +'</span>'
    } else {
      htmlYear += '<span class="wp-ipt-item">'+ val +'</span>'
    }
  });
  $('#wp-year-items').html(htmlYear);
  $('#wp-item-left').removeClass('disable');
  $('#wp-item-right').removeClass('disable');

  this.vaildYear();
  this.handleIptItem();
}

//  年份max min限定
Weekpicker.prototype.vaildYear = function () {
  var yearDom = $('.wp-ipt-item');
  var firstVal = yearDom[0];
  var lastVal = yearDom[yearDom.length - 1];
  if(parseInt($(firstVal).text()) <= this.minYear) {
    $('#wp-item-left').addClass('disable');
  }
  if(parseInt($(lastVal).text()) >= this.maxYear) {
    $('#wp-item-right').addClass('disable');
  }
}
// 给年份选项添加点击事件
Weekpicker.prototype.handleIptItem = function () {
  var W = this;
  $('.wp-ipt-item').on('click', function() {
    if (!$(this).hasClass('disable')) {
      W.year = parseInt($(this).text());
      $('.wp-ipt').val(W.year);
      W.addWeekHtml();

      $('.wp-ipt').removeClass('wp-white-bg');
      $('.wp-ipt-menu').hide();
    }
  });

}

Weekpicker.prototype.handleEvent = function () {
  var W = this;

  // 增加53周选择
  this.addWeekHtml();
  // 年份输入框监听
  $('.wp-ipt').focus(function () {
    $(this).select();
    $(this).addClass('wp-white-bg');
    W.addYearOpt(W.getYears());
    $('.wp-ipt-menu').show();
  });


  // 简单业务处理
  // 年份选择框关闭
  $('#wp-close-menu').on('click', function () {
    $('.wp-ipt').removeClass('wp-white-bg');
    $('.wp-ipt-menu').hide();
  });
  // 年份增加一年按钮
  $('.wp-right').on('click', function () {
    if(W.year < W.maxYear){
      W.year += 1;
    }
    $('.wp-ipt').val(W.year);
    W.addWeekHtml();
  });
  // 年份减少一年按钮
  $('.wp-left').on('click', function () {
    if(W.year > W.minYear){
      W.year -= 1;
    }
    $('.wp-ipt').val(W.year);
    W.addWeekHtml();
  });
}

// 增加52周供选择
Weekpicker.prototype.addWeekHtml = function () {
  var weekObj = this.getDayByWeek(53);
  var lastWeekDate = weekObj.weekEnd;
  var lastWeekDay = lastWeekDate.getDay();
  var totalWeek = 52;
  if (lastWeekDay == 0) {
    totalWeek = 53;
  }
  var htmlWeek = '';
  for (var i = 1; i <= totalWeek; i++ ) {
    var Obj = this.getDayByWeek(i);
    var begin = Obj.weekBegin;
    var beginText = begin.getFullYear() + '/' + (begin.getMonth() + 1) + '/' + begin.getDate();
    var end = Obj.weekEnd;
    var endText = end.getFullYear() + '/' + (end.getMonth() + 1) + '/' + end.getDate();
    // 有效区间
    var normal = true;
    // this.beginYear <= this.year && this.beginWeek <= i && this.endYear >= this.year && this.endWeek >= i
    //
    if(this.year < this.beginYear || this.year > this.endYear) {
      normal = false;
    }
    if( this.year == this.beginYear && i < this.beginWeek ) {
      normal = false;
    }
    if( this.year == this.endYear && i > this.endWeek ) {
      normal = false;
    }
    if (normal) {
      if(this.week == i) {
        htmlWeek += '<span class="wp-item active" title="'+ beginText + '~' + endText +'">'+ i +'</span>'
      }
      else {
        htmlWeek += '<span class="wp-item" title="'+ beginText + '~' + endText +'">'+ i +'</span>'
      }
    }
    else {
      htmlWeek += '<span class="wp-item disable" title="'+ beginText + '~' + endText +'">'+ i +'</span>'
    }
  }
  $('.wp-pane').html(htmlWeek);
  this.handleWeekpicked();
}
// 处理年份选择 每次增长或减少10年
Weekpicker.prototype.handleIptItemCtrl = function () {
  var W = this;
  $('#wp-item-left').on('click', function () {
    if(!$(this).hasClass('disable')){
      var doms = $('.wp-ipt-item');
      var fistDom = doms[0];
      var fistNum = parseInt($(fistDom).text());
      var years = new Array();
      for (var i = 10; i >= 1; i--){
        years.push(fistNum - i);
      }
      W.addYearOpt(years);
    }
  });
  $('#wp-item-right').on('click', function () {
    if(!$(this).hasClass('disable')){
      var doms = $('.wp-ipt-item');
      var lastDom = doms[doms.length - 1];
      var lastNum = parseInt($(lastDom).text());
      var years = new Array();
      for (var i = 1; i <= 10; i++){
        years.push(lastNum + i);
      }
      W.addYearOpt(years);
    }
  });
}
// 定位weekpicker
Weekpicker.prototype.posWeekpicker = function () {
  var outDom = $(this.output)
  var outDomHei = outDom.height();
  var outDomTop = outDom.offset().top;
  var outDomLeft = outDom.offset().left;
  $('.week-picker').css('position', 'fixed');
  $('.week-picker').css('left', outDomLeft + 'px');
  $('.week-picker').css('top', (outDomTop + outDomHei + 10) + 'px');
}
// 生成weekpicker 基础html
Weekpicker.prototype.initWeekpickerHtml = function () {
  var W = this;
  $('body').append('<div class="week-picker"></div>');
  var weekPickerHtml = '<div class="wp-title">' +
    '<a class="wp-left"></a>' +
    '<a class="wp-right"></a>' +
    '<input type="text" name="" value="'+ W.year +'" class="wp-ipt">' +

    '<div class="wp-ipt-menu">' +
      '<span id="wp-year-items">' +
      '</span>' +

      '<span class="wp-ipt-tag" id="wp-item-left">←</span>' +
      '<span id="wp-close-menu" class="wp-ipt-tag">x</span>' +
      '<span class="wp-ipt-tag" id="wp-item-right">→</span>' +
    '</div>' +
  '</div>' +
  '<div class="wp-pane">' +
  '</div>' +
  '<div class="wp-ctrl">' +
    '<button type="button" name="button" class="wp-btn" id="clearOutput">清空</button>' +
    '<button type="button" name="button" class="wp-btn" id="pickTheWeek">确定</button>' +
  '</div>';
$('.week-picker').html(weekPickerHtml);
}

Weekpicker.prototype.handleWeekpicked = function () {
  var W = this;
  $('.wp-item').on('click', function () {
    if(!$(this).hasClass('disable')){
      var weekVal = parseInt($(this).text());
      W.week = weekVal;
      $('.wp-item').removeClass('active');
      $(this).addClass('active');
    }
  });
}
Weekpicker.prototype.outputVal = function () {
  var W = this;
  var year = this.year;
  var week = this.week < 10 ? '0' + this.week : this.week;
  var dayObj = this.getDayByWeek(week);
  var begin = dayObj.weekBegin;
  var end = dayObj.weekEnd;
  var beginText = begin.getFullYear() + '/' + (begin.getMonth() + 1) + '/' + begin.getDate();
  var endText = end.getFullYear() + '/' + (end.getMonth() + 1) + '/' + end.getDate();
  $(this.output).val(year + '-' + week + '');
  $(this.output).attr('title', beginText + '-' + endText);
}

Weekpicker.prototype.handleBtnPicker = function () {
  var W = this;
  $('#pickTheWeek').on('click', function () {
    var isNeed = true;
    var weeks = $('.wp-item.disable');
    weeks.each(function (index, dom) {
        if (parseInt($(dom).text()) == W.week) {
          isNeed = false;
        }
    });
    if (isNeed)
    {
      W.outputVal();
    }
    $('.week-picker').remove();
  })
}

Weekpicker.prototype.handleClearBtn = function () {
  var W = this;
  $('#clearOutput').on('click', function () {
    $(W.output).val('');
    $(W.output).attr('title', '');
    $('.week-picker').remove();
  });
}

Weekpicker.prototype.handleDocClick = function () {
  var W = this;
  $(document).click(function (e) {
    e = window.event || e;
    obj = $(e.srcElement || e.target);
    if(!$(obj).is('.week-picker, .week-picker *, '+ W.output)) {
      $('.week-picker').remove();
      $(document).unbind('click');
    }
  })
}

Weekpicker.prototype.initEmpty = function () {
  var W = this;
  var year = this.year;
  var week = this.week < 10 ? '0' + this.week : this.week;
  var dayObj = this.getDayByWeek(week);
  var begin = dayObj.weekBegin;
  var end = dayObj.weekEnd;
  var beginText = begin.getFullYear() + '/' + (begin.getMonth() + 1) + '/' + begin.getDate();
  var endText = end.getFullYear() + '/' + (end.getMonth() + 1) + '/' + end.getDate();
  $(this.output).val(year + '-' + week + '');
  $(this.output).attr('title', beginText + '-' + endText);
}