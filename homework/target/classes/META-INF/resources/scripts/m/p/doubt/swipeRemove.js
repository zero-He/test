//移动删除
SwipeRemove = {
    init: function(fun){
    	this.fun = fun;
        var self = this;
        $('.c-doubter__wrap .c-doubter').unbind();
        $('.c-doubter__wrap .c-doubter__remove').unbind();
        $('.c-doubter__wrap').each(function(item){
            self.bindEvents(item);
        })
        $('.c-doubter__remove').click(function(){
        	var _this = this;
        	self.fun($(this).data("i"),function(){
        		$(_this).parent().remove();
        	});
        });
    },
    bindEvents: function(el){
        var self = this;
        var startX, startY, endX, endY;
        var deltaX, dir;
        var doubter = $('.c-doubter');

        doubter.on('touchstart', function(e){
            startX = e.touches[0].pageX;
            startY = e.touches[0].pageY;
            if( this.classList.contains('c-doubter--on') ){
                this.classList.remove('c-doubter--on');
                this.parentNode.querySelector('.c-doubter__remove').classList.remove('c-doubter__remove--on');
            }
        });

        doubter.on('touchmove', function(e){
            endX = e.touches[0].pageX;
            endY = e.touches[0].pageY;
            dir = self.dirCal(startX, startY, endX, endY);
            deltaX = endX - startX;
            if( dir === 'left' &&  Math.abs(deltaX) >= 30 ){
                if( deltaX <= 0 && deltaX >= -130 ){
                    this.style.webkitTransform = 'translate3d('+ deltaX +'px, 0, 0)';
                    this.style.transform = 'translate3d('+ deltaX +'px, 0, 0)';
                }
            }
        })

        doubter.on('touchend', function(e){
            dir = self.dirCal(startX, startY, endX, endY);
            deltaX = endX - startX;
            if( dir === 'left' &&  Math.abs(deltaX) >= 30 ){
                this.classList.add('c-doubter--on');
                this.parentNode.querySelector('.c-doubter__remove').classList.add('c-doubter__remove--on');
                this.style.webkitTransform = 'translate3d(0, 0, 0)';
                this.style.transform = 'translate3d(0, 0, 0)';
            }else{
            	this.style.webkitTransform = 'translate3d(0, 0, 0)';
                this.style.transform = 'translate3d(0, 0, 0)';
            }
            startX = null;
            startY = null;
            endX = null;
            endY = null;
            deltaX = null;
        })
    },
    dirCal: function(x1, y1, x2, y2){
        return ( Math.abs(x1-x2) > Math.abs(y1-y2) ) ? ( x1-x2 > 0 ? 'left' : 'right' ) : ( y1-y2 > 0 ? 'up' : 'down' );
    }
}