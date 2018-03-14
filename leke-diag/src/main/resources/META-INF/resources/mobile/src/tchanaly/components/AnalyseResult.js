import React, {Component, PropTypes} from 'react';
import ToolTips from '../../common/ToolTips';
import KlassScoreView from './KlassScoreView';
import KlassBehaviorView from './KlassBehaviorView';
import TeachBehaviorView from './TeachBehaviorView';

const ViewComponents = {
    'klass-score': KlassScoreView,
    'klass-behavior': KlassBehaviorView,
    'teach-behavior': TeachBehaviorView
}

export default class AnalyseResult extends Component {
    render() {
        const {store, actions} = this.props;
        const {loading, view} = store.result;
        if (loading || view == null) {
            return <div className="m-init"><i></i></div>;
        }
        if (view.success == false) {
            return <ToolTips>{view.message}</ToolTips>
        }
        const ViewComponent = ViewComponents[store.control.viewName];
        return <ViewComponent store={store} actions={actions} />
    }
}
