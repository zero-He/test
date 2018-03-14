const React = require('common/react/react');

function parseQuery(cycle, options) {
    const query = {
        cycleId: cycle.value
    };
    options.forEach(option => {
        query[option.field] = option.value;
    });
    return query;
}

class AnalyseFilter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            typeIdx: 0
        };
        this.query = parseQuery(props.cycle, props.options);
    }
    handleChangeQuery = (field, value) => {
        this.query[field] = value;
        this.doQuery();
    }
    doQuery = () => {
        const {onFilterQuery} = this.props;
        if (onFilterQuery) {
            const query = this.query;
            if (query.klassId) {
    			const arr = query.klassId.match(/(\d+)-(\d+)/);
    			if (arr.length == 3) {
    				query.classId = arr[1];
    				query.subjectId = arr[2];
    			}
    		}
            onFilterQuery(query);
        }
    }
    componentDidMount() {
        this.doQuery();
    }
    render() {
        const that = this;
        const {typeIdx} = this.state;
        const {cycle, options} = this.props;
        return (
			<div className="m-search-box">
				<div className="f-fl">
                    <ul>
                    {cycle.items.map((item, i) => {
                        let handler = () => {
                            that.setState({typeIdx: i});
                            that.handleChangeQuery('cycleId', item.subs[0].value);
                        };
                        return <li key={i} className={i == typeIdx ? 'active' : ''}><a onClick={handler}>{item.label}</a></li>;
                    })}
                    </ul>
                </div>
                <div className="f-fr">
                    <Select key="cycleId" field="cycleId" items={cycle.items[typeIdx].subs} onChange={that.handleChangeQuery} />
                    {options.map(option => <Select key={option.field} field={option.field} items={option.items} onChange={that.handleChangeQuery} />)}
                </div>
			</div>
		);
    }
}

class Select extends React.Component {
    handleChange = (e) => {
        const {field, onChange} = this.props;
        if (onChange) {
            onChange(field, e.target.value);
        }
    }
    render() {
        const {items} = this.props;
        return (
            <select className="u-select u-select-lg" onChange={this.handleChange}>
                {items.map(item => <option key={item.value} value={item.value}>{item.label}</option>)}
            </select>
        );
    }
}

AnalyseFilter.buildOption = function(title, field, items) {
    let current = items[0], hasGroup = false;
    if (current && current.subs && current.subs.length) {
        current = current.subs[0];
        hasGroup = true;
    }
    return {
        title,
        field,
        value: current ? current.value : null,
        hasGroup,
        items
    };
}

module.exports = AnalyseFilter;
