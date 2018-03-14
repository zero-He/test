define(function(require, exports, module){
var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var $ = require('jquery');
var JSON = require('json');
var Utils = require('utils');
var Dialog = require('dialog');
var {cx} = require('common/react/react-utils');
var LekeDate = require('common/base/date');
var WdatePicker = require('common/ui/ui-datepicker/ui-datepicker');
var PaperWin = require('paper/web/leke.control.paper');
var MicroWin = require("beike/microcourse/microcourse-select");
require('beike/coursewareSearch/pc/coursewareDialog');
require('common/ui/ui-scrollbar/jquery.scrollbar.min');
var history = require('repository/service/HistoryService');

function confirmFinishSplitGroup() {
  var tpl = '<div style="font-size:16px;text-align:center;padding-top:20px;">您已经完成分组了吗？</div>'
          + '<div style="font-size:12px;text-align:center;color:#999;padding-top:10px;">是：已完成分组，否：暂不使用分组。</div>';
  var dfd = $.Deferred();
  Dialog.open({
    title: '确认分组',
    tpl: tpl,
    size: 'mini',
    onClose: function(sure) {
      dfd.resolve(sure);
    },
    btns: [{
      className: 'btn-green',
      text: '是',
      cb: function() {
        this.close(true)
      }
    }, {
      className: 'btn-gray',
      text: '否',
      cb: function() {
        this.close(false)
      }
    }]
  })
  return dfd;
}

function openRepoChoiceWin(resType) {
  var dfd = $.Deferred();
  function callback(datas) {
    if (datas && datas.length) {
      datas.forEach(v => v.resType = resType);
      dfd.resolve(datas);
    }
  }
  history.loadHistory().done((datas) => {
    if (resType == 1) {
      $(window).loadCoursewaRepository({
        type: 'choseCW',
        data: datas,
        callBack: callback
      });
    } else if (resType == 2) {
      MicroWin.select({
        data: datas,
        callBack: callback
      });
    } else {
      PaperWin.openPaper({
        data: datas
      }, callback);
    }
  });
  return dfd;
}

var DS = {
  userMap: {},
  clone: function(obj) {
    if ($.isArray(obj)) {
      return obj.map(v => $.extend(true, {}, v));
    } else {
      return $.extend(true, {}, obj);
    }
  },
  getClasses: function() {
    var cls = DS.clone(classes);
    cls.forEach(c => c.checked = 'false');
    return cls;
  },
  fetchUsers: function(classId, forceRefresh) {
    var dfd = $.Deferred();
    if (forceRefresh != true && DS[classId]) {
      dfd.resolve(DS[classId]);
    } else {
      $.post('users.htm', {classId: classId}).done(function(json) {
        var users = json.datas.users;
        var groups = json.datas.groups;
        if (groups.length > 0) {
          var userIds = users.map(u => u.userId);
          groups.forEach(g => g.checked = false);
          groups.unshift({
            groupId: 0,
            groupName: '全班',
            checked: false,
            userIds: userIds
          });
        }
        users.sort((a, b) => {
          if (a.pinyin > b.pinyin) {
            return 1;
          } else if (a.pinyin < b.pinyin) {
            return -1;
          }
          return 0;
        });
        users.forEach(u => u.checked = u.hidden = false);
        DS[classId] = json.datas;
        dfd.resolve(json.datas);
      });
    }
    return dfd;
  },
  fillUsers: function(clazz) {
    var classId = clazz.classId;
    var dfd = $.Deferred();
    if (clazz.users != null) {
      dfd.resolve(clazz);
    } else {
      DS.fetchUsers(classId).done(function(datas) {
        clazz.users = DS.clone(datas.users);
        clazz.groups = DS.clone(datas.groups);
        dfd.resolve(clazz);
      });
    }
	return dfd;
  }
}

class Scrollbar extends React.Component {
  componentDidMount() {
  	$(this.refs.scroll).scrollbar();
  }
  render() {
    var className = 'scrollbar-inner ' + this.props.className;
  	return (
  	  <div ref="scroll" className={className}>
  	  	{this.props.children}
  	  </div>
  	);
  }
}

class CheckBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      checked: props.checked || false
    };
  }
  componentWillReceiveProps(nextProps) {
    if (this.props.checked != nextProps.checked) {
      this.setState({checked: nextProps.checked});
    }
  }
  handleClick(e) {
    if (this.props.onChange) {
      this.props.onChange(e);
    }
  }
  render() {
    var checked = this.state.checked;
    var className = `c-assign-single-checkbox c-assign-single-checkbox-${checked}`;
    return <span className={className} onClick={this.handleClick.bind(this)}></span>;
  }
}

