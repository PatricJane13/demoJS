function updatePage() {
    $.ajax({
        contentType: 'application/json',
        type: 'GET',
        url: '/api/admin/all',
        dataType: "json",
        success: function (data) {
            let body = $('#body');

            for (let i = 0; i < data.length; i++) {
                let s = '';
                for (let j = 0; j < data[i].roles.length; j++) {
                    let r = data[i].roles;
                    s += r[j].role + '</br>';
                }

                body.append('<tr id="' + data[i].id + '">' +
                    '<td>' + data[i].id + '</td>' +
                    '<td>' + data[i].firstName + '</td>' +
                    '<td>' + data[i].lastName + '</td>' +
                    '<td>' + data[i].email + '</td>' +
                    '<td>' + s + '</td>' +

                    '<td><button onclick="openModalUpdate('+data[i].id+')" id="updateButton"' + ' class="btn btn-info"' + ' data-toggle="modal"\n' +
                    'th:data-target="#update">Edit</button></td>' +

                    '<td><button onclick="openModal(' + data[i].id + ')" id="deleteId" data-userByModalId="' + data[i].id + '" class="btn btn-danger deleteButton" ' +
                    'data-toggle="modal"\n' +
                    'th:data-target="#delete">Delete</button></td>' +
                    '</tr>');
            }
        },
        error: function () {
            console.log("Error")
        }
    })
}