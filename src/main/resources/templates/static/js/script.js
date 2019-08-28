// console.log("Konnichiha!");

//I18n
$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace('international?lang=' + selectedOption);
        }
    });
});

// TODO: Apply poppers (popper.js) instead of other stuff
// $('.frequency').forEach(el => new Tooltip(el));

// //Custom tags
// function customTag(tagName, tagFunction) {
//     document.createElement(tagName);
//     var tagInstances = document.getElementsByTagName(tagName);
//     for ( var i = 0; i < tagInstances.length; i++) {
//         tagFunction(tagInstances[i]);
//     }
// }
//
// function () {
//
// }
//
// customTag("codingdude-gravatar", codingdudeGravatar);