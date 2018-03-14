import {combineReducers} from 'redux';
import types from '../actions/types';

const BASIC_INIT_STATE = {
    mode: 'errque',
    paper: null,
    qdxs: null,
    ques: null,
    results: null,
    workInfo: null
}

export default function basic(state = BASIC_INIT_STATE, action) {
    switch (action.type) {
        case types.INIT_DATAS:
        case types.UPDATE_DATAS:
            return {
                ...state,
                ...action.data
            };
        default:
            return state;
    }
}
