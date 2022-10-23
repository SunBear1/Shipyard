window.addEventListener('load', () => {
    loadHarbors();
});

/**
 * Create new table cell with button with assigned action.
 * @param {string} text text to be displayed on button
 * @param {function} action function to be executed on button click
 * @returns {HTMLTableDataCellElement} table cell with action button
 */
function createButtonCell(text, action) {
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    button.classList.add('ui-control', 'ui-button');
    td.appendChild(button);
    button.addEventListener('click', action);
    return td;
}

/**
 * Create new table cell with hyperlink.
 * @param {string} text text to be displayed on link
 * @param {string} url link url
 */
function createLinkCell(text, url) {
    const td = document.createElement('td');
    const a = document.createElement('a');
    a.appendChild(document.createTextNode(text));
    a.href = url;
    td.appendChild(a);
    return td;
}

/**
 * Delete selected character and refresh table.
 * @param {number} character character's id
 */
function deleteCharacter(harbor) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 204) {
            loadHarbors()
        }
    };
    xhttp.open("DELETE", getContextRoot() + '/api/harbor/' + harbor, true);
    xhttp.send();
}

/**
 * Create row for single character.
 * @param {{name: string, code: string}} harbor
 * @returns {HTMLTableRowElement} row with character data
 */
function createHarborRow(harbor) {
    const tr = document.createElement('tr');

    const name = document.createElement('td');
    name.appendChild(document.createTextNode(harbor.name));
    tr.appendChild(name);

    tr.appendChild(createLinkCell('view', '../view/view_harbor.html?code=' + harbor.code));

    tr.appendChild(createButtonCell('delete', () => {
        deleteCharacter(harbor.code);
    }));

    return tr;
}

/**
 * Fetches currently logged user's characters and displays them in table.
 */
function loadHarbors() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            let tbody = document.getElementById('harborsTableBody');
            clearElementChildren(tbody);
            response.harbors.forEach(character => {
                tbody.appendChild(createHarborRow(character));
            })
        }
    };
    xhttp.open("GET", getContextRoot() + '/api/harbors', true);
    xhttp.send();
}
