import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import Tips from '../../common/tips';
import Sheet from '../components/sheet';
import * as actions from '../actions';

class App extends Component {
    render() {
        let {store, actions} = this.props;
        if (!store.control.ready) {
            return <div className="m-init"><i></i></div>;
        }
        if (store.control.viewName == 'loading') {
            return <div className="m-init"><i></i></div>;
        }
        return <Sheet store={store} actions={actions} />;
    }
}

function mapStateToProps(state) {
    return {
        store: {
            ...state.basic,
            control: state.control,
            replys: state.replys
        }
    };
}

function mapDispatchToProps(dispatch) {
    return {
        actions: bindActionCreators(actions, dispatch)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
