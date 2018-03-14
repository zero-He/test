import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import createStore from '../common/createStore';
import {Provider} from 'react-redux';
import reducers from './reducers';
import {getInitDatas, onHeartbeat, onDeviceReady} from './actions'
import App from './containers/App';

const store = createStore(reducers);
store.dispatch(onDeviceReady());
store.dispatch(getInitDatas());
store.dispatch(onHeartbeat());

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('app')
);
