function validateDate(field){
	var dateStr = field.value;
	var objRegExp  = /[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-9][0-9]/;
	var isValid = objRegExp.test(dateStr);
	if(!isValid){
		field.style.backgroundColor = 'red';
	} else {
		field.style.backgroundColor = 'white';
	}
}
	
function submitForm(event){
	document.getElementById('event').value = event;
	document.forms[0].submit();
}

function submitFormWithConfirmation(event){
	document.getElementById('event').value = event;
	var confirmed = confirm("Czy jesteś pewien?");
	if (confirmed){
		document.forms[0].submit();
	}
}

function submitFormWithParam(event,eventParam){
	document.getElementById('event').value = event;
	document.getElementById('eventParam').value = eventParam;
	document.forms[0].submit();
}

function submitFormWithParamAndConfirmation(event,eventParam){
	document.getElementById('event').value = event;
	document.getElementById('eventParam').value = eventParam;
	var confirmed = confirm("Czy na pewno chcesz usunąć rekord?");
	if (confirmed){
		document.forms[0].submit();
	}
}