import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import Tips from '../common/tips';
import WorkKeeper from './components';
import * as actions from './actions';

class App extends React.Component {
    render() {
        let {store, actions} = this.props;
        if (store.viewName === 'loading') {
            return <div className="m-init"><i></i></div>;
        }
        if (store.viewName === 'nochild') {
            return <Tips message="您还没有绑定孩子~~" />;
        }
        return <WorkKeeper store={store} actions={actions} />;
    }
}

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

export default connect(mapStateToProps, mapDispatchToProps)(App);
