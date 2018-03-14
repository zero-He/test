(function() {
	var Utils = {
		getPosition : function(ev) {
			var x, y;
			if (this.isTouch(ev)) {
				rect = ev.target.getBoundingClientRect();

				x = ev.touches[0].pageX - rect.left;
				y = ev.touches[0].pageY - rect.top;
			} else {
				x = ev.pageX - ev.target.offsetLeft;
				y = ev.pageY - ev.target.offsetTop;
			}

			return {
				x : x,
				y : y
			};
		},
		isTouch : function(ev) {
			var type = ev.type;
			if (type.indexOf('touch') >= 0) {
				return true;
			} else {
				return false;
			}
		}
	};

	function Handboard(options) {
		if (!options) {
			return;
		}

		this.options = {
			canvas : options.canvas || null,
			width : options.width || 300,
			height : options.height || 300,
			lineWidth : options.lineWidth || 1,
			lineColor : options.lineColor || '#000'
		};

		var that = this;
		this.canvas = this.options.canvas;
		this.canvas.height = this.options.height;
		this.canvas.width = this.options.width;

		this.board = this.canvas.getContext('2d');
		this.board.lineWidth = this.options.lineWidth;
		this.board.strokeStyle = this.options.lineColor;

		this.mousePress = false;
		this.last = {};
		this.isEmpty = true;

		/* bind event */
		// Mobile
		this.canvas.addEventListener('touchstart', this.handleStart.bind(this),
				false);
		this.canvas.addEventListener('touchmove', function(ev) {
			that.handleDrawing.call(that, ev);
		}, false);
		this.canvas.addEventListener('touchend', function(ev) {
			that.handleEnd.call(that, ev);
		}, false);
	}

	Handboard.prototype.handleStart = function() {
		this.mousePress = true;
	};

	Handboard.prototype.handleDrawing = function(ev) {
		if (!this.mousePress) {
			return;
		}
		this.isEmpty = false;
		var xy = Utils.getPosition(ev);
		if (this.last) {
			this.board.beginPath();
			this.board.moveTo(this.last.x, this.last.y);
			this.board.lineTo(xy.x, xy.y);
			this.board.stroke();
		}
		this.last = xy;

		ev.preventDefault();
	};

	Handboard.prototype.handleEnd = function(ev) {
		this.mousePress = false;
		this.last = {};

		ev.preventDefault();
	};

	Handboard.prototype.clean = function() {
		this.isEmpty = true;
		this.board.clearRect(0, 0, this.canvas.width, this.canvas.height);
	};

	Handboard.prototype.getBase64 = function() {
		if (!this.isEmpty) {
			var dataUrl = this.canvas.toDataURL();
			return dataUrl;
		} else {
			return;
		}
	};

	window.Handboard = Handboard;
})();