function wrapAfter(options, fn, after) {
	var oldFn = options[fn];
	options[fn] = function() {
		if($.isFunction(oldFn)) {
			oldFn();
		}
		after();
	};
}

class DatePicker extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.state = {
      value: props.value,
      options: this.parseOptions(props.setting)
    };
  }
  parseOptions(setting) {
    var options = $.extend({
      readOnly : true
	}, setting);
	wrapAfter(options, 'onpicked', this.handleChange);
	wrapAfter(options, 'oncleared', this.handleChange);
	return options;
  }
  handleClick(e) {
	WdatePicker.open(e.nativeEvent, this.state.options);
  }
  getValue() {
    return this.refs.picket.value;
  }
  handleChange() {
    var val = this.refs.picket.value;
    this.setState({
      value: val
    });
    if (this.props.onChange) {
      this.props.onChange(val);
    }
  }
  render() {
    return (
      <input ref="picket" id={this.props.id} type="text" className={cx(this.props.className, 'Wdate')}
          defaultValue={this.props.defaultValue} placeholder={this.props.placeholder} onClick={this.handleClick} />
    );
  }
}

class ResChoice extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      datas : props.section.resources
    };
  }
  handleSelect(resType) {
    var that = this;
    openRepoChoiceWin(resType).done((datas) => {
      let resources = that.props.section.resources;
      datas = datas.filter(v => {
        return resources.filter(res => res.type === v.type && res.resId === v.resId).length == 0;
      });
      if (datas.length > 0) {
        let list = resources.concat(datas);
        that.props.section.resources = list;
        that.setState({datas: list});
      }
    });
  }
  handleRemove(res, i) {
    let datas = this.props.section.resources;
    datas = datas.filter(v => !(v.resType == res.resType && v.resId == res.resId))
    this.props.section.resources = datas;
    this.setState({datas: datas});
  }
  render() {
    var that = this;
    let {showRemove, onRemove} = this.props;
    let datas = this.props.section.resources;
    let list1 = datas.filter(v => v.resType == 1);
    let list2 = datas.filter(v => v.resType == 2);
    let list3 = datas.filter(v => v.resType == 3);
  	return (
      <div className="c-assign-single-header c-assign-work-collection">
        <div className={'c-assign-single-sc c-assign-single-left' + (list3.length == 0 ? ' c-assign-single-null' : '')}>
          <label className="c-assign-single-lh">
            <span className="work-quick-check paper">
              <button className="u-btn c-assign-single-choose" onClick={this.handleSelect.bind(this, 3)}></button>
              <span>添加试卷</span>
            </span>
          </label>
          <div className="c-assign-work">
            {list3.map((res) => (
              <span key={res.resId} className="c-assign-work-item">
  	            <b title={res.resName}>{res.resName}</b>
                <i onClick={that.handleRemove.bind(that, res)}>×</i>
              </span>
            ))}
          </div>
        </div>
        <div className={'c-assign-single-sc c-assign-single-left' + (list1.length == 0 ? ' c-assign-single-null' : '')}>
          <label className="c-assign-single-lh">
            <span className="work-quick-check courseware">
              <button className="u-btn c-assign-single-choose" onClick={this.handleSelect.bind(this, 1)}></button>
              <span>添加课件</span>
            </span>
          </label>
          <div className="c-assign-work">
            {list1.map((res) => (
              <span key={res.resId} className="c-assign-work-item">
  	            <b title={res.resName}>{res.resName}</b>
                <i onClick={that.handleRemove.bind(that, res)}>×</i>
              </span>
            ))}
          </div>
        </div>
        <div className={'c-assign-single-sc c-assign-single-left' + (list2.length == 0 ? ' c-assign-single-null' : '')}>
          <label className="c-assign-single-lh">
            <span className="work-quick-check tiny">
              <button className="u-btn c-assign-single-choose" onClick={this.handleSelect.bind(this, 2)}></button>
              <span>添加微课</span>
            </span>
          </label>
          <div className="c-assign-work">
            {list2.map((res) => (
              <span key={res.resId} className="c-assign-work-item">
  	            <b title={res.resName}>{res.resName}</b>
                <i onClick={that.handleRemove.bind(that, res)}>×</i>
              </span>
            ))}
          </div>
        </div>
        {showRemove ? <span className="c-assign-single-delete" onClick={() => onRemove()}></span> : null}
      </div>
  	);
  }
}

