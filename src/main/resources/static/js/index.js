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
        if (this.email && this.password) {         
          console.log('Signed in!!');
          this.showErrorMessage = false;
        } else {
          this.showErrorMessage = true;
        }
         this.loggedIn = true;
         this.loggedOut = false;
        window.location.href = '/web/accounts.html';
       
      })
      .catch(error => console.error('Error', error));
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