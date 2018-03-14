import React, {Component, PropTypes} from 'react';
import ReactCSSTransitionGroup from 'react/lib/ReactCSSTransitionGroup';
import {download} from '../../common/file';
import {FrameSet, FrameTop, FrameBottom} from '../../common/frame';
import {clearImgStyle} from '../../common/htmlparser';
import {fmtScore} from '../../common/fmt';
import Player from '../../common/player';
import Recorder from '../../common/recorder';
import richtext from '../../common/richtext';
import RichView from '../../common/richview';
import handboard from '../../common/handboard';

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
                    <span className="title-score">(总分<i className="title-total">{score}</i>分)</span>
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
        const {valid, score, selfCheck} = this.props.status;
        const nodes = [];
        if (valid != null) {
            const marks = String(score).split('').map(v => v === '.' ? 'point' : v);
            nodes.push(<span key="valid" className={`state-${valid}`}></span>);
            {marks.map((mark, i) => nodes.push(<span key={i} className={`state-${mark}`}></span>))}
            nodes.push(<span key="score" className="state-score"></span>);
        }
        return <span className="c-score-box">{nodes}</span>;
    }
}

class Judgement extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? JudgementAnswerArea : JudgementViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class SingleChoice extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? SingleChoiceAnswerArea : SingleChoiceViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class MultiChoice extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? MultiChoiceAnswerArea : MultiChoiceViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class FillBlank extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? FillBlankAnswerArea : FillBlankViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <FillBlankQueStem theme={theme} actions={actions} question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Punctuate extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? PunctuateAnswerArea : PunctuateViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Openend extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? OpenendAnswerArea : OpenendViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem theme={theme} question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Oral extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? OralAnswerArea : OralViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueStem theme={theme} question={question} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
            </div>
        );
    }
}

