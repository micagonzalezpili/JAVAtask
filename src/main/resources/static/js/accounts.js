const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],     
      nombre: "",
      apellido: "",   
      accounts: [],
      loans: [],
      loggedIn: true,
      activeAcc: [],
      savingAcc: [],
      checkingAcc: [],
      accType: ""
      
    };
  },
  created() {
    this.loadData();      
},
methods: {
  precioFormat(number){
    USDollar = new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
  });
  return USDollar.format(number)
}, 
loadData(){
axios.get('/api/clients/current')
         .then(response => {
         this.data = response.data
         console.log(this.data);
         this.nombre = this.data.firstName         
         this.apellido = this.data.lastName         
         this.accounts = this.data.accounts
         console.log(this.accounts);
         this.loans = this.data.clientLoans
         console.log(this.loans);     
         this.activeAcc = this.accounts.filter(acc => acc.active == true)
         console.log(this.activeAcc);
         this.savingAcc = this.accounts.filter(acc => acc.accountType == 'SAVING' && acc.active == true)
         console.log(this.savingAcc);
         this.checkingAcc = this.accounts.filter(acc => acc.accountType == 'CHECKING' && acc.active == true)
         console.log(this.checkingAcc);
         })
         .catch(error => {
           console.error(error);
         });
},
createAccount(){
  axios.post('/api/clients/current/accounts', 'accountType=' + this.accType)
  .then(response => {
    console.log("account created!!!");
    window.location.href = '/web/accounts.html';
  })
  .catch(error => {
    console.log(error)
  })
},
deleteAccount(id){
  axios.patch(`/api/clients/current/accounts?id=${id}`)
      .then(response => {
        console.log('account deleted!!');
        this.loadData();

      })
      .catch(error => {
        //alert(error.response.data); // hacer alert mas lindo!!!
        Swal.fire(
          'Oops..',
          `${error.response.data} Please try again.`,
          'error'
        )
      })
    },
logOut() {
  console.log("hola");
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