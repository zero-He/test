import React, {Component, PropTypes} from 'react';
import LekeDate from '../common/date';
import {fmtCount, fmtScore} from '../common/fmt';

// 资源类型对应的图标
let ICONS = ['type-courseware', 'type-microgram', 'type-test-paper'];

// 返回微课、课件:（未学习|已学习）试卷:（……）状态
function status(data) {
    let {resType, submitTime, correctTime, bugFixStage} = data;
	if (resType == 3) {//试卷
		if (submitTime == null) {
			return '待完成';
		}
		if (bugFixStage == null || bugFixStage == 0) {
			return correctTime != null ? '已批改' : '待批改';
		} else if (bugFixStage == 1) {
			return '待订正';
		} else if (bugFixStage == 2) {
			return '已订正';
		} else if (bugFixStage == 3) {
			return '订正通过';
		}
	} else {//微课和课件
        return submitTime == null ? '未学习' : '已学习'
	}
}

function urlencoder(url, data) {
    if (url) {
        return url.replace('{{homeworkId}}', data.homeworkId).replace('{{homeworkDtlId}}', data.homeworkDtlId);
    }
    return url;
}

// 按钮区域
class Buttons extends Component {
    render() {
        const {data, links} = this.props;
        if (Csts.isStudent) {
            if (data.resType == 3) {
                return (
                    <div>
                        {data.submitTime == null ? <a href={urlencoder(links.work, data)}>答题</a> : null}
                        {(data.submitTime != null && data.bugFixStage == 1) ? <a href={urlencoder(links.view, data)}>查看</a> : null}
                        {(data.submitTime != null && data.bugFixStage != 1) ? <a href={urlencoder(links.view, data)}>查看</a> : null}
                        {(data.correctTime != null && data.homeworkType != 6) ? <a href={urlencoder(links.analyse, data)}>报告</a> : null}
                    </div>
                );
            } else {
                return data.submitTime == null ? <a href={urlencoder(links.work, data)}>学习</a> : <a href={urlencoder(links.view, data)}>复习</a>;
            }
        } else {
            if (data.resType == 3) {
                return (
                    <div>
                        <a href={urlencoder(links.view, data)}>查看</a>
                        {(data.correctTime != null && data.homeworkType != 6) ? <a href={urlencoder(links.analyse, data)}>报告</a> : null}
                    </div>
                );
            } else {
                return <a href={urlencoder(links.view, data)}>查看</a>
            }
        }
    }
}

// 试卷得分
class ScoreBox extends Component {
    render() {
        let {score} = this.props, marks;
        if (score === undefined || score === null || score === '') {
			marks = ['null', 'null'];
		} else {
            score = fmtScore(score);
            marks = String(score).split('').map(v => v === '.' ? 'point' : v);
        }
        return (
            <div className="c-score-box">
                {marks.map((mark, i) => <span key={i} className={`state-${mark}`}></span>)}
                <span className="state-score"></span>
            </div>
        );
    }
}

// 列表项
export default class WorkItem extends Component {
    render() {
        let {data} = this.props;
        return (
            <section className="c-workitem">
                <div className="c-workitem-head">
                    <span className="teacher">{data.teacherName}</span>
                    <span className="close-time">{LekeDate.format(data.closeTime, 'yyyy-MM-dd HH:mm')} 截止</span>
                    <span className="status">{status(data)}</span>
                </div>
                <div className="c-workitem-body">
                    <div className="icon">
	                    <div className={ICONS[data.resType - 1] || 'type-test-paper'}></div>
                    </div>
                    <div className="middle">
                        <div className="name">{data.homeworkName}</div>
                        <div className="flags">
        	                <span className="flag-box">{data.rawTypeName}</span>
        				    {data.isSelfCheck ? <span className="flag-box">自行校对</span> : null}
        				    {data.isOpenAnswer ? <span className="flag-box">公布答案</span> : null}
                        </div>
                        <div className="submit-time">
                            <span>{data.resType === 3 ? '提交' : '学习'}时间：</span>
                            <span>{data.submitTime ? LekeDate.format(data.submitTime, 'yyyy-MM-dd HH:mm') : '--'}</span>
        				    {data.submitStatus === 2 ? <span className="flag-box">迟交</span> : null}
        				    {data.scoreRate == 1 ? <span className="flag-box">满分</span> : null}
                        </div>
                    </div>
                    <div className="score">
                        {data.correctTime != null ? <ScoreBox score={data.score}/> : null}
                    </div>
                </div>
                <div className="c-workitem-foot">
    		        {data.status == 2 ? '已作废' : <Buttons data={data} links={Links} />}
                </div>
            </section>
        );
    }
}
