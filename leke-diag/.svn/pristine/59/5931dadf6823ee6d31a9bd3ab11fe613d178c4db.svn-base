import React, {Component, PropTypes} from 'react';
import ToolTips from '../common/ToolTips';
import AnalyseNavTab from './components/AnalyseNavTab';
import AnalyseFilter from './components/AnalyseFilter';
import AnalyseResult from './components/AnalyseResult';

function validKlassIsNotExist(options, filters) {
	if (filters.indexOf('klassId') >= 0) {
		var option = options.filter(v => v.field == 'klassId')[0];
		return !(option.items && option.items.length);
	}
	return false;
}

export default class App extends Component {
    render() {
        const {store, actions} = this.props;
        const {options, filters} = store.control;
        if (validKlassIsNotExist(options, filters)) {
        	return (
        		<div className="c-analyse">
                    <AnalyseNavTab store={store} actions={actions} />
                    <ToolTips>您暂无关联班级，无数据可供分析！</ToolTips>;
                </div>
        	);
        }
        return (
            <div className="c-analyse">
                <AnalyseNavTab store={store} actions={actions} />
                <AnalyseFilter options={options} filters={filters} onChangeOptions={actions.onChangeOptions} />
                <AnalyseResult store={store} actions={actions} />
            </div>
        );
    }
}
