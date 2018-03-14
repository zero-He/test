import types from './types';
import transformInitData from './transformInitData';
import Modal from '../../common/modal';
import confirm from '../../common/confirm';
import {stateToAnswerJson} from './answer';

export function getInitDatas() {
    return (dispatch, getState) => {
        let state = getState();
        webapi.getInitDatas(data => {
            data = transformInitData(data, state.basic.mode == 'errque' ? 'allque' : 'errque');
            dispatch({
                type: types.INIT_DATAS,
                data
            });
        });
    }
}

export function onToggleErrMode() {
    return (dispatch, getState) => {
        let state = getState();
        webapi.getInitDatas(data => {
            data = transformInitData(data, state.basic.mode == 'errque' ? 'allque' : 'errque');
            dispatch({
                type: types.UPDATE_DATAS,
                data
            });
        });
    }
}

export function onDeviceReady() {
    return dispatch => {
        document.addEventListener('deviceready', function() {
            console.log('cordova deviceready.');
            dispatch({type: types.DEVICE_READY});
        }, false);
    }
}

// 视图路由
export function onRouterView(viewName) {
    return {
        type: types.ROUTER_VIEW,
        viewName
    };
}

// 题目路由(序号)
export function onSheetSkipIndex(index) {
    return {
        type: types.SHEET_SKIP_INDEX,
        index
    }
}

// 题目路由(下一题)
export function onSheetSkipNext() {
    return {type: types.SHEET_SKIP_NEXT};
}

// 题目路由(上一题)
export function onSheetSkipPrev() {
    return {type: types.SHEET_SKIP_PREV};
}

function fetchAnswers(getState, qid) {
    const state = getState();
    return state.replys[qid] ? state.replys[qid].concat() : state.paper.ques[qid].answers.map(a => "");
}

// 判断题答案处理
export function onAnswerJudgement(qid, answerId) {
    return {
        type: types.ANSWER_JUDGEMENT,
        qid,
        answers: [answerId]
    };
}

// 单选题答案处理
export function onAnswerSingleChoice(qid, answerId) {
    return {
        type: types.ANSWER_SINGLE_CHOICE,
        qid,
        answers: [answerId]
    };
}

// 多选题答案处理
export function onAnswerMultiChoice(qid, answerId) {
    return (dispatch, getState) => {
        const state = getState();
        const answers = (state.replys[qid] || []).concat();
        const index = answers.indexOf(answerId);
        if (index >= 0) {
            answers.splice(index, 1);
        } else {
            answers.push(answerId);
        }
        dispatch({
            type: types.ANSWER_MULTI_CHOICE,
            qid,
            answers
        });
    }
}

// 填空题答案处理
export function onAnswerFillBlank(qid, index, content) {
    return (dispatch, getState) => {
        const answers = fetchAnswers(getState, qid);
        answers[index] = content;
        dispatch({
            type: types.ANSWER_FILLBLANK,
            qid,
            answers
        });
    };
}

// 断句题答案处理
export function onAnswerPunctuate(qid, index, content) {
    return (dispatch, getState) => {
        const answers = fetchAnswers(getState, qid);
        if (content == undefined) {
            content = answers[index] === "" ? "/" : "";
        }
        answers[index] = content;
        dispatch({
            type: types.ANSWER_PUNCTUATE,
            qid,
            answers
        });
    }
}

// 问答题答案处理
export function onAnswerOpenend(qid, content) {
    return {
        type: types.ANSWER_OPENEND,
        qid,
        answers: [content]
    };
}

// 口语题答案处理
export function onAnswerOral(qid, content) {
    return {
        type: types.ANSWER_OPENEND,
        qid,
        answers: [content]
    };
}

// 其它题答案处理
export function onAnswerHandwrite(qid, content) {
    return {
        type: types.ANSWER_OPENEND,
        qid,
        answers: [content]
    };
}

let hbTimer, asTimer;

// 心跳
export function onHeartbeat() {
    return (dispatch, getState) => {
        hbTimer = setInterval(() => {
            dispatch({type: types.HEARTBEAT});
        }, 1000);
        asTimer = setInterval(() => {
            const state = getState();
            const {answerJson} = stateToAnswerJson(state);
            webapi.heartbeat(answerJson);
        }, 30 * 1000);
    };
}

// 保存作业
export function onWorkSave() {
    return (dispatch, getState) => {
        const state = getState();
        const {answerJson} = stateToAnswerJson(state);
        webapi.save(answerJson).done(json => {
            if (json.success) {
                Modal.toast('保存成功');
            } else {
                Modal.toast(json.message);
            }
        });
    }
}

function submit(answerJson, dispatch) {
    webapi.submit(answerJson).done(json => {
        if (json.success) {
            dispatch({
                type: types.ROUTER_RESULT,
                resultInfo: json.datas
            });
            clearInterval(hbTimer);
            clearInterval(asTimer);
        } else {
            Modal.toast(json.message);
        }
    });
}

// 提交作业
export function onWorkSubmit() {
    return (dispatch, getState) => {
        const state = getState();
        const {answerJson, unfinish} = stateToAnswerJson(state);
        if (unfinish > 0) {
            confirm(`您还有${unfinish}题未完成，确认提交吗？`).done(function() {
                submit(answerJson, dispatch);
            });
        } else {
            submit(answerJson, dispatch);
        }
    }
}
