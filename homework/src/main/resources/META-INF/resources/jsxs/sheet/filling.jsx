define(function(require, exports, module){
var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var {cx} = require('common/react/react-utils');
var ImgView = require('./imgview');
var $ = require('jquery');
var Utils = require('utils');
var Dialog = require('dialog');

let {Component, PropTypes} = React;

let CHOICE_MARKS = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
let JUDGEMENT_MARKS = ['√', 'X'];

class Choice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      answers: []
    };
    this.handlePosition = this.handlePosition.bind(this);
  }
  componentWillReceiveProps(nextProps) {
    this.setState({
      answers: []
    });
  }
  handleChange(answer) {
    let answers = this.state.answers;
    let index = answers.indexOf(answer);
    if (this.props.multi) {
      if (index >= 0) {
        answers.splice(index, 1);
      } else {
        answers.push(answer);
      }
    } else {
      if (index >= 0) {
        answers = [];
      } else {
        answers = [answer];
      }
    }
    this.setState({
      answers: answers
    });
    if (this.props.result) {
      this.props.result.answers = answers;
    }
  }
  handlePosition() {
    if (this.props.onPosition) {
      this.props.onPosition(this.props.zone);
    }
  }
  render() {
    let that = this;
    let marks = this.props.judge ? JUDGEMENT_MARKS : CHOICE_MARKS
    let {qno, answers} = this.props;
    let checkedAnswers = this.state.answers;
    return (
      <li>
        <dfn>{qno}</dfn>
        {answers.map((answer, idx) => {
          let checked = checkedAnswers.indexOf(answer) >= 0;
          return (
            <span key={answer}
              className={cx('item', {'active': checked})}
              onClick={this.handleChange.bind(that, answer)}>
              {marks[idx]}
            </span>
          );
        })}
        <span key="pos" className="pos" onClick={this.handlePosition}></span>
      </li>
    );
  }
}

Choice.propTypes = {
  no: PropTypes.string,
  judge: PropTypes.bool,
  multi: PropTypes.bool
}

function isValidScore(val, minVal, maxVal) {
  let isValidNum = /^\d+\.?\d{0,2}$/.test(val);
  if (isValidNum) {
    let num = parseFloat(val);
    return num >= minVal && num <= maxVal;
  }
  return false;
}

class ComboBox extends Component {
  constructor(props) {
    super(props);
    props.items.forEach(v => v.checked = (v.value == props.value));
    this.state = {
      value: props.value,
      items: props.items,
      showItems: false
    };
    this.handleInputChange = this.handleInputChange.bind(this);
  }
  handleInputChange(e) {
    let value = e.target.value;
    let {minVal, maxVal, onChange} = this.props;
    if (value == '' || isValidScore(value, minVal, maxVal)) {
      let {items} = this.state;
      items.forEach(v => v.checked = false);
      this.setState({value: value, items: items});
      if (onChange) {
        onChange(value);
      }
    }
  }
  handleItemClick(idx) {
    let {value, items} = this.state;
    items.forEach((v, i) => {
      v.checked = (i == idx);
      if (v.checked) {
        value = v.value;
      }
    });
    this.setState({value: value, items: items, showItems: false});
    let {onChange} = this.props;
    if (onChange) {
      onChange(value);
    }
  }
  componentDidMount() {
    let {input} = this.refs;
    $(input).click((e) => {
      let {showItems} = this.state;
      this.setState({showItems: !showItems});
    });
    $(document).click((e) => {
      if (e.target != input) {
        this.setState({showItems: false});
      }
    });
  }
  render() {
    let that = this;
    let {value, items, showItems} = this.state;
    return (
      <div className="u-custom-select">
        <input ref="input" type="text" className="u-ipt u-ipt-sm" value={value} onChange={this.handleInputChange} />
        <ul className="select-list" style={{display: showItems ? 'block' : 'none'}}>
          {items.map((item, i) => {
            let className = item.checked ? "select-item select-item-on" : "select-item";
            return <li key={i} className={className} value={item.value} onClick={that.handleItemClick.bind(that, i)}>{item.label}</li>;
          })}
        </ul>
      </div>
    );
  }
}

