const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            email: "",
            password: "",
            name: "",
            lastName: "",
            loggedIn: false



        };
    },
    created() {
    },
    methods: {
        signIn() {
            axios.post('/api/login', 'email=' + this.email + '&password=' + this.password, {
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
        createAccount(){
                    axios.post('/api/clients/current/accounts')
                    .then(response => {
                    console.log("account created!!!");
                    })
                    .catch(error => {
                    console.log(error)
                    })
                },
        signUp() {
            axios.post('/api/clients', 'first=' + this.name + '&lastName=' + this.lastName + '&email=' + this.email + '&password=' + this.password,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => {
                    console.log("registered");
                    this.signIn();
                    this.createAccount();
                })
                .catch(function (error) {
                  if (error.response) {          
                    Swal.fire({
                      icon: 'error',
                      title: 'Please, try again.',
                      text: 'Make sure you filled all the fields.'
                    }) } else if (error.request) {
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
        

    }
})
app.mount('#app');