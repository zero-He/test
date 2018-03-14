
export function isAnswerReply(que, reply) {
    if (reply && reply.length) {
        if (que.template === 'Punctuate') {
            return reply.filter(v => v != "").length > 0;
        } else {
            return reply.filter(v => v == "").length == 0;
        }
    }
    return false;
}

export function handleJudgeState(store, qid) {
    const que = store.ques[qid];
    if (!que.hasSub) {
        const reply = store.replys[qid];
        return isAnswerReply(que, reply) ? 'finished' : 'unfinish';
    }
    const unques = store.ques[qid].subs.filter(sub => {
        const reply = store.replys[sub.questionId];
        return !isAnswerReply(sub, reply);
    });
    return unques.length == 0 ? 'finished' : 'unfinish';
}

export function stateToAnswerJson(state) {
    const groups = state.basic.paper.detail.groups;
    let answerInfos = [], unfinish = 0;
    groups.forEach(group => {
        group.questions.forEach(sq => {
            const que = state.basic.ques[sq.questionId];
            if (!que.hasSub) {
                const reply = state.replys[que.questionId];
                if (!isAnswerReply(que, reply)) {
                    unfinish++;
                }
                answerInfos.push({
                    questionId: que.questionId,
                    answerContent: JSON.stringify(reply)
                });
                return;
            }
            let subfinished = true;
            const subs = que.subs.map(sub => {
                const reply = state.replys[sub.questionId];
                if (!isAnswerReply(sub, reply)) {
                    subfinished = false;
                }
                return {
                    questionId: sub.questionId,
                    answerContent: JSON.stringify(reply)
                };
            });
            if (!subfinished) {
                unfinish++;
            }
            answerInfos.push({
                questionId: que.questionId,
                subs: subs
            });
        });
    });
    const answerJson = JSON.stringify(answerInfos)
    return {answerJson, unfinish};
}
