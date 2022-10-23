window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => createInfoAction(event));
    updateHarborCode()
});

function updateHarborCode(){
    const urlParams = new URLSearchParams(window.location.search);
    const element = document.getElementById("harbor_code");
    clearElementChildren(element);
    element.appendChild(document.createTextNode(urlParams.get('code')));
}

/**
 * Fetches currently logged user's characters and updates edit form.
 * @param {number} id character's id
 */
function loadShip(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getContextRoot() + '/api/ship/' + id, true);
    xhttp.send();
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
            alert("New ship has been created");
        }
    };
    xhttp.open('POST', getContextRoot() + '/api/ships', true);
    const request = {
        'name': document.getElementById('name').value,
        'cost': document.getElementById('cost').value,
        'completionDate': (document.getElementById('completionDate').value),
        'harbor_code':  urlParams.get('code')
    };

    xhttp.send(JSON.stringify(request));
}

function updateElementText(element, text) {
    clearElementChildren(element);
    element.appendChild(document.createTextNode(text));
}
