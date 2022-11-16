$(function () {

    startdateId.max = new Date().toISOString().split("T")[0];
    enddateId.max = new Date().toISOString().split("T")[0];
    var start = document.getElementById('startdateId');
    var end = document.getElementById('enddateId');

    start.addEventListener('change', function () {
        if (start.value)
            end.min = start.value;
    }, false);

    end.addEventListener('change', function () {
        if (end.value)
            start.max = end.value;
    }, false);

});