var gulp = require('gulp');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');

function COMPILE(jsxs, dist) {
	gulp.src(jsxs).pipe(babel({
		presets : ['es2015', 'react']
	})).pipe(gulp.dest(dist));
}

function DEPLOY(jsxs, dist) {
	gulp.src(jsxs).pipe(babel({
		presets : ['es2015', 'react']
	})).pipe(uglify({
		mangle : {
			except : ['require', 'exports', 'module']
		}
	})).pipe(gulp.dest(dist));
}

var jsxs = './jsxs/**';
var dist = './scripts/homework';

gulp.task('build', function() {
	COMPILE(jsxs, dist);
});

gulp.task('watch', function() {
	gulp.watch(jsxs, ['build']);
});

gulp.task('deploy', function() {
	DEPLOY(jsxs, dist);
});

gulp.task('build:assign', function() {
	COMPILE('./jsxs/assign/**', './scripts/homework/assign');
});

gulp.task('build:sheet', function() {
	COMPILE('./jsxs/sheet/**', './scripts/homework/sheet');
});
