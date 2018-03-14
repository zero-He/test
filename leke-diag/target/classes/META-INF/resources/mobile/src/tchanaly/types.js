const types = {};

function reg(acts) {
    acts.split(',').forEach(act => types[act] = act);
}

// 初始化
reg('INIT_DATAS,UPDATE_OPTIONS,SEARCH,CHANGE_VIEW');
// 页面控制
reg('FETCH_DATAS,RECEIVE_DATAS');

export default types;
