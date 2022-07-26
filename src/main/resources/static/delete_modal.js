let deleteUserBtn = document.querySelector('#deleteUser');

// PASS DATA TO EDIT MODAL

function deleteModal(id) {
    fetch(baseUrl + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('deleteUserId').value = user.id;
            document.getElementById('deleteFirstName').value = user.name;
            document.getElementById('deleteLastName').value = user.lastname;
            document.getElementById('deleteAge').value = user.age;
            document.getElementById('deleteEmail').value = user.email;
            document.getElementById('deleteRoles').value = user.roles;
        })
    });
}


// EDIT BUTTON BEHAVIOR CHANGE

deleteUserBtn.addEventListener('click', event => {
    event.preventDefault()

    fetch(baseUrl + document.getElementById('deleteUserId').value,
        {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json;charset=UTF-8'
            }
        })
        .then(() => {
            document.getElementById("closeDeleteModal").click();
            updateTable();
        })
})