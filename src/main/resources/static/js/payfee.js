const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],
      nombre: "",
      apellido: "",
      accounts: [],
      loggedIn: true,
      own: "",
      other: "",
      description: "",
      amount: 0,
      accChosen: "",
      accountSelected: "3",
      arrayAccounts: [],
      loans: [],
      loanSelected: 0

    };
  },
  created() {
    this.loadData();

  },
  methods: {
    loadData() {
      axios.get('/api/clients/current')
        .then(response => {
          this.data = response.data
          console.log(this.data);
          this.nombre = this.data.firstName
          this.apellido = this.data.lastName
          this.accounts = this.data.accounts
          console.log(this.accounts);
          this.arrayAccounts = this.accounts.map(account => account.number)
          console.log(this.arrayAccounts);
          this.loans = this.data.clientLoans
          console.log(this.loans);
        })
        .catch(error => {
          console.error(error);
        });
    },
    payFee() {
        console.log(this.loanSelected);
      axios.post('/api/loans/payment', 'originAcc=' + this.accountSelected + '&id=' + this.loanSelected.id, 
       { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
          console.log("loan payed!!!");
         /*  window.location.href= '/web/accounts.html' */
        })
        .catch(
          error => {
            console.log(error);
            Swal.fire(
                'Oops..',
                `${error.response.data}`,
                'error'
              )
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
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, transfer.',
        cancelButtonText: 'No, cancel.',
        reverseButtons: true
      }).then((result) => {
        if (result.isConfirmed) {
          swalWithBootstrapButtons.fire(
            'Fee succesfully paid!',
            'Thanks for operating with CashFlow.',
            'success',
            this.payFee()
          )
        } else if (
          /* Read more about handling dismissals below */
          result.dismiss === Swal.DismissReason.cancel
        ) {
          swalWithBootstrapButtons.fire(
            'Cancelled',
            'No payment done :)',
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
  }/* ,
  computed: {
    
    selectedAccountBalance() {
    
      if (this.accountSelected) {
        const selectedAccount = this.accounts.find(account => account.id === this.accountSelected);
        return selectedAccount.balance;
      } else {
        return 0;
      }
    }
  } */
})
app.mount('#app');