import {combineReducers} from 'redux';
import types from './types';

const INIT_STATE = {
    control: {
        viewName: null, // teach-behavior, klass-behavior
        filters: [],
        userName: null,
        options: null
    },
    result: {
        loading: false,
        nodatas: false,
        query: null,
        view: null
    }
}

const filterMap = {
    'klass-score': ['klassId', 'cycleId'],
    'klass-behavior': ['klassId', 'cycleId'],
    'teach-behavior': ['cycleId']
}

function control(state = INIT_STATE.control, action) {
    switch (action.type) {
        case types.INIT_DATAS:
            return {
                ...state,
                userName: action.userName,
                options: action.options
            };
        case types.CHANGE_VIEW:
            return {
                ...state,
                viewName: action.viewName,
                filters: filterMap[action.viewName]
            };
        case types.UPDATE_OPTIONS:
            return {
                ...state,
                options: action.options
            };
        default:
            return state;
    }
}

function result(state = INIT_STATE.result, action) {
    switch (action.type) {
        case types.FETCH_DATAS:
            return {
                ...state,
                loading: true,
                nodatas: false,
                view: null
            };
        case types.RECEIVE_DATAS:
            console.log(action.view);
            return {
                ...state,
                loading: false,
                nodatas: action.view == null,
                query: action.query,
                view: action.view
            };
        default:
            return state;
    }
}

export default combineReducers({
    control,
    result
});
