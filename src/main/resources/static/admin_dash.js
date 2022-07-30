const baseUrl = "http://localhost:8080/fetch_adm/";
const authUrl = "http://localhost:8080/fetch_adm/authed_user";

const table = document.querySelector('tbody');
const newUserForm = document.querySelector('#newUserForm')
const currentUser = document.getElementById("currentUser");
const navbar = document.getElementById("header");


// FETCHING
const getUsers = async (url) => {
    return await (await fetch(url)).json()
}


const clearUsersTable = () => {
    document.querySelectorAll("#usersTable tbody tr").forEach(tr => tr.remove())
}


// TABLE FUNCTIONALITY
const updateTable = async () => {

    await getUsers(baseUrl).then((users) => {
        let temp = "";

        if (users.length > 0) {
            for (let i = 0; i < users.length; i++) {
                temp += `<tr id=${users[i]['id']}>
                        <td>${users[i]['id']}</td>
                        <td>${users[i]['name']}</td>
                        <td>${users[i]['lastname']}</td>
                        <td>${users[i]['age']}</td>
                        <td>${users[i]['email']}</td>
                        <td>${users[i]['roles'].map(role => role.name).join(', ')}</td>
                        <td>
                            <button class="btn btn-info" type="button"
                            data-bs-toggle="modal" data-bs-target="#editModal"
                            onclick="editModal(${users[i].id})">Edit</button>
                        </td>
                        <td>
                            <button class="btn btn-danger" type="button"
                            data-bs-toggle="modal" data-bs-target="#deleteModal"
                            onclick="deleteModal(${users[i].id})">Delete</button>
                        </td>
                     </tr>`
            }
            clearUsersTable()
            table.innerHTML = temp;
        }
    })
}

updateTable()


// NEW USER FORM

function clearNewUserForm() {
    document.querySelector('#newUserFirstName').value = null;
    document.querySelector('#newUserLastName').value = null;
    document.querySelector('#newUserAge').value = null;
    document.querySelector('#newUserEmail').value = null;
    document.querySelector('#newUserPassword').value = null;
    document.querySelector('#newUserRoles').value = null;
}

function getRolesFromForm(addedRoles) {
    let roles = [];
    if (addedRoles.indexOf("ADMIN") >= 0) {
        roles.push({ "id": 1 });
    }
    if (addedRoles.indexOf("USER") >= 0) {
        roles.push({ "id": 2 });
    }
    return roles;
}

newUserForm.addEventListener('submit', event => {
    event.preventDefault()

    const newUser = {
        name: document.querySelector('#newUserFirstName').value,
        lastname: document.querySelector('#newUserLastName').value,
        age: document.querySelector('#newUserAge').value,
        email: document.querySelector('#newUserEmail').value,
        password: document.querySelector('#newUserPassword').value,
        roles: getRolesFromForm(Array.from(document.getElementById("newUserRoles").selectedOptions).map(role => role.value))
    }

    fetch(baseUrl,
        {
            method: "POST", body: JSON.stringify(newUser),
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json;charset=UTF-8'
            }
        })
        .then(() => {
            // clearUsersTable();
            updateTable();
            clearNewUserForm();

            document.querySelector('#nav-users-tab').classList.value = 'nav-link active'
            document.querySelector('#nav-newUser-tab').classList.value = 'nav-link'

            document.querySelector('#nav-users').classList.value = 'tab-pane fade show active rounded border bg-white'
            document.querySelector('#nav-newUser').classList.value = 'tab-pane fade bg-white'
        })
})


// CURRENT USER

function getCurrentUser() {
    fetch(authUrl)
        .then((res) => res.json())
        .then((user) => {
            let temp = '';
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name).join(', ')}</td>
            </tr>`;
            currentUser.innerHTML = temp;
        });
}

getCurrentUser()


// HEADER

function userPanel() {
    fetch(authUrl)
        .then((res) => res.json())
        .then((u) => {
            navbar.innerHTML = `<h5>${u.email} with roles: ${u.roles.map(role => role.name).join(', ')}</h5>`
        });
}

userPanel()