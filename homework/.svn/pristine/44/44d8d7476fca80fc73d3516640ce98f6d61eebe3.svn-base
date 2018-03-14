import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import createStore from '../common/createStore';
import {Provider} from 'react-redux';
import reducers from './reducers';
import {getInitDatas,onDeviceReady} from './actions';
import App from './containers';

const store = createStore(reducers);
store.dispatch(getInitDatas());
store.dispatch(onDeviceReady());

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('app')
);
