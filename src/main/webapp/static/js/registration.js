// window.onload = function(){
//     init();
// }
//
// function init(){
//     getRegistrationInfo();
// }
//
//
//
// function validation(){
//     let validity = true;
//
//
//     return validity
// }
//
// function getRegistrationInfo(){
//     let firstName = document.getElementById('firstName');
//     let lastName = document.getElementById('lastName');
//     let email = document.getElementById('email');
//     let password1 = document.getElementById('password');
//     let password2 = document.getElementById('password2');
//     let submitButton = document.getElementById('sign-in');
//     submitButton.addEventListener('click', (e)=>{
//         if(validation()){
//             sendRegistrationInfo(firstName, lastName, email, password1, password2);
//         } else{
//         alert("something went wrong");
//         }
//     })
// }
//
// function sendRegistrationInfo(firstName, lastName, email, password1){
//     const data = {name : firstName.value, email: email.value, password1: password1.value};
//     console.log(data.name)
//     fetch('/newuser', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(data),
//         })
//         .then(response => response.json())
//         .then((data) => {
//             console.log('Success:', data);
//         })
//         .catch((error) => {
//             console.error('Error:', error);
//         });
// }
