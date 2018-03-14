import types from '../actions/types';
import Modal from '../../common/modal';

const start = Date.now();

const CONTROL_INIT_STATE = {
    heartbeat: 0,
    ready: false,
    usedTime: 0,
    viewName: 'loading', // loading, sheet, card
    index: 0,
    maxIndex: 0,
    enterMode: 'left', // left, right
    allAnswer: false,
    resultInfo: null
}

export default function control(state = CONTROL_INIT_STATE, action) {
    switch (action.type) {
        case types.DEVICE_READY:
            return {
                ...state,
                ready: true
            };
        case types.INIT_DATAS:
        case types.UPDATE_DATAS:
            return {
                ...state,
                viewName: 'sheet',
                index: 0,
                maxIndex: action.data.qdxs.length
            };
        case types.ROUTER_VIEW:
            return {
                ...state,
                viewName: action.viewName
            };
        case types.ROUTER_RESULT:
            return {
                ...state,
                viewName: 'result',
                resultInfo: action.resultInfo
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
            } else if (state.viewName == 'result') {
                return state;
            } else if (state.index >= state.maxIndex - 1) {
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
                    enterMode: 'right'
                };
            } else if (state.viewName == 'result') {
                return state;
            } else if (state.index <= 0) {
                Modal.toast('已经是第一题了');
                return state;
            }
            return {
                ...state,
                viewName: 'sheet',
                index: state.index - 1,
                enterMode: 'right'
            };
        case types.HEARTBEAT:
            return {
                ...state,
                usedTime: (Date.now() - start) / 1000
            };
        default:
            return state;
    }
}
