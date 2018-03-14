import React, {Component, PropTypes} from 'react';
import Tips from '../common/tips';

export default class WorkKeeper extends Component {
    render() {
        const {store, actions} = this.props;
        const userId = store.childs[store.childIdx].id;
        return (
            <div className="c-kemu-body">
                <ChildBox childIdx={store.childIdx} childs={store.childs} onChangeChild={actions.onChangeChild} />
                <SubjBox fetching={store.fetching} userId={userId} subjects={store.subjects} />
            </div>
        );
    }
}

class ChildBox extends Component {
    handleClick(userId) {
        const {onChangeChild} = this.props;
        if (onChangeChild) {
            onChangeChild(userId);
        }
    }
    render() {
        const that = this;
        const {childs, childIdx} = this.props;
        if (childs.length == 1) {
            return null;
        }
        return (
            <div className="c-child-box">
                <ul>
                {childs.map((child, index) => {
                    const className = (index === childIdx ? 'current' : '');
                    return <li key={index} className={className} onClick={this.handleClick.bind(that, index)}>{child.userName}</li>;
                })}
                </ul>
            </div>
        );
    }
}

class SubjBox extends Component {
    componentDidMount() {
        $(".c-kemu-box").on({
			touchstart : function(e) {
				$(this).addClass('touch');
			},
			touchend : function(e) {
				$(this).removeClass('touch');
			},
			touchcancel : function(e) {
				$(this).removeClass('touch');
			}
		}, 'li');
    }
    render() {
        const {fetching, userId, subjects} = this.props;
        if (fetching) {
            return (
                <article className="c-kemu-box">
                    <Tips message="数据加载中......" />
                </article>
            );
        }
        if (!(subjects && subjects.length)) {
            return (
                <article className="c-kemu-box">
                    <Tips message="暂无数据~~" />
                </article>
            );
        }
        return (
            <article className="c-kemu-box">
            	<ul className="c-kemu">
                {subjects.map(subject => <SubjItem key={subject.subjectId} userId={userId} {...subject} />)}
                </ul>
            </article>
        );
    }
}

class SubjItem extends Component {
    render() {
        const {userId, subjectId, subjectName, unfinishNum} = this.props;
        return (
            <a href={`/auth/m/person/homework/worklist.htm?studentId=${userId}&subjectId=${subjectId}`}>
    			<li>
	    			<span key="name" className={`title title${subjectId}`}>{subjectName}</span>
					<span key="num" className="unfinish-num">{unfinishNum <= 99 ? unfinishNum : '99+'}</span>
					<span key="status" className="unfinish">待完成</span>
					<span key="icon" className={`icon icon${subjectId}`}></span>
	    		</li>
    		</a>
        );
    }
}
