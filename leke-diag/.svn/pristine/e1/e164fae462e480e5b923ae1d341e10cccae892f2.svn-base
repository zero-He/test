const React = require('common/react/react');
const JSON = require('json');
const Utils = require('utils');
const {toFixed, toRate} = require('../utils/number');
const ReactDataGrid = require('./ReactDataGrid');

class KnoGraspFilter extends React.Component {
	constructor(props) {
		super(props);
		this.handleChange = this.handleChange.bind(this);
	}
	handleChange() {
		let refs = this.refs;
		let levels = [1, 2, 3].filter(v => refs[`zw${v}`].checked);
		if (this.props.onChange) {
			this.props.onChange(levels);
		}
	}
	render() {
		return (
			<ul>
				<li><input ref="zw1" type="checkbox" id="zw_1" defaultChecked={true} onChange={this.handleChange} /><label htmlFor="zw_1">已掌握</label></li>
				<li><input ref="zw2" type="checkbox" id="zw_2" defaultChecked={true} onChange={this.handleChange} /><label htmlFor="zw_2">掌握不牢</label></li>
				<li><input ref="zw3" type="checkbox" id="zw_3" defaultChecked={true} onChange={this.handleChange} /><label htmlFor="zw_3">未掌握</label></li>
			</ul>
		);
	}
}

function toFixedValue(value) {
    return toFixed(value);
}
function toFixedRate(value) {
    return toFixed(value) + '%';
}

const VIEW_ERR_QUE_URL = `${Leke.domain.wrongtopicServerName}/auth/student/wrong/wrongList.htm`;
const EXERCISE_QUE_URL = `${Leke.domain.homeworkServerName}/auth/student/exercise/doWork2.htm`;

const TIPS = {
	1: '得分率在85%以上',
	2: '班级得分率在[50%~85%)',
	3: '得分率在50%以下'
};

var KnoGraspTableColumnDefs = {
	name: { title: '知识点名称', field: 'name', width: '25%' },
	totalNum: { title: '涉题数', field: 'totalNum', render: toFixedValue, onSort: true },
	rightNum: { title: '正确题数', field: 'rightNum', render: toFixedValue },
	totalScore: { title: '分值', field: 'totalScore', render: toFixedValue, onSort: true },
	selfScore: { title: '得分', field: 'selfScore', render: toFixedValue },
	selfRate: { title: '得分率', field: 'selfRate', onSort: true, render: toFixedRate },
	klassRate: { title: '班级得分率', field: 'classRate', render: toFixedRate, onSort: true },
	level: { title: '掌握程度', field: 'level', render: (value, item, index) => {
		return <i className={`ismastered ismastered-${value}`} title={TIPS[value]}></i>;
	} },
	wrong: { title: '查看错题', field: 'id', render: (value, item, index) => <a href={`${VIEW_ERR_QUE_URL}?knowledgeId=${value}`} className="s-c-turquoise" target="_blank">去查看</a> },
	exercise: { title: '提升训练', field: 'id', render: (value, item, index) => <a href={`${EXERCISE_QUE_URL}?knoId=${value}`} className="s-c-turquoise" target="_blank">去训练</a> }
};

class KnoGraspTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			levels: [1,2,3],
			knoIds: []
		};
	}
	handleChange = (levels) => {
		let knoIds = this.state.knoIds;
		if (knoIds.length) {
			let knoRates = this.filterLevels(this.props.knoRates, levels);
			let newKnoIds = knoRates.map(v => v.id).filter(v => knoIds.indexOf(v) >= 0);
			this.setState({levels: levels, knoIds: newKnoIds});
		} else {
			this.setState({levels: levels});
		}
	}
	handleCheckOne2 = (e) => {
		let knoIds = this.state.knoIds;
		let knoId = parseInt(e.target.value);
		if (e.target.checked) {
			knoIds.push(knoId);
		} else {
			let idx = knoIds.indexOf(knoId);
			knoIds.splice(idx, 1);
		}
		this.setState({knoIds: knoIds});
	}
    handleCheckOne = (knoId) => {
        let {knoIds} = this.state;
        let idx = knoIds.indexOf(knoId);
        if (idx >= 0) {
            knoIds.splice(idx, 1);
        } else {
            knoIds.push(knoId);
        }
        this.setState({knoIds});
    }
	handleCheckAll = (e) => {
		if (e.target.checked) {
			let knoIds = this.filterLevels(this.props.knoRates, this.state.levels).map(v => v.id);
			this.setState({knoIds: knoIds});
		} else {
			this.setState({knoIds: []});
		}
	}
	handleClickWork = (e) => {
		let knoIds = this.state.knoIds;
		if (knoIds.length > 0 && knoIds.length <= 30) {
			let knos = this.props.knoRates.filter(v => knoIds.indexOf(v.id) >= 0).map(v => {
				return {knoId: v.id, diff: 6 - v.level};
			});
			let {classId, subjectId} = this.props;
			let data = {
				homeworkQuestionRule: knos,
				classId: classId,
				subjectId: subjectId
			};
			let prefix = Leke.ctx + '/auth/teacher/quick/assign.htm?data=';
			e.target.href = prefix + JSON.stringify(data);
		} else {
			if (knoIds.length > 30) {
				Utils.Notice.alert('抱歉，单份提升作业勾选知识点数不能超过30个，建议分成多份作业布置');
			}
			e.target.href = 'javascript:void(0);';
			e.stopPropagation();
			e.preventDefault();
		}
	}
	filterLevels = (knoRates, levels) => {
		return knoRates.filter(item => levels.indexOf(item.level) >= 0);
	}
	render() {
		let that = this;
		let {levels, knoIds} = this.state;
		let {knoRates, columns, increase} = this.props;
		if (knoRates.length == 0) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		columns = columns.split(',').map(v => KnoGraspTableColumnDefs[v]);
        if (increase) {
            knoRates.forEach(v => v.checked = knoIds.indexOf(v.id) >= 0);
            columns.unshift({
                title: <input type="checkbox" onClick={that.handleCheckAll} />,
                field: 'id',
                render: (value, item, index) => {
                    return <input type="checkbox" key={value} checked={item.checked} onClick={() => that.handleCheckOne(value)} />;
                }
            })
        }
		knoRates = this.filterLevels(knoRates, levels);
		return (
			<div className="m-knoanaly">
				<div className="clear">
					{increase ? <span className="tips">勾选知识点后，点击”布置提升作业“按钮可快速布置针对提升作业</span> : null}
					<KnoGraspFilter onChange={this.handleChange} />
				</div>
				<ReactDataGrid columns={columns} datas={knoRates} />
				{increase ? <a target="_blank" onClick={that.handleClickWork} className={"u-btn u-btn-auto u-btn-bg-" + (knoIds.length ? 'turquoise' : 'gray')}>布置提升作业</a> : null}
			</div>
		);
	}
}

module.exports = KnoGraspTable;
