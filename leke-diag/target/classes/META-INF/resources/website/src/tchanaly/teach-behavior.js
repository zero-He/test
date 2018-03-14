var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var AnalyseFilter = require('../common/AnalyseFilter');
var TeachBehaviorView = require('./TeachBehaviorView');
var $ = require('jquery');

const cycle = AnalyseFilter.buildOption('周期', 'cycleId', Csts.cycles);
const options = [];

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            query: null,
            view: null
        }
    }
    handleFilterQuery = (query) => {
        const that = this;
        this.setState({
            query: query,
            view: null
        });
        $.post('teach-behavior-data.htm', query).done(json => {
            if (json.success) {
                that.setState({view: json.datas.view});
            }
        })
    }
	render() {
        const {view, query} = this.state;
		return (
            <div>
				<AnalyseFilter cycle={cycle} options={options} onFilterQuery={this.handleFilterQuery} />
                <TeachBehaviorView view={view} query={query} />
            </div>
		);
	}
}

ReactDOM.render(<Main />, document.getElementById('app'));