class ClassChoice extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      keyword: '',
      classes: DS.getClasses(),
      clazz: null
    };
    props.section.classes = this.state.classes;
  }
  handleChangeKeyword(e) {
    this.setState({keyword: $.trim(e.target.value)});
  }
  handleDeleteKeyword(e) {
    this.setState({keyword: ''});
  }
  // 选择班级
  handleClickClass(clazz, index, e, changeClazz) {
    DS.fillUsers(clazz).done(function(clazz) {
      if (changeClazz) {
        if (clazz.users.length == 0) {
          clazz.checked = 'false';
        } else {
          if (clazz.checked == 'true') {
            if (clazz.groups.length > 0) {
              clazz.groups[0].checked = true;
              clazz.groups.slice(1).forEach(g => g.checked = false);
            }
            clazz.users.forEach(g => g.checked = true);
            clazz.users.forEach(g => g.hidden = false);
          } else if (clazz.checked == 'false') {
            clazz.groups.forEach(g => g.checked = false);
            clazz.users.forEach(g => g.checked = g.hidden = false);
          }
        }
      }
      this.setState({clazz: clazz});
    }.bind(this));
  }
  // 勾选/取消班级
  handleSelectClass(clazz, index, e) {
    e.stopPropagation();
    if (clazz.checked == 'half' || clazz.checked == 'false') {
      clazz.checked = 'true';
    } else {
      clazz.checked = 'false';
    }
    this.handleClickClass(clazz, index, e, true);
  }
  // 勾选/取消分组
  handleSelectGroup(group, index) {
    group.checked = !group.checked;
    let clazz = this.state.clazz;
    if (group.groupId === 0) { // 全班
      if (group.checked) {
        clazz.groups.slice(1).forEach(g => g.checked = false);
        clazz.users.forEach(u => u.checked = true);
      } else {
        clazz.users.forEach(u => u.checked = false);
      }
      clazz.users.forEach(u => u.hidden = false);
    } else { // 非全班
      if (group.checked) { // 选中
        var userIds = {};
        clazz.groups[0].checked = false;
        group.userIds.forEach(userId => userIds[userId] = true);
        var firstGroupChecked = clazz.groups.filter(g => g.groupId != group.groupId && g.checked).length === 0;
        if (firstGroupChecked) {
          clazz.users.forEach(user => {
            var exists = userIds[user.userId];
            user.checked = exists;
            user.hidden = !exists;
          });
        } else {
          clazz.users.forEach(user => {
            if (userIds[user.userId]) {
              user.checked = true;
              user.hidden = false;
            }
          });
        }
      } else if (clazz.groups.some(g => g.checked)) { // 取消，还有选中
        var userIds = {};
        group.userIds.forEach(userId => userIds[userId] = true);
        clazz.groups.slice(1).filter(g => g.checked).forEach(g => {
          g.userIds.forEach(userId => {
            delete userIds[userId];
          })
          // g.userIds.forEach(userId => userIds[userId] = true);
        });
        clazz.users.forEach(user => {
          if (userIds[user.userId]) {
            user.checked = false;
            user.hidden = true;
          }
        });
      } else { // 取消，无选中
        clazz.users.forEach(user => {
          user.checked = user.hidden = false;
        });
      }
    }
    this.syncClassCheckStatus(clazz);
    this.setState({clazz: clazz});
  }
  // 勾选/取消学生
  handleSelectUser(user, index) {
    var clazz = this.state.clazz;
    user.checked = !user.checked;
    this.syncClassCheckStatus(clazz);
    this.setState({clazz: this.state.clazz});
  }
  // 根据学生选中状态，同步班级状态
  syncClassCheckStatus(clazz) {
    if (clazz.users.every(u => u.checked == true)) {
      clazz.checked = 'true';
    } else if (clazz.users.every(u => u.checked != true)) {
      clazz.checked = 'false';
    } else {
      clazz.checked = 'half';
    }
  }
  handleSplitGroup(e) {
    e.stopPropagation();
    confirmFinishSplitGroup().done(function(sure) {
      if (sure) {
        let clazz = this.state.clazz;
        this.props.onReloadClass(clazz.classId)
      }
    }.bind(this));
  }
  componentDidUpdate() {
    let {groups, users} = this.refs;
    users.style.paddingTop = groups.offsetHeight + 'px';
  }
  render() {
    let that = this, users = [], groups = [], classId = 0;
    let {keyword, clazz, classes} = this.state;
    if (clazz) {
      users = clazz.users || users;
      groups = clazz.groups || groups;
      classId = clazz.classId;
    }
    users = users.filter(user => user.hidden !== true);
    let classIcons = ['行政班', '选修班', '分层班', '分层班'];
    return (
    <div className="c-assign-single c-assign-single-main">
      <div className="c-assign-single-body">
        <div className="c-assign-left">
          <div className="c-assign-search">
            <i className="icon-delete" onClick={this.handleDeleteKeyword.bind(this)}></i>
            <lable className="title">班级：</lable>
            <input type="text" ref="keyword" placeholder="请输入班级名称" value={keyword} onChange={this.handleChangeKeyword.bind(that)} />
          </div>
          <Scrollbar className="c-assign-single-classes">
		    <ul className="c-assign-class-container">
              {classes.filter((clazz) => {
                if (keyword == '' || clazz.checked != 'false') {
                  return true;
                }
                let className = '【' + classIcons[clazz.classType - 1] + '】' + clazz.className;
                return className.indexOf(keyword) >= 0;
              }).map((clazz, index) => {
                return (
                  <li className={cx('c-assign-single-class', {'c-assign-single-checked':clazz.classId == classId})} key={clazz.classId} onClick={that.handleClickClass.bind(that, clazz, index)}>
                    <CheckBox checked={clazz.checked} onChange={that.handleSelectClass.bind(that, clazz, index)} />
                    <span className="c-assign-single-name" title={clazz.className}>
                      <span className="class-type">【{classIcons[clazz.classType - 1]}】</span>
                      <span>{clazz.className}</span>
                    </span>
                  </li>
                );
              })}
            </ul>
          </Scrollbar>
        </div>
      	<Scrollbar className="c-assign-single-stu">
      	  <div className="c-detail-container">
		    <div ref="groups" className="groups">
              {groups.map((group, index) => {
                return (
                  <span key={group.groupId} className="item" title={group.groupName} onClick={that.handleSelectGroup.bind(that, group, index)}>
                    <CheckBox checked={group.checked} />
                    <span style={{paddingLeft:4}}>{group.groupName}</span>
                  </span>
                );
              })}
              {clazz && groups.length == 0 && (clazz.classType == 1 || clazz.classType == 4)
              ? <a className="get-groups" onClick={this.handleSplitGroup.bind(that)} target="_blank"
                  href={`${Leke.domain.lessonServerName}/auth/teacher/layering/list/list.htm?data=${Base64.encode(clazz.classId)}`}>班级还没有分组？试试去分组吧！</a>
              : null}
            </div>
		    <div ref="users">
              {users.map((user, index) => {
                return (
                  <span key={user.userId} className="item" title={user.userName} onClick={that.handleSelectUser.bind(that, user, index)}>
                    <CheckBox checked={user.checked} />
                    <span style={{paddingLeft:4}}>{user.userName}</span>
                  </span>
                );
              })}
              {clazz != null && users.length == 0 ? (<div className="m-tips"><i></i><span>该班级没有学生，请核实后再布置</span></div>) : null}
            </div>
            {clazz == null ? (<div className="m-tips"><i></i><span>请在左侧选择布置的班级</span></div>) : null}
		  </div>
      	</Scrollbar>
      </div>
    </div>
    );
  }
}

