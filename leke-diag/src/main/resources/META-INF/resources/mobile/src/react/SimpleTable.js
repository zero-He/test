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

class SimpleTable extends React.Component {
	render() {
		let that = this;
		let defVal = this.props.defVal || '';
		let {columns, datas, caption, showHead} = this.props;
		let thead = null;
		if (showHead) {
			thead = <thead><tr>{columns.map(column => <th style={column.width ? {width: column.width} : {}}>{column.title}</th>)}</tr></thead>;
		}
		let rows = datas.map((item, i) => {
			return <tr>{columns.map(column => <td style={column.width ? {width: column.width} : {}}>{fieldValue(column.field, item, i, defVal)}</td>)}</tr>;
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
	columns : React.PropTypes.array,
	datas : React.PropTypes.array,
	showHead: React.PropTypes.bool
};

module.exports = SimpleTable;
