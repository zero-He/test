var React = require('common/react/react');
var ReactChart = require('common/react/ReactChart');
var ReactPieChart = require('../common/ReactPieChart');
var {toFixed, toRate} = require('../utils/number');
var Dialog = require('dialog');
var Utils = require('utils');
var Section = require('../common/Section');

class AnalyseResult extends React.Component {
    render() {
        const {query, view} = this.props;
        if (view == null) {
			return <div className="m-mask"><div className="con"><i></i><div>数据加载中</div></div></div>;
		} else if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
        return (
            <div className="c-analayse">
                <SummaryInfo id="ach_summary" view={view} />
				<DiligentInfo id="ach_diligent" view={view} />
                <AchieveInfo id="ach_achieve" view={view} />
                <AttendInfo id="ach_attend" view={view} query={query} />
                <HomeworkInfo id="ach_homework" view={view} query={query} />
                <AchieveRank id="ach_achieverank" view={view} />
            </div>
        );
    }
}

class SummaryInfo extends React.Component {
    render() {
        const {view} = this.props;
        const {achieveScore, achieveNum} = view;
        const {lessonNum,  normalNum, usersNum} = view.attendInfo;
		const name = DiligentInfo.achieveLevels[Math.floor(achieveScore / 20)];
        let dear = `敬爱的<span class="fc-green">${view.userName}</span>老师：`;
        let text = `在此期间，您共上课<span class="fc-green">${lessonNum}</span>堂，班级全勤率<span class="fc-green">${toRate(normalNum, usersNum)}%</span>；`;
		text += `班级共取得成就<span class="fc-green">${achieveNum}</span>项，学科勤奋指数为<span class="fc-green">${achieveScore}</span>，`;
		text += `勤奋层级为<span class="fc-green">${name}</span>；`;
		text += `请及时掌握学情，督促学生养成良好的学习习惯，教学轻松更有效。`;
        return (
            <section id={this.props.id} className="c-paragraph">
                <section className="title" dangerouslySetInnerHTML={{__html: dear}}></section>
                <section className="con" dangerouslySetInnerHTML={{__html: text}}></section>
            </section>
        );
    }
}

class HelpLink extends React.Component {
    handleClick = () => {
        let {href} = this.props;
        Dialog.open({
			title : '帮助',
			url : href
		});
    }
    render() {
        return <i className="help" onClick={this.handleClick}></i>;
    }
}

class DiligentInfo extends React.Component {
	static achieveLevels = ['不勤奋', '不够勤奋', '较勤奋', '很勤奋', '非常勤奋', '非常勤奋'];
    buildGaugeOption(value) {
		const idx = Math.floor(value / 20);
		const name = DiligentInfo.achieveLevels[idx];
        return {
			series: [{
				type: 'gauge',
				radius: '90%',
				data: [{
					name: name,
					value: value
				}],
				axisLine: {
					lineStyle: {
						color: [[0.20, '#7bb57b'],[0.80, '#85c6f4'],[1, '#ffac8e']]
					}
				}
			}]
		};
    }
    buildLineOptions(datas) {
        const labels = datas.map(v => v.name.replace(/（\S*）/, ''));
        const values = datas.map(v => v.value);
        return {
			color: ['#76b3a1'],
        	title: {
        		text: '勤奋指数走势',
        		left: 'center',
				textStyle: {
		            fontWeight:'normal'
		        }
        	},
        	tooltip: {
        		trigger: 'item',
        		formatter: '{b}：{c}'
        	},
        	grid: {
        		left: '3%',
        		right: '3%',
        		bottom: '8%',
        		containLabel: true
        	},
        	xAxis: {
        		type: 'category',
				boundaryGap: false,
        		data: labels
        	},
        	yAxis: {
        		type: 'value'
        	},
        	series: [{
        		type: 'line',
				areaStyle: {normal: {}},
        		data: values
        	}]
        };
    }
	render() {
		const {achieveInfo, trends, achieveScore} = this.props.view;
		return (
            <Section id={this.props.id} title="勤奋指数" help={`${Leke.domain.staticServerName}/pages/help-center/diag/diligence-index.html`}>
                <div className="c-layout">
                    <div className="left" style={{width: '40%'}}>
                        <ReactChart className="maps" option={this.buildGaugeOption(achieveScore)} />
                    </div>
                    <div className="right" style={{width: '50%'}}>
                        <ReactChart className="maps" option={this.buildLineOptions(trends)} />
                    </div>
                </div>
				<div className="tips">勤奋指数共有5个层级分别是：[80-100]为非常勤奋，[60-80)为很勤奋，[40-60)为较勤奋，[20-40)为不够勤奋，[0-20)为不勤奋。</div>
			</Section>
		);
	}
}

