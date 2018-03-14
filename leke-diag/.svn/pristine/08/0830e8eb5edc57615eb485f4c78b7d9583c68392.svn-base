var $ = require('jquery');
var StaticDataGrid = require('../common/StaticDataGrid');
var {toFixed, toRate} = require('../utils/number');

function indexRender(value, row, index) {
    return index + 1;
}

function rateRender(value, row, index) {
    var rate = toRate(value, row.totalNum);
    return `${value}(${rate}%)`;
}

function realRateRender(value, row, index) {
    return toRate(row.realNum, row.totalNum) + '%';
}

var columns = [
    {title: '序号', field: 'index', render: indexRender},
    {title: '姓名', field: 'userName', onSort: 'userNamePy'},
    {title: '应上课堂数', field: 'totalNum', onSort: 'totalNum'},
    {title: '实上课堂数', field: 'realNum', onSort: 'realNum'},
    {title: '到课率', field: 'realRate', render: realRateRender, onSort: 'realNum'},
    {title: '全勤(比率)', field: 'normalNum', render: rateRender, onSort: 'normalNum'},
    {title: '迟到(比率)', field: 'belateNum', render: rateRender, onSort: 'belateNum'},
    {title: '早退(比率)', field: 'earlyNum', render: rateRender, onSort: 'earlyNum'},
    {title: '迟到且早退(比率)', field: 'exceptNum', render: rateRender, onSort: 'exceptNum'},
    {title: '缺勤(比率)', field: 'absentNum', render: rateRender, onSort: 'absentNum'}
];

var grid = new StaticDataGrid($('#app'), {
    datas: Csts.datas,
    columns: columns
});
