
function DropPage(el, opts) {
	this.el = $(el);
	this.opts = $.extend({}, DropPage.defaults, opts);
	this.dl = this._init();
}

DropPage.defaults = {
	url : null,
	formId : null,
	curPage : 1,
	pageSize : 10,
	autoLoad : true, // 自动加载
	distance : 50, // 拉动距离
	threshold : '', // 提前加载距离
	onParams : null,
	onRender : function(datas, curPage) {
	}
};

DropPage.prototype._invoke = function(me) {
	var that = this;
	var opts = this.opts;
	var params;
	if (opts.params) {
		params = $.extend({}, opts.params, {
			curPage : opts.curPage,
			pageSize : opts.pageSize
		});
	} else if (opts.onParams) {
		params = opts.onParams();
		params.curPage = opts.curPage;
		params.pageSize = opts.pageSize;
	} else if (opts.formId) {
		params = $('#' + opts.formId).serialize() || '';
		params += params.length > 0 ? '&' : '';
		params += 'curPage=' + opts.curPage + '&pageSize=' + opts.pageSize;
	} else {
		params = {
			curPage : opts.curPage,
			pageSize : opts.pageSize
		};
	}
	$.post(opts.url, params).done(function(json) {
		if (json && json.datas && json.datas.page && json.datas.page.dataList.length > 0) {
			var page = json.datas.page
			if (opts.onRender) {
				opts.onRender(page.dataList, opts.curPage);
				opts.curPage += 1;
				if (opts.curPage > page.totalPage) {
					me.lock();
					me.noData();
				}
			}
		} else {
			me.lock();
			me.noData();
		}
		me.resetload();
	}).fail(function() {
		me.resetload();
	});
}

DropPage.prototype._init = function() {
	var that = this;
	var options = $.extend({}, this.opts, {
		scrollArea : window,
		loadUpFn3 : function(me) {
			that.reload();
		},
		loadDownFn : function(me) {
			if (me.isData) {
				that._invoke(me);
			}
		}
	});
	var dl = this.el.dropload(options);
	return dl;
}

DropPage.prototype.reload = function() {
	this.opts.curPage = 1;
	this.dl.unlock();
	this.dl.noData(false);
	this.dl.opts.loadDownFn(this.dl);
}

class DropLoad extends React.Component {
	constructor(props) {
		super(props);
		this.dp = null;
	}
	reload() {
		this.dp.reload();
	}
	componentDidMount() {
		let that = this;
		let opts = this.props.opts;
		if (that.props.params) {
			opts.params = this.props.params;
		}
		opts.onRender = function(datas, curPage) {
			if (that.props.onRender) {
				that.props.onRender(datas, curPage);
			}
		}
		this.dp = new DropPage(this.refs.dlDom, opts);
	}
	componentWillUnmount() {
		this.dp = null;
	}
	render() {
		return (
			<div ref="dlDom">
				<div>{this.props.children}</div>
			</div>
		);
	}
}

module.exports = DropLoad;