
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


export default function transformInitData(Csts) {
    const ques = convertQues(Csts.ques);
    const qdxs = convertQdxs(Csts.paper);
    const results = convertResults(Csts.results);
    return {
        ...Csts,
        ques,
        qdxs,
        results
    };
}