class Handwrite extends Component {
    render() {
        let {store, actions, theme, que, question} = this.props;
        const status = parseAnswerResultStatus(store, question.questionId);
        let QueArea = status.editable ? HandwriteAnswerArea : HandwriteViewArea;
        return (
            <div className="c-que">
                <QueIndex que={que} status={status} />
                <QueArea theme={theme} store={store} actions={actions} question={question} />
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
                audios.push(<Player key={text} source={text} />);
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
    const result = store.results[qid];
    let editable = true, valid = null, score = null, selfCheck = false;
    if (result) {
        score = result.totalResultScore;
        if (result.totalIsRight == null) {
            valid = null;
        } else if (result.totalIsRight == true) {
            valid = 'right';
            editable = false;
        } else {
            valid = result.totalScoreRate == 0 ? 'wrong' : 'half';
        }
    }
    if (store && store.workInfo) {
        selfCheck = store.workInfo.selfCheck;
    }
    return {editable, valid, score, selfCheck};
}

const marks = ['对', '错'];
class JudgementAnswerArea extends Component {
    render() {
        const {theme, actions, question, store} = this.props;
        const qid = question.questionId;
        const reply = store.replys[qid] || [];
        const options = question.answers.map((answer, i) => {
            const answerId = `${answer.answerId}`;
            const selected = reply.indexOf(answerId) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`} onClick={() => actions.onAnswerJudgement(qid, answerId)}>{marks[i]}</div>
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

class JudgementViewArea extends Component {
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
        const qid = question.questionId;
        const reply = store.replys[qid] || [];
        const options = question.answers.map((answer, i) => {
            const answerId = `${answer.answerId}`;
            const selected = reply.indexOf(answerId) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`} onClick={() => actions.onAnswerSingleChoice(qid, answerId)}>{String.fromCharCode(65 + i)}</div>
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

class SingleChoiceViewArea extends Component {
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
        const qid = question.questionId;
        const reply = store.replys[qid] || [];
        const options = question.answers.map((answer, i) => {
            const answerId = `${answer.answerId}`;
            const selected = reply.indexOf(answerId) >= 0;
            return (
                <li key={answer.answerId} className="option">
                    <div className={`option-mark option-mark-${selected}`} onClick={() => actions.onAnswerMultiChoice(qid, answerId)}>{String.fromCharCode(65 + i)}</div>
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

class MultiChoiceViewArea extends Component {
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
        return (
            <div className="c-area">
                <div className="c-que-stem" dangerouslySetInnerHTML={{__html: text}}></div>
            </div>
        );
    }
}

class FillBlankAnswerArea extends Component {
    handleInput = (index, value) => {
        let {actions, question} = this.props;
        richtext(value, {
            canEnter: false,
            subjective: question.subjective
        }).done((text) => {
            actions.onAnswerFillBlank(question.questionId, index, text);
        });
    }
    render() {
        const that = this;
        const {theme, actions, question, store} = this.props;
        const qid = question.questionId;
        const reply = store.replys[qid] || question.answers.map(a => "");
        return (
            <div className="c-area">
                <div className="c-area-fillblank">
                {question.answers.map((answer, index) => {
                    let html = index < reply.length ? reply[index] : '';
                    return (
                        <div key={index} className="fill">
                            <div className="fill-index">[{answer.ord + 1}]</div>
                            <div className="fill-text" onClick={that.handleInput.bind(that, index, html)}>
                                {html ? <RichView html={html} /> : <div className="fill-text-placeholder">请点击输入答案</div>}
                            </div>
                        </div>
                    );
                })}
                </div>
            </div>
        );
    }
}

class FillBlankViewArea extends Component {
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
    shouldComponentUpdate(nextProps, nextState) {
        return false;
    }
    componentDidMount() {
        const {actions, question} = this.props;
        $(this.refs.answer).find('.mark').each(function() {
            $(this).click(function() {
                let idx = $(this).data('idx');
                let val = $(this).text();
                $(this).text(val == '/' ? ' ' : '/');
                actions.onAnswerPunctuate(question.questionId, idx, val == '/' ? '' : '/');
            });
        });
    }
    render() {
        const {theme, actions, question, store} = this.props;
        const reply = store.replys[question.questionId];
        let index = 0, html = '';
        question.stemContent.split(/\$\$([\s\S]*?)\$\$/g).map(function(text1, i) {
            if (i % 2 === 0) {
                html += text1;
                return;
            }
            text1.split(/##([\s\S]*?)##/g).map(function(text2, i) {
    			if (i % 2 == 0) {
                    html += text2;
                    return;
    			}
                const idx = index++;
                const value = reply ? reply[idx] : " ";
                html += `<span class="mark" data-idx="${idx}">${value}</span>`;
    		});
        });
        return (
            <div className="c-area">
                <div ref="answer" className="c-area-punctuate" dangerouslySetInnerHTML={{__html: html}}></div>
            </div>
        );
    }
}

class PunctuateViewArea extends Component {
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
    handleInput = (value) => {
        let {actions, question} = this.props;
        richtext(value, {
            canEnter: true,
            subjective: true
        }).done((html) => {
            actions.onAnswerOpenend(question.questionId, html);
        });
    }
    render() {
        const {theme, actions, question, store} = this.props;
        const qid = question.questionId;
        const reply = store.replys[qid] || [""];
        const html = clearImgStyle(reply.length ? reply[0] : '');
        return (
            <div className="c-area">
                <div className="c-area-openend" onClick={() => this.handleInput(html)}>
                    {html ? <RichView html={html} /> : <div className="placeholder">请点击输入答案</div>}
                </div>
            </div>
        );
    }
}

class OpenendViewArea extends Component {
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

function parseOralUrl(reply) {
    if (reply && reply.length) {
        const matches = reply[0].match(/<!--\s+(audio|recorder)\s*:\s*(\S+)\s*-->/);
        if (matches && matches.length >= 3) {
            return matches[2];
        }
    }
    return "";
}

class OralAnswerArea extends Component {
    handleAnswerOral = (url) => {
        const {actions, question} = this.props;
        const html = url ? `<!-- recorder:${url} -->` : "";
        actions.onAnswerOral(question.questionId, html);
    }
    render() {
        const {theme, actions, question, store} = this.props;
        const qid = question.questionId;
        const reply = store.replys[qid] || [""];
        let fileUrl = parseOralUrl(reply);
        return (
            <div className="c-area">
                <div className="c-area-oral">
                    <Recorder key={qid} fileUrl={fileUrl} onAnswerOral={this.handleAnswerOral} />
                </div>
            </div>
        );
    }
}

class OralViewArea extends Component {
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
    return $(`<div>${stemContent}</div>`).find('img').attr('src');
}

class HandwriteAnswerArea extends Component {
    handleHandboard(imgUrl) {
        let {actions, question} = this.props;
        handboard(imgUrl).done((url) => {
            const html = `<img src="${url}" />`;
            actions.onAnswerHandwrite(question.questionId, html);
        });
    }
    render() {
        const {theme, actions, question, store} = this.props;
        const qid = question.questionId;
        const reply = store.replys[qid] || [""];
        let imgUrl = parseHandwriteUrl(reply);
        if (!(imgUrl)) {
            imgUrl = parseStemImgUrl(question.stemContent);
        }
        return (
            <div className="c-area">
                <div className="c-area-handwrite">
                    <span className="editor" onClick={() => this.handleHandboard(imgUrl)}></span>
                    <img src={imgUrl} />
                </div>
            </div>
        );
    }
}

class HandwriteViewArea extends Component {
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
