// PAYMENT GATEWAY INTEGRATION 

// First Request to Server to Create Order

const paymentStart = () => {

    // To check whether our function is working or not...
    console.log("Payment Started...");

    let amount = $("#payment_field").val();    

    // To check whether we are recieving amount from the field or not...
    console.log(amount);

    if (amount == '' || amount == null) {

        alert("Amount is required");

        // Used SweetAlert Js after placing its Cdn in normal/base.html
        //swal("Failed !", "Amount is required !", "error");

        return;

    }   
        
    var billingDetailsFirstName = $("#billingDetailsFirstName").val();
    var billingDetailsLastName = $("#billingDetailsLastName").val();
    var billingDetailsAddress1 = $("#billingDetailsAddress1").val();
    var billingDetailsAddress2 = $("#billingDetailsAddress2").val();
    var billingDetailsPinCode = $("#billingDetailsPinCode").val();
    var billingDetailsCity = $("#billingDetailsCity").val();
    var billingDetailsPhone = $("#billingDetailsPhone").val();
	var billingDetailsEmail = $("#billingDetailsEmail").val();
    var billingDetailsAdditionalInfo = $("#billingDetailsAdditionalInfo").val();
    
    var isValid = $("#isValid").val(); 
     
    // To check whether the values we get or not from the form data...     
    console.log(billingDetailsFirstName);    
    console.log(billingDetailsLastName);
    console.log(billingDetailsAddress1);
    console.log(billingDetailsAddress2);
    console.log(billingDetailsPinCode);
    console.log(billingDetailsCity);
    console.log(billingDetailsPhone);
    console.log(billingDetailsEmail);
    console.log(billingDetailsAdditionalInfo);
            
    // Code...
    // Using Ajax to send request to server to create order and that of JQuery

    $.ajax({
        url: '/create_order', // At which url the request is sent to server
        data: JSON.stringify({ amount: amount, info: 'order_request', 
        billingDetailsFirstName:billingDetailsFirstName,
        billingDetailsLastName:billingDetailsLastName,
        billingDetailsAddress1:billingDetailsAddress1,
        billingDetailsAddress2:billingDetailsAddress2,
        billingDetailsPinCode:billingDetailsPinCode,
        billingDetailsCity:billingDetailsCity,
        billingDetailsPhone:billingDetailsPhone,
        billingDetailsEmail:billingDetailsEmail,
        billingDetailsAdditionalInfo:billingDetailsAdditionalInfo,
        isValid
        }), // Right side amount is comming from above and stored at left side amount
        contentType: 'application/json',
        type: 'POST',       
        dataType: 'json',
        success: function(response) {

            // This function is invoked when success...
            console.log(response);

            if (response.status == "created") {

                // Initiating Open Payment Form...

                // Creating options variable which is a object

                let options = {

                    key: 'rzp_test_vcCYga8goFLnmu', // Generate id from our razorpay dashboard
                    amount: response.amount, // Amount comming from our response
                    currency: 'INR', // Indian Ruppees
                    name: 'Bakery Order',
                    description: 'Order Of User',
                    image: 'https://play-lh.googleusercontent.com/D-haUsSx771Pt4brCyFEJUNKZaC8NUsD2bMB-ZL_yE2LnYdmt3YbgfZwDDM9B-wBHw', // We can set our image which is to be displayed...
                    order_id: response.id,
                    handler: function(response) {
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);

                        console.log('Payment Successfull !');

                        // Using Function to send data after successfull payment to update in database by using it...
                        // Function is created at bottom of this file...

                        updatePaymentOnServer(response.razorpay_payment_id, response.razorpay_order_id, 'paid');

                        // alert("Congratulations!! Payment Successfull");

                        // Used SweetAlert Js after placing its Cdn in normal/base.html
                        // swal("Success!", "Congratulations!! Payment Successfull", "success");
                        // Commented because now called this above alert in updatePaymentOnServer()

                    },
                    prefill: {
                        name: "",
                        email: "",
                        contact: ""
                    },
                    notes: {
                        address: "Amresh Garg - Creator"                                                
                    },
                    theme: {
                        color: "#3399cc"
                    }

                };

                // Initiating Payment

                // Creating Object of Razorpay and passing options to it...

                let rzp = new Razorpay(options);

                // We could also create a handler for it but here we doing that task here itself...

                // On payment fail this function gets executed...
                // Here we used console.log instead of alert from build integration razorpay documentation...

                rzp.on('payment.failed', function(response) {

                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);

                    // Added by me for giving alert!!

                    alert(" Oops... Payment Failed !! ");

                    // Used SweetAlert Js after placing its Cdn in normal/base.html
                    // swal("Failed !", "Oops... Payment Failed !!", "error");

                });

                rzp.open();


            }


        },
        error: function(error) {

            // This function is invoked when error...
            console.log(error)
            alert("Something went wrong !!")

        }

    })

};

// Creating that function...

function updatePaymentOnServer(payment_id, order_id, status) {

    $.ajax({
        url: '/update_order', // Another url which sends the details to server after successfull payment, so that the server can store and update that details in their database...
        data: JSON.stringify({
            payment_id: payment_id,
            order_id: order_id,
            status: status
        }), // Right side amount is comming from above and stored at left side amount
        contentType: 'application/json',
        type: 'POST',
        dataType: 'json',
        success: function(response) {
            console.log("**** Congratulations!! Payment Successfull ****")
            // swal("Success!", "Congratulations!! Payment Successfull", "success");
        },
        error: function(error) {
            // When data failed to go on server due to some reasons...
            
            console.log("**** Your Payment is Successfull, But Unable to get it on server, We will contact you as soon as possible !! ****")
            
            // Giving Fail Sweet-Alert...
            //swal("Failed !", "Your Payment is Successfull, But Unable to get it on server, We will contact you as soon as possible !!", "error");
       
        }

    });

}