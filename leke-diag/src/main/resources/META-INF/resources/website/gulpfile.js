var gulp = require('gulp');
var wrap = require('gulp-wrap');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var sourcemaps = require('gulp-sourcemaps');

var babelConfig = {
	presets : ["es2015", "react", "stage-0"],
	plugins: ["transform-decorators-legacy"]
};

var uglifyConfig = {
	mangle : {
		except : ['require', 'exports', 'module']
	}
};

var wrapTemplate = 'define(function (require, exports, module) {\n<%= contents %>\n});';

function COMPILE(jsxs, dist) {
	gulp.src(jsxs)
	.pipe(babel(babelConfig))
	.pipe(wrap(wrapTemplate))
	.pipe(gulp.dest(dist));
}

function DEPLOY(jsxs, dist) {
	gulp.src(jsxs)
	.pipe(babel(babelConfig))
	.pipe(uglify(uglifyConfig))
	.pipe(wrap(wrapTemplate))
	.pipe(gulp.dest(dist));
}

function DEPLOY_MAP(jsxs, dist) {
	gulp.src(jsxs)
	.pipe(babel(babelConfig))
	.pipe(sourcemaps.init({
		loadMaps : true
	}))
	.pipe(uglify(uglifyConfig))
	.pipe(wrap(wrapTemplate))
	.pipe(sourcemaps.write('./'))
	.pipe(gulp.dest(dist));
}

var src = './src/**';
var dist = '../scripts/diag/report';

var src_old = './report/**';

gulp.task('build', function() {
	COMPILE(src, dist);
	COMPILE(src_old, dist);
});

gulp.task('deploy', function() {
	DEPLOY(src, dist);
	DEPLOY(src_old, dist);
});

gulp.task('watch', function() {
	gulp.watch(src, ['build']);
});

gulp.task('watch:deploy', function() {
	gulp.watch(src, ['deploy']);
});