const achieveSettings = [
    {color: '#cf960b', title: '全勤'},
    {color: '#cfbe0c', title: '提前预习'},
    {color: '#d07a0d', title: '专心听讲'},
    {color: '#0dadcf', title: '勤思好问'},
    {color: '#83cf0c', title: '活跃分子'},
    {color: '#cf550c', title: '手有余香'},
    {color: '#0bbfce', title: '及时改错'},
    {color: '#11cf6f', title: '不磨蹭'},
    {color: '#cfa511', title: '练习达人'},
    {color: '#1090cf', title: '温故知新'}
];

class AchieveInfo extends React.Component {
	render() {
        const {userNum, achieves} = this.props.view;
        achieves.forEach((ach, i) => {
            ach.total = userNum;
            ach.value = ach.userIds.length;
            ach.title = achieveSettings[i].title;
            ach.color = (toRate(ach.value, ach.total) >= 70 ? achieveSettings[i].color : '#aeaeae')
        });
        var Ball = supportCanvas ? AchieveBall : AchieveBallIE8;
		return (
            <Section id={this.props.id} title="班级行为成就" help={`${Leke.domain.staticServerName}/pages/help-center/diag/class-behavior.html`}>
				<div className="con">
                    <div className="achievements">
                    {achieves.map(data => <Ball key={data.title} {...data} />)}
                    </div>
                </div>
			</Section>
		);
	}
}

var supportCanvas = (function() {
    try {
        return !!document.createElement('canvas').getContext;
    } catch(e) {
        return false;
    }
})();


window.requestAnimationFrame = (function () {
	return window.requestAnimationFrame ||
		window.webkitRequestAnimationFrame ||
		window.mozRequestAnimationFrame ||
		function (callback) {
			window.setTimeout(callback, 6000 / 60);
		};
})();

function showAchieveUserNames(names, title) {
    if (!(names && names.length)) {
        Utils.Notice.alert(`还没有学生获取${title}成就`);
        return;
    }
    var tpl = '<div class="names">' + names.map(n => `<span>${n}</span>`).join('') + '</div>';
	Dialog.open({
		size : 'nm',
		title : `${title}成就获得名单`,
		tpl : tpl
	});
}

class AchieveBall extends React.Component {
	constructor(props) {
		super(props);
		this.running = true;
	}
	componentDidMount() {
		const that = this;
		const {value, total, color} = this.props;
        const score = toRate(value, total);
        const rate = (100 - score) / 100;
		const ctx = this.refs.cvs.getContext('2d');
		var step = 1;
		function draw() {
			step ++;
			var width = 80, height = 80, wave = 3, fillHeight = height * rate;
		    var angle = step * Math.PI / 180;
		    var dh = Math.sin(angle) * wave;
		    var dhr = Math.cos(angle) * wave;
			ctx.clearRect(0, 0, width, height);
		    ctx.fillStyle = color;
		    ctx.beginPath();
		    ctx.moveTo(0, fillHeight + dh);
		    ctx.bezierCurveTo(width / 2, fillHeight + dh - wave, width / 2, fillHeight + dhr - wave, width, fillHeight + dhr);
		    ctx.lineTo(width, height);
		    ctx.lineTo(0, height);
		    ctx.lineTo(0, fillHeight + dh);
		    ctx.closePath();
		    ctx.fill();
			if (that.running) {
				requestAnimationFrame(draw);
			}
		}
		draw();
	}
	componentWillUnmount() {
		this.running = false;
	}
	render() {
		const {title, total, value, userNames} = this.props;
        const score = toRate(value, total);
		return (
			<div>
                <div className="c-wavebg" onClick={() => showAchieveUserNames(userNames, title)}>
                    <AchieveTips title={title} total={total} value={value} score={score} />
                    <div className="wv-text">{title}</div>
                    <canvas ref="cvs" height="80" width="80"></canvas>
                </div>
            </div>
		);
	}
}

class AchieveBallIE8 extends React.Component {
    render() {
		const {title, value, total, color, userNames} = this.props;
        const score = toRate(value, total);
		return (
			<div>
                <div className="c-wavebg-ie8" onClick={() => showAchieveUserNames(userNames, title)}>
                    <AchieveTips title={title} total={total} value={value} score={score} />
                    <div className="wv-progress" style={{height: `${score}%`, background: color}}></div>
                    <div className="wv-text">{title}</div>
                </div>
            </div>
		);
    }
}

class AchieveTips extends React.Component {
    render() {
		const {title, value, total, score} = this.props;
        return (
            <div className="pop">
                <span>
                    班级人数：{total}人<br />
                    获得人数：{value}人<br />
                    获得比率：{score}%
                </span>
                <div className="arrow"><em></em><i></i></div>
            </div>
        );
    }
}

