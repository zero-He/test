import React from 'react';
import ReactDOM from 'react-dom';
import {Provider, connect} from 'react-redux';
import {createStore, applyMiddleware, bindActionCreators} from 'redux';
import createLogger from 'redux-logger';
import thunk from 'redux-thunk';

function connectApp(App, actions) {
    function mapStateToProps(state) {
        return {
            store: state
        };
    }

    function mapDispatchToProps(dispatch) {
        return {
            actions: bindActionCreators(actions, dispatch)
        };
    }
    return connect(mapStateToProps, mapDispatchToProps)(App);
}

export function bootReduxApp(App, actions, reducers, options = {}) {
    const middleware = [thunk];
    if (options.dev) {
        middleware.push(createLogger());
    }
    const store = createStore(reducers, applyMiddleware(...middleware));
    if (actions.getInitDatas) {
        store.dispatch(actions.getInitDatas());
    }
    const ConnectApp = connectApp(App, actions);
    ReactDOM.render(
        <Provider store={store}>
            <ConnectApp />
        </Provider>,
        document.getElementById('app')
    );
}
