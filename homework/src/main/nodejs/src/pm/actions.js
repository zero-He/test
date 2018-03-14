import types from './types';

const CACHE_SUBJS = {};

function fetchSubjs(state, dispatch) {
    const userId = state.childs[state.childIdx].id;
    if (CACHE_SUBJS[userId]) {
        dispatch({
            type: types.RECEIVE_SUBJS,
            subjects: CACHE_SUBJS[userId]
        });
    } else {
        dispatch({type: types.FETCH_SUBJS});
        webapi.fetchSubjs(userId).done(json => {
            CACHE_SUBJS[userId] = json.datas.subjects;
            dispatch({
                type: types.RECEIVE_SUBJS,
                subjects: json.datas.subjects
            });
        });
    }
}

export function getInitDatas() {
    return (dispatch, getState) => {
        webapi.getInitData(childs => {
            if (childs && childs.length) {
                dispatch({
                    type: types.INIT_DATAS,
                    viewName: 'manager',
                    childs
                });
                fetchSubjs(getState(), dispatch);
            } else {
                dispatch({
                    type: types.INIT_DATAS,
                    viewName: 'nochild',
                    childs
                });
            }
        });
    }
}

export function onChangeChild(childIdx) {
    return (dispatch, getState) => {
        dispatch({
            type: types.CHANGE_CHILD,
            childIdx
        });
        fetchSubjs(getState(), dispatch);
    }
}
