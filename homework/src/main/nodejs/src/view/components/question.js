import React, {Component, PropTypes} from 'react';
import ReactCSSTransitionGroup from 'react/lib/ReactCSSTransitionGroup';
import AudioPlayer from '../../common/player';
import RichView from '../../common/richview';
import {FrameSet, FrameTop, FrameBottom} from '../../common/frame';
import {fmtScore} from '../../common/fmt';
import {clearImgStyle} from '../../common/htmlparser';

export default class QuestionWrapper extends Component {
    render() {
        const {store, actions, qdx} = this.props;
        const style = {};
        style[store.control.enterMode] = 0;
        return (
            <div className="c-que-wrapper">
                <div className="wrapper-head">
                    <QueNav group={qdx.group} />
                </div>
                <ReactCSSTransitionGroup component="div" className="wrapper-body" transitionName={`slide-${store.control.enterMode}`}
                        transitionEnterTimeout={400} transitionLeaveTimeout={400}>
                    <div key={qdx.que.qid} className="c-slide-item" style={style}>
                        <ReactQuestion store={store} actions={actions} que={qdx.que} sub={qdx.sub} />
                    </div>
                </ReactCSSTransitionGroup>
            </div>
        );
    }
}

class QueNav extends Component {
    render() {
        let {title, score, index, total} = this.props.group;
        return (
            <div className="c-que-nav">
                <div className="title">
                    <span className="title-name">{title}</span>
                    <span className="title-score">(总分<i className="title-total">{fmtScore(score)}</i>分)</span>
                </div>
                <div className="serial">
                    <span className="serial-curr">{index}</span>
                    <span className="serial-sep">/</span>
                    <span className="serial-total">{total}</span>
                </div>
            </div>
        );
    }
}

class ReactQuestion extends Component {
    render() {
        const {store, actions, que, sub} = this.props;
        const question = store.ques[que.qid];
        const theme = store.paper.paperType;
        const QuestionComponent = QuestionComponents[question.template];
        return <QuestionComponent store={store} actions={actions} theme={theme} que={que} sub={sub} question={question} />
    }
}

class QueIndex extends Component {
    render() {
        const {que, status} = this.props;
        return (
            <div className="c-que-index">
                <div className="serial">{que.index}. ({fmtScore(que.score)}分)</div>
                <div className="result">
                    <ResultScoreBox status={status} />
                </div>
            </div>
        );
    }
}

class ResultScoreBox extends Component {
    render() {
        const {isSubmit, valid, score, selfCheck} = this.props.status;
        if (isSubmit === false) {
            return null;
        }
        const nodes = [];
        if (valid != null) {
            const marks = String(fmtScore(score)).split('').map(v => v === '.' ? 'point' : v);
            nodes.push(<span key="valid" className={`state-${valid}`}></span>);
            {marks.map((mark, i) => nodes.push(<span key={i} className={`state-${mark}`}></span>))}
            nodes.push(<span key="score" className="state-score"></span>);
        } else if (selfCheck) {
            nodes.push(<i key="selfcheck" className="state-selfcheck">自行校对</i>)
        } else {
            nodes.push(<i key="uncorrect" className="state-uncorrect">待批改</i>)
        }
        return <span className="c-score-box">{nodes}</span>;
    }
}

class Judgement extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <JudgementAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class SingleChoice extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <SingleChoiceAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class MultiChoice extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <MultiChoiceAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class FillBlank extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <FillBlankQueStem theme={theme} actions={actions} question={question} />
                <FillBlankAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Punctuate extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <PunctuateAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Openend extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem theme={theme} question={question} />
                <OpenendAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Oral extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem theme={theme} question={question} />
                <OralAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Handwrite extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <HandwriteAnswerArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class BigQue extends Component {
    render() {
        let {store, actions, theme, question, que, sub} = this.props;
        let subquestion = store.ques[sub.qid];
        const QuestionComponent = QuestionComponents[subquestion.template];
        const style = {height: 'auto'};
        style[store.control.enterMode] = 0;
        if (theme == 2) {
            return (
                <div>
                    <QueStem question={question} />
                    <ReactCSSTransitionGroup transitionName={`slide-${store.control.enterMode}`} transitionEnterTimeout={400} transitionLeaveTimeout={400}>
                        <div key={sub.qid} className="c-slide-item" style={style}>
                            <BigQueChildNav index={sub.index} total={sub.total} />
                            <QuestionComponent store={store} actions={actions} theme={theme} que={sub} question={subquestion} />
                        </div>
                    </ReactCSSTransitionGroup>
                </div>
            );
        }
        return (
            <FrameSet>
                <FrameTop>
                    <div className="c-que">
                        <QueStem question={question} />
                    </div>
                </FrameTop>
                <FrameBottom>
                    <ReactCSSTransitionGroup transitionName={`slide-${store.control.enterMode}`} transitionEnterTimeout={400} transitionLeaveTimeout={400}>
                        <div key={sub.qid} className="c-slide-item" style={style}>
                            <BigQueChildNav index={sub.index} total={sub.total} />
                            <QuestionComponent store={store} actions={actions} theme={theme} que={sub} question={subquestion} />
                        </div>
                    </ReactCSSTransitionGroup>
                </FrameBottom>
            </FrameSet>
        );
    }
}

