define(function(require, exports, module){
var React = require('common/react/react');

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
		let {columns, datas} = this.props;
		let rows = datas.map((item, i) => {
			return <tr>{columns.map(column => <td>{fieldValue(column.field, item, i, defVal)}</td>)}</tr>;
		});
		return (
			<div className="m-table m-table-center">
				<table>
					<thead>
						<tr>
							{columns.map(column => <th style={column.width ? {width: column.width} : {}}>{column.title}</th>)}
						</tr>
					</thead>
					<tbody>{rows}</tbody>
				</table>
			</div>
		);
	}
}

module.exports = SimpleTable;
});
