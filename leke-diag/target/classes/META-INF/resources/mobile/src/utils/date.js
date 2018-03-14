var ObjProto = Object.prototype;
var toString = ObjProto.toString;
var hasOwnProperty = ObjProto.hasOwnProperty;

var _ = {};

_.has = function(obj, key) {
    return obj != null && hasOwnProperty.call(obj, key);
};

['Function', 'String', 'Number', 'Date'].forEach(function(name) {
	_['is' + name] = function(obj) {
        return toString.call(obj) === '[object ' + name + ']';
    };
});

_.memoize = function(func, hasher) {
    var memoize = function(key) {
        var cache = memoize.cache;
        var address = '' + (hasher ? hasher.apply(this, arguments) : key);
        if (!_.has(cache, address)) {
            cache[address] = func.apply(this, arguments);
        }
        return cache[address];
    };
    memoize.cache = {};
    return memoize;
};

var UNIT_MS = {
	'Milliseconds' : 1,
    'Seconds' : 1e3,
    'Minutes' : 6e4,
    'Hours' : 36e5,
    'Date' : 864e5,
    'Week' : 6048e5
};

var UNIT_ALIAS = {
	ms : 'Milliseconds',
    s : 'Seconds',
    m : 'Minutes',
    H : 'Hours',
    d : 'Date',
    M : 'Month',
    y : 'FullYear'
};

/**
 * LocalDate : 日期封装
 * <p>除 set 方法外，其他修改方法都应该返回新的 LocalDate 对象</p>
 */
function LocalDate(input) {
	var date = new Date();
	if(input) {
		if(_.isDate(input) || input instanceof LocalDate) {
			date = new Date(input.getTime());
		} else if(_.isNumber(input)) {
			date = new Date(input);
		}
	}
	this._d = date;
}

LocalDate.prototype.get = function(unit) {
	var field = UNIT_ALIAS[unit] || unit;
	var val = null;
	switch (field) {
	case 'isoMonth':
		val = this.get('M') + 1;
		break;
	case 'simpleYear':
		val = this.get('y') % 100;
		break;
	default:
		val = this._d['get' + field]();
		break;
	}
	return val;
};

LocalDate.prototype.set = function(unit, val) {
	var field = UNIT_ALIAS[unit] || unit;
	switch (field) {
	case 'isoMonth':
		this.set('M', val - 1);
		break;
	case 'simpleYear':
		this.set('y', Math.floor(this.get('y') / 100) * 100 + val);
		break;
	default:
		this._d['set' + field](val);
		break;
	}
	return this;
};

LocalDate.prototype.truncate = function(unit) {
	var result = new LocalDate(this.getTime());
	var field = UNIT_ALIAS[unit] || unit;
	switch (field) {
	case 'FullYear': result.set('M', 0); // 继续
	case 'Month': result.set('d', 1); // 继续
	case 'Date': result.set('H', 0); // 继续
	case 'Hours': result.set('m', 0); // 继续
	case 'Minutes': result.set('s', 0); // 继续
	case 'Seconds': result.set('ms', 0);
	}
	return result;
};

function padZero(num, len) {
	var result = num + '';
	while(result.length < len) {
		result = '0' + result;
	}
	return result;
}

function tokenFunc(field, len) {
	len = len || 2;
	return function() {
		var val = _.isString(field) ? this.get(field) : field.call(this);
		return padZero(val, len);
	};
}

var REG_FMT_TOKEN = /(yyyy|yy|MM|dd|HH|mm|ss|SSS|.)/g;
var TOKEN_FUNCS = {
	yyyy: tokenFunc('y', 4),
	yy: tokenFunc('simpleYear'),
	MM: tokenFunc('isoMonth'),
	dd: tokenFunc('d'),
	HH: tokenFunc('H'),
	mm: tokenFunc('m'),
	ss: tokenFunc('s'),
	SSS: tokenFunc('ms', 3)
};

var formatter = _.memoize(function(fmt) {
	var matches = fmt.match(REG_FMT_TOKEN);
	var tokens = matches.map(function(match) {
		return TOKEN_FUNCS[match] || match;
	});
	return function(lekeDate) {
		var output = '';
		tokens.forEach(function(token) {
			output += _.isFunction(token) ? token.call(lekeDate) : token;
		});
		return output;
	};
});

