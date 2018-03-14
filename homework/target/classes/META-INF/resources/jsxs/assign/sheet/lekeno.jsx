define(function(require, exports, module){
var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var {cx} = require('common/react/react-utils');
var ImgView = require('./imgview');
var $ = require('jquery');
var Utils = require('utils');
var Dialog = require('dialog');
require('common/ui/ui-autocomplete/ui-autocomplete');

let {Component, PropTypes} = React;

class HomeworkMatchCombo extends Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
  }
  componentWillReceiveProps(nextProps) {
    if (this.props.pageId != nextProps.pageId) {
      this.refs.select.value = '0';
    }
  }
  handleChange(e) {
    if (this.props.onChange) {
      var homeworkId = parseInt(e.target.value);
      this.props.onChange(homeworkId);
    }
  }
  render() {
    let homeworks = this.props.homeworks;
    return (
      <select ref="select" onChange={this.handleChange}>
        <option value="0">请选择作业</option>
        {homeworks.map(hw => {
          let title = `${hw.className} ${hw.teacherName} ${hw.closeTime}`;
          return <option key={hw.homeworkId} value={hw.homeworkId} title={title}>{title}</option>;
        })}
      </select>
    );
  }
}

class UserNameMatchCombo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      homeworkId: props.homeworkId
    }
  }
  componentWillReceiveProps(nextProps) {
    if (this.props.homeworkId != nextProps.homeworkId) {
      this.setState({
        homeworkId: nextProps.homeworkId
      });
      if (this.refs.ipt) {
        $(this.refs.ipt).autocomplete('destroy');
      }
    }
  }
  componentDidUpdate() {
    let that = this;
    let homeworkId = this.props.homeworkId;
    $(this.refs.ipt).autocomplete({
      url: `users.htm?homeworkId=${homeworkId}`,
      nameKey: 'userName',
      minLen : 1,
      textFn: function(item){
        if (item) {
          let submitFlag = item.isSubmit ? '(已提交)' : '';
          return `${item.lekeNo} ${item.userName} ${item.className}${submitFlag}`;
        }
        return '';
      },
      onChange: function(item) {
        if (that.props.onChange) {
          that.props.onChange(item);
        }
      }
    });
  }
  render() {
    let disabled = !(this.state.homeworkId > 0);
    return <input ref="ipt" type="text" style={{width:282}} disabled={disabled} placeholder="请输入姓名或者乐号" />;
  }
}

class UserView extends Component {
  render() {
    return (
      <div className="student">
        <i className="icon-student"></i>
        <span>学生：{this.props.children}</span>
      </div>
    );
  }
}

class ErrorView extends Component {
  render() {
    return (
      <div className="reason">
        <i className="icon-reason"></i>
        <span>异常原因：{this.props.children}</span>
      </div>
    );
  }
}

class LekeNoHandle extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pageId: props.pageId,
      imgs: [],
      homeworkId: 0,
      homeworkDtlId: 0,
      userId: 0,
      userName: '',
      homeworks: []
    };
    this.fillCurrPage(props.pageId);
    this.handleJump = this.handleJump.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.handleHomeworkChange = this.handleHomeworkChange.bind(this);
    this.handleUserNameChange = this.handleUserNameChange.bind(this);
  }
  fillCurrPage(pageId) {
    $.post('get.htm', {pageId: pageId}).done(function(json) {
      if (json.success) {
        this.setState(json.datas);
      }
    }.bind(this));
  }
  doNextPage(pageId) {
    if (pageId) {
      this.fillCurrPage(pageId);
    } else {
      Dialog.alert('已经是最后一个').done(function() {
        window.close();
      });
    }
  }
  handleHomeworkChange(homeworkId) {
    this.setState({
      homeworkId: homeworkId,
      homeworkDtlId: 0,
      userId: 0,
      userName: ''
    });
  }
  handleUserNameChange(item) {
    if (item) {
      this.setState(item);
    } else {
      this.setState({
        homeworkDtlId: 0,
        userId: 0,
        userName: ''
      });
    }
  }
  handleJump() {
    var pageId = this.state.pageId;
    $.post('next.htm', {pageId: pageId}).done(function(json) {
      this.doNextPage(json.datas.nextPageId);
    }.bind(this));
  }
  handleSave() {
    let {homeworkId, userId} = this.state;
    if (!(homeworkId > 0)) {
      Utils.Notice.alert('请选择作业');
      return;
    }
    if (!(userId > 0)) {
      Utils.Notice.alert('请选择学生');
      return;
    }
    
    let state = this.state;
    var data = {
      pageId: state.pageId,
      lekeNo: state.lekeNo,
      userId: state.userId,
      userName: state.userName,
      homeworkId: state.homeworkId,
      homeworkDtlId: state.homeworkDtlId
    };
    var dataJson = JSON.stringify(data);
  
    $.post('save.htm', {dataJson: dataJson}).done(function(json) {
      if (json.success) {
        Utils.Notice.alert('匹配成功！');
        this.doNextPage(json.datas.nextPageId);
      }
    }.bind(this));
  }
  render() {
    let that = this;
    let {pageId, imgs, userName, homeworks, homeworkId} = this.state;
  	return (
  	  <div>
  	    <div className="c-sheet-show">
  	      <ImgView imgs={imgs} />
  	    </div>
  	    <div className="c-sheet-operation">
  	      <h5 className="head">异常处理</h5>
  	      <div className="con">
  	        <UserView>未知</UserView>
  	        <ErrorView>乐号异常</ErrorView>
  	        <ul className="c-abnormal-account">
              <li>
                <label>作业：</label>
                <HomeworkMatchCombo homeworks={homeworks} pageId={pageId} onChange={this.handleHomeworkChange} />
              </li>
              <li>
                <label>学生：</label>
                <UserNameMatchCombo homeworkId={homeworkId} onChange={this.handleUserNameChange} />
              </li>
            </ul>
  	        <div className="btns">
  	          <button className="u-btn u-btn-nm u-btn-bg-turquoise" onClick={this.handleSave}>提交</button>
  	          <button className="u-btn u-btn-nm u-btn-bg-gray" onClick={this.handleJump}>暂不处理</button>
  	        </div>
  	      </div>
  	    </div>
  	  </div>
  	);
  }
}

ReactDOM.render(<LekeNoHandle pageId={firstPageId} />, document.getElementById('container'));
});