class Openend extends Component {
  constructor(props) {
    super(props);
    this.state = {
      score: null
    };
    this.handleChange = this.handleChange.bind(this);
    this.handlePosition = this.handlePosition.bind(this);
  }
  componentWillReceiveProps(nextProps) {
    this.setState({
      score: null
    });
    this.refs.select.value = "";
  }
  handleChange(val) {
    let score = val || '0';
    this.setState({
      score: score
    });
    if (this.props.result) {
      this.props.result.score = parseFloat(score);
    }
  }
  handlePosition() {
    if (this.props.onPosition) {
      this.props.onPosition(this.props.zone);
    }
  }
  render() {
    let that = this;
    let {qno, maxScore} = this.props;
    let items = [], max = Math.floor(maxScore);
    for (var i = 0; i <= max; i++) {
      items.push({
        value: `${i}`,
        label: `${i}`
      });
    }
    if (max < maxScore) {
      items.push({
        value: `${maxScore}`,
        label: `${maxScore}`
      });
    }
    return (
      <li>
        <dfn>{qno}</dfn>
        <ComboBox value="0" items={items} minVal={0} maxVal={maxScore} onChange={this.handleChange} />
        <span key="pos" className="pos" onClick={this.handlePosition}></span>
      </li>
    );
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

class FillView extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    let rules = this.props.rules;
    return (
      <div className="c-abnormal-fill">
        <ul className="lists">
        {this.props.children}
        </ul>
      </div>
    );
  }
}

class Filling extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bookId: props.bookId,
      imgs: [],
      rules: [],
      userName: '',
      errorNo: '',
      results: [],
      zone: null
    };
    this.fillCurrBook(props.bookId);
    this.handleJump = this.handleJump.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.handlePosition = this.handlePosition.bind(this);
  }
  fillCurrBook(bookId) {
    $.post('get.htm', {bookId: bookId}).done(function(json) {
      if (json.success) {
        this.setState(json.datas);
      }
    }.bind(this));
  }
  doNextBook(bookId) {
    if (bookId) {
      this.fillCurrBook(bookId);
    } else {
      Dialog.alert('已经是最后一个').done(function() {
        window.close();
      });
    }
  }
  handleJump() {
    var bookId = this.state.bookId;
    $.post('next.htm', {bookId: bookId}).done(function(json) {
      this.doNextBook(json.datas.nextBookId);
    }.bind(this));
  }
  handleSave() {
    let {bookId, results} = this.state;
    var dataJson = JSON.stringify(results);
    $.post('save.htm', {bookId: bookId, dataJson: dataJson}).done(function(json) {
      if (json.success) {
        this.doNextBook(json.datas.nextBookId);
      }
    }.bind(this));
  }
  handlePosition(zone) {
    if (this.refs.imgview && this.refs.imgview.handleDraw) {
      this.refs.imgview.handleDraw(zone);
    }
  }
  render() {
    let that = this;
    let {bookId, imgs, rules, userName, errorNo, zone} = this.state;
    let errorName, errorInfo;
    if (errorNo === '20302') {
      errorName = '';
      errorInfo = (
        <div className="c-abnormal-match">
          <p>1. 学生<span className="s-c-orange">填涂客观题答案超过设置的正确答案数量</span>，如单选题填涂多个答案；</p>
          <p>2. 学生<span className="s-c-orange">未在规定区域答题</span>，如超出答题区等；</p>
          <p>3. 老师<span className="s-c-orange">未在主观题打分框打分</span>，或者打分不规范；</p>
        </div>
      );
    } else {
      errorName = '';
      errorInfo = (
        <div className="c-abnormal-match">
          <p>题目<span className="s-c-orange">得分为0分，疑似异常</span>；</p>
          <p>异常原因：学生填涂不规范，<span className="s-c-orange">如未填涂，填涂过淡，填涂区域小</span>。</p>
        </div>
      );
    }
  	return (
  	  <div>
  	    <div className="c-sheet-show">
  	      <ImgView ref="imgview" imgs={imgs} index={zone && zone.idx ? zone.idx - 1 : null} />
  	    </div>
  	    <div className="c-sheet-operation">
  	      <h5 className="head">异常处理</h5>
  	      <div className="con">
  	        <UserView>{userName}</UserView>
  	        <ErrorView>{errorName}</ErrorView>
  	        {errorInfo}
  	        <FillView>
  	        {rules.map((rule, i) => {
  	          var result = that.state.results.filter(res => res.questionId == rule.qid)[0];
              if (rule.rangeType == '1') {
                return <Choice key={bookId + i} {...rule} result={result} onPosition={this.handlePosition} />;
              } else if (rule.rangeType == '11') {
                result.score = 0;
                return <Openend key={bookId + i} {...rule} result={result} onPosition={this.handlePosition} />;
              } else {
                return <li key={bookId + i}>{rule.title}</li>;
              }
            })}
  	        </FillView>
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

ReactDOM.render(<Filling bookId={firstBookId} />, document.getElementById('container'));
});
