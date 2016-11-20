/**
 * 
 */
window.onload = function() {
	refreshBoard();
}

function startGame()
{
	if($("#gameboard").hasClass("disableddiv"))
		$("#gameboard").removeClass("disableddiv");
	document.getElementById("skipButton").disabled = false;
	document.getElementById("deleteButton").disabled = false;
}

function endGame()
{
	$("#gameboard").addClass("disableddiv");
	document.getElementById("skipButton").disabled = true;
	document.getElementById("deleteButton").disabled = true;
}

function refreshBoard()
{
	startGame();
	clearCanvas('webgoCanvas');
	initCanvas('webgoCanvas');
	getTurn();
	getStones();
}

function initCanvas(canvas) {
	var canvas = document.getElementById(canvas);
	canvas.addEventListener("click", canvasClick, false);
    var context = canvas.getContext('2d');
    context.beginPath();
    context.strokeStyle = 'grey';
    var border = 30;
    
    for (var y = border; y <= 810+border; y += 45) {
		context.moveTo(border, y);
		context.lineTo(810+border, y);
	}
	
	for (var x = border; x <= 810+border; x += 45) {
		context.moveTo(x, border);
		context.lineTo(x, 810+border);
	}
	
    context.stroke();
    context.closePath();
}

function clearCanvas(canvas)
{
	var canvas = document.getElementById(canvas);
    var context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function drawStone(canvas, x, y, color)
{
	var canvas = document.getElementById(canvas);
    var context = canvas.getContext('2d');
    var radius = 20;

    context.beginPath();
    context.arc(x, y, radius, 0, 2 * Math.PI, false);
    if(color == 0)
    	context.fillStyle = 'white';
    else
    	context.fillStyle = 'black';
    context.fill();
    context.lineWidth = 2;
    context.strokeStyle = 'black';
    context.stroke();	
    context.closePath();
}

function restart()
{	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/reset",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	refreshBoard();
        },
        error: function (e) {
            alert('An error occured.');
        }
	});		
	
	$('#restartButton').blur();
}

function skip()
{
	addSkip();
	var i = getSkipCount();
	if(i == 2)
	{
		// Game end
		var blacks = getBlacks();
		var whites = getWhites();
		var text = "Game ended! Winner is ";
		
		if(blacks > whites)
		{
			text += "BLACK with result " + blacks + " - " + whites + ".";
		}
		else if(whites > blacks)
		{
			text += "WHITE with result " + whites + " - " + blacks + ".";
		}
		else
		{
			text = "The game ended in a draw.";
		}
			
		endGame();
		alert(text);
	}
	else
	{
		if($('#turnindicator').hasClass('black'))
			setTurn(0);
		else
			setTurn(1);
	}
	
	$('#skipButton').blur();
}

function deletemode()
{
	if($('#deleteButton').hasClass('active'))
	{
		$('#deleteButton').removeClass('active');
	}
	else
	{
		$('#deleteButton').addClass('active');
	}
	$('#deleteButton').blur();
}

function getStones()
{
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/stones",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	$.each(data, function() {
        		var x;
        		var y;
        		var color;
				$.each(this, function(propName, propValue) {
					if(propName == 'x')
						x = propValue;
					else if(propName == 'y')
						y = propValue;
					else if(propName == 'color')
						color = propValue
				});
				drawStone('webgoCanvas', x, y, color);
    		});
        },
        error: function (e) {
            alert('An error occured.');
        }
	});		
}

function canvasClick(e)
{
	if($('#deleteButton').hasClass('active'))
		removeStone(e);
	else
		setStone(e);
}

function removeStone(e)
{
	var position = getPosition(e);
	var stonePos = calculateStonePos(position)
	
	var data = {}
	data["x"] = stonePos[0];
	data["y"] = stonePos[1];
	
	$.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/remove",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	if(data == true)
        		refreshBoard();
        },
        error: function (e) {
        }
	});		
}

function setStone(e)
{
	var position = getPosition(e);
	var stonePos = calculateStonePos(position)
	var turn;
	
	if($('#turnindicator').hasClass('black'))
		turn = 1;
	else
		turn = 0;
	
	var data = {}
	data["x"] = stonePos[0];
	data["y"] = stonePos[1];
	data["color"] = turn;
	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/add",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	if(data == true)
        	{
            	getStones();
            	if(turn == 1)
            		setTurn(0);
            	else
            		setTurn(1);
        	}
        	else
        	{
        		alert('Illigal move. Try again.');
        	}
        	resetSkipCount();
        },
        error: function (e) {
            alert('Illigal move. Try again.');
        }
	});		
}

function calculateStonePos(position)
{	
	var x = getClosestPosition(position[0]);
	var y = getClosestPosition(position[1]);
		
	return [x,y];
}

function getClosestPosition(a)
{
	var closest;
	var diff = 870;	// board width
	for(x = 30; x <= 18*45+30; x += 45)
	{
		var z = a-x;
		if(Math.abs(z) <= diff)
		{
			diff = z;
			closest = x;
		}
		else
		{
			break;
		}
	}
	return closest;
}

function getPosition(event)
{
	var x;
	var y;
	var canvas = document.getElementById("webgoCanvas");
	
	if (event.x != undefined && event.y != undefined)
	{
		x = event.x;
		y = event.y;
	}
	else // Firefox method to get the position
	{
		x = event.clientX + document.body.scrollLeft + 
			document.documentElement.scrollLeft;
		y = event.clientY + document.body.scrollTop +
	      	document.documentElement.scrollTop;
	}
	
	x -= canvas.offsetLeft;
	y -= canvas.offsetTop;
	
	return [x, y]
}

function getTurn()
{
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/getturn",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	if(data == 0 && $('#turnindicator').hasClass('black'))
        		$('#turnindicator').removeClass('black');
        	else
        		$('#turnindicator').addClass('black');
        },
        error: function (e) {
            
        }
	});
}

function setTurn(turn)
{
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/setturn",
        data: JSON.stringify(turn),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	if(turn == 0 && $('#turnindicator').hasClass('black'))
        		$('#turnindicator').removeClass('black');
        	else
        		$('#turnindicator').addClass('black');
        },
        error: function (e) {
            
        }
	});			
}

function addSkip()
{
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/skips/add",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {

        },
        error: function (e) {
        }
	});			
}

function getSkipCount()
{
	var count = 0;
	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/skips",
        async: false,
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	count = data;
        },
        error: function (e) {
            
        }
	});
	
	return count;
}

function resetSkipCount()
{
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/skips/reset",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        },
        error: function (e) {
        }
	});			
}

function getWhites()
{
	var count = 0;
	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/whites",
        async: false,
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	count = data;
        },
        error: function (e) {
            
        }
	});
	
	return count;
}

function getBlacks()
{
	var count = 0;
	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/blacks",
        async: false,
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	count = data;
        },
        error: function (e) {
            
        }
	});
	
	return count;
}


