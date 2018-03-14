import React, {Component, PropTypes} from 'react';
import LekeDate from '../common/date';
import {fmtCount, fmtScore} from '../common/fmt';

function iconType(data) {
	let {resType} = data;
	if(resType == 1){
		return 'type-courseware';
	}else if(resType == 2){
		return 'type-microgram';
	}else if(resType == 3){
		return 'type-test-paper';
	}else{
		return 'type-test-paper';
	}
}

function urlencoder(url, data) {
    if (url) {
        return url.replace('{{homeworkId}}', data.homeworkId).replace('{{homeworkDtlId}}', data.homeworkDtlId);
    }
    return url;
}

// 内容区域
class ContentView extends Component {
    render() {
        const {data} = this.props;
        if (data.resType == 3) {
            return (
                <div className="middle">
                    <div className="name">{data.homeworkName}</div>
                    <div className="flags">
    	                {data.paperType == 2 ? <span className="flag-box">答题卡</span> : <span className="flag-box">普通试卷</span>}
    				    {data.isSelfCheck ? <span className="flag-box">不作批改</span> : null}
    				    {data.isOpenAnswer ? <span className="flag-box">公布答案</span> : null}
                    </div>
                    <div className="submit-time">
                        <span>上交：</span><span>{data.finishNum}/{data.totalNum}</span>&nbsp;&nbsp;&nbsp;
    				    <span>批改：</span><span>{data.correctNum}/{data.finishNum}</span>&nbsp;&nbsp;&nbsp;
                        <span>平均分：</span>{data.avgScore == null ? <span>--</span>:<span>{data.avgScore}</span>}
                    </div>
                </div>
            );
        } else {
            return (
                <div className="middle">
                    <div className="name">{data.homeworkName}</div>
                    <div className="flags">
                        <span className="flag-box">{data.rawTypeName}</span>
    	                {data.isSelfCheck ? <span className="flag-box">不作批改</span> : null}
    	                {data.isOpenAnswer ? <span className="flag-box">公布答案</span> : null}
                    </div>
                    <div className="submit-time">
                        <span>已查看：</span>
                        <span>{data.finishNum}/{data.totalNum}</span>
                    </div>
                </div>
    		);
        }
    }
}

export default class WorkItem extends Component {
    render() {
        let {data} = this.props;
        return (
            <section className="c-workitem">
                <div className="c-workitem-head">
                    <span className="teacher">{data.className}</span>
                    <span className="close-time">{LekeDate.format(data.closeTime, 'yyyy-MM-dd HH:mm')} 截止</span>
                </div>
                <div className="c-workitem-body">
                    <div className="icon">
	                    <div className={iconType(data)}></div>
                    </div>
	                <ContentView data={data}/>
                </div>
                <div className="c-workitem-foot">
	                {(data.correctNum > 0 && data.homeworkType != 6) ? <a href={urlencoder(Links.analyse, data)} className="ana-link">报告</a> : ''}
                </div>
            </section>
        );
    }
}
