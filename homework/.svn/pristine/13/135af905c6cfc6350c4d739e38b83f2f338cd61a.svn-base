import types from './types';
import transformInitData from './transformInitData';
import throttle from 'lodash/throttle';

export function getInitDatas() {
    return dispatch => {
        webapi.getInitDatas(data => {
            data = transformInitData(data);
            dispatch({
                type: types.INIT_DATAS,
                data
            });
        })
    }
}

export function onDeviceReady() {
    return dispatch => {
        document.addEventListener('deviceready', function() {
            console.log('cordova deviceready.');
            dispatch({type: types.DEVICE_READY});
        }, false);
    }
}

// 视图路由
export function onRouterView(viewName) {
    return {
        type: types.ROUTER_VIEW,
        viewName
    };
}

function dispatchSkip(action) {
    return dispatch => {
    //    throttle(() => {
            dispatch(action);
    //    }, 500);
    }
}

// 题目路由(序号)
export function onSheetSkipIndex(index) {
    return dispatchSkip({
        type: types.SHEET_SKIP_INDEX,
        index
    });
}

// 题目路由(下一题)
export function onSheetSkipNext() {
    return dispatchSkip({type: types.SHEET_SKIP_NEXT});
}

// 题目路由(上一题)
export function onSheetSkipPrev() {
    return dispatchSkip({type: types.SHEET_SKIP_PREV});
}
