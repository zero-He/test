var {toFixed, toRate} = require('../../../utils/number');
var ReactPieChart = require('../../../react/ReactPieChart');

class AttendStatInfo extends React.Component {
	render() {
		var attend = this.props.view.attendStatInfo;
        const {lessonNum, realNum, normalNum, belateNum, earlyNum, exceptNum, absentNum} = attend;
        let datas = [
            { value : normalNum, name : '全勤' },
            { value : belateNum, name : '迟到' },
            { value : earlyNum, name : '早退' },
            { value : exceptNum, name : '迟到且早退' },
            { value : absentNum, name : '缺勤' }
        ];
        let color = ['#83cf0b', '#619eed', '#70cfff', '#ffd270', '#ff6d6e'];
        let summary = `应上课<span class="green-txt">${lessonNum}</span>堂，`;
		summary += `实上<span class="green-txt">${realNum}</span>堂，`;
		summary += `上课率<span class="green-txt">${toRate(realNum, lessonNum)}%</span>。`;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">考勤</div>
				<ReactPieChart datas={datas} summary={summary} color={color} unit="堂" />
			</section>
		);
	}
}

module.exports = AttendStatInfo;
