var ReactChart = require('../../react/ReactChart');

let option = {
	color : ['#92d050', '#ffcc00', '#cc3300'],
	legend : {
		x : 'left',
		orient : 'vertical',
		selectedMode : false,
		data : ['已完成', '部分完成', '未完成']
	},
	tooltip : {
		formatter : '{b}：{c}人<br>比率：{d}%'
	},
	series : [{
		name : '完成情况',
		type : 'pie',
		radius : '80%',
		label : {
			normal : {
				show : false
			}
		},
		labelLine : {
			normal : {
				show : false
			}
		},
		data : [{
			value : 100,
			name : '已完成'
		}, {
			value : 20,
			name : '部分完成'
		}, {
			value : 5,
			name : '未完成'
		}]
	}]
};

class ReportView extends React.Component {
	render() {
		return <ReactChart style={{height: 250}} option={option} />;
	}
}

let app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);