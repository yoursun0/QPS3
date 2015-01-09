var dwin;
function new_window(dest) {
	var durl
	var bscrollbar
	var bmenubar
	var bresizeable
	var btoolbar
	var woption
	durl = dest;
	bscrollbars = 1;	
	bmenubar = 0;
	bresizable = 1;
	btoolbar = 0;
	btitlebar = 0;
	woption = "scrollbars = " + bscrollbars;
	woption = woption + ", menubar = " + bmenubar; 
	woption = woption + ", resizable = " + bresizable;
	woption = woption + ", toolbar = " + btoolbar;
	woption = woption + ", titlebar = " + btitlebar;
	dwin = window.open(durl, "nw", woption);
}



