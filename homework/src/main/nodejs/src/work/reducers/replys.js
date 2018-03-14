import {combineReducers} from 'redux';
import types from '../actions/types';

export default function answer(state = {}, action) {
    switch (action.type) {
        case types.INIT_DATAS:
            return {
                ...state,
                ...action.data.replys
            };
        case types.ANSWER_JUDGEMENT:
        case types.ANSWER_SINGLE_CHOICE:
        case types.ANSWER_MULTI_CHOICE:
        case types.ANSWER_FILLBLANK:
        case types.ANSWER_PUNCTUATE:
        case types.ANSWER_OPENEND:
        case types.ANSWER_ORAL:
        case types.ANSWER_HANDWRITE:
            return {
                ...state,
                [action.qid]: action.answers
            }
        default:
            return state;
    }
}
