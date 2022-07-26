let saveUserBtn = document.querySelector('#saveEditedUser');

// PASS DATA TO DELETE MODAL

function editModal(id) {
    fetch(baseUrl + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('userId').value = user.id;
            document.getElementById('firstName').value = user.name;
            document.getElementById('lastName').value = user.lastname;
            document.getElementById('age').value = user.age;
            document.getElementById('email').value = user.email;
            document.getElementById('password').value = null;
        })
    });
}


// DELETE BUTTON BEHAVIOR CHANGE

saveUserBtn.addEventListener('click', event => {
    event.preventDefault()

    const editedUser = {
        id: document.querySelector('#userId').value,
        name: document.querySelector('#firstName').value,
        lastname: document.querySelector('#lastName').value,
        age: document.querySelector('#age').value,
        email: document.querySelector('#email').value,
        password: document.querySelector('#password').value,
        roles: getRolesFromForm(Array.from(document.getElementById("roleSelect").selectedOptions).map(role => role.value))
    }

    fetch(baseUrl,
        {
            method: 'PATCH', body: JSON.stringify(editedUser),
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json;charset=UTF-8'
            }
        })
        .then(() => {
            document.getElementById("closeEditModal").click();
            updateTable();
        })
})