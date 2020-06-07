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

                body.append($('<tr id="' + data[i].id + '">').append(
                    $('<td>').append($('<span>')).text(data[i].id),
                    $('<td>').append($('<span>')).text(data[i].firstName),
                    $('<td>').append($('<span>')).text(data[i].lastName),
                    $('<td>').append($('<span>')).text(data[i].email),
                    $('<td>').append($('<span>')).append(s),
                    $('<td>').append($('<button onclick="openModalUpdate('+data[i].id+')">').text("Edit").attr({
                            "id": "updateButton",
                            "class": "btn btn-info",
                            "data-toggle": "modal",
                            "th:data-target": "#update" + data[i].id
                        }).data("user", data[i]),
                    ),
                    $('<td>').append($('<button onclick="openModal(' + data[i].id + ')">').text("Delete").attr({
                        "id": "deleteId",
                        "data-userByModalId": data[i].id,
                        "class": "btn btn-danger deleteButton",
                        "data-toggle": "modal",
                        "data-target": "#delete" + data[i].id
                    }).data("user", data[i]))
                ));
            }
        },
        error: function () {
            console.log("Error")
        }
    })
}