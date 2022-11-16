(function ($) {
    "use strict";
    //Inactivity function
    var idleTime = 0;
    $(document).ready(function () {
        // Increment the idle time counter every minute.
        setInterval(timerIncrement, 60000); // 1 minute
        // Zero the idle timer on mouse movement.
        $(this).mousemove(function (e) {
            idleTime = 0;
        });
        $(this).keypress(function (e) {
            idleTime = 0;
        });
    });
    function timerIncrement() {
        idleTime = idleTime + 1;
        if (idleTime > 14) { // 15 minutes
            alert("You have been inactive for 15 minutes\nSite will be reloaded")
            window.location.reload();
        }
    }

})(jQuery);