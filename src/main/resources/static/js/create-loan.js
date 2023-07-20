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
      loanName: "",
      maxAmount: 0,
      payments: 0,
      percentage: 0
      
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
axios.get('http://localhost:8080/api/clients/current')
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
createLoan(){
    axios.post('/api/admin/loans', 'loanName=' + this.loanName + '&maxAmount=' + this.maxAmount + '&payments=' + this.payments + '&percentage=' + this.percentage, 
    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
  .then(response => {
    console.log("loan created!!!");    
  })
  .catch(error => {
    console.log(error)

  })
},
confirmOperation() {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: 'Are you sure?',
      text: "You are about to create a new loan.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, create.',
      cancelButtonText: 'No, cancel.',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        swalWithBootstrapButtons.fire(
          'Loan succesfully created!',
          'See you soon.',
          'success',
          this.createLoan()
        )
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelled',
          'No loan created :)',
          'error'
        )
      }
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