define(function(require, exports, module){
var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var {cx} = require('common/react/react-utils');
var ImgView = require('./imgview');
var $ = require('jquery');
var _ = require('underscore');
var Utils = require('utils');
var Dialog = require('dialog');
var Convertions = require('common/convertions');

let {Component, PropTypes} = React;

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

class ThumbView extends Component {
  constructor(props) {
    super(props);
    this.handleDelPage = this.handleDelPage.bind(this);
    this.handlePosition = this.handlePosition.bind(this);
  }
  handleDelPage() {
    if (this.props.onDelPage) {
      this.props.onDelPage(this.props.pageId);
    }
  }
  handlePosition() {
    if (this.props.onPosition) {
      this.props.onPosition(this.props.path);
    }
  }
  render() {
    let {path, index, deleted} = this.props;
    let indexStr = Convertions.toCnNum(index);
    let delHtml = [];
    if (deleted) {
      delHtml.push(<i className="del" onClick={this.handleDelPage}></i>);
    }
    return (
      <li>
        <div className="figure">
          <img src={path} width="132" onClick={this.handlePosition} />
          {delHtml}
        </div>
        <h5>{indexStr}</h5>
      </li>
    );
  }
}

class RepeatHandle extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bookId: props.bookId,
      pages: [],
      imgs: [],
      imgIndex: 0,
      userName: ''
    };
    this.fillCurrBook(props.bookId);
    this.handleJump = this.handleJump.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.handleDelPage = this.handleDelPage.bind(this);
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
    let {bookId, pages} = this.state;
    let counts = _.countBy(pages, 'index');
    for (let key in counts) {
      if (counts[key] > 1) {
        Utils.Notice.alert('还有重页没有删除呢');
        return;
      }
    }
    let pageIds = pages.map(page => page.pageId);
    let dataJson = JSON.stringify({bookId: bookId, pageIds: pageIds});
    $.post('save.htm', {dataJson: dataJson}).done(function(json) {
      if (json.success) {
        this.doNextBook(json.datas.nextBookId);
      }
    }.bind(this));
  }
  handlePosition(img) {
    let imgs = this.state.imgs;
    let imgIndex = _.indexOf(imgs, img);
    this.setState({
      imgIndex: imgIndex
    });
  }
  handleDelPage(pageId) {
    let pages = this.state.pages;
    let newPages = [];
    pages.forEach(page => {
      if (page.pageId !== pageId) {
        newPages.push(page);
      }
    });
    let imgs = _.flatten(newPages.map(page => page.imgs));
    this.setState({
      pages: newPages,
      imgs: imgs,
      imgIndex: 0
    });
  }
  render() {
    let that = this;
    let {pages, userName, imgs, imgIndex} = this.state;
    let counts = _.countBy(pages, 'index');
  	return (
  	  <div>
  	    <div className="c-sheet-show">
  	      <ImgView imgs={imgs} index={imgIndex} />
  	    </div>
  	    <div className="c-sheet-operation">
  	      <h5 className="head">异常处理</h5>
  	      <div className="con">
  	        <UserView>{userName}</UserView>
  	        <ErrorView>重页异常</ErrorView>
  	        <div className="c-abnormal-page">
              <ul className="num">
              {pages.map(page => {
                return (
                  <ThumbView pageId={page.pageId}
                    index={page.index}
                    path={page.imgs[0]}
                    deleted={counts[page.index] > 1}
                    onDelPage={that.handleDelPage}
                    onPosition={that.handlePosition}
                  />
                );
              })}
  	          </ul>
            </div>
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

ReactDOM.render(<RepeatHandle bookId={firstBookId} />, document.getElementById('container'));
});
