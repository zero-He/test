import {combineReducers} from 'redux';
import types from './types';
import Modal from '../common/modal';

const BASIC_INIT_STATE = {
    paper: null,
    qdxs: null,
    ques: null,
    results: null,
    workInfo: null
}

function basic(state = BASIC_INIT_STATE, action) {
    switch (action.type) {
        case types.INIT_DATAS:
            return {
                ...state,
                ...action.data
            };
        default:
            return state;
    }
}

const CONTROL_INIT_STATE = {
    viewName: 'loading', // loading, sheet, card
    ready: false,
    index: 0,
    maxIndex: 0,
    enterMode: 'left' // left, right
}

function control(state = CONTROL_INIT_STATE, action) {
    switch (action.type) {
        case types.INIT_DATAS:
            return {
                ...state,
                viewName: 'sheet',
                index: 0,
                maxIndex: action.data.qdxs.length
            };
        case types.DEVICE_READY:
            return {
                ...state,
                ready: true
            };
        case types.ROUTER_VIEW:
            return {
                ...state,
                viewName: action.viewName
            };
        case types.SHEET_SKIP_INDEX:
            return {
                ...state,
                viewName: 'sheet',
                index: action.index
            };
        case types.SHEET_SKIP_NEXT:
            if (state.viewName == 'card') {
                return state;
            }
            if (state.index >= state.maxIndex - 1) {
            	return {
            		...state,
            		viewName: 'card',
            		enterMode: 'left'
            	};
            }
            return {
                ...state,
                viewName: 'sheet',
                index: state.index + 1,
                enterMode: 'left'
            };
        case types.SHEET_SKIP_PREV:
            if (state.viewName == 'card') {
                return {
                    ...state,
                    viewName: 'sheet',
                    index: state.maxIndex - 1,
                    enterMode: 'left'
                };
            }
            if (state.index <= 0) {
                Modal.toast('已经是第一题了');
                return state;
            }
            return {
                ...state,
                viewName: 'sheet',
                index: state.index - 1,
                enterMode: 'right'
            };
        default:
            return state;
    }
}

export default combineReducers({
    basic,
    control
});
