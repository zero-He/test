
const types = {};

function reg(acts) {
    acts.split(',').forEach(act => types[act] = act);
}

// 初始化
reg('INIT_DATAS,DEVICE_READY');
// 页面控制
reg('ROUTER_VIEW,SHEET_SKIP_INDEX,SHEET_SKIP_PREV,SHEET_SKIP_NEXT');

export default types;