class Section extends React.Component {
  render() {
    let {section, onReloadClass, showRemove, onRemove} = this.props;
    return (
      <div className="m-assgin-box">
        <div className="c-assign-h3"><h3>选择作业</h3></div>
        <ResChoice section={section} showRemove={showRemove} onRemove={onRemove} />
        <div className="c-assign-h3"><h3>选择学生</h3></div>
        <ClassChoice section={section} onReloadClass={onReloadClass} />
      </div>
    );
  }
}

var randIndex = 1001;

class AssignForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpenAnswer: false,
      openAnswerTime: '',
      startTime: LekeDate.format(Leke.Clock.time(), 'yyyy-MM-dd HH:mm'),
      closeTime: LekeDate.of(Leke.Clock.time()).add(2, 'd').format('yyyy-MM-dd HH:mm'),
      sections: [{key: randIndex++, resources: resources || []}],
      pending: false
    };
    this.handleAddSection = this.handleAddSection.bind(this);
    this.handleReloadClass = this.handleReloadClass.bind(this);
    this.handleSaveAssign = this.handleSaveAssign.bind(this);
    this.handleChangeOpenAnswer = this.handleChangeOpenAnswer.bind(this);
    this.handleChangeStartTime = this.handleChangeStartTime.bind(this);
    this.handleChangeCloseTime = this.handleChangeCloseTime.bind(this);
    this.handleChangeOpenAnswerTime = this.handleChangeOpenAnswerTime.bind(this);
  }
  handleChangeOpenAnswer() {
    let {isOpenAnswer, openAnswerTime, closeTime} = this.state;
    if (isOpenAnswer) {
      this.setState({
        isOpenAnswer: false,
  	    openAnswerTime: ''
  	  });
    } else {
      this.setState({
        isOpenAnswer: true,
  	    openAnswerTime: LekeDate.of(closeTime).add(2, 'd').format('yyyy-MM-dd HH:mm')
  	  });
    }
  }
  handleChangeOpenAnswerTime(val) {
    this.setState({
  	  openAnswerTime: val
  	});
  }
  handleChangeStartTime(val) {
  	this.setState({
  	  startTime: val
  	});
  }
  handleChangeCloseTime(val) {
  	this.setState({
  	  closeTime: val
  	});
  }
  handleAddSection() {
    var sections = this.state.sections;
    sections.push({id: randIndex++, resources: []});
    this.setState({
      sections: sections
    });
  }
  handleDelSection(id) {
    if (this.state.sections.length <= 1) {
      Utils.Notice.alert('最后一个啦');
      return;
    }
    var sections = $.grep(this.state.sections, (section, i) => (section.id != id));
    this.setState({
      sections: sections
    });
  }
  handleReloadClass(classId) {
    DS.fetchUsers(classId, true).done(function(datas) {
      let sections = this.state.sections;
      sections.forEach(section => {
        section.clazz = null;
        section.classes.filter(v => v.classId == classId).forEach(c => {
          c.checked = 'false';
          c.users = DS.clone(datas.users);
          c.groups = DS.clone(datas.groups);
        });
      });
      this.setState({sections: sections});
    }.bind(this))
  }
  handleSaveAssign() {
    let {startTime, closeTime, isOpenAnswer, openAnswerTime} = this.state;
    var sections = this.state.sections.map(s => {
      return {
        resources: s.resources,
        classes: s.classes.filter(c => c.checked !== 'false').map(c => {
          var clazz = {
            classId: c.classId,
            className: c.className,
            classType: c.classType
          };
          clazz.groups = c.groups.filter(g => g.checked && g.groupId !== 0).map(g => {
            return {
              groupId: g.groupId,
              groupName: g.groupName
            }
          });
          clazz.users = c.users.filter(u => !u.hidden && u.checked).map(u => {
            return {
              userId: u.userId,
              userName: u.userName
            };
          });
          return clazz;
        })
      }
    });
    for (let i = 0; i < sections.length; i++) {
      var section = sections[i];
      if (section.resources.length == 0) {
        Utils.Notice.alert(`第${i+1}条作业未选择`);
        return;
      } else if (section.classes.length == 0) {
        Utils.Notice.alert(`第${i+1}条班级未选择`);
        return;
      }
    }
    if (startTime == '') {
      Utils.Notice.alert('请设置作业开始时间');
      return;
    } else if (closeTime == '') {
      Utils.Notice.alert('请设置作业截止时间');
      return;
    }
    if (isOpenAnswer && openAnswerTime == '') {
      Utils.Notice.alert('请设置公布答案时间');
      return;
    }
    var datas = {
      startTime: LekeDate.parse(startTime, 'yyyy-MM-dd HH:mm').getTime(),
      closeTime: LekeDate.parse(closeTime, 'yyyy-MM-dd HH:mm').getTime(),
      sections: sections
    }
    if (openAnswerTime != '') {
      datas.openAnswerTime = LekeDate.parse(openAnswerTime, 'yyyy-MM-dd HH:mm').getTime();
    }
    this.setState({pending: true});
    $.post('saveAssign.htm', {dataJson: JSON.stringify(datas)}).done(function(json) {
      if (json.success) {
        var url = 'success.htm';
        if (json.datas.homeworkId) {
          url += '?homeworkId=' + json.datas.homeworkId;
        }
        window.location.href = url;
      } else {
        this.setState({
          pending: false
        });
      }
    }.bind(this));
  }
  render() {
    var that = this;
    var showRemove = this.state.sections.length > 1;
    var btnCls = cx('u-btn u-btn-lg', this.state.pending ? 'u-btn-bg-gray' : 'u-btn-bg-turquoise');
    return (
      <div>
        {this.state.sections.map((section, i) => {
          return <Section key={section.id + ""} section={section} onReloadClass={this.handleReloadClass}
            showRemove={showRemove} onRemove={that.handleDelSection.bind(that, section.id)} />;
        })}
        <div className="c-assign-h3"><h3>选择时间</h3></div>
        <div className="c-assign-date z-clear c-assign-bottom-date">
          <div className="c-assign-picker">
            <p className="z-tips">作业开始时间：</p>
            <DatePicker id="startTime" className="u-ipt u-ipt-nm c-assign-date-container"
                setting={{dateFmt: 'yyyy-MM-dd HH:mm', maxDate: '#F{$dp.$D(\'closeTime\');}'}}
                placeholder="作业开始时间" defaultValue={this.state.startTime} onChange={this.handleChangeStartTime} />
          </div>
          <span className="c-assign-line"></span>
          <div className="c-assign-picker">
            <p className="z-tips">作业截止时间：</p>
            <DatePicker id="closeTime" className="u-ipt u-ipt-nm c-assign-date-container"
                setting={{dateFmt: 'yyyy-MM-dd HH:mm', minDate: '#F{$dp.$D(\'startTime\');}'}}
                placeholder="作业截止时间" defaultValue={this.state.closeTime} onChange={this.handleChangeCloseTime} />
          </div>
          <div className="c-assign-picker c-assign-publish-picker">
            <CheckBox checked={this.state.isOpenAnswer} onChange={this.handleChangeOpenAnswer} />
            <span className="z-inline-zips"><span onClick={this.handleChangeOpenAnswer}>设置公布答案时间</span></span>
            {this.state.isOpenAnswer ? <DatePicker id="openAnswerTime" className="u-ipt u-ipt-nm c-assign-date-container"
                setting={{dateFmt: 'yyyy-MM-dd HH:mm'}}
                placeholder="公布答案时间" defaultValue={this.state.openAnswerTime} onChange={this.handleChangeOpenAnswerTime} /> : null}
            <p className="z-tips"></p>
          </div>
        </div>
        <div className="c-assign-plus m-tiptext m-tiptext-text m-tiptext-warning">
          <i className="iconfont icon">󰅂 </i>
          <span>小乐提醒您：如果您想给不同层次的学生布置不同的作业，可以点击“<a className="c-assign-plus-btn" onClick={this.handleAddSection}>+增加作业</a>”试试。</span>
        </div>
        <div className="c-quick-btns">
          <ul>
            <li className="btn" onClick={this.handleAddSection}>增加作业</li>
            <li className="btn" onClick={this.handleSaveAssign} disabled={this.state.pending}>确定布置</li>
          </ul>
        </div>
      </div>
    );
  }
}

ReactDOM.render(<AssignForm />, document.getElementById('container'));
});
