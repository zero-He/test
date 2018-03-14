import {createStore, applyMiddleware} from 'redux';
import createLogger from 'redux-logger';
import thunk from 'redux-thunk';

export default function createReduxStore(reducers, isDev) {
    const middleware = [thunk];
    if (isDev) {
        middleware.push(createLogger());
    }
    return createStore(reducers, applyMiddleware(...middleware));
}
