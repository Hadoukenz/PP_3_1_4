let currentUser = document.getElementById("currentUser");
let navbar = document.getElementById("header");


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