var gulp = require('gulp');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var browserify = require('browserify');
var babelify = require("babelify");
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var sourcemaps = require('gulp-sourcemaps');

var baseDir = './src/';

var bundles = [{
	src : '/report/lesson/lesson-preview.js',
	name : 'lesson-preview.bundle.js',
	dist : '/report'
}, {
	src : '/report/lesson/lesson-review.js',
	name : 'lesson-review.bundle.js',
	dist : '/report'
}, {
	src : '/report/lesson/lesson-middle.js',
	name : 'lesson-middle.bundle.js',
	dist : '/report'
}, {
	src : '/report/homework/homework-overall.js',
	name : 'homework-overall.bundle.js',
	dist : '/report'
}, {
	src : '/report/homework/homework-person.js',
	name : 'homework-person.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/tchanaly-score.js',
	name : 'tchanaly-score.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/stuanaly-combined.js',
	name : 'stuanaly-combined.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/stuanaly-subject.js',
	name : 'stuanaly-subject.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/stuanaly-behavior.js',
	name : 'stuanaly-behavior.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/stuanaly-behavior-inclass.js',
	name : 'stuanaly-behavior-inclass.bundle.js',
	dist : '/report'
}, {
	src : '/report/analyze/stuanaly-behavior-homework.js',
	name : 'stuanaly-behavior-homework.bundle.js',
	dist : '/report'
}, {
	src : '/report/testing/testing-echarts.js',
	name : 'testing-echarts.bundle.js',
	dist : '/report'
}, {
	src : '/tchanaly/index.js',
	name : 'tchanaly.bundle.js',
	dist : '/report/tchanaly'
}, {
	src : '/tchanaly/klass-behavior-attend.js',
	name : 'klass-behavior-attend.bundle.js',
	dist : '/report/tchanaly'
}, {
	src : '/tchanaly/klass-behavior-work.js',
	name : 'klass-behavior-work.bundle.js',
	dist : '/report/tchanaly'
}];

function COMPILE(entries, bundle, dist) {
	browserify({
		entries : entries,
		transform : [babelify]
	}).bundle()
	.pipe(source(bundle))
	.pipe(buffer())
	.pipe(gulp.dest(dist));
}

function DEPLOY(src, bundle, dist) {
	browserify({
		entries : src,
		transform : [babelify],
		debug : true
	}).bundle()
	.pipe(source(bundle))
	.pipe(buffer())
//	.pipe(sourcemaps.init({loadMaps : false}))
	.pipe(uglify())
//	.pipe(sourcemaps.write('./'))
	.pipe(gulp.dest(dist));
}

gulp.task('build', function() {
	bundles.forEach(function(bundle) {
		COMPILE('./src/' + bundle.src, bundle.name, '../scripts/m/diag' + bundle.dist);
	});
});

gulp.task('deploy', function() {
	bundles.forEach(function(bundle) {
		DEPLOY(baseDir + bundle.src, bundle.name, '../scripts/m/diag' + bundle.dist);
	});
});

gulp.task('common', function() {
	[{
		src : '/lib/echarts.js',
		name : 'echarts.bundle.js',
		dist : '/lib'
	}, {
		src : '/lib/dropload.js',
		name : 'dropload.bundle.js',
		dist : '/lib'
	}].forEach(function(bundle) {
		DEPLOY(baseDir + bundle.src, bundle.name, '../scripts/m/diag' + bundle.dist);
	});
});

gulp.task('watch', function() {
	bundles.forEach(function(bundle) {
		COMPILE('./src/' + bundle.src, bundle.name, '../scripts/m/diag' + bundle.dist);
	});
	console.log('----------------------');
	gulp.watch(baseDir + '**', ['build']);
});

gulp.task('watch:deploy', function() {
	bundles.forEach(function(bundle) {
		DEPLOY(baseDir + bundle.src, bundle.name, '../scripts/m/diag' + bundle.dist);
	});
	gulp.watch(baseDir + '**', ['deploy']);
});
