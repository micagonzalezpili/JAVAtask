
const { createApp } = Vue;

const app = createApp({
  data() {
    return {          
      email: "",
      password: "",
      loggedIn: false,
      loggedOut: true,
      showErrorMessage: false
         
    };
  },
  created() {   
},
methods: {   

  signIn() {
    axios.post('/api/login', 'email=' + this.email + '&password=' + this.password, { // mando los datos como una key
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
      .then(response => {        
         this.loggedIn = true;
         this.loggedOut = false;
      /*    Swal.fire({
          title: 'Error!',
          text: 'Do you want to continue',
          icon: 'error',
          confirmButtonText: 'Cool'
        })
         const Toast = Swal.mixin({
          toast: true,
          position: 'top-end',
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
          }
        })        
        Toast.fire({
          icon: 'success',
          title: 'Signed in successfully'
        }) */
        window.location.href = '/web/accounts.html';
       
      })
      .catch(function (error) {
        if (error.response) {          
          alert("Please, fill all the fields and make sure your email and password are correct.",error.response.status);          
        } else if (error.request) {
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
          // http.ClientRequest in node.js
          console.log(error.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log('Error', error.message);
        }
        console.log(error.config);
      });
  }, 
  logOut() {
    axios.post('/api/logout')
      .then(response => {
        console.log('Signed out!!');
        window.location.href = '/index.html';
      })
      .catch(error => {
        console.error('Error', error);
      });
  } 
  
}})
app.mount('#app');