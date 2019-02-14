var dialogInstace , onMoveStartId , mousePos = {x:0,y:0};	//	用于记录当前可拖拽的对象
	
	// var zIndex = 9000;

	//	获取元素对象	
	function g(id){return document.getElementById(id);}
	
	function showDialog(id,maskId){
		g(id).style.display = 'block';
		g(maskId).style.display = 'block';
		autoCenter( g(id) );
		fillToBody( g(maskId) );
	}

	//	自动居中元素（el = Element）
	function autoCenter( el ){
		var bodyW = document.documentElement.clientWidth;
		var bodyH = document.documentElement.clientHeight;

		var elW = el.offsetWidth;
		var elH = el.offsetHeight;

		el.style.left = (bodyW-elW)/2 + 'px';
		el.style.top = (bodyH-elH)/2 + 'px';
		
	}

	//	自动扩展元素到全部显示区域
	function fillToBody( el ){
		el.style.width  = document.documentElement.clientWidth  +'px';
		el.style.height = document.documentElement.clientHeight + 'px';
	}

	//	Dialog实例化的方法
	function Dialog( dragId , moveId ){

		var instace = {} ;

		instace.dragElement  = g(dragId);	//	允许执行 拖拽操作 的元素
		instace.moveElement  = g(moveId);	//	拖拽操作时，移动的元素

		instace.mouseOffsetLeft = 0;			//	拖拽操作时，移动元素的起始 X 点
		instace.mouseOffsetTop = 0;			//	拖拽操作时，移动元素的起始 Y 点

		instace.dragElement.addEventListener('mousedown',function(e){

			var e = e || window.event;

			dialogInstace = instace;
			instace.mouseOffsetLeft = e.pageX - instace.moveElement.offsetLeft ;
			instace.mouseOffsetTop  = e.pageY - instace.moveElement.offsetTop ;
			
			onMoveStartId = setInterval(onMoveStart,10);
			return false;
			// instace.moveElement.style.zIndex = zIndex ++;
		})

		return instace;
	}

	//	在页面中侦听 鼠标弹起事件
	document.onmouseup = function(e){
		dialogInstace = false;
		clearInterval(onMoveStartId);
	}
	document.onmousemove = function( e ){
		var e = window.event || e;
		mousePos.x = e.clientX;
		mousePos.y = e.clientY;
		

		e.stopPropagation && e.stopPropagation();
		e.cancelBubble = true;
		e = this.originalEvent;
        e && ( e.preventDefault ? e.preventDefault() : e.returnValue = false );

        document.body.style.MozUserSelect = 'none';
	}	

	function onMoveStart(){


		var instace = dialogInstace;
	    if (instace) {
	    	
	    	var maxX = document.documentElement.clientWidth -  instace.moveElement.offsetWidth;
	    	var maxY = document.documentElement.clientHeight - instace.moveElement.offsetHeight ;

			instace.moveElement.style.left = Math.min( Math.max( ( mousePos.x - instace.mouseOffsetLeft) , 0 ) , maxX) + "px";
			instace.moveElement.style.top  = Math.min( Math.max( ( mousePos.y - instace.mouseOffsetTop ) , 0 ) , maxY) + "px";

	    }

	}