const React = require('common/react/react');

const SORT_ASC = 'm-sort-asc', SORT_DESC = 'm-sort-desc', SORT_DEF = 'm-sort-default';

function sortByField(datas, field, isAsc) {
	datas.sort(function(a, b) {
		a = a[field], b = b[field];
		var ret = isAsc ? 1 : -1;
		if (a > b) {
			return ret;
		}
		if (a < b) {
			return -ret;
		}
		return 0;
	});
}

function convertColumns(columns) {
	return columns.map(col => {
		var newCol = {
			title: col.title,
			field: col.field,
			width: col.width,
			render: function(row, index) {
				const {field, render, fmt} = col;
				var value = row[field];
				if (render && typeof render === 'function') {
					value = render(value, row, index);
				}
				if (value === undefined || value === null || value === '') {
					return '';
				}
				if (fmt && typeof fmt == 'string') {
					var arr = fmt.match(/(\w+)`(.*)`/);
					if (arr[1] === 'date') {
						value = LekeDate.of(value).format(arr[2]);
					} else if (arr[1] == 'number') {
						value = Utils.Number.format(value, arr[2]);
					}
				}
				return value;
			}
		};
		if (col.onSort) {
			newCol.sort = SORT_DEF;
			newCol.onSort = function(datas, isAsc) {
				if (col.onSort === true) {
					sortByField(datas, col.field, isAsc);
				} else if (typeof col.onSort === 'function') {
					col.onSort(datas, isAsc);
				} else {
					sortByField(datas, col.onSort, isAsc);
				}
			}
		}
		return newCol;
	});
}

class DataGrid extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            columns: convertColumns(props.columns),
            datas: props.datas,
			total: props.total,
            sortCol: null
        };
    }
    componentWillReceiveProps(nextProps) {
        let {datas, total} = nextProps;
        let {sortCol} = this.state;
        if (sortCol) {
            sortCol.onSort(datas, sortCol.sort === SORT_ASC);
        }
        this.setState({datas, total});
    }
    handleOnSort = (index) => {
        let {columns, datas} = this.state;
		columns.forEach((c, i) => {
			if (c.sort && i != index) {
				c.sort = SORT_DEF;
			}
		})
		const sortCol = columns[index];
		if (sortCol.sort == SORT_ASC) {
			sortCol.sort = SORT_DESC;
		} else {
			sortCol.sort = SORT_ASC;
		}
		sortCol.onSort(datas, sortCol.sort === SORT_ASC);
        this.setState({columns, datas, sortCol});
    }
    render() {
        let {columns, datas, total} = this.state;
        const header = columns.map((col, index) => {
            let style = {};
            if (col.width) {
                style.width = col.width;
            }
            if (col.sort) {
                return <th key={index} style={style} className='m-sort-leke' onClick={() => this.handleOnSort(index)}>{col.title}<i className={col.sort}></i></th>;
            }
            return <th key={index} style={style}>{col.title}</th>;
        });
		if (total) {
			datas = datas.concat(total);
		}
        const tbody = datas.map((data, index) => {
            var rows = columns.map((col, fieldIdx) => {
                 let style = {};
                 if (col.width) {
                     style.width = col.width;
                 }
                const value = col.render(data, index);
                return <td key={fieldIdx} style={style}>{col.render(data, index)}</td>;
            });
            return <tr key={index}>{rows}</tr>;
        });
        return (
            <div className="m-table m-table-center">
            	<table>
            		<thead><tr>{header}</tr></thead>
            		<tbody>{tbody}</tbody>
            	</table>
            </div>
        );
    }
}

module.exports = DataGrid;