export class BigQueChildNav extends Component {
    render() {
        let {index, total} = this.props;
        return (
            <div className="c-que-child-nav">
                <div className="title">
                    <span className="title-name">子题</span>
                </div>
                <div className="serial">
                    <span className="serial-current">{index}</span>
                    <span className="serial-separator">/</span>
                    <span className="serial-total">{total}</span>
                </div>
            </div>
        );
    }
}

let QuestionComponents = {
    "Judgement": Judgement,
    "SingleChoice": SingleChoice,
    "MultiChoice": MultiChoice,
    "FillBlank": FillBlank,
    "Punctuate": Punctuate,
    "Openend": Openend,
    "Oral": Oral,
    "Handwrite": Handwrite,
    "BigQue": BigQue,
    "Cloze": BigQue,
    "Reading": BigQue,
    "Listening": BigQue
};

class QueStem extends Component {
    render() {
        let {stemContent} = this.props.question;
        if (!(stemContent && stemContent.length > 0)) {
            return null;
        }
        let html = "", audios = [];
        stemContent.split(/<!--\s+audio\s*:\s*(\S+)\s*-->/).forEach((text, index) => {
            if (index % 2 === 0) {
                html += text;
            } else {
                audios.push(<AudioPlayer key={text} source={text} />);
            }
        });
        return (
            <div className="c-que-stem">
                {audios}
                <div dangerouslySetInnerHTML={{__html: html}}></div>
            </div>
        );
    }
}

function parseAnswerResultStatus(store, qid) {
    if (store.workInfo.submitTime == null) {
        return {
            isSubmit: false
        };
    }
    const result = store.results[qid];
    let valid = null, score = null, selfCheck = false;
    if (result) {
        score = result.totalResultScore;
        if (result.totalIsRight == null) {
            valid = null;
        } else if (result.totalIsRight == true) {
            valid = 'right';
        } else {
            valid = result.totalScoreRate == 0 ? 'wrong' : 'half';
        }
    }
    if (store && store.workInfo) {
        selfCheck = store.workInfo.selfCheck;
    }
    return {valid, score, selfCheck};
}

function parseAnswerResults(result) {
    if (result && result.answerResults) {
        return result.answerResults;
    }
    return null;
}

function parseAnswerResultsContent(result) {
    result = parseAnswerResults(result);
    return result ? result.map(a => a.myAnswer) : [];
}

class AnswerArea extends Component {
    render() {
        return <div className="c-area"></div>;
    }
}

const marks = ['对', '错'];
class JudgementAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const answerIds = parseAnswerResultsContent(store.results[question.questionId]);
        const options = question.answers.map((answer, i) => {
            const selected = answerIds.indexOf(`${answer.answerId}`) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`}>{marks[i]}</div>
                </li>
            );
        });
        return (
            <div className="c-area">
                <ul className={`c-area-options c-area-options-2`}>{options}</ul>
            </div>
        );
    }
}

class SingleChoiceAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const answerIds = parseAnswerResultsContent(store.results[question.questionId]);
        const options = question.answers.map((answer, i) => {
            const selected = answerIds.indexOf(`${answer.answerId}`) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`}>{String.fromCharCode(65 + i)}</div>
                    <div className="option-text" dangerouslySetInnerHTML={{__html: answer.answerContent}}></div>
                </li>
            );
        });
        return (
            <div className="c-area">
                <ul className={`c-area-options c-area-options-${theme}`}>{options}</ul>
            </div>
        );
    }
}

class MultiChoiceAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const answerIds = parseAnswerResultsContent(store.results[question.questionId]);
        const options = question.answers.map((answer, i) => {
            const selected = answerIds.indexOf(`${answer.answerId}`) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`}>{String.fromCharCode(65 + i)}</div>
                    <div className="option-text" dangerouslySetInnerHTML={{__html: answer.answerContent}}></div>
                </li>
            );
        });
        return (
            <div className="c-area">
                <ul className={`c-area-options c-area-options-${theme}`}>{options}</ul>
            </div>
        );
    }
}

class FillBlankQueStem extends Component {
    render() {
        let{stemContent} = this.props.question;
        if (!(stemContent && stemContent.length > 0)) {
            return null;
        }
        let index = 0;
        var text = stemContent.split(/##([\s\S]*?)##/g).map(function(text, i) {
			if (i % 2 == 0) {
                return text;
			} else {
                index++;
				return `<span class="c-que-stem-fillmark"><i>[${index}]</i></span>`;
			}
		}).join('');
        return <div className="c-que-stem" dangerouslySetInnerHTML={{__html: text}}></div>;
    }
}

class FillBlankAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const result = store.results[question.questionId];
        if (isSheetAnswer(result)) {
            return <SheetAnswerArea html={result.answerResults[0].myAnswer} />;
        }
        const results = parseAnswerResults(result);
        return (
            <div className="c-area">
                <div className='c-area-fillblank'>
                {question.answers.map((answer, index) => {
                    let html = '', state = null;
                    if (results && results[index]) {
                        const answerResult = results[index];
                        html = answerResult.myAnswer;
                        if (answerResult.isRight === true) {
                            state = <div className="fill-state fill-state-right"></div>;
                        } else if (answerResult.isRight === false) {
                            state = <div className="fill-state fill-state-wrong"></div>;
                        }
                    }
                    return (
                        <div key={index} className={result ? 'fill result' : 'fill'}>
                            <div className="fill-index">[{answer.ord + 1}]</div>
                            <div className="fill-text">
                                <RichView html={html} />
                            </div>
                            {state}
                        </div>
                    );
                })}
                </div>
            </div>
        );
    }
}

class SheetAnswerArea extends Component {
    render() {
        const {html} = this.props;
        return (
            <div className="c-area">
                <div className="c-area-openend">
                    <RichView html={html} />
                </div>
            </div>
        );
    }
}

class PunctuateAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const result = store.results[question.questionId];
        if (isSheetAnswer(result)) {
            return <SheetAnswerArea html={result.answerResults[0].myAnswer} />;
        }
        const answerIds = parseAnswerResultsContent(result);
        let index = 0, html = "";
        question.stemContent.split(/\$\$([\s\S]*?)\$\$/g).map(function(text1, i) {
            if (i % 2 === 0) {
                html += text1;
                return
            }
            text1.split(/##([\s\S]*?)##/g).map(function(text2, i) {
    			if (i % 2 == 0) {
                    html += text2;
                    return;
    			}
                const idx = index++;
                const value = answerIds && answerIds[idx] ? answerIds[idx] : " ";
                html += `<span class="mark">${value}</span>`;
    		});
        });
        return (
            <div className="c-area">
                <div className="c-area-punctuate" dangerouslySetInnerHTML={{__html: html}}></div>
            </div>
        );
    }
}

class OpenendAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const answerIds = parseAnswerResultsContent(store.results[question.questionId]);
        const html = clearImgStyle(answerIds.join(''));
        return (
            <div className="c-area">
                <div className="c-area-openend">
                    <RichView html={html} />
                </div>
            </div>
        );
    }
}

class OralAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const result = store.results[question.questionId];
        if (isSheetAnswer(result)) {
            return <SheetAnswerArea html={result.answerResults[0].myAnswer} />;
        }
        const answerIds = parseAnswerResultsContent(result);
        let nodes = [];
        if (answerIds && answerIds.length > 0) {
            answerIds[0].split(/<!--\s+(audio|recorder)\s*:\s*(\S+)\s*-->/).forEach((text, index) => {
                if (index % 3 === 2) {
                    nodes.push(<AudioPlayer key={text} source={text} />)
                }
            });
        }
        return (
            <div className="c-area">
                <div className="c-area-oral">{nodes}</div>
            </div>
        );
    }
}

function parseHandwriteUrl(reply) {
    if (reply && reply.length) {
        const imgs = $(`<div>${reply[0]}</div>`).find('img');
        if (imgs.length > 0) {
            return imgs.attr('src');
        }
    }
    return "";
}

function parseStemImgUrl(stemContent) {
    return $(stemContent).attr('src');
}

class HandwriteAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const answerIds = parseAnswerResultsContent(store.results[question.questionId]);
        let imgUrl = parseHandwriteUrl(answerIds);
        if (!(imgUrl)) {
            imgUrl = parseStemImgUrl(question.stemContent);
        }
        return (
            <div className="c-area">
                <div className="c-area-handwrite">
                    <img src={imgUrl} />
                </div>
            </div>
        );
    }
}

function isSheetAnswer(result) {
    if (result && result.answerResults && result.answerResults.length) {
        return result.answerResults[0].answerSource == 4;
    }
    return false;
}
