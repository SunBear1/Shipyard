window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    loadShip(getShipId());
});

/**
 *
 * @returns {number} character's ide from request path
 */
function getShipId() {
    const urlParams = new URLSearchParams(window.location.search);
    return parseInt(urlParams.get('id'));
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
function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            loadShip(getShipId());
        }
    };
    xhttp.open('PUT', getContextRoot() + '/api/ship/' + getShipId(), true);

    const request = {
        'name': document.getElementById('name').value,
        'cost': document.getElementById('cost').value,
        'completionDate': (document.getElementById('completionDate').value)
    };

    xhttp.send(JSON.stringify(request));
}
