import types from './types';

function buildOption(title, field, items) {
    let current = items[0], hasGroup = false;
    if (current && current.subs && current.subs.length) {
        current = current.subs[0];
        hasGroup = true;
    }
    return {
        title,
        field,
        current,
        hasGroup,
        visible: true,
        items
    };
}

export function getInitDatas() {
    return (dispatch, getState) => {
        webapi.getInitDatas(datas => {
            const options = [];
            options.push(buildOption('班级', 'klassId', datas.klasses));
            options.push(buildOption('周期', 'cycleId', datas.cycles));
            dispatch({
                type: types.INIT_DATAS,
                userName: datas.userName,
                options
            });
            changeView(datas.viewName, dispatch, getState);
        });
    }
}

function changeView(viewName, dispatch, getState) {
    dispatch({
        type: types.CHANGE_VIEW,
        viewName
    });
    fetchRptView(dispatch, getState);
}

function validKlassIsNotExist(options, filters) {
	if (filters.indexOf('klassId') >= 0) {
		var option = options.filter(v => v.field == 'klassId')[0];
		return !(option.items && option.items.length);
	}
	return false;
}

function fetchRptView(dispatch, getState) {
    const state = getState();
    if (validKlassIsNotExist(state.control.options, state.control.filters)) {
    	return;
    }
    const query = parseQuery(state.control);
    dispatch({type: types.FETCH_DATAS});
    webapi.fetchRptView(query).done(json => {
        let {view} = json.datas;
        dispatch({
            type: types.RECEIVE_DATAS,
            query: query,
            view: view
        });
    });
}

export function parseQuery(control) {
    const query = {
        viewName: control.viewName
    };
    control.options.filter(option => option.current).forEach(option => {
        query[option.field] = option.current.value;
    });
    return query;
}

export function onChangeView(viewName) {
    return (dispatch, getState) => changeView(viewName, dispatch, getState);
}

export function onChangeOptions(options) {
    return (dispatch, getState) => {
        dispatch({
            type: types.UPDATE_OPTIONS,
            options
        });
        fetchRptView(dispatch, getState);
    };
}
