$(document).on("click", ".addUser", function () {
    let user = $(".addUserForm").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/addUser',
        data: user,
        dataType: "json",
        success: function (data) {
            let body = $('#body');
            let s = '';
            for (let j = 0; j < data.roles.length; j++) {
                let r = data.roles;
                s += r[j].role + '</br>';
            }
            body.append($('<tr id="' + data.id + '">').append(
                $('<td>').append($('<span>')).text(data.id),
                $('<td>').append($('<span>')).text(data.firstName),
                $('<td>').append($('<span>')).text(data.lastName),
                $('<td>').append($('<span>')).text(data.email),
                $('<td>').append($('<span>')).append(s),
                $('<td>').append($('<button onclick="openModalUpdate('+data.id+')">').text("Edit").attr({
                        "id": "updateButton",
                        "class": "btn btn-info",
                        "data-toggle": "modal",
                        "th:data-target": "#update" + data.id
                    }).data("user", user),
                ),
                $('<td>').append($('<button onclick="openModal(' + data.id + ')">').text("Delete").attr({
                    "id": "deleteId",
                    "data-userByModalId": data.id,
                    "class": "btn btn-danger deleteButton",
                    "data-toggle": "modal",
                    "data-target": "#delete" + data.id
                }).data("user", user))
            ));
            $('#myTab li:first-child a').tab('show');
        }
    });
});

function openModal(id) {
    let userId = 'id=' + id;
    $.ajax({
        contentType: 'application/json',
        type: 'GET',
        url: '/api/admin/getUserById',
        data: userId,
        dataType: "json",
        success: function (data) {
            $('#delete').modal('toggle');
            $('#firstNameDelete').val(data.firstName);
            $('#lastNameDelete').val(data.lastName);
            $('#emailDelete').val(data.email);
            $('#passwordDelete').val(data.password);
            $('#isUserDeleteButton').data('target', data.id);
        }
    })
}

function deleteUser() {
    let id = 'id=' + $('#isUserDeleteButton').data('target');

    $.ajax({
        type: 'GET',
        url: '/api/admin/deleteUser',
        data: id,
        success: function () {
            $('#' + $('#isUserDeleteButton').data('target')).remove();
            $('#delete').modal('hide');
        }
    })
}

function openModalUpdate(id) {
    let userId = 'id=' + id;

    $.ajax({
        contentType: 'application/json',
        type: 'GET',
        url: '/api/admin/getUserById',
        data: userId,
        dataType: "json",
        success: function (data) {
            $('#update').modal('toggle');
            $('#firstNameUpdate').val(data.firstName);
            $('#lastNameUpdate').val(data.lastName);
            $('#emailUpdate').val(data.email);
            $('#passwordUpdate').val(data.password);
            $('#isUserUpdateButton').data('target', data.id);
        }
    })
}


function updateUser() {
    let user = {
        id: $('#isUserUpdateButton').data('target'),
        firstName: $("#firstNameUpdate").val(),
        lastName: $("#lastNameUpdate").val(),
        email: $("#emailUpdate").val(),
        password: $("#passwordUpdate").val(),
        roles: document.getElementById("selectRoleUpdate").value
    };

    $.ajax({
        type: 'POST',
        url: '/api/admin/updateUser',
        data: user,
        success: function (data) {
            $('#update').modal('hide');
            $('#' + $('#isUserUpdateButton').data('target')).remove();

            let body = $('#body');
            let s = '';
            for (let j = 0; j < data.roles.length; j++) {
                let r = data.roles;
                s += r[j].role + '</br>';
            }

            body.append($('<tr id="' + data.id + '">').append(
                $('<td>').append($('<span>')).text(user.id),
                $('<td>').append($('<span>')).text(user.firstName),
                $('<td>').append($('<span>')).text(user.lastName),
                $('<td>').append($('<span>')).text(user.email),
                $('<td>').append($('<span>')).append(s),
                $('<td>').append($('<button onclick="openModalUpdate('+data.id+')">').text("Edit").attr({
                        "id": "updateButton",
                        "class": "btn btn-info",
                        "data-toggle": "modal",
                        "th:data-target": "#update" + data.id
                    }).data("user", user),
                ),
                $('<td>').append($('<button onclick="openModal(' + data.id + ')">').text("Delete").attr({
                    "id": "deleteId",
                    "data-userByModalId": data.id,
                    "class": "btn btn-danger deleteButton",
                    "data-toggle": "modal",
                    "data-target": "#delete" + data.id
                }).data("user", user))
            ));
        },
        error: function () {
            console.log('Error');
        }
    })
}
