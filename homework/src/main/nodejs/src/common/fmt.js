import padStart from 'lodash/padStart';

export function fmtCount(count) {
    if (count == undefined || count == null || count <= 0) {
        return '';
    }
    return count > 99 ? `(99+)` : `(${count})`;
}

export function fmtScore(score) {
    if (score == null || score === undefined) {
        return '--';
    }
    return fixedNum(score, 1);
}

export function fmtTime(time) {
    if (time) {
        time = Math.ceil(time);
        let second = time % 60;
        let minute = Math.floor(time / 60);
        return `${padStart(minute, 2, '0')}:${padStart(second, 2, '0')}`;
    }
    return '00:00';
}


/**
 * 将Number四舍五入为指定小数位数的数字。<br>
 * 注意：Number.toFixed为五舍六入。
 * @param num 数值
 * @param digit 位数
 */
export function fixedNum(num, digit) {
	var _num = parseFloat(num);
	if (isNaN(_num)) {
		return num;
	}
	digit = digit || 0;
	if (digit < 1) {
		return Math.round(_num);
	}
	var dd = Math.pow(10, digit);
	return Math.round(_num * dd) / dd;
}

/**
 * 将数值格式化。<br>
 * 示例：
 * 	LekeNum.format(2356, '$#,##0.##') ==>  $2,356
 * 	LekeNum.format(2356, '$#,##0.00') ==>  $2,356.00
 * 	LekeNum.format(2356.125, '$#,##0.00')  ==>  $2,356.13
 * @param value 数值
 * @param pattern 格式
 */
export function fmtNum(value, pattern) {
	if (value === undefined || value === null || value === '' || isNaN(+value) || !pattern) {
		return value; // return as it is.
	}
	var isNegative, result, decimal, group, posLeadZero, posTrailZero, posSeparator, part, szSep, integer,

	len = pattern.length, start = pattern.search(/[0-9\-\+#]/),
	// find prefix
	prefix = start > 0 ? pattern.substring(0, start) : '',
	// reverse string: not an ideal method if there are surrogate pairs
	str = pattern.split('').reverse().join(''),

	end = str.search(/[0-9\-\+#]/), offset = len - end, indx = offset
			+ ((pattern.charAt(offset) === '.') ? 1 : 0),
	// find suffix
	suffix = end > 0 ? pattern.substring(indx, len) : '';
	// pattern with prefix & suffix removed
	pattern = pattern.substring(start, indx);
	// convert any string to number according to formation sign.
	value = pattern.charAt(0) === '-' ? -value : +value;
	isNegative = value < 0 ? value = -value : 0; // process only abs(), and turn on flag.

	// search for separator for grp & decimal, anything not digit, not +/- sign, not #.
	result = pattern.match(/[^\d\-\+#]/g);
	decimal = (result && result[result.length - 1]) || '.'; // treat the right most symbol as decimal
	group = (result && result[1] && result[0]) || ','; // treat the left most symbol as group separator

	// split the decimal for the format string if any.
	pattern = pattern.split(decimal);
	// Fix the decimal first, toFixed will auto fill trailing zero.
	value = value.toFixed(pattern[1] && pattern[1].length);
	value = +(value) + ''; // convert number to string to trim off *all* trailing decimal zero(es)

	// fill back any trailing zero according to format
	posTrailZero = pattern[1] && pattern[1].lastIndexOf('0'); // look for last zero in format
	part = value.split('.');
	// integer will get !part[1]
	if (!part[1] || (part[1] && part[1].length <= posTrailZero)) {
		value = (+value).toFixed(posTrailZero + 1);
	}
	szSep = pattern[0].split(group); // look for separator
	pattern[0] = szSep.join(''); // join back without separator for counting the pos of any leading 0.

	posLeadZero = pattern[0] && pattern[0].indexOf('0');
	if (posLeadZero > -1) {
		while (part[0].length < (pattern[0].length - posLeadZero)) {
			part[0] = '0' + part[0];
		}
	} else if (+part[0] === 0) {
		part[0] = '';
	}

	value = value.split('.');
	value[0] = part[0];

	// process the first group separator from decimal (.) only, the rest ignore.
	// get the length of the last slice of split result.
	posSeparator = (szSep[1] && szSep[szSep.length - 1].length);
	if (posSeparator) {
		integer = value[0];
		str = '';
		offset = integer.length % posSeparator;
		len = integer.length;
		for (indx = 0; indx < len; indx++) {
			str += integer.charAt(indx); // ie6 only support charAt for sz.
			// -posSeparator so that won't trail separator on full length
			/*jshint -W018 */
			if (!((indx - offset + 1) % posSeparator) && indx < len - posSeparator) {
				str += group;
			}
		}
		value[0] = str;
	}
	value[1] = (pattern[1] && value[1]) ? decimal + value[1] : '';

	// remove negative sign if result is zero
	result = value.join('');
	if (result === '0' || result === '') {
		// remove negative sign if result is zero
		isNegative = false;
	}

	// put back any negation, combine integer and fraction, and add back prefix & suffix
	return prefix + ((isNegative ? '-' : '') + result) + suffix;
}
