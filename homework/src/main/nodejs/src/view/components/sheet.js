import React, {Component, PropTypes} from 'react';
import ReactCSSTransitionGroup from 'react/lib/ReactCSSTransitionGroup';
import ReactHammer from '../../common/ReactHammer';
import DocView from '../../common/docview';
import {FrameSet, FrameTop, FrameBottom} from '../../common/frame';
import {fmtScore, fmtTime} from '../../common/fmt';
import Question from './question';

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
                <div className="c-header-clock">{fmtTime(store.workInfo.usedTime)}</div>
                <div className="c-header-score">{fmtScore(store.workInfo.score)}分</div>
                <div className="c-header-card" onClick={() => actions.onRouterView(nextViewName)}></div>
            </header>
        );
    }
}

class Sheet extends Component {
    componentDidMount() {
        const {actions} = this.props;
        $(this.refs.sheetbody).on("swipeLeft", function() {
            actions.onSheetSkipNext();
        }).on("swipeRight", function() {
            actions.onSheetSkipPrev();
        });
    }
    render() {
        const {store, actions} = this.props;
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
        let workInfo = store.workInfo;
        return (
            <div style={{display: display}}>
                <div className="c-card-overlay" onClick={() => actions.onRouterView('sheet')}></div>
                <div className="c-card">
                    <CardBody store={store} onSkipIndex={onSkipIndex} />
                </div>
            </div>
        );
    }
}

class CardBody extends Component {
    render() {
        let {store, control, onSkipIndex} = this.props;
        let detail = store.paper.detail;
        return (
            <div className="c-card-body">
                {detail.groups.map((group, i) => <CardGroup key={i} group={group} store={store} onSkipIndex={onSkipIndex} />)}
            </div>
        );
    }
}

class CardGroup extends Component {
    render() {
        let {group, store, onSkipIndex} = this.props;
        return (
            <div className="c-card-group">
                <div className="group-title"><div>{group.groupTitle}(总分{fmtScore(group.score)}分)</div></div>
                <div className="group-ques">
                    {group.questions.map((que, i) => <CardQueNo key={i} que={que} store={store} onSkipIndex={onSkipIndex} />)}
                </div>
            </div>
        );
    }
}

class CardQueNo extends Component {
    render() {
        const {que, store, onSkipIndex} = this.props;
        return (
            <div>
                <span className={`c-queno c-queno c-queno-isright`} onClick={() => onSkipIndex(que.index)}>{que.ord}</span>
            </div>
        )
    }
}
