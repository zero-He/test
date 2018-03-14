import React, {Component, PropTypes} from 'react';

function fieldValue(field, data, index, defVal) {
	var type = (typeof field);
	var value;
	if (type === 'string') {
		value = data[field];
	} else if (type === 'function') {
		value = field(data, index);
	} else {
		value = '';
	}
	if (value == null || value == undefined) {
		return defVal ? defVal : '';
	}
	return value;
}

export default class SimpleTable extends Component {
	render() {
		let that = this;
		let defVal = this.props.defVal || '';
		let {columns, datas, caption, showHead} = this.props;
		let thead = null;
		if (showHead) {
			thead = <thead><tr>{columns.map((column, j) => <th key={j} style={column.width ? {width: column.width} : {}}>{column.title}</th>)}</tr></thead>;
		}
		let rows = datas.map((item, i) => {
			return <tr key={i}>{columns.map((column, j) => <td key={j} style={column.width ? {width: column.width} : {}}>{fieldValue(column.field, item, i, defVal)}</td>)}</tr>;
		});
		return (
			<table>
				{caption}
				{thead}
				<tbody>{rows}</tbody>
			</table>
		);
	}
}

SimpleTable.propTypes = {
	columns : PropTypes.array,
	datas : PropTypes.array,
	showHead: PropTypes.bool
};
