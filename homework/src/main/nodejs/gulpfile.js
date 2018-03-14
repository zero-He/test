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
var distDir = './dist/';
var testDir = './test/';
var indexName = '/index.js';

function COMPILE(entries, bundle, dist) {
	browserify({
		entries : entries,
		transform : [babelify]
	}).bundle()
	.pipe(source(bundle))
	.pipe(buffer())
	.pipe(gulp.dest(dist));
}

function DEPLOY(entries, bundle, dist) {
	browserify({
		entries : entries,
		transform : [babelify]
	}).bundle()
	.pipe(source(bundle))
	.pipe(buffer())
	.pipe(uglify())
	.pipe(gulp.dest(dist));
}

function test(name) {
	return () => {
		COMPILE(baseDir + name + indexName, name + '.bundle.js', testDir + name + '/');
	};
}
gulp.task('test:demo', test('demo'));
gulp.task('test:pm', test('pm'));
gulp.task('test:list', test('list'));
gulp.task('test:view', test('view'));
gulp.task('test:work', test('work'));
gulp.task('test:tealist', test('tealist'));
gulp.task('test', ['test:pm', 'test:list', 'test:view', 'test:work', 'test:tealist']);

function build(src, name) {
	return () => {
		COMPILE(baseDir + src + indexName, name + '.bundle.js', distDir);
	};
}
gulp.task('build:pm', build('pm', 'parent-manager'));
gulp.task('build:list', build('list', 'person-worklist'));
gulp.task('build:view', build('view', 'person-viewwork'));
gulp.task('build:work', build('work', 'student-dowork'));
gulp.task('build:tealist', build('tealist', 'teacher-worklist'));
gulp.task('build', ['build:pm', 'build:list', 'build:view', 'build:work', 'build:tealist']);

function deploy(src, name) {
	process.env.NODE_ENV = 'production';
	return () => {
		DEPLOY(baseDir + src + indexName, name + '.bundle.js', distDir);
	};
}
gulp.task('deploy:pm', deploy('pm', 'parent-manager'));
gulp.task('deploy:list', deploy('list', 'person-worklist'));
gulp.task('deploy:view', deploy('view', 'person-viewwork'));
gulp.task('deploy:work', deploy('work', 'student-dowork'));
gulp.task('deploy:tealist', deploy('tealist', 'teacher-worklist'));
gulp.task('deploy', ['deploy:pm', 'deploy:list', 'deploy:view', 'deploy:work', 'deploy:tealist']);
