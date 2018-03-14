var $ = require('jquery');
var StaticDataGrid = require('../common/StaticDataGrid');

function indexRender(value, row, index) {
    return index + 1;
}

function normalRateRender(value, row, index) {
    if (value >= 90) {
        return '<span class="positive">积极</span>';
    } else if (value >= 60) {
        return '<span class="kind-positive">较积极</span>';
    }
    return '<span class="negative">不积极</span>';
}

var columns = [
    {title: '序号', field: 'index', render: indexRender},
    {title: '姓名', field: 'studentName'},
    {title: '应提交作业份数', field: 'stuHomeworkNum', onSort: 'stuHomeworkNum'},
    {title: '按时提交率（份）', field: 'submitNumPro', onSort: 'submitNum'},
    {title: '延时补交率（份）', field: 'lateSubmitNumPro', onSort: 'lateSubmitNum'},
    {title: '未提交率（份）', field: 'noSubmitNumPro', onSort: 'noSubmitNum'}
];

var grid = new StaticDataGrid($('#app'), {
    datas: Csts.datas,
    columns: columns
});
