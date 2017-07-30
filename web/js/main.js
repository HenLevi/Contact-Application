$(document).ready(function () {
    contactListPage();
});

// Gets the contact list page template, puts it in document, then fills it with contact list
function  contactListPage() {
    $.ajax({
        url: "templates/listOfContact.html",
        type: "GET"
    })
            .done(function (html) {
                fillContactListWithData(html);
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to load contact list");
            });
}

function fillContactListWithData(template) {
    $.ajax({
        url: "api/contact/",
        type: "GET",
        dataType: "json"
    })
            .done(function (json) {
                var data = json["data"];
                var listContact = $(".contactList");
                listContact.html("");
                for (var i = 0; i < data.length; i++) { //for each contact
                    var contactList = data[i];
                    var tmp = $(template); //fill list of contact template with data from database
                    tmp.find(".name").text(contactList["name"]);
                    tmp.find(".job").text(contactList["job"]);
                    tmp.find(".img").attr("src", contactList["img"]);
                    tmp.attr("id", "listcontact_" + contactList["id"]);
                    tmp.find(".editBtn").attr("onclick", "editContact('" + contactList.id + "');return false;");
                    tmp.find(".deleteBtn").attr("onclick", "deleteContact('" + contactList.id + "');return false;");
                    listContact.append(tmp);
                }
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to load contact list");
            });
}

function deleteContact(id) {
    console.log(id);
    //request to delete the contact from the database
    $.ajax({
        url: "api/contact/deleteContact/" + id,
        type: "DELETE",
        dataType: "json"
    })
            .done(function (result) {
                if (result["status"] !== -1) { //if succeeds
                    id = result["data"];
                    var contact = $("#contact" + id);
                    contact.hide(); //hide the specific contact from list 
                    contactListPage(result);
                }
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to delete contact");
            });
}


function editContact(id) {
    var elem = $("#listcontact_" + id);
    console.log("id of contact:" + id);

    //for edit
    var obj = elem.find(".job");
    var switchToSpan = function ()
    {
        if (jQuery(this).val())
            var $text = jQuery(this).val();

        var $span = $("<span>", {
            text: $text
        });
        $span.addClass("job");
        $(this).replaceWith($span);

        var dataToSend = {
            "id": id,
            "name": elem.find(".name").text(),
            "job": elem.find(".job").text(),
             "img": ""
            
            
        };
        update(dataToSend).then(function (data) {
        });
    };

    var $input = $("<input>", {
        val: $(elem.find(".job")).text(),
        type: "text",
        width: "80%",
        rel: jQuery(obj).text()
    });
    $input.addClass("loadNum");
    $(obj).replaceWith($input);

    $input.keyup(function (e) {
        if (e.which === 13) {
            this.blur();
        }
    });
    $input.on("click", function (e) {  //Allow clicking inside the input field
        e.preventDefault();
    });
    $input.on("blur", switchToSpan);
    $input.select();
}

function update(contact) {
    return $.ajax({

        url: "api/contact/editContact/",
        type: "PUT",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(contact)
    })
            .done(function (html) {
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to load edit contact");
            });
}

// Gets the   add contact page template, puts it in document, then fills it with contact list
function  addContactListPage() {
    $.ajax({
        url: "templates/add.html",
        type: "GET"
    })
            .done(function (html) {
                fillAddContactListWithData(html);
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to load add contact list");
            });
}

function fillAddContactListWithData(template) {
    $.ajax({
        url: "api/contact/",
        type: "GET",
        dataType: "json"

    })
            .done(function (json) {
                var data = json["data"];
                var addContact = $(".addContact");
                addContact.html("");
                var addcontactList = data;
                var tmp = $(template); //fill new contact template with data from database
                tmp.find(".addBtn").attr("onclick", "addContact('" + addcontactList.id + "');return false;");
                tmp.find(".editBtn").attr("onclick", "editTask('" + addcontactList.id + "');return false;");
                tmp.find(".deleteBtn").attr("onclick", "deleteContact('" + addcontactList.id + "');return false;");
                addContact.append(tmp);
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: Unable to load add contact list");
            });
}


function addContact() {
    var toAdd = JSON.stringify({
        "name": $("#name").val(),
        "job": $("#job").val(),
        "img": ""
    });
    console.log(toAdd);
    $.ajax({
        url: "api/contact/addContact",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: toAdd
    })
            .done(function (html) {     //if secsess load again contactListPage
                contactListPage(html);
                $(".addContact").html("");
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("Error: to add contact");
            });
}