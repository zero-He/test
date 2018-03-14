
function convertQues(ques) {
    let data = {};
    for (var key in ques) {
        let que = ques[key];
        data[que.questionId] = que;
        if (que.hasSub) {
            que.subs.forEach(sub => data[sub.questionId] = sub);
        }
    }
    return data;
}

function convertResults(results) {
    let data = {};
    for (var key in results) {
        let result = results[key];
        data[result.questionId] = result;
        if (result.subs && result.subs.length) {
            result.subs.forEach(sub => data[sub.questionId] = sub);
        }
    }
    return data;
}

function collectRightQids(results) {
    let data = [];
    for (let key in results) {
        let result = results[key];
        if (result.totalIsRight) {
            data.push(result.questionId);
        } else if (result.subs && result.subs.length) {
            result.subs.filter(v => v.totalIsRight).forEach(sub => data.push(sub.questionId));
        }
    }
    return data;
}

function convertQdxs(paper) {
    let qids = [];
    let total = 0, index = 0;
    paper.detail.groups.forEach(g => total += g.questions.length);
    paper.detail.groups.forEach(g => {
        g.questions.forEach(sq => {
            index++;
            let group = {
                title: g.groupTitle,
                score: g.score,
                index: index,
                total: total
            };
            let que = {
                qid: sq.questionId,
                index: sq.ord,
                score: sq.score
            };
            sq.index = qids.length;
            if (sq.hasSub) {
                sq.subs.forEach((csq, idx) => {
                    qids.push({
                        group: group,
                        que: que,
                        sub: {
                            qid: csq.questionId,
                            score: csq.score,
                            index: idx + 1,
                            total: sq.subs.length
                        }
                    });
                });
            } else {
                qids.push({
                    group: group,
                    que: que
                });
            }
        });
    });
    return qids;
}

function filterQids(paper, isDiscardFn) {
    let groups = [];
    paper.detail.groups.forEach(g => {
        let sqs = [];
        g.questions.forEach(sq => {
            if (isDiscardFn(sq.questionId)) {
                return;
            }
            if (!sq.hasSub) {
                sqs.push(sq);
                return;
            }
            let subs = sq.subs.filter(v => !isDiscardFn(v.questionId));
            sqs.push({
                ...sq,
                subs
            });
        });
        if (sqs.length > 0) {
            groups.push({
                ...g,
                questions: sqs
            });
        }
    });
    return {
        ...paper,
        detail: {
            groups
        }
    };
}

const CHOICE = 'SingleChoice,MultiChoice,Judgement'.split(',');
const FILLING = 'FillBlank,Punctuate'.split(',');
const OPENEND = 'Openend,Handwrite,Oral'.split(',');
function fillNullReply(question) {
    if (FILLING.indexOf(question.template) >= 0) {
        return question.answers.map(v => "");
    } else if (OPENEND.indexOf(question.template) >= 0) {
        return [""];
    }
    return [];
}

function fillNullReplyIfResult(question, results) {
    const result = results[question.questionId];
    if (result && result.answerResults) {
        return result.answerResults.map(v => v.myAnswer);
    }
    return fillNullReply(question);
}

function convertReplys(paper, ques, results) {
    let replys = {};
    paper.detail.groups.forEach(g => {
        g.questions.forEach(sq => {
            const question = ques[sq.questionId];
            if (!question.hasSub) {
                replys[question.questionId] = fillNullReplyIfResult(question, results);
                return;
            }
            question.subs.forEach(subquest => {
                replys[subquest.questionId] = fillNullReplyIfResult(subquest, results);
            });
        });
    });
    return replys;
}

export default function transformInitData(Csts, mode) {
    let paper = Csts.paper;
    if (mode == 'allque') {
        let rightQids = collectRightQids(Csts.results);
        paper = filterQids(Csts.paper, (qid) => rightQids.indexOf(qid) >= 0);
    }
    const qdxs = convertQdxs(paper);
    const ques = convertQues(Csts.ques);
    const results = convertResults(Csts.results);
    const replys = convertReplys(Csts.paper, ques, results);
    return {
        ...Csts,
        mode: mode,
        paper,
        ques,
        qdxs,
        results,
        replys
    };
}
