var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var AnalyseFilter = require('../common/AnalyseFilter');
var KlassBehaviorView = require('./KlassBehaviorView');
var $ = require('jquery');

const cycle = AnalyseFilter.buildOption('周期', 'cycleId', Csts.cycles);
const options = [];
options.push(AnalyseFilter.buildOption('班级', 'klassId', Csts.klasses));

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
        $.post('klass-behavior-data.htm', query).done(json => {
            if (json.success) {
                that.setState({view: json.datas.view});
            }
        })
    }
	render() {
        const {view, query} = this.state;
        if (!(Csts.klasses && Csts.klasses.length)) {
            return <div className="m-tips"><i></i><span>您暂无关联班级，无数据可供分析！</span></div>;
        }
		return (
            <div>
				<AnalyseFilter cycle={cycle} options={options} onFilterQuery={this.handleFilterQuery} />
                <KlassBehaviorView view={view} query={query} />
            </div>
		);
	}
}

ReactDOM.render(<Main />, document.getElementById('app'));