class AttendInfo extends React.Component {
	render() {
        const {view, query} = this.props;
        const {lessonNum, usersNum, normalNum, belateNum, earlyNum, exceptNum, absentNum} = view.attendInfo;
        let datas = [
            { value : normalNum, name : '全勤' },
            { value : belateNum, name : '迟到' },
            { value : earlyNum, name : '早退' },
            { value : exceptNum, name : '迟到且早退' },
            { value : absentNum, name : '缺勤' }
        ];
        let color = ['#83cf0b', '#619eed', '#70cfff', '#ffd270', '#ff6d6e'];
        let summary = `班级共<span class="green-txt">${view.userNum}</span>人，`;
		summary += `总课堂数<span class="green-txt">${lessonNum}</span>堂，`;
		summary += `考勤统计<span class="green-txt">${usersNum}</span>人次。`;
		return (
            <Section id={this.props.id} title="班级考勤" help={`${Leke.domain.staticServerName}/pages/help-center/diag/kq-gz-xs.html`}
                detail={`./klass-behavior-attend/${query.klassId}-${query.cycleId}.htm?_newtab=1`}>
				<ReactPieChart datas={datas} summary={summary} color={color} chartTitle="attend" unit="人次" />
			</Section>
		);
	}
}

class HomeworkInfo extends React.Component {
	render() {
        const {view, query} = this.props;
        const {studentTotalNum, classHomeworkNum, submitNum, lateSubmitNum, noSubmitNum, submitPro, lateSubmitPro,
        	noSubmitPro} = view.homeworkCount;
        let datas = [
            { value : submitNum, name : '按时提交', rate : submitPro},
            { value : lateSubmitNum, name : '延后补交', rate : lateSubmitPro},
            { value : noSubmitNum, name : '未提交', rate : noSubmitPro}
        ];
        let summary = `共学生<span class="green-txt">${studentTotalNum}</span>人，共需完成班级作业<span class="green-txt">${classHomeworkNum}</span>份`;
		return (
            <Section id={this.props.id} title="作业提交统计"
            	detail={`./klass-behavior-work/${query.klassId}-${query.cycleId}.htm?_newtab=1`}>
				<ReactPieChart datas={datas} summary={summary} chartTitle="homework" unit="人份" />
			</Section>
		);
	}
}


class AchieveRank extends React.Component {
	static achieveLevels = ['非常勤奋', '很勤奋', '较勤奋', '不够勤奋', '不勤奋', '无数据'];
	toLevel(score) {
		if (score != null) {
			const level = 4 - Math.floor(score / 20);
			return level < 0 ? 0 : level;
		}
		return 5;
	}
	markRankIndex(userRanks) {
		let prev = 10000, index = 0;
		for (var i = 0; i < userRanks.length; i++) {
			let score = userRanks[i].score;
			if (score != null) {
				if (score < prev) {
					index++;
					prev = score;
				}
				userRanks[i].index = index;
			} else {
				userRanks[i].index = '--';
			}
			userRanks[i].level = this.toLevel(score);
		}
	}
	render() {
		const {userRanks} = this.props.view;
		if (!(userRanks && userRanks.length)) {
			return null;
		}
		this.markRankIndex(userRanks);
		let columns = [
			{ title: '排名', width: '15%', field: 'index'},
			{ title: '姓名', width: '35%', field: 'userName'},
			{ title: '勤奋指数', width: '25%', field: 'score'},
			{ title: '获得成就数', width: '25%', field: 'achieveNum'}
		];

		return (
            <Section id={this.props.id} title="学生行为成就排行榜">
				<div className="con">
                    <div className="m-table m-table-center ranklist">
                        <table>
                            <thead>
                                <tr>
                                    <th>层级</th>
                                    <th>排名</th>
                                    <th>姓名</th>
                                    <th>勤奋指数</th>
                                    <th>获得成就数</th>
                                </tr>
                            </thead>
                            <tbody>
        					{AchieveRank.achieveLevels.map((levelName, i) => {
        						const datas = userRanks.filter(v => v.level == i);
                                const labelTd = <td rowSpan={`${datas.length}`} className={`lv-${i + 1}`}>{`${levelName}(${datas.length}人)`}</td>
                                return datas.map((data, j) => {
                                    return (
                                        <tr>
                                            {j === 0 ? labelTd : null}
                                            <td>{data.index}</td>
                                            <td>{data.userName}</td>
                                            <td>{data.score}</td>
                                            <td>{data.achieveNum}</td>
                                        </tr>
                                    );
                                });
        					})}
                            </tbody>
                        </table>
                    </div>
				</div>
			</Section>
		);
	}
}

module.exports = AnalyseResult;
