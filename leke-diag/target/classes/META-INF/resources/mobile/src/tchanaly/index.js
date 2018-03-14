import {bootReduxApp} from '../common/redux-boot';
import reducers from './reducers';
import * as actions from './actions';
import App from './containers';

bootReduxApp(App, actions, reducers, {
    dev: true
});
