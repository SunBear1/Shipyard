window.addEventListener('load', () => {
    const urlParams = new URLSearchParams(window.location.search);
    createAddShipButton(urlParams.get('code'))
    loadHarbor(urlParams.get('code'));
});


function loadHarbor(code) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const response = JSON.parse(this.responseText);
            let tbody = document.getElementById('shipsTableBody');
            clearElementChildren(tbody);
            response.ships.forEach(ship => {
                tbody.appendChild(createShipRow(ship));
            })
            for (const [key, value] of Object.entries(response)) {
                const element = document.getElementById(key);
                if (element) {
                    updateElementText(element, value);
                }
            }
        }
    };
    xhttp.open("GET", getContextRoot() + '/api/harbor/' + code, true);
    xhttp.send();
}


/**
 * Removes all children of the provided element and creates new text node inside it.
 * @param {HTMLElement} element parent element
 * @param {string} text text to be displayed
 */
function updateElementText(element, text) {
    clearElementChildren(element);
    element.appendChild(document.createTextNode(text));
}

function createShipRow(ship) {
    const tr = document.createElement('tr');

    const name = document.createElement('td');
    name.appendChild(document.createTextNode(ship.name));
    tr.appendChild(name);

    tr.appendChild(createLinkCell('view', '../../ships/view/view_ship.html?id=' + ship.id));

    tr.appendChild(createLinkCell('edit', '../../ships/edit/edit_ship.html?id=' + ship.id));

    tr.appendChild(createButtonCell('delete', () => {
        deleteShip(ship.id);
    }));

    return tr;
}

function createLinkCell(text, url) {
    const td = document.createElement('td');
    const a = document.createElement('a');
    a.appendChild(document.createTextNode(text));
    a.href = url;
    td.appendChild(a);
    return td;
}


function createButtonCell(text, action) {
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    button.classList.add('ui-control', 'ui-button');
    td.appendChild(button);
    button.addEventListener('click', action);
    return td;
}


function deleteShip(ship_id) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 204) {
            const urlParams = new URLSearchParams(window.location.search);
            createAddShipButton(urlParams.get('code'))
            loadHarbor(urlParams.get('code'));
        }
    };
    xhttp.open("DELETE", getContextRoot() + '/api/ship/' + ship_id, true);
    xhttp.send();
}

function createAddShipButton(code) {
    const urlParams = new URLSearchParams(window.location.search);
    loadHarbor(urlParams.get('code'));

    let button_div = document.getElementById("add_button")
    const button = document.createElement('button');
    button.appendChild(document.createTextNode("Create new ship"));
    button.classList.add('ui-control', 'ui-button');

    button.onclick = function () {
        window.open('../../ships/add/add_ship.html?code=' + code, "_self");
    }
    button_div.appendChild(button);
}
