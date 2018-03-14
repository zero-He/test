import React, {Component, PropTypes} from 'react';
import ReactChart from '../../common/ReactChart';
import ReactPieChart from '../../common/ReactPieChart';
import Anchor from '../../common/Anchor';
import {toFixed, toRate} from '../../utils/number';
import SimpleTable from '../../common/SimpleTable';

let anchor_items = [
	{title: '回到顶部', link: 'ach_summary'},
	{title: '勤奋指数', link: 'ach_diligent'},
	{title: '班级行为成就', link: 'ach_achieve'},
	{title: '班级考勤', link: 'ach_attend'},
	{title: '作业完成态度', link: 'ach_workfinish'},
	{title: '学生行为成就排行榜', link: 'ach_achieverank'}
];

export default class AnalyseResult extends Component {
    render() {
        const {store, actions} = this.props;
        const {view, query} = store.result;
        return (
            <section className="c-detail">
                <SummaryInfo id="ach_summary" view={view} actions={actions} userName={store.control.userName} />
				<DiligentInfo id="ach_diligent" view={view} actions={actions} />
				<AchieveInfo id="ach_achieve" view={view} actions={actions} />
				<AttendInfo id="ach_attend" view={view} actions={actions} query={query} />
				<HomeworkInfo id="ach_workfinish" view={view} actions={actions} query={query} />
				<AchieveRank id="ach_achieverank" view={view} actions={actions} />
				<Anchor items={anchor_items} />
            </section>
        );
    }
}

class HelpLink extends React.Component {
    render() {
		const {href} = this.props;
        return (
			<a href={href}>
				<i className="c-help"></i>
			</a>
		);
    }
}

class SummaryInfo extends Component {
    render() {
        const {view, userName} = this.props;
        const {achieveScore, achieveNum} = view;
        const {lessonNum, usersNum, normalNum} = view.attendInfo;
		const name = DiligentInfo.achieveLevels[Math.floor(achieveScore / 20)];
        let dear = `敬爱的<span class="fc-green">${userName}</span>老师：`;
        let text = `在此期间，您共上课<span class="fc-green">${lessonNum}</span>堂，班级全勤率<span class="fc-green">${toRate(normalNum, usersNum)}%</span>；`;
		text += `班级共取得成就<span class="fc-green">${achieveNum}</span>项，学科勤奋指数为<span class="fc-green">${achieveScore}</span>，`;
		text += `勤奋层级为<span class="fc-green">${name}</span>；`;
		text += `请及时掌握学情，督促学生养成良好的学习习惯，教学轻松更有效。`;
        return (
            <section id={this.props.id} className="ana-module">
                <div className="texts">
                    <section dangerouslySetInnerHTML={{__html: dear}}></section>
                    <section className="txt-idt" dangerouslySetInnerHTML={{__html: text}}></section>
                </div>
            </section>
        );
    }
}

class DiligentInfo extends Component {
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
        		left: '5%',
        		right: '6%',
        		bottom: '5%',
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
			<section id={this.props.id} className="ana-module">
				<div className="title">勤奋指数<HelpLink href={`${Leke.domain.staticServerName}/pages/help-center/diag/diligence-index.html?_newtab=1`} /></div>
                <ReactChart className="maps" option={this.buildGaugeOption(achieveScore)} />
				<div className="tips">勤奋指数共有5个层级分别是：[80-100]为非常勤奋，[60-80]为很勤奋，[40-60]为较勤奋，[20-40]为不够勤奋，[0-20]为不勤奋。</div>
                <ReactChart className="maps" option={this.buildLineOptions(trends)} />
			</section>
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

class AchieveInfo extends Component {
	render() {
        const {userNum, achieves} = this.props.view;
        achieves.forEach((ach, i) => {
            ach.total = userNum;
            ach.value = ach.userIds.length;
            ach.title = achieveSettings[i].title;
            ach.color = (toRate(ach.value, ach.total) >= 70 ? achieveSettings[i].color : '#aeaeae')
        });
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">班级行为成就<HelpLink href={`${Leke.domain.staticServerName}/pages/help-center/diag/class-behavior.html?_newtab=1`} /></div>
				<div className="achievements">
				{achieves.map(data => <AchieveBall key={data.title} {...data} />)}
				</div>
			</section>
		);
	}
}

class AchieveBall extends Component {
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
			var width = 55, height = 55, wave = 2, fillHeight = height * rate;
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
		const {title} = this.props;
		return (
			<div className="c-wavebg">
				<div className="wv-text">{title}</div>
				<canvas ref="cvs" height="55" width="55"></canvas>
			</div>
		);
	}
}

class AttendInfo extends Component {
	render() {
		const {query, view} = this.props;
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
			<section id={this.props.id} className="ana-module">
				<div className="title">
					<span>班级考勤</span>
					<a href={`./klass-behavior-attend/${query.klassId}-${query.cycleId}.htm?_newtab=1`} className="fc-green m-seedetail">详情 <i></i></a>
					<HelpLink href={`${Leke.domain.staticServerName}/pages/help-center/diag/kq-gz-xs.html?_newtab=1`} />
				</div>
				<ReactPieChart datas={datas} summary={summary} color={color} unit="人次" />
			</section>
		);
	}
}

class HomeworkInfo extends Component {
	render() {
		const {view, query} = this.props;
		const {studentTotalNum, classHomeworkNum, submitNum, lateSubmitNum, noSubmitNum, submitPro, lateSubmitPro,
        	noSubmitPro} = view.homeworkCount;
        let datas = [
            { value : submitNum, name : '按时提交', rate : submitPro},
            { value : lateSubmitNum, name : '延后补交', rate : lateSubmitPro},
            { value : noSubmitNum, name : '未提交', rate : noSubmitPro}
        ];
        let summary = `班级共<span class="green-txt">${studentTotalNum}</span>人，共布置作业<span class="green-txt">${classHomeworkNum}</span>份。`;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">
					<span>作业提交统计</span>
				</div>
				<ReactPieChart datas={datas} summary={summary} unit="人份" />
			</section>
		);
	}
}

class AchieveRank extends Component {
	static achieveLevels = ['非常勤奋', '很勤奋', '较勤奋', '不够勤奋', '不勤奋'];
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
			<section id={this.props.id} className="ana-module">
				<div className="title">学生行为成就排行榜</div>
				<div className="c-table c-table-fixed">
					<SimpleTable key="head" columns={columns} datas={[]} showHead={true} />
					{AchieveRank.achieveLevels.map((levelName, i) => {
						let datas = userRanks.filter(v => v.level == i);
						var caption = <caption key={'t'+i} className={`r-${i + 1}`}>{`${levelName}(${datas.length}人)`}</caption>;
						return <SimpleTable key={i} caption={caption} columns={columns} datas={datas} defVal="--" />;
					})}
				</div>
			</section>
		);
	}
}
