$(document).ready(function() {

    // alert("TESTING")

    // Now targeting the navbar ul by the class menus....

    $(".menus li a").click(function(e) {

        // alert("Click Testing")

        let target = $(this).attr("href");
        // Here this is what we are clicking the li a of navbar...
        // Here our target is that div containing the target...

        $("html,body").animate({
            scrollTop: $(target).offset().top - 58,
        }, 1000);

        // Notice we are taking html,body
        // Here 1000 is 1000 ms delay
        // 58 is our navbar height, we have to adjust accordingly... 

        e.preventDefault();

    })


})