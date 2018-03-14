define(function (require, exports, module) {
	var React = require('common/react/react');
	var ReactDOM = require('common/react/react-dom');
	var $ = require('jquery');
	var JSON = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var {cx} = require('common/react/react-utils');
	var LekeDate = require('common/base/date');
	var WdatePicker = require('common/ui/ui-datepicker/ui-datepicker');
	require('common/ui/ui-scrollbar/jquery.scrollbar.min');
	var PaperWin = require('paper/web/leke.control.paper');

	
	function openRepoChoiceWin(resType) {
	  var dfd = $.Deferred();
	  function callback(datas) {
	    if (datas && datas.length) {
	      datas.forEach(v => v.resType = resType);
	      dfd.resolve(datas);
	    }
	  }
	  if (resType == 1) {
	    $(window).loadCoursewaRepository({
	      type: 'choseCW',
	      data: {},
	      callBack: callback
	    });
	  } else if (resType == 2) {
	    MicroWin.select(callback);
	  } else {
	    PaperWin.openPaper({}, callback);
	  }
	  return dfd;
	}

	var DS = {
		userMap: {},
		clone: function (obj) {
			if ($.isArray(obj)) {
				return obj.map(v => $.extend(true, {}, v));
			} else {
				return $.extend(true, {}, obj);
			}
		},
		getClasses: function () {
			var cls = DS.clone(classes);
			cls.forEach(c => c.checked = 'false');
			return cls;
		},
		fillUsers: function (clazz) {
			var classId = clazz.classId;
			var dfd = $.Deferred();
			if (clazz.users != null) {
				dfd.resolve(clazz);
			} else if (DS[classId]) {
				clazz.users = DS[classId].users;
				clazz.groups = DS[classId].groups;
				dfd.resolve(clazz);
			} else {
				$.post('/auth/teacher/assign/users.htm', {classId: classId}).done(function (json) {
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
					clazz.users = DS.clone(users);
					clazz.groups = DS.clone(groups);
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


	class RadioBox extends React.Component {
		render() {
			<div>
				<label><input type="radio" name="exam-time"/>60分钟</label>
				<label><input type="radio" name="exam-time" checked/>90分钟</label>
				<label><input type="radio" name="exam-time"/>120分钟</label>
			</div>
		}
	}

	function wrapAfter(options, fn, after) {
		var oldFn = options[fn];
		options[fn] = function () {
			if ($.isFunction(oldFn)) {
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
				readOnly: true
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
				       defaultValue={this.props.defaultValue} placeholder={this.props.placeholder} onClick={this.handleClick}/>
			);
		}
	}

	class ResChoice extends React.Component {
		constructor(props) {
			super(props);
			this.state = {
				datas: props.section.resources
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

		handleRemove(res, i, props) {
		    let datas = this.props.section.resources;
		    datas = datas.filter(v => !(v.resType == res.resType && v.resId == res.resId))
		    this.props.section.resources = datas;
		    this.setState({datas: datas});
		}

		render() {
			var that = this;
			let {showRemove, onRemove} = this.props;
			let datas = this.props.section.resources;
			return (
				<div className="c-assign-single-header c-assign-work-collection">
					<div className="c-assign-single-hwc c-assign-single-left">
						<label className="c-assign-single-lh">
							<span className="work-quick-check paper">试卷：</span>
							<button className="u-btn u-btn-sm u-btn-bg-orange c-assign-single-choose" onClick={this.handleSelect.bind(this, 3)}>请选择</button>
						</label>
						<div className="c-assign-work">
							{datas.filter(v => v.resType == 3).map((res) => (
								<span key={res.resId} className="c-assign-work-item">
					                <b title={res.resName}>{res.resName}</b>
					                <i onClick={that.handleRemove.bind(that, res, this.props)}>×</i>
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
				classes: DS.getClasses(),
				clazz: null
			};
			props.section.classes = this.state.classes;
		}

		// 选择班级
		handleClickClass(clazz, index, e, changeClazz) {
			DS.fillUsers(clazz).done(function (clazz) {
				if (changeClazz) {
					if (clazz.users.length == 0) {
						clazz.checked = 'false';
						//  Utils.Notice.alert('该班级没有学生，请核实后再布置');
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

		componentDidUpdate() {
			let {groups, users} = this.refs;
			users.style.paddingTop = groups.offsetHeight + 'px';
		}

		render() {
			let that = this, users = [], groups = [], classId = 0;
			if (this.state.clazz) {
				users = this.state.clazz.users || users;
				groups = this.state.clazz.groups || groups;
				classId = this.state.clazz.classId;
			}
			users = users.filter(user => user.hidden !== true);
			let classIcons = ['c-assign-xing-icon', 'c-assign-xuan-icon', 'c-assign-fen-icon', 'c-assign-fen-icon'];
			return (
				<div className="c-assign-single c-assign-single-main">
					<div className="c-assign-single-body">
						<Scrollbar className="c-assign-single-classes">
							<ul className="c-assign-class-container">
								{this.state.classes.map((clazz, index) => {
									return (
										<li className={cx('c-assign-single-class', {'c-assign-single-checked': clazz.classId == classId})} key={clazz.classId}
										    onClick={that.handleClickClass.bind(that, clazz, index)}>
											<CheckBox checked={clazz.checked} onChange={that.handleSelectClass.bind(that, clazz, index)}/>
											<span className={classIcons[clazz.classType - 1]}></span>
											<span className="c-assign-single-name" title={clazz.className}>{clazz.className}</span>
										</li>
									);
								})}
							</ul>
						</Scrollbar>
						<Scrollbar className="c-assign-single-stu">
							<div className="c-detail-container">
								<div ref="groups" className="groups">
									{groups.map((group, index) => {
										return (
											<span key={group.groupId} className="item" title={group.groupName} onClick={that.handleSelectGroup.bind(that, group, index)}>
                    <CheckBox checked={group.checked}/>
                    <span style={{paddingLeft: 4}}>{group.groupName}</span>
                  </span>
										);
									})}
								</div>
								<div ref="users">
									{users.map((user, index) => {
										return (
											<span key={user.userId} className="item" title={user.userName} onClick={that.handleSelectUser.bind(that, user, index)}>
                    <CheckBox checked={user.checked}/>
                    <span style={{paddingLeft: 4}}>{user.userName}</span>
                  </span>
										);
									})}
									{that.state.clazz != null && users.length == 0 ? (<div className="m-tips"><i></i><span>该班级没有学生，请核实后再布置</span></div>) : null}
								</div>
								{that.state.clazz == null ? (<div className="m-tips"><i></i><span>请在左侧选择要考试的班级</span></div>) : null}
							</div>
						</Scrollbar>
					</div>
				</div>
			);
		}
	}

	class Section extends React.Component {
		render() {
			let {section, showRemove, onRemove} = this.props;
			return (
				<div>
					<div className="c-assign-h3"><h3>选择试卷</h3></div>
					<ResChoice section={section} showRemove={showRemove} onRemove={onRemove}/>
					<div className="c-assign-h3"><h3>选择学生</h3></div>
					<ClassChoice section={section}/>
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
				/*closeTime: LekeDate.of(Leke.Clock.time()).add(2, 'd').format('yyyy-MM-dd HH:mm'),*/
				closeTime: '',
				diffTime: '90',
				sections: [{key: randIndex++, resources: resources || []}],
				pending: false
			};
			this.handleSaveAssign = this.handleSaveAssign.bind(this);
			this.handleChangeOpenAnswer = this.handleChangeOpenAnswer.bind(this);
			this.handleChangeStartTime = this.handleChangeStartTime.bind(this);
			this.handleChangeExamTime = this.handleChangeExamTime.bind(this);
			this.handleChangeOpenAnswerTime = this.handleChangeOpenAnswerTime.bind(this);
		}

		handleChangeOpenAnswer() {
			let {isOpenAnswer, openAnswerTime, startTime} = this.state;
			if (isOpenAnswer) {
				this.setState({
					isOpenAnswer: false,
					openAnswerTime: ''
				});
			} else {
				this.setState({
					isOpenAnswer: true,
					openAnswerTime: LekeDate.of(startTime).add(2, 'd').format('yyyy-MM-dd HH:mm')
				});
			}
		}

		/*考试时长改变触发*/
		handleChangeExamTime(val){
			let {startTime, closeTime, diffTime} = this.state;
			this.setState({
				diffTime: val.currentTarget.defaultValue,
				closeTime: LekeDate.of(startTime).add(val.currentTarget.defaultValue, 'm').format('yyyy-MM-dd HH:mm')
			});

		}

		handleChangeOpenAnswerTime(val) {
			let {openAnswerTime} = this.state;
			this.setState({
				openAnswerTime: val
			});
		}

		handleChangeStartTime(val) {
			let {startTime, closeTime, diffTime} = this.state;
			this.setState({
				startTime: val,
				closeTime: LekeDate.of(val).add(diffTime, 'm').format('yyyy-MM-dd HH:mm')
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
					Utils.Notice.alert(`请选择试卷`);
					return;
				} else if (section.classes.length == 0) {
					Utils.Notice.alert(`请选择班级`);
					return;
				}
			}
			if (startTime == '') {
				Utils.Notice.alert('请设置试卷开始时间');
				return;
			}

			if (closeTime == '') {
				closeTime = LekeDate.of(startTime).add(90, 'm').format('yyyy-MM-dd HH:mm');
			}

			if (isOpenAnswer && openAnswerTime == '') {
				Utils.Notice.alert('请设置公布答案时间');
				return;
			}

			var time = new Date().getTime();
			var st = LekeDate.parse(startTime, 'yyyy-MM-dd HH:mm').getTime();
			if (time >= st) {
				Utils.Notice.alert('请重新选择考试开始时间!');
				return;
			}

			var datas = {
				startTime: LekeDate.parse(startTime, 'yyyy-MM-dd HH:mm').getTime(),
				closeTime: LekeDate.parse(closeTime, 'yyyy-MM-dd HH:mm').getTime(),
				openAnswerTime: openAnswerTime == '' ? '' : LekeDate.parse(openAnswerTime, 'yyyy-MM-dd HH:mm').getTime(),
				sections: sections
			}
			this.setState({pending: true});
			$.post('/auth/teacher/exam/assign/saveAssign.htm', {dataJson: JSON.stringify(datas)}).done(function (json) {
				if (json.success) {
					var url = '/auth/teacher/exam/assign/success.htm';
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
					<div className="m-assgin-tab">
						<ul>
							<li className="assgin-tab-header">发布考试</li>
						</ul>
					</div>
					{this.state.sections.map((section, i) => {
						return <Section key={section.id + ""} section={section} showRemove={showRemove} onRemove={that.handleDelSection.bind(that, section.id)}/>;
					})}
					<div className="c-assign-h3"><h3>选择考试时间</h3></div>
					<div className="c-assign-date z-clear c-assign-bottom-date">
						<div className="c-assign-picker">
							<DatePicker id="startTime" className="u-ipt u-ipt-nm c-assign-date-container"
							            setting={{dateFmt: 'yyyy-MM-dd HH:mm'}}
							            placeholder="试卷开始时间" defaultValue={this.state.startTime} onChange={this.handleChangeStartTime}/>
							<p className="z-tips">考试开始时间</p>
						</div>
						<div className="exam-duration">
							<span>考试时长：</span>
							<label><input type="radio" name="exam-time" onChange={this.handleChangeExamTime} defaultValue={60}/>60分钟</label>
							<label><input type="radio" name="exam-time" onChange={this.handleChangeExamTime} defaultValue={90} defaultChecked={true}/>90分钟</label>
							<label><input type="radio" name="exam-time" onChange={this.handleChangeExamTime} defaultValue={120}/>120分钟</label>
						</div>
						<div className="c-assign-picker c-assign-publish-picker">
							<CheckBox checked={this.state.isOpenAnswer} onChange={this.handleChangeOpenAnswer}/>
							<span className="z-inline-zips"><span onClick={this.handleChangeOpenAnswer}>设置公布答案时间</span></span>
							{this.state.isOpenAnswer ? <DatePicker id="openAnswerTime" className="u-ipt u-ipt-nm c-assign-date-container"
							                                       setting={{dateFmt: 'yyyy-MM-dd HH:mm'}}
							                                       placeholder="公布答案时间" defaultValue={this.state.openAnswerTime} onChange={this.handleChangeOpenAnswerTime}/> : null}
							<p className="z-tips"></p>
						</div>
					</div>
					<div className="c-assign-plus m-tiptext m-tiptext-text m-tiptext-warning">
						<i className="iconfont icon">󰅂 </i>
						<span>学生仅能在平板端进行线上考试。</span>
					</div>
					<div className="z-sub-btn">
						<button className={btnCls} onClick={this.handleSaveAssign} disabled={this.state.pending}>{this.state.pending ? '处理中...' : '发布'}</button>
					</div>
				</div>
			);
		}
	}

	ReactDOM.render(<AssignForm />, document.getElementById('container'));
});
