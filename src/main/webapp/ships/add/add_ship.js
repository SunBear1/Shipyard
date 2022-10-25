window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => createInfoAction(event));
    updateHarborCode()
});

function updateHarborCode() {
    const urlParams = new URLSearchParams(window.location.search);
    const element = document.getElementById("harbor_code");
    clearElementChildren(element);
    element.appendChild(document.createTextNode(urlParams.get('code')));
}


/**
 * Action event handled for updating character info.
 * @param {Event} event dom event
 */
function createInfoAction(event) {
    event.preventDefault();
    const urlParams = new URLSearchParams(window.location.search);
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            alert("New ship with name " + document.getElementById('name').value + " was created in harbor " + urlParams.get('code'));
        }
    };
    xhttp.open('POST', getContextRoot() + '/api/ships', true);
    const request = {
        'name': document.getElementById('name').value,
        'cost': document.getElementById('cost').value,
        'completionDate': (document.getElementById('completionDate').value),
        'harbor_code': urlParams.get('code')
    };

    xhttp.send(JSON.stringify(request));
}

