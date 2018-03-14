import LekeDate from '../utils/date';
import LekeNumber from '../utils/number';

const SORT_ASC = 'm-sort-asc', SORT_DESC = 'm-sort-desc', SORT_DEF = 'm-sort-default';

function sortByField(datas, field, isAsc) {
	console.log(isAsc)
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
	return columns.map((col, i) => {
		var newCol = {
			colidx: i,
			title: col.title,
			field: col.field,
			width: col.width,
			fixed: col.fixed,
			render: function(row, index) {
				const {field, render, fmt} = col;
				var value = row[field];
				if (render && $.isFunction(render)) {
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
				} else if ($.isFunction(col.onSort)) {
					col.onSort(datas, isAsc);
				} else {
					sortByField(datas, col.onSort, isAsc);
				}
			}
		}
		return newCol;
	});
}

function renderTable(datas, columns) {
	const header = columns.map((col, i) => {
		const width = col.width ? `style="width: ${col.width}"` : '';
		const cls = col.sort ? `class="m-sort-leke"` : '';
		const icon = col.sort ? `<i class="${col.sort}"></i>` : '';
		return `<th data-colidx="${col.colidx}" ${width} ${cls}>${col.title}${icon}</th>`;
	}).join('');
	const tbody = datas.map((data, index) => {
		var rows = columns.map(col => {
			const width = col.width ? `style="width: ${col.width}"` : '';
			const value = col.render(data, index);
			return `<td>${value}</td>`;
		}).join('');
		return `<tr>${rows}</tr>`;
	}).join('');
	var html = `
		<table>
			<thead>
				<tr>${header}</tr>
			</thead>
			<tbody>
				${tbody}
			</tbody>
		</table>
	`;
	return html;
}

var StaticDataGrid = function(el, opts) {
	this.$el = $(el);
	this.opts = opts;
	this.datas = opts.datas;
	this.columns = convertColumns(opts.columns);
	this.init();
}

StaticDataGrid.prototype.init = function() {
	const that = this;
	const {$el, opts, datas, columns} = this;
	let html;
	if (opts.fixed === true) {
		let fixedPer = opts.fixedPer || 35;
		html = `
		<div class="c-table c-table-fixed">
			<div class="fixed-table" style="width: ${fixedPer}%;">
			</div>
			<div class="moved-table" style="width: ${100-fixedPer}%;">
			</div>
		</div>
		`;
	} else {
		let tableHtml = renderTable(datas, columns);
		html = `
		<div class="c-table c-table-fixed">
		</div>
		`;
	}
	if (datas.length == 0) {
		html += `<div class="m-tips"><span class="img"></span><span class="msg">对不起，没有您要查询的数据</span></div>`;
	}
	$el.html(html);
	this.render();
}

StaticDataGrid.prototype.render = function() {
	const that = this;
	const {$el, opts, datas, columns} = this;
	if (opts.fixed === true) {
		let fixedHtml = renderTable(datas, columns.filter(c => c.fixed === true));
		let movedHtml = renderTable(datas, columns.filter(c => c.fixed !== true));
		$el.find('.fixed-table').html(fixedHtml);
		$el.find('.moved-table').html(movedHtml);
	} else {
		let tableHtml = renderTable(datas, columns);
		$el.find('.c-table').html(tableHtml);
	}
	$el.find('th.m-sort-leke').click(function() {
		const index = parseInt($(this).data('colidx'));
		columns.forEach((c, i) => {
			if (c.sort && i != index) {
				c.sort = SORT_DEF;
			}
		})
		const col = columns[index];
		if (col.sort == SORT_ASC) {
			col.sort = SORT_DESC;
		} else {
			col.sort = SORT_ASC;
		}
		col.onSort(datas, col.sort === SORT_ASC);
		that.render();
	});
}

export default StaticDataGrid;