LocalDate.prototype.format = function(fmt) {
	var f = formatter(fmt || 'yyyy-MM-dd');
	return f(this);
};

LocalDate.prototype.getTime = function() {
	return this.get('Time');
};

LocalDate.prototype.setTime = function(val) {
	return this.set('Time', val);
};

LocalDate.prototype.compareTo = function(that) {
	if(that instanceof LocalDate) {
		return this._d.getTime() - that._d.getTime();
	} else {
		throw 'target to compare is not LocalDate instance.';
	}
};

LocalDate.prototype.toDate = function() {
	return new Date(this.getTime());
};

LocalDate.prototype.add = function(i, unit) {
	var result = new LocalDate(this.getTime());
	var field = UNIT_ALIAS[unit] || unit;
	var ms = UNIT_MS[field];
	if(ms) {
		result.setTime(this.getTime() + i * ms);
	} else {
		if(field === 'FullYear') {
			result.set('y', this.get('y') + i);
		} else if(field === 'Month') {
			var months = this.get('M') + i;
			var dy = Math.floor(months / 12);
			var mon = months - dy * 12;
			if(dy !== 0) {
				result.set('y', this.get('y') + dy);
			}
			result.set('M', mon);
		}
	}
	return result;
};

function parseFunc(field) {
	return function(match) {
		// 假设都已经是数字了
		var val = parseInt(match);
		if(_.isString(field)) {
			this.set(field, val)
		} else {
			field.call(this, val);
		}
	};
}

var PARSE_TOKENS = {
	yyyy: {
		m: '\\d{4}',
		p: parseFunc('y')
	},
	yy: {
		m: '\\d{2}',
		p: parseFunc('simpleYear')
	},
	MM: {
		m: '\\d{1,2}',
		p: parseFunc('isoMonth')
	},
	dd: {
		m: '\\d{1,2}',
		p: parseFunc('d')
	},
	HH: {
		m: '\\d{1,2}',
		p: parseFunc('H')
	},
	mm: {
		m: '\\d{1,2}',
		p: parseFunc('m')
	},
	ss: {
		m: '\\d{1,2}',
		p: parseFunc('s')
	},
	SSS: {
		m: '\\d{3}',
		p: parseFunc('ms')
	}
};

var parser = _.memoize(function(fmt) {
	var matches = fmt.match(REG_FMT_TOKEN);
	var pattern = '^';
	var ps = [];
	matches.forEach(function(match) {
		var pt = PARSE_TOKENS[match];
		if(pt) {
			pattern += '(' + pt.m + ')';
			ps.push(pt.p);
		} else {
			pattern += match;
		}
	});
	pattern += '$';
	var reg = new RegExp(pattern);
	return function(dateStr) {
		if(!dateStr || !reg.test(dateStr)) {
			return null;
		}
		var result = new LocalDate().truncate('y');
		var matched = reg.exec(dateStr);
		ps.forEach(function(p, i) {
			p.call(result, matched[i + 1]);
		});
		return result;
	};
});

LocalDate.parse = function(dateStr, fmt) {
	var p = parser(fmt);
	return p(dateStr);
};

LocalDate.DEFAULT_FMTS = ['yyyy-MM-dd', 'yyyy-MM', 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', 'yyyy/MM/dd'];

LocalDate.of = function(input) {
	if(!input) {
		return new LocalDate();
	} else if(_.isDate(input) || input instanceof LocalDate || _.isNumber(input)) {
		return new LocalDate(input);
	} else if(_.isString(input)) {
		var ld = null;
		LocalDate.DEFAULT_FMTS.some(function(fmt) {
			ld = LocalDate.parse(input, fmt);
			return !!ld;
		});
		return ld;
	} else {
		return new LocalDate();
	}
};

LocalDate.format = function(input, fmt) {
	if(!input) {
		return '';
	}
	fmt = fmt || 'yyyy-MM-dd';
	return LocalDate.of(input).format(fmt);
};

module.exports = LocalDate;