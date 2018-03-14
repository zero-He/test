var React = require('common/react/react');
var $ = require('jquery');

const viewUrlPrefix = `${Leke.domain.beikeServerName}/auth/common/microcourse/preview.htm?microcourseId=`;
const defaultImgUrl = `${Leke.domain.staticServerName}/images/analyse/course-bg.png`;

class Microcourse extends React.Component {
	render() {
		let {data} = this.props;
		return (
			<li key={data.microcourseId} className="course-item">
				<a href={viewUrlPrefix + data.microcourseId} target="_blank">
					<img src={data.thumbnail ? `${Leke.domain.fileServerName}/${data.thumbnail}` : defaultImgUrl} />
					<div className="cover">
						<h3 className="coursename">{data.microcourseName}</h3>
						<p>主讲教师：<span className="teacher f-ml20">{data.createdName}</span></p>
						<p>时长：<span className="time f-ml20">{data.timeStr ? data.timeStr : '未知'}</span></p>
						<p>课程简介：<span className="intro f-ml20">{data.description ? data.description.substring(0, 23) : '暂无'}</span></p>
					</div>
				</a>
			</li>
		);
	}
}

class MicroRecommend extends React.Component {
	render() {
		const {datas} = this.props;
		return (
			<ul className="clear">{datas.map((data, i) => <Microcourse key={i} data={data} />)}</ul>
		);
	}
}

$(document).on('mouseenter mouseleave', '.course-item', function(e) {
	var w = $(this).width();
	var h = $(this).height();
	var x = (e.pageX - (this.offsetLeft + (w / 2))) * (w > h ? (h / w) : 1);
	var y = (e.pageY - (this.offsetTop + (h / 2))) * (h > w ? (w / h) : 1);
	var dirNum = Math.round((((Math.atan2(y, x) * (180 / Math.PI)) + 180) / 90) + 3) % 4;
	var pos = [
		{ left : 0, top : -148 },
		{ left : 255, top : 0 },
		{ left : 0, top : 148 },
		{ left : -255, top : 0 }
	];
	if (e.type == 'mouseenter') {
		$(this).find('.cover').css(pos[dirNum]).stop(true, true).animate({ left : 0, top : 0 }, 200);
	} else {
		$(this).find('.cover').stop(true, true).animate(pos[dirNum], 200);
	}
});

module.exports = MicroRecommend;
