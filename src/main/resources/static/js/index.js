const { createApp } = Vue;

const app = createApp({
  data() {
    return {          
      email: "",
      password: "",
      loggedIn: false
    
      
       
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
        console.log('Signed in!!');
         this.loggedIn = true;
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