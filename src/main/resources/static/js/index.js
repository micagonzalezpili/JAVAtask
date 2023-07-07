
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
        window.location.href = '/web/accounts.html';
       
      })
      .catch(function (error) {
         if (error.response) {          
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Make sure your email and password are correct.'
          })       
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