var {toFixed, toRate} = require('../../../utils/number');

class Star extends React.Component {
	render() {
		let score = this.props.score;
		let percent = toRate(score, 5);
		return <div className="stars"><em><i style={{width: `${percent}%`}}></i></em></div>;
	}
}

class EvalItem extends React.Component {
	render() {
		let {label, score} = this.props;
		return (
			<div className="sum sum-item">
				<span>{label}：</span>
				<Star score={score} />
				<div><span className="fc-orange">{score}</span></div>
			</div>
		);
	}
}

class EvalInfo extends React.Component {
	render() {
		let {title, evalInfo} = this.props;
		let userCount = 0, score1 = 0, score2 = 0, score3 = 0, score = 0, rate = 0;
		if (evalInfo != null) {
			userCount = evalInfo.userCount;
			score1 = evalInfo.professionalScore;
			score2 = evalInfo.rhythmScore;
			score3 = evalInfo.interactionScore;
			score = toFixed((score1 + score2 + score3) / 3, 1);
			rate = toRate(evalInfo.goodCount, userCount, 1);
		}
		return (
			<div className="judge-item">
                <div className="inner-title">{title}</div>
                <div className="sum">
                	<Star score={score} />
                    <div><span className="fc-orange">{score}</span>分</div>
                    <div>共<span>{userCount}</span>人</div>
                    <div>好评率：<span className="fc-orange">{rate}%</span></div>
                </div>
                <EvalItem label="教学效果" score={score1} />
                <EvalItem label="教学态度" score={score2} />
                <EvalItem label="课堂互动" score={score3} />
            </div>
		);
	}
}

class FlowerInfo extends React.Component {
	render() {
		let {flower} = this.props;
		return (
			<div className="judge-item">
				<div className="inner-title">收到鲜花</div>
				<div className="flowers">{flower}</div>
			</div>
		);
	}
}

// 课堂评价
class LessonEvaluate extends React.Component {
	renderFlower(flower) {
		return (
			<div className="item f-ml20">
				<h3 className="title">收到鲜花</h3>
				<div className="evaluatecount">{flower}</div>
			</div>
		);
	}
	render() {
		let {flower, lessonEval, teacherEval} = this.props;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">课堂评价</div>
				<EvalInfo title="本课堂评价" evalInfo={lessonEval} />
				<EvalInfo title="累计评价" evalInfo={teacherEval} />
				<FlowerInfo flower={flower} />
			</section>
		);
	}
}

module.exports = LessonEvaluate;