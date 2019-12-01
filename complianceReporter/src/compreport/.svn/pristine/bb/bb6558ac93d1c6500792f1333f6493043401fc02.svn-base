<!-- Script to support autocomplete database search -->

$('#requestSubmitBtn')
    .click(function () {
        var btn = $(this);
        btn.button('loading');
        setTimeout(function () {
            btn.button('reset')
        }, 1500)
});


$('#bAllDatabases').click(function () {
    if ($(this).is(':checked')) {
        //clear database search field
        clearSearchField();
        //hide the database fields
        hideSearchBlock();

        return true;
    } else {
        //show search block
        showSearchBlock();

        return true;
    }
});



$('#clear_search').click(function () {
    $("#name").select2("val", "");
});


function initialize() {

    //init autocomplete database search
    databaseSearch();
    //fade out messages
    fadeOutMessages();
    //afix side nav
    affixSideNav();
}


function affixSideNav() {
    $('.bs-docs-sidenav').affix({
        offset: {
            top: 40
        }
    });
}


function databaseSearch() {

    $("#name").select2({
        placeholder: "Search for one or more databases....",
        minimumInputLength: 2,
        multiple: true,
        quietMillis: 100,
        id: function (o) {
            return o.id + ':' + o.name;
        },
        initSelection: function (element, callback) {
            var data = [];
            $(element.val().split(",")).each(function () {
                //split the id:name token
                var value = this.split(":");
                data.push({id: value[0], name: value[1]});
            });
            callback(data);
        },
        ajax: {
            url: contextPath + '/databaseSearch',
            dataType: 'json',
            data: function (term, page) {
                return {
                    term: term
                };
            },
            results: function (data, page) {
                return {
                    results: data.dbInfos
                };
            }
        },
        formatResult: databaseFormatResult,
        formatSelection: databaseFormatSelection,
        dropdownCssClass: "bigdrop",
        escapeMarkup: function (m) {
            return m;
        }
    });
}


function databaseFormatResult(database) {

    //start creating html markup
    var tableMarkup = "<table class='table table-bordered table-condensed'>";

    //create row 1
    var tr1Markup = "<tr><td class='db-info'><div class='db-name'>";
    tr1Markup += database.name;
    tr1Markup += "</div></td></tr>";

    //create row 2
    var tr2Markup = "<tr><td class='db-details'>Usage: ";
    if (database.usage !== undefined) {
        tr2Markup += database.usage;
    }
    if (database.status !== undefined) {
        tr2Markup += " : Status: " + database.status;
    }
    if (database.virtualSqlInstance !== undefined) {
        tr2Markup += " : Virtual Sql Instance: " + database.virtualSqlInstance;
    }
    tr2Markup += "</td></tr>";

    //add row 1 and row 2 to the table
    tableMarkup += tr1Markup + tr2Markup + "</table>";

    return tableMarkup;
}

function databaseFormatSelection(database) {
    return database.name;
}


/*$("#name").on("change", function(e) {
 console.log(JSON.stringify({val:e.val, added:e.added, removed:e.removed}));
 });*/


function fadeOutMessages() {
    $('#success').fadeOut(35000);
    $('#errors').fadeOut(35000);
}


//clear database fields
function clearSearchField() {
    $("#name").val("").trigger("change");
}


function clearAllDatabaseCheckbox() {
    $('#bAllDatabases').val('false');
}


function showSearchBlock() {
    $('#searchBlock').fadeIn(1500);
}


function hideSearchBlock() {
    $('#searchBlock').fadeOut(1500);
}




