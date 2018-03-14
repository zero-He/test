import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import createStore from '../common/createStore';
import reducers from './reducers';
import App from './containers';
import {getInitDatas} from './actions';

const store = createStore(reducers);
store.dispatch(getInitDatas());

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('app')
);
