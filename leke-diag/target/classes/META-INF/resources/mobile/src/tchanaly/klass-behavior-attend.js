import StaticDataGrid from '../common/StaticDataGrid';
import {toFixed, toRate} from '../utils/number';

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
    {width: '35%', title: '序号', field: 'index', render: indexRender, fixed: true},
    {width: '75%', title: '姓名', field: 'userName', fixed: true, onSort: 'userNamePy'},
    {width: '70px', title: '应上', field: 'totalNum', onSort: 'totalNum'},
    {width: '70px', title: '实上', field: 'realNum', onSort: 'realNum'},
    {width: '70px', title: '到课率', field: 'realRate', render: realRateRender, onSort: 'realNum'},
    {width: '70px', title: '全勤', field: 'normalNum', render: rateRender, onSort: 'normalNum'},
    {width: '70px', title: '迟到', field: 'belateNum', render: rateRender, onSort: 'belateNum'},
    {width: '70px', title: '早退', field: 'earlyNum', render: rateRender, onSort: 'earlyNum'},
    {width: '70px', title: '迟到&早退', field: 'exceptNum', render: rateRender, onSort: 'exceptNum'},
    {width: '70px', title: '缺勤', field: 'absentNum', render: rateRender, onSort: 'absentNum'}
];

var grid = new StaticDataGrid($('#app'), {
    fixed: true,
    fixedPer: 30,
    datas: Csts.datas,
    columns: columns
});
