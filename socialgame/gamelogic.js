var npanels = 22;
var onmenu;

// Show element by ID.
function show(elem){
	document.getElementById(elem).style.display = 'inline';
}

// Hide element by ID.
function hide(elem){
	document.getElementById(elem).style.display = 'none';
}

function chg(num){
	var ma = "g" + onmenu;
	var mb = "g" + num;
	hide(ma);
	show(mb);
	onmenu=num;
}

// Called when Play button is pressed.
function start(){
	hide('welcome');
	onmenu = 1;
	show('g1');
}

function next(){
	var sele = "s" + onmenu;
	var n = document.getElementById(sele).selectedIndex;
	var failed = false;
	if(onmenu != 7 && onmenu != 8 && onmenu != 11 && onmenu != 12 && onmenu != 18 && onmenu != 19 && onmenu != 20 && onmenu != 21 && onmenu != 22){
		if(n == 0){
			alert("Please choose an option from the list.");
			failed = true;
		}
	}
	if(failed) return;
	switch(onmenu){
		case 1:
			if(n==1)
				chg(2);
			else
				chg(10);
			break;
		case 2:
			if(n==1)
				chg(3);
			break;
		case 3:
			if(n==1)
				chg(4);
			else
				chg(5);
			break;
		case 4:
			if(n==1)
				chg(15);
			else
				chg(16);
			break;
		case 5:
			if(n==1)
				chg(6);
			else
				chg(7);
			break;
		case 6:
			if(n==1)
				chg(8);
			else
				chg(9);
			break;
		case 7:
			exit();
			break;
		case 8:
			exit();
			break;
		case 9:
			if(n==1)
				chg(20);
			break;
		case 10:
			if(n==1)
				chg(11);
			else
				chg(12);
			break;
		case 11:
			if(n==1)
				chg(13);
			else
				chg(14);
			break;
		case 12:
			exit();
			break;
		case 13:
			if(n==1)
				chg(19);
			break;
		case 14:
			if(n==1)
				chg(19);
			break;
		case 15:
			if(n==1)
				chg(8);
			else
				chg(9);
			break;
		case 16:
			if(n==1)
				chg(17);
			else
				chg(18);
			break;
		case 17:
			if(n==1)
				chg(21);
			else
				chg(22);
			break;
		case 18:
			exit();
			break;
		case 19:
			exit();
			break;
		case 20:
			exit();
			break;
		case 21:
			exit();
			break;
		case 22:
			exit();
			break;
	}
}

// Go back to the main menu.
function exit(){
	hide('g1');
	hide('g2');
	hide('g3');
	hide('g4');
	hide('g5');
	hide('g6');
	hide('g7');
	hide('g8');
	hide('g9');
	hide('g10');
	hide('g11');
	hide('g12');
	hide('g13');
	hide('g14');
	hide('g15');
	hide('g16');
	hide('g17');
	hide('g18');
	hide('g19');
	hide('g20');
	hide('g21');
	hide('g22');
	show('welcome');
}


