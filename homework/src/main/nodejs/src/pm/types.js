const types = {};

function reg(acts) {
    acts.split(',').forEach(act => types[act] = act);
}

reg('INIT_DATAS,CHANGE_CHILD,FETCH_SUBJS,RECEIVE_SUBJS');

export default types;
