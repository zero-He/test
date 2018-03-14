import types from './types';

const INIT_STATE = {
    viewName: 'loading', // loading, manager
    childs: [],
    childIdx: 0,
    fetching: false,
    subjects: []
}

export default function query(state = INIT_STATE, action) {
    switch (action.type) {
        case types.INIT_DATAS:
            return {
                ...state,
                viewName: action.viewName,
                childs: action.childs
            };
        case types.CHANGE_CHILD:
            return {
                ...state,
                childIdx: action.childIdx
            };
        case types.FETCH_SUBJS:
            return {
                ...state,
                fetching: true,
                subjects: []
            };
        case types.RECEIVE_SUBJS:
            return {
                ...state,
                fetching: false,
                subjects: action.subjects
            };
        default:
            return state;
    }
}
