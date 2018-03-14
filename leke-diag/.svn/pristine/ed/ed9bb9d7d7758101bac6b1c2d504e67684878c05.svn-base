import StaticDataGrid from '../common/StaticDataGrid';

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
    {title: '姓名', field: 'userName', onSort: 'userNamePy'},
    {title: '完成态度', field: 'normalRate', render: normalRateRender, onSort: 'normalRate'}
];

var grid = new StaticDataGrid($('#app'), {
    datas: Csts.datas,
    columns: columns
});
