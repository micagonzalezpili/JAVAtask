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
        

    }
})
app.mount('#app');