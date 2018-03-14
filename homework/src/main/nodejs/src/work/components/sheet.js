import React, {Component, PropTypes} from 'react';
import ReactCSSTransitionGroup from 'react/lib/ReactCSSTransitionGroup';
import ReactHammer from '../../common/ReactHammer';
import DocView from '../../common/docview';
import {FrameSet, FrameTop, FrameBottom} from '../../common/frame';
import {fmtScore, fmtTime} from '../../common/fmt';
import Question from './question';
import {handleJudgeState} from '../actions/answer';

export default class Main extends Component {
    render() {
        const {store, actions} = this.props;
        return (
            <ReactHammer onSwipeLeft={actions.onSheetSkipNext} onSwipeRight={actions.onSheetSkipPrev}>
                <div>
                    <Header store={store} actions={actions} />
                    <ReactCSSTransitionGroup component="div" transitionName={`slide-${store.control.enterMode}`} transitionEnterTimeout={400} transitionLeaveTimeout={400}>
                        <div key={store.control.viewName}>
                            <Sheet store={store} actions={actions} />
                            <Card store={store} actions={actions} onSkipIndex={actions.onSheetSkipIndex} />
                            <Result store={store} actions={actions} />
                        </div>
                    </ReactCSSTransitionGroup>
                </div>
            </ReactHammer>
        );
    }
}

class Header extends Component {
    render() {
        const {store, actions} = this.props;
        const nextViewName = store.control.viewName === 'card' ? 'sheet' : 'card';
        return (
            <header className="c-header">
                <div className="c-header-clock">{fmtTime(store.control.usedTime)}</div>
                <div className={`c-header-${store.mode}`} onClick={() => actions.onToggleErrMode()}></div>
                <div className="c-header-card" onClick={() => actions.onRouterView(nextViewName)}></div>
            </header>
        );
    }
}

class Sheet extends Component {
    render() {
        const {store, actions} = this.props;
        if (store.control.viewName === 'result') {
            return null;
        }
        const qdx = store.qdxs[store.control.index];
        if (store.paper.attachment == null) {
            return (
                <div ref="sheet" className="c-sheet">
                    <div className="c-sheet-body">
                        <Question store={store} actions={actions} qdx={qdx} />
                    </div>
                </div>
            );
        }
        return (
            <div ref="sheet" className="c-sheet">
                <FrameSet>
                    <FrameTop>
                        <div className="c-sheet-material">
                            <DocView attach={store.paper.attachment} />
                        </div>
                    </FrameTop>
                    <FrameBottom>
                        <div className="c-sheet-body">
                            <Question store={store} actions={actions} qdx={qdx} />
                        </div>
                    </FrameBottom>
                </FrameSet>
            </div>
        );
    }
}

class Card extends Component {
    render() {
        let {store, actions, onSkipIndex} = this.props;
        let display = store.control.viewName === 'card' ? 'block' : 'none';
        return (
            <div style={{display: display}}>
                <div className="c-card-overlay" onClick={() => actions.onRouterView('sheet')}></div>
                <div className="c-card" style={{paddingBottom: 50}}>
                    <div className="c-card-explain">
                        <span>说明：</span>
                        <span className="c-queno c-queno-finished"></span>
                        <span className="comment">已答</span>
                        <span className="c-queno c-queno-unfinish"></span>
                        <span className="comment">未答</span>
                    </div>
                    <CardBody store={store} onSkipIndex={onSkipIndex} />
                </div>
                <footer className="c-card-foot">
                    <button className="btn btn-save" onClick={actions.onWorkSave}>保存</button>
                    <button className="btn btn-submit" onClick={actions.onWorkSubmit}>提交</button>
                </footer>
            </div>
        );
    }
}

class CardBody extends Component {
    render() {
        let {store, onSkipIndex} = this.props;
        let detail = store.paper.detail;
        return (
            <div className="c-card-body">
                {detail.groups.map((group, i) => <CardGroup key={i} store={store} group={group} onSkipIndex={onSkipIndex} />)}
            </div>
        );
    }
}

class CardGroup extends Component {
    render() {
        let {store, group, onSkipIndex} = this.props;
        return (
            <div className="c-card-group">
                <div className="group-title"><div>{group.groupTitle}</div></div>
                <div className="group-ques">
                    {group.questions.map((que, i) => <CardQueNo key={i} store={store} que={que} onSkipIndex={onSkipIndex} />)}
                </div>
            </div>
        );
    }
}

class CardQueNo extends Component {
    render() {
        const {store, que, onSkipIndex} = this.props;
        let status = handleJudgeState(store, que.questionId);
        return (
            <div>
                <span className={`c-queno c-queno c-queno-${status}`} onClick={() => onSkipIndex(que.index)}>{que.ord}</span>
            </div>
        )
    }
}

class Result extends Component {
    render() {
        const {store} = this.props;
        if (store.control.viewName !== 'result') {
            return null;
        }
        let {finishNum, homeworkType, usedTime, isCorrect, score, lekeVal, expVal} = store.control.resultInfo;
        return (
            <div className="c-work-result">
                <div className="m-tips">
                    <i className="img"></i>
                    <div className="msg">作业提交成功！</div>
                </div>
                <div className="msg1">
                    你是全班第{finishNum}个提交{homeworkType}的学生，用时{usedTime}分钟。
                </div>
                <div className="msg2">
                    得分：{fmtScore(score)}{isCorrect ? '' : '+'}{lekeVal ? `，乐豆：+${lekeVal}` : ''}{expVal ? `，经验：+${expVal}` : ''}。
                </div>
            </div>
        );
    }
}
