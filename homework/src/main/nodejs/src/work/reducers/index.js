import { combineReducers } from 'redux';
import control from './control';
import replys from './replys';
import basic from './basic';

export default combineReducers({
    control,
    replys,
    basic
});
